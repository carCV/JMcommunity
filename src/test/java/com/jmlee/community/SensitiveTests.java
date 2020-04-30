package com.jmlee.community;

import com.jmlee.community.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description TODO
 * @Author jmlee
 * @Date 2020/4/29 16:35
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SensitiveTests {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter() {
        String text = "这里可以赌博，可以开票，可以吸毒，哈哈~~";
        text = sensitiveFilter.filter(text);
        System.out.println(text);

        String text2 = "这里可以☼▽赌△★博，可以开☆○票★，可以吸◇◇●毒，哈哈~☆~";
        text2 = sensitiveFilter.filter(text2);
        System.out.println(text2);
    }

}
