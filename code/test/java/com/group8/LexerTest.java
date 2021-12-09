package com.group8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

	@Test
	@DisplayName("Lexical Analysis")
	void analyse() {
		List<Token> tokens = new ArrayList<>();
		tokens.add(new Token(Token.Type.PLUS, "+", null, 1));
		tokens.add(new Token(Token.Type.MINUS, "-", null, 1));
		tokens.add(new Token(Token.Type.TIMES, "*", null, 1));
		tokens.add(new Token(Token.Type.DIVIDE, "/", null, 1));
		tokens.add(new Token(Token.Type.LEFT_BRACKET, "(", null, 1));
		tokens.add(new Token(Token.Type.RIGHT_BRACKET, ")", null, 1));
		tokens.add(new Token(Token.Type.ASSIGNMENT, "<-", null, 1));

		tokens.add(new Token(Token.Type.NUMBER, "1", 1.0, 1));
		tokens.add(new Token(Token.Type.IDENTIFIER, "test", null, 1));
		tokens.add(new Token(Token.Type.STRING, "\"test\"", "test", 1));
		tokens.add(new Token(Token.Type.NULL, "null", null, 1));

		tokens.add(new Token(Token.Type.PLOT, "plot", null, 1));
		tokens.add(new Token(Token.Type.VAR, "var", null, 1));
		tokens.add(new Token(Token.Type.PRINT, "print", null, 1));
		tokens.add(new Token(Token.Type.SEMICOLON, ";", null, 1));

		tokens.add(new Token(Token.Type.EOF, "", null, 1));


		Lexer lexer = new Lexer("+ - * / ( ) <- 1 test \"test\" null plot var print ;");

		assertEquals(tokens, lexer.analyse());

	}
}