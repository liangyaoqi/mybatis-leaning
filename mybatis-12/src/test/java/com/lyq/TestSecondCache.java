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
public class TestSecondCache {
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
    public void test() {
        for (int i = 0; i < 2; i++) {
            try (SqlSession sqlSession = this.sqlSessionFactory.openSession(true);) {
                UserMapper mapper = sqlSession.getMapper(UserMapper.class);
                List<UserModel2> userModelList1 = mapper.getList1(null);
                log.info("{}", userModelList1);
            }
        }
    }

    //二级缓存失效的三种方式

    //增、删、改
    @Test
    public void level2CacheTest2() throws IOException {
        try (SqlSession sqlSession = this.sqlSessionFactory.openSession(true);) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<UserModel2> userModelList1 = mapper.getList1(null);
            log.info("{}", userModelList1);
        }
        try (SqlSession sqlSession = this.sqlSessionFactory.openSession(true);) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            //新增一条数据
            mapper.insert1(UserModel2.builder().id(Integer.valueOf(System.nanoTime() % 100000 + "")).name("路人").age(30).build());
        }
        try (SqlSession sqlSession = this.sqlSessionFactory.openSession(true);) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<UserModel2> userModelList1 = mapper.getList1(null);
            log.info("{}", userModelList1);
        }
    }

    //select元素的flushCache属性置为true
    @Test
    public void level2CacheTest3(){
        //先查询2次getList1,getList1第二次会从二级缓存中拿到数据
        log.info("getList1查询");
        for (int i = 0; i < 2; i++) {
            try (SqlSession sqlSession = this.sqlSessionFactory.openSession(true);) {
                UserMapper mapper = sqlSession.getMapper(UserMapper.class);
                List<UserModel2> userModelList1 = mapper.getList1(null);
                log.info("{}", userModelList1);
            }
        }
        //getList2的flushCache为true，所以查询之前会先将对应的二级缓存中的所有数据清空，所以二次都会访问db
        log.info("getList2查询");
        for (int i = 0; i < 2; i++) {
            try (SqlSession sqlSession = this.sqlSessionFactory.openSession(true);) {
                UserMapper mapper = sqlSession.getMapper(UserMapper.class);
                List<UserModel2> userModelList1 = mapper.getList2(null);
                log.info("{}", userModelList1);
            }
        }
        //二级缓存中没有getList1需要查找的数据了，所以这次访问getList1会去访问db
        log.info("getList1查询");
        try (SqlSession sqlSession = this.sqlSessionFactory.openSession(true);) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<UserModel2> userModelList1 = mapper.getList1(null);
            log.info("{}", userModelList1);
        }
    }

    //select元素的useCache置为false跳过二级缓存，但是不会清空二级缓存数据
    @Test
    public void level2CacheTest4(){
        //第一次查询访问getList1，会将数据丢到二级缓存中
        try (SqlSession sqlSession = this.sqlSessionFactory.openSession(true);) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<UserModel2> userModelList1 = mapper.getList1(null);
            log.info("{}", userModelList1);
        }
        //getList3对应的select的useCache为false，会跳过二级缓存，所以会直接去访问db
        for (int i = 0; i < 2; i++) {
            try (SqlSession sqlSession = this.sqlSessionFactory.openSession(true);) {
                UserMapper mapper = sqlSession.getMapper(UserMapper.class);
                List<UserModel2> userModelList1 = mapper.getList3(null);
                log.info("{}", userModelList1);
            }
        }
        //下面的查询又去执行了getList1，由于上面的第一次查询也是访问getList1会将数据放在二级缓存中，所以下面的查询会从二级缓存中获取到数据
        try (SqlSession sqlSession = this.sqlSessionFactory.openSession(true);) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<UserModel2> userModelList1 = mapper.getList1(null);
            log.info("{}", userModelList1);
        }
    }
}
