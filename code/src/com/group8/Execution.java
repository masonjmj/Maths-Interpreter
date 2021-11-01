package com.group8;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Execution {

	static private Stack<Token> operatorStack;
	static private Stack<Integer> numberStack;
	static private boolean identifierToAssign;


	static void execute(List<Token> tokens, Map<Integer, Object> symbolTable) {
		operatorStack = new Stack<>();
		numberStack = new Stack<>();
		identifierToAssign = false;

		if (tokens.size() > 1) {
			if (tokens.get(1) == Token.ASSIGNMENT) { // Kind of hacky; replace later
				identifierToAssign = true;
				Main.identifiers.put((String) symbolTable.get(0), shuntingYard(tokens, symbolTable));
			}else if(tokens.get(0) == Token.PLOT){
				Token temp = null;
				int i;
				int commas=0;
				for(i=0; i< tokens.size(); i++){
					temp = tokens.get(i);
					if(temp==Token.COMMA){
						commas++;
					}
				}
				for(i = 0; i<tokens.size(); i++){
					temp = tokens.get(i);
					if(temp == Token.IDENTIFIER) {
						break;
					}else{
						temp=null;
					}
				}
				if(temp == null){
					System.out.println(shuntingYard(tokens,symbolTable));
				}else if(commas==1){
					Main.identifiers.put((String) symbolTable.get(i), null);
					for (int a = 0; a <= (int) symbolTable.get(tokens.size() - 2); a++) {
						Main.identifiers.put((String) symbolTable.get(i), a);
						System.out.println("("+a+","+shuntingYard(tokens.subList(0, tokens.size() - 3), symbolTable, 2)+")");
					}
				}else if(commas==2){
					Main.identifiers.put((String) symbolTable.get(i), null);
					for (int a = (int) symbolTable.get(tokens.size() - 4); a <= (int) symbolTable.get(tokens.size() - 2); a++) {
						Main.identifiers.put((String) symbolTable.get(i), a);
						System.out.println("("+a+","+shuntingYard(tokens.subList(0, tokens.size() - 5), symbolTable, 2)+")");
					}
				}else{
					Main.identifiers.put((String) symbolTable.get(i), null);
					for (int a = (int) symbolTable.get(tokens.size() - 6); a <= (int) symbolTable.get(tokens.size() - 4); a+=(int) symbolTable.get(tokens.size() - 2)) {
						Main.identifiers.put((String) symbolTable.get(i), a);
						System.out.println("("+a+","+shuntingYard(tokens.subList(0, tokens.size() - 7), symbolTable, 2)+")");
					}
				}
			}else {
				System.out.println(shuntingYard(tokens, symbolTable));
			}
		} else {
			System.out.println(shuntingYard(tokens, symbolTable));
		}




	}

	private static int shuntingYard(List<Token> tokens, Map<Integer, Object> symbolTable){
		return shuntingYard(tokens, symbolTable, 0);
	}

	private static int shuntingYard(List<Token> tokens, Map<Integer, Object> symbolTable, int start) {
		int i = start;

		while (i < tokens.size()) {
			if (tokens.get(i) == Token.NUMBER) {
				numberStack.push((Integer) symbolTable.get(i)); // Push number from symbol table onto number stack
			} else if(tokens.get(i) == Token.IDENTIFIER){
				if (identifierToAssign) {
					identifierToAssign = false;
				} else {
					Integer value = (Integer) Main.identifiers.get((String) symbolTable.get(i));
					if (value == null) {
						throw new RuntimeException("Variable \"" + (String) symbolTable.get(i) + "\" has not been defined");
					} else {
						numberStack.push(value);
					}
				}
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
