package com.polarbookshop.catalogservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "polar")
public class PolarProperties {

	/**
	 * A message to welcome user
	 */
	private String greetingUsingConfig;
	private String greetingUsingEnv;

	public String getGreetingUsingConfig() {
		return greetingUsingConfig;
	}

	public void setGreetingUsingConfig(String greetingUsingConfig) {
		this.greetingUsingConfig = greetingUsingConfig;
	}

	public String getGreetingUsingEnv() {
		return greetingUsingEnv;
	}

	public void setGreetingUsingEnv(String greetingUsingEnv) {
		this.greetingUsingEnv = greetingUsingEnv;
	}
}
