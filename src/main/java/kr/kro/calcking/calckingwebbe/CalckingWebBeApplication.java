package kr.kro.calcking.calckingwebbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CalckingWebBeApplication {
	public static void main(String[] args) {
		SpringApplication.run(CalckingWebBeApplication.class, args);
	}
}
