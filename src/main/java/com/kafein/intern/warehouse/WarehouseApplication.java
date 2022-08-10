package com.kafein.intern.warehouse;

import com.kafein.intern.warehouse.service.ReportService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WarehouseApplication {

    public static void main(String[] args) {

        SpringApplication.run(WarehouseApplication.class, args);
    }

}
