package com.jmlee.community;

import com.jmlee.community.util.CommunityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Md5Tests {

    @Test
    public void testMd5() {

        String s = CommunityUtil.md5("123456");
        System.out.println(s);

    }
}
