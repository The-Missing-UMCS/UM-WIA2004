package com.umwia2004.solution.util;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import org.fusesource.jansi.Ansi;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.fusesource.jansi.Ansi.ansi;

public class TableUtil {

    public static void printTable(boolean[][] table) {
        for (boolean[] row : table) {
            printRow(row);
        }
    }

    public static void printRow(List<Boolean> list) {
        printRow(toPrimitiveArray(list), 1);
    }

    public static void printRow(boolean[] row) {
        String output = formatRow(row, 1);
        System.out.println(output);
    }

    public static void printRow(boolean[] row, int width) {
        String output = formatRow(row, width);
        System.out.println(output);
    }

    public static void printRowWithIndices(boolean[] row) {
        int maxIndexWidth = String.valueOf(row.length - 1).length();

        // Print the row
        System.out.println(formatRow(row, maxIndexWidth));

        // Print indices
        String indices = formatIndices(row.length, maxIndexWidth);
        System.out.println(indices);
    }

    public static void printRowWithIndices(List<Boolean> list) {
        printRowWithIndices(toPrimitiveArray(list));
    }

    // Convert List<Boolean> to boolean[]
    private static boolean[] toPrimitiveArray(List<Boolean> list) {
        boolean[] array = new boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    // Format a boolean[] as a row of colored blocks
    private static String formatRow(boolean[] row, int width) {
        return IntStream.range(0, row.length)
            .mapToObj(i -> row[i]
                ? ansi().fg(Ansi.Color.GREEN).a("█".repeat(width)).reset().toString()
                : ansi().fg(Ansi.Color.RED).a("█".repeat(width)).reset().toString())
            .collect(Collectors.joining(" "));
    }

    // Format indices for a row
    private static String formatIndices(int length, int width) {
        return java.util.stream.IntStream.range(0, length)
            .mapToObj(i -> String.format("%" + width + "d", i))
            .collect(Collectors.joining(" "));
    }

    public static void renderTable(String[] headers, String[][] rows) {
        AsciiTable table = new AsciiTable();

        // Add headers to the table
        table.addRule();
        table.addRow((Object[]) headers);
        table.addRule();

        // Add rows to the table
        for (String[] row : rows) {
            table.addRow((Object[]) row);
            table.addRule();
        }

        // Set the width calculator to adjust column widths based on the longest line in each column
        table.setPaddingLeftRight(1);
        table.getRenderer().setCWC(new CWC_LongestLine());

        // Render the table
        System.out.println(table.render());
    }

    public static <T> void renderTable(List<ColumnMapping> columnMappings, List<T> dataList) {
        String[] headers = columnMappings.stream()
            .map(ColumnMapping::getHeader)
            .toArray(String[]::new);

        String[] fieldNames = columnMappings.stream()
            .map(ColumnMapping::getFieldName)
            .toArray(String[]::new);

        renderTable(headers, fieldNames, dataList);
    }

    public static <T> void renderTable(String[] headers, String[] fieldNames, List<T> dataList) {
        String[][] rows = new String[dataList.size()][fieldNames.length];

        for (int i = 0; i < dataList.size(); i++) {
            rows[i] = mapFieldsToStringArray(fieldNames, dataList.get(i));
        }

        renderTable(headers, rows);
    }

    private static <T> String[] mapFieldsToStringArray(String[] fieldNames, T object) {
        String[] values = new String[fieldNames.length];

        for (int i = 0; i < fieldNames.length; i++) {
            values[i] = ReflectionUtils.readFieldAsString(object, fieldNames[i]);
        }
        return values;
    }
}
