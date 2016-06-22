package com.yizhenmoney.damocles.zookeeper.auth;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.yizhenmoney.damocles.zookeeper.helper.LoginUserHelper;

@Component
public class UcUserDetailsService implements UserDetailsService {

	@Autowired
	private ShaPasswordEncoder encoder;
	@Autowired
	private LoginUserHelper loginUserHelper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		String password= loginUserHelper.getPassWordByUserName(username);
		return new User(username, encoder.encodePassword(password, "abcdefg"), new ArrayList<GrantedAuthority>());
	}

}
