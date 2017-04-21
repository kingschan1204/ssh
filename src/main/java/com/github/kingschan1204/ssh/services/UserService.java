package com.github.kingschan1204.ssh.services;

import com.github.kingschan1204.ssh.model.po.SshUsersEntity;
import com.github.kingschan1204.ssh.model.vo.UserVo;
import com.github.kingschan1204.ssh.repositories.UserDao;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.converters.IntegerConverter;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingschan on 2017/4/17.
 */
@Service
public class UserService {
    @Resource
    private UserDao userDao;

    // 根据id数组删除
    public void deleteByIds(Integer[] ids) {
        for(Integer id : ids) {
            userDao.delete(id);
        }
    }

    // 新增用户
    public void saveUser(SshUsersEntity user) {
        userDao.save(user);
    }

    public UserVo getUser(Integer id) {
        SshUsersEntity user = userDao.findOne(id);
        UserVo vo = new UserVo();
        vo.setAge(user.getAge());
        vo.setRemark(user.getRemark());

        // birthday需要转换一下
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
        String birthday = df.format(user.getBirthday());
        //再转换为date字符串
        birthday = birthday.substring(0,birthday.length() - 9);

        vo.setBirthday(birthday);
        vo.setEmail(user.getEmail());
        vo.setId(user.getId());
        vo.setPassword(user.getPassword());
        vo.setUsername(user.getUsername());
        vo.setSex(user.isSex());
        return vo;
    }

    public Page<UserVo> getUsers(int pageindex, int pagesize, final String username, final String email)throws Exception{
        Pageable pageable = new PageRequest(pageindex - 1,pagesize);
        Page<UserVo> data=userDao.findAll(new Specification<SshUsersEntity>() {
            @Override
            public Predicate toPredicate(Root<SshUsersEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(username)){
                    predicates.add(criteriaBuilder.like(root.<String>get("username"), "%"+username+"%"));
                }
                if (StringUtils.isNotBlank(email)){
                    predicates.add(criteriaBuilder.like(root.<String>get("email"), "%"+email+"%"));
                }
                if (predicates.size()==0)return null;
                return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
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
