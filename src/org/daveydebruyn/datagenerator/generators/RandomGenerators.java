package org.daveydebruyn.datagenerator.generators;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class RandomGenerators {

    private static int afterComma;
    private static String format = "0.";
    private static DecimalFormat df;

    private static Random randomGenerator = new Random();

    // private static void main(String[] args) {
    // for (int i = 0; i < 20; i++) {
    // int max = randomGenerator.nextInt(400);
    // int min = 0;
    // int afterComma = randomGenerator.nextInt(9) + 1;
    // System.out.println("Input: " + max + ", " + min + ", " + afterComma);
    // System.out.println("Return: " +
    // RandomGenerators.generateRandomDecimal(max, min, afterComma));
    // }
    // }

    public static String generateRandomString(boolean toUpperCase, int size) {
        // Generate random letter from alphabet and return it as upper or
        // lowercase;
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(alphabet.charAt(randomGenerator.nextInt(alphabet.length())));
        }
        if (toUpperCase) {
            return sb.toString().toUpperCase();
        } else {
            return sb.toString();
        }
    }

    public static Integer generateRandomNumber(Integer min, Integer max) {
        if (max != null && min != null) {
            return randomGenerator.nextInt(max - min) + min;
        }
        return randomGenerator.nextInt(10);
    }

    public static double generateRandomDecimal(Double max, Double min) {
        if (format == "0.") {
            String[] minArray = min.toString().split("\\.");
            String[] maxArray = max.toString().split("\\.");

            if (minArray[1].length() > maxArray[1].length()) {
                afterComma = minArray[1].length();
            } else {
                afterComma = maxArray[1].length();
            }

            for (int i = 0; i < afterComma; i++) {
                format += "0";
            }

            df = new DecimalFormat(format);
        }

        String formate = df.format(min + randomGenerator.nextDouble() * (max - min));

        try {
            return (Double) df.parse(formate);
        } catch (ParseException pe) {
            return min + randomGenerator.nextDouble() * (max - min);
        }

    }

    public static boolean generateRandomBoolean() {
        return randomGenerator.nextBoolean();
    }

    public static Date generateRandomDate(Date beforeDate, Date afterDate, Date interval) throws ParseException {
        int year = randomGenerator.nextInt(2014);
        int month = randomGenerator.nextInt(12);
        GregorianCalendar date = new GregorianCalendar(year, month, 1);
        int day = randomGenerator.nextInt(date.getActualMaximum(date.DAY_OF_MONTH));
        date.set(year, month, day);
        if(beforeDate != null && afterDate != null) {

        } else {

        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(sdf.format(date.getTime()));
        } catch (ParseException e) {
            throw new ParseException("Cannot parse date '" + date.getTime() + "' to format yyyy-MM-dd", day);
        }
    }
}
