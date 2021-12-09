package com.group8;

import java.util.ArrayList;
import java.util.List;

public class Interpreter implements Expression.Visitor<Object>, Statement.Visitor<Void> {

	final Environment globalEnvironment = new Environment();
	private Environment environment = globalEnvironment;
	private plotGraph graph = new plotGraph();

	Interpreter() {
		globalEnvironment.define("sin", new Function() {
			@Override
			public int numberOfArguments() {
				return 1;
			}

			@Override
			public Object call(Interpreter interpreter, List<Object> arguments) {
				if (!(arguments.get(0) instanceof Number)) {
					throw new RuntimeError("Incompatible type for sin function");
				} else {
					if (arguments.get(0) instanceof Integer) {
						return Math.sin((int) arguments.get(0));
					}
					return Math.sin((double) arguments.get(0));
				}
			}
		});

		globalEnvironment.define("cos", new Function() {
			@Override
			public int numberOfArguments() {
				return 1;
			}

			@Override
			public Object call(Interpreter interpreter, List<Object> arguments) {
				if (!(arguments.get(0) instanceof Number)) {
					throw new RuntimeError("Incompatible type for cos function");
				} else {
					if (arguments.get(0) instanceof Integer) {
						return Math.cos((int) arguments.get(0));
					}
					return Math.cos((double) arguments.get(0));
				}
			}
		});

		globalEnvironment.define("tan", new Function() {
			@Override
			public int numberOfArguments() {
				return 1;
			}

			@Override
			public Object call(Interpreter interpreter, List<Object> arguments) {
				if (!(arguments.get(0) instanceof Number)) {
					throw new RuntimeError("Incompatible type for tan function");
				} else {
					if (arguments.get(0) instanceof Integer) {
						return Math.tan((int) arguments.get(0));
					}
					return Math.tan((double) arguments.get(0));
				}
			}
		});

		globalEnvironment.define("arcsin", new Function() {
			@Override
			public int numberOfArguments() {
				return 1;
			}

			@Override
			public Object call(Interpreter interpreter, List<Object> arguments) {
				if (!(arguments.get(0) instanceof Number)) {
					throw new RuntimeError("Incompatible type for arcsin function");
				} else {
					if (arguments.get(0) instanceof Integer) {
						return Math.asin((int) arguments.get(0));
					}
					return Math.asin((double) arguments.get(0));
				}
			}
		});

		globalEnvironment.define("arccos", new Function() {
			@Override
			public int numberOfArguments() {
				return 1;
			}

			@Override
			public Object call(Interpreter interpreter, List<Object> arguments) {
				if (!(arguments.get(0) instanceof Number)) {
					throw new RuntimeError("Incompatible type for arccos function");
				} else {
					if (arguments.get(0) instanceof Integer) {
						return Math.acos((int) arguments.get(0));
					}
					return Math.acos((double) arguments.get(0));
				}
			}
		});

		globalEnvironment.define("arctan", new Function() {
			@Override
			public int numberOfArguments() {
				return 1;
			}

			@Override
			public Object call(Interpreter interpreter, List<Object> arguments) {
				if (!(arguments.get(0) instanceof Number)) {
					throw new RuntimeError("Incompatible type for arctan function");
				} else {
					if (arguments.get(0) instanceof Integer) {
						return Math.atan((int) arguments.get(0));
					}
					return Math.atan((double) arguments.get(0));
				}
			}
		});

		globalEnvironment.define("radians", new Function() {
			@Override
			public int numberOfArguments() {
				return 1;
			}

			@Override
			public Object call(Interpreter interpreter, List<Object> arguments) {
				if (!(arguments.get(0) instanceof Number)) {
					throw new RuntimeError("Incompatible type for radians function");
				} else {
					if (arguments.get(0) instanceof Integer) {
						return Math.toRadians((int) arguments.get(0));
					}
					return Math.toRadians((double) arguments.get(0));
				}
			}
		});

		globalEnvironment.define("degrees", new Function() {
			@Override
			public int numberOfArguments() {
				return 1;
			}

			@Override
			public Object call(Interpreter interpreter, List<Object> arguments) {
				if (!(arguments.get(0) instanceof Number)) {
					throw new RuntimeError("Incompatible type for degrees function");
				} else {
					if (arguments.get(0) instanceof Integer) {
						return Math.toDegrees((int) arguments.get(0));
					}
					return Math.toDegrees((double) arguments.get(0));
				}
			}
		});

		globalEnvironment.define("minPoint", new Function() {
			@Override
			public int numberOfArguments() {
				return 2;
			}

			@Override
			public Object call(Interpreter interpreter, List<Object> arguments) {
				graph.setMinPoint(((Number) arguments.get(0)).doubleValue(), ((Number) arguments.get(1)).doubleValue());
				return(arguments.get(0));
			}
		});

		globalEnvironment.define("maxPoint", new Function() {
			@Override
			public int numberOfArguments() {return 2;}

			@Override
			public Object call(Interpreter interpreter, List<Object> arguments) {
				graph.setMaxPoint(((Number) arguments.get(0)).doubleValue(), ((Number) arguments.get(1)).doubleValue());
				return(arguments.get(0));

			}
		});

		globalEnvironment.define("setIncrement", new Function() {
			@Override
			public int numberOfArguments() {return 2;}

			@Override
			public Object call(Interpreter interpreter, List<Object> arguments) {
				graph.setIncrement(((Number) arguments.get(0)).doubleValue(), ((Number) arguments.get(1)).doubleValue());
				return(arguments.get(0));

			}
		});

		globalEnvironment.define("PI", Math.PI);
	}

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
	public Void visit(Statement.FunctionDecleration statement) {
		Function function = new Function(statement, environment);
		environment.define(statement.identifier.lexeme, function);
		return null;
	}

