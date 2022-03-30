package me.zixuan.reactive.simple;

import org.testng.annotations.Test;
import reactor.core.publisher.DirectProcessor;

class ProcessorTest {


    @Test
    void testDirectProcessor() {
        DirectProcessor<Long> data = DirectProcessor.create();
    }

    @Test
    void testUnicastProcessor() {
    }

    @Test
    void testEmitterProcessor() {
    }

    @Test
    void testReplayProcessor() {
    }

    @Test
    void testTopicProcessor() {
    }

    @Test
    void testWorkQueueProcessor() {
    }

}
