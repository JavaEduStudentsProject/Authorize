package com.bezkoder.spring.security.mongodb.security.services;

import com.bezkoder.spring.security.mongodb.kafka.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.security.mongodb.models.User;
import com.bezkoder.spring.security.mongodb.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Autowired
  MessageListener ml;


  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = ml.listenerGetUserResponse(username);
    return UserDetailsImpl.build(user);
  }



}
