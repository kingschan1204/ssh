package com.github.kingschan1204.ssh.services;

import com.github.kingschan1204.ssh.model.po.SshUsersEntity;
import com.github.kingschan1204.ssh.model.vo.UserVo;
import com.github.kingschan1204.ssh.repositories.UserDao;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;

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

    public Page<UserVo> getUsers(int pageindex, int pagesize, final String username, final String email)throws Exception{
        Pageable pageable = new PageRequest(pageindex,pagesize);
        Page<UserVo> data=userDao.findAll(new Specification<SshUsersEntity>() {
            @Override
            public Predicate toPredicate(Root<SshUsersEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (StringUtils.isNotBlank(username)){
                    predicate.getExpressions().add(
                            criteriaBuilder.like(root.<String>get("username"), "%" + StringUtils.trim(username) + "%")
                    );
                }
                if (StringUtils.isNotBlank(email)){
                    predicate.getExpressions().add(
                            criteriaBuilder.or(criteriaBuilder.like(root.<String>get("email"), "%" + StringUtils.trim(email) + "%"))
                    );
                }
                return predicate;
            }
        },pageable
        ).map(new Converter<SshUsersEntity, UserVo>() {
            @Override
            public UserVo convert(SshUsersEntity sshUsersEntity) {
                UserVo vo = new UserVo();
                BeanUtils.copyProperties(sshUsersEntity,vo);
                vo.setBirthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sshUsersEntity.getBirthday()) );
                return vo;
            }
        });
        return data;
    }
}
