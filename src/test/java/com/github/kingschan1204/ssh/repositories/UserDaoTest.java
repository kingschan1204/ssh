package com.github.kingschan1204.ssh.repositories;

import com.github.kingschan1204.ssh.model.po.SshUsersEntity;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kingschan on 2017/4/17.
 */
@ContextConfiguration(locations =
        {
                "classpath:/applicationContext.xml"
        })
//@TransactionConfiguration(defaultRollback=false)
public class UserDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private UserDao dao;

    @Transactional(readOnly = false,transactionManager ="transactionManager",rollbackFor =Exception.class )
    @Rollback(value = false)
    @Test
    public void saveTest(){
       /* SshUsersEntity user = new SshUsersEntity();
        user.setAge(12);
        user.setBirthday(new Timestamp(System.currentTimeMillis()));
        user.setEmail("10074517@qq.com");
        user.setPassword("password");
        user.setRemark("remark");
        user.setSex(true);
        user.setUsername("kings.chan");
        dao.save(user);*/
        for (int i = 0; i < 2; i++) {
            SshUsersEntity user= dao.findOne(1);
            JSONObject json = JSONObject.fromObject(user);
            System.out.println(json);
        }
    }
}
