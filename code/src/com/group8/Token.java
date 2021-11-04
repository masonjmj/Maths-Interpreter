package com.group8;

import java.util.Objects;

public class Token {
	public enum Type{
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Token token = (Token) o;
		return line == token.line && type == token.type && lexeme.equals(token.lexeme) && Objects.equals(literal, token.literal);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, lexeme, literal, line);
	}

	@Override
	public String toString() {
		return type + " " + lexeme + " " + literal;
	}
}
