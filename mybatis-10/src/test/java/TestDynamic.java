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
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TestDynamic {
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
            List<UserModel> userModelList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                userModelList.add(UserModel.builder().id(10 + i).name("mybatis-" + i).age(20 + i).build());
            }
            int count = mapper.insertBatch(userModelList);
            log.info("{}", count);
        }
    }
}
