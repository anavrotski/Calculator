package com.example.calculator;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Locale;
import java.util.Stack;
import java.util.StringTokenizer;

public class Parser {
	/** list of available functions */
	private final String[] FUNCTIONS = { "abs", "acos", "asin", "atan", "cos",
			"cosh", "exp", "log", "log10", "^", "sin", "sinh", "sqrt", "tan",
			"tanh" };
	/** list of available operators */
	private final String OPERATORS = "+-*/";
	/** separator of arguments */
	private final String SEPARATOR = ",";
	/** settings for numbers formatting */
	private final NumberFormat numberFormat = NumberFormat.getInstance(Locale
			.getDefault());
	/** temporary stack that holds operators, functions and brackets */
	private final Stack<String> stackOperations = new Stack<String>();
	/** stack for holding expression converted to reversed polish notation */
	private final Stack<String> stackRPN = new Stack<String>();
	/** stack for holding the calculations result */
	private final Stack<String> stackAnswer = new Stack<String>();

	public Parser(final int precision) {
		setPrecision(precision);
	}

	public final void setPrecision(final int precision) {
		numberFormat.setMinimumFractionDigits(precision);
	}

	public final int getPrecision() {
		return numberFormat.getMinimumFractionDigits();
	}

	public final void parse(String expression) throws ParseException {
		/* cleaning stacks */
		stackOperations.clear();
		stackRPN.clear();

		/*
		 * make some preparations: remove spaces; handle unary + and -, handle
		 * degree character
		 */
		expression = expression.replace(" ", "")
				.replace("°", "*" + Double.toString(Math.PI) + "/180")
				.replace("(-", "(0-").replace(",-", ",0-").replace("(+", "(0+")
				.replace(",+", ",0+");
		if (expression.charAt(0) == '-' || expression.charAt(0) == '+') {
			expression = "0" + expression;
		}
		/* splitting input string into tokens */
		StringTokenizer stringTokenizer = new StringTokenizer(expression,
				OPERATORS + SEPARATOR + "()", true);

		/* loop for handling each token - shunting-yard algorithm */
		while (stringTokenizer.hasMoreTokens()) {
			String token = stringTokenizer.nextToken();
			if (isSeparator(token)) {
				while (!stackOperations.empty()
						&& !isOpenBracket(stackOperations.lastElement())) {
					stackRPN.push(stackOperations.pop());
				}
			} else if (isOpenBracket(token)) {
				stackOperations.push(token);
			} else if (isCloseBracket(token)) {
				while (!stackOperations.empty()
						&& !isOpenBracket(stackOperations.lastElement())) {
					stackRPN.push(stackOperations.pop());
				}
				stackOperations.pop();
				if (!stackOperations.empty()
						&& isFunction(stackOperations.lastElement())) {
					stackRPN.push(stackOperations.pop());
				}
			} else if (isNumber(token)) {
				stackRPN.push(token);
			} else if (isOperator(token)) {
				while (!stackOperations.empty()
						&& isOperator(stackOperations.lastElement())
						&& getPrecedence(token) <= getPrecedence(stackOperations
								.lastElement())) {
					stackRPN.push(stackOperations.pop());
				}
				stackOperations.push(token);
			} else if (isFunction(token)) {
				stackOperations.push(token);
			} else {
				throw new ParseException("Unrecognized token", 0);
			}
		}
		while (!stackOperations.empty()) {
			stackRPN.push(stackOperations.pop());
		}

		/* reverse stack */
		Collections.reverse(stackRPN);
	}

	public final String evaluate() throws ParseException {
		if (!stackRPN.contains("var")) {
			return evaluate(0);
		}
		throw new ParseException("Unrecognized token: var", 0);
	}

	public final String evaluate(final double variableValue)
			throws ParseException {
		/* check if is there something to evaluate */
		if (stackRPN.empty()) {
			return "";
		}

		/* clean answer stack */
		stackAnswer.clear();

		/* get the clone of the RPN stack for further evaluating */
		@SuppressWarnings("unchecked")
		Stack<String> stackRPN = (Stack<String>) this.stackRPN.clone();

		/* evaluating the RPN expression */
		while (!stackRPN.empty()) {
			String token = stackRPN.pop();
			if (isNumber(token)) {
				stackAnswer.push(token);
			} else if (isOperator(token)) {
				Double a = numberFormat.parse(stackAnswer.pop()).doubleValue();
				Double b = numberFormat.parse(stackAnswer.pop()).doubleValue();
				if (token.equals("+")) {
					stackAnswer.push(numberFormat.format(b + a));
				} else if (token.equals("-")) {
					stackAnswer.push(numberFormat.format(b - a));
				} else if (token.equals("*")) {
					stackAnswer.push(numberFormat.format(b * a));
				} else if (token.equals("/")) {
					stackAnswer.push(numberFormat.format(b / a));
				}
			} else if (isFunction(token)) {
				Double a = numberFormat.parse(stackAnswer.pop()).doubleValue();
				if (token.equals("abs")) {
					stackAnswer.push(numberFormat.format(Math.abs(a)));
				} else if (token.equals("acos")) {
					stackAnswer.push(numberFormat.format(Math.acos(a)));
				} else if (token.equals("asin")) {
					stackAnswer.push(numberFormat.format(Math.asin(a)));
				} else if (token.equals("atan")) {
					stackAnswer.push(numberFormat.format(Math.atan(a)));
				} else if (token.equals("cos")) {
					stackAnswer.push(numberFormat.format(Math.acos(a)));
				} else if (token.equals("cosh")) {
					stackAnswer.push(numberFormat.format(Math.cosh(a)));
				} else if (token.equals("exp")) {
					stackAnswer.push(numberFormat.format(Math.exp(a)));
				} else if (token.equals("log")) {
					stackAnswer.push(numberFormat.format(Math.log(a)));
				} else if (token.equals("sin")) {
					stackAnswer.push(numberFormat.format(Math.sin(a)));
				} else if (token.equals("sinh")) {
					stackAnswer.push(numberFormat.format(Math.sinh(a)));
				} else if (token.equals("sqrt")) {
					stackAnswer.push(numberFormat.format(Math.sqrt(a)));
				} else if (token.equals("tan")) {
					stackAnswer.push(numberFormat.format(Math.tan(a)));
				} else if (token.equals("tanh")) {
					stackAnswer.push(numberFormat.format(Math.tanh(a)));
				} else if (token.equals("^")) {
					Double b = numberFormat.parse(stackAnswer.pop())
							.doubleValue();
					stackAnswer.push(numberFormat.format(Math.pow(b, a)));
				}
			}
		}

		if (stackAnswer.size() > 1) {
			throw new ParseException("Some operator is missing", 0);
		}

		return stackAnswer.pop();
	}

	public Number evaluateNumber() throws ParseException {
		return numberFormat.parse(evaluate());
	}

	public String format(final NumberFormat number) {
		return numberFormat.format(number);
	}

	private boolean isNumber(final String token) {
		try {
			Double.parseDouble(token);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean isFunction(final String token) {
		for (String item : FUNCTIONS) {
			if (item.equals(token)) {
				return true;
			}
		}
		return false;
	}

	private boolean isSeparator(final String token) {
		return token.equals(SEPARATOR);
	}

	private boolean isOpenBracket(final String token) {
		return token.equals("(");
	}

	private boolean isCloseBracket(final String token) {
		return token.equals(")");
	}

	private boolean isOperator(final String token) {
		return OPERATORS.contains(token);
	}

	private byte getPrecedence(final String token) {
		if (token.equals("+") || token.equals("-")) {
			return 1;
		}
		return 2;
	}
}