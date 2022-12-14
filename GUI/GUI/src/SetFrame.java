import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import DTO.User;

public class SetFrame extends JFrame{
	User u;
    private JPanel contentPane;
    private JTextField textField;		//nick
    private JTextField textField_1;		//상메
    Start o = null;
    public SetFrame(Start o_, User user) {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	u = user;
    	o = o_;
        setTitle("설정");
        setBounds(100, 100, 450, 281);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));


        Dimension frameSize = new Dimension(450, 281);
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();

        setLocation((windowSize.width - frameSize.width)/2,
                (windowSize.height - frameSize.height)/2);

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(12, 10, 410, 222);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("별명변경");
        lblNewLabel.setBounds(28, 47, 57, 15);
        panel.add(lblNewLabel);

        textField = new JTextField();
        textField.setFont(new Font("굴림", Font.PLAIN, 17));
        textField.setBounds(28, 72, 377, 25);
        panel.add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("상태메세지 변경");
        lblNewLabel_1.setBounds(28, 105, 113, 15);
        panel.add(lblNewLabel_1);

        textField_1 = new JTextField();
        textField_1.setFont(new Font("굴림", Font.PLAIN, 17));
        textField_1.setColumns(10);
        textField_1.setBounds(28, 130, 377, 25);
        panel.add(textField_1);

		ButtonListener bl = new ButtonListener();
		
        JButton btnNewButton = new JButton("완료");
        btnNewButton.setBounds(310, 166, 97, 23);
        panel.add(btnNewButton);

        JButton out = new JButton("탈퇴");
        out.setBounds(12, 189, 80, 23);
        panel.add(out);

        out.addActionListener(bl);
        btnNewButton.addActionListener(bl);
        setVisible(true);
    }
    class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();

			if (b.getText().equals("완료")) {
				String qu = textField_1.getText();
            	String nick = textField.getText();
            	System.out.println(qu);
            	User user = new User();
            	user.setQuote(qu);
            	user.setNickname(nick);
            	
            	try {
    				Scanner in = new Scanner(o.socket.getInputStream());
    				PrintWriter os = new PrintWriter(o.socket.getOutputStream(), true);

    				os.println(3);
    				
					o.out.writeObject(user);
					o.out.flush();
					
					String res = in.nextLine();
					if(res.equals("s")) {
						System.out.println("Update Successful");
						JOptionPane.showMessageDialog(null, "Update succeeded");
						textField_1.setText("");
						textField.setText("");
						dispose();
					} else {
						textField_1.setText("");
						textField.setText("");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (b.getText().equals("탈퇴")) {
            	try {
    				Scanner in = new Scanner(o.socket.getInputStream());
    				PrintWriter os = new PrintWriter(o.socket.getOutputStream(), true);

    				os.println(22);
    				System.out.println(u.getID());
					o.out.writeObject(u);
					o.out.flush();
					
					String res = in.nextLine();
					if(res.equals("s")) {
						System.out.println("Sign out Successful");
						JOptionPane.showMessageDialog(null, "Sign out succeeded");
						System.exit(0);
					} else {
						System.out.println("Error occured");
						JOptionPane.showMessageDialog(null, "Error occured", "Sign out failed",
								JOptionPane.ERROR_MESSAGE);
						dispose();
						new LoginFrame(o);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
    	
    }
//    public static void main(String[] args) {
//        new SetFrame();
//    }

}
