package com.eventsourcing.eventsourcinglib.exception;

import com.eventsourcing.eventsourcinglib.util.CommonConstants;

/*
 * @author kaihe
 * 
 */

public class ValidationException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ValidationException(String serverStatusCode) {
        super(serverStatusCode, CommonConstants.VALIDATION_ERROR_DESCRIPTION, CommonConstants.VALIDATION_ERROR_SEVERITY);
    }
    
    public ValidationException(String serverStatusCode, String statusDesc) {
        super(serverStatusCode, statusDesc, CommonConstants.VALIDATION_ERROR_SEVERITY);
    }

}