	@Override
	public Void visit(Statement.Plot statement) {
		graph.points.clear();
		//Expression.Variable var = findVariable(statement.expression);
		Environment prevEnv = environment;
		environment = new Environment(environment);
		environment.define("x", graph.minPoint.getX());
		double value = ((Number) environment.get("x")).doubleValue();
		double max = graph.getMaxX();
		double resolution = 0.1;
		for(double i = value;i<max; i+= resolution){
			//System.out.println("("+i+","+evaluate(statement.expression)+")");
		graph.addPoint(i, ((Number) evaluate(statement.expression)).doubleValue());
		environment.assign("x", ((Number) environment.get("x")).doubleValue() + resolution);
		}
		environment.assign("x", value);
		graph.initUI();
		graph.setVisible(true);
		environment = prevEnv;
		return null;
	}

	@Override
	public Void visit(Statement.Block statement) {
		executeBlock(statement.statements, new Environment(environment));
		return null;
	}

	@Override
	public Void visit(Statement.If statement) {
		if (isTruthy(evaluate(statement.expression))) {
			execute(statement.thenStatement);
		} else if (statement.elseStatement != null) {
			execute(statement.elseStatement);
		}
		return null;
	}

	@Override
	public Void visit(Statement.While statement) {
		while (isTruthy(evaluate(statement.expression))) {
			execute(statement.body);
		}
		return null;
	}

