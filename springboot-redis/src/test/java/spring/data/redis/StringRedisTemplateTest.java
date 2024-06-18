package spring.data.redis;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-18 19:51
 * @Description:
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyw.pojo.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.xmlunit.util.Mapper;

@SpringBootTest(classes = com.lyw.Main.class)
public class StringRedisTemplateTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testString() throws JsonProcessingException {

        User user = new User(1L, "lyw", 18);
        String userJson = mapper.writeValueAsString(user);

        stringRedisTemplate.opsForValue().set("user", userJson);

        String userJson1 = stringRedisTemplate.opsForValue().get("user");

        User user1 = mapper.readValue(userJson1, User.class);
    }
}
