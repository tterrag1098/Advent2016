package com.tterrag.advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class Day01 {
    
    @AllArgsConstructor
    @Setter
    @EqualsAndHashCode
    private static class Pos implements Cloneable {
        int x, y;
        
        public void move(int x, int y) {
            this.x += x;
            this.y += y;
        }
        
        public int distance() {
            return Math.abs(x) + Math.abs(y);
        }
        
        @Override
        public Pos clone() {
            return new Pos(x, y);
        }
    }
    
    @RequiredArgsConstructor
    private enum Dir {
        NORTH((p, i) -> p.move(0, i)),
        EAST((p, i) -> p.move(i, 0)), 
        SOUTH((p, i) -> p.move(0, -i)), 
        WEST((p, i) -> p.move(-i, 0));
        
        final BiConsumer<Pos, Integer> func;
        
        public Dir turn(boolean right) {
            Dir[] values = values();
            return values[(ordinal() + (right ? 1 : -1) + values.length) % values.length];
        }
        
        public void move(Pos pos, int amnt) {
            while (amnt > 0) {
                this.func.accept(pos, 1);
                amnt--;
                if (firstIntersection == null && prevPositions.contains(pos)) {
                    firstIntersection = pos.clone();
                } else {
                    prevPositions.add(pos.clone());
                }
            }
        }
    }
    
    private static Pos firstIntersection;
    private static final Set<Pos> prevPositions = new HashSet<>();
    private static final Pos pos = new Pos(0, 0);
    
    public static void main(String[] args) throws IOException {
        String input = Files.readAllLines(Paths.get("day1.txt")).get(0);
        List<String> instructions = Arrays.asList(input.split(", "));
        Dir dir = Dir.NORTH;
        for (String s : instructions) {
            char turn = s.charAt(0);
            if (turn == 'R') {
                dir = dir.turn(true);
            } else {
                dir = dir.turn(false);
            }
            dir.move(pos, Integer.parseInt(s.substring(1)));
            if (firstIntersection != null) {
                break;
            }
        }
        
        System.out.println(firstIntersection == null ? pos.distance() : firstIntersection.distance());
    }
}
