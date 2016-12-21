package com.tterrag.advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

public class Day21 {

    interface Operation {
        String apply(String in, int... data);
    }
    
    @RequiredArgsConstructor
    enum OpType {
        SWAP_POS((s, data) -> {
            char[] chars = s.toCharArray();
            char tmp = chars[data[0]];
            chars[data[0]] = chars[data[1]];
            chars[data[1]] = tmp;
            return new String(chars);
        }),
        SWAP_LETTER((s, data) -> s.replace((char) data[0], ' ').replace((char) data[1], (char) data[0]).replace(' ', (char) data[1])),
        ROTATE_ALL((s, data) -> {
            char[] chars = s.toCharArray();
            char[] newchars = new char[chars.length];
            for(int i = 0; i < chars.length; i++){
                newchars[((i + (data[1] == 1 ? -data[0] : data[0])) + chars.length) % chars.length ] = chars[i];
            }
            return new String(newchars);
        }),
        ROTATE_BASED((s, data) -> {
            int index = s.indexOf(data[0]);
            return ROTATE_ALL.apply(s, (char) (1 + index + (index >= 4 ? 1 : 0)), 0);
        }),
        REVERSE((s, data) -> {
           String substr = s.substring(data[0], data[1] + 1);
           String reversed = new StringBuilder(substr).reverse().toString();
           return s.replace(substr, reversed);
        }),
        MOVE((s, data) -> {
            StringBuilder sb = new StringBuilder(s);
            char c = s.charAt(data[0]);
            sb.deleteCharAt(data[0]);
            sb.insert(data[1], c);
            return sb.toString();
        });
        
        @Delegate
        private final Operation func;
    }

    public static void main(String[] args) throws IOException {
        String input = "abcdefgh";
        System.out.println("Part 1: " + process(input));
        
        input = "fbgdceah";
        List<String> permutations = new ArrayList<String>();
        permutation(permutations, "", input);
        
        for (String s : permutations) {
            if (process(s).equals(input)) {
                System.out.println("Part 2: " + s);
                break;
            }
        }
    }

    private static String process(String input) throws IOException {
        for (String s : Files.readAllLines(Paths.get("day21.txt"))) {
            String[] words = s.split(" ");
            switch(words[0]) {
                case "swap":
                    if ("position".equals(words[1])) {
                        input = OpType.SWAP_POS.apply(input, Byte.parseByte(words[2]), Byte.parseByte(words[5]));
                    } else {
                        input = OpType.SWAP_LETTER.apply(input, words[2].charAt(0), words[5].charAt(0));
                    }
                    break;
                case "rotate":
                    if ("based".equals(words[1])) {
                        input = OpType.ROTATE_BASED.apply(input, words[6].charAt(0));
                    } else {
                        char lr = (char) (words[1].equals("left") ? 1 : 0);
                        input = OpType.ROTATE_ALL.apply(input, Byte.parseByte(words[2]), lr);
                    }
                    break;
                case "reverse":
                    input = OpType.REVERSE.apply(input, Byte.parseByte(words[2]), Byte.parseByte(words[4]));
                    break;
                case "move":
                    input = OpType.MOVE.apply(input, Byte.parseByte(words[2]), Byte.parseByte(words[5]));
                    break;
            }
        }
        return input;
    }

    private static void permutation(List<String> collector, String prefix, String str) {
        int n = str.length();
        if (n == 0) {
            collector.add(prefix);
        } else {
            for (int i = 0; i < n; i++) {
                permutation(collector, prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
            }
        }
    }
}
