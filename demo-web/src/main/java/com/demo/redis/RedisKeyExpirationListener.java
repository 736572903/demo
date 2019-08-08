package com.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * 监听所有reids的过期事件,仅适合单机！！！！！！！！！！！！！
 */
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    
    @Autowired
    private RedisTemplate redisTemplate;
 
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }
 
 
    @Override
    public void onMessage(Message message, byte[] pattern) {
 
        String expiredKey = message.toString();
 
        System.out.println("过期的key 是: "+expiredKey);
    }
}
