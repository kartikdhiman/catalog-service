package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.config.PolarProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@Autowired
	private PolarProperties polarProperties;

	@Value("${polar.greeting-using-value}")
	private String greetingMsg;

	@Autowired
	private Environment env;

	@GetMapping("/")
	public String getGreeting() {
		return """
						greeting Using <bold>@Value</bold>: %s</br>
						greeting using <bold>Environment</bold> : %s</br>
						greeting using <bold>ConfigurationProperties</bold>: %s""".formatted(
						greetingMsg,
						env.getProperty("greeting.greeting-using-env"),
						polarProperties.getGreetingUsingConfig()
		);
	}
}
