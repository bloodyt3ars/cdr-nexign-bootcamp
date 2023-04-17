package ru.homework.cdrtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import ru.homework.cdrtest.component.GeneratePhoneNumbers;

@SpringBootApplication
public class CdrTestApplication {

	public static void main(String[] args) {

		/*ConfigurableApplicationContext run = SpringApplication.run(CdrTestApplication.class, args);
		GeneratePhoneNumbers bean = run.getBean(GeneratePhoneNumbers.class);
		bean.generate(500);*/
		SpringApplication.run(CdrTestApplication.class, args);
	}

}
