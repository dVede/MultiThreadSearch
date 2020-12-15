package com.dvede.multi.thread.prog

import java.util.concurrent.BlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class MyThreadPoolExecutor(coorPoolSize: Int, maxPoolSize: Int, keepAliveTime: Long, seconds: TimeUnit?, queue: BlockingQueue<Runnable?>?) : ThreadPoolExecutor(coorPoolSize, maxPoolSize, keepAliveTime, seconds, queue) {

    private val executing = AtomicInteger(0)

    override fun execute(command: Runnable) {
        executing.incrementAndGet()
        super.execute(command)
    }

    override fun afterExecute(r: Runnable?, t: Throwable?) {
        super.afterExecute(r, t)
        val count: Int = executing.decrementAndGet()
        if (count == 0) {
            shutdown()
        }
    }
}