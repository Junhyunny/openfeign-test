package com.openfeign.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@FeignClient(name = "TestClient", url = "http://localhost:8080")
interface TestClient {

	@GetMapping(path = "/test/array")
	void testArray(@SpringQueryMap Map<String, String[]> paramMap);

	@GetMapping(path = "/test/list")
	void testList(@SpringQueryMap Map<String, List<Object>> paramMap);

}

@SpringBootApplication
@RestController
@EnableFeignClients(clients = { TestClient.class })
public class OpenfeignTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenfeignTestApplication.class, args);
	}

	@Autowired
	TestClient testClient;

	@GetMapping(path = "/testArray")
	public void TestArray() {
		Map<String, String[]> paramMap = new HashMap<>(2);
		paramMap.put("a", new String[] { "a1", "a2" });
		paramMap.put("b", new String[] { "b1", "b2" });

		testClient.testArray(paramMap);
	}

	@GetMapping(path = "/testList")
	public void TestList() {
		Map<String, List<Object>> paramMap = new HashMap<>(2);

		ArrayList<Object> listA = new ArrayList<>();
		listA.add("a1");
		listA.add("a2");

		ArrayList<Object> listB = new ArrayList<>();
		listB.add("b1");
		listB.add("b2");

		paramMap.put("a", listA);
		paramMap.put("b", listB);

		testClient.testList(paramMap);
	}

	@GetMapping(path = "/test/array")
	void testArray(@RequestHeader Map<String, Object> header, @RequestParam Map<String, String[]> paramMap) {
		System.out.println(header);
		System.out.println(paramMap);
	}

	@GetMapping(path = "/test/list")
	void get(@RequestHeader Map<String, Object> header, @RequestParam Map<String, List<Object>> paramMap) {
		System.out.println(header);
		System.out.println(paramMap);
	}

}
