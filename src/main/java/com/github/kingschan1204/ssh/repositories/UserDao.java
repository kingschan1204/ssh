package com.github.kingschan1204.ssh.repositories;

import com.github.kingschan1204.ssh.model.po.SshUsersEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kingschan on 2017/4/17.
 * 用户dao
 */
@Repository
public interface UserDao extends PagingAndSortingRepository<SshUsersEntity, Integer>  {

}
