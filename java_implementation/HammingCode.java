import java.util.Scanner;

public class HammingCode {
    // Method to calculate the number of parity bits required
    private static int calculateParityBits(int dataBits) {
        int parityBits = 0;
        while (Math.pow(2, parityBits) < (dataBits + parityBits + 1)) {
            parityBits++;
        }
        return parityBits;
    }

    // Method to generate the Hamming code
    public static String generateHammingCode(String data) {
        int dataBits = data.length();
        int parityBits = calculateParityBits(dataBits);
        int totalBits = dataBits + parityBits;
        char[] hammingCode = new char[totalBits];

        // Initialize the hamming code array with placeholders
        for (int i = 0; i < totalBits; i++) {
            hammingCode[i] = '0';
        }

        // Place data bits and parity bits at appropriate positions
        for (int i = 0, j = 0; i < totalBits; i++) {
            if ((i + 1 & i) == 0) {
                // Position is a power of 2, leave it for parity bit
                hammingCode[i] = '0';
            } else {
                // Position is for data bit
                hammingCode[i] = data.charAt(j);
                j++;
            }
        }

        // Calculate parity bits
        for (int i = 0; i < parityBits; i++) {
            int parityPos = (int) Math.pow(2, i) - 1;
            int parity = 0;
            for (int j = parityPos; j < totalBits; j += 2 * (parityPos + 1)) {
                for (int k = j; k < j + parityPos + 1 && k < totalBits; k++) {
                    if (k != parityPos) {
                        parity ^= Character.getNumericValue(hammingCode[k]);
                    }
                }
            }
            hammingCode[parityPos] = (char) (parity + '0');
        }

        return new String(hammingCode);
    }

    // Method to detect and correct a single-bit error in the received Hamming code
    public static String detectAndCorrectError(String received) {
        int totalBits = received.length();
        int parityBits = calculateParityBits(totalBits);
        int errorPos = 0;

        // Check parity bits
        for (int i = 0; i < parityBits; i++) {
            int parityPos = (int) Math.pow(2, i) - 1;
            int parity = 0;
            for (int j = parityPos; j < totalBits; j += 2 * (parityPos + 1)) {
                for (int k = j; k < j + parityPos + 1 && k < totalBits; k++) {
                    parity ^= Character.getNumericValue(received.charAt(k));
                }
            }
            if (parity != 0) {
                errorPos += parityPos + 1;
            }
        }

        // Correct error if found
        if (errorPos != 0) {
            System.out.println("Error detected at position: " + errorPos);
            char[] receivedArray = received.toCharArray();
            receivedArray[errorPos - 1] = receivedArray[errorPos - 1] == '0' ? '1' : '0';
            received = new String(receivedArray);
        } else {
            System.out.println("No error detected.");
        }

        return received;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the data to be transmitted:");
        String data = scanner.nextLine();

        String hammingCode = generateHammingCode(data);
        System.out.println("Generated Hamming Code: " + hammingCode);

        System.out.println("Enter the received Hamming Code:");
        String received = scanner.nextLine();

        String correctedCode = detectAndCorrectError(received);
        System.out.println("Corrected Hamming Code: " + correctedCode);

        scanner.close();
    }
}
