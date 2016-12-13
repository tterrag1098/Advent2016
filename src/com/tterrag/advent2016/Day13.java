package com.tterrag.advent2016;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class Day13 {

    @RequiredArgsConstructor
    @EqualsAndHashCode
    @Getter
    private static class Node {
        private final int x, y;
        private transient @Getter(AccessLevel.NONE) Optional<Boolean> cache = Optional.empty();
        private transient @Setter int distance = Integer.MAX_VALUE;
        
        public boolean isOpen() {
            if (cache.isPresent()) {
                return cache.get();
            } else {
                int maths = (x * x) + (3 * x) + (2 * x * y) + y + (y * y);
                maths += 1350; // Puzzle input
                cache = Optional.of(BitSet.valueOf(new long[] { maths }).cardinality() % 2 == 0);
                return isOpen();
            }
        }
        
        public List<Node> movableTo() {
            List<Node> ret = new ArrayList<>();
            // Move in URDL order (clockwise)
            if (y > 0) {
                ret.add(new Node(x, y - 1));
            }
            ret.add(new Node(x + 1, y));
            ret.add(new Node(x, y + 1));
            if (x > 0) {
                ret.add(new Node(x - 1, y));
            }
            return ret;
        }
    }
    
    private static Set<Node> seen = new HashSet<>(); 
    
    public static void main(String[] args) {
        Node root = new Node(1, 1);
        root.setDistance(0);
        
        Queue<Node> active = new ArrayDeque<>();
        active.add(root);
        
        int within50 = 0;
        
        while (!active.isEmpty()) {
            Node cur = active.poll();
            for (Node n : cur.movableTo()) {
                if (seen.contains(n)) continue;
                seen.add(n);
                if (n.isOpen()) {
                    n.setDistance(cur.getDistance() + 1);
                    // Part 1, check for target coordinate and print distance to
                    if (n.getX() == 31 && n.getY() == 39) {
                        System.out.println("Part 1: " + n.getDistance());
                    } else {
                        active.add(n);
                    }
                    // Part 2, increment variable each time we encounter a new node that is <=50 moves away
                    // This is dangerous technically, as there is no loop exit case, but it works out that
                    // The input will always have dead ends in all directions
                    if (n.getDistance() <= 50) {
                        within50++;
                    }
                }
            }
        }
        
        System.out.println("Part 2: " + within50);
    }
}
