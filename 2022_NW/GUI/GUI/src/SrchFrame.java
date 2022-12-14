import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.*;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class SrchFrame extends JFrame {

    private JPanel contentPane;
    private JTextField textField;

    public SrchFrame() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Search");
        setBounds(100, 100, 435, 268);

        Dimension frameSize = new Dimension(435, 268);
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();

        setLocation((windowSize.width - frameSize.width)/2,
                (windowSize.height - frameSize.height)/2);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(12, 10, 395, 209);
        contentPane.add(panel);
        panel.setLayout(null);

        textField = new JTextField();
        textField.setBounds(28, 43, 254, 35);
        panel.add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("검색");
        btnNewButton.setBounds(294, 49, 68, 23);
        panel.add(btnNewButton);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.WHITE);
        panel_1.setBounds(28, 105, 333, 82);
        panel.add(panel_1);
        panel_1.setLayout(null);

        JList list = new JList();
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setBounds(166, 5, 0, 0);
        panel_1.add(list);

        setVisible(false);
    }

//    public static void main(String[] args) {
//        new SrchFrame();
//    }

}
