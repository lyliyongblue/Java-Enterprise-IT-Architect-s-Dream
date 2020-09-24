package com.liyong.flux.context;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.liyong.flux.context.exception.ResourceNotFoundException;

@Component
public class CommonExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public Object resourceNotFound(ResourceNotFoundException e) {
		return e.getMessage();
	}
}
