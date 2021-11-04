package com.group8;

abstract class Statement {
	interface Visitor<T>{
		T visit(statementExpression statement);
		T visit(Print statement);
		T visit(VariableDeclaration statement);
		T visit(Plot statement);
	}

	static class statementExpression extends Statement {
		final Expression expression;

		statementExpression(Expression expression) {
			this.expression = expression;
		}

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

	abstract <T> T accept(Visitor<T> visitor);
}
