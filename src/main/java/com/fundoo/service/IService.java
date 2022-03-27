package com.fundoo.service;

import com.fundoo.dto.LoginDto;
import com.fundoo.dto.UserDto;

public interface IService {

	public String registerUser(UserDto userdto);

	public String updateUser(UserDto userDto, int id);

	public String deleteUser(int id);

	public String validateUserLogin(LoginDto loginDto);

	public String resetPassword(String password, String token);

	public String verifyuser(String token);

	public String forgotPassword(String email);

	public Integer getUserId(String token);

	public String getUserEmail(int token);
}
