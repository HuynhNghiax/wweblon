package com.example.doanltweb.dao;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.doanltweb.dao.db.JDBIConnect;
import com.example.doanltweb.dao.model.Log;

import java.sql.*;
import java.time.LocalDateTime;
public class LogDao {
	 private static final Jdbi jdbi = JDBIConnect.get();

	  public static void saveLog(int userId, String level, String ip, String resource, String dataIn, String dataOut) {
		  	LogDao.info("Ghi log: userId={} level={} resource={} ip={} before={} after={}", userId, level, resource, ip, dataIn, dataOut);
	            String sql = "INSERT INTO log (user_id, level, time, ip, resource, data_in, data_out) " +
	                         "VALUES (:userId, :level, :time, :ip, :resource, :dataIn, :dataOut)";

	            jdbi.useHandle(handle -> {
	                handle.createUpdate(sql)
	                    .bind("userId", userId)
	                    .bind("level", level)
	                    .bind("time", Timestamp.valueOf(LocalDateTime.now()))
	                    .bind("ip", ip)
	                    .bind("resource", resource)
	                    .bind("dataIn", dataIn)
	                    .bind("dataOut", dataOut)
	                    .execute();
	            });


	        }

	private static void info(String string, int userId, String level, String resource, String ip, String dataIn,
			String dataOut) {
		// TODO Auto-generated method stub

	}
	  }
