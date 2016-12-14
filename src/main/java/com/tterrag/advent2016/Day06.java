package com.tterrag.advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Day06 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("day6.txt"));

        Map<Integer, Map<Character, Integer>> counts = new HashMap<>();
        for (String string : lines) {
            char[] chars = string.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                counts.putIfAbsent(i, new HashMap<>());
                counts.get(i).compute(chars[i], (c, val) -> val == null ? 1 : val + 1); 
            }
        }
        
        char[] maxchars = new char[8], minchars = new char[8];
        for (int i = 0; i < maxchars.length; i++) {
            List<Character> sorted = counts.get(i).entrySet().stream().sorted(Entry.comparingByValue()).map(Entry::getKey).collect(Collectors.toList());
            minchars[i] = sorted.get(sorted.size() - 1);
            maxchars[i] = sorted.get(0);
        }
        
        System.out.println("Part 1: " + new String(maxchars));
        System.out.println("Part 2: " + new String(minchars));
    }
}
