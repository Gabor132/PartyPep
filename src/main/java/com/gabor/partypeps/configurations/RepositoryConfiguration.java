package com.gabor.partypeps.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableJpaRepositories("com.gabor.partypeps.repositories")
@Transactional
public class RepositoryConfiguration {
}
