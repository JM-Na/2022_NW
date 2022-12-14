import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import DTO.GetWeatherRes;
import DTO.User;

public class Person_Board extends JFrame {
	private JPanel contentPane;
	private JTextArea textArea;

	// Jlist
	Start o = null;

	public Person_Board(Start o_, User user) throws IOException, ClassNotFoundException {
		o = o_;
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Board");
		setBounds(100, 100, 421, 662);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		Dimension frameSize = new Dimension(421, 662);
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();

		setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 405, 623);
		contentPane.add(panel);

		// add btn
		JButton add_btn = new JButton("Add");
		add_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Add_Frame();
			}
		});
		panel.setLayout(null);

		add_btn.setBounds(22, 38, 97, 23);
		panel.add(add_btn);

		// srch btn
		JButton search_btn = new JButton("Search");
		search_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SrchFrame();
			}
		});
		search_btn.setBounds(146, 38, 97, 23);
		panel.add(search_btn);

		// set btn
		JButton Set_btn = new JButton("Settings");
		Set_btn.setBounds(285, 38, 97, 23);
		panel.add(Set_btn);
		Set_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SetFrame(o, user);
			}
		});

		// ---------------------
		// info_user
		JPanel my_info = new JPanel();
		my_info.setBounds(22, 91, 360, 61);
		panel.add(my_info);
		my_info.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 10, 42, 41);
		my_info.add(panel_1);
		panel_1.setLayout(null);
		
		String result = "";
		Scanner in = new Scanner(o.socket.getInputStream());
		PrintWriter os = new PrintWriter(o.socket.getOutputStream(), true);
		System.out.println(1);
		os.println(21);
		System.out.println(1);
		o.out.writeObject(user);
		o.out.flush();
		System.out.println(1);
		result += in.nextLine();

		JLabel myinfo_out = new JLabel(result);
		myinfo_out.setBounds(66, 25, 282, 15);
		my_info.add(myinfo_out);

		JPanel on_p = new JPanel();
		on_p.setBounds(22, 162, 360, 154);
		panel.add(on_p);
		on_p.setLayout(null);

		JLabel lblNewLabel = new JLabel("On-line");
		lblNewLabel.setBounds(12, 10, 57, 15);
		on_p.add(lblNewLabel);

		JPanel on_out = new JPanel();
		on_out.setBackground(Color.WHITE);
		on_out.setBounds(22, 35, 311, 98);
		on_p.add(on_out);
		on_out.setLayout(null);

		JList on_list = new JList();
		on_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		on_list.setBounds(0, 0, 1, 1);
		on_out.add(on_list);

		JPopupMenu online_popm = new JPopupMenu();
		online_popm.setBounds(0, 0, 200, 50);
		on_out.add(online_popm);

		JMenuItem on_info = new JMenuItem("정보");
		online_popm.add(on_info);

		on_info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UserinfoFrame();
			}
		});

		JMenuItem on_chat = new JMenuItem("1:1 채팅하기");
		online_popm.add(on_chat);

		on_chat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//new chatFrame();
				int port;
				try {
					o.out.writeInt(9);
					User me = (User)o.oin.readObject();

					o.out.writeInt(20);

					port = o.oin.readInt();
					ExecutorService pool = Executors.newFixedThreadPool(10);

					pool.execute(new Chat().service(me.getNickname(), InetAddress.getLocalHost(), port));

				} catch (IOException ex) {
					throw new RuntimeException(ex);
				} catch (ClassNotFoundException ex) {
					throw new RuntimeException(ex);
				}
			}
		});

		JMenuItem on_file = new JMenuItem("파일 보내기");
		online_popm.add(on_file);

		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(294, 0, 17, 98);
		on_out.add(scrollBar);

		JPanel off_p = new JPanel();
		off_p.setLayout(null);
		off_p.setBounds(22, 336, 360, 154);
		panel.add(off_p);

		JLabel lblOffline = new JLabel("Off-line");
		lblOffline.setBounds(12, 10, 57, 15);
		off_p.add(lblOffline);

		JPanel off_out = new JPanel();
		off_out.setLayout(null);
		off_out.setBackground(Color.WHITE);
		off_out.setBounds(22, 35, 311, 98);
		off_p.add(off_out);

		JList off_list = new JList();
		off_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		off_list.setBounds(0, 0, 1, 1);
		off_out.add(off_list);

		// off_list.addElement("12341241");

		JPopupMenu off_popm = new JPopupMenu();
		off_popm.setBounds(0, 0, 200, 50);
		off_out.add(off_popm);

		JMenuItem off_info = new JMenuItem("정보");
		off_popm.add(off_info);

		JMenuItem off_chat = new JMenuItem("1:1 채팅");
		off_popm.add(off_chat);
		off_chat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// new (ChatFrame);
			}
		});

		JMenuItem off_file = new JMenuItem("파일 보내기");
		off_popm.add(off_file);

		JScrollBar scrollBar_1 = new JScrollBar();
		scrollBar_1.setBounds(294, 0, 17, 98);
		off_out.add(scrollBar_1);

		// 공공데이터
		textArea = new JTextArea();
		textArea.setBounds(22, 510, 360, 77);
		List<GetWeatherRes> getWeatherRes = new ArrayList<GetWeatherRes>();
		o.out.writeInt(19);
		o.out.flush();
		getWeatherRes = (List<GetWeatherRes>) o.oin.readObject();
		for(int i = 0; i<getWeatherRes.size(); i++) {
			if(getWeatherRes.get(i).getCategory().equals("POP"))
				textArea.append("강수확률 : " + getWeatherRes.get(i).getFcstValue() + "%");
			else if(getWeatherRes.get(i).getCategory().equals("REH"))
				textArea.append("습도 : " + getWeatherRes.get(i).getFcstValue() + "%");
			else if(getWeatherRes.get(i).getCategory().equals("SKY")) {
				int sky = Integer.parseInt(getWeatherRes.get(i).getFcstValue());
				String skyStatus = "하늘 : ";
				if(sky < 6)
					skyStatus += "맑음";
				else if(sky < 8)
					skyStatus += "구름 많음";
				else
					skyStatus += "흐림";

				textArea.append(skyStatus);
			}
			else {
				textArea.append("기온 : "+getWeatherRes.get(i).getFcstValue()+"℃");
			}
		}
		panel.add(textArea);

		setVisible(true);
	}

