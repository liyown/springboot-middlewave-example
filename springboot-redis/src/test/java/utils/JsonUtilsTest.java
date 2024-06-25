package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyw.pojo.Shop;
import com.lyw.utils.RedisData;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-19 23:59
 * @Description:
 */
public class JsonUtilsTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testToJson() throws JsonProcessingException {
        byte[] s = objectMapper.writeValueAsBytes(123);

//        objectMapper.read

    }


    @Test
    public void testToObject() throws JsonProcessingException {
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        String target = "{\"id\":1,\"name\":\"105茶餐厅\",\"typeId\":1,\"images\":\"https://qcloud.dpfile.com/pc/jiclIsCKmOI2arxKN1Uf0Hx3PucIJH8q0QSz-Z8llzcN56-_QiKuOvyio1OOxsRtFoXqu0G3iT2T27qat3WhLVEuLYk00OmSS1IdNpm8K8sG4JN9RIm2mTKcbLtc2o2vfCF2ubeXzk49OsGrXt_KYDCngOyCwZK-s3fqawWswzk.jpg,https://qcloud.dpfile.com/pc/IOf6VX3qaBgFXFVgp75w-KKJmWZjFc8GXDU8g9bQC6YGCpAmG00QbfT4vCCBj7njuzFvxlbkWx5uwqY2qcjixFEuLYk00OmSS1IdNpm8K8sG4JN9RIm2mTKcbLtc2o2vmIU_8ZGOT1OjpJmLxG6urQ.jpg\",\"area\":\"大关\",\"address\":\"金华路锦昌文华苑29号\",\"x\":120.149192,\"y\":30.316078,\"avgPrice\":80,\"sold\":4215,\"comments\":3035,\"score\":37,\"openHours\":\"10:00-22:00\",\"createTime\":1640167839000,\"updateTime\":1642066339000}";

        RedisData<Shop> redisData = new RedisData<>();
        redisData.setData(objectMapper.readValue(target, Shop.class));
        redisData.setExpireTime(Instant.now());
        String json = objectMapper.writeValueAsString(redisData);

        RedisData<Shop> redisData1 = objectMapper.readValue(json, new com.fasterxml.jackson.core.type.TypeReference<RedisData<Shop>>() {
        });
        System.out.println(redisData1);

    }

    @Test
    public void test11() {
        String s1 = new String("ab") + new String("c");
        String s2 = "abc";
        String intern = s1.intern();
        System.out.println(intern == s2); // 输出false
        ReentrantLock reentrantLock = new ReentrantLock();
    }
}
