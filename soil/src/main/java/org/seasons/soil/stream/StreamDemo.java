package org.seasons.soil.stream;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {
    public static void main(String[] args) {

        Stream<String> stringStream = Stream.of("1", "2", "3");

//        Stream<String> sorted = stringStream.sorted();

        Stream<String> filterStream = stringStream.filter(a -> a.equals("2"));
        Stream<Integer> mapStream = filterStream.map(Integer::valueOf);
       mapStream.collect(Collectors.toSet());

    }
}
