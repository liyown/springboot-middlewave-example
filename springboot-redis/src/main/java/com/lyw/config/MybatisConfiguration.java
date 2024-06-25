package com.lyw.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.lyw.pojo.User;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-19 09:32
 * @Description:
 */
@Configuration
public class MybatisConfiguration {

    @Bean
    MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }

//    @Bean
//    TypeHandler<User>  userHandler() {
//        return new BaseTypeHandler<User>() {
//            @Override
//            public void setNonNullParameter(java.sql.PreparedStatement ps, int i, User parameter, org.apache.ibatis.type.JdbcType jdbcType) throws java.sql.SQLException {
//                ps.setString(i, parameter.toString());
//            }
//
//            @Override
//            public User getNullableResult(java.sql.ResultSet rs, String columnName) throws java.sql.SQLException {
//                User user = new User();
//                user.setNickName(rs.getString(columnName));
//                return user;
//            }
//
//            @Override
//            public User getNullableResult(java.sql.ResultSet rs, int columnIndex) throws java.sql.SQLException {
//                User user = new User();
//                user.setNickName(rs.getString(columnIndex));
//                return user;
//            }
//
//            @Override
//            public User getNullableResult(java.sql.CallableStatement cs, int columnIndex) throws java.sql.SQLException {
//                User user = new User();
//                user.setNickName(cs.getString(columnIndex));
//                return user;
//            }
//        };
//    }
}
