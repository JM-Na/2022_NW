//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.EventQueue;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JList;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextField;
//import javax.swing.ScrollPaneConstants;
//import javax.swing.SwingConstants;
//import javax.swing.border.EmptyBorder;
//
//public class ChatFrame extends JFrame{
//    private JPanel contentPane;
//    private JTextField textField;
//    String sendMes;
//
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    ChatFrame frame = new ChatFrame();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    /**
//     * Create the frame.
//     */
//    public ChatFrame() {
//        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBounds(100, 100, 649, 506);
//
//        contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//
//        setContentPane(contentPane);
//        contentPane.setLayout(null);
//
//        JPanel panel = new JPanel();
//        panel.setBounds(0, 0, 633, 467);
//        contentPane.add(panel);
//        panel.setLayout(null);
//
//
//
//
//        Dimension frameSize = new Dimension(649, 506);
//        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
//
//        setLocation((windowSize.width - frameSize.width)/2,
//                (windowSize.height - frameSize.height)/2);
//
//        /**
//         * Center
//         */
//
//
//
//        /**
//         * south
//         * 파일 보내기보내기버튼
//         * 채팅 입력란
//         * 보내기 버튼
//         */
//        JPanel south = new JPanel();
//        south.setBounds(0, 422, 633, 45);
//        panel.add(south);
//        south.setLayout(null);
//
//        JPanel panel_2 = new JPanel();
//        panel_2.setBounds(78, 7, 456, 33);
//        south.add(panel_2);
//
//
//        //채팅 입력 textfield
//        textField = new JTextField();
//        textField.setFont(new Font("나눔고딕", Font.PLAIN, 17));
//        textField.setHorizontalAlignment(SwingConstants.LEFT);
//        panel_2.add(textField);
//        textField.setColumns(28);
//
//        //채팅입력란 안에서 Enter 입력 -> send
//        textField.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyTyped(KeyEvent e) {}
//            @Override
//            public void keyReleased(KeyEvent e) {
//                //isEnter 함수
//                //pressEnter함수
//                if (isEnter(e)) {
//                    pressEnter(textField.getText().replaceAll("\n", ""));
////                }
//            }
//            //textfield에 작성후 -> 서버로 넘기기
//            @Override
//            public void keyPressed(KeyEvent e){
//                if(e.getKeyCode()==KeyEvent.VK_ENTER){
//                    sendMes = textField.getText();
//                    try{
//                        dos.writeUTF(sendMes);
//                    }catch(IOException ee){
//                        ee.printStackTrace();
//                    }
//                }
//            }
//
//        });
//
//        //파일 전송 버튼
//        JButton send_f_btn = new JButton("+");
//        send_f_btn.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
//        send_f_btn.setBounds(12, 7, 54, 30);
//        south.add(send_f_btn);
//
//        send_f_btn.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent arg0) {
//                //sendFile();
//            }
//        });
//
//        //보내기 버튼
//        JButton send_mes_btn = new JButton("Send");
//        send_mes_btn.setBounds(546, 10, 75, 25);
//        south.add(send_mes_btn);
//        send_mes_btn.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 13));
//
//        //보내기 실행
//        send_f_btn.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e) {
//                e.getSource();
//                System.out.println(e.getActionCommand());
//                sendMes = textField.getText();
//                try {
//                    dos.writeUTF(sendMes);
//                    textField.setText("");
//
//                } catch (IOException ee) {
//                    ee.printStackTrace();
//
//                }
//            }
//
//        });
//
//
//        /**
//         * West
//         */
//        JPanel west = new JPanel();
//        west.setBounds(525, 0, 108, 422);
//        panel.add(west);
//        west.setLayout(new BorderLayout(0, 0));
//
//        JPanel panel_5 = new JPanel();
//        west.add(panel_5);
//        panel_5.setLayout(new BorderLayout(0, 0));
//
//        JPanel panel_6 = new JPanel();
//        panel_5.add(panel_6, BorderLayout.EAST);
//        panel_6.setLayout(new BorderLayout(0, 0));
//
//        JPanel panel_9 = new JPanel();
//        panel_6.add(panel_9);
//        panel_9.setLayout(new BorderLayout(0, 0));
//
//        JPanel panel_10 = new JPanel();
//        panel_9.add(panel_10, BorderLayout.NORTH);
//
//        /**!=====
//         * JLabel 위치 수정
//         */
//        JLabel Member_L = new JLabel("Members");
//        Member_L.setFont(new Font("나눔고딕", Font.BOLD, 12));
//        panel_10.add(Member_L);
//
//        //채팅 초대 버튼
//        JButton add_user_btn = new JButton("+");
//        add_user_btn.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
//        panel_10.add(add_user_btn);
//
//
//        //채팅 초대 버튼 실행
//        add_user_btn.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e){
//                //
//            }
//        });
//
//
//        JPanel panel_7 = new JPanel();
//        panel_9.add(panel_7, BorderLayout.SOUTH);
//        panel_7.setLayout(new BorderLayout(0, 0));
//
//        JPanel panel_11 = new JPanel();
//        FlowLayout flowLayout_1 = (FlowLayout) panel_11.getLayout();
//        flowLayout_1.setAlignment(FlowLayout.RIGHT);
//        panel_7.add(panel_11);
//
//        //채팅 나가기 버튼
//        JButton exit_btn = new JButton("Exit");
//        exit_btn.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
//        panel_11.add(exit_btn);
//
//        //나가기 버튼 실행
//
//
//
//        //참여자 목록
//        JPanel list_out = new JPanel();
//        list_out.setForeground(Color.WHITE);
//        panel_9.add(list_out, BorderLayout.CENTER);
//
//
//        //참여자 목록 받아오기
//        JList list = new JList();
//        list_out.add(list);
//
//
//
////        userListModel.clear();
////        for(String userName : -----.getUsernameList()){
////            userListModel.addElement(userName);
////        }
//
//        //예시
//        userListModel.addElement("923409242");
//        userListModel.addElement("1234789279823");
//
//
//
//        list.setModel(userListModel);
//
//        JPanel center = new JPanel();
//        center.setBounds(0, 0, 525, 422);
//        panel.add(center);
//        center.setLayout(new BorderLayout(0, 0));
//
//        JScrollPane scrollPane = new JScrollPane();
//        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//        center.add(scrollPane, BorderLayout.EAST);
//
////        long time = System.currentTimeMillis();
////        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm");
////        String str = dayTime.format(new Date(time));
////        //getDlm().addElement("-------------------------"+str+"-------------------------");
//
//        JList list_1 = new JList();
//        center.add(list_1, BorderLayout.NORTH);
//
//
//
//
//    }
//
//
//
//}
