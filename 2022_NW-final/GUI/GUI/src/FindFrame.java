import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.Socket;

public class FindFrame extends JFrame{
    /* Panel */
    JPanel panel = new JPanel();

    /* Label */
    JLabel idL = new JLabel("ID");
    JLabel pwL = new JLabel("PW");

    /* TextField */
    JTextField id = new JTextField();
    JTextField pw = new JTextField();

    /* Button */
    JButton findBtn = new JButton("Find PW");

    Start o = null;

    FindFrame(Start _o) {
        o = _o;

        setTitle("Register");

        /* Label 크기 작업 */
        idL.setPreferredSize(new Dimension(50, 30));
        pwL.setPreferredSize(new Dimension(50, 30));

        /* TextField 크기 작업 */
        id.setPreferredSize(new Dimension(140, 30));
        pw.setPreferredSize(new Dimension(140, 30));

        /* Button 크기 작업 */
        findBtn.setPreferredSize(new Dimension(95, 25));

        /* Panel 추가 작업 */
        setContentPane(panel);

        panel.add(idL);
        panel.add(id);

        panel.add(pwL);
        panel.add(pw);

        panel.add(findBtn);

        /* Button 이벤트 리스너 추가 */
        ButtonListener bl = new ButtonListener();

        findBtn.addActionListener(bl);

        setSize(250, 150);
        setLocationRelativeTo(null);
        setResizable(false);


    }

    /* Button 이벤트 리스너 */
    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();

            /* TextField에 입력된 회원 정보들을 변수에 초기화 */
            String uid = id.getText();

            if(b.getText().equals("Find PW")) {
//                if(o.db.idcheck(uid)) {	// 데이터베이스에 접속해 아이디 정보를 확인하는 부분
//                    System.out.println("Find PW Successful");
//                    pw.setText(uid);
                }
            }
        }
    }
