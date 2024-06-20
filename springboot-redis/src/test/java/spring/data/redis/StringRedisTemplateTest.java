package spring.data.redis;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-18 19:51
 * @Description:
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyw.HMDPMain;
import com.lyw.pojo.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

@SpringBootTest(classes = HMDPMain.class)
public class StringRedisTemplateTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testString() throws JsonProcessingException {

//        User user = new User();
//        String userJson = mapper.writeValueAsString(user);
//
//        stringRedisTemplate.opsForValue().set("user", userJson);
//
//        String userJson1 = stringRedisTemplate.opsForValue().get("user");
//
//        User user1 = mapper.readValue(userJson1, User.class);
    }

    @Test
    public void testHash() throws JsonProcessingException {

//        stringRedisTemplate.opsForHash().put("user:hash", "name", "lyw");
//        stringRedisTemplate.opsForHash().put("user:hash", "age", "18");
//        Map<Object, Object> user = stringRedisTemplate.opsForHash().entries("user:hash");
//        System.out.println(user);
    }
}
