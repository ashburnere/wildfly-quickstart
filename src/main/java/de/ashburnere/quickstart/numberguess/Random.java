package de.ashburnere.quickstart.numberguess;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;

import javax.inject.Qualifier;

/**
 * 
 * A qualifier is used to disambiguate between two beans both of which are
 * eligible for injection based on their type. For more, see the Weld Reference
 * Guide.
 * 
 * The @Random qualifier, used for injecting a random number.
 * 
 * 
 * @author Erik
 *
 */
@Target({ TYPE, METHOD, PARAMETER, FIELD })
@Retention(RUNTIME)
@Documented
@Qualifier
public @interface Random {

}
