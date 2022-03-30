package me.zixuan.reactive.youtube.service;

import lombok.val;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoServices {

    public static void main(String[] args) {
        val fluxAndMonoServices = new FluxAndMonoServices();
        fluxAndMonoServices.fruitsFlux().subscribe();
        fluxAndMonoServices.fruitMono().subscribe();
    }

    public Flux<String> fruitsFlux() {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana")).log();
    }

    public Mono<String> fruitMono() {
        return Mono.just("Mango").log();
    }

    public Flux<String> map() {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .map(String::toUpperCase);
    }

    public Flux<String> filter(int number) {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .filter(s -> s.length() > number);
    }

    public Flux<String> flatMap() {
        // map an element in the stream to a new Publisher
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .flatMap(s -> Flux.just(s.split("")))
                .log();
    }

    public Flux<String> flatMapAsync() {
        // All the Publishers are subscribed at once and their elements arrives asynchronously
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .flatMap(s -> Flux.just(s.split("")))
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)))
                .log();
    }

    public Flux<String> concatMap() {
        // Publishers are subscribed synchronously and elements are in original order
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .flatMap(s -> Flux.just(s.split("")))
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)))
                .log();
    }

    public Mono<List<String>> flatMapMono() {
        // Mono -> Mono
        return Mono.just("Mango")
                .flatMap(s -> Mono.just(List.of(s.split(""))))
                .log();
    }

    public Flux<String> flatMapMany() {
        // Mono -> Flux
        return Mono.just("Mango")
                .flatMapMany(s -> Flux.just(s.split("")))
                .log();
    }

    public Flux<Integer> transform(int number) {
        // Flux<T> -> Flux<O> (T and O can be the same type)
        Function<Flux<String>, Flux<Integer>> stringToLength = stringFlux -> stringFlux.map(String::length);
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .transform(stringToLength)
                .log();
    }

    public Flux<String> defaultIfEmpty(int number) {
        return Flux.<String>empty()
                .defaultIfEmpty("Default");
    }

    public Flux<String> switchIfEmpty() {
        return Flux.<String>empty()
                .switchIfEmpty(Flux.just("Switch"))
                .log();
    }

    /* ----- concat (synchronous, subscribe lazily) ----- */
    public Flux<String> concat() {
        val fruits = Flux.just("Mango", "Orange");
        val veggies = Flux.just("Tomato", "Lemon");
        return Flux.concat(fruits, veggies);
    }

    public Flux<String> concatWith() {
        val fruits = Flux.just("Mango", "Orange");
        val veggies = Flux.just("Tomato", "Lemon");
        return fruits.concatWith(veggies);
    }

    public Flux<String> concatWithMono() {
        val fruits = Mono.just("Mango");
        val veggies = Mono.just("Tomato");
        return fruits.concatWith(veggies);
    }

    /* ----- merge (asynchronous, subscribe eagerly) ----- */
    public Flux<String> merge() {
        val fruits = Flux.just("Mango", "Orange")
                .delayElements(Duration.ofMillis(50));
        val veggies = Flux.just("Tomato", "Lemon")
                .delayElements(Duration.ofMillis(75));
        return Flux.merge(fruits, veggies);
    }

    public Flux<String> mergeWith() {
        val fruits = Flux.just("Mango", "Orange")
                .delayElements(Duration.ofMillis(50));
        val veggies = Flux.just("Tomato", "Lemon")
                .delayElements(Duration.ofMillis(75));
        return fruits.mergeWith(veggies);
    }

    public Flux<String> mergeSequential() {
        // subscribed eagerly, but still sequential
        val fruits = Flux.just("Mango", "Orange")
                .delayElements(Duration.ofMillis(50));
        val veggies = Flux.just("Tomato", "Lemon")
                .delayElements(Duration.ofMillis(75));
        return Flux.mergeSequential(fruits, veggies);
    }

    /* ----- zip (keep zipping until one source completes) ----- */
    public Flux<String> zip() {
        val fruits = Flux.just("Mango", "Orange");
        val veggies = Flux.just("Tomato", "Lemon");
        return Flux.zip(fruits, veggies, (first, second) -> first + second);
    }

    public Flux<String> zipWith() {
        val fruits = Flux.just("Mango", "Orange");
        val veggies = Flux.just("Tomato", "Lemon");
        return fruits.zipWith(veggies, (first, second) -> first + second);
    }

    public Flux<Tuple2<String, String>> zipTuple() {
        val fruits = Flux.just("Mango", "Orange");
        val veggies = Flux.just("Tomato", "Lemon");
        return fruits.zipWith(veggies);
    }

    /* ----- doOn* (side effects) ----- */
    public Flux<String> doOn(int number) {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .filter(s -> s.length() > number)
                .doOnNext(s -> System.out.println("s = " + s))
                .doOnSubscribe(subscription -> System.out.println("subscription = " + subscription))
                .doOnComplete(() -> System.out.println("Completed!"));
    }

    public Flux<String> doOnError(int number) {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .map(s -> {
                    if(s.equalsIgnoreCase("Mango"))
                        throw new RuntimeException("Exception occurred!");
                    return s.toUpperCase();
                })
                .doOnError(throwable -> System.out.println("throwable = " + throwable));
    }

    /* ----- onError* ----- */
    public Flux<String> onErrorReturn() {
        return Flux.just("Apple", "Mango", "Orange")
                .concatWith(Flux.error(new RuntimeException("Exception occurred!")))
                .onErrorReturn("Orange");
    }

    public Flux<String> onErrorContinue() {
        return Flux.just("Apple", "Mango", "Orange")
                .map(s -> {
                    if (s.equalsIgnoreCase("Mango"))
                        throw new RuntimeException("Exception occurred!");
                    return s.toUpperCase();
                })
                .onErrorContinue((exception, object) -> {
                    System.out.println("exception = " + exception);
                    System.out.println("object = " + object);
                });
    }
    public Flux<String> onErrorMap() {
        return Flux.just("Apple", "Mango", "Orange")
                .checkpoint("Error checkpoint1")
                .map(s -> {
                    if (s.equalsIgnoreCase("Mango"))
                        throw new RuntimeException("Exception occurred!");
                    return s.toUpperCase();
                })
                .checkpoint("Error checkpoint2")
                .onErrorMap(throwable -> {
                    System.out.println("throwable = " + throwable);
                    return new IllegalStateException("From onErrorMap");
                });
    }

    /* ----- collect ----- */


}
