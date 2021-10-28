package com.group8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {
	static void analyse(String input) {
		List<Token> tokens = new ArrayList<>(100);
		Map<Integer, Object> symbolTable = new HashMap<>();
		String numberCompositionBuffer = "";
		String stringCompositionBuffer = "";

		int tokenIndex = 0;

		for (int stringIndex = 0; stringIndex < input.length(); ++stringIndex) {

			switch (input.charAt(stringIndex)) {
				case ' ':
					break;
				case '+':
					tokens.add(tokenIndex++, Token.PLUS);
					break;
				case '-':
					tokens.add(tokenIndex++, Token.MINUS);
					break;
				case '*':
					tokens.add(tokenIndex++, Token.TIMES);
					break;
				case '/':
					tokens.add(tokenIndex++, Token.DIVIDE);
					break;
				case '(':
					tokens.add(tokenIndex++, Token.LEFT_BRACKET);
					break;
				case ')':
					tokens.add(tokenIndex++, Token.RIGHT_BRACKET);
					break;
				case '=':
					tokens.add(tokenIndex++, Token.ASSIGNMENT);
					break;
				default:
					if (Character.isDigit(input.charAt(stringIndex))) {
						while (stringIndex < input.length() && Character.isDigit(input.charAt(stringIndex))) {
							numberCompositionBuffer = numberCompositionBuffer + input.charAt(stringIndex);
							++stringIndex;
						}

						symbolTable.put(tokenIndex, Integer.valueOf(numberCompositionBuffer));
						numberCompositionBuffer = "";
						tokens.add(tokenIndex++, Token.NUMBER);

						--stringIndex;

					} else if (Character.isAlphabetic(input.charAt(stringIndex))){
						while (stringIndex < input.length() && Character.isLetterOrDigit(input.charAt(stringIndex))) {
							stringCompositionBuffer = stringCompositionBuffer + input.charAt(stringIndex);
							++stringIndex;
						}

						symbolTable.put(tokenIndex, stringCompositionBuffer);
						stringCompositionBuffer = "";
						tokens.add(tokenIndex++, Token.IDENTIFIER);
						--stringIndex;



//						switch (stringCompositionBuffer) {
//							case "<-":
//								tokens.add(tokenIndex++, Token.ASSIGNMENT);
//								break;
//							default:
//								System.out.println("Unknown Token: \"" + stringCompositionBuffer + "\"");
//						}
				}
					else{
						System.out.printf("Unexpected character '%c'%n", input.charAt(stringIndex));
						return;
					}
			}
		}

		for ( Token element : tokens) {
			System.out.print(element + " ");
		}
		System.out.print("\n");

//		System.out.println(Parser.parse(tokens));

		if (Parser.parse(tokens)) {
			Execution.execute(tokens, symbolTable);
		}
	}
}

enum Token{
	PLUS,
	MINUS,
	TIMES,
	DIVIDE,
	LEFT_BRACKET,
	RIGHT_BRACKET,
	ASSIGNMENT,
	NUMBER,
	IDENTIFIER
}