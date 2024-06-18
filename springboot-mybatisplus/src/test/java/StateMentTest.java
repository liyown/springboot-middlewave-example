import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.Update;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.lyw.mapper.UserMapper;
// import com.lyw.pojo.User;
import com.lyw.pojo.User;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.BiFunction;

/**
 * @author: liuyaowen
 * @poject: springboot
 * @create: 2024-06-06 12:13
 * @Description:
 */
// @SpringBootTest(classes = Main.class)
public class StateMentTest {

    // @Autowired
    private DataSource dataSource;

    @Test
    public void test() {
        Configuration configuration = new Configuration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        MapperBuilderAssistant userMapperAssistant = new MapperBuilderAssistant(configuration, "UserMapper");
        TableInfo tableInfo = TableInfoHelper.initTableInfo(userMapperAssistant, User.class);
        System.out.println(tableInfo);
        Update update = new Update();
        update.inject(userMapperAssistant, UserMapper.class, User.class, tableInfo);

        MappedStatement mappedStatement = update.injectMappedStatement(UserMapper.class, User.class, tableInfo);
        System.out.println(mappedStatement.getSqlSource());

        BiFunction<ApplicationContextFactory, WebApplicationType, ConfigurableApplicationContext> action = ApplicationContextFactory::create;
    }

    @Test
    public void test01() {
        User user = new User();
        user.setName("lyw");
        user.setAge(18);
         // 序列化 user
        System.out.println(user);
    }

    @Test
    public void test02() throws SQLException {
        User user = new User();
        user.setName("lyw");
        user.setAge(18);

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from user where (name = ?)");
        preparedStatement.setObject(1, user.toString().getBytes());
        boolean execute = preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1));
        }
    }

    @Test
    public void test03() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", "a")
                .gt("age", 20)
                .or()
                .isNull("email");
        System.out.println(queryWrapper.getCustomSqlSegment());
    }


}



