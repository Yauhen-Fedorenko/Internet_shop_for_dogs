package com.spring.shop.random;

import java.util.HashMap;
import java.util.Map;

public class RandomPass {

    private static Map<Integer, String> symbols = new HashMap<>();

    static {
        symbols.put(0, "a");
        symbols.put(1, "b");
        symbols.put(2, "c");
        symbols.put(3, "d");
        symbols.put(4, "e");
        symbols.put(5, "f");
        symbols.put(6, "g");
        symbols.put(7, "h");
        symbols.put(8, "i");
        symbols.put(9, "k");
        symbols.put(10, "l");
        symbols.put(11, "m");
        symbols.put(12, "n");
        symbols.put(13, "o");
        symbols.put(14, "p");
        symbols.put(15, "r");
        symbols.put(16, "s");
        symbols.put(17, "t");
        symbols.put(18, "u");
        symbols.put(19, "v");
        symbols.put(20, "w");
        symbols.put(21, "x");
        symbols.put(22, "y");
        symbols.put(23, "z");
        symbols.put(24, "@");
        symbols.put(25, "!");
        symbols.put(26, "_");
        symbols.put(27, "*");
        symbols.put(28, "$");
        symbols.put(29, "?");
        symbols.put(30, "1");
        symbols.put(31, "2");
        symbols.put(32, "3");
        symbols.put(33, "4");
        symbols.put(34, "5");
        symbols.put(35, "6");
        symbols.put(36, "7");
        symbols.put(37, "8");
        symbols.put(38, "9");
    }

    private boolean isUpper() {
        return Math.round(Math.random())==0;
    }
    public String getNewPassword(){
        String pass = new String("");
        for (int i = 0; i < 10; i++) {
            int key = (int) (Math.random() * 38);
            String symbol = symbols.get(key);
            if (key<24&&isUpper()) symbol = symbol.toUpperCase();
            pass += symbol;
        }
        return pass;
    }
}
