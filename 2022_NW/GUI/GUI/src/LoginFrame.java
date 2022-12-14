import DTO.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class LoginFrame extends JFrame {
	/* Panel */
	JPanel basePanel = new JPanel(new BorderLayout());
	JPanel centerPanel = new JPanel(new BorderLayout());
	JPanel westPanel = new JPanel();
	JPanel eastPanel = new JPanel();
	JPanel southPanel = new JPanel();

	/* Label */
	JLabel idL = new JLabel("ID");
	JLabel pwL = new JLabel("PW");

	/* TextField */
	JTextField id = new JTextField();
	JPasswordField pw = new JPasswordField();

	/* Button */
	JButton loginBtn = new JButton("Log in");
	JButton joinBtn = new JButton("Register");
	JButton findBtn = new JButton("Find PW");

	Start o = null;

	LoginFrame(Start _o) {
		o = _o;

		setTitle("Log in");

		/* Panel 크기 작업 */
		centerPanel.setPreferredSize(new Dimension(260, 80));
		westPanel.setPreferredSize(new Dimension(210, 75));
		eastPanel.setPreferredSize(new Dimension(90, 75));
		southPanel.setPreferredSize(new Dimension(290, 40));

		/* Label 크기 작업 */
		idL.setPreferredSize(new Dimension(50, 30));
		pwL.setPreferredSize(new Dimension(50, 30));

		/* TextField 크기 작업 */
		id.setPreferredSize(new Dimension(140, 30));
		pw.setPreferredSize(new Dimension(140, 30));

		/* Button 크기 작업 */
		loginBtn.setPreferredSize(new Dimension(75, 63));
		joinBtn.setPreferredSize(new Dimension(135, 25));
		findBtn.setPreferredSize(new Dimension(135, 25));

		/* Panel 추가 작업 */
		setContentPane(basePanel); // panel을 기본 컨테이너로 설정

		basePanel.add(centerPanel, BorderLayout.CENTER);
		basePanel.add(southPanel, BorderLayout.SOUTH);
		centerPanel.add(westPanel, BorderLayout.WEST);
		centerPanel.add(eastPanel, BorderLayout.EAST);

		westPanel.setLayout(new FlowLayout());
		eastPanel.setLayout(new FlowLayout());
		southPanel.setLayout(new FlowLayout());

		/* westPanel 컴포넌트 */
		westPanel.add(idL);
		westPanel.add(id);
		westPanel.add(pwL);
		westPanel.add(pw);

		/* eastPanel 컴포넌트 */
		eastPanel.add(loginBtn);

		/* southPanel 컴포넌트 */
		southPanel.add(findBtn);
		southPanel.add(joinBtn);

		/* Button 이벤트 리스너 추가 */
		ButtonListener bl = new ButtonListener();

		loginBtn.addActionListener(bl);
		findBtn.addActionListener(bl);
		joinBtn.addActionListener(bl);

		setSize(310, 150);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/* Button 이벤트 리스너 */
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();

			/* TextField에 입력된 아이디와 비밀번호를 변수에 초기화 */
			String uid = id.getText();
			String upass = "";
			for (int i = 0; i < pw.getPassword().length; i++) {
				upass = upass + pw.getPassword()[i];
			}

			/* 비밀번호 찾기 버튼 이벤트 */
			if (b.getText().equals("Find PW")) {
				o.ff.setVisible(true);
			}

			/* 회원가입 버튼 이벤트 */
			else if (b.getText().equals("Register")) {
				o.jf.setVisible(true);
			}

			/* 로그인 버튼 이벤트 */
			else if (b.getText().equals("Log in")) {
				if (uid.equals("") || upass.equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter both your ID and password", "Login failed",
							JOptionPane.ERROR_MESSAGE);
					System.out.println("Login failed > Login information not entered");
				} else if (uid != null && upass != null) {

					User user = new User();
					user.setID(uid);
					user.setPwd(upass);
					try {
						PrintWriter os = new PrintWriter(o.socket.getOutputStream(), true);
						Scanner in = new Scanner(o.socket.getInputStream());
						os.println(1);
						o.out.writeObject(user);
						o.out.flush();
						String result = in.nextLine();
						System.out.println(1);
						if (result.equals("s")) { // login
							System.out.println("Login Successful");
							JOptionPane.showMessageDialog(null, "Login succeeded");
							id.setText("");
							pw.setText("");
							dispose();
							Person_Board pb = new Person_Board(o);


						} else if (result.equals("i")) { // error cases
							System.out.println("Invalid ID");
							JOptionPane.showMessageDialog(null, "Invalid ID", "Login failed",
									JOptionPane.ERROR_MESSAGE);
							id.setText("");
							pw.setText("");
							dispose();
							new LoginFrame(o);
						} else if (result.equals("w")) {
							System.out.println("Wrong Password");
							JOptionPane.showMessageDialog(null, "Wrong Password", "Login failed",
									JOptionPane.ERROR_MESSAGE);
							id.setText("");
							pw.setText("");
							dispose();
							new LoginFrame(o);
						} else {
							System.out.println("Invalid approach");
							JOptionPane.showMessageDialog(null, "Invalid approach", "Login failed",
									JOptionPane.ERROR_MESSAGE);
							id.setText("");
							pw.setText("");
							dispose();
							new LoginFrame(o);
						}
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
			}
		}
	}
}
