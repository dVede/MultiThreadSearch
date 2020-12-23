package com.dvede.multi.thread.test;

import com.dvede.multi.thread.prog.MultiThreadFileSearch;
import com.dvede.multi.thread.prog.OneThreadFileSearch;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;
import java.util.List;

@BenchmarkMode(Mode.All)
@Warmup(iterations = 10)
@Measurement(iterations = 100, batchSize = 10)
public class BMTest {

    @State(Scope.Benchmark)
    public static class BenchMarkState {
        String file = System.getProperty("user.home");
        String pattern = "Dockerfile";
    }

    @Benchmark
    public void multiThreadFileSearch(Blackhole blackhole, BenchMarkState state) {
        List<File> result = MultiThreadFileSearch.Companion.grep(state.file, state.pattern);
        blackhole.consume(result);
    }

    @Benchmark
    public void oneThreadFileSearch(Blackhole blackhole, BenchMarkState state) {
        List<File> result = OneThreadFileSearch.Companion.searchFile2(state.file, state.pattern);
        blackhole.consume(result);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BMTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
