package com.tterrag.advent2016;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Getter;

public class Day14 {

    private static final Pattern TRIPLE_PATTERN = Pattern.compile("(.)\\1{2}");

    @EqualsAndHashCode
    private static class Match implements Predicate<String>, Comparable<Match> {
        private final @Getter String key;
        private final int pos;
        
        private String match;
        
        public Match(String key, char c, int pos) {
            this.key = key;
            this.pos = pos;
            char[] chars = new char[5];
            Arrays.fill(chars, c);
            this.match = new String(chars);
        }
        
        public boolean expired(int pos) {
            return pos - this.pos > 1000;
        }

        @Override
        public boolean test(String arg0) {
            return arg0.contains(match);
        }

        @Override
        public int compareTo(Match o) {
            return Integer.compare(this.pos, o.pos);
        }
    }
    
    private static final List<Match> matches = new ArrayList<>();
    private static final TreeSet<Match> keys = new TreeSet<>();
    
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String salt = "zpqevtbw";
        int pos = 0;
        
        MessageDigest hasher = MessageDigest.getInstance("MD5");

        while (keys.size() < 64) {
            String hashstr = String.format("%016x", ByteBuffer.wrap(hasher.digest((salt + pos).getBytes())).getLong());
            
            int test = pos;
            matches.removeIf(m -> m.expired(test));
            List<Match> found = matches.stream().filter(m -> m.test(hashstr)).collect(Collectors.toList());
            matches.removeAll(found);
            keys.addAll(found);
            
            Matcher matcher = TRIPLE_PATTERN.matcher(hashstr);
            if (matcher.find()) {
                matches.add(new Match(hashstr, matcher.group().charAt(0), pos));
            }
            
            pos++;
        }
        
        System.out.println(keys.last().pos);
    }
}
