package me.zixuan.algorithms.hackerrank;

import java.util.Scanner;

public class OutputFormatting {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("================================");
        for (int i = 0; i < 3; i++) {
            String s = scan.next();
            int x = scan.nextInt();
            System.out.printf("%-15s%03d%n", s, x);
        }
        System.out.println("================================");
    }
}
