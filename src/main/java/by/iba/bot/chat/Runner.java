package by.iba.bot.chat;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import redis.clients.jedis.Jedis;

public class Runner {

	public static void main(String[] args) {
		
		/*
		 * 		jedis.set("Users:admin:hash", "$2a$10$33EJiUWsMEHOUrOETcNfBeVx1K4X0QiFgTVcW3XdcC.lp/WuunHbi");
		jedis.set("Users:user:hash", "$2a$10$wzM3EBHyGT16AirCStpCxO3TzOYRFRFTzYyBhtV/CE7B9qm6W0qJC");
		System.out.println(jedis.get("Users:admin:hash"));
		System.out.println(jedis.get("Users:user:hash"));
		 */
		Jedis jedis = new Jedis("localhost");
		jedis.set("welcome", "Hello Guys.");
		jedis.set("Message_ids", "1,2,3,4,5");
		jedis.set("Message:1:name", "First name");
		jedis.set("Message:1:text", "First text");
		jedis.set("Message:2:name", "Second name");
		jedis.set("Message:2:text", "Second text");
		jedis.set("Message:3:name", "Third name");
		jedis.set("Message:3:text", "Third text");
		jedis.set("Message:4:name", "Fourth name");
		jedis.set("Message:4:text", "Fourth text");
		jedis.set("Message:5:name", "Fifth name");
		jedis.set("Message:5:text", "Fifth text");
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println("admin="+encoder.encode("admin"));
		System.out.println("user="+encoder.encode("user"));
		System.out.println(encoder.matches("admin", "$2a$10$33EJiUWsMEHOUrOETcNfBeVx1K4X0QiFgTVcW3XdcC.lp/WuunHbi"));
		
		jedis.set("Users:"+"admin"+":hash", "$2a$10$33EJiUWsMEHOUrOETcNfBeVx1K4X0QiFgTVcW3XdcC.lp/WuunHbi");
		//$2a$10$33EJiUWsMEHOUrOETcNfBeVx1K4X0QiFgTVcW3XdcC.lp/WuunHbi=admin

	}

}
