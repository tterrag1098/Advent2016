package com.tterrag.advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day09 {

    private static final Pattern MARKER_PATTERN = Pattern.compile("\\((\\d+)x(\\d+)\\)");
    
    public static void main(String[] args) throws IOException {
        String input = Files.readAllLines(Paths.get("day9.txt")).get(0);
        String uncompressed = input;

        // Part 1 - Actually decompress the string
        Matcher matcher = MARKER_PATTERN.matcher(uncompressed);
        int start = 0;
        while (matcher.find(start)) {
            int chars = Integer.parseInt(matcher.group(1)), repeat = Integer.parseInt(matcher.group(2));
            String toRepeat = uncompressed.substring(matcher.end(), matcher.end() + chars);
            String replacement = toRepeat;
            for (int i = 1; i < repeat; i++) {
                replacement += toRepeat;
            }
            uncompressed = uncompressed.replace(matcher.group(0) + toRepeat, replacement);
            start = uncompressed.indexOf(replacement, start) + replacement.length() - 1;
            matcher.reset(uncompressed);
        }        
        System.out.println("Part 1: " + input.length());
        
        // Part 2 - Calculate it recursively
        System.out.println("Part 2: " + getUncompressedLength(matcher.reset(input), 0, input.length()));
    }

    private static long getUncompressedLength(Matcher matcher, int start, int end) {
        long size = 0;
        matcher.find();
        try {
            while (matcher.end() < end) {
                int chars = Integer.parseInt(matcher.group(1)), repeat = Integer.parseInt(matcher.group(2));
                size += matcher.start() - start;
                start = matcher.end() + chars;
                size += getUncompressedLength(matcher, matcher.end(), matcher.end() + chars) * repeat;
            }
        } catch (IllegalStateException e) {}
        return size + (end - start);
    }
}
