package com.group8;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static Map<String, Object> identifiers = new HashMap<>();
	public static void main(String[] args) {


		testing();
		String input;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("> ");
			input = scanner.nextLine().trim();

			if (!input.isBlank()) {
				Lexer.analyse(input);
			} else {
				break;
			}
		}
	}



	// Testing
	public static void testing() {
		// Replace with proper unit testing

		// Basic operations
		System.out.println("TEST: 5 Expected output: 5");
		Lexer.analyse("5");

		System.out.println("TEST: 5+5 Expected output: 10");
		Lexer.analyse("5+5");

		System.out.println("TEST: 5-5 Expected output: 0");
		Lexer.analyse("5-5");

		System.out.println("TEST: 5*5 Expected output: 25");
		Lexer.analyse("5*5");

		System.out.println("TEST: 5/5 Expected output: 1");
		Lexer.analyse("5/5");

		System.out.println("TEST: 5+(4-2+3) Expected output: 10");
		Lexer.analyse("5+(4-2+3)");

		System.out.println("TEST: 5   +   5 Expected output: 10");
		Lexer.analyse("5   +   5"); // Whitespace ignored

		System.out.println("TEST: x = 5 Expected output: No exceptions");
		Lexer.analyse("x = 5");

		System.out.println("TEST: a = 19+7 Expected output: No exceptions");
		Lexer.analyse("a = 19+7");

		System.out.println("TEST: x - 10 Expected output: -5");
		Lexer.analyse("x - 10");

		// Errors
		System.out.println("TEST: 5 + Expected output: Syntax error");
		Lexer.analyse("5 +");

		System.out.println("TEST: 5 + ( 5 Expected output: Syntax error");
		Lexer.analyse("5 + ( 5");

		try {
			System.out.println("TEST: 2147483648 + 1 Expected output: Runtime Exception");
			Lexer.analyse("2147483648 + 1");
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("TEST: 5x Expected output: Syntax error");
		Lexer.analyse("5x");





	}
}
