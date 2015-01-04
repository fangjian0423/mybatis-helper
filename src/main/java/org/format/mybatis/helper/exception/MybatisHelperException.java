package org.format.mybatis.helper.exception;

public class MybatisHelperException extends RuntimeException {

    public MybatisHelperException() {
        super();
    }

    public MybatisHelperException(String msg) {
        super(msg);
    }

    public MybatisHelperException(Throwable e) {
        super(e);
    }

    public MybatisHelperException(String msg, Throwable e) {
        super(msg, e);
    }

}
