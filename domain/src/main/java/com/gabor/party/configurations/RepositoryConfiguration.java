package com.gabor.party.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableJpaRepositories("com.gabor.party.repositories")
@Transactional
public class RepositoryConfiguration { }
