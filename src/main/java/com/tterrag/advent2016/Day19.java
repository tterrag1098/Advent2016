package com.tterrag.advent2016;

public class Day19 {

	public static void main(String[] args) {
		int n = 3014603;
		
		int pot = 1 << (31 - Integer.numberOfLeadingZeros(n));
		int l = n - pot;
		
		System.out.println("Part 1: " + ((2 * l) + 1));
		
		int po3 = (int) Math.pow(3, Math.floor(Math.log10(n) / Math.log10(3)));
		int w = n;
		int i = po3;
		if (po3 != n) {
			while (i++ < n) {
				w = (w + 1) % n;
				if (w > po3) {
					w++;
				}
			}
		}
		
		System.out.println("Part 2: " + w);
	}

}
