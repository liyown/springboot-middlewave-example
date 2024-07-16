package spring.data.redis;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: liuyaowen
 * @poject: spring_middleware.java
 * @create: 2024-07-13 22:51
 * @Description:
 */
@SpringBootTest
public class PubSubAPI {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void testPubSub() {
        // PUBLISH channel message
        /**
         * 1.将信息 message 发送到指定的频道 channel
         */
        Long publish = redisTemplate.convertAndSend("channel", "message");
    }

}
