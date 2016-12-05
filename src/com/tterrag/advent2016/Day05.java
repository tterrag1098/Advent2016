package com.tterrag.advent2016;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day05 {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String input = "uqwqemis";
        MessageDigest hasher = MessageDigest.getInstance("MD5");

        String password = "";
        char[] chars = new char[8];
        for (int i = 0; password.length() < 8 || new String(chars).contains("\0"); i++) {
            String hashstr = String.format("%016x", ByteBuffer.wrap(hasher.digest((input + i).getBytes())).getLong());
            if (hashstr.startsWith("00000")) {
                // Part 1
                if (password.length() < 8)
                    password += hashstr.charAt(5);
                
                // Part 2
                if (Character.isDigit(hashstr.charAt(5))) {
                    int charidx = Integer.parseInt(String.valueOf(hashstr.charAt(5)));
                    if (charidx < 8 && chars[charidx] == 0) {
                        chars[charidx] = hashstr.charAt(6);
                    }
                }
            }
        }

        System.out.println("Part 1: " + password);
        System.out.println("Part 2: " + new String(chars));
    }

}
