package com.group8;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to represent variable scope
 */
public class Environment {
	final Environment enclosing;
	// Variables stored in a map so their value can be accessed by a key
	private final Map<String, Object> values = new HashMap<>();

	Environment() {
		enclosing = null;
	}

	Environment(Environment enclosing) {
		this.enclosing = enclosing; // Stores parent environment so that it can be accessed if a variable isn't found in this scope
	}

	public void define(String identifier, Object value) {
		values.put(identifier, value);
	}

	public Object get(Token identifier) {
		if (values.containsKey(identifier.lexeme)) {
			return values.get(identifier.lexeme);
		}

		if (enclosing != null) {
			return enclosing.get(identifier); // Checks parent environment (outer scope) if variable isn't found
		}

		throw new RuntimeError(identifier, "Undefined variable '" + identifier.lexeme + "'");
	}

	public Object get(String identifier){
		if (values.containsKey(identifier)) {
			return values.get(identifier);
		}

		if (enclosing != null) {
			return enclosing.get(identifier);
		}

		throw new RuntimeError("Undefined variable '" + identifier + "'");
	}

	public void assign(Token identifier, Object value) {
		if (values.containsKey(identifier.lexeme)) {
			values.put(identifier.lexeme, value);
			return;
		}
		if (enclosing != null) {
			enclosing.assign(identifier, value);
			return;
		}

		throw new RuntimeError(identifier, "Undefined variable '" + identifier.lexeme + "'");
	}

	public void assign(String identifier, Object value) {
		if (values.containsKey(identifier)) {
			values.put(identifier, value);
			return;
		}
		if (enclosing != null) {
			enclosing.assign(identifier, value);
			return;
		}

		throw new RuntimeError("Undefined variable '" + identifier + "'");
	}
}
