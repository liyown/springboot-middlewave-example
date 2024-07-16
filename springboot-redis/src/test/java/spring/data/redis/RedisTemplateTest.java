package spring.data.redis;

import com.lyw.HMDPMain;
import com.lyw.pojo.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;


/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-18 17:35
 * @Description:
 */
@SpringBootTest(classes = HMDPMain.class)
public class RedisTemplateTest {

//    @Resource(re)
//    private RedisTemplate<String, Object> myRedisTemplate;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

//    @Test
//    public void testString() {
//        myRedisTemplate.opsForValue().set("name", 123);
//        Integer name = (Integer)myRedisTemplate.opsForValue().get("name");
//        System.out.println(name);
//
//    }

    @Test
    @SuppressWarnings("unchecked")
    public void testHash() {
        List<MapRecord<String, String,String>> read = stringRedisTemplate.<String,String>opsForStream().read(
                Consumer.from("group1", "c1"),
                StreamReadOptions.empty().count(1).block(Duration.ofSeconds(1)),
                StreamOffset.create("stream1", ReadOffset.lastConsumed()),
                StreamOffset.create("stream2", ReadOffset.lastConsumed()));
        System.out.println(read);
    }

    @Test
    public void testRaw() {
        User user = new User();
        user.setNickName("zhangsan");
        Integer age = 18;
        User[] arr = {user};
        redisTemplate.opsForValue().set("name", 18);
    }

    @Test
    public void testRaw1() throws IOException {
        User user = new User();
        user.setNickName("zhangsan");
        Integer age = 18;
        User[] arr = {user};
        // 将Person对象序列化到文件中
        FileOutputStream fileOut = new FileOutputStream("person.bin");
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        byte[] serialize = jdkSerializationRedisSerializer.serialize(12);
        assert serialize != null;
        fileOut.write(serialize);
        fileOut.close();

    }


}
