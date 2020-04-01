package com.fincatto.documentofiscal;

import br.inf.portalfiscal.nfe.TEnviNFe;
import br.inf.portalfiscal.nfe.TNfeProc;
import br.inf.portalfiscal.nfe.TRetEnviNFe;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.MultipleFileDownload;
import com.amazonaws.services.s3.transfer.Transfer;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.fincatto.documentofiscal.utils.Util;
import org.apache.commons.io.FileUtils;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * Responsável pelas operações de conexão com o Amazon com.fincatto.documentofiscal.S3, upload, download e
 * cópia de objetos.
 *
 * @author paulo
 */
public class S3 {

    private AmazonS3 s3client;
    public static final String bucket = "revgas-files";

    public S3() {
        connect();
    }

    public AmazonS3 getS3client() {
        return s3client;
    }

    /**
     * Envia um arquivo para um bucket
     *
     * @param bucketName Nome do bucket destino
     * @param key        O caminho do arquivo no bucket com o nome e extensão. Ex.:
     *                   /path/to/file.pdf
     * @param file       O arquivo a ser enviado
     * @return
     * @throws AmazonServiceException sf o upload for rejeitado.
     *                                Isso pode acontecer quando a permissão é negada, por exemplo
     * @throws AmazonClientException  se o cliente encontrar algum erro
     *                                durante a comunicação com o com.fincatto.documentofiscal.S3. Ex.: Sem internet
     * @see http://docs.aws.amazon.com/AmazonS3/latest/dev/UploadObjSingleOpJava.html
     */
    public Boolean uploadFile(String bucketName, String key, File file) {
        try {
            getS3client().putObject(new PutObjectRequest(bucketName, key, file));
            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Faz o download de um arquivo da Amazon com.fincatto.documentofiscal.S3. O download retorna uma
     * InputStream, essa stream e salva em um arquivo temporário no diretório
     * temporário usado pela JVM
     *
     * @param bucketName
     * @param key
     * @return
     * @throws AmazonS3Exception
     * @throws IOException
     */
    public File downloadFile(String bucketName, String key) throws IOException {
        try (S3Object s3obj = getS3client().getObject(new GetObjectRequest(bucketName, key));
             InputStream in = s3obj.getObjectContent()) {

            String path = System.getProperty("java.io.tmpdir");
            String filename = Paths.get(key).getFileName().toString();

            File file = new File(path + "/" + filename);
            if (file.exists()) {
                file.delete();
            }
            if (!file.exists()) {
                file.createNewFile();
            }

            // reescreve o arquivo
            OutputStream out = new FileOutputStream(file, false);

            int read = 0;
            byte[] bytes = new byte[2048]; // buffer de 2KB

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            out.close();

            return file;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public boolean objectExists(String bucket, String key) {
        try {
            s3client.getObjectMetadata(bucket, key);
        } catch (AmazonS3Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Realizar o download de um diretório do com.fincatto.documentofiscal.S3
     * OBS: Configurar variáveis de ambiente do tomcat
     */
    public void downloadDir(String bucket_name, String key_prefix, String dir_path, boolean pause) {
        TransferManagerBuilder builder = TransferManagerBuilder.standard();
        builder.setS3Client(s3client);
        TransferManager xfer_mgr = builder.build();
        try {
            MultipleFileDownload xfer = xfer_mgr.downloadDirectory(
                    bucket_name, key_prefix, new File(dir_path));
            waitForCompletion(xfer);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        xfer_mgr.shutdownNow();
    }

    public void waitForCompletion(Transfer xfer) {
        try {
            xfer.waitForCompletion();
        } catch (AmazonServiceException e) {
            System.err.println("Amazon service error: " + e.getMessage());
            System.exit(1);
        } catch (AmazonClientException e) {
            System.err.println("Amazon client error: " + e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            System.err.println("Transfer interrupted: " + e.getMessage());
            System.exit(1);
        }
    }

    public String generatePresignedUrl(String bucketName, String key) {

        // Set the presigned URL to expire after one hour.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);

        // Generate the presigned URL.
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, key)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);

        return url.toString();
    }

    public static String getPath(String chaveNFe, String event, String tpAmb) {
        String cnpj = chaveNFe.substring(6, 20);
        String ano = "20" + chaveNFe.substring(2, 4);
        String mes = chaveNFe.substring(4, 6);
        String ambiente = tpAmb.equals(DFAmbiente.HOMOLOGACAO.getCodigo()) ? "hom" : "prod";
        return String.format("log-df/%s/%s/%s/%s/%s/%s.xml", ambiente, cnpj, event, ano, mes, chaveNFe);
    }

    public void sendEnviNFe(final String xml) throws Exception {
        TEnviNFe enviNFe = (TEnviNFe) Util.unmarshler(TEnviNFe.class, xml);
        String chaveNF = Util.chaveFromTNFe(enviNFe.getNFe().get(0));
        File xmlTemp = File.createTempFile(chaveNF, ".xml");
        FileUtils.writeByteArrayToFile(xmlTemp, xml.getBytes(StandardCharsets.UTF_8));
        this.uploadFile(bucket, getPath(chaveNF, "enviNFe", enviNFe.getNFe().get(0).getInfNFe().getIde().getTpAmb()), xmlTemp);
    }

    public void sendRetEnviNFe(final String xml, TRetEnviNFe retEnviNFe) throws Exception {
        String chaveNF = retEnviNFe.getProtNFe().getInfProt().getChNFe();
        File xmlTemp = File.createTempFile(chaveNF, ".xml");
        FileUtils.writeByteArrayToFile(xmlTemp, xml.getBytes(StandardCharsets.UTF_8));
        this.uploadFile(bucket, getPath(chaveNF, "retEnviNFe", retEnviNFe.getTpAmb()), xmlTemp);
    }

    public void sendProcNFe(String xml) throws JAXBException, IOException {
        TNfeProc tNfeProc = (TNfeProc) Util.unmarshler(TNfeProc.class, xml);
        String chaveNF = Util.chaveFromTNFe(tNfeProc.getNFe());
        File xmlTemp = File.createTempFile(chaveNF, ".xml");
        FileUtils.writeByteArrayToFile(xmlTemp, xml.getBytes(StandardCharsets.UTF_8));
        this.uploadFile(bucket, getPath(chaveNF, "nfeProc", tNfeProc.getNFe().getInfNFe().getIde().getTpAmb()), xmlTemp);
    }



    /**
     * Realiza a conexão com o Amazon com.fincatto.documentofiscal.S3
     * OBS: Configurar variáveis de ambiente do tomcat
     */
    private void connect() {
        BasicAWSCredentials creds = new BasicAWSCredentials(System.getProperty("AWS_ACCESS_KEY_ID"), System.getProperty("AWS_SECRET_KEY"));
        s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.SA_EAST_1).withCredentials(new AWSStaticCredentialsProvider(creds)).build();
    }
}