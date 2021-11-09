package ru.netology.graphics.server;

import ru.netology.graphics.image.TextColorSchema;
import java.util.*;

class Symbols implements TextColorSchema {

    @Override
    public char convert(int color) {
        char v = '#';
        Map<Integer, Character> symbols = new HashMap<>();
        symbols.put(255 * 7 / 8, '`');
        symbols.put(255 * 6 / 8, '-');
        symbols.put(255 * 5 / 8, '+');
        symbols.put(255 * 4 / 8, '*');
        symbols.put(255 * 3 / 8, '%');
        symbols.put(255 * 2 / 8, '@');
        symbols.put(255 * 1 / 8, '$');
        for (Integer i : symbols.keySet()) {
            if(i<=color){
                v = symbols.get(i);
                break;
            }
        }
        return v;
    }
}
