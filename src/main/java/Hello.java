import com.tjise.entity.User;
import com.tjise.mapper.UserMapper;
import com.tjise.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

public class Hello {

    public static void main(String[]args){
        SqlSession session=MyBatisUtil.openSession();
        UserMapper userMapper=session.getMapper(UserMapper.class);
        User user=new User(2,"汪俊伟","000000",100);
        try{
            userMapper.insertUser(user);
            session.commit();
        }catch (Exception e){
            e.printStackTrace();
            session.rollback();
        }
    }
}
