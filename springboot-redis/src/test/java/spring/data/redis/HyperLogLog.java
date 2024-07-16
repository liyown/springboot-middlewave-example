package spring.data.redis;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author: liuyaowen
 * @poject: spring_middleware.java
 * @create: 2024-07-13 21:24
 * @Description:
 */
@SpringBootTest
public class HyperLogLog {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testHyperLogLogAdd() {
        String key = "hyperLogLog";
        stringRedisTemplate.opsForHyperLogLog().add(key, "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        Long size = stringRedisTemplate.opsForHyperLogLog().size(key);
        System.out.println(size);
    }

    @Test
    public void testHyperLogLogUnion() {
        String key1 = "hyperLogLog1";
        stringRedisTemplate.opsForHyperLogLog().add(key1, "1", "2", "3", "4", "5");
        String key2 = "hyperLogLog2";
        stringRedisTemplate.opsForHyperLogLog().add(key2, "4", "5", "6", "7", "8");
        String key3 = "hyperLogLog3";
        stringRedisTemplate.opsForHyperLogLog().add(key3, "1", "2", "3", "4", "5", "6", "7", "8");
        Long size = stringRedisTemplate.opsForHyperLogLog().union(key3, key1, key2);
        System.out.println(size);
    }

    @Test
    public void testHyperLogLogDelete() {
        String key = "hyperLogLog";
        stringRedisTemplate.opsForHyperLogLog().delete(key);
        Long size = stringRedisTemplate.opsForHyperLogLog().size(key);
        System.out.println(size);
    }

}
