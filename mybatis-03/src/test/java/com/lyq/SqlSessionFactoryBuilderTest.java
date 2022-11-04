package com.lyq;

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
import java.util.List;

@Slf4j
public class SqlSessionFactoryBuilderTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void getSqlSessionFactory() throws IOException {
        InputStream config = Resources.getResourceAsStream("mybatis-config.xml");
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(config);

    }

    @Test
    public void testSqlSession() {
        // 参数可以指定是否自动提交事务
        // try-final语法糖,自动关闭括号内打开的连接
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
           /* UserModel user = UserModel.builder().id(2L).name("lyq").age(19).salary(3000D).sex(1).build();
            int affect = sqlSession.insert("com.lyq.mapper.UserMapper.insertUser", user);
            log.info("影响行数为:{}",affect);*/

            //xxx是mapperxml的命名空间,在没有和mapper接口绑定是可以自定义
            List<Object> list = sqlSession.selectList("xxx.listAll");
            log.info("全部数据:{}",list);
        }
    }

    @Test
    public void testMapper(){
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<UserModel> list = userMapper.listAll();
            list.stream().forEach(user-> System.out.println(user.toString()));
        }
    }
}
