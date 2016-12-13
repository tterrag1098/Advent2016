package com.tterrag.advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import lombok.Value;

public class Day12 {
    
    // Initial values of registers, part 1 is all 0s, part 2 has C set to 1.
    private static final int[] REGISTERS = new int[] { 0, 0, 1, 0 };

    @RequiredArgsConstructor
    enum InstructionType implements BiFunction<Integer, Integer, Integer> {
        cpy((x, y) -> REGISTERS[y] = x),
        inc((x, y) -> REGISTERS[x]++),
        dec((x, y) -> REGISTERS[x]--),
        jnz((x, y) -> REGISTERS[x] == 0 ? 1 : y);
        
        private final BiFunction<Integer, Integer, Integer> function;

        @Override
        public Integer apply(Integer x, Integer y) {
            // Only return jump value if this is JNZ, otherwise just return 1 (next instruction)
            int ret = function.apply(x, y);
            return this == jnz ? ret : 1;
        }
    }
    
    @Value
    private static class Instruction implements Supplier<Integer> {
        InstructionType type;
        // This is a horrible hack, only true when type == cpy and x is a register instead of a value
        boolean xIsRegister;
        int x, y;
        
        public Integer get() {
            return type.apply(xIsRegister ? REGISTERS[x] : x, y);
        }
        
        public static Instruction fromString(String s) {
            String[] words = s.split(" ");
            InstructionType type = InstructionType.valueOf(words[0]);
            return new Instruction(type,
                    type == InstructionType.cpy && Character.isAlphabetic(words[1].charAt(0)), 
                    parseRegisterOrNumber(words[1]), 
                    words.length == 2 ? 0 : parseRegisterOrNumber(words[2])
            );
        }
    }
    
    private static final List<Instruction> instructions = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        for (String s : Files.readAllLines(Paths.get("day12.txt"))) {
            instructions.add(Instruction.fromString(s));
        }
        
        int cur = 0;
        while (cur < instructions.size()) {
            Instruction i = instructions.get(cur);
            cur += i.get();
        }
        
        System.out.println(REGISTERS[0]);
    }
    
    private static int parseRegisterOrNumber(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return s.charAt(0) - 'a';
        }
    }
}
