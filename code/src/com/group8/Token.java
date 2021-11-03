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
		COMMA,

		// Literals
		NUMBER,
		IDENTIFIER,

		// Reserved Words
		PLOT,
		VAR,
		STRING,

		// End Of File
		EOF
	}

	final Type type;
	final String lexeme;
	final Object literal;


	Token(Type type, String lexeme, Object literal) {
		this.type = type;
		this.lexeme = lexeme;
		this.literal = literal;
	}
	@Override
	public String toString() {
		return type + " " + lexeme + " " + literal;
	}
}
