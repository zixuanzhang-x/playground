package me.zixuan.algorithms.hackerrank;

import java.util.Scanner;

public class StdinAndStdout2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int i = scan.nextInt();
        double d = scan.nextDouble();

        /*
        * https://www.hackerrank.com/challenges/java-stdin-stdout/forum
        * After supplying data for int, we would hit the enter key. What nextInt() and
        * nextDouble() does is it assigns integer to appropriate variable and keeps the
        * enter key unread in the keyboard buffer. so when its time to supply String the
        * nextLine() will read the enter key from the user thinking that the user has
        * entered the enter key.(that's we get empty output) . Unlike C, there is no
        * fflush() to clean buffer, so we have to flush by not taking it in variable.
        * */
        scan.nextLine();
        String s = scan.nextLine();

        System.out.println("String: " + s);
        System.out.println("Double: " + d);
        System.out.println("Int: " + i);
    }
}
