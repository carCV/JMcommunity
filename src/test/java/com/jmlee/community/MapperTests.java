package com.jmlee.community;

import com.jmlee.community.dao.DiscussPostMapper;
import com.jmlee.community.dao.LoginTicketMapper;
import com.jmlee.community.dao.UserMapper;
import com.jmlee.community.entity.DiscussPost;
import com.jmlee.community.entity.LoginTicket;
import com.jmlee.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void testSelectUser() {
        User user = userMapper.selectById(101);
        System.out.println(user);

        User user2 = userMapper.selectByName("liubei");
        System.out.println(user2);

        User user3 = userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println(user3);
    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setEmail("test@163.com");
        user.setHeaderUrl("http://www.nowcoder.com/102.png");
        user.setCreateTime(new Date());
        int rows = userMapper.insertUser(user);

        System.out.println(rows);
        System.out.println(user.getId());
    }

    @Test
    public void testUpdateUser() {
        int rows = userMapper.updateStatus(153, 1);
        System.out.println(rows);

        rows = userMapper.updateHeader(153,"http://www.nowcoder.com/103.png");
        System.out.println(rows);

        rows = userMapper.updatePassword(153, "helloNew");
        System.out.println(rows);
    }

    @Test
    public void testSelectDiscussPost() {

        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPost(0, 0, 10);
        discussPosts.forEach(System.out::println);

        Integer rows = discussPostMapper.selectDiscussPostRows(0);
        System.out.println(rows);

    }


    @Test
    public void testInsertLoginTicket() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000*60*10));

        Integer rows = loginTicketMapper.insertLoginTicket(loginTicket);

    }

    @Test
    public void testSelectLoginTicket() {
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);

        loginTicketMapper.updateStatus("abc",1);
        loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);
    }

}
