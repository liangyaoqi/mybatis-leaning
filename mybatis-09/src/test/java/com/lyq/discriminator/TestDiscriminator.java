package com.lyq.discriminator;

import com.lyq.entity.OrderModel;
import com.lyq.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class TestDiscriminator {
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
            OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
            //查询订单为1的
            OrderModel orderModel = mapper.getById1(1);
            log.info("{}", orderModel);
            log.info("------------------------------------------------------------");
            //查询订单为2的
            orderModel = mapper.getById1(2);
            log.info("{}", orderModel);
            log.info("------------------------------------------------------------");
            //查询订单为3的
            orderModel = mapper.getById1(3);
            log.info("{}", orderModel);
        }
    }
}
