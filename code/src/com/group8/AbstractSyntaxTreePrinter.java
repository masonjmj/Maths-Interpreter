package com.group8;

public class AbstractSyntaxTreePrinter implements Expression.Visitor<String> {
	public String print(Expression expression) {
		return expression.accept(this);
	}

	@Override
	public String visit(Expression.Binary expression) {
		return group(expression.operator.lexeme, expression.left, expression.right);
	}

	@Override
	public String visit(Expression.Group expression) {
		return group("group", expression.expression);
	}

	@Override
	public String visit(Expression.Literal expression) {
		if (expression.value == null) {
			return "null";
		}
		return expression.value.toString();
	}

	@Override
	public String visit(Expression.Unary expression) {
		return group(expression.operator.lexeme, expression.right);
	}

	@Override
	public String visit(Expression.Variable expression) {
		return expression.identifier.lexeme;
	}

	@Override
	public String visit(Expression.Assignment expression) {
		return group(expression.identifier.lexeme, expression.value);
	}

	@Override
	public String visit(Expression.Logical expression) {
		return group(expression.operator.lexeme, expression.left, expression.right);
	}

	@Override
	public String visit(Expression.Call expression) {
		return null;
	}

	// Helpers

	private String group(String string, Expression... expressions) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("(").append(string);
		for (Expression expression : expressions) {
			stringBuilder.append(" ");
			stringBuilder.append(expression.accept(this));
		}
		stringBuilder.append(")");

		return stringBuilder.toString();
	}
}
