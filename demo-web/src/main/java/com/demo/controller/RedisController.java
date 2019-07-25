package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 */
@Controller
@RequestMapping("")
public class RedisController {
	
	@Autowired
    private RedisTemplate<String, String> redisTemplate;
	
	@GetMapping("/useRedis")
	@ResponseBody
	public String useRedis(){
		
		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		
		operations.set("redis", "good");
		
		return operations.get("redis");
		
	}
	
}
