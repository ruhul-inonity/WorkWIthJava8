package com.inonity;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author ruhul
 */

public class StreamsInAction {


    /**
     * Enable one single method from main and check its method body
     */

    public enum SUBJECT {
        MATH,
        SCIENCE,
        GEOGRAPHY,
        ENGLISH,
        LITERATURE
    }

    public static void main(String[] args) {

        /**
         * procedure to consume streams
         */
        //consumingStream();

        /**
         * creating a frequency map
         */

        //createFrequencyMap();

        /**
         * creating infinite stream
         */
        //infiniteStream();

        /**
         * collect elements of a stream into a collection
         */
        //collectStreamIntoCollection();

        /**
         * Collecting Elements using toMap
         */
        //convertObjectToMap();

        /**
         * Collecting Elements to Map of Collections
         */
        //convertObjectToCollectionOfMap();

        /**
         * Flatten (concat)streams with flatmap
         */
        //concatStreams();

        /**
         * Grouping by value
         */
        //groupByValue();

        /**
         * Joining a stream to a single String
         */
        joinStreamToString();
    }

    /**
     * A use case that comes across frequently, is creating a String from a stream, where the stream-items are separated
     * by a certain character. The Collectors.joining() method can be used for this
     */

    private static void joinStreamToString() {
        System.out.println(".................. Join stream to string ......................................................");

        Stream<String> fruitStream = Stream.of("apple", "banana", "pear", "kiwi", "orange");
        String result = fruitStream.filter(s -> s.contains("a"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.joining(", "));
        System.out.println(result);
    }


    /**
     * Collectors.groupingBy when you need to perform the equivalent of a database cascaded "group by"
     * operation
     */
    private static void groupByValue() {

        System.out.println(".................. group by value ......................................................");

        List<Student> list = new ArrayList<>();
        list.add(new Student("Jack", SUBJECT.MATH.name(), 35.0));
        list.add(new Student("Jack", SUBJECT.SCIENCE.name(), 12.9));
        list.add(new Student("Jack", SUBJECT.GEOGRAPHY.name(), 37.0));

        list.add(new Student("Sparrow", SUBJECT.ENGLISH.name(), 87.0));
        list.add(new Student("Sparrow", SUBJECT.GEOGRAPHY.name(), 77.0));
        list.add(new Student("Sparrow", SUBJECT.MATH.name(), 59.0));
        list.add(new Student("Sparrow", SUBJECT.LITERATURE.name(), 12.0));


        list.add(new Student("Captain", SUBJECT.LITERATURE.name(), 19.0));

        Map<String, List<String>> map = list.stream()
                .collect(Collectors.groupingBy(Student::getName,
                                Collectors.mapping(Student::getSubject, Collectors.toList())));
        System.out.println(map);
    }

    /**
     * A Stream of items that are in turn streamable can be flattened into a single continuous Stream
     */
    private static void concatStreams() {
        System.out.println("..................Convert list of Items into a single Lis ......................................................");

        List<String> list1 = Arrays.asList("one", "two");
        List<String> list2 = Arrays.asList("three", "four", "five");
        List<String> list3 = Arrays.asList("six");
        List<String> finalList = Stream.of(list1, list2,
                list3).flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(finalList);

        System.out.println(".......................Combine values of list in a single map......................................................");

        Map<String, List<Integer>> map = new LinkedHashMap<>();
        map.put("a", Arrays.asList(1, 2, 3));
        map.put("b", Arrays.asList(4, 5, 6));
        List<Integer> allValues = map.values() // Collection<List<Integer>>
                .stream()// Stream<List<Integer>>
                .flatMap(List::stream)// Stream<Integer>
                .collect(Collectors.toList());
        System.out.println(allValues);

        System.out.println(".......................Combine List of Map into a single continuous Stream......................................................");

        List<Map<String, String>> list = new ArrayList<>();
        Map<String,String> map1 = new HashMap();
        map1.put("1", "one");
        map1.put("2", "two");
        Map<String,String> map2 = new HashMap();
        map2.put("3", "three");
        map2.put("4", "four");
        list.add(map1);
        list.add(map2);
        Set<String> output= list.stream()
                .map(Map::values)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        System.out.println(output);
    }

    /**
     * Often it requires to make a map of list out of a primary list. Example: From a student of list, we need to make a map
     * of list of subjects for each student.
     */
    private static void convertObjectToCollectionOfMap() {

        System.out.println(".................. object to collection of map ......................................................");

        List<Student> list = new ArrayList<>();
        list.add(new Student("Jack", SUBJECT.MATH.name(), 35.0));
        list.add(new Student("Jack", SUBJECT.SCIENCE.name(), 12.9));
        list.add(new Student("Jack", SUBJECT.GEOGRAPHY.name(), 37.0));

        list.add(new Student("Sparrow", SUBJECT.ENGLISH.name(), 87.0));
        list.add(new Student("Sparrow", SUBJECT.GEOGRAPHY.name(), 77.0));
        list.add(new Student("Sparrow", SUBJECT.MATH.name(), 59.0));
        list.add(new Student("Sparrow", SUBJECT.LITERATURE.name(), 12.0));


        list.add(new Student("Captain", SUBJECT.LITERATURE.name(), 19.0));

        Map<String, List<String>> subjectMap = new HashMap<>();
        list.stream().forEach(s -> {
            subjectMap.computeIfAbsent(s.getName(), x -> new ArrayList<>()).add(s.getSubject());
        });
        System.out.println(subjectMap.toString());


        System.out.println(".................. object to collection map sorted by subject and number ......................................................");

        Map<String, Map<String, List<Double>>> map = new HashMap<>();
        list.stream().forEach(student -> map.computeIfAbsent(student.getName(), s -> new HashMap<>())
                .computeIfAbsent(student.getSubject(), s -> new ArrayList<>())
                .add(student.getMarks()));
        System.out.println(map);
    }

    /**
     * Collector accumulates elements into a Map, Where key is the Student Id and Value is Student Value.
     */
    private static void convertObjectToMap() {

        System.out.println(".................. object to map ......................................................");

        List<Student> students = new ArrayList<Student>();
        students.add(new Student(1, "test1"));
        students.add(new Student(2, "test2"));
        students.add(new Student(3, "test3"));
        students.add(new Student(4, "test4"));

        Map<Integer, String> IdToName = students.stream()
                .collect(Collectors.toMap(Student::getId, Student::getName));
        System.out.println(IdToName);
    }


    /**
     * Elements from a Stream can be easily collected into a container by using the Stream.collect operation
     */
    private static void collectStreamIntoCollection() {
        System.out.println(".................. Stream.collect operation......................................................");

        System.out.println(Arrays
                .asList("apple", "banana", "pear", "kiwi", "orange")
                .stream()
                .filter(s -> s.contains("a"))
                //.filter(s -> s.equals("pear"))
                .collect(Collectors.toList())
        );

        System.out.println(".................. Stream.collect using method reference ......................................................");

        // syntax with method reference
        List<String> stringsList = Arrays.asList("FOO", "BAR", "Happy", "Homes", "bleh", "something");
        List<String> newStringList;
        newStringList = stringsList
                .stream()
                .filter(s -> s != null && s.length() > 3)
                .collect(Collectors.toCollection(ArrayList::new)
                );
        System.out.println(newStringList.toString());

        // syntax with lambda
       /* System.out.println(stringsList
                .stream()
                .filter(s -> s != null && s.length() <= 3)
                .collect(Collectors.toCollection(() -> new LinkedHashSet<>()))
        );*/
    }

    /**
     * generating infinite streams and printing it with limit
     */
    private static void infiniteStream() {
        System.out.println(".................. infinite using iterate......................................................");

        IntStream naturalNumbers = IntStream.iterate(1, x -> x + 1); // Generate infinite stream - 1, 2, 3, 4, 5, 6, 7, ...
        naturalNumbers.limit(5).forEach(System.out::println);// Print out only the first 5 terms

        System.out.println(".....................infinite using generate......................................................");
        //another way of generating infinite streams is using Stream.generate method
        Stream<Double> infiniteRandomNumbers = Stream.generate(Math::random);
        infiniteRandomNumbers.limit(10).forEach(System.out::println);

    }


    /**
     * classifying objects and grouping them
     */
    private static void createFrequencyMap() {
        System.out.println(".................. frequency map ......................................................");

        Stream.of("apple", "orange", "banana", "orange", "coconut")
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .forEach(System.out::println);
    }

    private static void consumingStream() {
        System.out.println(".................. consuming stream ......................................................");
        //example 1
        IntStream.range(1, 10).filter(a -> a % 2 == 0).forEach(System.out::println);

        //example 2
        List<String> list = Arrays.asList("FOO", "BAR", "Happy", "Homes");
        Iterable<String> iterable = () -> list.stream().map(String::toLowerCase).iterator();
        for (String str : iterable) {
            System.out.println(str);
        }
    }

}
