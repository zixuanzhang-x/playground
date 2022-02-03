package me.zixuan.reactive.simple;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

class MonoTest {
    @Test
    void testCreateMono() {
        // Mono.just
        Mono.just("Hello").subscribe(System.out::println);
        Mono.justOrEmpty(11).subscribe(System.out::println);
        Mono.justOrEmpty(Optional.empty()).subscribe();

        // Mono.from
        Mono.fromCallable(() -> new String[]{"callable"}).subscribe();
        Mono.fromSupplier(() -> 1).subscribe();
        // Mono.fromFuture();
        Mono.fromRunnable(() -> System.out.println("runnable"))
                .subscribe(t -> System.out.println("received " + t),
                        err -> System.out.println("error: " + err),
                        () -> System.out.println("completed"),
                        subscription -> System.out.println("got subscription: " + subscription));

        // util functions
        Mono.empty();
        Mono.error(new Throwable());
        Mono.never();
        Mono.from(Flux.just(1, 2, 3)).subscribe(System.out::println);
        Mono.defer(() -> Mono.just("1"));
    }
}
