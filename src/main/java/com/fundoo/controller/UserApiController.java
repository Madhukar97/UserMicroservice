package com.fundoo.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fundoo.dto.LoginDto;
import com.fundoo.dto.UserDto;
import com.fundoo.model.User;
import com.fundoo.repository.UserRepo;
import com.fundoo.service.EmailService;
import com.fundoo.service.IService;
import com.fundoo.service.UserService;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/usermicroservice")
public class UserApiController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private IService iService;

	@Autowired
	private EmailService emailService;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers(){
		return new ResponseEntity<>(userRepo.findAll(),HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) throws UnsupportedEncodingException, MessagingException {
		String msg = iService.registerUser(userDto);
		String siteUrl = "http://localhost:8083/usermicroservice/";
		emailService.sendVerificationEmail(userDto, siteUrl);
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody UserDto user) {
		String msg = iService.updateUser(user, id);
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable int id){
		String msg = iService.deleteUser(id);
		return new ResponseEntity<>(msg,HttpStatus.OK) ;
	}

	@PostMapping("/user_login")
	public ResponseEntity<String> validateUserLogin(@RequestBody LoginDto loginDto){
		String msg = iService.validateUserLogin(loginDto);
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}

	@PutMapping("/resetpassword/{password}")
	public ResponseEntity<String> resetPassword (@PathVariable String password, @RequestHeader String token) {
		String msg = iService.resetPassword(password, token);
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}

	@GetMapping("/verify")
	public String verifyAccount(@RequestParam String token) {
		String msg = iService.verifyuser(token);
		return msg;
	}


	@PostMapping("/forgotPass/{email}")
	public ResponseEntity<String> forgotPass(@PathVariable String email) throws UnsupportedEncodingException, MessagingException {
		String siteUrl = "http://localhost:3000/resetpass";
		emailService.sendForgotPassEmail(email, siteUrl);
		String msg = iService.forgotPassword(email);
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}

	@GetMapping("/getuserid/{token}")
	public ResponseEntity<Integer> getUserId(@PathVariable String token){
		Integer id = iService.getUserId(token);
		return new ResponseEntity<Integer>(id, HttpStatus.OK);
	}
	
	@GetMapping("/getuseremail/{id}")
	public ResponseEntity<String> getUserEmail(@PathVariable int id){
		String email = iService.getUserEmail(id);
		return new ResponseEntity<String>(email, HttpStatus.OK);
	}

}
