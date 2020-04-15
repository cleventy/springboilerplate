package com.cleventy.springboilerplate.unit;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cleventy.springboilerplate.business.entities.user.User;
import com.cleventy.springboilerplate.business.entities.user.UserRepository;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void getAdminUsersNotEmptyAndOrdered() {
		Iterable<User> userIterable = this.userRepository.findAllByOrderByIdAsc();
		Iterator<User> userIterator = userIterable.iterator();
		assertTrue(userIterator.hasNext()); // at least one admin user
		User previousUser = null;
		User currentUser = null;
		while(userIterator.hasNext()) {
			previousUser = currentUser;
			currentUser = userIterator.next();
			if (previousUser!=null) {
				assertThat(currentUser.getId(), greaterThan(previousUser.getId())); // admin users ordered
			}
		}
	}

}
