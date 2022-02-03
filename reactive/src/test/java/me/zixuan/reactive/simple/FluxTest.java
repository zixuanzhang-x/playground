package me.zixuan.reactive.simple;

import lombok.val;
import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

class FluxTest {
    @Test
    void testCreateFlux() {

    }

    @Test
    void testSubscriber() {
        val flux = Flux.just(1, 2, 3, 4);

        // Consume no events
        flux.subscribe();

        // Consume only value events
        flux.subscribe(n -> System.out.println("consuming " + n));

        // Consume value and error events
        flux.subscribe(n -> System.out.println("consuming " + n),
                err -> System.out.println("error: " + err));

        // Consume value, error, and completion events
        flux.subscribe(n -> System.out.println("consuming " + n),
                err -> System.out.println("error: " + err),
                () -> System.out.println("completed"));

        // Consume value, error, completion, and subscription events
        Disposable disposable = flux.subscribe(n -> System.out.println("consuming " + n),
                err -> System.out.println("error: " + err),
                () -> System.out.println("completed"),
                sub -> System.out.println("subscription:" + sub));
    }
}
