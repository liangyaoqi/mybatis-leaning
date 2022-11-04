package com.lyq.proxy;

import com.lyq.entity.UserModel;
import com.lyq.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.util.List;

@Slf4j
public class UserMapperProxyTest {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() throws IOException {
        InputStream config = Resources.getResourceAsStream("mybatis-config.xml");
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(config);
    }

    @Test
    public void test3() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(ProxyTest.class.getClassLoader(), new Class[]{UserMapper.class}, new ProxyTest.UserMapperProxy(sqlSession, UserMapper.class));
            List<UserModel> userModels = userMapper.listAll();
            userModels.forEach(user -> log.debug("{}", user));
        }
    }
}
