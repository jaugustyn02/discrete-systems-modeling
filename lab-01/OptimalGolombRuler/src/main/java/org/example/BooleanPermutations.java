package org.example;

import java.util.ArrayList;
import java.util.List;

public class BooleanPermutations {
    public static List<List<Boolean>> generatePermutations(int ones, int len) {
        List<List<Boolean>> result = new ArrayList<>();
        generatePermutationsHelper(ones, len, new ArrayList<>(), result);
        return result;
    }

    private static void generatePermutationsHelper(int onesLeft, int remaining, List<Boolean> prefix, List<List<Boolean>> result) {
        if (onesLeft == 0 && remaining == 0) {
            result.add(prefix);
            return;
        }
        if (remaining == 0) {
            return;
        }
        List<Boolean> prefixWithOne = new ArrayList<>(prefix);
        prefixWithOne.add(true);
        generatePermutationsHelper(onesLeft - 1, remaining - 1, prefixWithOne, result);

        List<Boolean> prefixWithZero = new ArrayList<>(prefix);
        prefixWithZero.add(false);
        generatePermutationsHelper(onesLeft, remaining - 1, prefixWithZero, result);
    }
}