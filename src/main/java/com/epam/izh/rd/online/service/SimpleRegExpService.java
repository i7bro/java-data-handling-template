package com.epam.izh.rd.online.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        String str = null;
        try (BufferedReader dr = new BufferedReader(new FileReader(
                new File("./src/main/resources/sensitive_data.txt")))) {
            str = dr.readLine();
            Pattern pattern = Pattern.compile("[0-9]{4}\\W[0-9]{4}\\W[0-9]{4}\\W[0-9]{4}");
            Matcher matcher = pattern.matcher(str);
            StringBuilder sbTemp;
            while (matcher.find()) {
                sbTemp = new StringBuilder(matcher.group().replaceAll("\\W", ""));
                sbTemp.replace(4, 12, " **** **** ");
                str = str.replace(matcher.group(), sbTemp.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        String str = null;
        try (BufferedReader br = new BufferedReader(new FileReader(
                new File("./src/main/resources/sensitive_data.txt")))) {

            if ((str = br.readLine()) != null) {
                str = str.replaceAll("\\Q${payment_amount}\\E", String.valueOf((int) paymentAmount));
                str = str.replaceAll("\\Q${balance}\\E", String.valueOf((int) balance));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }
}
