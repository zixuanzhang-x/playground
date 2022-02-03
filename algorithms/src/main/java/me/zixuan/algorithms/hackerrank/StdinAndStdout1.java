package me.zixuan.algorithms.hackerrank;

//Scanner scanner = new Scanner(System.in);
//String myString = scanner.next();
//int myInt = scanner.nextInt();
//scanner.close();
//
//System.out.println("myString is: " + myString);
//System.out.println("myInt is: " + myInt);

import java.util.Scanner;

public class StdinAndStdout1 {
    public static void main(String[] args) {
        // read 3 lines of input, and each line contains a single integer
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();

        // print 3 integers in 3 lines
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
}
