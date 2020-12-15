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
        val result1: ArrayList<File> = searchFile1(System.getProperty("user.home"), "Dockerfile")
        val result2: ArrayList<File> = searchFile2(System.getProperty("user.home"), "Dockerfile")
        val result3: ArrayList<File> = searchFile3(System.getProperty("user.home"), "Dockerfile")
        val result4: ArrayList<Path> = searchFile4(System.getProperty("user.home"), "Dockerfile")
        assertEquals(result1.sort(), result2.sort())
        assertEquals(result1.sort(), result3.sort())
        assertEquals(result1.sort(), result4.sort())
        assertEquals(result2.sort(), result3.sort())
        assertEquals(result2.sort(), result4.sort())
        assertEquals(result3.sort(), result4.sort())
    }

    @Test
    fun searchIncorrectPathTest(){
        val excepted: ArrayList<File> = ArrayList()
        val result1: ArrayList<File> = searchFile1("/homa", "Dockerfile")
        val result2: ArrayList<File> = searchFile2("/homa", "Dockerfile")
        val result3: ArrayList<File> = searchFile3("/homa", "Dockerfile")
        val result4: ArrayList<Path> = searchFile4("/homa", "Dockerfile")
        assertEquals(result1.sort(), excepted.sort())
        assertEquals(result1.sort(), result2.sort())
        assertEquals(result1.sort(), result3.sort())
        assertEquals(result1.sort(), result4.sort())
        assertEquals(result2.sort(), result3.sort())
        assertEquals(result2.sort(), result4.sort())
        assertEquals(result3.sort(), result4.sort())
    }
}