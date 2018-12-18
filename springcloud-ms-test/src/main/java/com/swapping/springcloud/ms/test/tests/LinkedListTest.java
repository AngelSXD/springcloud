package com.swapping.springcloud.ms.test.tests;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LinkedListTest {


    public Apple getApple(){
        return new Apple();
    }

    class Apple{

        private String uid;

        private String name;

        private int id;



        public Apple() {
        }

        public Apple(String uid, String name) {
            this.uid = uid;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }



    public static void main(String[] args) {
        List<Apple> list = new ArrayList<>();
        LinkedListTest test = new LinkedListTest();

        long startDate1 = System.currentTimeMillis();

        for (int i=0; i < 50000; i++){
            Apple apple = test.getApple();

            apple.setId(i);
            apple.setUid( i%3==0 ? "123" : UUID.randomUUID().toString());
            apple.setName("苹果"+i+"号");

            list.add(apple);
        }

        long endDate1 = System.currentTimeMillis();

        System.out.println("生成消耗时间："+(endDate1-startDate1));





        long startDate = System.currentTimeMillis();

        list = list.parallelStream().filter(distinctByKey(Apple::getUid)).collect(Collectors.toList());
        long endDate = System.currentTimeMillis();

        System.out.println("消耗时间："+(endDate-startDate));
        System.out.println("集合大小："+list.size());
    }


    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
