package com.tterrag.advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

public class Day03 {

    @RequiredArgsConstructor
    private static class Triangle {
        private final int a, b, c;
        
        public boolean isValid() {
            return a + b > c && b + c > a && c + a > b;
        }
    }
    
    private static List<int[]> cols = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
        List<String> inputs = Files.readAllLines(Paths.get("day3.txt"));
        
        int valid = 0;
        
        for (String s : inputs) {
            String[] strs = s.trim().split("\\s+");
            cols.add(new int[] {Integer.parseInt(strs[0]), Integer.parseInt(strs[1]), Integer.parseInt(strs[2])});
        }
        
        for (int i = 0; i < cols.size(); i += 3) {
            int[] row1 = cols.get(i);
            int[] row2 = cols.get(i + 1);
            int[] row3 = cols.get(i + 2);
            int[][] square = new int[][]{row1, row2, row3};
            for (int j = 0; j < 3; j++) {
                Triangle tri = new Triangle(square[0][j], square[1][j], square[2][j]);
                if (tri.isValid()) {
                    valid++;
                }
            }
        }
        
        System.out.println(valid);
    }
}
