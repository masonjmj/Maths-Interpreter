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

		statement(0);

		return success;
	}

	static void statement(int level) {
		if (match(Token.IDENTIFIER)) { // Replace later
			System.out.println("Assignment");
			assignment(level + 1);
		}else if(match(Token.PLOT)){
			System.out.println("Plotting");
			plot(level+1);
		} else {
			System.out.println("Expression");
			expression(level + 1);
		}
	}

	static void assignment(int level) {
		if (match(Token.IDENTIFIER)) {
			advance(level + 1);
			assignment_p(level + 1);
		}
	}

	static void assignment_p(int level) {
		if (match(Token.ASSIGNMENT)) {
			advance(level + 1);
			expression(level + 1);
		}
	}

	static void expression(int level) {
//		System.out.println("expression() called at level " + level);
		term(level + 1);
		expression_p(level + 1);
	}

	static void expression_p(int level) {
//		System.out.println("expression_p() called at level " + level);
		if (match(Token.ASSIGNMENT)) {
			System.out.println("Assignment operator not valid in an expression");
			success = false;
			advance(level + 1);
			term(level + 1);
			expression_p(level + 1);

		} else if (match(Token.PLUS) || match(Token.MINUS)) {
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

	static void plot(int level){
		advance(level+1);
		if(match(Token.LEFT_BRACKET)){
			advance(level+1);
			expression(level+1);
		}else{
			System.out.println("SYNTAX ERROR: Expected bracket");
			success = false;
		}
		if(match(Token.COMMA)){
			advance(level+1);
			if(!match(Token.NUMBER)){
				System.out.println("Expected number");
				success = false;
			}
			advance(level+1);
			if (match(Token.COMMA)) {
				advance(level+1);
				if(!match(Token.NUMBER)){
					System.out.println("Expected number");
					success = false;
				}
				advance(level+1);

				if (match(Token.COMMA)) {
					advance(level+1);
					if(!match(Token.NUMBER)){
						System.out.println("Expected number");
						success = false;
					}
					advance(level+1);
				}
			}
		}

		if(!match(Token.RIGHT_BRACKET)){
			System.out.println(("SYNTAX ERROR: expected bracket"));
			success = false;
		}

	}

	static void factor(int level) {
//		System.out.println("factor() called at level " + level);
		if (match(Token.NUMBER)) {
			advance(level + 1);
		} else if (match(Token.IDENTIFIER)){
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
			System.out.println("SYNTAX ERROR: Number or variable expected at token " + currentToken);
			success = false;
		}
	}
}
