def calculate_parity_bits(data_bits):
    """Calculate the number of parity bits needed for the given number of data bits."""
    parity_bits = 0
    while (2 ** parity_bits) < (data_bits + parity_bits + 1):
        parity_bits += 1
    return parity_bits

def generate_hamming_code(data):
    """Generate the Hamming Code for the given data."""
    data_bits = len(data)
    parity_bits = calculate_parity_bits(data_bits)
    total_bits = data_bits + parity_bits
    hamming_code = ['0'] * total_bits

    # Place data bits and leave space for parity bits
    j = 0
    for i in range(1, total_bits + 1):
        if (i & (i - 1)) == 0:
            continue
        hamming_code[i - 1] = data[j]
        j += 1

    # Calculate parity bits
    for i in range(parity_bits):
        parity_pos = (2 ** i) - 1
        parity = 0
        for j in range(parity_pos, total_bits, 2 * (parity_pos + 1)):
            for k in range(j, min(j + parity_pos + 1, total_bits)):
                parity ^= int(hamming_code[k])
        hamming_code[parity_pos] = str(parity)

    return ''.join(hamming_code)

def detect_and_correct_error(received):
    """Detect and correct a single-bit error in the received Hamming Code."""
    total_bits = len(received)
    parity_bits = calculate_parity_bits(total_bits)
    error_pos = 0

    # Check parity bits
    for i in range(parity_bits):
        parity_pos = (2 ** i) - 1
        parity = 0
        for j in range(parity_pos, total_bits, 2 * (parity_pos + 1)):
            for k in range(j, min(j + parity_pos + 1, total_bits)):
                parity ^= int(received[k])
        if parity != 0:
            error_pos += parity_pos + 1

    # Correct error if found
    if error_pos != 0:
        print(f"Error detected at position: {error_pos}")
        received_list = list(received)
        received_list[error_pos - 1] = '1' if received_list[error_pos - 1] == '0' else '0'
        received = ''.join(received_list)
    else:
        print("No error detected.")

    return received

if __name__ == "__main__":
    data = input("Enter the data to be transmitted: ")
    hamming_code = generate_hamming_code(data)
    print("Generated Hamming Code: " + hamming_code)

    received = input("Enter the received Hamming Code: ")
    corrected_code = detect_and_correct_error(received)
    print("Corrected Hamming Code: " + corrected_code)
