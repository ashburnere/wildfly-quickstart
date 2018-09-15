package de.ashburnere.quickstart.numberguess;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

/**
 * The @MaxNumber qualifier, used for injecting the maximum number that can be injected.
 * 
 * @author Erik
 *
 */
@Retention(RUNTIME)
@Documented
@Qualifier
public @interface MaxNumber {

}
