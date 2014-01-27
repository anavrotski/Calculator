package com.example.calculator;

import java.text.ParseException;

/**
 * @author Andrei. Class is calling non-static parsing method as static.
 */
public final class Calculator {
	/**
	 * Private constructor.
	 */
	private Calculator() {
		super();
	}

	/**
	 * Precision of calculation.
	 */
	private static final int PRECISION = 8;

	/**
	 * @param text
	 *            String representation of mathematical equation.
	 * @return Result of calculation.
	 */
	public static String calculate(final String text) {
		Parser parser = new Parser(PRECISION);
		String result = "";

		try {
			parser.parse(text);
			result = parser.evaluate();
		} catch (ParseException e) {
			e.printStackTrace();
			System.err.println("Something wrong with equation.");
		}
		return result;
	}
}
