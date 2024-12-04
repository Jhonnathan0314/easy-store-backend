package com.easy.store.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Properties;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void contextLoads() { }

	@Test
	public void mainMethodTest() {
		Properties originalProperties = (Properties) System.getProperties().clone();
		try {
			System.setProperty("spring.main.web-application-type", "none");
			BackendApplication.main(new String[] {});
		} finally {
			System.setProperties(originalProperties);
		}
	}

}
