package com.tartigrado.df.validadores.cte;

import com.tartigrado.df.validadores.cte.exception.CTeXSDValidationException;

public interface CTeValidador {

    void validaTConsStatServ(String tConsStatServ) throws CTeXSDValidationException;

    void validaTEnviCTe(String tEnviCTe) throws CTeXSDValidationException;

    void validaTConsReciCTe(String tConsReciCTe) throws CTeXSDValidationException;

    void validaTEvento(String tEvento) throws CTeXSDValidationException;

    void validaEvCancCTe(String evCancCTe) throws CTeXSDValidationException;
}
