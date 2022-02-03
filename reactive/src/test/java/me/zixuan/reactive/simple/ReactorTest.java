package me.zixuan.reactive.simple;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReactorTest {
    @Test
    void testSimpleStringFlux() {
        val str = new StringBuilder();
        Flux<String> stringFlux = Flux.just("Quick", "brown", "fox", "jumped", "over", "the", "wall");
        stringFlux.subscribe(s -> str.append(s).append(" "));
        System.out.println(str.toString());
        assertEquals("Quick brown fox jumped over the wall ", str.toString());
    }

    @Test
    void testFibonacci() {
        Flux<Long> fibonacciGenerator = Flux.generate(
                () -> Tuples.of(0L, 1L),
                (state, sink) -> {
                    System.out.println("generating " + state.getT1());
                    sink.next(state.getT1());
                    System.out.println("returning new state");
                    return Tuples.of(state.getT2(), state.getT1() + state.getT2());
                },
                state -> System.out.println("Flux terminated with final event of: " + state));
        List<Long> fibonacciSequence = new LinkedList<>();
        int size = 50;
        fibonacciGenerator.take(size).subscribe(n -> {
            System.out.println("consuming " + n);
            fibonacciSequence.add(n);
        });
        System.out.println(fibonacciSequence);
        assertEquals(7778742049L, fibonacciSequence.get(size - 1));

        // BaseSubscriber
        BaseSubscriber<Long> fibonacciSubscriber = new BaseSubscriber<Long>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                super.hookOnSubscribe(subscription);
            }

            @Override
            protected void hookOnNext(Long value) {
                super.hookOnNext(value);
            }

            @Override
            protected void hookOnComplete() {
                super.hookOnComplete();
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                super.hookOnError(throwable);
            }

            @Override
            protected void hookOnCancel() {
                super.hookOnCancel();
            }
        };

        fibonacciGenerator.subscribe(fibonacciSubscriber);
    }

    @Test
    void testFibonacciFluxSink() {
        Flux<Long> fibonacciGenerator = Flux.create(fluxSink -> {
            long current = 1L, prev = 0L;
            AtomicBoolean stop = new AtomicBoolean(false);
            fluxSink.onDispose(() -> {
                stop.set(true);
                System.out.println("------ onDispose called ------");
            });
            while (current > 0) {
                fluxSink.next(current);
                System.out.println("generated " + current);
                long next = current + prev;
                prev = current;
                current = next;
            }
            fluxSink.complete();
        });
        List<Long> fibonacciSequence = new LinkedList<>();
        fibonacciGenerator.take(50).subscribe(n -> {
            System.out.println("consuming " + n);
            fibonacciSequence.add(n);
        });
        System.out.println(fibonacciSequence);
        assertEquals(12586269025L, fibonacciSequence.get(fibonacciSequence.size() - 1));
    }
}
