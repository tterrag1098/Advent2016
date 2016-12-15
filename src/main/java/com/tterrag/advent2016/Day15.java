package com.tterrag.advent2016;

import java.util.Arrays;
import java.util.List;

import lombok.Value;

public class Day15 {

    @Value
    private static class Disc {
        int positions;
        int pos;
    }
    
    private static final List<Disc> discs = Arrays.asList(
            new Disc(13, 1),
            new Disc(19, 10),
            new Disc(3, 2),
            new Disc(7, 1),
            new Disc(5, 3),
            new Disc(17, 5),
            new Disc(11, 0) // Remove this disc for part 1
    );

    public static void main(String[] args) {
        main: for (int i = 0;; i++) {
            for (int j = 0; j < discs.size(); j++) {
                Disc d = discs.get(j);
                if ((d.getPos() + (i + j + 1)) % d.getPositions() != 0) {
                    continue main;
                }
            }
            System.out.println(i);
            break;
        }
    }
}
