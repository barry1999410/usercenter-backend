package com.lby.center.service;

import com.lby.applicationContext;
import com.lby.center.model.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = applicationContext.class)
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Resource
    UserService userService;


    @Test
   public void testAddUser(){
        User user = new User();
        user.setId(0L);
        user.setUsername("刘伯阳");
        user.setUserAccount("17612314051");
        user.setAvatarUrl("https://ts1.cn.mm.bing.net/th/id/R-C.0f7e0f8f147bb9dfafc5e4c3bece59f2?rik=auXMf%2b3yZ3xMLQ&riu=http%3a%2f%2fimg.qqtouxiangzq.com%2f6%2f1182%2f32.jpg&ehk=kLA%2fNQgc8j3Poiz5Hva1NiVpJlwbSQosepCOeN5wde4%3d&risl=&pid=ImgRaw&r=0");
        user.setGender(1);
        user.setUsrPassword("123456");
        user.setEmail("1146911950@qq.com");
        user.setUserStatus(1);
        boolean res = userService.save(user);
        System.out.println(user);

    }
}
