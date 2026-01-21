package com.mechyam;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
	
@SpringBootTest
@ActiveProfiles("test") // added new line for local Mariadb storage without using the RDS
class MechyamBackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
