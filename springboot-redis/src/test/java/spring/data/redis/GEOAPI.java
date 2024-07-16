package spring.data.redis;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;

import java.util.List;

/**
 * @author: liuyaowen
 * @poject: spring_middleware.java
 * @create: 2024-07-13 22:07
 * @Description:
 */
@SpringBootTest
public class GEOAPI {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testGeo() {
        // GEOADD key longitude latitude member [longitude latitude member ...]
        /**
         * 1.添加地理位置的坐标
         */
        Long add = stringRedisTemplate.opsForGeo().add("key", new Point(1, 1), "value1");

        // GEODIST key member1 member2 [unit]
        /**
         * 2.返回两个给定位置之间的距离
         */
        Distance distance = stringRedisTemplate.opsForGeo().distance("key", "value1", "value2");
        Double value = distance.getValue();

        // GEOHASH key member [member ...]
        /**
         * 3.返回一个或多个位置元素的 Geohash 表示
         */
        List<String> hash = stringRedisTemplate.opsForGeo().hash("key", "value1");

        // GEOPOS key member [member ...]
        /**
         * 4.返回一个或多个位置元素的经纬度
         */
        List<Point> points = stringRedisTemplate.opsForGeo().position("key", "value1");

        // GEOSEARCH key FROMMEMBER member [BYRADIUS radius m|km|ft|mi] [BYBOX width height m|km|ft|mi] [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count] [ASC|DESC] [STORE storeKey] [STOREDIST storeDistKey]
        /**
         * 5.搜索指定半径范围内的元素
         */
        GeoResults<RedisGeoCommands.GeoLocation<String>> search = stringRedisTemplate.opsForGeo().search("key", GeoReference.fromMember("value1"),
                                                                   new Distance(5, RedisGeoCommands.DistanceUnit.KILOMETERS),
                                                                   RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                                                                                                        .includeDistance()
                                                                                                        .includeCoordinates()
                                                                                                        .sortAscending()
                                                                                                        .limit(5));
        assert search != null;
        search.getContent().forEach(geoLocation -> {
            String name = geoLocation.getContent().getName();
            Point point = geoLocation.getContent().getPoint();
            Double distance1 = geoLocation.getDistance().getValue();
        });


    }
}
