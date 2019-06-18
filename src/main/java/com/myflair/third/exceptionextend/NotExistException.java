package com.myflair.third.exceptionextend;

/**
 * Created by user on 2018/4/6.
 */
public class NotExistException extends  Exception {

    public NotExistException(String message) {
        super(message);
    }

    public NotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistException(Throwable cause) {
        super(cause);
    }

    public NotExistException() {
    }
}
