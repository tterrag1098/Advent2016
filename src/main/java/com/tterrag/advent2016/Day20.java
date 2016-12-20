package com.tterrag.advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;

public class Day20 {
    
    private static final BitSet blocked = new BitSet();
    private static final BitSet blocked_neg = new BitSet();
    private static boolean intMinBlocked = false;
    
    public static void main(String[] args) throws IOException {
        for (String s : Files.readAllLines(Paths.get("day20.txt"))) {
            String[] ends = s.split("-");
            long max = Long.parseLong(ends[1]);
            for (long i = Long.parseLong(ends[0]); i <= max; i++) {
                int ip = (int) i;
                if (ip > 0) {
                    blocked.set(ip);
                } else if (ip != Integer.MIN_VALUE) {
                    blocked_neg.set(-ip);
                } else {
                    intMinBlocked = true;
                }
            }
        }
        
        root: for (long i = 0; i <= 4294967295L; i++) {
            for (int j = 0; j < blocked.size(); j++) {
                if (!blocked.get(j)) {
                    System.out.println("Part 1: " + i);
                    break root;
                }
            }
        }
        
        // Max IP minus blocked IP count (positive, negative, and int_min)
        long unblocked = 4294967296L - (((long) blocked.cardinality()) + ((long) blocked_neg.cardinality()) + (intMinBlocked ? 1L : 0L));
        System.out.println("Part 2: " + unblocked);
    }
}
