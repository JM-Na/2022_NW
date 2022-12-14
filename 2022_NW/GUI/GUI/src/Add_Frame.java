import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;

public class Add_Frame extends JFrame {
    private JPanel contentPane;
    private JTextField textField;

    public Add_Frame() {

        setTitle("추가");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 420, 264);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        Dimension frameSize = new Dimension(420, 264);
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();

        setLocation((windowSize.width - frameSize.width)/2,
                (windowSize.height - frameSize.height)/2);

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(12, 10, 380, 205);
        contentPane.add(panel);
        panel.setLayout(null);

        textField = new JTextField();
        textField.setBounds(30, 42, 251, 32);
        panel.add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("New button");
        btnNewButton.setBounds(305, 35, 47, 39);
        panel.add(btnNewButton);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.WHITE);
        panel_1.setBounds(40, 93, 277, 91);
        panel.add(panel_1);
        panel_1.setLayout(null);

        JList list = new JList();
        list.setBounds(138, 5, 0, 0);
        panel_1.add(list);

        setVisible(false);

    }

//    public static void main(String[] args) {
//        new SetFrame();
//    }

}
