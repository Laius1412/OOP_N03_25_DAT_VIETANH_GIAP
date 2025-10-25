package com.example.servingwebcontent;

import com.example.servingwebcontent.service.UserService;
import com.example.servingwebcontent.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServingWebContentApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Tạo user mặc định khi khởi động ứng dụng
        userService.createDefaultUsers();
        
        // Tạo dữ liệu mẫu cho thành viên
        personService.createSampleData();
    }
}
