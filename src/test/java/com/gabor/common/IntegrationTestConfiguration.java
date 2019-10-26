package com.gabor.common;

import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.configurations.UrlTestConfiguration;
import com.gabor.partypeps.configurations.DatabaseConfiguration;
import com.gabor.partypeps.configurations.EntityManagerFactoryConfiguration;
import com.gabor.partypeps.configurations.RepositoryConfiguration;
import com.gabor.partypeps.enums.RequestPathEnum;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = {
        DatabaseConfiguration.class,
        EntityManagerFactoryConfiguration.class,
        RepositoryConfiguration.class,
        MapperTestConfiguration.class,
        ServiceTestConfiguration.class,
        UrlTestConfiguration.class
})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "IT")
public @interface IntegrationTestConfiguration {
    RequestPathEnum path();

    boolean hasId() default false;

    boolean hasOtherParameter() default false;

    long id() default 1;

    String otherParam() default "";

    boolean withCredentials() default false;

    String username() default "admin";

    String password() default "admin";
}
