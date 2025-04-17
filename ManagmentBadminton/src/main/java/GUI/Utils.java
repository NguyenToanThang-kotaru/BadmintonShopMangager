package GUI;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Utils {
    public static String formatCurrency(int amount) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###");
        return formatter.format(amount);
    }
    
    public static String formatCurrency(String amountStr) {
        try {
            String cleanString = amountStr.replaceAll("[^0-9]", "");
            if (!cleanString.isEmpty()) {
                int amount = Integer.parseInt(cleanString);
                return formatCurrency(amount);
            }
            return "0";
        } catch (NumberFormatException e) {
            return amountStr; 
        }
    }
    
    public static int parseCurrency(String formattedAmount) {
        try {
            return Integer.parseInt(formattedAmount.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String formatCurrency(double amount) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###.##"); // hỗ trợ phần thập phân
        return formatter.format(amount);
    }

    public static double parseCurrencyToDouble(String formattedAmount) {
        try {
            String cleanString = formattedAmount.replaceAll("[^0-9.]", ""); // giữ lại dấu chấm
            return Double.parseDouble(cleanString);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}