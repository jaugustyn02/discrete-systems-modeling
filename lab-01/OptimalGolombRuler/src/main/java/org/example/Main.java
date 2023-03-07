package org.example;


import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Order: ");
        int order = scanner.nextInt();

        long start = System.nanoTime();

        List<Integer> marks = new ArrayList<>();
        List<List<Boolean>> permutations = new ArrayList<>();
        permutations.add(new ArrayList<>());

        int len = (order - 1) * order / 2;
        boolean flag = false;

        do{
            if(order > 2)
                permutations = BooleanPermutations.generatePermutations(order - 2, len - 2);
            for(List<Boolean> perm: permutations) {
                marks.add(0);
                for (int i = 0; i < perm.size(); i++) {
                    if (perm.get(i))
                        marks.add(i+1);
                }
                if(order > 1)
                    marks.add(len);
                if(rulerIsValid(marks, order)){
                    flag = true;
                    break;
                }
                marks.clear();
            }
            ++len;
        }while (!flag);

        System.out.println("Length: " + (len - 1));
        System.out.println("ODR: " + marks);

        long finish = System.nanoTime();
        long timeElapsed = (finish - start) / 1000000;
        System.out.println("Time: " + timeElapsed + " ms");
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
}