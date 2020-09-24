package com.yong.spring.jpa.jms.commons;
 
/**
 * 所有不需要业务代码处理的异常，需要公共框架处理的异常，都必须继承该类，一遍框架对该框架做特殊处理
 * @author created by liyong on 2018年2月5日 下午4:58:37
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -6340813251799316478L;

    public BusinessException() {
        super();
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}

	