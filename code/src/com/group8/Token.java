package com.group8;

public class Token {
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
	final String lexeme;
	final Object literal;
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
