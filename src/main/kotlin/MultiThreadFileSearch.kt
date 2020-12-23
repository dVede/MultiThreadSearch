package com.dvede.multi.thread.prog

import org.apache.log4j.Logger
import java.io.File
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class MultiThreadFileSearch {
    companion object {
        private val N_THREADS = Runtime.getRuntime().availableProcessors() + 1
        private val log: Logger = Logger.getLogger(MultiThreadFileSearch::class.java)
        private var files1: MutableList<File> = Collections.synchronizedList(mutableListOf<File>())
        private var inv : InverseSemaphore = InverseSemaphore()
        private lateinit var pool: ThreadPoolExecutor

        fun grep(path: String, pattern: String): List<File> {
            log.info("------------MultiThreadFileSearch-----------")
            log.info("Directory path: $path")
            log.info("Search pattern: $pattern")
            val dir = File(path)
            when {
                dir.exists() -> {
                    files1.clear()
                    pool = ThreadPoolExecutor(N_THREADS, N_THREADS, 60L, TimeUnit.SECONDS, LinkedBlockingQueue())
                    inv.beforeSubmit()
                    val startTime = System.currentTimeMillis()
                    fileSearch(dir, pattern)
                    inv.awaitCompletion()
                    pool.shutdownNow()
                    val endTime = System.currentTimeMillis() - startTime
                    log.info("--------------Search Completed--------------")
                    log.info("Time elapsed: $endTime ms")
                    log.info("Files were found: ${files1.size}")
                    files1.sort()
                    files1.forEach {
                        log.info("Found a file on the path: $it")
                    }
                }
                dir.isFile -> {
                    log.error("-------------------ERROR--------------------")
                    log.error("The file path was used, but the directory path is needed.")
                }
                else -> {
                    log.error("-------------------ERROR--------------------")
                    log.error("Directory with this path was not found.")
                }
            }
            log.info("--------------------END----------------------" + System.lineSeparator())
            return files1.toList()
        }

        private fun fileSearch(dir: File, pattern: String) {
            if (dir.listFiles() != null) {
                val files: Array<File> = dir.listFiles()!!
                for (i in files.indices) {
                    if (files[i].isDirectory) {
                        inv.beforeSubmit()
                        pool.submit{ fileSearch(files[i], pattern) }
                    } else if (files[i].name.contains(pattern, ignoreCase = true))
                        files1.add(files[i])
                }
            }
            inv.taskCompleted()
        }
    }
}