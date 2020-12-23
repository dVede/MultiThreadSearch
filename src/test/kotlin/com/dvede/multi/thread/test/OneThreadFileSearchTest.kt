package com.dvede.multi.thread.test

import com.dvede.multi.thread.prog.OneThreadFileSearch.Companion.searchFile1
import com.dvede.multi.thread.prog.OneThreadFileSearch.Companion.searchFile2
import com.dvede.multi.thread.prog.OneThreadFileSearch.Companion.searchFile3
import com.dvede.multi.thread.prog.OneThreadFileSearch.Companion.searchFile4
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.nio.file.Path

internal class OneThreadFileSearchTest {
    @Test
    fun searchTest(){
        val result1: List<File> = searchFile1(System.getProperty("user.home"), "Dockerfile")
        val result2: List<File> = searchFile2(System.getProperty("user.home"), "Dockerfile")
        val result3: List<File> = searchFile3(System.getProperty("user.home"), "Dockerfile")
        val result4: List<Path> = searchFile4(System.getProperty("user.home"), "Dockerfile")

        assertEquals(result1.sorted(), result2.sorted())
        assertEquals(result1.sorted(), result3.sorted())
        assertEquals(result1.sorted().toString(), result4.sorted().toString())
        assertEquals(result2.sorted(), result3.sorted())
        assertEquals(result2.sorted().toString(), result4.sorted().toString())
        assertEquals(result3.sorted().toString(), result4.sorted().toString())
    }

    @Test
    fun searchIncorrectPathTest(){
        val excepted: List<File> = emptyList()
        val result1: List<File> = searchFile1("/homa", "Dockerfile")
        val result2: List<File> = searchFile2("/homa", "Dockerfile")
        val result3: List<File> = searchFile3("/homa", "Dockerfile")
        val result4: List<Path> = searchFile4("/homa", "Dockerfile")
        print(result1)
        assertEquals(result1, excepted)
        assertEquals(result1, result2)
        assertEquals(result1, result3)
        assertEquals(result1, result4)
        assertEquals(result2, result3)
        assertEquals(result2, result4)
        assertEquals(result3, result4)
    }
}