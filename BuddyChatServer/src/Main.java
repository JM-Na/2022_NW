import model.testDto;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(9000);
        ;
        System.out.println("Server is running...");
        ExecutorService pool = Executors.newFixedThreadPool(100);

        while (true) {
            Socket sock = listener.accept();
            pool.execute(new RunBuddy(sock));

        }
    }

    private static class RunBuddy implements Runnable {
        private Socket socket;

        public RunBuddy(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                var in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                while(true) {
                    System.out.println("Access Client IP : " + socket.getInetAddress() + " Port : "+ socket.getPort());
                    Object request = in.readObject();
                    Object output = new testDto();
                    /*
                    int mode = request.getRequest(); request 내용
                    switch(mode)
                    case 1:
                    case 2: ...
                    */
                    out.writeObject(output);
                }

            } catch(Exception e) {
                System.out.println("Error : "+socket);
            } finally {
                try {
                    socket.close();
                } catch(Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}