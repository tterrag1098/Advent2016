package com.tterrag.advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

public class Day10 {
    
    @Value
    @ToString
    private static class Task implements Consumer<Integer> {
        boolean output;
        int number;
        
        @Override
        public void accept(Integer t) {
            if (output) {
                outputs.putIfAbsent(number, new ArrayList<>());
                outputs.get(number).add(t);
            } else {
                bots.get(number).addChip(t);
            }
        }
    }
    
    @RequiredArgsConstructor
    @Getter
    @ToString
    private static class Bot {
        private final int number;
        private int chip1 = -1, chip2 = -1;
        @Setter
        private Task low, high;
        
        public void addChip(int chip) {
            if (chip1 < 0) {
                chip1 = chip;
            } else if (chip2 < 0) {
                chip2 = chip;
            } else {
                throw new IllegalStateException("Bot cannot hold 3 chips");
            }
        }
        
        public int removeChip(boolean high) {
            int ret = -1;
            if (chip1 > chip2 == high) {
                ret = chip1;
                chip1 = -1;
            } else if (chip2 >= 0) {
                ret = chip2;
                chip2 = -1;
            }
            if (ret < 0) {
                throw new IllegalStateException("Cannot remove chip from empty bot");
            }
            return ret;
        }
        
        public int chipCount() {
            return chip1 < 0 ? 0 : chip2 < 0 ? 1 : 2;
        }
    }
    
    private static final Map<Integer, Bot> bots = new HashMap<>();
    private static final Map<Integer, List<Integer>> outputs = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("day10.txt"));
        
        for (String s : lines) {
            String[] words = s.split(" ");
            if (words[0].equals("value")) {
                int bot = Integer.parseInt(words[5]);
                bots.putIfAbsent(bot, new Bot(bot));
                bots.get(bot).addChip(Integer.parseInt(words[1]));
            } else {
                int bot = Integer.parseInt(words[1]);
                bots.putIfAbsent(bot, new Bot(bot));
                bots.get(bot).setLow(new Task(words[5].equals("output"), Integer.parseInt(words[6])));
                bots.get(bot).setHigh(new Task(words[10].equals("output"), Integer.parseInt(words[11])));
            }
        }
        
        List<Bot> with2;
        while ((with2 = bots.values().stream().filter(b -> b.chipCount() == 2).collect(Collectors.toList())).size() > 0) {
            for (Bot bot : with2) {
                int low = bot.removeChip(false), high = bot.removeChip(true);
                if (low == 17 && high == 61) {
                    System.out.println("Part 1: " + bot.getNumber());
                }
                bot.getLow().accept(low);
                bot.getHigh().accept(high);
            }
        }
        
        System.out.println("Part 2: " + (outputs.get(0).get(0) * outputs.get(1).get(0) * outputs.get(2).get(0)));
    }

}
