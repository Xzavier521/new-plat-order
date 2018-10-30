package com.nsw.wx.order.redis;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.core.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 加锁
 *
 * @author 张维维
 * date: 2018/10/30/030 18:57
 */
@Component
@Slf4j
public class RedisLock {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key
     * @param value 当前时间≠超时时间
     * @return
     */
    public boolean lock(String key,String value) {
        if (redisTemplate.opsForValue().setIfAbsent(key, value)){
            return true;
        }

        String currentValue = redisTemplate.opsForValue().get(key);
        //如果锁超时
        if (!StringUtils.isEmpty(currentValue)&&Long.parseLong(currentValue)
                <System.currentTimeMillis()){
            //获取上一个锁的时间
            String oldvalue = redisTemplate.opsForValue().getAndSet(key, value);
            if(!StringUtils.isEmpty(oldvalue)&& oldvalue.equals(currentValue)){
                return true;
            }
        }
        return false;

    }

    public  void  unlock(String key,String value){
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            log.error("redis分布式解锁异常,{}",ex);
        }
    }
}
