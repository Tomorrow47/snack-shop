package com.snackshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class SnackShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(SnackShopApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void openBrowser() {
        try {
            String url = "http://localhost:8080";
            Desktop.getDesktop().browse(new URI(url));
            System.out.println("✅ 浏览器已自动打开: " + url);
        } catch (Exception e) {
            System.out.println("⚠️  请手动打开浏览器访问: http://localhost:8080");
        }
    }
}