//    public static void main(String[] args) {
//        new Person_Board();
//    }

}

class Chat extends JFrame implements ActionListener,Runnable {
	private JTextArea output;
	private JTextField input;
	private JButton sendBtn;
	private Socket socket;
	private ObjectInputStream reader=null;
	private ObjectOutputStream writer=null;
	private String nickName;

	public Chat() throws IOException {


		//센터에 TextArea만들기
		output = new JTextArea();
		output.setFont(new Font("맑은 고딕",Font.BOLD,15));
		output.setEditable(false);
		JScrollPane scroll = new JScrollPane(output);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  //항상 스크롤바가 세로로 떠있음

		//하단에 버튼과 TextArea넣기
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		input = new JTextField();

		sendBtn = new JButton("보내기");

		bottom.add("Center",input);  //센터에 붙이기
		bottom.add("East",sendBtn);  //동쪽에 붙이기
		//container에 붙이기
		Container c = this.getContentPane();
		c.add("Center", scroll);  //센터에 붙이기
		c.add("South", bottom);  //남쪽에 붙이기
		//윈도우 창 설정
		setBounds(300,300,300,300);
		setVisible(true);



		//윈도우 이벤트

		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				//System.exit(0);
				try{
					//InfoDTO dto = new InfoDTO(nickName,Info.EXIT);
					InfoDTO dto = new InfoDTO();
					dto.setNickName(nickName);
					dto.setCommand(Info.EXIT);
					writer.writeObject(dto);  //역슬러쉬가 필요가 없음
					writer.flush();
				}catch(IOException io){
					io.printStackTrace();
				}
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e){
		try{
			//서버로 보냄
			//JTextField값을 서버로보내기
			//버퍼 비우기
			String msg=input.getText();
			InfoDTO dto = new InfoDTO();
			//dto.setNickName(nickName);
			if(msg.equals("exit")){
				dto.setCommand(Info.EXIT);
			} else {
				dto.setCommand(Info.SEND);
				dto.setMessage(msg);
				dto.setNickName(nickName);
			}
			writer.writeObject(dto);
			writer.flush();
			input.setText("");

		}catch(IOException io){
			io.printStackTrace();
		}
	}

	public Runnable service(String nickName, InetAddress serverIP, int port) throws IOException {
		this.socket = new Socket(serverIP, port);
		this.nickName = nickName;
		reader= new ObjectInputStream(socket.getInputStream());
		writer = new ObjectOutputStream(socket.getOutputStream());
		Thread t = new Thread(this);
		t.start();
		input.addActionListener(this);
		sendBtn.addActionListener(this);
		return null;
	}

	@Override
	public void run() {
		InfoDTO dto= null;
		while(true){
			try{
				dto = (InfoDTO) reader.readObject();
				if(dto.getCommand()==Info.EXIT){  //서버로부터 내 자신의 exit를 받으면 종료됨
					reader.close();
					writer.close();
					socket.close();
					System.exit(0);
				} else if(dto.getCommand()==Info.SEND){
					output.append(dto.getMessage()+"\n");

					int pos=output.getText().length();
					output.setCaretPosition(pos);
				}
			}catch(IOException e){
				e.printStackTrace();
			}catch(ClassNotFoundException e){
				e.printStackTrace();
			}
		}
	}
}

