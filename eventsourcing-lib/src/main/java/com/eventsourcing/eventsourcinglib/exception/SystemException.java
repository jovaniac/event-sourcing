package com.eventsourcing.eventsourcinglib.exception;

import com.eventsourcing.eventsourcinglib.util.CommonConstants;

/*
 * @author kaihe
 * 
 */

public class SystemException extends BaseException {

    private static final long serialVersionUID = 1L;

    public SystemException(String serverStatusCode) {
        super(serverStatusCode, CommonConstants.SYSTEM_ERROR_DESCRIPTION, CommonConstants.SYSTEM_ERROR_SEVERITY);
    }

    public SystemException(String serverStatusCode, String statusDesc) {
        super(serverStatusCode, statusDesc, CommonConstants.SYSTEM_ERROR_SEVERITY);
    }
}
