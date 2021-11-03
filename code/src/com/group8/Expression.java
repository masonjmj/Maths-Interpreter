package com.group8;

abstract class Expression {

	// Implements visitor pattern so that calls to the tree can find out what type of
	// node they are looking at.
	interface Visitor<T> {
		T visit(Binary expression);
		T visit(Group expression);
		T visit(Literal expression);
		T visit(Unary expression);
		T visit(Variable expression);
		T visit(Assignment expression);
	}

	static class Binary extends Expression {
		final Expression left;
		final Token operator;
		final Expression right;

		Binary(Expression left, Token operator, Expression right) {
			this.left = left;
			this.operator = operator;
			this.right = right;
		}

		@Override
		<T> T accept(Visitor<T> visitor) {
			return visitor.visit(this);
		}
	}

	static class Group extends Expression {
		final Expression expression;

		Group(Expression expression) {
			this.expression = expression;
		}

		@Override
		<T> T accept(Visitor<T> visitor) {
			return visitor.visit(this);
		}
	}

	static class Literal extends Expression {
		final Object value;

		Literal(Object value) {
			this.value = value;
		}

		@Override
		<T> T accept(Visitor<T> visitor) {
			return visitor.visit(this);
		}
	}

	static class Unary extends Expression {
		final Token operator;
		final Expression right;

		Unary(Token operator, Expression right) {
			this.operator = operator;
			this.right = right;
		}

		@Override
		<T> T accept(Visitor<T> visitor) {
			return visitor.visit(this);
		}
	}

	static class Variable extends Expression {
		final Token identifier;

		Variable(Token identifier) {
			this.identifier = identifier;
		}

		@Override
		<T> T accept(Visitor<T> visitor) {
			return visitor.visit(this);
		}
	}

	static class Assignment extends Expression {
		final Token identifier;
		final Expression value;

		Assignment(Token identifier, Expression value) {
			this.identifier = identifier;
			this.value = value;
		}

		@Override
		<T> T accept(Visitor<T> visitor) {
			return visitor.visit(this);
		}
	}


	abstract <T> T accept(Visitor<T> visitor);
}

