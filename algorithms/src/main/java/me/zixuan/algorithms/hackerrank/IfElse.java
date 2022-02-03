package me.zixuan.algorithms.hackerrank;

import java.util.Scanner;

public class IfElse {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // constrains: 1 <= N <= 100
        int N = scanner.nextInt();
        if (N % 2 == 1) {
            System.out.println("Weird");
        } else if (N <= 5) {
            System.out.println("Not Weird");
        } else if (N <= 20) {
            System.out.println("Weird");
        } else {
            System.out.println("Not Weird");
        }
    }
}
