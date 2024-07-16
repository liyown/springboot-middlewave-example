package spring.data.redis;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * @author: liuyaowen
 * @poject: spring_middleware.java
 * @create: 2024-07-13 21:51 * @Description:
 */
@SpringBootTest
public class SETAPI {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void testSet() {
        // SADD key member [member ...]
        /**
         * 1.添加一个或多个指定的member元素到集合的 key中
         */
        Long add = stringRedisTemplate.opsForSet().add("key", "value1", "value2");
        // SMEMBERS key
        /**
         * 2.返回集合中的所有成员
         */
        List members = (List) stringRedisTemplate.opsForSet().members("key");
        // SISMEMBER key member
        /**
         * 3.判断 member 元素是否集合 key 的成员
         */
        Boolean isMember = stringRedisTemplate.opsForSet().isMember("key", "value");
        // SCARD key
        /**
         * 4.获取集合的成员数
         */
        Long size = stringRedisTemplate.opsForSet().size("key");
        // SREM key member [member ...]
        /**
         * 5.移除集合中一个或多个成员
         */
        Long remove = stringRedisTemplate.opsForSet().remove("key", "value");
        // SPOP key
        /**
         * 6.移除并返回集合中的一个随机元素
         */
        Object pop = stringRedisTemplate.opsForSet().pop("key");
        // SRANDMEMBER key [count]
        /**
         * 7.返回集合中一个或多个随机数
         */
        Object randomMember = stringRedisTemplate.opsForSet().randomMember("key");
        // SMOVE source destination member
        /**
         * 8.将 member 元素从 source 集合移动到 destination 集合
         */
        Boolean move = stringRedisTemplate.opsForSet().move("source", "destination", "value");
        // SINTER key [key ...]
        /**
         * 9.返回一个集合的全部成员，该集合是所有给定集合的交集
         */
        Object intersect = stringRedisTemplate.opsForSet().intersect("key1", "key2");
        // SINTERSTORE destination key [key ...]
        /**
         * 10.这个命令与 SINTER 命令类似，但它将结果保存到 destination 集合，而不是简单地返回结果集
         */
        Long intersectAndStore = stringRedisTemplate.opsForSet().intersectAndStore("key1", "key2", "destination");
        // SUNION key [key ...]
        /**
         * 11.返回一个集合的全部成员，该集合是所有给定集合的并集
         */
        Object union = stringRedisTemplate.opsForSet().union("key1", "key2");
        // SUNIONSTORE destination key [key ...]
        Long unionAndStore = stringRedisTemplate.opsForSet().unionAndStore("key1", "key2", "destination");
        // SDIFF key [key ...]
        /**
         * 12.返回一个集合的全部成员，该集合是所有给定集合的差集
         */
        Object diff = stringRedisTemplate.opsForSet().difference("key1", "key2");
        // SDIFFSTORE destination key [key ...]
        Long diffAndStore = stringRedisTemplate.opsForSet().differenceAndStore("key1", "key2", "destination");
    }
}
