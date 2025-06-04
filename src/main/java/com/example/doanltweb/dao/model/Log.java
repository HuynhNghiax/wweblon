package com.example.doanltweb.dao.model;

import java.time.LocalDateTime;

public class Log {
	private int id;
	private int userId;
	private String level;
	private LocalDateTime dateTime;
	private String ipAddress;
	private String resource;
	private String dataIn;
	private String dataOut;
	public Log(int id, int userId, String level, LocalDateTime dateTime, String ipAddress, String resource,
			String dataIn, String dataOut) {
		super();
		this.id = id;
		this.userId = userId;
		this.level = level;
		this.dateTime = dateTime;
		this.ipAddress = ipAddress;
		this.resource = resource;
		this.dataIn = dataIn;
		this.dataOut = dataOut;
	}
	public Log() {
		super();
	}
	
	
	
}
