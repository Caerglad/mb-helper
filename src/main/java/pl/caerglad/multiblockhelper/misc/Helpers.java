package pl.caerglad.multiblockhelper.misc;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Helpers {
    public static int calculateSelectionSize(int a, int b) {
        return a > b ? a - b + 1 : b - a + 1;
    }

    public static String[][][] convertListTo3DArray(List<String> list, int x, int y, int z) {
        if (list.size() != x * y * z) {
            throw new IllegalArgumentException("List size does not match the given 3D array dimensions.");
        }

        String[][][] array3D = new String[x][y][z];
        int index = 0;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    array3D[i][j][k] = list.get(index++);
                }
            }
        }

        return array3D;
    }

    public static List<String> convertStringsToSymbols(List<String> strings, Map<String, String> dictionary) {
        List<String> symbolList = new ArrayList<>();

        for (String str : strings) symbolList.add(dictionary.getOrDefault(str, "?"));

        return symbolList;
    }

    public static Map<String, String> createSymbolDictionary(List<String> strings) {
        Set<String> uniqueStrings = new LinkedHashSet<>(strings);
        Map<String, String> dictionary = new HashMap<>();
        List<Character> symbols = getCharacters(uniqueStrings);

        int index = 0;
        for (String uniqueString : uniqueStrings) {
            if (uniqueString.equals("block.minecraft.air")) {
                dictionary.put(uniqueString, "#");
            } else {
                dictionary.put(uniqueString, symbols.get(index).toString());
                index++;
            }
        }

        return dictionary;
    }

    private static @NotNull List<Character> getCharacters(Set<String> uniqueStrings) {
        List<Character> symbols = new ArrayList<>();

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            symbols.add(ch);
        }

        for (char ch = 'a'; ch <= 'z'; ch++) {
            symbols.add(ch);
        }

        if (uniqueStrings.size() > symbols.size()) {
            throw new IllegalArgumentException("Not enough symbols to map unique strings.");
        }
        return symbols;
    }
}
