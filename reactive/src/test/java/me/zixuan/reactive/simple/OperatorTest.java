package me.zixuan.reactive.simple;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.stream.Stream;

class OperatorTest {

    private static Stream<Arguments> provideFibonacciGenerator() {
        Flux<Long> fibonacciGenerator = Flux.generate(
                () -> Tuples.of(0L, 1L),
                (state, sink) -> {
                    if (state.getT1() < 0)
                        sink.complete();
                    else
                        sink.next(state.getT1());
                    return Tuples.of(state.getT2(), state.getT1() + state.getT2());
                }
        );
        return Stream.of(Arguments.of(fibonacciGenerator));
    }

    @ParameterizedTest
    @MethodSource("provideFibonacciGenerator")
    void testFibonacciFilter(Flux<Long> fibonacciGenerator) {
        fibonacciGenerator.filter(n -> n % 2 == 0)
                .subscribe(System.out::println);

        fibonacciGenerator.filterWhen(n -> Mono.just(n < 10))
                .subscribe(System.out::println);
    }

    @ParameterizedTest
    @MethodSource("provideFibonacciGenerator")
    void testFibonacciTake(Flux<Long> fibonacciGenerator) {
        fibonacciGenerator.take(10)
                .subscribe(System.out::println);

        // takeLast only works for bounded streams
        fibonacciGenerator.takeLast(10)
                .subscribe(System.out::println);

        // last returns a Mono for bounded streams
        fibonacciGenerator.last()
                .subscribe(System.out::println);

        fibonacciGenerator.take(Duration.ofSeconds(5))
                .subscribe(System.out::println);
    }

    @ParameterizedTest
    @MethodSource("provideFibonacciGenerator")
    void testFibonacciSkip(Flux<Long> fibonacciGenerator) {
        // skip n elements from the beginning
        fibonacciGenerator.skip(10)
                .subscribe(System.out::println);

        // skip a Duration from beginning
        fibonacciGenerator.skip(Duration.ofMillis(10))
                .subscribe(System.out::println);

        // skip n elements from the end
        fibonacciGenerator.skipLast(10)
                .subscribe(System.out::println);

        // skip all elements until certain criteria is met
        fibonacciGenerator.skipUntil(t -> t > 100)
                .subscribe(System.out::println);
    }

    @Test
    void testFibonacciUtils() {
        // distinct: select unique items in the stream
        Flux.just(1, 1, 2, 3, 3).distinct()
                .subscribe(System.out::println);
        System.out.println("---------------");

        // distinctUntilChanged: filter out subsequent repetitions of an element
        Flux.just(1, 2, 2, 3, 2, 4, 5, 4, 4).distinctUntilChanged()
                .subscribe(System.out::println);
        System.out.println("---------------");

        // ignoreElements: ignore all value events and only propagate completion/error
        Flux.just(1, 2, 3).ignoreElements()
                .subscribe(System.out::println);
        System.out.println("---------------");

        // single: select only a single item
        Flux.just(1).single()
                .subscribe(System.out::println);
        System.out.println("---------------");

        // elementAt: indexing the stream
        Flux.just(0, 1, 2, 3, 4).elementAt(4)
                .subscribe(System.out::println);
    }

    @ParameterizedTest
    @MethodSource("provideFibonacciGenerator")
    void testFibonacciMap(Flux<Long> fibonacciGenerator) {
        RomanNumber numberConverter = new RomanNumber();
        fibonacciGenerator.skip(1)
                .take(10)
                .map(t -> numberConverter.toRomanNumeral(t.intValue()))
                .subscribe(System.out::println);
    }

    @ParameterizedTest
    @MethodSource("provideFibonacciGenerator")
    void testFibonacciFlatMap(Flux<Long> fibonacciGenerator) {
        Factorization numberConverter = new Factorization();
        fibonacciGenerator.skip(1)
                .take(10)
//                .map(t -> numberConverter.findfactor(t.intValue()))
                .flatMap(t ->
                        Flux.fromIterable(numberConverter.findfactor(t.intValue())))
                .subscribe(System.out::println);
    }

    @ParameterizedTest
    @MethodSource("provideFibonacciGenerator")
    void testFibonacciRepeat(Flux<Long> fibonacciGenerator) {
        // The repeat() operator, invoked without any argument, replays the stream an infinite number of times
        fibonacciGenerator.take(10).repeat(2).subscribe(System.out::println);

        // There is also a predicate variant in which a Boolean provider is passed to the repeat operator.
        // Upon completion, the provider is evaluated every time in order to discover whether the stream
        // needs to be repeated.
        fibonacciGenerator.take(10).repeat(() -> false);
    }

    @ParameterizedTest
    @MethodSource("provideFibonacciGenerator")
    void testFibonacciCollect(Flux<Long> fibonacciGenerator) {
        fibonacciGenerator.take(10).collectList().subscribe(System.out::println);
        fibonacciGenerator.take(10).collectSortedList((x, y) -> -1 * Long.compare(x, y))
                .subscribe(System.out::println);
        fibonacciGenerator.take(10).collect(HashSet::new, HashSet::add)
                .subscribe(System.out::println);
        fibonacciGenerator.take(10).collectMap(t -> t % 2 == 0 ? "even" : "odd")
                .subscribe(System.out::println);
        fibonacciGenerator.take(10).collectMultimap(t -> t % 2 == 0 ? "even" : "odd")
                .subscribe(System.out::println);
    }

    @ParameterizedTest
    @MethodSource("provideFibonacciGenerator")
    void testFibonacciReduce(Flux<Long> fibonacciGenerator) {
        fibonacciGenerator.take(10).reduce(Long::sum).subscribe(System.out::println);
        fibonacciGenerator.take(10).reduce(-88L, Long::sum).subscribe(System.out::println);
    }

    @ParameterizedTest
    @MethodSource("provideFibonacciGenerator")
    void testFibonacciConditional(Flux<Long> fibonacciGenerator) {
        fibonacciGenerator.take(10).all(x -> x >= 0).subscribe(System.out::println);
        fibonacciGenerator.take(10).any(x -> x == 10).subscribe(System.out::println);
    }

    @ParameterizedTest
    @MethodSource("provideFibonacciGenerator")
    void testFibonacciConcatAndStart(Flux<Long> fibonacciGenerator) {
        fibonacciGenerator.take(5).concatWith(Flux.just(-1L, -2L, -3L)).subscribe(System.out::println);
        fibonacciGenerator.take(5).startWith(Flux.just(-1L, -2L, -3L)).subscribe(System.out::println);
    }
}

class RomanNumber {
    TreeMap<Integer, String> romanMap= new TreeMap<>();
    RomanNumber(){
        romanMap.put(1000, "M");
        romanMap.put(900, "CM");
        romanMap.put(500, "D");
        romanMap.put(400, "CD");
        romanMap.put(100, "C");
        romanMap.put(90, "XC");
        romanMap.put(50, "L");
        romanMap.put(40, "XL");
        romanMap.put(10, "X");
        romanMap.put(9, "IX");
        romanMap.put(5, "V");
        romanMap.put(4, "IV");
        romanMap.put(1, "I");
    }
    String toRomanNumeral(int number) {
        int l =  romanMap.floorKey(number);
        if ( number == l ) {
            return romanMap.get(number);
        }
        return romanMap.get(l) + toRomanNumeral(number-l);
    }
}

class Factorization {
    Collection<Integer> findfactor(int number) {
        ArrayList<Integer> factors= new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            if (number % i == 0) {
                factors.add(i);
            }
        }
        return factors;
    }
}