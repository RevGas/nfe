package com.fincatto.documentofiscal;

import java.io.IOException;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;


public class DFSocketFactory {

    private final DFConfig config;

    public DFSocketFactory(final DFConfig config) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        this.config = config;
    }

    public SSLContext createSSLContext() throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException, GeneralSecurityException {
        final KeyManager[] keyManagers = createKeyManagers(config);
        final TrustManager[] trustManagers = createTrustManagers(config);

        final SSLContext sslContext = SSLContext.getInstance(config.getSSLProtocolos()[0]);
        sslContext.init(keyManagers, trustManagers, null);
        return sslContext;
    }

    private static KeyManager[] createKeyManagers(final DFConfig config) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, GeneralSecurityException {
        final String alias = getAlias(config.getCertificadoKeyStore());
        return new KeyManager[]{new CustomX509KeyManager(getKeyManagerForKeystore(config.getCertificadoKeyStore(), config.getCertificadoSenha()), alias)};
    }

    private static TrustManager[] createTrustManagers(final DFConfig config) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

        final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(config.getCadeiaCertificadosKeyStore());
        return trustManagerFactory.getTrustManagers();
    }

    private static String getAlias(final KeyStore ks) throws KeyStoreException {
        final Enumeration<String> aliasesEnum = ks.aliases();
        while (aliasesEnum.hasMoreElements()) {
            final String alias = aliasesEnum.nextElement();
            if (ks.isKeyEntry(alias)) {
                return alias;
            }
        }
        throw new KeyStoreException("Nenhum alias encontrado no certificado");
    }

    /**
     * Obtém um X509KeyManager para o KeyStore enviado como parâmetro.
     * @param keyStore KeyStore
     * @param password Senha do KeyStore
     * @return Retorna uma instância de X509KeyManager
     * @throws GeneralSecurityException
     */
    private static X509KeyManager getKeyManagerForKeystore(KeyStore keyStore, String password) throws GeneralSecurityException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509", "SunJSSE");
        keyManagerFactory.init(keyStore, password.toCharArray());
        for (KeyManager keyManager : keyManagerFactory.getKeyManagers()) {
            if (keyManager instanceof X509KeyManager) {
                return (X509KeyManager) keyManager;
            }
        }
        throw new IllegalStateException("Não foi possível encontrar um X509KeyManager");
    }

}

class CustomX509KeyManager  implements X509KeyManager {
    private final X509KeyManager keyManager;
    private final String alias;

    public CustomX509KeyManager(X509KeyManager keyManager, String alias) {
        this.keyManager = keyManager;
        this.alias = alias;
    }

    @Override
    public String chooseClientAlias(String[] keyTypes, Principal[] issuers, Socket socket) {
        return this.alias;
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        return this.alias;
    }

    @Override
    public X509Certificate[] getCertificateChain(String alias) {
        return this.keyManager.getCertificateChain(alias);
    }

    @Override
    public  String[] getClientAliases(String keyType, Principal[] issuers) {
        return this.keyManager.getClientAliases(keyType, issuers);
    }

    @Override
    public PrivateKey getPrivateKey(String alias) {
        PrivateKey privateKey = this.keyManager.getPrivateKey(alias);
        return privateKey;
    }

    @Override
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        return this.keyManager.getServerAliases(keyType, issuers);
    }

}