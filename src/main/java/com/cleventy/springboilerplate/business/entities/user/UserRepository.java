package com.cleventy.springboilerplate.business.entities.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	public User findByUsername(String username);

	public User findByEmail(String email);
	
	public Iterable<User> findAllByOrderByIdAsc();
	
	Page<User> findAllByOrderByIdAsc(Pageable pageable);

	public boolean existsByUsername(String username);
	public boolean existsByEmail(String email);

}

