package com.anirudh.springmediatr.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Spring MediatR library.
 * This class sets up component scanning to discover and configure
 * the core components of the Spring MediatR library
 *
 * @author Anirudh Pandita.
 * @version 1.0
 * @since 1.0
 */
@Configuration
@ComponentScan(basePackages = {"com.anirudh.springmediatr"})
public class SpringMediatrConfiguration {
}
