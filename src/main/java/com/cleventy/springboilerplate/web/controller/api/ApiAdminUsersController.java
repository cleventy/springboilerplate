package com.cleventy.springboilerplate.web.controller.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cleventy.springboilerplate.business.services.exceptions.DuplicateInstanceException;
import com.cleventy.springboilerplate.business.services.exceptions.InstanceNotFoundException;
import com.cleventy.springboilerplate.business.services.exceptions.InternalErrorException;
import com.cleventy.springboilerplate.business.services.userservice.UserService;
import com.cleventy.springboilerplate.business.services.userservice.cos.UserDetailsCO;
import com.cleventy.springboilerplate.web.controller.api.form.UserDetailsForm;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(ApiURL.BASE+ApiURL.ADMIN)
@Slf4j
public class ApiAdminUsersController {
	
	@Autowired
	private UserService userService;

	@GetMapping(path=ApiURL.USERS, produces = ApiUtils.ACCEPT_HEADER)
	public ResponseEntity<?> getUsersPaged(@RequestParam(name=ApiURL.PAGE_PARAM) Optional<Integer> page) {
		log.debug("api admin getUsersPaged " + page);
		if (page.isPresent()) {
			return ResponseEntity.ok().body(this.userService.getUsers(page.get()));
		} else {
			return ResponseEntity.ok().body(this.userService.getUsers());
		}
	}
	
	@GetMapping(path=ApiURL.USERS + ApiURL.SLASH + ApiURL.VAR_INI + ApiURL.VAR_ID + ApiURL.VAR_END, produces = ApiUtils.ACCEPT_HEADER)
	public ResponseEntity<UserDetailsCO> getUser(@PathVariable(ApiURL.VAR_ID) Long userId) throws InstanceNotFoundException {
		log.debug("api admin getUsers " + userId);
		return ResponseEntity.ok().body(this.userService.getUser(userId));
	}
	
	@PostMapping(path=ApiURL.USERS, produces = ApiUtils.ACCEPT_HEADER)
	public ResponseEntity<?> createUser(@RequestBody UserDetailsForm userDetailsForm) throws DuplicateInstanceException {
		log.debug("api admin users create user");
		return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.createUser(userDetailsForm));
	}

	@PutMapping(path=ApiURL.USERS + ApiURL.SLASH + ApiURL.VAR_INI + ApiURL.VAR_ID + ApiURL.VAR_END, produces = ApiUtils.ACCEPT_HEADER)
	public ResponseEntity<?> updateUser(@PathVariable(ApiURL.VAR_ID) Long userId, @RequestBody UserDetailsForm userDetailsForm) throws InstanceNotFoundException, DuplicateInstanceException {
		log.debug("api admin users update user");
		return ResponseEntity.ok().body(this.userService.updateUser(userId, userDetailsForm));
	}

	@DeleteMapping(path=ApiURL.USERS + ApiURL.SLASH + ApiURL.VAR_INI + ApiURL.VAR_ID + ApiURL.VAR_END, produces = ApiUtils.ACCEPT_HEADER)
	public ResponseEntity<?> deleteUser(@PathVariable(ApiURL.VAR_ID) Long userId) throws InstanceNotFoundException {
		log.debug("api admin users delete user");
		return ResponseEntity.ok().body(this.userService.deleteUser(userId));
	}

	@PostMapping(path=ApiURL.USERS + ApiURL.SLASH + ApiURL.VAR_INI + ApiURL.VAR_ID + ApiURL.VAR_END + ApiURL.SEND_WELCOME_EMAIL, produces = ApiUtils.ACCEPT_HEADER)
	public ResponseEntity<Map<String, Object>> sendWelcomeEmail(@PathVariable(ApiURL.VAR_ID) Long userId) throws InstanceNotFoundException, InternalErrorException {
		try {
			log.debug("api admin users send welcome email");
			this.userService.sendWelcomeEmail(userId);
			Map<String, Object> result = new HashMap<String,Object>();
			result.put("userId", userId);
			result.put("sent", Boolean.TRUE);
			return ResponseEntity.ok().body(result);
		} catch (IOException | MessagingException e) {
			throw new InternalErrorException(e);
		}
	}

	
}
