package com.jmlee.community;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Description test
 * @Author jmlee
 * @Date 2020/5/19 16:31
 * @Version 1.0
 */
public class BlockingQueueTests {

    public static void main(String[] args) {
        BlockingQueue queue = new ArrayBlockingQueue(10);

        // 模拟一个生产者生产数据
        new Thread(new Producer(queue)).start();

        // 模拟三个消费者并发消费数据
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();
    }
}

class Producer implements Runnable {

    private BlockingQueue<Integer> queue;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        try {
            for (int i = 1; i <= 100; i++) {
                Thread.sleep(20);
                queue.put(i);
                System.out.println(Thread.currentThread().getName() + "生产" + queue.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {

    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        try {
            while (true) {
                Thread.sleep(new Random().nextInt(1000));
                queue.take();

                System.out.println(Thread.currentThread().getName() + "消费" + queue.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
