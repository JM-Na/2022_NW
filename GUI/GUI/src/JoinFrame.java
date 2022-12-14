import DTO.User;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class JoinFrame extends JFrame {

	private JPanel contentPane;

	/* TextField */
	JTextField ID_txt = new JTextField();
	JTextField nick_txt = new JTextField();
	JPasswordField pw_txt = new JPasswordField();
	JPasswordField pwcheck_txt = new JPasswordField();
	JTextField name_txt = new JTextField();
	JTextField birth_txt = new JTextField();
	JTextField phone_txt = new JTextField();
	JTextField git_txt = new JTextField();
	JTextField mail_txt = new JTextField();

	/**
	 * Launch the application.
	 */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    JoinFrame frame = new JoinFrame();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
	Start o = null;

	/**
	 * Create the frame.
	 */
	public JoinFrame(Start _o) {
		o = _o;

		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("회원가입");
		setBounds(100, 100, 357, 794);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(12, 10, 317, 741);
		contentPane.add(panel);
		panel.setLayout(null);

		JPanel ID = new JPanel();
		ID.setBounds(12, 10, 293, 71);
		panel.add(ID);
		ID.setLayout(null);

		JLabel ID_L = new JLabel("ID :");
		ID_L.setBounds(12, 10, 57, 15);
		ID.add(ID_L);

		ID_txt.setFont(new Font("굴림", Font.PLAIN, 17));
		ID_txt.setBounds(12, 26, 190, 25);
		ID.add(ID_txt);
		ID_txt.setColumns(20);

		JButton btnNewButton = new JButton("중복확인");
		btnNewButton.setBounds(212, 28, 49, 23);
		ID.add(btnNewButton);

		JPanel nick = new JPanel();
		nick.setLayout(null);
		nick.setBounds(12, 91, 293, 60);
		panel.add(nick);

		JLabel nick_L = new JLabel("별명 : ");
		nick_L.setBounds(12, 10, 57, 15);
		nick.add(nick_L);

		nick_txt.setFont(new Font("굴림", Font.PLAIN, 17));
		nick_txt.setColumns(20);
		nick_txt.setBounds(12, 25, 262, 26);
		nick.add(nick_txt);

		JPanel pw = new JPanel();
		pw.setLayout(null);
		pw.setBounds(12, 161, 293, 60);
		panel.add(pw);

		JLabel pw_L = new JLabel("PW : ");
		pw_L.setBounds(12, 10, 57, 15);
		pw.add(pw_L);

		pw_txt.setColumns(20);
		pw_txt.setBounds(12, 29, 262, 26);
		pw.add(pw_txt);

		JPanel pwcheck = new JPanel();
		pwcheck.setLayout(null);
		pwcheck.setBounds(12, 231, 293, 71);
		panel.add(pwcheck);

		JLabel pwcheck_L = new JLabel("PW 확인 : ");
		pwcheck_L.setBounds(12, 10, 57, 15);
		pwcheck.add(pwcheck_L);

		pwcheck_txt.setColumns(20);
		pwcheck_txt.setBounds(12, 29, 262, 26);
		pwcheck.add(pwcheck_txt);

		JPanel name = new JPanel();
		name.setLayout(null);
		name.setBounds(12, 312, 293, 60);
		panel.add(name);

		JLabel name_L = new JLabel("이름 :");
		name_L.setBounds(12, 10, 57, 15);
		name.add(name_L);

		name_txt.setFont(new Font("굴림", Font.PLAIN, 17));
		name_txt.setColumns(20);
		name_txt.setBounds(12, 29, 262, 26);
		name.add(name_txt);

		JPanel birth = new JPanel();
		birth.setLayout(null);
		birth.setBounds(12, 382, 293, 60);
		panel.add(birth);

		JLabel birth_L = new JLabel("생년월일 :");
		birth_L.setBounds(12, 10, 57, 15);
		birth.add(birth_L);

		birth_txt.setFont(new Font("굴림", Font.PLAIN, 17));
		birth_txt.setColumns(20);
		birth_txt.setBounds(12, 29, 262, 26);
		birth.add(birth_txt);

		JPanel phone = new JPanel();
		phone.setLayout(null);
		phone.setBounds(12, 452, 293, 60);
		panel.add(phone);

		JLabel phone_L = new JLabel("전화번호 :");
		phone_L.setBounds(12, 10, 57, 15);
		phone.add(phone_L);

		phone_txt.setFont(new Font("굴림", Font.PLAIN, 17));
		phone_txt.setColumns(20);
		phone_txt.setBounds(12, 29, 262, 26);
		phone.add(phone_txt);

		JPanel git = new JPanel();
		git.setLayout(null);
		git.setBounds(12, 522, 293, 60);
		panel.add(git);

		JLabel git_L = new JLabel("깃허브 :");
		git_L.setBounds(12, 10, 57, 15);
		git.add(git_L);

		git_txt.setFont(new Font("굴림", Font.PLAIN, 17));
		git_txt.setColumns(20);
		git_txt.setBounds(12, 29, 262, 26);
		git.add(git_txt);

		JPanel mail = new JPanel();
		mail.setLayout(null);
		mail.setBounds(12, 592, 293, 60);
		panel.add(mail);

		JLabel mail__L = new JLabel("e-mail");
		mail__L.setBounds(12, 10, 57, 15);
		mail.add(mail__L);

		mail_txt.setFont(new Font("굴림", Font.PLAIN, 17));
		mail_txt.setColumns(20);
		mail_txt.setBounds(12, 29, 262, 26);
		mail.add(mail_txt);

		JButton btnNewButton_2 = new JButton("Register");
		btnNewButton_2.setBounds(101, 696, 97, 23);
		panel.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ButtonListener());

		JLabel out = new JLabel("                                           ");
		out.setBounds(101, 673, 100, 22);
		panel.add(out);
	}

	/* Button 이벤트 리스너 */
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			/* TextField에 입력된 회원 정보들을 변수에 초기화 */
			try {
				String uid = ID_txt.getText();
				String unick = nick_txt.getText();
				String upass = "";
				for (int i = 0; i < pw_txt.getPassword().length; i++) {
					upass = upass + pw_txt.getPassword()[i];
				}
				String uCpw = "";
				for (int i = 0; i < pwcheck_txt.getPassword().length; i++) {
					uCpw = uCpw + pwcheck_txt.getPassword()[i];
				}
				String uname = name_txt.getText();
				String ubirth = birth_txt.getText();
				String uphone = phone_txt.getText();
				String ugit = git_txt.getText();
				String uemail = mail_txt.getText();
				Scanner in = new Scanner(o.socket.getInputStream());
				PrintWriter os = new PrintWriter(o.socket.getOutputStream(), true);
				os.println(2);
				/* 가입하기 버튼 이벤트 */
				if (b.getText().equals("Register")) {

					System.out.println(uCpw);
					System.out.println(upass);
					if(!uCpw.equals(upass)) {
						ID_txt.setText("");
						nick_txt.setText("");
						pw_txt.setText("");
						pwcheck_txt.setText("");
						name_txt.setText("");
						birth_txt.setText("");
						phone_txt.setText("");
						git_txt.setText("");
						mail_txt.setText("");
	                    JOptionPane.showMessageDialog(null, "Check your Password", "registration failed", JOptionPane.ERROR_MESSAGE);
	                    System.out.println("registration failed > Check your Password");
	                    dispose();
	                    new JoinFrame(o);
					}

					User user = new User();
					user.setID(uid);
					user.setNickname(unick);
					user.setPwd(upass);
					user.setChk_pwd(uCpw);
					user.setName(uname);
					user.setPhone(uphone);
					user.setBirthdate(ubirth);
					user.setWeb(ugit);
					user.setEmail(uemail);
					o.out.writeObject(user);
					o.out.flush();
					String res = in.nextLine();
					if (res.equals("s")) {
						System.out.println("Signin Successful");
						JOptionPane.showMessageDialog(null, "Signin succeeded");

						dispose();
						LoginFrame lg = new LoginFrame(o);
					} else if (res.equals("d")) {
						ID_txt.setText("");
						nick_txt.setText("");
						pw_txt.setText("");
						pwcheck_txt.setText("");
						name_txt.setText("");
						birth_txt.setText("");
						phone_txt.setText("");
						git_txt.setText("");
						mail_txt.setText("");
	                    JOptionPane.showMessageDialog(null, "Duplicate ID", "registration failed", JOptionPane.ERROR_MESSAGE);
	                    System.out.println("registration failed > Duplicate ID");
	                    dispose();
	                    new JoinFrame(o);
					}

				}
			} catch (Exception e1) {

			}
		}
	}

}
