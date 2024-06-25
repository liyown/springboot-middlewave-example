package spring.data.redis;

import com.lyw.HMDPMain;
import com.lyw.pojo.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.List;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-22 17:49
 * @Description:
 */
@SpringBootTest(classes = HMDPMain.class)
public class RedisStreamsReadTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testString() {

        List<MapRecord<String, String, String>> mystream = stringRedisTemplate.<String, String>opsForStream().read(
                StreamReadOptions.empty().block(Duration.ofMinutes(1)).count(1),
                StreamOffset.create("s1", ReadOffset.from("1")),
                StreamOffset.create("s2", ReadOffset.from("0"))
        );


    }

    @Test
    public void test12() {
        List<MapRecord<String, String,String>> read = stringRedisTemplate.<String,String>opsForStream().read(
                Consumer.from("seckillGroup", "c1"),
                StreamReadOptions.empty().count(1).block(Duration.ofSeconds(1)),
                StreamOffset.create("stream.order", ReadOffset.from("0")));

    }
}
