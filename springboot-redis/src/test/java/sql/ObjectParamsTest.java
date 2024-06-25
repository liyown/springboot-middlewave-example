package sql;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lyw.HMDPMain;
import com.lyw.mapper.UserMapper;
import com.lyw.pojo.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.relational.core.sql.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-24 15:04
 * @Description:
 */
@SpringBootTest(classes = HMDPMain.class)
public class ObjectParamsTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testObjectParams() {
        User user = new User();
        user.setId(1L);

        UpdateWrapper<User> objectUpdateWrapper = new UpdateWrapper<>();
        objectUpdateWrapper.eq("id", 1L);
        objectUpdateWrapper.set("asd", new User());

        System.out.println(userMapper.selectByIds(1L));

    }
    @Test
    public void test2() {


    }


}
