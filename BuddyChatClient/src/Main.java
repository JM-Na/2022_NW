import model.*;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        Socket socket;
        ObjectInputStream in;
        ObjectOutputStream out;
        try {
            socket = new Socket("localhost", 9000);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(null);
            in = new ObjectInputStream(socket.getInputStream());
            testDto result = (testDto) in.readObject();


            System.out.println(result.getHello());

        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }
}