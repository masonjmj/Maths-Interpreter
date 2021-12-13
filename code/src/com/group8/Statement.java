package com.group8;

import java.util.List;

abstract class Statement {
	// Visitor interface used to implement the visitor design pattern
	interface Visitor<T>{
		T visit(statementExpression statement);
		T visit(Print statement);
		T visit(VariableDeclaration statement);
		T visit(FunctionDeclaration statement);
		T visit(Plot statement);
		T visit(Block statement);
		T visit(If statement);
		T visit(While statement);
		T visit(Return statement);
	}

	static class statementExpression extends Statement {
		final Expression expression;

		statementExpression(Expression expression) {
			this.expression = expression;
		}

		// Overrides accept to fulfil extending Statement and so that the visit function can be
		// called in anything that implements the visitor interface
		@Override
		<T> T accept(Visitor<T> visitor) {
			return visitor.visit(this);
		}
	}

	static class Plot extends Statement{
		final Expression expression;

		Plot(Expression expression){this.expression = expression;}

		@Override
		<T> T accept(Visitor<T> visitor) {
			return visitor.visit(this);
		}
	}

	static class Print extends Statement {
		final Expression expression;

		Print(Expression expression) {
			this.expression = expression;
		}

		@Override
		<T> T accept(Visitor<T> visitor) {
			return visitor.visit(this);
		}
	}

	static class VariableDeclaration extends Statement {
		final Token identifier;
		final Expression expression;

		VariableDeclaration(Token identifier, Expression expression) {
			this.identifier = identifier;
			this.expression = expression;
		}

		@Override
		<T> T accept(Visitor<T> visitor) {
			return visitor.visit(this);
		}
	}

	static class FunctionDeclaration extends Statement {
		final Token identifier;
		final List<Token> parameters;
		final List<Statement> body;

		FunctionDeclaration(Token identifier, List<Token> parameters, List<Statement> body) {
			this.identifier = identifier;
			this.parameters = parameters;
			this.body = body;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}

	static class Block extends Statement {
		final List<Statement> statements;

		Block(List<Statement> statements) {
			this.statements = statements;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}

	static class If extends Statement {
		final Expression expression;
		final Statement thenStatement;
		final Statement elseStatement;

		If(Expression expression, Statement thenStatement, Statement elseStatement) {
			this.expression = expression;
			this.thenStatement = thenStatement;
			this.elseStatement = elseStatement;
		}
		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}

	static class While extends Statement {
		final Expression expression;
		final Statement body;

		While(Expression expression, Statement body) {
			this.expression = expression;
			this.body = body;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}

	static class Return extends Statement {
		final Token reservedWord;
		final Expression value;

		Return(Token reservedWord, Expression value) {
			this.reservedWord = reservedWord;
			this. value = value;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}

	abstract <T> T accept(Visitor<T> visitor);
}
