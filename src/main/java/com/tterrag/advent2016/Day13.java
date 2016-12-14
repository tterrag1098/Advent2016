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
import lombok.SneakyThrows;

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
    private static int delay = 12;
    
    public static void main(String[] args) {
        Node root = new Node(1, 1);
        root.setDistance(0);
        
        clearScreen();
        
        Queue<Node> active = new ArrayDeque<>();
        active.add(root);
        
        Node target = root;
        
        int within50 = 0;
        
        int maxX = 0, maxY = 0;
        
        while (!active.isEmpty()) {
            Node cur = active.poll();
            for (Node n : cur.movableTo()) {
                if (seen.contains(n)) continue;
                seen.add(n);
                maxX = Math.max(maxX, n.getX());
                maxY = Math.max(maxY, n.getY());
                if (n.isOpen()) {
                    n.setDistance(cur.getDistance() + 1);
                    active.add(n);

                    // Part 1, check for target coordinate and print distance to
                    if (n.getX() == 31 && n.getY() == 39) {
                        target = n;
                        draw(n.getX(), n.getY(), 'X');
                    } else {
                        draw(n.getX(), n.getY(), 'O');
                    }

                    // Part 2, increment variable each time we encounter a new node that is <=50 moves away
                    // This is dangerous technically, as there is no loop exit case, but it works out that
                    // The input will always have dead ends in all directions
                    if (n.getDistance() <= 50) {
                        within50++;
                    }
                } else {
                    draw(n.getX(), n.getY(), '#');
                }
            }
        }
        
        delay = 3;
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                Node n = new Node(x, y);
                if (!seen.contains(n)) {
                    draw(x, y, n.isOpen() ? ' ' : '#');
                }
            }
        }
        
        // Shift prints to below graph
        draw(53, 0, '\n');
        System.out.println("Part 1: " + target.getDistance());
        System.out.println("Part 2: " + within50);
    }
    
    @SneakyThrows
    private static void draw(int row, int col, char c) {
        char esc = (char) (27);
        String color;
        if (c == 'O') {
            color = esc + "[31m";
        } else if (c == '#') {
            color = esc + "[37m";
        } else {
            color = esc + "[32m";
        }
        System.out.print(String.format("%c[%d;%df", esc, row + 1, col + 1) + color + c);
        Thread.sleep(delay);
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
