package com.tterrag.advent2016;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

public class Day18 {

	@RequiredArgsConstructor
	private static class Row {
		final boolean[] traps;
		
		public Row(String input) {
			traps = new boolean[input.length()];
			for (int i = 0; i < input.length(); i++) {
				traps[i] = input.charAt(i) == '^';
			}
		}
		
		public boolean get(int i) {
			return i >= 0 && i < traps.length && traps[i];
		}
		
		public Row next() {
			boolean[] newrow = new boolean[traps.length];
			for (int i = 0; i < newrow.length; i++) {
				newrow[i] = 
						(get(i - 1) && get(i) && !get(i + 1)) ||
						(get(i) && get(i + 1) && !get(i - 1)) ||
						(get(i - 1) && !get(i) && !get(i + 1))||
						(!get(i - 1) && !get(i) && get(i + 1));
			}
			return new Row(newrow);
		}
		
		public int safe() {
			int ret = 0;
			for (int i = 0; i < traps.length; i++) {
				if (!get(i)) ret++;
			}
			return ret;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < traps.length; i++) {
				sb.append(get(i) ? '^' : '.');
			}
			return sb.toString();
		}
	}
	
	public static void main(String[] args) {
		Row root = new Row(".^.^..^......^^^^^...^^^...^...^....^^.^...^.^^^^....^...^^.^^^...^^^^.^^.^.^^..^.^^^..^^^^^^.^^^..^");
		
		List<Row> part1 = new ArrayList<>();
		part1.add(root);
		List<Row> part2 = new ArrayList<>(part1);
		
		while (part2.size() < 400000) {
			if (part1.size() < 40) {
				part1.add(part1.get(part1.size() - 1).next());
			}
			part2.add(part2.get(part2.size() - 1).next());
		}
		
		System.out.println("Part 1: " + part1.stream().mapToInt(Row::safe).sum());
		System.out.println("Part 2: " + part2.stream().mapToInt(Row::safe).sum());
	}
}
