package com.dvede.multi.thread.test

import com.dvede.multi.thread.prog.MultiThreadFileSearch
import com.dvede.multi.thread.prog.OneThreadFileSearch.Companion.searchFile2
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class MultiThreadFileSearchTest {

    @Test
    fun fileSearchTest(){
        val result1: List<File> = MultiThreadFileSearch.grep(System.getProperty("user.home"), "Dockerfile")
        MultiThreadFileSearch.grep(System.getProperty("user.home"), "Dockerfile")
        val result3: List<File> = searchFile2(System.getProperty("user.home"), "Dockerfile")
        assertEquals(result1, result3.sorted())
    }

    @Test
    fun fileSearchIncorrectPathTest(){
        val result1: List<File> = MultiThreadFileSearch.grep("/david", "Dockerfile")
        val result2: List<File> = searchFile2("/david", "Dockerfile")
        assertEquals(result1, result2.sorted())
    }
}