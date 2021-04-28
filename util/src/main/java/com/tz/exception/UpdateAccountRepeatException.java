package com.tz.exception;

/**
 * 更新用户数据账号重复异常，不需要回到更新页面
 */
public class UpdateAccountRepeatException extends RuntimeException {

    static final long serialVersionUID = 703L;

    public UpdateAccountRepeatException() {
        super();
    }

    public UpdateAccountRepeatException(String message) {
        super(message);
    }

    public UpdateAccountRepeatException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateAccountRepeatException(Throwable cause) {
        super(cause);
    }

    protected UpdateAccountRepeatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
