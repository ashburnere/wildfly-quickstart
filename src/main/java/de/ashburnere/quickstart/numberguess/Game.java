package de.ashburnere.quickstart.numberguess;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * 
 * The final bean in the application is the session-scoped Game class. This is
 * the primary entry point of the application. It’s responsible for setting up
 * or resetting the game, capturing and validating the user’s guess and
 * providing feedback to the user with a FacesMessage. We’ve used the
 * post-construct lifecycle method to initialize the game by retrieving a random
 * number from the @RandomInstance<Integer> bean.
 * 
 * You’ll notice that we’ve also added the @Named annotation to this class. This
 * annotation is only required when you want to make the bean accessible to a
 * JSF view via EL (i.e. #{game})
 * 
 * http://localhost:8080/wildfly-quickstart/home.jsf
 * 
 * @author Erik
 *
 */
@SuppressWarnings("serial")
@Named
@SessionScoped
public class Game implements Serializable {

	private static final Logger log = Logger.getLogger(Game.class.getName());
	
	/**
	 * The number that the user needs to guess
	 */
	private int number;

	/**
	 * The users latest guess
	 */
	private int guess;

	/**
	 * The smallest number guessed so far (so we can track the valid guess range).
	 */
	private int smallest;

	/**
	 * The largest number guessed so far
	 */
	private int biggest;

	/**
	 * The number of guesses remaining
	 */
	private int remainingGuesses;

	/**
	 * The maximum number we should ask them to guess
	 */
	@Inject
	@MaxNumber
	private int maxNumber;

	/**
	 * The random number to guess
	 */
	@Inject
	@Random
	Instance<Integer> randomNumber;

	public Game() {
	}

	public int getNumber() {
		return number;
	}

	public int getGuess() {
		return guess;
	}

	public void setGuess(int guess) {
		this.guess = guess;
	}

	public int getSmallest() {
		return smallest;
	}

	public int getBiggest() {
		return biggest;
	}

	public int getRemainingGuesses() {
		return remainingGuesses;
	}

	/**
	 * Check whether the current guess is correct, and update the biggest/smallest
	 * guesses as needed. Give feedback to the user if they are correct.
	 */
	public void check() {
		if (guess > number) {
			biggest = guess - 1;
		} else if (guess < number) {
			smallest = guess + 1;
		} else if (guess == number) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Correct!"));
		}
		remainingGuesses--;
	}

	/**
	 * Reset the game, by putting all values back to their defaults, and getting a
	 * new random number. We also call this method when the user starts playing for
	 * the first time using {@linkplain PostConstruct @PostConstruct} to set the
	 * initial values.
	 */
	@PostConstruct
	public void reset() {
		log.info("Reset game");
		this.smallest = 0;
		this.guess = 0;
		this.remainingGuesses = 10;
		this.biggest = maxNumber;
		this.number = randomNumber.get();
	}

	/**
	 * A JSF validation method which checks whether the guess is valid. It might not
	 * be valid because there are no guesses left, or because the guess is not in
	 * range.
	 *
	 */
	public void validateNumberRange(FacesContext context, UIComponent toValidate, Object value) {
		log.info("validateNumberRange");
		if (remainingGuesses <= 0) {
			FacesMessage message = new FacesMessage("No guesses left!");
			context.addMessage(toValidate.getClientId(context), message);
			((UIInput) toValidate).setValid(false);
			return;
		}
		int input = (Integer) value;

		if (input < smallest || input > biggest) {
			((UIInput) toValidate).setValid(false);

			FacesMessage message = new FacesMessage("Invalid guess");
			context.addMessage(toValidate.getClientId(context), message);
		}
	}
}
