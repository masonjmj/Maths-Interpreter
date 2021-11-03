package com.group8;

public class Token {
	public static enum Type{
		//Operators
		PLUS,
		MINUS,
		TIMES,
		DIVIDE,
		LEFT_BRACKET,
		RIGHT_BRACKET,
		ASSIGNMENT,


		// Literals
		NUMBER,
		IDENTIFIER,
		STRING,
		NULL,

		// Reserved Words
		PLOT,
		VAR,
		PRINT,
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
