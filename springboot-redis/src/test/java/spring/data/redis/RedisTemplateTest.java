package spring.data.redis;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.swing.*;


/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-18 17:35
 * @Description:
 */
@SpringBootTest(classes = com.lyw.Main.class)
public class RedisTemplateTest {

    @Resource
    private RedisTemplate<String, Object> myRedisTemplate;

    @Test
    public void testString() {
        myRedisTemplate.opsForValue().set("name", 123);
        Object name = myRedisTemplate.opsForValue().get("name");
        System.out.println(name);

    }


}
