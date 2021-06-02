package com.jmlee.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * @Description
 * @Author jmLee
 * @Date 2021/4/11 21:59
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreadTests {

    private static final Integer MAX_THREADS = 100;

    // 减法器
    private static CountDownLatch countDownLatch = new CountDownLatch(MAX_THREADS);


    @Test
    public void testInvoke() {

        for (int i = 0; i < MAX_THREADS; i++) {
            new Thread(()-> {

                try {
                    // 减法器减一
                    countDownLatch.countDown();
                    countDownLatch.await();

                    System.out.println(Thread.currentThread().getName() + "模拟并发");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
