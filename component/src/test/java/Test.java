
import com.tz.mapper.AdminMapper;
import com.tz.pojo.Admin;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class Test {


    @Autowired
    AdminMapper adminMapper;


    @org.junit.Test
    public void generateData() {
        Admin admin = new Admin();
        for (int i = 2; i < 133; i++) {
            admin.setId(i);
            admin.setLoginAcct("account" + i);
            admin.setUserName("admin" + i);
            admin.setUserPswd("password" + i);
            admin.setEmail("email" + i);
            admin.setCreateTime("2021-4-1");
            adminMapper.insert(admin);
        }
    }
}
