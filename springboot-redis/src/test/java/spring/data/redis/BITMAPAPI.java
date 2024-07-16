package spring.data.redis;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * @author: liuyaowen
 * @poject: spring_middleware.java
 * @create: 2024-07-13 22:25
 * @Description:
 */
@SpringBootTest
public class BITMAPAPI {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testBitmap() {
        // SETBIT key offset value
        /**
         * 1.对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit), 最大长度为 512MB，也就是 2^32 bit
         */
        Boolean setbit = stringRedisTemplate.opsForValue().setBit("key", 1, true);

        // GETBIT key offset
        /**
         * 2.对 key 所储存的字符串值，获取指定偏移量上的位(bit)
         */
        Boolean getbit = stringRedisTemplate.opsForValue().getBit("key", 1);

        // BITFIELD key [GET type offset] [SET type offset value] [INCRBY type offset increment] [OVERFLOW WRAP|SAT|FAIL]
        /**
         * 3.对 key 所储存的字符串值，获取或设置指定偏移量上的位(bit),增加或减少指定偏移量上的位(bit)
         */
        // 获取 1bit 下标从1-7的所有数据
        BitFieldSubCommands command = BitFieldSubCommands.create()
                                                             .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(1)
                                                             .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(2)
                                                             .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(3)
                                                             .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(4)
                                                             .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(5)
                                                             .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(6)
                                                             .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(7);
        List<Long> longs = stringRedisTemplate.opsForValue().bitField("key", command);

    }

}
