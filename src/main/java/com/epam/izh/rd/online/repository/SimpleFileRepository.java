package com.epam.izh.rd.online.repository;

import java.io.*;
import java.nio.file.*;
import java.util.stream.Stream;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        long countFiles = 0;
        if (!path.contains("src"))
            path = "src\\main\\resources\\" + path;
        File file = new File(path);
        if (file.isFile())
            countFiles++;
        else {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files)
                    countFiles += countFilesInDirectory(f.getParent() + "\\" + f.getName());
            }
        }
        return countFiles;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        long countDir = 0;
        if (!path.contains("src"))
            path = "src\\main\\resources\\" + path;
        File file = new File(path);
        if (file.isDirectory()) {
            countDir++;
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files)
                    countDir += countDirsInDirectory(f.getParent() + "\\" + f.getName());
            }
        }
        return countDir;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        String parentDir = new File(to).getParent();
        if (!new File(parentDir).exists()) {
            try {
                Files.createDirectory(Paths.get(parentDir));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Files.copy(Paths.get(from), Paths.get(to), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        String pathName = path + "/" + name;
        File file = new File((pathName));
        try {
            Files.createDirectory(Paths.get(path));
            Files.createFile(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.exists();
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        String retStr = null;
        try {
            BufferedReader bf = new BufferedReader(new FileReader(new File("src/main/resources/" + fileName)));
            retStr = bf.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return retStr;
    }
}
