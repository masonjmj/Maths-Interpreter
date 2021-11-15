package com.group8;

import java.util.HashMap;
import java.util.Map;

public class Environment {
	private final Map<String, Object> values = new HashMap<>();

	public void define(String identifier, Object value) {
		values.put(identifier, value);
	}

	public Object get(Token identifier) {
		if (values.containsKey(identifier.lexeme)) {
			return values.get(identifier.lexeme);
		}

		throw new RuntimeError(identifier, "Undefined variable '" + identifier.lexeme + "'");
	}

	public void assign(Token identifier, Object value) {
		if (values.containsKey(identifier.lexeme)) {
			values.put(identifier.lexeme, value);
			return;
		}

		throw new RuntimeError(identifier, "Undefined variable '" + identifier.lexeme + "'");
	}
}
