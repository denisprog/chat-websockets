package by.iba.bot.chat.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import by.iba.bot.chat.Constants;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class UserDetailsServiceRedisImpl implements UserDetailsService {

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		UserDetails userDetails = null;
		String password = redisTemplate.opsForValue().get("Users:"+userName+":hash");
		if(password!=null) {
			GrantedAuthority authority = new SimpleGrantedAuthority(Constants.Security.ROLE_ADMIN);
			userDetails = (UserDetails) new User(userName, password, Arrays.asList(authority));
		}else throw new UsernameNotFoundException("Was not found password for userName="+userName);
		return userDetails;
	}
}