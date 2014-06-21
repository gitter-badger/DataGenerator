package org.daveydebruyn.datagenerator;

import java.util.Arrays;
import java.util.Random;

import org.daveydebruyn.datagenerator.generators.RandomGenerators;
import org.junit.Test;

public class RandomGeneratorsTest {

    private Random randomGenerator;

    @Test
    public void testGenerateRandomNumber() {
        int[] test1Array = new int[20];
        int[] test2Array = new int[20];
        for (int i = 0; i < 20; i++) {
            test1Array[i] = RandomGenerators.generateRandomNumber(null, null);
        }
        for (int i = 0; i < 20; i++) {
            test2Array[i] = RandomGenerators.generateRandomNumber(0, 99);
        }
        System.out.println(Arrays.toString(test1Array));
        System.out.println(Arrays.toString(test2Array));
    }

    public void testGenerateRandomDecimal() {
        double[] test1Array = new double[20];
        for (int i = 0; i < 20; i++) {
            test1Array[i] = RandomGenerators.generateRandomDecimal(0.0D, 99.9999D);
        }
        System.out.println(Arrays.toString(test1Array));
    }

    @Test
    public void testGenerateRandomLetter() {
        String[] test1Array = new String[10];
        String[] test2Array = new String[10];
        for (int i = 0; i < 10; i++) {
            test1Array[i] = RandomGenerators.generateRandomString(true, 10);
        }
        for (int i = 0; i < 10; i++) {
            test2Array[i] = RandomGenerators.generateRandomString(false, 1);
        }
        System.out.println(Arrays.toString(test1Array));
        System.out.println(Arrays.toString(test2Array));
    }
}
