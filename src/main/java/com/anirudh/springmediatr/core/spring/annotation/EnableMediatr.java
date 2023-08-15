package com.anirudh.springmediatr.core.spring.annotation;

import com.anirudh.springmediatr.core.spring.SpringMediatrConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Enable MediatR-related functionality in Spring applications.
 * Applying this annotation to a configuration class or the main class triggers the configuration
 * necessary for MediatR operations.
 *
 * @author Anirudh Pandita
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SpringMediatrConfiguration.class) // Import the configuration class
public @interface EnableMediatr {
}