package com.group8;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static final Interpreter interpreter = new Interpreter();
	public static boolean encounteredError = false;


	public static void main(String[] args) throws IOException {
		if (args.length > 1) {
			System.out.println("Too many arguments.");
			System.exit(64); // UNIX standard exit code: bad usage
		} else if (args.length == 1) {
			runFromFile(args[0]);
		} else {
			runInteractively();
		}
	}


	/**
	 * Runs the interpreter on a file
	 *
	 * @param path The path to the file to be interpreted
	 * @throws IOException
	 */
	private static void runFromFile(String path) throws IOException {
		run(Files.readString(Paths.get(path)));
		if (encounteredError) {
			System.exit(65);
		}
	}

	/**
	 * Runs the interpreter as an interactive command line prompt
	 */
	private static void runInteractively() {
		Scanner input = new Scanner(System.in);

		while (true) {
			System.out.print("> ");
			String line = input.nextLine();

			if (line.isBlank()) {
				break;
			} else {
				run(line);
				encounteredError = false;
			}
		}
	}


	/**
	 * Calls the lexer, parser and interpreter on the source code
	 *
	 * @param code A string of the soruce code
	 */
	private static void run(String code) {
		Lexer lexer = new Lexer(code);
		List<Token> tokens = lexer.analyse();
		if (!encounteredError) {
			Parser parser = new Parser(tokens);
			List<Statement> statements = parser.parse();
			if (!encounteredError) {
				interpreter.interpret(statements);
			}
		}
	}

	static void error(String message) {
		System.err.println(message);
		encounteredError = true;
	}

	static void error(Token token, String message) {
		error(token.line, message);
	}

	/**
	 * Prints out any error and sets {@link #encounteredError} to true, preventing the next step
	 * from running on code containing errors.
	 *
	 * @param line The line the error occured on
	 * @param message The error message provided
	 */
	static void error(int line, String message) {
		System.err.println(message + " on line " + line);
		encounteredError = true;
	}

}


	// Testing
//	public static void testing() {
//		// Replace with proper unit testing
//
//		// Basic operations
//		System.out.println("TEST: 5 Expected output: 5");
//		Lexer.analyse("5");
//
//		System.out.println("TEST: 5+5 Expected output: 10");
//		Lexer.analyse("5+5");
//
//		System.out.println("TEST: 5-5 Expected output: 0");
//		Lexer.analyse("5-5");
//
//		System.out.println("TEST: 5*5 Expected output: 25");
//		Lexer.analyse("5*5");
//
//		System.out.println("TEST: 5/5 Expected output: 1");
//		Lexer.analyse("5/5");
//
//		System.out.println("TEST: 5+(4-2+3) Expected output: 10");
//		Lexer.analyse("5+(4-2+3)");
//
//		System.out.println("TEST: 5   +   5 Expected output: 10");
//		Lexer.analyse("5   +   5"); // Whitespace ignored
//
//		System.out.println("TEST: x = 5 Expected output: No exceptions");
//		Lexer.analyse("x = 5");
//
//		System.out.println("TEST: a = 19+7 Expected output: No exceptions");
//		Lexer.analyse("a = 19+7");
//
//		System.out.println("TEST: x - 10 Expected output: -5");
//		Lexer.analyse("x - 10");
//
//		// Errors
//		System.out.println("TEST: 5 + Expected output: Syntax error");
//		Lexer.analyse("5 +");
//
//		System.out.println("TEST: 5 + ( 5 Expected output: Syntax error");
//		Lexer.analyse("5 + ( 5");
//
//		try {
//			System.out.println("TEST: 2147483648 + 1 Expected output: Runtime Exception");
//			Lexer.analyse("2147483648 + 1");
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		System.out.println("TEST: 5x Expected output: Syntax error");
//		Lexer.analyse("5x");
//
//
//
//
//
//	}
//}
