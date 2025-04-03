package com.rithiro.personaltracking.utils;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class Helper {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomString(int length) {
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }
        return result.toString();
    }

    public static String calculateDuration(Date startDate, Date endDate) {
        Duration duration = Duration.between(startDate.toInstant(), endDate.toInstant());
        long totalMinutes = duration.toMinutes();
        // long days = totalMinutes / (24 * 60);
        // long hours = (totalMinutes % (24 * 60)) / 60;
        // long minutes = totalMinutes % 60;

        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;

        return "%s h %s mn".formatted(hours, minutes);
    }

    public static <T> List<List<T>> splitList(List<T> list, int chunkSize) {
        if (chunkSize <= 0)
            throw new IllegalArgumentException("Chunk size must be greater than 0");
        int numOfChunks = (int) Math.ceil((double) list.size() / chunkSize);
        return IntStream.range(0, numOfChunks)
                .mapToObj(i -> list.subList(i * chunkSize, Math.min((i + 1) * chunkSize, list.size()))).toList();
    }

    public static String getOperatingSystem(String userAgent) {
        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            if (userAgent.contains("windows"))
                return "Windows";
            else if (userAgent.contains("mac"))
                return "MacsOS";
            else if (userAgent.contains("x11") || userAgent.contains("linux"))
                return "Linux";
            else if (userAgent.contains("android"))
                return "Android";
            else if (userAgent.contains("iphone") || userAgent.contains("ipad"))
                return "iOS";
            else if (userAgent.contains("postman"))
                return "Postman";
            else
                return "Unknown OS";
        } else
            return "Unknown OS";
    }
}
