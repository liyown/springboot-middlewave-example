package spring.data.redis;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author: liuyaowen
 * @poject: spring_middleware.java
 * @create: 2024-07-13 21:36
 * @Description:
 */
@SpringBootTest
public class HSETAPI {


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testHSET() {
        // HSET key field value
        stringRedisTemplate.opsForHash().put("key", "field", "value");
        // HMSET key field value [field value ...]
        stringRedisTemplate.opsForHash().putAll("key", Map.of("field1", "value1", "field2", "value2"));

        // HGET key field 返回的是Object
        Map object = (Map) stringRedisTemplate.opsForHash().get("key", "field");
        // HMGET key field [field ...]
        Object value1 = stringRedisTemplate.opsForHash().multiGet("key", List.of("field1", "field2"));

         // HINCRBY key field increment
         Long incr = stringRedisTemplate.opsForHash().increment("key", "field", 1);
        // HINCRBYFLOAT key field increment
        Double incrByFloat = stringRedisTemplate.opsForHash().increment("key", "field", 1.1);

        // HSETNX key field value
        Boolean setnx = stringRedisTemplate.opsForHash().putIfAbsent("key", "field", "value");

        // HDEL key field [field ...]
        Long delete = stringRedisTemplate.opsForHash().delete("key", new Object[]{"field1", "field2"});

        // HEXISTS key field
        Boolean exists = stringRedisTemplate.opsForHash().hasKey("key", "field");

        // HGETALL key
        Object entries = stringRedisTemplate.opsForHash().entries("key");

        // HKEYS key
        Object keys = stringRedisTemplate.opsForHash().keys("key");

        // HVALS key
        Object values = stringRedisTemplate.opsForHash().values("key");

        // HLEN key
        Long length = stringRedisTemplate.opsForHash().size("key");

        // HSTRLEN key field
        Long length1 = stringRedisTemplate.opsForHash().lengthOfValue("key", "field");

        // HSET key field value [field value ...]
        Boolean set = stringRedisTemplate.opsForHash().putIfAbsent("key", "field", "value");

        // HSCAN key cursor [MATCH pattern] [COUNT count]
        Object scan = stringRedisTemplate.opsForHash().scan("key", null);
    }
}
