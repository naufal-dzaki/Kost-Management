package utils;

public class TableFormatter {
    public static String formatTable(String[] headers, String[][] data) {
        StringBuilder sb = new StringBuilder();
        int[] columnWidths = new int[headers.length];
        
        // Calculate column widths
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = headers[i].length();
            for (String[] row : data) {
                if (row[i] != null && row[i].length() > columnWidths[i]) {
                    columnWidths[i] = row[i].length();
                }
            }
        }
        
        // Build header
        sb.append("\n");
        for (int i = 0; i < headers.length; i++) {
            sb.append(String.format(" %-" + (columnWidths[i] + 2) + "s", headers[i]));
        }
        sb.append("\n");
        
        // Build separator
        for (int width : columnWidths) {
            sb.append("-".repeat(width + 3));
        }
        sb.append("\n");
        
        // Build rows
        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                sb.append(String.format(" %-" + (columnWidths[i] + 2) + "s", row[i]));
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
}