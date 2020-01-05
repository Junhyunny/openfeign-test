package com.openfeign.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@FeignClient(name = "server-xxxxxx", url = "http://localhost:8080")
interface CandlestickClient {

	@GetMapping("/{resourceName}/endpoint")
	List<String> getCandlesticks(@PathVariable(name = "resourceName") String resourceName);
}

@SpringBootApplication
@RestController
@EnableFeignClients(clients = { CandlestickClient.class })
public class OpenfeignTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenfeignTestApplication.class, args);
	}

	@Autowired
	CandlestickClient candlestickClient;

	@GetMapping(value = "/test")
	public void test() {
		System.out.println(candlestickClient.getCandlesticks("hello1"));
		System.out.println(candlestickClient.getCandlesticks("hello2"));
	}

	@GetMapping(value = "/hello1/endpoint")
	public List<String> hello1() {
		List<String> list = new ArrayList<>();
		list.add("hello");
		list.add("world");
		return list;
	}

	@GetMapping(value = "/hello2/endpoint")
	public List<String> hello2() {
		List<String> list = new ArrayList<>();
		list.add("openfeign");
		list.add("test");
		return list;
	}
}
