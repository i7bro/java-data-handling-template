package com.epam.izh.rd.online.service;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleTextService implements TextService {

    /**
     * Реализовать функционал удаления строки из другой строки.
     *
     * Например для базовой строки "Hello, hello, hello, how low?" и строки для удаления ", he"
     * метод вернет "Hellollollo, how low?"
     *
     * @param base - базовая строка с текстом
     * @param remove - строка которую необходимо удалить
     */
    @Override
    public String removeString(String base, String remove) {
        Pattern pattern = Pattern.compile(remove);
        String[] str = pattern.split(base);
        return concatenate(str);
    }

    /**
     * Реализовать функционал проверки на то, что строка заканчивается знаком вопроса.
     *
     * Например для строки "Hello, hello, hello, how low?" метод вернет true
     * Например для строки "Hello, hello, hello!" метод вернет false
     */
    @Override
    public boolean isQuestionString(String text) {
        return text.matches(".*\\?$");
    }

    /**
     * Реализовать функционал соединения переданных строк.
     *
     * Например для параметров {"Smells", " ", "Like", " ", "Teen", " ", "Spirit"}
     * метод вернет "Smells Like Teen Spirit"
     */
    @Override
    public String concatenate(String... elements) {
        StringBuilder sb = new StringBuilder();
        for (String str : elements) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * Реализовать функционал изменения регистра в вид лесенки.
     * Возвращаемый текст должен начинаться с прописного регистра.
     *
     * Например для строки "Load Up On Guns And Bring Your Friends"
     * метод вернет "lOaD Up oN GuNs aNd bRiNg yOuR FrIeNdS".
     */
    @Override
    public String toJumpCase(String text) {
        String[] strings = new String[text.length()];

        for (int i = 0; i < strings.length; i++) {
            String temp = String.valueOf(text.charAt(i));
            if (!temp.matches("[a-zA-Zа-яА-Я]")) {
                strings[i] = temp;
                continue;
            }
            strings[i] = (i % 2) == 0 ? temp.toLowerCase() : temp.toUpperCase();
        }
        return String.join("", strings);
    }

    /**
     * Метод определяет, является ли строка палиндромом.
     *
     * Палиндром - строка, которая одинаково читается слева направо и справа налево.
     *
     * Например для строки "а роза упала на лапу Азора" вернется true, а для "я не палиндром" false
     */
    @Override
    public boolean isPalindrome(String string) {
        String temp = removeString(string, "[^a-zA-Zа-яА-Я]");
        String tempRevert = new StringBuilder(temp).reverse().toString();
        return !temp.equals("") && temp.equalsIgnoreCase(tempRevert);
    }
}