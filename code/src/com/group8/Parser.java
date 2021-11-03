package com.group8;

import java.util.List;

public class Parser {
	 private final List<Token> tokens;
	 private int currentToken = 0;

	Parser(List<Token> tokens) {
		this.tokens = tokens;
	}

}
