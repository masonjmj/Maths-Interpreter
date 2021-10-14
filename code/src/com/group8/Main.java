package com.group8;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
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
}
