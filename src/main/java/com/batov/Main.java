package com.batov;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * 2+2 работает
 * 2 + 2 работает
 * 2+ 2 работает
 * Х + Х работает
 * Х+Х работает
 * Х+ Х работает
 * маленькие буквы не работают (х+х)
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение:");
        String input = scanner.nextLine();
        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) {
        String[] tokens = input.split("\\s*([+\\-*/])\\s*");
        if (tokens.length != 2) {
            throw new IllegalArgumentException("Некорректное выражение");
        }

        int num1 = convertToNumber(tokens[0]);
        int num2 = convertToNumber(tokens[1]);
        char operator = input.replaceAll("\\s*[IVXLCDM0-9]+\\s*", "").charAt(0);

        validateInput(tokens[0], tokens[1], num1, num2);

        int result = calculate(num1, num2, operator);

        return convertToOutputFormat(result, tokens[0]);
    }

    private static void validateInput(String token1, String token2, int num1, int num2) {
        boolean isRoman1 = isRoman(token1);
        boolean isRoman2 = isRoman(token2);

        if (isRoman1 != isRoman2) {
            throw new IllegalArgumentException("Числа должны быть одного типа (либо оба римские, либо оба арабские)");
        }
        if ((num1 < 1 || num1 > 10) || (num2 < 1 || num2 > 10)) {
            throw new IllegalArgumentException("Числа должны быть в диапазоне от 1 до 10 включительно");
        }
    }

    private static int convertToNumber(String str) {
        if (isRoman(str)) {
            return romanToArabic(str);
        } else {
            return Integer.parseInt(str);
        }
    }

    private static boolean isRoman(String str) {
        return str.matches("\\s*[IVXLCDM]+\\s*");
    }

    private static int romanToArabic(String roman) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int curValue = romanMap.get(roman.charAt(i));
            if (curValue < prevValue) {
                result -= curValue;
            } else {
                result += curValue;
            }
            prevValue = curValue;
        }

        return result;
    }

    private static int calculate(int num1, int num2, char operator) {
        return switch (operator) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            case '/' -> num1 / num2;
            default -> throw new IllegalArgumentException("Неверная операция: " + operator);
        };
    }

    private static String convertToOutputFormat(int result, String num1) {
        if (isRoman(num1)) {
            return arabicToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    private static String arabicToRoman(int number) {
        String[] units = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] hundreds = {"", "C"};

        return hundreds[number / 100] +
                tens[(number % 100) / 10] +
                units[number % 10];
    }


}