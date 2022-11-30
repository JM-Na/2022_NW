package com.example.buddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.ServerSocket;

@SpringBootApplication
public class BuddyApplication {

	public static void main(String[] args) {

		SpringApplication.run(BuddyApplication.class, args);

		// 메모리 사용량 출력
		long heapSize = Runtime.getRuntime().totalMemory();
		System.out.println("HEAP Size(M) : " + heapSize / (1024 * 1024) + " MB");

	}
}
