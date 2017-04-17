package com.github.kingschan1204.ssh.services;

import com.github.kingschan1204.ssh.model.po.SshUsersEntity;
import com.github.kingschan1204.ssh.repositories.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by kingschan on 2017/4/17.
 */
@Service
public class UserService {
    @Resource
    private UserDao userDao;

    // 新增用户
    public void saveUser(SshUsersEntity user) {
        userDao.save(user);
    }
}
