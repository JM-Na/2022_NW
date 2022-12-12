import java.awt.EventQueue;

import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ChatBoard {

    DefaultListModel<String> userListModel = new DefaultListModel<String>();

    private JFrame frame;
    private JTextField textField;

    private DefaultListModel dlm;

    private static String sendMes;      //메세지 데이터
    private static DataOutputStream dos;
    private static DataOutputStream dis;

//    StringBuffer messageList = new StringBuffer();
//    StringBuffer chatLog = new StringBuffer();

//    HTMLMaker htmlMaker = new HTMLMaker();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ChatBoard window = new ChatBoard();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */

    /**
     * ||---------------------------------------------------------------------------
     * ||       시간표시
     * ||                                                   초대 버튼               ||
     * ||
     * ||                                               참여자 List
     * ||                   채팅내용
     * ||
     * ||
     * ||                                                   나가기 버튼
     * ||
     * || 파일 보내기버튼      채팅 입력란                       보내기버튼              ||
     * ??---------------------------------------------------------------------------
     */
    public ChatBoard() {
        initialize();
    }



    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        /**
         * 전체 창
         */
        frame = new JFrame();
        frame.setBounds(100, 100, 649, 506);
        frame.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 422, 633, 45);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        Dimension frameSize = new Dimension(649, 506);
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();

       frame.setLocation((windowSize.width - frameSize.width)/2,
                (windowSize.height - frameSize.height)/2);

        /**
         * panel2
         * 파일 보내기보내기버튼
         * 채팅 입력란
         * 보내기 버튼
         */
        JPanel panel_2 = new JPanel();
        panel_2.setBounds(78, 7, 456, 33);
        panel.add(panel_2);


        //채팅 입력 textfield
        textField = new JTextField();
        textField.setFont(new Font("나눔고딕", Font.PLAIN, 17));
        textField.setHorizontalAlignment(SwingConstants.LEFT);
        panel_2.add(textField);
        textField.setColumns(28);

        //채팅입력란 안에서 Enter 입력 -> send
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
                //isEnter 함수
                //pressEnter함수
                if (isEnter(e)) {
                    pressEnter(textField.getText().replaceAll("\n", ""));
                }
            }
            //textfield에 작성후 -> 서버로 넘기기
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    sendMes = textField.getText();
                    try{
                        dos.writeUTF(sendMes);
                    }catch(IOException ee){
                        ee.printStackTrace();
                    }
                }
            }

        });

        //파일 전송 버튼
        JButton btnNewButton = new JButton("+");
        btnNewButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
        btnNewButton.setBounds(12, 7, 54, 30);
        panel.add(btnNewButton);

        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                sendFile();
            }
        });

        //보내기 버튼
        JButton btnNewButton_1 = new JButton("Send");
        btnNewButton_1.setBounds(546, 10, 75, 25);
        panel.add(btnNewButton_1);
        btnNewButton_1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 13));

        //보내기 실행
        btnNewButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                e.getSource();
                System.out.println(e.getActionCommand());
                    sendMes = textField.getText();
                    try {
                    dos.writeUTF(sendMes);
                    textField.setText("");

                } catch (IOException ee) {
                        ee.printStackTrace();

            }
        }

        });


        /**
         * members label
         * 초대하기
         * 참여자 목록
         * 채팅 나가기
         */
        JPanel panel_4 = new JPanel();
        panel_4.setBounds(525, 0, 108, 422);
        frame.getContentPane().add(panel_4);
        panel_4.setLayout(new BorderLayout(0, 0));

        JPanel panel_5 = new JPanel();
        panel_4.add(panel_5);
        panel_5.setLayout(new BorderLayout(0, 0));

        JPanel panel_6 = new JPanel();
        panel_5.add(panel_6, BorderLayout.EAST);
        panel_6.setLayout(new BorderLayout(0, 0));

        JPanel panel_9 = new JPanel();
        panel_6.add(panel_9);
        panel_9.setLayout(new BorderLayout(0, 0));

        JPanel panel_10 = new JPanel();
        panel_9.add(panel_10, BorderLayout.NORTH);

        /**!=====
         * JLabel 위치 수정
         */
        JLabel lblNewLabel = new JLabel("Members");
        lblNewLabel.setFont(new Font("나눔고딕", Font.BOLD, 12));
        panel_10.add(lblNewLabel);

        //채팅 초대 버튼
        JButton btnNewButton_2 = new JButton("+");
        btnNewButton_2.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
        panel_10.add(btnNewButton_2);


        //채팅 초대 버튼 실행
        btnNewButton_2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new Search("");
            }
        });


        JPanel panel_7 = new JPanel();
        panel_9.add(panel_7, BorderLayout.SOUTH);
        panel_7.setLayout(new BorderLayout(0, 0));

        JPanel panel_11 = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) panel_11.getLayout();
        flowLayout_1.setAlignment(FlowLayout.RIGHT);
        panel_7.add(panel_11);

        //채팅 나가기 버튼
        JButton btnNewButton_3 = new JButton("Exit");
        btnNewButton_3.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
        panel_11.add(btnNewButton_3);

        //나가기 버튼 실행



        //참여자 목록
        JPanel panel_12 = new JPanel();
        panel_12.setForeground(Color.WHITE);
        panel_9.add(panel_12, BorderLayout.CENTER);

        JPanel panel_13 = new JPanel();
        panel_12.add(panel_13);
        panel_13.setLayout(null);
        panel_13.setPreferredSize(new Dimension(90,0));


        //참여자 목록 받아오기
        JList list = new JList();
        panel_12.add(list);



