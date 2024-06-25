package spring.data.redis;

import com.lyw.HMDPMain;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-18 17:35
 * @Description:
 */
@SpringBootTest(classes = HMDPMain.class)
public class RedisTemplateTest {

    @Resource
    private RedisTemplate<String, Object> myRedisTemplate;

    @Test
    public void testString() {
        myRedisTemplate.opsForValue().set("name", 123);
        Integer name = (Integer)myRedisTemplate.opsForValue().get("name");
        System.out.println(name);

    }


}
