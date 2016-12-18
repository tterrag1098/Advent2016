package com.tterrag.advent2016;

public class Day16 {

	private static final String input = "01111001100111011";
	
	public static void main(String[] args) {
		System.out.println("Part 1: " + findChecksum(input, 272));
		System.out.println("Part 2: " + findChecksum(input, 35651584));
	}
	
	private static String findChecksum(String in, final int maxSize) {
		StringBuilder input = new StringBuilder(in);
		
		while (input.length() < maxSize) {
			String reversed = new StringBuilder(input).reverse().toString();
			reversed = reversed.replace('1', 'a').replace('0', '1').replace('a', '0');
			input.append('0').append(reversed);
		}
		
		String checksum = input.substring(0, maxSize);

		while (checksum.length() % 2 == 0) {
			StringBuilder temp = new StringBuilder();
			for (int i = 0; i < checksum.length() - 1; i += 2) {
				char repl = checksum.charAt(i) == checksum.charAt(i + 1) ? '1' : '0';
				temp.append(repl);
			}
			checksum = temp.toString();
		}
		
		return checksum;
	}
}
