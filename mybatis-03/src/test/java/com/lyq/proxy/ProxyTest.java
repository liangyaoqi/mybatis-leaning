package com.lyq.proxy;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class ProxyTest {
    public static class UserMapperProxy implements InvocationHandler {
        private SqlSession sqlSession;

        private Class<?> mapperClass;

        public UserMapperProxy(SqlSession sqlSession, Class<?> mapperClass) {
            this.sqlSession = sqlSession;
            this.mapperClass = mapperClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            log.debug("invoke start");
            String statement = mapperClass.getName() + "." + method.getName();
            List<Object> result = sqlSession.selectList(statement);
            log.debug("invoke end");
            return result;
        }

    }
}

