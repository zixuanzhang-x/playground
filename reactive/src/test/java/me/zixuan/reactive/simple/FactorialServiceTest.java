package me.zixuan.reactive.simple;

import lombok.val;
import org.junit.jupiter.api.Test;

class FactorialServiceTest {

    @Test
    void testFactorial() {
        val factorialGenerator = new FactorialService().generateFactorial(10);
        factorialGenerator
                .doOnNext(System.out::println)
                .last()
                .subscribe(n -> System.out.println("Last element is: " + n));
    }
}