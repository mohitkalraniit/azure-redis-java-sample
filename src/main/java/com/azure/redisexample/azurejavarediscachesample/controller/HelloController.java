package com.azure.redisexample.azurejavarediscachesample.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@RestController
public class HelloController {

   @Autowired
   private StringRedisTemplate template;

   @RequestMapping("/")
   // Define the Hello World controller.
   public String hello() {
   
      ValueOperations<String, String> ops = this.template.opsForValue();

      // Add a Hello World string to your cache.
      String key = "greeting";
      if (!this.template.hasKey(key)) {
          ops.set(key, "Hello World!");
      }

      // Return the string from your cache.
      return ops.get(key);
   }
   
   @RequestMapping("/readkey/{key}")
   // Define the Hello World controller.
   public String readkey(@PathVariable(value="key") String key) {
   
      ValueOperations<String, String> ops = this.template.opsForValue();
      // Return the string from your cache.
      return ops.get(key);
   }
   
   @RequestMapping("/setkey/{key}")
   // Define the Hello World controller.
   public String setkey(@PathVariable(value="key") String key,@RequestParam(value="val") String value) {
   
      ValueOperations<String, String> ops = this.template.opsForValue();
      
      if (!this.template.hasKey(key)) {
          ops.set(key,value);
      }
      // Return the string from your cache.
      return ops.get(key);
   }
   
   @RequestMapping("/expirekey/{key}")
   // Define the Hello World controller.
   public String expirekey(@PathVariable(value="key") String key,@RequestParam(value="val") String value) {
	   ValueOperations<String, String> ops = this.template.opsForValue();

	   if (this.template.hasKey(key)) {
    	  this.template.expire(key,Integer.parseInt(value) ,TimeUnit.SECONDS);
      }
      // Return the string from your cache.
      return ops.get(key);
   }
}