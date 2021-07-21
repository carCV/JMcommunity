package com.jmlee.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @author jmLee
 */
@SpringBootApplication
public class CommunityApplication {

    public static void main(String[] args) {
        // 解决elasticsearch netty启动冲突问题，see Netty4Utils.setAvailableProcessors()
        System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(CommunityApplication.class, args);
    }

}
