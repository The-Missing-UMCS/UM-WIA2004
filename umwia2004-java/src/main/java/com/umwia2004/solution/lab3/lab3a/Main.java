package com.umwia2004.solution.lab3.lab3a;

import com.umwia2004.solution.lab3.lab3a.domain.DataSize;
import com.umwia2004.solution.lab3.lab3a.domain.File;
import com.umwia2004.solution.lab3.lab3a.helper.Disk;
import com.umwia2004.solution.lab3.lab3a.helper.FileManager;

import java.util.Arrays;

public class Main {
    public static void main(java.lang.String[] args) {
        Disk disk = new Disk();
        FileManager fileManager = new FileManager(disk);

        String[] operations = new String[]{
            "ADD file1 1024",
            "ADD file2 1024",
            "ADD file3 4098",
            "ADD file4 5120",
            "DEL file1",
            "DEL file2",
            "ADD file5 1024",
        };

        Arrays.stream(operations)
            .map(operation -> operation.split(" "))
            .forEach(info -> {
                try {
                    if ("ADD".equals(info[0])) {
                        fileManager.allocate(new File(new DataSize(Integer.parseInt(info[2]), DataSize.Unit.BYTE), info[1]));
                    } else {
                        fileManager.deallocate(info[1]);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

        disk.printDiskStatus();
    }
}
