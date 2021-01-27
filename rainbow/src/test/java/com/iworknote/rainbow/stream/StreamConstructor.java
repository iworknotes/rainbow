package com.iworknote.rainbow.stream;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 流的构建方式
 *
 * @author YL.huang
 * @date 01/26/2021
 */
public class StreamConstructor {

    /**
     * 通过数值直接构建流
     */
    @Test
    public void streamFromValue() {
        Stream stream = Stream.of(1, 2, 3, "a", "b");
        stream.forEach(System.out::println);
    }

    /**
     * 通过数组构建
     */
    @Test
    public void streamFromArray() {
        int[] numbers = {1, 2, 3, 4, 5};
        IntStream stream = Arrays.stream(numbers);
        stream.forEach(System.out::println);
    }

    /**
     * 通过文件构建
     *
     * @throws IOException
     */
    @Test
    public void streamFromFile() throws IOException {
        String filePath = "/Users/binary/Documents/workspace/iworknotes/rainbow/rainbow/src/test/java/com/iworknote/rainbow/stream/StreamConstructor.java";
        Stream stream = Files.lines(Paths.get(filePath));
        stream.forEach(System.out::println);
    }

    /**
     * 通过函数生成流(无限流)
     */
    @Test
    public void streamFromFunction() {
        Stream stream = Stream.generate(Math::random);

        stream.limit(10)
                .forEach(System.out::println);

        Random random = new Random();
        IntStream intStream = random.ints(0, 100);
        intStream.limit(10).forEach(System.out::println);
    }

}
