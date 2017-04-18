package com.github.kingschan1204.ssh.repositories;

import com.github.kingschan1204.ssh.model.po.SshUsersEntity;
import net.sf.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * Created by kingschan on 2017/4/17.
 */
@ContextConfiguration(locations =
        {
                "classpath:/applicationContext.xml"
        })
public class UserDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private UserDao dao;


    @Rollback(value = false) //加上这个注解事务才不会回滚
    @Test
    public void saveTest(){
        for (int i = 0; i < 1000; i++) {
            SshUsersEntity user = new SshUsersEntity();
            user.setAge(12);
            user.setBirthday(new Timestamp(System.currentTimeMillis()));
            user.setEmail("10074517@qq.com");
            user.setPassword("password");
            user.setRemark("remark");
            user.setSex(true);
            user.setUsername(i+"kings.chan");
            dao.save(user);
        }
    }

    /**
     * 分页测试
     */
    @Ignore
    @Test
    public void pagingTest(){
        Pageable pageable = new PageRequest(0,3);
        Page<SshUsersEntity> itera=dao.findAll(pageable);
        System.out.println( JSONObject.fromObject(itera));
        for (SshUsersEntity user :itera) {
            System.out.println( JSONObject.fromObject(user));
        }

    }
}
