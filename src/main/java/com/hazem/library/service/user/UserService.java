package com.hazem.library.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hazem.library.entity.User;

@Service
public interface UserService {

    List<User> index();

    User getUser(Long userId);

    User getByName(String name);

    User saveUser(User theUser);

    User deleteUser(Long id);

    User updateUser(User theUser);
}
