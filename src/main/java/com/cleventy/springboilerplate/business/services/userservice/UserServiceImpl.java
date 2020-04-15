package com.cleventy.springboilerplate.business.services.userservice;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.cleventy.springboilerplate.business.entities.user.User;
import com.cleventy.springboilerplate.business.entities.user.UserConstants;
import com.cleventy.springboilerplate.business.entities.user.UserRepository;
import com.cleventy.springboilerplate.business.services.exceptions.BadRequestException;
import com.cleventy.springboilerplate.business.services.exceptions.DuplicateInstanceException;
import com.cleventy.springboilerplate.business.services.exceptions.InstanceNotFoundException;
import com.cleventy.springboilerplate.business.services.userservice.cos.UserBasicDetailsCO;
import com.cleventy.springboilerplate.business.services.userservice.cos.UserDetailsCO;
import com.cleventy.springboilerplate.util.collection.Chunk;
import com.cleventy.springboilerplate.util.collection.IterableUtil;
import com.cleventy.springboilerplate.util.date.DateUtil;
import com.cleventy.springboilerplate.util.email.EmailSender;
import com.cleventy.springboilerplate.web.controller.api.form.UserDetailsForm;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserRepository userRepository;
    
	@Autowired
    private PasswordEncoder passwordEncoder;
    
	@Autowired
    private EmailSender emailSender;
    
	@Override
	@Transactional(readOnly = true)
	public List<UserBasicDetailsCO> getUsers() {
//    	List<UserBasicDetailsCO> userBasicDetailsCOs = new ArrayList<UserBasicDetailsCO>();
//		for (User user : this.userRepository.findAll()) {
//			userBasicDetailsCOs.add(new UserBasicDetailsCO(user.getId(), user.getLogin(), user.getEmail()));
//		}
//		return userBasicDetailsCOs;
		
//		return IteratorUtils.toList(this.userRepository.findAll().iterator()).stream()
//				.map(user -> new UserBasicDetailsCO(user.getId(), user.getUsername(), user.getEmail()))
//				.collect(Collectors.toList());
		
		return IterableUtil.toStream(this.userRepository.findAllByOrderByIdAsc())
				.map(user -> new UserBasicDetailsCO(user.getId(), user.getUsername(), user.getEmail()))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Chunk<UserBasicDetailsCO> getUsers(Integer page) {
		int pageRepository = IterableUtil.getPageToRequestToRepositoryAndCheck(page);
		return IterableUtil.toChunk(
				IterableUtil.toStream(this.userRepository.findAllByOrderByIdAsc(PageRequest.of(pageRepository, IterableUtil.CHUNK_SIZE)))
					.map(user -> new UserBasicDetailsCO(user.getId(), user.getUsername(), user.getEmail()))
					.collect(Collectors.toList())
				,
				this.userRepository.count(), // total 
				page, 
				IterableUtil.CHUNK_SIZE
			);
	}


	@Override
	@Transactional(readOnly = true)
	public User findUserAndCheckNullAndState(String username) throws InstanceNotFoundException {
		try {
			return findUserAndCheckNullAndState(this.userRepository.findByUsername(username));
		} catch (InstanceNotFoundException e) {
			throw new InstanceNotFoundException(username, User.class.getName());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public User findUserAndCheckNullAndState(Long userId) throws InstanceNotFoundException {
		try {
			return findUserAndCheckNullAndState(this.findUserAndCheckNull(userId));
		} catch (InstanceNotFoundException e) {
			throw new InstanceNotFoundException(userId, User.class.getName());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetailsCO getUser(Long userId) throws InstanceNotFoundException {
		User user = this.findUserAndCheckNull(userId);
		return toUserDetailsCO(user);
	}
	
	@Override
	@Transactional
	public UserDetailsCO createUser(UserDetailsForm userDetailsForm) throws DuplicateInstanceException {
		checkUserDetailsForm(userDetailsForm);
		if (this.userRepository.existsByUsername(userDetailsForm.getUsername())) {
			throw new DuplicateInstanceException(userDetailsForm.getUsername(), User.class.getName());
		}
		if (this.userRepository.existsByEmail(userDetailsForm.getEmail())) {
			throw new DuplicateInstanceException(userDetailsForm.getEmail(), User.class.getName());
		}
		User user = User
				.builder()
				.username(userDetailsForm.getUsername())
				.password(this.passwordEncoder.encode(userDetailsForm.getPassword()))
				.email(userDetailsForm.getEmail())
				.name(userDetailsForm.getName())
				.role(userDetailsForm.getRole())
				.state(userDetailsForm.getState())
				.registerDate(DateUtil.getNow())
				.build();
		this.userRepository.save(user);
		return toUserDetailsCO(user);
	}

	@Override
	@Transactional
	public UserDetailsCO updateUser(Long userId, UserDetailsForm userDetailsForm) throws InstanceNotFoundException, DuplicateInstanceException {
		checkUserDetailsForm(userDetailsForm);
		User user = this.findUserAndCheckNull(userId);
		try {
			User userByUsername = this.findUserAndCheckNull(userDetailsForm.getUsername());
			if (!userByUsername.getId().equals(userId)) {
				throw new DuplicateInstanceException(userDetailsForm.getUsername(), User.class.getName());
			}
		} catch (InstanceNotFoundException e) {
			// it's ok, no other user exists by same username
		}
		try {
			User userByEmail = this.findUserAndCheckNullByEmail(userDetailsForm.getEmail());
			if (!userByEmail.getId().equals(userId)) {
				throw new DuplicateInstanceException(userDetailsForm.getEmail(), User.class.getName());
			}
		} catch (InstanceNotFoundException e) {
			// it's ok, no other user exists by same email
		}
		user.setUsername(userDetailsForm.getUsername());
		user.setPassword(this.passwordEncoder.encode(userDetailsForm.getPassword()));
		user.setEmail(userDetailsForm.getEmail());
		user.setName(userDetailsForm.getName());
		user.setRole(userDetailsForm.getRole());
		user.setState(userDetailsForm.getState());
		this.userRepository.save(user);
		return toUserDetailsCO(user);
	}

	@Override
	@Transactional
	public UserDetailsCO deleteUser(Long userId) throws InstanceNotFoundException {
		User user = this.findUserAndCheckNull(userId);
		user.setState(UserConstants.STATE_INACTIVE);
		return toUserDetailsCO(user);
	}


	@Override
	@Transactional(readOnly = true)
	public void sendWelcomeEmail(Long userId) throws InstanceNotFoundException, IOException, MessagingException {
		
		User user = this.findUserAndCheckNullAndState(userId);
		this.emailSender.sendWelcomeEmail(user.getEmail(), user.getUsername(), user.getRegisterDate(), Locale.getDefault());
		
	}

	//////////////////////////////////////////////////////////////////
	// AUX
	//////////////////////////////////////////////////////////////////
	
	// GET AND VALIDATION
	private User findUserAndCheckNull(String username) throws InstanceNotFoundException {
		try {
			return findUserAndCheckNull(this.userRepository.findByUsername(username));
		} catch (InstanceNotFoundException e) {
			throw new InstanceNotFoundException(username, User.class.getName());
		}
	}
	private User findUserAndCheckNullByEmail(String email) throws InstanceNotFoundException {
		try {
			return findUserAndCheckNull(this.userRepository.findByEmail(email));
		} catch (InstanceNotFoundException e) {
			throw new InstanceNotFoundException(email, User.class.getName());
		}
	}
	private User findUserAndCheckNull(Long userId) throws InstanceNotFoundException {
		Optional<User> optUser = this.userRepository.findById(userId);
		if (!optUser.isPresent()) {
			throw new InstanceNotFoundException(userId, User.class.getName());
		}
		return optUser.get();
	}
	private static User findUserAndCheckNull(User user) throws InstanceNotFoundException {
		if (user == null) {
			throw new InstanceNotFoundException(user, User.class.getName());
		}
		return user;
	}
	private static User findUserAndCheckNullAndState(User user) throws InstanceNotFoundException {
		if (user == null || !UserConstants.STATE_ACTIVE.equals(user.getState())) {
			throw new InstanceNotFoundException(user, User.class.getName());
		}
		return user;
	}
	
	// FORM VALIDATIONS
	private static void checkUserDetailsForm(UserDetailsForm userDetailsForm) throws BadRequestException {
		if (StringUtils.isEmpty(userDetailsForm.getUsername())) {
			throw new BadRequestException("username", "Can't be empty");
		}
		if (StringUtils.isEmpty(userDetailsForm.getPassword())) {
			throw new BadRequestException("password", "Can't be empty");
		}
		if (StringUtils.isEmpty(userDetailsForm.getEmail())) {
			throw new BadRequestException("email", "Can't be empty");
		}
		if (StringUtils.isEmpty(userDetailsForm.getName())) {
			throw new BadRequestException("name", "Can't be empty");
		}
		if (!CollectionUtils.contains(UserConstants.ROLES.iterator(), userDetailsForm.getRole())) {
			throw new BadRequestException("role", "Wrong value");
		}
		if (!CollectionUtils.contains(UserConstants.STATES.iterator(), userDetailsForm.getState())) {
			throw new BadRequestException("state", "Wrong value");
		}
	}

	// TRANSFORMATIONS
	private static UserDetailsCO toUserDetailsCO(User user) {
		return new UserDetailsCO(user.getId(), user.getUsername(), user.getEmail(), user.getName(), user.getRole(), user.getState());
	}
	


}
