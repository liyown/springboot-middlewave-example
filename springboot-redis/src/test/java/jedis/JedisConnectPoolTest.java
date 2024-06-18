package jedis;
import java.time.Duration;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-18 16:57
 * @Description:
 */
@Slf4j
public class JedisConnectPoolTest {

    private JedisPool jedisPool;
    @Before
    public void init() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(5);
        jedisPoolConfig.setMaxTotal(5);
        jedisPoolConfig.setMinIdle(0);
        jedisPoolConfig.setMaxWait(Duration.ofMillis(200));
        jedisPool = new JedisPool(jedisPoolConfig, "106.53.217.225", 6379, 1000, "jhkdjhkjdhsIUTYURTU_r2DFys");
    }


    private Jedis getJedis() {
        return jedisPool.getResource();
    }

    @Test
    public void testString() {
        Jedis jedis = getJedis();
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

        jedis.close();
    }
}
