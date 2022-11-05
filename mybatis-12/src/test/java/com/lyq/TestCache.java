package com.lyq;

import com.lyq.entity.UserModel2;
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
public class TestCache {
    private SqlSessionFactory sqlSessionFactory;
    @Before
    public void before() throws IOException {
        //指定mybatis全局配置文件
        String resource = "mybatis-config.xml";
        //读取全局配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //构建SqlSessionFactory对象
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test(){
        try (SqlSession sqlSession = this.sqlSessionFactory.openSession(true);) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            //第一次查询
            List<UserModel2> userModelList1 = mapper.getList1(null);
            log.info("{}", userModelList1);
            //第二次查询
            List<UserModel2> userModelList2 = mapper.getList1(null);
            log.info("{}", userModelList2);
            log.info("{}", userModelList1 == userModelList2);
        }
    }


    // 让mybatis一级缓存失效的三种方式

    //增、删、改
    @Test
    public void test1(){
        try (SqlSession sqlSession = this.sqlSessionFactory.openSession(true);) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            //第一次查询
            List<UserModel2> userModelList1 = mapper.getList1(null);
            log.info("{}", userModelList1);

            //新增一条数据
            mapper.insert1(UserModel2.builder().id(100).name("路人").age(30).build());

            //第二次查询
            List<UserModel2> userModelList2 = mapper.getList1(null);
            log.info("{}", userModelList2);
            log.info("{}", userModelList1 == userModelList2);
        }
    }

    //删
    @Test
    public void test2(){
        try (SqlSession sqlSession = this.sqlSessionFactory.openSession(true);) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            //第一次查询
            List<UserModel2> userModelList1 = mapper.getList1(null);
            log.info("{}", userModelList1);

            //嗲用clearCache方法清除SqlSession缓存
            sqlSession.clearCache();

            //第二次查询
            List<UserModel2> userModelList2 = mapper.getList1(null);
            log.info("{}", userModelList2);
            log.info("{}", userModelList1 == userModelList2);
        }
    }

    //Select元素的flushCache置为true
    @Test
    public void test3(){
        try (SqlSession sqlSession = this.sqlSessionFactory.openSession(true);) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            //查询1：getList1
            log.info("查询1 start");
            log.info("查询1：getList1->{}", mapper.getList1(null));
            //查询2：getList1
            log.info("查询2 start");
            log.info("查询2：getList1->{}", mapper.getList1(null));
            //查询3：getList2
            log.info("查询3 start");
            log.info("查询3：getList2->{}", mapper.getList2(null));
            //查询4：getList2
            log.info("查询4 start");
            log.info("查询4：getList2->{}", mapper.getList2(null));
            //查询5：getList1
            log.info("查询5 start");
            log.info("查询5：getList1->{}", mapper.getList1(null));
        }
    }


}
