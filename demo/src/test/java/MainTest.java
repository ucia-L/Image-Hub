import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.example.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;



public class MainTest {
    @Mock
    private UserService userServiceMock; // 使用Mockito创建模拟对象

    @Test
    public void test() {

    }
}
