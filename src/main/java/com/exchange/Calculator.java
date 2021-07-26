package com.exchange;

import java.math.*;
import java.util.*;

import com.exchange.model.*;

/**
 * @author Devskiller
 */
class Calculator {

	private final ForexEngine forexEngine;

	Calculator(ForexEngine forexEngine) {
		this.forexEngine = forexEngine;
	}

	/**
	 * Calculates exchanged currency rate.
	 *
	 * @param amount    amount to convert
	 * @param convertTo currency to convert to
	 * @return exchanged amount
	 */
	Money exchange(Money amount, Currency convertTo) throws RateUnavailableException {
		try {
			BigDecimal exchangeRate = forexEngine.getExchangeRate(new Pair(amount.getCurrency(), convertTo));
			BigDecimal exchangedAmount = exchangeRate.multiply(amount.getAmount());
			return new Money(exchangedAmount, convertTo);
		} catch (RateUnavailableException e) {}
		try {
			BigDecimal exchangeRatePairInverse = forexEngine.getExchangeRate(new Pair(convertTo, amount.getCurrency()));
			BigDecimal exchangedAmount = amount.getAmount().divide(exchangeRatePairInverse);
			return new Money(exchangedAmount, convertTo);
		} catch (RateUnavailableException e) {}
		throw new RateUnavailableException();
	}

}
