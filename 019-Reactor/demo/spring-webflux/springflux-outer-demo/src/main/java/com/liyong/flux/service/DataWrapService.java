package com.liyong.flux.service;

import org.springframework.stereotype.Service;

@Service
public class DataWrapService {
	public String wrapData(String data) {
		return "[" + data + "]";
	}
}
