package com.group8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Lexer {
	private static final Map<String, Token.Type> reservedWords =
			Map.of(
					"plot", Token.Type.PLOT,
					"var", Token.Type.VAR);

	private final String code;
	private final List<Token> tokens = new ArrayList<>();
	private int startOfLexeme = 0;
	private int currentPosition = 0;

	Lexer(String code) {
		this.code = code;
	}

	List<Token> analyse() {
		while (!atEndOfInput()) {
			startOfLexeme = currentPosition;
			findToken();
		}

		tokens.add(new Token(Token.Type.EOF, "", null));
		return tokens;
	}

	private void findToken() {
		char character = advance();
		switch (character) {
			case ' ':
			case '\r':
			case '\t':
				break; // Ignore whitespace
			case '\n':
				break;
			case '+':
				addToken(Token.Type.PLUS);
				break;
			case '-':
				addToken(Token.Type.MINUS);
				break;
			case '*':
				addToken(Token.Type.TIMES);
				break;
			case '/':
				if (match('/')) {
					while (lookAhead() != '\n' && !atEndOfInput()) {
						advance();
					}
				} else {
					addToken(Token.Type.DIVIDE);
				}
				break;
			case '(':
				addToken(Token.Type.LEFT_BRACKET);
				break;
			case ')':
				addToken(Token.Type.RIGHT_BRACKET);
				break;
			case '"':
				string();
				break;
			default:
				if (Character.isDigit(character)) {
					number();
				} else if (Character.isLetter(character)) {
					identifier();
				} else {
					System.err.println("Unexpected character.");
				}
				break;
		}
	}

	// Helper functions

	private boolean atEndOfInput() {
		return currentPosition >= code.length();
	}

	// Consume and return character
	private char advance() {
		return code.charAt(currentPosition++);
	}

	// Consume character only if it matches
	private boolean match(char charToMatch) {
		if (atEndOfInput()) return false;
		if (code.charAt(currentPosition) != charToMatch) return false;
		currentPosition++;
		return true;
	}

	// Return character without consuming it
	private char lookAhead() {
		return lookAhead(0);
	}

	private char lookAhead(int howFar) {
		if (currentPosition + howFar >= code.length()) {
			return '\0';
		}
		return code.charAt(currentPosition + howFar);
	}

	private void addToken(Token.Type type) {
		addToken(type, null);
	}

	private void addToken(Token.Type type, Object literal) {
		String lexemeText = code.substring(startOfLexeme, currentPosition);
		tokens.add(new Token(type, lexemeText, literal));
	}

	// Match a number with digits and then optionally a decimal and more digits.
	private void number() {
		while (Character.isDigit(lookAhead())) {
			advance();
		}

		if (lookAhead() == '.' && Character.isDigit(lookAhead(1))) {
			advance();

			while (Character.isDigit(lookAhead())) {
				advance();
			}
		}

		addToken(Token.Type.NUMBER, Double.parseDouble(code.substring(startOfLexeme, currentPosition)));
	}

	private void string() {
		while (lookAhead() != '"' && !atEndOfInput()) {
//			if (lookAhead()) {
//
//			}

			advance();
		}

		if (atEndOfInput()) {
			System.err.println("Mismatched \"");
			return;
		}

		advance(); // Makes sure to include final "

		addToken(Token.Type.STRING, code.substring(startOfLexeme+1, currentPosition -1));
	}

	private void identifier() {
		while (Character.isLetterOrDigit(lookAhead())) {
			advance();
		}

		// Check if it matches a reserved word. If not then it's an identifier
		String temp = code.substring(startOfLexeme, currentPosition);
		Token.Type type = reservedWords.get(temp);
		if (type == null) {
			type = Token.Type.IDENTIFIER;
		}
		addToken(type);
	}
}
