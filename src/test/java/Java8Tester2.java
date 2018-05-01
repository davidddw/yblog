/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 d05660@163.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

enum Type {
    MEAT, FISH, OTHER
}

class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class Java8Tester2 {
    private static List<Dish> menu;

    static {
        menu = Arrays.asList(
                new Dish("pork", false, 800, Type.MEAT),
                new Dish("beef", false, 700, Type.MEAT),
                new Dish("chicken", false, 400, Type.MEAT),
                new Dish("french fries", true, 530, Type.OTHER),
                new Dish("rice", true, 350, Type.OTHER),
                new Dish("season fruit", true, 120, Type.OTHER),
                new Dish("pizza", true, 550, Type.OTHER),
                new Dish("prawns", false, 300, Type.FISH),
                new Dish("salmon", false, 450, Type.FISH) );
    }

    private static void test1() {
        //menu.stream().forEach(System.out::println);
        List<String> lowCaloricDishesName = menu.stream()
                .filter(d -> d.getCalories() < 400)
                .peek(e -> System.out.println("Filtered value: " + e))
                .sorted(Comparator.comparing(Dish::getCalories))
                .peek(e -> System.out.println("Sorted value: " + e))
                .map(Dish::getName)
                .collect(Collectors.toList());
        lowCaloricDishesName.stream().forEach(System.out::println);

        Stream.of("one", "two", "three", "four").peek(e -> System.out.println(e)).forEach(System.out::println);
        IntStream.of(new int[]{1,2,3,4}).forEach(System.out::print);
        lowCaloricDishesName.stream().collect(Collectors.joining()).toString();
        System.out.println("---------------------->");
        Arrays.asList(1,2,3,4).stream().map(n -> n*n).forEach(System.out::print);
        System.out.println("---------------------->");
        Integer[] evens = Stream.of(1,2,3,4,5,6).filter(n -> n%2==0).toArray(Integer[]::new);
        Arrays.asList(evens).forEach(System.out::println);
        System.out.println("---------------------->");
        String text = null;
        int m = Optional.ofNullable(text).map(String::length).orElse(-1);
        System.out.println(m);
        System.out.println("---------------------->");
        String concat = Stream.of(text).reduce(String::concat).orElse("");
        System.out.println(concat);
    }

    public static void main(String[] args) {
        test1();
    }
}