//        userListModel.clear();
//        for(String userName : -----.getUsernameList()){
//            userListModel.addElement(userName);
//        }

        //예시
        userListModel.addElement("923409242");
        userListModel.addElement("1234789279823");



       list.setModel(userListModel);

        JPanel panel_8 = new JPanel();
        panel_8.setBounds(0, 0, 525, 422);
        frame.getContentPane().add(panel_8);
        panel_8.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel_8.add(scrollPane, BorderLayout.EAST);

        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String str = dayTime.format(new Date(time));
        //getDlm().addElement("-------------------------"+str+"-------------------------");

        JList list_1 = new JList();
        panel_8.add(list_1, BorderLayout.NORTH);
    }

    public class Search extends JFrame {

        Search(String u_id) {

            setBounds(1300, 300, 250, 200);
            setLayout(null);
            setTitle("Search");

            JPanel pn = new JPanel();
            pn.setSize(250, 200);
            pn.setBackground(Color.white);

            TextField searchID = new TextField();
            searchID.setSize(205, 30);

            //검색
            ImageIcon s1 = new ImageIcon("image/search.jpg");
            Image s2 = s1.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon search = new ImageIcon(s2);
            JButton bt = new JButton(s1);
            bt.setBounds(204, 0, 30, 30);
            bt.setFocusPainted(false);
            add(bt);

            add(searchID); add(pn);
            searchID.setText("search user id!");
            setVisible(true);


            //누르면 ID 검색 결과 나오도록
            bt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    String id = searchID.getText();
                    searchID.setText("");
//                    InfoJDBC info = new InfoJDBC();
//                    String name = info.getName(id);

//                    if(name.equals("")) {
//                        System.out.println("such user doesn't exist...");
//                    }
//                    else {
//                        //해당 아이디 List
                          //JList list_id = new JList();
                         //Frame에 추가
//                    }
                }
            });
        }
    }


    /**
     * Function
     *
     */

    /**
     * 채팅 보내기 관련
     */
    //Enter -> Send message
    boolean isEnter(KeyEvent e){
        return e.getKeyCode() == KeyEvent.VK_ENTER;
    }

    void pressEnter(String userMessage){
        if (isNullString(userMessage))
            return;
        else
            sendMessage(userMessage);

        textField.setText("");
        textField.setCaretPosition(0);
    }

    boolean isNullString(String userMessage){
        return userMessage ==null||userMessage.equals("");
    }

    void sendMessage(String userMessage){
        //----.getSender().sendMessage(userMessage);
    }

    /**
     * 파일 보내기 관련
     */
    void sendFile(){

        String imagePath = FileChooserUtil.getFilePath();
//
//        if(imagePath == null)
//            return;
//        else if(imagePath.endsWith("png") || imagePath.endsWith("jpg"))
//            //-----.getSender().sendImage(imagePath);
//        else
//            JOptionPane.showMessageDialog(null, ".png, .jpg 확장자 파일만 전송 가능");

    }
    //public DefaultListModel getDlm(){return dlm;}




}
