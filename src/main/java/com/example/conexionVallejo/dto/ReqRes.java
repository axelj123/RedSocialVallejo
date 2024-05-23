package com.example.conexionVallejo.dto;

import java.util.List;

import com.example.conexionVallejo.modelos.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {

	
	
	private int statusCode;
	private String error;
	private String message;
	private String token;
	private String refreshToker;
	private String expirationTime;
	private String displayName;
	private String emailAddress;
	private String password;
	private String nacimiento;
	private User user;
	private List<User> userList;

}
