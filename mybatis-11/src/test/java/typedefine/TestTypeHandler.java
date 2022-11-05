package typedefine;

import com.lyq.entity.SexEnum;
import com.lyq.entity.UserModel1;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TestTypeHandler {
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
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            // 此时mybatis无法自动映射结果集，因为User类中的枚举属性sex并不知道该如何映射
            List<UserModel1> userModelList = userMapper.getListUser();
            log.info("{}",userModelList);
        }
    }

    @Test
    public void insert1() throws IOException {
        try (SqlSession sqlSession = this.sqlSessionFactory.openSession(true);) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            Map<String, Object> map = new HashMap<>();
            Integer id = Integer.valueOf(Calendar.getInstance().getTime().getTime() / 1000 + "");
            map.put("id", id);
            map.put("name", id.toString());
            map.put("age", 30);
            map.put("sex", SexEnum.WOMAN);
            int result = mapper.insert1(map);
            log.info("{}", result);
        }
    }
}
