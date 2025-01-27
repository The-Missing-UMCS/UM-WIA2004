package com.umwia2004.solution.lab3.lab3b;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Disk disk = new Disk();
        FileManager fileManager = new FileManager(disk);

        List<File> files = List.of(
            new File(1024, "file1"),
            new File(1024, "file2"),
            new File(4096, "file3"),
            new File(5120, "file4"),
            new File(1024, "file5")
        );

        files.forEach(file -> {
            try {
                fileManager.allocate(file);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        disk.printDiskStatus();
    }
}
