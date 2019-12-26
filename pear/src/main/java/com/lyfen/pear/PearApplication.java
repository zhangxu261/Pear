package com.lyfen.pear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author lyfen
 */
@SpringBootApplication
public class PearApplication {
    public static void main(String[] args) {
        SpringApplication.run(PearApplication.class, args);
    }
}
