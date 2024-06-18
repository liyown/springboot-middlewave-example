package jedis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import redis.clients.jedis.Jedis;

import java.util.Optional;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-18 16:07
 * @Description:
 */
@Slf4j
public class JedisTest {

    private Jedis jedis;

    @Before
    public void init() {
        jedis = new Jedis("106.53.217.225", 6379);
        jedis.auth("jhkdjhkjdhsIUTYURTU_r2DFys");
        jedis.select(0);

    }

    @AfterEach
    public void close() {
        Optional.ofNullable(jedis).ifPresent(Jedis::close);
    }


    @Test
    public void testString() {
        // 设置
        jedis.set("name", "liuyaowen");
        // 获取
        String name = jedis.get("name");
        log.info("name: {}", name);
        // 删除
        jedis.del("name");
        // 获取
        String name2 = jedis.get("name");
        log.info("name2: {}", name2);
    }

    @Test
    public void testHash() {
        // 设置
        jedis.hset("user", "name", "liuyaowen");
        // 获取
        String name = jedis.hget("user", "name");
        log.info("name: {}", name);
        // 删除
        jedis.hdel("user", "name");
        // 获取
        String name2 = jedis.hget("user", "name");
        log.info("name2: {}", name2);
    }


}
