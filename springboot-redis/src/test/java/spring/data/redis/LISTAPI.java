package spring.data.redis;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * @author: liuyaowen
 * @poject: spring_middleware.java
 * @create: 2024-07-13 21:43
 * @Description:
 */
@SpringBootTest
public class LISTAPI {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testList() {
        // LPUSH key value [value ...]
        stringRedisTemplate.opsForList().leftPush("key", "value1", "value2");
        // RPUSH key value [value ...]
        stringRedisTemplate.opsForList().rightPush("key", "value1", "value2");

        // LPOP key
        String leftPop = stringRedisTemplate.opsForList().leftPop("key");
        // RPOP key
        String rightPop = stringRedisTemplate.opsForList().rightPop("key");

        // LINDEX key index 返回列表中指定位置的元素
        String index = stringRedisTemplate.opsForList().index("key", 0);

        // LINSERT key BEFORE|AFTER pivot value 在列表的元素前或者后插入元素
        Long insert = stringRedisTemplate.opsForList().leftPush("key", "value1", "value2");

        // LLEN key 返回列表的长度
        Long size = stringRedisTemplate.opsForList().size("key");

        // LREM key count value 移除列表元素，参数说明：count>0 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。count<0 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值。count=0 移除表中所有与 VALUE 相等的值。
        Long remove = stringRedisTemplate.opsForList().remove("key", 1, "value");

        // LSET key index value 修改列表元素的值，参数说明：index 从 0 开始，0 表示第一个元素，1 表示第二个元素，以此类推。index 可以是负数，表示从列表尾开始的位置，-1 表示最后一个元素，-2 表示倒数第二个元素，以此类推。
        stringRedisTemplate.opsForList().set("key", 0, "value");

        // LTRIM key start stop 对列表进行修剪，只保留指定区间内的元素，参数说明：start 和 stop 都是由0开始计数的，0 是列表的第一个元素（表头），1 是第二个元素，-1 是列表的最后一个元素（表尾），-2 是倒数第二个元素，以此类推。start 和 stop 也可以是负数，-1 表示列表的最后一个元素（表尾），-2 表示倒数第二个元素，以此类推。
        stringRedisTemplate.opsForList().trim("key", 0, 1);

        // BLPOP key [key ...] timeout 阻塞式弹出元素，参数说明：timeout 为 0 表示一直阻塞，直到有元素可以弹出。timeout 大于 0 表示阻塞 timeout 秒，超时返回 null。timeout 小于 0 表示不阻塞，立即返回 null。
        List blockLeftPop = stringRedisTemplate.opsForList().leftPop("key", 10);
        // BRPOP key [key ...] timeout 阻塞式弹出元素，参数说明：timeout 为 0 表示一直阻塞，直到有元素可以弹出。timeout 大于 0 表示阻塞 timeout 秒，超时返回 null。timeout 小于 0 表示不阻塞，立即返回 null。
        List blockRightPop = stringRedisTemplate.opsForList().rightPop("key", 10);

        // BRPOPLPUSH source destination timeout 阻塞式弹出元素并将元素放入另一个列表，参数说明：timeout 为 0 表示一直阻塞，直到有元素可以弹出。timeout 大于 0 表示阻塞 timeout 秒，超时返回 null。timeout 小于 0 表示不阻塞，立即返回 null。
        String blockRightPopLeftPush = stringRedisTemplate.opsForList().rightPopAndLeftPush("source", "destination", 10);

        // RPOPLPUSH source destination 将列表 source 的最后一个元素弹出并放入列表 destination，返回弹出的元素
        String rightPopLeftPush = stringRedisTemplate.opsForList().rightPopAndLeftPush("source", "destination");
    }
}
