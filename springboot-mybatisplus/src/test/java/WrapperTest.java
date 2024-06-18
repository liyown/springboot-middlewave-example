import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.lyw.pojo.User;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.junit.jupiter.api.Test;

import java.io.FilterOutputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: liuyaowen
 * @poject: springboot
 * @create: 2024-06-11 19:31
 * @Description:
 */
public class WrapperTest {

    @Test
    public void testQueryWrapper() {
        User user = new User();
        user.setName("lyw");
        user.setAge(18);
        QueryWrapper<User> wrapper = new QueryWrapper<>(user);
        wrapper.eq("name", "lyw");
        System.out.println(wrapper.getCustomSqlSegment());

    }

    @Test
    public void testUpdateWrapper() {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.set("name", "lyw");
        wrapper.eq("name","lyww");
        System.out.println(wrapper.getSqlSegment());
        System.out.println(wrapper.getSqlSet());

    }

    @Test
    public void testLambdaQueryWrapper() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName, "John")
                .ge(User::getAge, 18)
                .last("limit 10");
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), User.class);
        System.out.println(lambdaQueryWrapper.getSqlSegment());

    }

    @Test
    public void testLock() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
    }
}
