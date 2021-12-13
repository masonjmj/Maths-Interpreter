package com.group8;

/**
 * A class to store all the relevant information for a token
 */
public class Token {

	/**
	 * An enum for all the possible token types
	 */
	public enum Type{
		//Operators
		PLUS,
		MINUS,
		TIMES,
		DIVIDE,
		POWER,
		NOT,
		EQUAL,
		GREATER,
		GREATER_EQUAL,
		LESS,
		LESS_EQUAL,
		LEFT_BRACKET,
		RIGHT_BRACKET,
		LEFT_CURLY_BRACKET,
		RIGHT_CURLY_BRACKET,
		ASSIGNMENT,
		COMMA,


		// Literals
		NUMBER,
		IDENTIFIER,
		STRING,
		TRUE,
		FALSE,
		NULL,

		// Reserved Words
		PLOT,
		VAR,
		FUN,
		PRINT,
		AND,
		OR,
		IF,
		ELSE,
		WHILE,
		RETURN,
		SEMICOLON,

		// End Of File
		EOF
	}

	final Type type;
	/**
	 * The entered string that was analysed as this token
	 */
	final String lexeme;

	/**
	 * The literal value of the corresponding Java type if this token is a literal
	 */
	final Object literal;

	/**
	 * The line this token occurred on
	 */
	final int line;


	Token(Type type, String lexeme, Object literal, int line) {
		this.type = type;
		this.lexeme = lexeme;
		this.literal = literal;
		this.line = line;
	}
	@Override
	public String toString() {
		return type + " " + lexeme + " " + literal;
	}
}
