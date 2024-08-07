package com.hazem.library.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hazem.library.entity.User;
import com.hazem.library.rest.exceptionHandler.NotFoundException;
import com.hazem.library.service.user.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserService userService;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.getByName(username);
    if (user == null) throw new NotFoundException("No such user exists");
    return UserDetailsImpl.build(user);
  }

}
