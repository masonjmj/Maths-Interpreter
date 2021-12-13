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
			if (match(Token.Type.FUN)) {
				return functionDeclaration();
			}
			if (match(Token.Type.VAR)) {
				return variableDeclaration();
			}
			return statement();
		} catch (ParseError error) {
			advance();
			return null;
		}
	}

	private Statement functionDeclaration() {
		Token identifier = consume(Token.Type.IDENTIFIER, "Function name expected");
		consume(Token.Type.LEFT_BRACKET, "( expected after function name");
		List<Token> paramaters = new ArrayList<>();
		if (!check(Token.Type.RIGHT_BRACKET)) {
			do {
				paramaters.add(consume(Token.Type.IDENTIFIER, "Parameter name expected"));
			} while (match(Token.Type.COMMA));
		}
		consume(Token.Type.RIGHT_BRACKET, ") expected after parameter list");

		consume(Token.Type.LEFT_CURLY_BRACKET, "{ expected before function body");
		List<Statement> body = block();
		return new Statement.FunctionDecleration(identifier, paramaters, body);
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
		} else if (match(Token.Type.LEFT_CURLY_BRACKET)) {
			return new Statement.Block(block());
		} else if (match(Token.Type.IF)) {
			return ifStatement();
		} else if (match(Token.Type.WHILE)) {
			return whileStatement();
		} else if (match(Token.Type.RETURN)) {
			return returnStatement();
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
		//System.out.println(new AbstractSyntaxTreePrinter().print(expression));
		consume(Token.Type.SEMICOLON, "';' expected");
		return new Statement.statementExpression(expression);
	}

	private List<Statement> block() {
		List<Statement> statements = new ArrayList<>();
		while (!check(Token.Type.RIGHT_CURLY_BRACKET) && !atEndOfInput()) {
			statements.add(declaration());
		}
		consume(Token.Type.RIGHT_CURLY_BRACKET, "Unmatched {");
		return statements;
	}

	private Statement ifStatement() {
		consume(Token.Type.LEFT_BRACKET, "( expected after if");
		Expression expression = expression();
		consume(Token.Type.RIGHT_BRACKET, ") expected after if condition");

		Statement thenStatement = statement();
		Statement elseStatement = null;
		if (match(Token.Type.ELSE)) {
			elseStatement = statement();
		}
		return new Statement.If(expression, thenStatement, elseStatement);
	}

	private Statement whileStatement() {
		consume(Token.Type.LEFT_BRACKET, "( expected after while");
		Expression expression = expression();
		consume(Token.Type.RIGHT_BRACKET, ") expected after while condition");
		Statement body = statement();
		return new Statement.While(expression, body);
	}

	private Statement returnStatement() {
		Token reservedWord = previous();
		Expression value = null;
		if (!check(Token.Type.SEMICOLON)) {
			value = expression();
		}

		consume(Token.Type.SEMICOLON, "; expected after return");
		return new Statement.Return(reservedWord, value);
	}

	private Expression expression() {
		return assignment();
	}

	private Expression assignment() {
		Expression expression = or();

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

	private Expression or() {
		Expression expression = and();

		return orPrime(expression);
	}

	private Expression orPrime(Expression expression) {
		if (match(Token.Type.OR)) {
			Token operator = previous();
			Expression right = and();
			expression = new Expression.Logical(expression, operator, right);
			expression = orPrime(expression);
		}
		return expression;
	}

	private Expression and() {
		Expression expression = equality();

		return andPrime(expression);
	}

	private Expression andPrime(Expression expression) {
		if (match(Token.Type.AND)) {
			Token operator = previous();
			Expression right = equality();
			expression = new Expression.Logical(expression, operator, right);
			expression = andPrime(expression);
		}
		return expression;
	}

	private Expression equality() {
		Expression expression = comparison();

		return equalityPrime(expression);
	}

	private Expression equalityPrime(Expression expression) {
		if (match(Token.Type.EQUAL)) {
			Token operator = previous();
			Expression right = comparison();
			expression = new Expression.Binary(expression, operator, right);
			expression = equalityPrime(expression);
		}
		return expression;
	}

	private Expression comparison() {
		Expression expression = sum();
		return comparisonPrime(expression);
	}

	private Expression comparisonPrime(Expression expression) {
		if (match(Token.Type.GREATER, Token.Type.LESS, Token.Type.GREATER_EQUAL, Token.Type.LESS_EQUAL)) {
			Token operator = previous();
			Expression right = sum();
			expression = new Expression.Binary(expression, operator, right);
			expression = comparisonPrime(expression);
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
		Expression expression = exponential();
		return termPrime(expression);
	}

	private Expression termPrime(Expression expression) {
		if (match(Token.Type.TIMES, Token.Type.DIVIDE)) {
			Token operator = previous();
			Expression right = exponential();
			expression = new Expression.Binary(expression, operator, right);
			expression = termPrime(expression);
		}
		return expression;
	}

	private Expression exponential() {
		Expression expression = unary();
		return exponentialPrime(expression);
	}

	private Expression exponentialPrime(Expression expression) {
		if (match(Token.Type.POWER)) {
			Token operator = previous();
			Expression right = unary();
			expression = new Expression.Binary(expression, operator, right);
			expression = exponentialPrime(expression);
		}
		return expression;
	}

	private Expression unary() {
		if (match(Token.Type.MINUS, Token.Type.NOT)) {
			Token operator = previous();
			Expression right = unary();
			return new Expression.Unary(operator, right);
		}
		return call();
	}

	private Expression call() {
		Expression expression = factor();

		return callPrime(expression);
	}

	private Expression callPrime(Expression expression) {
		if (match(Token.Type.LEFT_BRACKET)) {
			expression = completeCall(expression);
			expression = callPrime(expression);
		}
		return expression;
	}

	private Expression completeCall(Expression callingExpression) {
		List<Expression> arguments = new ArrayList<>();
		if (!check(Token.Type.RIGHT_BRACKET)) {
			do {
				arguments.add(expression());
			} while (match(Token.Type.COMMA));
		}
		Token closingBracket = consume(Token.Type.RIGHT_BRACKET, ") expected after arguments");

		return new Expression.Call(callingExpression, closingBracket, arguments);
	}

	private Expression factor() {
		if (match(Token.Type.NULL)) {
			return new Expression.Literal(null);
		}
		if (match(Token.Type.FALSE)){
			return new Expression.Literal(false);
		}
		if (match(Token.Type.TRUE)) {
			return new Expression.Literal(true);
		}
		if (match(Token.Type.NUMBER, Token.Type.STRING)) {
			return new Expression.Literal(previous().literal);
		}
		if (match(Token.Type.LEFT_BRACKET)) {
			Expression expression = expression();
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
