def xor(a, b):
    """Performs XOR operation between two binary strings a and b."""
    result = []
    for i in range(1, len(b)):
        if a[i] == b[i]:
            result.append('0')
        else:
            result.append('1')
    return ''.join(result)

def mod2div(dividend, divisor):
    """Performs modulo-2 division on the dividend using the divisor and returns the remainder."""
    pick = len(divisor)
    tmp = dividend[:pick]

    while pick < len(dividend):
        if tmp[0] == '1':
            tmp = xor(divisor, tmp) + dividend[pick]
        else:
            tmp = xor('0' * pick, tmp) + dividend[pick]
        pick += 1

    if tmp[0] == '1':
        tmp = xor(divisor, tmp)
    else:
        tmp = xor('0' * pick, tmp)

    return tmp

def encodeData(data, key):
    """Encodes data using the CRC key and returns the encoded data."""
    l_key = len(key)
    appended_data = data + '0' * (l_key - 1)
    remainder = mod2div(appended_data, key)
    codeword = data + remainder
    return codeword

if __name__ == "__main__":
    data = input("Enter the data to be transmitted: ")
    key = input("Enter the divisor: ")

    encoded_data = encodeData(data, key)
    print("Encoded data to be transmitted: " + encoded_data)
