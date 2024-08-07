package com.hazem.library.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hazem.library.DAO.UserRepository;
import com.hazem.library.entity.User;
import com.hazem.library.rest.exceptionHandler.NotFoundException;

@Component
public class UserServiceImpl implements UserService{

    private UserRepository UserRepository;

    @Autowired
	public UserServiceImpl(UserRepository theUserRepository) {
		this.UserRepository = theUserRepository;
	}

    @Override
    public List<User> index() {
        return UserRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        Optional<User> result = UserRepository.findById(id);

		User theUser = null;

		if (result.isPresent()) {
			theUser = result.get();
			return theUser;
		} else {
			throw new NotFoundException("User not found id: " + id);
		}
    }

    @Override
    public User getByName(String name) {
        Optional<User> result = UserRepository.findByUserName(name);

		User theUser = null;

		if (result.isPresent()) {
			theUser = result.get();
			return theUser;
		} else {
			throw new NotFoundException("User not found user name: " + name);
		}
    }

    @Override
    public User saveUser(User theUser) {
        return UserRepository.save(theUser);
    }

    @Override
    public User deleteUser(Long id) {
        UserRepository.deleteById(id);
        return null;
    }

    @Override
    public User updateUser(User theUser) {
        return UserRepository.save(theUser);
    }
    
}
