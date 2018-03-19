package com.peter.concurrency.example;

import com.peter.concurrency.annotations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

@Slf4j
@ThreadSafe
public class AtomicExample2 {


    private static AtomicReference<Integer> count = new AtomicReference<>();

    private static AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(AtomicExample2.class, "value");


    @Getter
    private volatile int value = 100;

    public static void main(String[] args) throws InterruptedException {
        AtomicExample2 example2 = new AtomicExample2();

        int localValue = 100;

        AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(localValue, 0);
        stampedReference.compareAndSet(localValue, 102, 0, 1);

        updater.compareAndSet(example2, 100, 120);
        count.compareAndSet(0, 10);
    }

}
