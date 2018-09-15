package de.ashburnere.quickstart.numberguess;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 * The application-scoped Generator class is responsible for creating the random
 * number, via a producer method. It also exposes the maximum possible number
 * via a producer method.
 * 
 * The Generator is application scoped, so we don’t get a different random each
 * time.
 * 
 * @author Erik
 *
 */
@SuppressWarnings("serial")
@ApplicationScoped
public class Generator implements Serializable {

	private java.util.Random random = new java.util.Random(System.currentTimeMillis());
	
	private static final Logger log = Logger.getLogger(Generator.class.getName());

	private int maxNumber = 100;

	java.util.Random getRandom() {
		return random;
	}

	@Produces
	@Random
	int next() {
		// a number between 1 and 100
		int random = getRandom().nextInt(maxNumber - 1) + 1;
		log.info("created new random number: " + random);
		return random;
	}

	@Produces
	@MaxNumber
	int getMaxNumber() {
		log.info("get max number: " + maxNumber);
		return maxNumber;
	}
}
