package com.credible;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({"com.credible.api.repositories"})
@ComponentScan({"com.credible.api", "com.credible.test.configuration"})
public class NonSecureApplication {
}
