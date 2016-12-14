package com.tterrag.advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.ToString;

public class Day04 {

    @ToString
    private static class Key {
        
        private static final Pattern PATTERN = Pattern.compile("((?:[a-z]+-)+)([0-9]+)\\[([a-z]+)\\]");

        private String input;
        private char[] checksum;
        private @Getter int key;
        private Map<Character, Integer> counts = new HashMap<>();
        
        public Key(String newInput, Key old) {
            this.input = newInput;
            this.checksum = old.checksum;
            this.key = old.key;
            this.counts = old.counts;
        }
        
        public Key(String in) {            
            Matcher matcher = PATTERN.matcher(in);
            matcher.find();
            this.input = matcher.group(1).replaceAll("-$", "");
            input.replace("-", "").chars().forEach(c->counts.compute((char)c, (k, v) -> v == null ? 1 : v + 1));
            key = Integer.parseInt(matcher.group(2));
            checksum = matcher.group(3).toCharArray();
        }
        
        public boolean isValid() {
            List<Entry<Character, Integer>> sortedEntries = counts.entrySet().stream().sorted((e1, e2) -> e1.getValue().equals(e2.getValue()) ? Character.compare(e1.getKey(), e2.getKey()) : -Integer.compare(e1.getValue(), e2.getValue())).collect(Collectors.toList());
            for (int i = 0; i < checksum.length; i++) {
                if (sortedEntries.get(i).getKey() != checksum[i]) return false;
            }
            return true;
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("day4.txt"));
        List<Key> validKeys = lines.stream().map(Key::new).filter(Key::isValid).collect(Collectors.toList());
        System.out.println("Part 1 Sum: " + validKeys.stream().mapToInt(Key::getKey).sum());
        System.out.println("Part 2 ID: "  + validKeys.stream().map(k -> new Key(k.input.chars().map(c -> c == '-' ? ' ' : (((c - 'a') + k.key) % 26) + 'a').mapToObj(i -> "" + Character.valueOf((char) i)).collect(Collectors.joining()), k)).filter(key -> key.input.equals("northpole object storage")).findFirst().get().key);
    }

}
