package com.group8;

import java.util.List;

public class Parser {

	static List<Token> tokens;
	static Token lookAhead;
	static int currentToken;
	static boolean success;

	static boolean match(Token token) {
		boolean result;

		result = token == lookAhead;

//		if (result) {
//			System.out.println("Token " + currentToken + " matched " + token);
//		} else {
//			System.out.println("Token " + currentToken + " DOESN'T match " + token);
//		}

		return result;
	}

	static void advance(int level) {

		++currentToken;

		if (currentToken < tokens.size()) {
			lookAhead = tokens.get(currentToken);
		} else {
			lookAhead = null;
		}


//		System.out.println("advance() called at level " + level + " with next token " + lookAhead);
	}

	static boolean parse(List<Token> tokensToParse) {
		tokens = tokensToParse;
		currentToken = 0;
		lookAhead = tokens.get(currentToken);
		success = true;

		expression(0);

		return success;
	}

	static void expression(int level) {
//		System.out.println("expression() called at level " + level);
		term(level + 1);
		expression_p(level + 1);
	}

	static void expression_p(int level) {
//		System.out.println("expression_p() called at level " + level);
		if (match(Token.PLUS) || match(Token.MINUS)) {
			advance(level + 1);
			term(level + 1);
			expression_p(level + 1);
		}
	}

	static void term(int level) {
//		System.out.println("term() called at level " + level);
		factor(level + 1);
		term_p(level + 1);
	}

	static void term_p(int level) {
//		System.out.println("term_p() called at level " + level);
		if (match(Token.TIMES) || match(Token.DIVIDE)) {
			advance(level + 1);
			factor(level + 1);
			term_p(level + 1);
		}
	}

	static void factor(int level) {
//		System.out.println("factor() called at level " + level);
		if (match(Token.NUMBER)) {
			advance(level + 1);
		} else if (match(Token.LEFT_BRACKET)) {
			advance(level + 1);
			expression(level + 1);
			if (match(Token.RIGHT_BRACKET)) {
				advance(level + 1);
			} else {
				System.out.println("SYNTAX ERROR: Mismatched bracket at token " + currentToken);
				success = false;
			}
		} else {
			System.out.println("SYNTAX ERROR: Number expected at token " + currentToken);
			success = false;
		}
	}
}
