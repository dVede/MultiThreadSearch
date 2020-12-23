package com.dvede.multi.thread.prog

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

class OneThreadFileSearch {
    companion object {
        private val resultRec = arrayListOf<File>()

        fun searchFile1(path: String, pattern: String) : List<File> {
            val dir = File(path)
            if (dir.listFiles() != null) {
                val fileList: Array<File> = dir.listFiles()!!
                fileList.indices.forEach { i ->
                    if (fileList[i].isDirectory) {
                        searchFile1(fileList[i].toString(), pattern)
                    } else if (fileList[i].name.contains(pattern, ignoreCase = true)) {
                        resultRec.add(fileList[i])
                    }
                }
            }
            return resultRec
        }

        fun searchFile2(path: String, pattern: String) : List<File>  {
            val result = arrayListOf<File>()
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

        fun searchFile3(path: String, pattern: String) : List<File> {
            val result = arrayListOf<File>()
            File(path).walk()
                .filter { it.isFile }
                .filter { it.name.contains(pattern, ignoreCase = true) }
                .forEach { result.add(it) }
            return result
        }

        fun searchFile4(path: String, pattern: String) : List<Path> {
            val result = arrayListOf<Path>()
            val dir = Paths.get(path)
            val deque: Deque<Path> = ArrayDeque()
            deque.push(dir)
            while (!deque.isEmpty()) {
                val child: Path = deque.pop()
                if (Files.isDirectory(child)) {
                    if (Files.isReadable(child)) {
                        Files.newDirectoryStream(child).use {
                            it.forEach { path1 -> deque .push(path1) }
                        }
                    }
                } else if (child.fileName.toString().contains(pattern, ignoreCase = true))
                    result.add(child)
            }
            return result
        }
    }
}