	@Override
	public Void visit(Statement.Return statement) {
		Object value = null;
		if (statement.value != null) {
			value = evaluate(statement.value);
		}
		throw new Return(value);
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
				if (!(right instanceof Number)) {
					throw new RuntimeError(expression.operator, "Incompatible type for unary minus operator");
				}
				if (right instanceof Integer) {
					return -(int)right;
				}
				return -(double)right;
			case NOT:
				if (!(right instanceof Boolean)) {
					throw new RuntimeError(expression.operator, "Incompatible type for unary not operator");
				}
				return !(boolean) right;
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
	public Object visit(Expression.Logical expression) {
		Object left = evaluate(expression.left);

		if (expression.operator.type == Token.Type.OR) {
			if (isTruthy(left)) {
				return left;
			}
		} else {
			if (!isTruthy(left)) {
				return left;
			}
		}
		return evaluate(expression.right);
	}

	@Override
	public Object visit(Expression.Call expression) {
		Object callingExpression = evaluate(expression.callingExpression);

		List<Object> arguments = new ArrayList<>();
		for (Expression argument : expression.arguments) {
			arguments.add(evaluate(argument));
		}

		if (!(callingExpression instanceof Function)) {
			throw new RuntimeError(expression.closingBracket, "Expression is not a callable function");
		}

		Function function = (Function) callingExpression;
		if (arguments.size() != function.numberOfArguments()) {
			throw new RuntimeError(expression.closingBracket, function.numberOfArguments() + " arguments expected");
		}
		return function.call(this, arguments);
	}

	@Override
	public Object visit(Expression.Binary expression) {
		Object left = evaluate(expression.left);
		Object right = evaluate(expression.right);

		switch (expression.operator.type) {
			case EQUAL:
				if (left == null) {
					return right == null;
				}else if(left instanceof Number && right instanceof Number) {
					if(left instanceof Integer && right instanceof Integer){
						return (int) left == (int) right;
					}else {
						return ((Number) left).doubleValue() == ((Number) right).doubleValue();
					}
				}
				return left.equals(right);
			case GREATER:
				checkNumericOperands(expression.operator, left, right);
				if (left instanceof Integer && right instanceof Integer) {
					return (int)left > (int)right;
				}
				return ((Number) left).doubleValue() > ((Number) right).doubleValue();
			case LESS:
				checkNumericOperands(expression.operator, left, right);
				if (left instanceof Integer && right instanceof Integer) {
					return (int)left < (int)right;
				}
				return ((Number) left).doubleValue() < ((Number) right).doubleValue();
			case GREATER_EQUAL:
				checkNumericOperands(expression.operator, left, right);
				if (left instanceof Integer && right instanceof Integer) {
					return (int)left >= (int)right;
				}
				return ((Number) left).doubleValue() >= ((Number) right).doubleValue();
			case LESS_EQUAL:
				checkNumericOperands(expression.operator, left, right);
				if (left instanceof Integer && right instanceof Integer) {
					return (int)left <= (int)right;
				}
				return ((Number) left).doubleValue() <= ((Number) right).doubleValue();
			case PLUS:
				if ((left instanceof Number) && (right instanceof Number)) {
					if (left instanceof Integer && right instanceof Integer) {
						return (int)left + (int) right;
					}
					return ((Number)left).doubleValue() + ((Number) right).doubleValue();
				}
				if (left instanceof String && right instanceof String) {
					return (String)left + (String)right;
				}
				throw new RuntimeError(expression.operator, "Incompatible types for operator '" + expression.operator.lexeme + "'");
			case MINUS:
				checkNumericOperands(expression.operator, left, right);
				if (left instanceof Integer && right instanceof Integer) {
					return (int)left - (int)right;
				}
				return ((Number) left).doubleValue() - ((Number) right).doubleValue();
			case TIMES:
				checkNumericOperands(expression.operator, left, right);
				if (left instanceof Integer && right instanceof Integer) {
					return (int)left * (int)right;
				}
				return ((Number) left).doubleValue() * ((Number) right).doubleValue();
			case DIVIDE:
				checkNumericOperands(expression.operator, left, right);
				if (left instanceof Integer && right instanceof Integer) {
					return (int)left / (int)right;
				}
				return ((Number) left).doubleValue() / ((Number) right).doubleValue();
			case POWER:
				checkNumericOperands(expression.operator, left, right);
				if (left instanceof Integer && right instanceof Integer) {
					return Math.pow((int) left, (int) right);
				}
				return Math.pow(((Number) left).doubleValue(),((Number) right).doubleValue());
		}
		return null;
	}

	public void interpret(List<Statement> statements) {
		try {
			for (Statement statement : statements) {
				execute(statement);
			}
		} catch (RuntimeError error) {
			if (error.token != null) {
				Main.error(error.token, error.getMessage());
			} else{
				Main.error(error.getMessage());
			}

		}

	}

	public void executeBlock(List<Statement> statements, Environment environment) {
		Environment previous = this.environment;
		try{
			this.environment = environment;
			for (Statement statement : statements) {
				execute(statement);
			}
		} finally {
			this.environment = previous;
		}
	}

	private void execute(Statement statement) {
		statement.accept(this);
	}

	private Object evaluate(Expression expression) {
		return expression.accept(this);
	}

	private void checkNumericOperands(Token operator, Object left, Object right) {
		if ((left instanceof Number) && (right instanceof Number)) {
			return;
		}
		throw new RuntimeError(operator, "Incompatible type(s) for '" + operator.lexeme + "'");
	}

	private boolean isTruthy(Object object) {
		if (object instanceof Boolean) return (boolean) object;
		return false;
	}

	private Expression.Variable findVariable(Expression expression){
		if(expression instanceof Expression.Variable) {
			return (Expression.Variable) expression;
		}
		if(expression instanceof Expression.Literal) {
			return null;
		}
		if(expression instanceof Expression.Group){
			return findVariable(((Expression.Group) expression).expression);
		}
		if(expression instanceof Expression.Unary){
			return findVariable(((Expression.Unary) expression).right);
		}
		if(expression instanceof Expression.Binary){
			Expression l = findVariable(((Expression.Binary) expression).left);
			if(l != null){
				return (Expression.Variable) l;
			}else{
				return findVariable(((Expression.Binary) expression).right);
			}
		}

		return null;

	}
}
