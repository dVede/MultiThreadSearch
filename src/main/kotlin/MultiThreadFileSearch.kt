package com.dvede.multi.thread.prog

import org.apache.log4j.Logger
import java.io.File
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class MultiThreadFileSearch {
    companion object {
        private val N_THREADS = Runtime.getRuntime().availableProcessors() + 1
        private val log: Logger = Logger.getLogger(MultiThreadFileSearch::class.java)
        private var files1: MutableList<File> = Collections.synchronizedList(mutableListOf<File>())
        private lateinit var pool: MyThreadPoolExecutor

        fun grep(path: String, pattern: String): ArrayList<File> {
            val array: ArrayList<File> = ArrayList()
            log.info("------------MultiThreadFileSearch-----------")
            log.info("Directory path: $path")
            log.info("Search pattern: $pattern")
            val dir = File(path)
            when {
                dir.exists() -> {
                    pool = MyThreadPoolExecutor(N_THREADS, N_THREADS, 60L, TimeUnit.SECONDS, LinkedBlockingQueue())
                    val startTime = System.currentTimeMillis()
                    fileSearch(dir, pattern)
                    while (!pool.isShutdown) { }
                    val endTime = System.currentTimeMillis() - startTime
                    log.info("--------------Search Completed--------------")
                    log.info("Time elapsed: $endTime ms")
                    array.addAll(files1)
                    array.sort()
                    log.info("Files were found: ${array.size}")
                    array.forEach {
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
            files1.clear()
            log.info("--------------------END----------------------\n")
            return array
        }

        private fun fileSearch(dir: File, pattern: String) {
            if (dir.listFiles() != null) {
                val files: Array<File> = dir.listFiles()!!
                for (i in files.indices) {
                    if (files[i].isDirectory) {
                        pool.execute { fileSearch(files[i], pattern) }
                    } else if (files[i].name.contains(pattern, ignoreCase = true))
                        files1.add(files[i])
                }
            }
        }
    }
}