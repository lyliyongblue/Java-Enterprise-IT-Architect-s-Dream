package com.yong.spring.data.jpa.commons;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class ResultHandler<T> implements Serializable {
	private static final long serialVersionUID = -2311850324633143627L;
	/** 业务执行成功: 1 */
	public static final String SUCCESS = "1";
	/** 业务执行失败: 0 */
	public static final String ERROR = "0";
	
	private String code;	//业务代码；  除成功，失败之外的业务代码由业务自行定义
    private String msg;  //业务代码描述
    private T data;    //业务返回数据

    public ResultHandler() {
    }
    
    /**
     * 构造函数，由调用者制定属性值
     * @param code 业务代码
     * @param msg  业务代码描述
     * @param data 业务数据
     */
    public ResultHandler(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 表示业务处理成功
     * @param data  业务数据
     */
    public static <T> ResultHandler<T> success(T data) {
        return new ResultHandler<T>(ResultHandler.SUCCESS, "", data);
    }
    
    /**
     * 表示业务处理成功
     * @param msg 业务代码描述
     * @param data 业务数据
     */
    public static <T> ResultHandler<T> success(String msg, T data) {
    	return new ResultHandler<T>(ResultHandler.SUCCESS, msg, data);
    }

    /**
     * 表示业务处理失败
     * @param data 业务数据
     */
    public static <T> ResultHandler<T> error(T data) {
        return new ResultHandler<T>(ResultHandler.ERROR, "", data); 
    }  

    /**
     * 表示业务处理失败
     * @param msg 业务描述
     * @param data 业务数据
     */
    public static <T> ResultHandler<T> error(String msg, T data) {
        return error(ResultHandler.ERROR, msg, data);
    }
    
    public static <T> ResultHandler<T> error(String code, String msg, T data) {
        return new ResultHandler<T>(code, msg, data);
    }

    /**
     * 业务代码
     */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 业务代码描述
	 */
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 业务数据
	 */
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	@JsonIgnore
	public boolean success() {
		return ResultHandler.SUCCESS.equals(this.code);
    }
}
