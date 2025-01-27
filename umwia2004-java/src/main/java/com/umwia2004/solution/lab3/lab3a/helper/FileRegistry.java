package com.umwia2004.solution.lab3.lab3a.helper;

import com.umwia2004.solution.util.TableUtil;

import java.util.HashMap;
import java.util.Map;

public class FileRegistry {
    private final Map<String, FileRegistryRecord> fileRegistry;

    public FileRegistry() {
        this(new HashMap<>());
    }

    protected FileRegistry(Map<String, FileRegistryRecord> fileRegistry) {
        this.fileRegistry = fileRegistry;
    }

    public boolean isFileExisted(String fileName) {
        return fileRegistry.containsKey(fileName);
    }

    public void addRecord(String fileName, int startIndex, int blockCount) {
        fileRegistry.put(fileName, new FileRegistryRecord(startIndex, blockCount));
    }

    public FileRegistryRecord removeRecord(String fileName) {
        return fileRegistry.remove(fileName);
    }

    public void printRegistry() {
        String[] headers = {"File Name", "Start Index", "Block Count"};
        String[][] rows = fileRegistry.keySet().stream()
            .map(fileName -> new String[]{
                fileName,
                String.valueOf(fileRegistry.get(fileName).startIndex()),
                String.valueOf(fileRegistry.get(fileName).blockCount())}
            )
            .toArray(java.lang.String[][]::new);
        TableUtil.renderTable(headers, rows);
    }

    public record FileRegistryRecord(int startIndex, int blockCount) {
    }
}
