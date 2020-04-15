package com.cleventy.springboilerplate.business.services.userservice;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import com.cleventy.springboilerplate.business.entities.user.User;
import com.cleventy.springboilerplate.business.services.exceptions.DuplicateInstanceException;
import com.cleventy.springboilerplate.business.services.exceptions.InstanceNotFoundException;
import com.cleventy.springboilerplate.business.services.userservice.cos.UserBasicDetailsCO;
import com.cleventy.springboilerplate.business.services.userservice.cos.UserDetailsCO;
import com.cleventy.springboilerplate.util.collection.Chunk;
import com.cleventy.springboilerplate.web.controller.api.form.UserDetailsForm;

public interface UserService {
	
	public List<UserBasicDetailsCO> getUsers();
	public Chunk<UserBasicDetailsCO> getUsers(Integer page);

	public User findUserAndCheckNullAndState(String username) throws InstanceNotFoundException;
	public User findUserAndCheckNullAndState(Long userId) throws InstanceNotFoundException;

	public UserDetailsCO createUser(UserDetailsForm userDetailsForm) throws DuplicateInstanceException;
	public UserDetailsCO getUser(Long userId) throws InstanceNotFoundException; 
	public UserDetailsCO updateUser(Long userId, UserDetailsForm userDetailsForm) throws InstanceNotFoundException, DuplicateInstanceException;
	public UserDetailsCO deleteUser(Long userId) throws InstanceNotFoundException;

	public void sendWelcomeEmail(Long userId) throws InstanceNotFoundException, IOException, MessagingException;

}
