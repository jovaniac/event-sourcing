package com.eventsourcing.eventsourcinglib.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

/*
 * @author kaihe
 * 
 */


@Data
@EqualsAndHashCode(callSuper=false)
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ServerError serverError;

    public BaseException(String serverStatusCode, String statusDesc, AdditionalStatus.Severity severity) {
        this.serverError = ServerError.builder()
                .serverStatusCode(serverStatusCode)
                .severity(severity)
                .statusDesc(statusDesc)
                .build();
    }

    public String getServerStatusCode() {
        return serverError != null ? serverError.getServerStatusCode() : null;
    }
    
    public String getStatusDesc() {
        return serverError != null ? serverError.getStatusDesc() : null;
    }
}
