package org.daveydebruyn.datagenerator.generators;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.UUID;

public class RandomGenerators {

    private static Random randomGenerator = new Random();

    private static int afterComma = 0;

    public static Integer generateRandomNumber(Integer min, Integer max) {
        return (min != null && max != null) ? randomGenerator.nextInt(max - min) + min      :
               (min != null && max == null) ? randomGenerator.nextInt((min*2) - min) + min  :
               (min == null && max != null) ? randomGenerator.nextInt(max)                  : randomGenerator.nextInt(10);
    }

    public static double generateRandomDecimal(Double max, Double min) {
        if(afterComma == 0) {
            String[] minArray = min.toString().split("\\.");
            String[] maxArray = max.toString().split("\\.");

            afterComma = (minArray[1].length() > maxArray[1].length()) ? minArray[1].length() : maxArray[1].length();
        }

        String formatted = String.format("%." + afterComma + "f", min + randomGenerator.nextDouble() * (max - min));
        formatted = formatted.replaceAll(",",".");
        return Double.parseDouble(formatted);
    }

    public static String generateRandomString(boolean toUpperCase, int size) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(alphabet.charAt(randomGenerator.nextInt(alphabet.length())));
        }

        return (toUpperCase) ? sb.toString().toUpperCase() : sb.toString();
    }

    public static UUID generateRandomUUID() {
        return UUID.randomUUID();
    }

    public static boolean generateRandomBoolean() {
        return randomGenerator.nextBoolean();
    }
    
    public static Calendar generateRandomDate(Calendar beforeDate, Calendar afterDate, int interval) throws Exception {
        return afterDate;
//        Calendar generatedDate = null;
//        if(beforeDate != null && afterDate != null) {
//            if(beforeDate.after(afterDate)) {
//                generatedDate = new Calendar(
//                        generateRandomNumber(afterDate.getYear(), beforeDate.getYear()),
//                        generateRandomNumber(afterDate.getMonth(), beforeDate.getMonth()),
//                        generateRandomNumber(afterDate.getDay(), beforeDate.getDay()));
//            } else {
    }

    //TODO: Make this work with interval.
    public static Date generateRandomDate(Date beforeDate, Date afterDate, int interval) throws Exception {
        Date generatedDate = null;
        if(beforeDate != null && afterDate != null) {
            if(beforeDate.after(afterDate)) {
                generatedDate = new Date(
                        generateRandomNumber(afterDate.getYear(), beforeDate.getYear()),
                        generateRandomNumber(afterDate.getMonth(), beforeDate.getMonth()),
                        generateRandomNumber(afterDate.getDay(), beforeDate.getDay()));
            } else {
                throw new Exception("Can't generate a date because " + beforeDate + " must be after " + afterDate + ".");
            }
        } else if (beforeDate != null && afterDate == null) {
            generatedDate = new Date(
                    generateRandomNumber(null, beforeDate.getYear()),
                    generateRandomNumber(null, beforeDate.getMonth()),
                    generateRandomNumber(null, beforeDate.getDay()));
        } else if (beforeDate == null && afterDate != null) {
            generatedDate = new Date(
                    generateRandomNumber(afterDate.getYear(), null),
                    generateRandomNumber(afterDate.getMonth(), null),
                    generateRandomNumber(afterDate.getDay(), null));
        } else {
            Date date = new Date();

            int year = date.getYear();
            int month = generateRandomNumber(0, 12);
            int day = 1;

            // Create a calendar object and set year and month
            Calendar mycal = new GregorianCalendar(year, month, day);

            // Get the number of days in that month
            int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

            generatedDate = new Date(
                    generateRandomNumber(date.getYear() -10, date.getYear() + 10),
                    month,
                    generateRandomNumber(1, daysInMonth+1));
        }
        return generatedDate;
    }

    public static String generateNull() {
        return "null";
    }

}
