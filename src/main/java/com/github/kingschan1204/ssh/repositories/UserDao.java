package com.github.kingschan1204.ssh.repositories;

import com.github.kingschan1204.ssh.model.po.SshUsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kingschan on 2017/4/17.
 * 用户dao
 */
@Repository
public interface UserDao extends PagingAndSortingRepository<SshUsersEntity, Integer>  {

    /**
     * 根据用户名和email分页查询
     * @param username
     * @param email
     * @param pageable
     * @return
     */
    Page<SshUsersEntity> findByUsernameOrEmail(String username,String email,Pageable pageable);

    /**
     * 查出所有用户，jpa动态查询
     * @param spec
     * @param pageable
     * @return
     */
    Page<SshUsersEntity> findAll(Specification<SshUsersEntity> spec, Pageable pageable);

}
