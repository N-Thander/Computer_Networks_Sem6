import java.util.Scanner;

public class CRC {
    // Method to perform XOR operation
    private static String xor(String a, String b) {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < a.length(); i++) {
            if (a.charAt(i) == b.charAt(i)) {
                result.append("0");
            } else {
                result.append("1");
            }
        }
        return result.toString();
    }

    // Method to perform modulo-2 division
    private static String mod2Div(String dividend, String divisor) {
        int pick = divisor.length();
        String tmp = dividend.substring(0, pick);

        while (pick < dividend.length()) {
            if (tmp.charAt(0) == '1') {
                tmp = xor(divisor, tmp) + dividend.charAt(pick);
            } else {
                tmp = xor("0".repeat(pick), tmp) + dividend.charAt(pick);
            }
            pick += 1;
        }

        if (tmp.charAt(0) == '1') {
            tmp = xor(divisor, tmp);
        } else {
            tmp = xor("0".repeat(pick), tmp);
        }

        return tmp;
    }

    // Method to encode data using CRC
    public static String encodeData(String data, String key) {
        int keyLen = key.length();
        String appendedData = data + "0".repeat(keyLen - 1);
        String remainder = mod2Div(appendedData, key);
        return data + remainder;
    }

    // Main method to test CRC encoding
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the data to be transmitted:");
        String data = scanner.nextLine();

        System.out.println("Enter the divisor:");
        String key = scanner.nextLine();

        String encodedData = encodeData(data, key);

        System.out.println("Encoded data to be transmitted: " + encodedData);

        scanner.close();
    }
}
