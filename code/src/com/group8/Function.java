package com.group8;

import java.util.List;

public class Function {

	private final Statement.FunctionDeclaration declaration;
	private final Environment closure;

	Function() {
		this(null, null);
	}

	Function(Statement.FunctionDeclaration declaration, Environment closure) {
		this.declaration = declaration;
		this.closure = closure;
	}

	int numberOfArguments(){
		return declaration.parameters.size();
	}

	Object call(Interpreter interpreter, List<Object> arguments){
		Environment environment = new Environment(closure);
		for (int i = 0; i < declaration.parameters.size(); i++) {
			environment.define(declaration.parameters.get(i).lexeme, arguments.get(i));
		}
		try {
			interpreter.executeBlock(declaration.body, environment);
		} catch (Return returnValue) {
			return returnValue.value;
		}
		return null;
	}
}
