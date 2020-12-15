package com.dvede.multi.thread.test

import com.dvede.multi.thread.prog.MultiThreadFileSearch
import com.dvede.multi.thread.prog.OneThreadFileSearch.Companion.searchFile2
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class MultiThreadFileSearchTest {

    @Test
    fun fileSearchTest(){
        val result1: ArrayList<File> = MultiThreadFileSearch.grep(System.getProperty("user.home"), "Dockerfile")
        MultiThreadFileSearch.grep(System.getProperty("user.home"), "Dockerfile")
        val result3: ArrayList<File> = searchFile2(System.getProperty("user.home"), "Dockerfile")
        assertEquals(result1.sort(), result3.sort())
    }

    @Test
    fun fileSearchIncorrectPathTest(){
        val result1: ArrayList<File> = MultiThreadFileSearch.grep("/david", "Dockerfile")
        val result2: ArrayList<File> = searchFile2("/david", "Dockerfile")
        assertEquals(result1.sort(), result2.sort())
    }
}