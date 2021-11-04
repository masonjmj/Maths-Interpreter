package com.group8;

import java.util.ArrayList;
import java.util.List;

public class Parser {
	private static class ParseError extends RuntimeException {}
	private final List<Token> tokens;
	private int currentToken = 0;

	Parser(List<Token> tokens) {
		this.tokens = tokens;
	}

	public List<Statement> parse(){
		List<Statement> statements = new ArrayList<>();

		while (!atEndOfInput()) {
			statements.add(declaration());
		}
		return statements;
	}

	private Statement declaration() {
		try {
			if (match(Token.Type.VAR)) {
				return variableDeclaration();
			}
			return statement();
		} catch (ParseError error) {
			advance();
			return null;
		}
	}

	private Statement variableDeclaration() {
		Token identifier = consume(Token.Type.IDENTIFIER, "Expected a variable name");

		Expression expression = null;
		if (match(Token.Type.ASSIGNMENT)) {
			expression = expression();
		}

		consume(Token.Type.SEMICOLON, "';' expected");
		return new Statement.VariableDeclaration(identifier, expression);
	}

	private Statement statement() {
		if (match(Token.Type.PRINT)) {
			return printStatement();
		}else if(match(Token.Type.PLOT)){
			return plotStatement();
		}

		return expressionStatement();
	}

	private Statement plotStatement(){
		Expression expression = expression();
		consume(Token.Type.SEMICOLON, "';' expected");
		return new Statement.Plot(expression);
	}

	private Statement printStatement() {
		Expression value = expression();
		consume(Token.Type.SEMICOLON, "';' expected");
		return new Statement.Print(value);
	}

	private Statement expressionStatement() {
		Expression expression = expression();
		consume(Token.Type.SEMICOLON, "';' expected");
		return new Statement.statementExpression(expression);
	}

	private Expression expression() {
		return assignment();
	}

	private Expression assignment() {
		Expression expression = sum();

		if (match(Token.Type.ASSIGNMENT)) {
			Token equals = previous();
			Expression value = assignment();

			if (expression instanceof Expression.Variable) {
				Token identifier = ((Expression.Variable) expression).identifier;
				return new Expression.Assignment(identifier, value);
			}
			throw error(equals, "Invalid assignment");
		}
		return expression;
	}

	private Expression sum() {
		Expression expression = term();
		return sumPrime(expression);
	}

	private Expression sumPrime(Expression expression) {
		if (match(Token.Type.PLUS, Token.Type.MINUS)) {
			Token operator = previous();
			Expression right = term();
			expression = new Expression.Binary(expression, operator, right);
			expression = sumPrime(expression);
		}
		return expression;
	}

	private Expression term() {
		Expression expression = unary();
		return termPrime(expression);
	}

	private Expression termPrime(Expression expression) {
		if (match(Token.Type.TIMES, Token.Type.DIVIDE)) {
			Token operator = previous();
			Expression right = unary();
			expression = new Expression.Binary(expression, operator, right);
			expression = termPrime(expression);
		}
		return expression;
	}

	private Expression unary() {
		if (match(Token.Type.MINUS)) {
			Token operator = previous();
			Expression right = unary();
			return new Expression.Unary(operator, right);
		}
		return factor();
	}

	private Expression factor() {
		if (match(Token.Type.NULL)) {
			return new Expression.Literal(null);
		}
		if (match(Token.Type.NUMBER, Token.Type.STRING)) {
			return new Expression.Literal(previous().literal);
		}
		if (match(Token.Type.LEFT_BRACKET)) {
			Expression expression = sum();
			consume(Token.Type.RIGHT_BRACKET, "Mismatched brackets");
			return new Expression.Group(expression);
		}
		if (match(Token.Type.IDENTIFIER)) {
			return new Expression.Variable(previous());
		}

		throw error(lookAhead(), "Expression expected");
	}

	// Helpers

	private Token lookAhead() {
		return tokens.get(currentToken);
	}

	private Token previous() {
		return tokens.get(currentToken - 1);
	}

	private boolean atEndOfInput() {
		return lookAhead().type == Token.Type.EOF;
	}

	private boolean check(Token.Type type) {
		if (atEndOfInput()) {
			return false;
		}
		return lookAhead().type == type;
	}

	private Token advance() {
		if (!atEndOfInput()) {
			currentToken++;
		}
		return previous();
	}

	private boolean match(Token.Type... types) {
		for (Token.Type type : types) {
			if (check(type)) {
				advance();
				return true;
			}
		}
		return false;
	}

	private Token consume(Token.Type type, String message) {
		if (check(type)) {
			return advance();
		}

		throw error(lookAhead(), message);
	}

	private ParseError error(Token token, String message) {
		Main.error(token, message);
		return new ParseError();
	}
}
