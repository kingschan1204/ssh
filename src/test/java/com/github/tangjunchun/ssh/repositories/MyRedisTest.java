package com.github.tangjunchun.ssh.repositories;

import com.github.kingschan1204.ssh.model.vo.UserVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/15.
 */
@ContextConfiguration(locations =
        {
                "classpath:/applicationContext.xml"
        })
public class MyRedisTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    RedisTemplate redisTemplate;
    // inject the template as ListOperations
    // can also inject as Value, Set, ZSet, and HashOperations


    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOps;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOps;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Object> hashOps;

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOps;

    @Resource(name = "redisTemplate")
    private ZSetOperations<String, String> ZSetOps;

    @Test
    public void testListOps() {
        //List 其实是一个双向链表，从下面的代码就能看出来可以从两边进行push/pop操作
        redisTemplate.delete("listTest"); //删除这个List
        //添加操作
        listOps.rightPush("listTest", "1");
        listOps.rightPush("listTest", "2");
        listOps.leftPush("listTest", "3");
        listOps.leftPush("listTest", "4");

        // 查看listTest这个List的值
        System.out.println(listOps.range("listTest", 0, -1));   //[4, 3, 1, 2]

        // 取左边第一个值
        System.out.println(listOps.leftPop("listTest"));    //4

        // 查看listTest这个List的值
        System.out.println(listOps.range("listTest", 0, -1));   //[3, 1, 2]

        //修改操作  将下标为1的值修改为 "set_1"，(下标从0开始)
        listOps.set("listTest", 1, "set_1");
        System.out.println(listOps.range("listTest", 0, -1));   //[3, set_1, 2]

        //删除操作  将下标为1，值为 set_1的元素删除
        listOps.remove("listTest", 1, "set_1");
        System.out.println(listOps.range("listTest", 0, -1));   //[3, 2]
    }

    //set的操作
    @Test
    public void testSetOps() {
        //添加
        setOps.add("setTest1", "1", "2", "3", "4");
        setOps.add("setTest2", "a", "b", "1", "2");

        //查询 set集合是无序的
        System.out.println(setOps.members("setTest1"));//[2, 1, 3, 4]

        //判断是否有这个值
        System.out.println(setOps.isMember("setTest1", "4"));// true

        //删除  可以删除多个值
        setOps.remove("setTest1", "1", "4");
        System.out.println(setOps.members("setTest1"));//[3, 2]

        //交集
        System.out.println(setOps.intersect("setTest1", "setTest2"));//[2]

        //并集
        System.out.println(setOps.union("setTest1", "setTest2"));//[3, a, 2, 1, b]

        //差集
        System.out.println(setOps.difference("setTest1", "setTest2"));//[3]
    }

    // ZSetOps操作
    @Test
    public void testZSetOps() {
        //添加
        ZSetOps.add("ZSetTest", "google.com", 10);
        ZSetOps.add("ZSetTest", "baidu.com", 8);
        ZSetOps.add("ZSetTest", "soso.com", 5);
        ZSetOps.add("ZSetTest", "360.com", 2);

        //默认按照分数的降序排列
        System.out.println(ZSetOps.range("ZSetTest", 0, -1));
        //[360.com, soso.com, baidu.com, google.com]

        //按照分数范围查询
        System.out.println(ZSetOps.rangeByScore("ZSetTest", 5, 10));
        //[soso.com, baidu.com, google.com]

        //查看排名
        System.out.println(ZSetOps.rank("ZSetTest", "baidu.com"));// 2  说明第一名是0

        // 按分数从大到小排列
        System.out.println(ZSetOps.reverseRange("ZSetTest", 0, -1));
        //[google.com, baidu.com, soso.com, 360.com]

        //看这个对象的 socre
        System.out.println(ZSetOps.score("ZSetTest", "google.com")); //10.0

        // 删除,按照对象
        ZSetOps.remove("ZSetTest", "360.com");
        // 删除，根据下标
        ZSetOps.removeRange("ZSetTest", 0, 1);
        // 删除，根据score
        ZSetOps.removeRangeByScore("ZSetTest", 2, 5);
    }

    // hash表操作 和Map差不多
    @Test
    public void testHashOps() {
        // 添加
        hashOps.put("hashTest", "key1", "aa");
        Map<String, String> map = new HashMap<String, String>();
        map.put("key2", "bb");
        map.put("key3", "cc");
        map.put("key4", "dd");
        hashOps.putAll("hashTest", map);

        // 查询所有的key
        System.out.println(hashOps.keys("hashTest")); //[key3, key4, key2, key1]

        //查询 所有的value
        System.out.println(hashOps.values("hashTest"));//[aa, bb, cc, dd]

        //删除 key
        hashOps.delete("hashTest", "key1");
        System.out.println(hashOps.keys("hashTest")); //[key2, key4, key3]
    }

    // String操作
    @Test
    public void testValueOps() {
        valueOps.set("valueTest", "1");
        System.out.println(valueOps.get("valueTest"));
    }

    @Resource(name = "redisTemplate")
    private ListOperations<String, UserVo> userListOps;

    @Test
    public void userList() {
        redisTemplate.delete("userList"); //删除这个List
        // 放入user,从1开始,每次往右边追加
        for (int i = 1; i < 8; i++) {
            userListOps.rightPush("userList", new UserVo(i, "tjc" + i, "password", "男", 18, "7715@qq.com", "1993-10-11", "remark"));
            // 醒目点
            System.out.println();
        }

        System.out.println("初始队列为");
        // 查看当前队列
        System.out.println(userListOps.range("userList", 0, -1)); // 应为 1 2 3 4 5 6 7

        // 取出左边第一个user,取出操作在获取左边第一个user的同时会将其从队列删除
        System.out.println("取出user:" + userListOps.leftPop("userList"));    //取出user:1

        List<UserVo> userList = userListOps.range("userList", 0, -1);

        // 查看当前队列
        System.out.println("当前队列:" + userList); // 应为 2 3 4 5 6 7


        int size = userList.size();

        // 重复操作,最后只剩下7
        while(size > 1) {
            // 取出左边第一个user,取出操作在获取左边第一个user的同时会将其从队列删除
            System.out.println("取出user:" + userListOps.leftPop("userList"));

            // 获取当前队列
            userList = userListOps.range("userList", 0, -1);

            // 查看当前队列
            System.out.println("当前队列:" + userList);

            size = userList.size();
        }
    }
}
