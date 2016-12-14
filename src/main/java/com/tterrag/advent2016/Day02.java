package com.tterrag.advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day02 {

	private static final char[][] PAD = 
		{{ 0,   0,  '1',  0,   0 },
		 { 0,  '2', '3', '4',  0 },
		 {'5', '6', '7', '8', '9'},
		 { 0,  'A', 'B', 'C',  0 },
		 { 0,   0,  'D',  0,   0 }};
	
	private static int x = 0, y = 2;
	
	public static void main(String[] args) throws IOException {
		List<String> input = Files.readAllLines(Paths.get("day2.txt"));
		List<Character> code = new ArrayList<>();
		
		for (String s : input) {
			for (char c : s.toCharArray()) {
				switch (c) {
				case 'D':
					validateMove(1, 0);
					break;
				case 'U':
					validateMove(-1, 0);
					break;
				case 'L':
					validateMove(0, -1);
					break;
				case 'R':
					validateMove(0, 1);
					break;
				default:
					break;
				}
			}
			code.add(PAD[x][y]);
		}
		
		System.out.println(code.stream().map(c -> c.toString()).collect(Collectors.joining()));
	}

	private static void validateMove(int mx, int my) {
		int newX = x + mx;
		int newY = y + my;
		if (newX < 0 || newX >= PAD.length || PAD[newX][y] == 0) return;
		if (newY < 0 || newY >= PAD[x].length || PAD[x][newY] == 0) return;
		if (PAD[newX][newY] == 0) return;
		x = newX;
		y = newY;
	}

}
