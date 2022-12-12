
// Client.java
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws Exception {
		String server_ip = "localhost";
		int port_num = 1234;

		var socket = new Socket(server_ip, port_num);

		var scanner = new Scanner(System.in);
		var in = new Scanner(socket.getInputStream());
		var out = new PrintWriter(socket.getOutputStream(), true);

		System.out.println("Connection with server succesful!");
		while (true) {
			String output = scanner.nextLine();

			if (output.equalsIgnoreCase("bye")) {
				out.println(output);
				out.flush();
				break;
			}
			out.println(output);

			
		}
		System.out.println("Client closed");
	}
}
