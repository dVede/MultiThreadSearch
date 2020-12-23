package com.dvede.multi.thread.prog

import java.util.concurrent.atomic.AtomicInteger

class InverseSemaphore {
    private val executing = AtomicInteger(0)
    private val lock = Object()

    fun beforeSubmit() {
        executing.incrementAndGet()
    }

    fun taskCompleted() {
        val count = executing.decrementAndGet()
        synchronized(lock) {
            if (count == 0) lock.notifyAll()
        }
    }

    fun awaitCompletion() {
        synchronized(lock) { while (executing.get() > 0) lock.wait() }
    }
}