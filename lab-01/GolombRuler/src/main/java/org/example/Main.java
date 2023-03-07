package org.example;


import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> P = getPrimeOddNumbers(100);
        List<Integer> M = new ArrayList<>();
        for(int p: P) {
            for (int k = 0; k < p; k++) {
                int mark = 2 * p * k + (k * k) % p;
                M.add(mark);
            }
            System.out.println(p+":");
            System.out.println(M);
            if(rulerIsValid(M, p)){
                System.out.println("correct");
            }
            else {
                System.out.println("incorrect");
            }
            M.clear();
        }
    }

    public static boolean rulerIsValid(List<Integer> M, int p){
        List<Integer> D = new ArrayList<>();
        for (int i = 0; i < p - 1; i++) {
            for (int j = i + 1; j < p; j++) {
                D.add(M.get(j) - M.get(i));
            }
        }
        Set<Integer> S = new HashSet<>(D);
        return S.size() == D.size();
    }

    public static List<Integer> getPrimeOddNumbers(int n){
        List<Integer> P = new ArrayList<>();
        for(int i=3; i<=n; i++){
            if(isPrime(i)){
                P.add(i);
            }
        }
        return P;
    }

    public static boolean isPrime(int num) {
        for (int i = 2; i < num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

}