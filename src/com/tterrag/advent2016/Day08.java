package com.tterrag.advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day08 {

    private static final int[][] screen = new int[6][50];

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("day8.txt"));

        for (String s : lines) {
            String[] words = s.split(" ");
            if ("rect".equals(words[0])) {
                String[] dims = words[1].split("x");
                rect(Integer.parseInt(dims[0]), Integer.parseInt(dims[1]));
            } else {
                int rowcol = Integer.parseInt(words[2].split("=")[1]);
                int amnt = Integer.parseInt(words[4]);
                if ("column".equals(words[1])) {
                    rotateColumn(rowcol, amnt);
                } else {
                    rotateRow(rowcol, amnt);
                }
            }
        }
        
        System.out.println("Part 1: " + Arrays.stream(screen).mapToInt(arr -> Arrays.stream(arr).sum()).sum());
        Arrays.stream(screen).forEach(arr -> System.out.println(Arrays.toString(arr))); // read it yerself
    }

    private static void rect(int w, int h) {
        for (int x = h; x > 0; x--) {
            for (int y = w; y > 0; y--) {
                screen[x - 1][y - 1] = 1;
            }
        }
    }

    private static void rotateRow(int row, int amnt) {
        int[] oldrow = screen[row];
        int[] newrow = new int[50];
        for (int i = 0; i < newrow.length; i++) {
            newrow[(i + amnt) % newrow.length] = oldrow[i];
        }
        screen[row] = newrow;
    }

    private static void rotateColumn(int col, int amnt) {
        for (; amnt > 0; amnt--) {
            for (int i = screen.length - 1; i > 0; i--) {
                int shift = (i + 1) % screen.length;
                int tmp = screen[i][col];
                screen[i][col] = screen[shift][col];
                screen[shift][col] = tmp;
            }
        }
    }
}
