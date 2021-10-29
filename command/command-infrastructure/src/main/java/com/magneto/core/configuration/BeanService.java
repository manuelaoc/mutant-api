package com.magneto.core.configuration;

import com.magneto.core.repository.MutantRepository;
import com.magneto.core.service.VerifyMutantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanService {

    @Bean
    public VerifyMutantService saveMutantService(MutantRepository mutantRepository) {
        return new VerifyMutantService(mutantRepository);
    }
}
