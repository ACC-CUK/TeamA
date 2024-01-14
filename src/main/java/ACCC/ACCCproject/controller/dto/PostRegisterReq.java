package ACCC.ACCCproject.controller.dto;

import lombok.Getter;

@Getter
public class PostRegisterReq {
	private String email;
	private String password;
	private String userName;
	private String registCode;
	private String gender;
	private String detail;
	private String phoneNumber;
	private Long departmentNumber;
}
