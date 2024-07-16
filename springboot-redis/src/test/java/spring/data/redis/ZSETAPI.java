package spring.data.redis;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * @author: liuyaowen
 * @poject: spring_middleware.java
 * @create: 2024-07-13 21:56
 * @Description:
 */
@SpringBootTest
public class ZSETAPI {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testZSet() {
        // ZADD key score member [score member ...]
        /**
         * 1.添加一个或多个成员，或者更新已存在成员的分数
         */
        Boolean add = stringRedisTemplate.opsForZSet().add("key", "value1", 1);
        // ZRANGE key start stop [WITHSCORES]
        /**
         * 2.排序后返回有序集合中指定区间内的成员，通过索引，分数从低到高
         */
        Object range = stringRedisTemplate.opsForZSet().range("key", 0, -1);
        // ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
        /**
         * 3.通过分数返回有序集合指定区间内的成员
         */
        Object rangeByScore = stringRedisTemplate.opsForZSet().rangeByScore("key", 1, 2);
        // ZREM key member [member ...]
        /**
         * 3.移除有序集合中的一个或多个成员
         */
        Long remove = stringRedisTemplate.opsForZSet().remove("key", "value");
        /**
         * 11.移除有序集合中给定的排名区间的所有成员
         */
        Long removeRange = stringRedisTemplate.opsForZSet().removeRange("key", 0, -1);
        // ZINCRBY key increment member
        /**
         * 4.有序集合中对指定成员的分数加上增量 increment
         */
        Double incr = stringRedisTemplate.opsForZSet().incrementScore("key", "value", 1);
        // ZRANK key member
        /**
         * 5.返回有序集合中指定成员的排名，分数从低到高
         */
        Long rank = stringRedisTemplate.opsForZSet().rank("key", "value");
        // ZREVRANK key member
        /**
         * 6.返回有序集中指定成员的排名，分数从高到低
         */
        Long reverseRank = stringRedisTemplate.opsForZSet().reverseRank("key", "value");
        // ZREVRANGE key start stop [WITHSCORES]
        /**
         * 7.返回有序集中指定区间内的成员，通过索引，分数从高到底
         */
        Object reverseRange = stringRedisTemplate.opsForZSet().reverseRange("key", 0, -1);
        // ZCARD key
        /**
         * 8.获取有序集合的成员数
         */
        Long size = stringRedisTemplate.opsForZSet().size("key");
        // ZSCORE key member
        /**
         * 9.返回有序集中，成员的分数值
         */
        Double score = stringRedisTemplate.opsForZSet().score("key", "value");
        // ZCOUNT key min max
        /**
         * 10.计算在有序集合中指定区间分数的成员数
         */
        Long count = stringRedisTemplate.opsForZSet().count("key", 1, 2);

        // ZINTERSTORE destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX]
        /**
         * 12.计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 key 中
         */
        Long intersectAndStore = stringRedisTemplate.opsForZSet().intersectAndStore("key1", "key2", "destination");
        // ZUNIONSTORE destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX]
        /**
         * 13.计算给定的一个或多个有序集的并集，并存储在新的 key 中
         */
        Long unionAndStore = stringRedisTemplate.opsForZSet().unionAndStore("key1", "key2", "destination");
        // ZDIFFSTORE destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX]
        /**
         * 14.计算给定的一个或多个有序集的差集，并存储在新的 key 中
         */
        Long differenceAndStore = stringRedisTemplate.opsForZSet().differenceAndStore("key1", List.of("key2", "key3"), "destination");

    }

}
