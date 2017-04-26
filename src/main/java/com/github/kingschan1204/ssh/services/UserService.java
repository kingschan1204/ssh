package com.github.kingschan1204.ssh.services;

import com.alibaba.druid.sql.PagerUtils;
import com.github.kingschan1204.ssh.model.po.SshUsersEntity;
import com.github.kingschan1204.ssh.model.vo.UserByPageVo;
import com.github.kingschan1204.ssh.model.vo.UserVo;
import com.github.kingschan1204.ssh.repositories.UserDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import java.util.Iterator;
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
    public void saveUser(UserVo userVo) {
        SshUsersEntity userPo = new SshUsersEntity();
        // 把vo转成po
        BeanUtils.copyProperties(userVo,userPo);
        // 手动转化类型不同的birthday属性
        userPo.setBirthday(Timestamp.valueOf(userVo.getBirthday() + " 00:00:00"));
        userPo.setSex(userVo.getSex().equals("男"));
        userDao.save(userPo);
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
        vo.setSex(user.isSex()?"男":"女");
        return vo;
    }

    /**
     * 分页查找用户并支持分页
     * @param pageindex 页码
     * @param pagesize  显示条数
     * @param username 用户名
     * @param email Email
     * @param orderField 排序字段
     * @param sortStyle 排序方式
     * @return
     * @throws Exception
     */
    public Page<UserVo> getUsers(int pageindex, int pagesize, final String username, final String email,String orderField,String sortStyle)throws Exception{
        Pageable pageable =null;
        // 判断是否包含排序信息,生产对应的Pageable查询条件
        if (null != sortStyle && null != orderField){
            Sort sort=new Sort(
                    sortStyle.equalsIgnoreCase("asc")?
                            Sort.Direction.ASC:Sort.Direction.DESC
                    ,orderField);
            pageable = new PageRequest(pageindex - 1,pagesize,sort);
        }else{
            pageable = new PageRequest(pageindex - 1,pagesize);
        }

        // 获取包含分页信息和UserVo集合的Page<UserVo>对象
        Page<UserVo> data=userDao.findAll(new Specification<SshUsersEntity>() {
            @Override
            public Predicate toPredicate(Root<SshUsersEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                // 判断字段是否存在来决定添加的条件
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
                String birthday = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sshUsersEntity.getBirthday());
                // 再转换为date字符串
                birthday = birthday.substring(0,birthday.length() - 9);
                vo.setBirthday(birthday);
                // 转换性别
                vo.setSex(sshUsersEntity.isSex()?"男":"女");
                return vo;
            }
        });
        return data;
    }

    /**
     * 不分页获取所有UserVo集合
     * @return
     */
    public List<UserVo> getAllUsers() {
        Iterator<SshUsersEntity> userPos = userDao.findAll().iterator();
        List l = new ArrayList();
        while(userPos.hasNext()){
            SshUsersEntity userPo = userPos.next();
            UserVo vo = new UserVo();
            BeanUtils.copyProperties(userPo,vo);
            String birthday = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userPo.getBirthday());
            // 再转换为date字符串
            birthday = birthday.substring(0,birthday.length() - 9);
            vo.setBirthday(birthday);
            vo.setSex(userPo.isSex()?"男":"女");
            l.add(vo);
        }
        return l;
    }

}