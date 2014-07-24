package org.daveydebruyn.datagenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.daveydebruyn.datagenerator.generators.RandomGenerators;
import org.junit.Test;

public class RandomGeneratorsTest {

    @Test
    public void testGenerateRandomNumber() {
        List<Integer> test1Array = new ArrayList<Integer>();
        List<Integer> test2Array = new ArrayList<Integer>();
        List<Integer> test3Array = new ArrayList<Integer>();
        List<Integer> test4Array = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            test1Array.add(RandomGenerators.generateRandomNumber(null, null));
        }
        for (int i = 0; i < 10; i++) {
            test2Array.add(RandomGenerators.generateRandomNumber(10, null));
        }
        for (int i = 0; i < 10; i++) {
            test3Array.add(RandomGenerators.generateRandomNumber(null, 99));
        }
        for (int i = 0; i < 10; i++) {
            test4Array.add(RandomGenerators.generateRandomNumber(0, 99));
        }
        System.out.println(test1Array);
        System.out.println(test2Array);
        System.out.println(test3Array);
        System.out.println(test4Array);
    }

    @Test
    public void testGenerateRandomDecimal() {
        List<Double> testArray = new ArrayList<Double>();
        for (int i = 0; i < 20; i++) {
            testArray.add(RandomGenerators.generateRandomDecimal(0.0D, 99.9999D));
        }
        System.out.println(testArray);
    }

    @Test
    public void testGenerateRandomString() {
        List<String> test1Array = new ArrayList<String>();
        List<String> test2Array = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            test1Array.add(RandomGenerators.generateRandomString(true, 10));
        }
        for (int i = 0; i < 10; i++) {
            test2Array.add(RandomGenerators.generateRandomString(false, 1));
        }
        System.out.println(test1Array);
        System.out.println(test2Array);
    }

    @Test
    public void testGenerateRandomBoolean() {
         List<Boolean> testArray = new ArrayList<Boolean>();
         for(int i = 0;i<10;i++) {
             testArray.add(RandomGenerators.generateRandomBoolean());
         }
         System.out.println(testArray);
    }
    
    @Test
    public void testGenerateRandomUUID() {
        List<UUID> testArray = new ArrayList<UUID>();
        for(int i = 0;i<10;i++) {
            testArray.add(RandomGenerators.generateRandomUUID());
        }
        System.out.println(testArray);
    }

    @Test
    public void testGenerateRandomDate() throws Exception {
        List<Date> test1Array = new ArrayList<Date>();
        List<Date> test2Array = new ArrayList<Date>();
        for (int i = 0; i < 10; i++) {
//            test1Array.add(RandomGenerators.generateRandomDate(null, null, 0));
        }
        for (int i = 0; i < 10; i++) {
//            test2Array.add(RandomGenerators.generateRandomDate(null, null, 0));
        }
        System.out.println(test1Array);
        System.out.println(test2Array);
    }
}
