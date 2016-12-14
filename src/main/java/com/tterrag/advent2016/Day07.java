package com.tterrag.advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day07 {

    private static Pattern BRACKETS_PATTERN = Pattern.compile("\\[(\\w+)\\]");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("day7.txt"));

        int count = 0;
        loop: for (String s : lines) {
            Matcher matcher = BRACKETS_PATTERN.matcher(s);
            while (matcher.find()) {
                if (hasABBA(matcher.group(1))) {
                    continue loop;
                }
            }
            s = s.replaceAll(BRACKETS_PATTERN.pattern(), " ");
            matcher = Pattern.compile("\\w+").matcher(s);
            while (matcher.find()) {
                if (hasABBA(matcher.group(0))) {
                    count++;
                    break;
                }
            }
        }

        System.out.println("Part 1: " + count);
        count = 0;

        for (String s : lines) {
            List<String> ABAs = new ArrayList<>();
            String supernet = s.replaceAll(BRACKETS_PATTERN.pattern(), " ");
            findABAs(supernet, ABAs);
            Matcher matcher = BRACKETS_PATTERN.matcher(s);
            while (matcher.find()) {
                if (hasBAB(matcher.group(1), ABAs)) {
                    count++;
                    break;
                }
            }
        }
        
        System.out.println("Part 2: " + count);
    }

    private static boolean hasABBA(String s) {
        for (int i = 0; i < s.length() - 3; i++) {
            char a = s.charAt(i);
            char b = s.charAt(i + 1);
            if (a != b && s.charAt(i + 2) == b && s.charAt(i + 3) == a) {
                return true;
            }
        }
        return false;
    }

    private static void findABAs(String s, List<String> results) {
        for (int i = 0; i < s.length() - 2; i++) {
            char a = s.charAt(i);
            char b = s.charAt(i + 1);
            if (a != ' ' &&  b != ' ' && a != b && a == s.charAt(i + 2)) {
                results.add(s.substring(i, i + 3));
            }
        }
    }
   
    private static boolean hasBAB(String s, List<String> ABAs) {
        for (int i = 0; i < s.length() - 2; i++) {
            char b = s.charAt(i);
            char a = s.charAt(i + 1);
            if (b != a && b == s.charAt(i + 2)) {
                for (String aba : ABAs) {
                    if (b == aba.charAt(1) && a == aba.charAt(0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
