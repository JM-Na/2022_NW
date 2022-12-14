import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Start {

    LoginFrame lf = null;
    JoinFrame jf = null;
    FindFrame ff = null;
    Person_Board mf = null;

    Socket socket;
    ObjectInputStream oin;
    ObjectOutputStream out;

    public static void main(String[] args) throws Exception {


        Start st = new Start();
        st.socket = new Socket(InetAddress.getLocalHost(), 9000);
        st.out = new ObjectOutputStream(st.socket.getOutputStream());
        st.oin = new ObjectInputStream(st.socket.getInputStream());

        st.lf = new LoginFrame(st);
        st.jf = new JoinFrame(st);
        st.ff = new FindFrame(st);
        st.mf = new Person_Board(st);
//        st.af = new Add_Frame(st);
//        st.sch = new SrchFrame(st);
//        st.set = new SetFrame(st);
//        st.chf = new ChatFrame();
//        st.uif = new UserinfoFrame(st);
    }
}
