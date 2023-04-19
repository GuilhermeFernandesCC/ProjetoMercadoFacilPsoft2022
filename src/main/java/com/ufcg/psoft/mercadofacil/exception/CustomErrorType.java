package com.ufcg.psoft.mercadofacil.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorType {

    //@JsonProperty("timestamp")
    //private LocalDateTime timestamp;
    @JsonProperty("errorCode")
    private String errorCode;
    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<String> errors = new ArrayList<String>();

    public CustomErrorType(MercadoFacilException mfe) {
        //this.timestamp = LocalDateTime.now();
        this.errorCode = mfe.getErrorCode();
        this.message = mfe.getMessage();
        this.errors = new ArrayList<>();
    }

}
