package com.asiainfo.util;

/**
 * @Auther: zhuyj
 * @Date: 2018/7/31/031 11:06
 * @JIRA:
 * @Description:
 */
public class CustomerException extends RuntimeException{
    public CustomerException() {
    }

    public CustomerException(String message) {
        super(message);
    }

    public CustomerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerException(Throwable cause) {
        super(cause);
    }

    public CustomerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
