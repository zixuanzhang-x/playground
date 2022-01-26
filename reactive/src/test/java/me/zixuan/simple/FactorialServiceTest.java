package me.zixuan.simple;

import lombok.val;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.*;

class FactorialServiceTest {

    @Test
    void testFactorial() {
        val factorialGenerator = new FactorialService().generateFactorial(10);
        factorialGenerator
                .doOnNext(System.out::println)
                .last()
                .subscribe();
    }
}