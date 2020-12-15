package com.dvede.multi.thread.prog

import java.io.File
import java.nio.file.DirectoryStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList

class OneThreadFileSearch {
    companion object {
        private val result: ArrayList<File> = ArrayList()
        private val result2: ArrayList<Path> = ArrayList()

        @JvmStatic
        fun searchFile1(path: String, pattern: String) : ArrayList<File> {
            val dir = File(path)
            if (dir.listFiles() != null) {
                val fileList: Array<File> = dir.listFiles()!!
                fileList.indices.forEach { i ->
                    if (fileList[i].isDirectory) {
                        searchFile1(fileList[i].toString(), pattern)
                    } else if (fileList[i].name.contains(pattern, ignoreCase = true)) {
                        result.add(fileList[i])
                    }
                }
            }
            return result
        }

        @JvmStatic
        fun searchFile2(path: String, pattern: String) : ArrayList<File>  {
            val directory = File(path)
            val stack: Stack<File> = Stack()
            stack.push(directory)
            while (!stack.isEmpty()) {
                val child: File = stack.pop()
                if (child.isDirectory) {
                    child.listFiles()?.forEach { f ->
                        stack.push(f)
                    }
                } else if (child.name.contains(pattern, ignoreCase = true))
                    result.add(child)
            }
            return result
        }

        @JvmStatic
        fun searchFile3(path: String, pattern: String) : ArrayList<File> {
            File(path).walk()
                .filter { it.isFile }
                .filter { it.name.contains(pattern, ignoreCase = true) }
                .forEach { result.add(it) }
            return result
        }

        @JvmStatic
        fun searchFile4(path: String, pattern: String) : ArrayList<Path> {
            val dir = Paths.get(path)
            val stack: Stack<Path> = Stack()
            stack.push(dir)
            while (!stack.isEmpty()) {
                val child: Path = stack.pop()
                if (Files.isDirectory(child)) {
                    if (Files.isReadable(child)) {
                        val directoryFiles: DirectoryStream<Path> = Files.newDirectoryStream(child)
                        directoryFiles.forEach { path1 ->
                            stack.push(path1)
                        }
                        directoryFiles.close()
                    }
                } else if (child.fileName.toString().contains(pattern, ignoreCase = true))
                    result2.add(child)
            }
            return result2
        }
    }
}
