package com.tz.exception;

/**
 * 添加或者更新数据用户数据时用户名重复抛出的异常
 */
public class LoginAcctDuplicateException extends RuntimeException {

    static final long serialVersionUID = -7L;

    public LoginAcctDuplicateException() {
        super();
    }

    public LoginAcctDuplicateException(String message) {
        super(message);
    }

    public LoginAcctDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctDuplicateException(Throwable cause) {
        super(cause);
    }

    protected LoginAcctDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
