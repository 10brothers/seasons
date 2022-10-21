package stream;

import java.util.stream.Stream;

public class StreamMain {

    public static void main(String[] args) {
        Stream<String> stringStream = Stream.of("1", "2", "3");
        Stream<String> stringStream1 = stringStream.filter(a -> a.equals("1"));
        Stream<Integer> integerStream = stringStream1.map(Integer::valueOf);
        long count = integerStream.count();

        System.out.println(count);

    }

}
