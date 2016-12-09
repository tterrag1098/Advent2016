package com.tterrag.advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day09 {

    private static final Pattern MARKER_PATTERN = Pattern.compile("\\((\\d+x\\d+)\\)");
    
    public static void main(String[] args) throws IOException {
        String input = Files.readAllLines(Paths.get("day9.txt")).get(0);

        // Part 1 - Actually decompress the string
        Matcher matcher = MARKER_PATTERN.matcher(input);
        int start = 0;
        while (matcher.find(start)) {
            String[] instruction = matcher.group(1).split("x");
            int chars = Integer.parseInt(instruction[0]);
            int repeat = Integer.parseInt(instruction[1]);
            String toRepeat = input.substring(matcher.end(), matcher.end() + chars);
            String replacement = toRepeat;
            for (int i = 1; i < repeat; i++) {
                replacement += toRepeat;
            }
            input = input.replace(matcher.group(0) + toRepeat, replacement);
            start = input.indexOf(replacement, start) + replacement.length() - 1;
            matcher.reset(input);
        }        
        System.out.println(input.length());
        
        // Part 2 - Calculate it recursively
        // TODO lol
    }

}
