package spring.data.redis;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

/**
 * @author: liuyaowen
 * @poject: spring_middleware.java
 * @create: 2024-07-13 21:27
 * @Description:
 */
@SpringBootTest
public class StringAPI {


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testStringAdd() {
        // SET key value
        stringRedisTemplate.opsForValue().set("key", "value");
        // MSET
        stringRedisTemplate.opsForValue().multiSet(Map.of("key1", "value1", "key2", "value2"));

        // GET key
        String value = stringRedisTemplate.opsForValue().get("key");
        // MGET
        Map<String, String> map = stringRedisTemplate.opsForValue().multiGet(Map.of("key1", "key2"));

        // INCR key
        Long incr = stringRedisTemplate.opsForValue().increment("key");
        // INCRBY key increment
        Long incrBy = stringRedisTemplate.opsForValue().increment("key", 2);
        // INCRBYFLOAT key increment
        Double incrByFloat = stringRedisTemplate.opsForValue().increment("key", 2.1);

        // SETNX key value
        Boolean setnx = stringRedisTemplate.opsForValue().setIfAbsent("key", "value");
        // SETEX key seconds value
        stringRedisTemplate.opsForValue().set("key", "value", 10);

        // 不常用
        // APPEND key value
        Integer append = stringRedisTemplate.opsForValue().append("key", "value");
        // GETRANGE key start end
        String range = stringRedisTemplate.opsForValue().get("key", 0, 1);
        // SETRANGE key offset value
        stringRedisTemplate.opsForValue().set("key", "value", 1);
        // STRLEN key
        Long length = stringRedisTemplate.opsForValue().size("key");
        // GETSET key value 返回旧值设置新值
        String getSet = stringRedisTemplate.opsForValue().getAndSet("key", "value");

    }
}
