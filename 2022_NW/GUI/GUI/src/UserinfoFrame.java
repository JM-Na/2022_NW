import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.*;

public class UserinfoFrame extends JFrame {
    private JPanel contentPane;

    public UserinfoFrame() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 399, 216);

        Dimension frameSize = new Dimension(339, 216);
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();

        setLocation((windowSize.width - frameSize.width)/2,
                (windowSize.height - frameSize.height)/2);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 384, 182);
        contentPane.add(panel);
        panel.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setLayout(null);
        panel_1.setBounds(64, 34, 254, 34);
        panel.add(panel_1);

        JLabel lblNewLabel = new JLabel("ID : ");
        lblNewLabel.setBounds(12, 10, 57, 15);
        panel_1.add(lblNewLabel);

        JLabel id_out = new JLabel("New label");
        id_out.setBounds(46, 10, 196, 15);
        panel_1.add(id_out);

        JPanel panel_1_1 = new JPanel();
        panel_1_1.setLayout(null);
        panel_1_1.setBounds(64, 78, 254, 34);
        panel.add(panel_1_1);

        JLabel lblNewLabel_1 = new JLabel("이름 : ");
        lblNewLabel_1.setBounds(12, 10, 57, 15);
        panel_1_1.add(lblNewLabel_1);

        JLabel name_out = new JLabel("New label");
        name_out.setBounds(46, 10, 196, 15);
        panel_1_1.add(name_out);

        JPanel panel_2 = new JPanel();
        panel_2.setLayout(null);
        panel_2.setBounds(133, 134, 217, 26);
        panel.add(panel_2);

        JLabel time_out = new JLabel("최종 접속시간 :");
        time_out.setBounds(12, 6, 193, 15);
        panel_2.add(time_out);

        setVisible(false);
    }

//    public static void main(String[] args) {
//        new UserinfoFrame();
//    }
}
