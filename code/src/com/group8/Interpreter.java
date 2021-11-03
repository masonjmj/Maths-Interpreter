package com.group8;

import java.util.List;

public class Interpreter implements Expression.Visitor<Object>, Statement.Visitor<Void>{

	private Environment environment = new Environment();

	@Override
	public Void visit(Statement.statementExpression statement) {
		evaluate(statement.expression);
		return null;
	}

	@Override
	public Void visit(Statement.Print statement) {
		System.out.println(evaluate(statement.expression));
		return null;
	}

	@Override
	public Void visit(Statement.VariableDeclaration statement) {
		Object value = null;

		if (statement.expression != null) {
			value = evaluate(statement.expression);
		}

		environment.define(statement.identifier.lexeme, value);
		return null;
	}

	@Override
	public Object visit(Expression.Literal expression) {
		return expression.value;
	}

	@Override
	public Object visit(Expression.Group expression) {
		return evaluate(expression.expression);
	}

	@Override
	public Object visit(Expression.Unary expression) {
		Object right = evaluate(expression.right);

		switch (expression.operator.type) {
			case MINUS:
				if (!(right instanceof Double)) {
					throw new RuntimeError(expression.operator, "Incompatible type for unary operator");
				}
				return -(double)right;
		}

		return null;
	}

	@Override
	public Object visit(Expression.Variable expression) {
		return environment.get(expression.identifier);
	}

	@Override
	public Object visit(Expression.Assignment expression) {
		Object value = evaluate(expression.value);
		environment.assign(expression.identifier, value);
		return value;
	}

	@Override
	public Object visit(Expression.Binary expression) {
		Object left = evaluate(expression.left);
		Object right = evaluate(expression.right);

		switch (expression.operator.type) {
			case PLUS:
				if (left instanceof Double && right instanceof Double) {
					return (double)left + (double)right;
				}
				if (left instanceof String && right instanceof String) {
					return (String)left + (String)right;
				}
				throw new RuntimeError(expression.operator, "Incompatible types for operator '" + expression.operator.lexeme + "'");
			case MINUS:
				checkNumericOperands(expression.operator, left, right);
				return (double)left - (double)right;
			case TIMES:
				checkNumericOperands(expression.operator, left, right);
				return (double)left * (double)right;
			case DIVIDE:
				checkNumericOperands(expression.operator, left, right);
				return (double)left / (double)right;
		}
		return null;
	}

	public void interpret(List<Statement> statements) {
		try {
			for (Statement statement : statements) {
				execute(statement);
			}
		} catch (RuntimeError error) {
			Main.error(error.token, error.getMessage());
		}

	}

	private void execute(Statement statement) {
		statement.accept(this);
	}

	private Object evaluate(Expression expression) {
		return expression.accept(this);
	}

	private void checkNumericOperands(Token operator, Object left, Object right) {
		if (left instanceof Double && right instanceof Double) {
			return;
		}
		throw new RuntimeError(operator, "Incompatible type(s) for '" + operator.lexeme + "'");
	}
}
