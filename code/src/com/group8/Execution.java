package com.group8;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Execution {

	static private Stack<Token> operatorStack;
	static private Stack<Integer> numberStack;


	static void execute(List<Token> tokens, Map<Integer, Integer> symbolTable) {
		operatorStack = new Stack<>();
		numberStack = new Stack<>();

		System.out.println(shuntingYard(tokens, symbolTable));
	}

	private static int shuntingYard(List<Token> tokens, Map<Integer, Integer> symbolTable) {
		int i = 0;

		while (i < tokens.size()) {
			if (tokens.get(i) == Token.NUMBER) {
				numberStack.push(symbolTable.get(i)); // Push number from symbol table onto number stack
			} else if (tokens.get(i) == Token.PLUS || tokens.get(i) == Token.MINUS) {
				while (!operatorStack.isEmpty() && (operatorStack.peek() != Token.LEFT_BRACKET)) {
					calculate();
				}
				operatorStack.push(tokens.get(i));
			} else if (tokens.get(i) == Token.TIMES || tokens.get(i) == Token.DIVIDE) {
				while (!operatorStack.isEmpty() && (operatorStack.peek() != Token.LEFT_BRACKET) && (operatorStack.peek() != Token.PLUS) && (operatorStack.peek() != Token.MINUS)) {
					calculate();
				}
				operatorStack.push(tokens.get(i));
			} else if (tokens.get(i) == Token.LEFT_BRACKET) {
				operatorStack.push(tokens.get(i));
			} else if (tokens.get(i) == Token.RIGHT_BRACKET) {
				while (!operatorStack.isEmpty() && (operatorStack.peek() != Token.LEFT_BRACKET)) {
					calculate();
				}
				if (operatorStack.peek() == Token.LEFT_BRACKET) {
					operatorStack.pop();
				}
			}
			i++;
		}

		while (!operatorStack.isEmpty()) {
			calculate();
		}

		return numberStack.peek();
	}

	private static void calculate() {
		Token operatorToken = operatorStack.pop();

		int operand2 = numberStack.pop();
		int operand1 = numberStack.pop();

		switch (operatorToken) {
			case PLUS:
				numberStack.push(operand1 + operand2);
				break;
			case MINUS:
				numberStack.push(operand1 - operand2);
				break;
			case TIMES:
				numberStack.push(operand1 * operand2);
				break;
			case DIVIDE:
				numberStack.push(operand1 / operand2);
				break;
		}
	}
}
