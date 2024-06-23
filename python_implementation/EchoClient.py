import socket

def start_echo_client(host='localhost', port=12345):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as client_socket:
        client_socket.connect((host, port))
        print(f"Connected to echo server at {host}:{port}")

        while True:
            message = input("Enter message to send: ")
            if message.lower() == 'exit':
                print("Closing connection.")
                break
            client_socket.sendall(message.encode())
            data = client_socket.recv(1024)
            print(f"Received echo: {data.decode()}")

if __name__ == "__main__":
    start_echo_client()
