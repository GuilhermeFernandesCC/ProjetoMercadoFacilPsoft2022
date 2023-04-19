package com.ufcg.psoft.mercadofacil.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MercadoFacilException extends RuntimeException{
    private String errorCode;
    private String message;

}
