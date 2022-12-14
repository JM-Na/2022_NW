import DTO.GetWeatherRes;
import DTO.Request;
import DTO.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

class ChatServerObject {
	private static ServerSocket serverSocket;
	private static int roomNum = 0;
	private static ObjectInputStream reader;
	private static ObjectOutputStream writer;

	private static List<User> onlineUser;
	private List<ChatHandlerObject> list;

	public ChatServerObject(Socket socket) {
		try {
			list = new ArrayList<ChatHandlerObject>();
			while (true) {
				ChatHandlerObject handler = new ChatHandlerObject(socket, list);
				handler.start();
				list.add(handler);

			} // while
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		try {
			System.out.println("Server is running...");
			serverSocket = new ServerSocket(9000);
			onlineUser = new ArrayList<User>();

			while (true) {
				Socket socket = serverSocket.accept();
				MainService mainService = new MainService(socket);
				mainService.start();
				System.out.println(socket.getInetAddress() + " : " + socket.getPort() + "is connected");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class MainService extends Thread {

		private Socket socket;
		private User user;

		public MainService(Socket socket) {
			this.socket = socket;

		}

		public void run() {

			int state;
			double result;
			int ID = 0;
			final int DEFAULT_BUFFER_SIZE = 100000;
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/messenger";
			String user = "messenger";
			String passwd = "1234";

			try {
				ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				Scanner in = new Scanner(socket.getInputStream());
				while (true) {
					// type���� request ����
					int type = in.nextInt();
					switch (type) {
					case 1: { // login
						if (ID == 0) { // when not logged in
							try (Connection con = DriverManager.getConnection(url, user, passwd)) {
								User u = (User) oin.readObject(); // get object user from client

								Statement stmt = con.createStatement();
								String sql = "select u_id from user where ID = \"" + u.getID() + "\"";
								ResultSet rs = stmt.executeQuery(sql);
								if (rs.next()) {
									Statement stmt1 = con.createStatement();
									String sql1 = "select u_id from user where ID = \"" + u.getID() + "\" and pwd = \""
											+ u.getPwd() + "\";";
									ResultSet rs1 = stmt.executeQuery(sql1);
									if (rs1.next()) {
										// popup login successful
										String sql2 = "update user set user.online = true where ID = \"" + u.getID()
												+ "\"";
										// set user state to online
										PreparedStatement pstm = con.prepareStatement(sql2);
										pstm.executeUpdate();

										String sql3 = "update user set recent_login = \"" + getTime()
												+ "\" where ID =\"" + u.getID() + "\"";
										// set user's recent login
										PreparedStatement pstm1 = con.prepareStatement(sql3);
										pstm1.executeUpdate();

										ResultSet rs2 = stmt.executeQuery(sql);
										if (rs2.next()) {
											ID = rs2.getInt("u_id"); // update logged in id
										}
										out.println("s");
									} else {
										out.println("w");
										// Wrong Password
									}
								} else {
									out.println("i");
									// pop up invalid id
								}
							} catch (SQLException e) {
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							out.println("l");
							// pop up already logged in
						}
						break;
					}
					case 2: { // signin
								// textfield input data
						try (Connection con = DriverManager.getConnection(url, user, passwd)) {
							User u = (User) oin.readObject();
							System.out.println(1);
							// get object user from client
							Statement stmt = con.createStatement();
							String sql = "select u_id from user where ID = \"" + u.getID() + "\";";
							// check if there is duplicate id
							ResultSet rs = stmt.executeQuery(sql);
							PreparedStatement pstm = null;
							if (rs.next()) {
								out.println("d");
								// pop up duplicate id
							}  else {
								String sql1 = "insert into user(ID, pwd, nickname, email, birthdate, phone, name, web) values(\""
										+ u.getID() + "\", \"" + u.getPwd() + "\", \"" + u.getNickname() + "\", \""
										+ u.getEmail() + "\", \"" + u.getBirthdate() + "\", \"" + u.getPhone()
										+ "\", \"" + u.getName() + "\", \"" + u.getWeb() + "\")";
								pstm = con.prepareStatement(sql1);
								pstm.executeUpdate();
								out.println("s");
								// pop up sign in complete
							}
						} catch (SQLException | ClassNotFoundException e) {
							e.printStackTrace();
						}
						break;
					}
					case 3: { // set nickname and quote
						try (Connection connection = DriverManager.getConnection(url, user, passwd)) {
							User u = (User) oin.readObject(); // get object user from client
							System.out.println(1);

							String sql = "update user set quote = \"" + u.getQuote() + "\" where u_id = \"" + ID + "\"";
							PreparedStatement ptmt = connection.prepareStatement(sql);
							ptmt.executeUpdate();
							System.out.println(1);
							// save quote
							String sql1 = "update user set nickname = \"" + u.getNickname() + "\" where u_id = \"" + ID
									+ "\"";
							PreparedStatement ptmt1 = connection.prepareStatement(sql1);
							ptmt1.executeUpdate();
							System.out.println(1);
							// save nickname
							out.println("s");
						} catch (SQLException | ClassNotFoundException e) {
							e.printStackTrace();
						}
						break;
					}
					case 4: { // search user
						try (Connection con = DriverManager.getConnection(url, user, passwd)) {
							String target_id = in.nextLine();

							Statement stmt = con.createStatement();
							String sql = "select nickname from user";
							ResultSet rs = stmt.executeQuery(sql);
							while (rs.next()) {
								if (rs.getString("nickname").contains(target_id)) { // find nickname that contains
																					// target
									// words
									out.println(rs.getString("nickname"));
								}
							}
							out.println("0"); // end sign
						} catch (SQLException e) {
							e.printStackTrace();
						}
						break;
					}
					case 5: { // add friend
						try (Connection con = DriverManager.getConnection(url, user, passwd)) {
							User u = (User) oin.readObject(); // get object user from client
							String target_name = u.getNickname();
							String sql = "select u_id from user where nickname = \"" + target_name + "\"";
							Statement stmt = con.createStatement();
							ResultSet rs = stmt.executeQuery(sql);
							if (rs.next()) {
								int target_id = rs.getInt("u_id");
								String sql1 = "select * from ban where ban_id = \"" + target_id + "\" and u_id = \""
										+ ID + "\"";
								Statement stmt1 = con.createStatement();
								ResultSet rs1 = stmt.executeQuery(sql1);
								if (rs1.next()) {
									// already banned user
								} else {
									PreparedStatement ptmt = null;
									String sql2 = "insert into request values(\"" + ID + "\", \"" + target_id
											+ "\",1;)";
									ptmt = con.prepareStatement(sql2);
									// add to request table
								}
							} else {
								// popup invalid friend id
							}
						} catch (SQLException | ClassNotFoundException e) {
							e.printStackTrace();
						}
						break;
					}
					case 6: { // accept friend requests
						try (Connection con = DriverManager.getConnection(url, user, passwd)) {
							PreparedStatement ptmt = null, ptmt1 = null, ptmt2 = null;
							User u = (User) oin.readObject(); // get object user from client
							int f_id = u.getU_id();
							// return value of accept button
							String sql = "insert into friend values(\"" + ID + "\", \"" + f_id + "\";)";
							ptmt = con.prepareStatement(sql);
							ptmt.executeUpdate();
							String sql1 = "insert into friend values(\"" + f_id + "\", \"" + ID + "\";)";
							ptmt1 = con.prepareStatement(sql1);
							ptmt1.executeUpdate();
							// add to friend list
							String sql2 = "delete from request where u_id = \"" + f_id + "\" and target_id = \"" + ID
									+ "\" and type = 2)";
							ptmt2 = con.prepareStatement(sql2);
							ptmt2.executeUpdate();
							// delete completed request from request table
						} catch (SQLException | ClassNotFoundException e) {
							e.printStackTrace();
						}
						break;
					}
					case 7: { // deny friend requests
						try (Connection con = DriverManager.getConnection(url, user, passwd)) {
							PreparedStatement ptmt = null;

							String f_name = in.nextLine();
							// return value of deny button
							String sql = "delete from request where u_id = \"" + f_name + "\" and target_id = \"" + ID
									+ "\" and type = 2)";
							ptmt = con.prepareStatement(sql);
							ptmt.executeUpdate();
							// delete denied requests
						} catch (SQLException e) {
							e.printStackTrace();
						}
						break;
					}
					case 8: { // user informations
						try (Connection con = DriverManager.getConnection(url, user, passwd)) {
							String target_id = in.nextLine(); // user_id where we right clicked
							Statement stmt = con.createStatement();
							String sql = "select ID, nickname, online, recent_login from user where u_id = \""
									+ target_id + "\"";
							ResultSet rs = stmt.executeQuery(sql);

						} catch (SQLException e) {
							e.printStackTrace();
						}
						break;
					}
					case 9: { // logout
						try (Connection con = DriverManager.getConnection(url, user, passwd)) {
							// get request with user's u_id
							String sql1 = "update user set user.online = false where u_id = \"" + ID + "\"";
							// set user state to online
							PreparedStatement pstm = con.prepareStatement(sql1);
							pstm.executeUpdate();
							ID = 0;
							// return to login display

						} catch (SQLException e) {
							e.printStackTrace();
						}
						break;
					}
					case 10: { // find online friends
						try (Connection con = DriverManager.getConnection(url, user, passwd)) {
							Statement stmt = con.createStatement();
							String sql = "select name, nickname, quote from user join friend where friend.u_id = \""
									+ ID + "\" and user.online = true and user.ID != \"" + ID + "\"";
							ResultSet rs = stmt.executeQuery(sql);
							while (rs.next()) {
								out.println(rs.getString("name") + "," + rs.getString("nickname") + ","
										+ rs.getString("quote"));
							}
							out.println("0"); // end sign
						} catch (SQLException e) {
							e.printStackTrace();
						}
						break;
					}
					case 11: { // find offline friends
						try (Connection con = DriverManager.getConnection(url, user, passwd)) {
							Statement stmt = con.createStatement();
							String sql = "select f_id, nickname from user join friend where friend.u_id = \"" + ID
									+ "\" and user.online = false and user.ID != \"" + ID + "\"";
							ResultSet rs = stmt.executeQuery(sql);
							while (rs.next()) {
								out.println(rs.getString("ID") + " " + rs.getString("nickname"));
							}
							out.println("0"); // end sign
						} catch (SQLException e) {
							e.printStackTrace();
						}
						break;
					}
					case 12: { // find waiting friend requests (sent)
						try (Connection con = DriverManager.getConnection(url, user, passwd)) {
							Statement stmt = con.createStatement();
							String sql = "select target_id from request where u_id = \"" + ID + "\"";
							ResultSet rs = stmt.executeQuery(sql);
							while (rs.next()) {
								out.println(rs.getString("target_id"));
							}
							out.println("0"); // end sign
						} catch (SQLException e) {
							e.printStackTrace();
						}
						break;
					}
					case 13: { // find waiting friend requests (received)
						try (Connection con = DriverManager.getConnection(url, user, passwd)) {
							Statement stmt = con.createStatement();
							String sql = "select u_id from request where target_id = \"" + ID + "\" and type = 1 ";
							ResultSet rs = stmt.executeQuery(sql);
							while (rs.next()) {
								String f_id = rs.getString("u_id");
								String sql1 = "select * from ban where ban_id = \"" + f_id + "\" and u_id = \"" + ID
										+ "\"";
								Statement stmt1 = con.createStatement();
								ResultSet rs1 = stmt1.executeQuery(sql1);

								if (!rs1.next())
									out.println(rs.getString("u_id"));
								// print if target is not on banned list
							}
							out.println("0"); // end sign
						} catch (SQLException e) {
							e.printStackTrace();
						}
						break;
					}
					case 14: { // find waiting chat requests (received)
						try (Connection con = DriverManager.getConnection(url, user, passwd)) {
							Statement stmt = con.createStatement();
							String sql = "select u_id from request where target_id = \"" + ID + "\" and type = 2";
							ResultSet rs = stmt.executeQuery(sql);
							while (rs.next()) {
								String f_id = rs.getString("u_id");
								String sql1 = "select * from ban where ban_id = \"" + f_id + "\" and u_id = \"" + ID
										+ "\"";
								Statement stmt1 = con.createStatement();
								ResultSet rs1 = stmt1.executeQuery(sql1);

								if (!rs1.next())
									out.println(rs.getString("u_id"));
								// print if target is not on banned list
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						break;
					}
					case 15: { // log out
						try (Connection con = DriverManager.getConnection(url, user, passwd)) {
							String sql1 = "update user set user.online = false where u_id = \"" + ID + "\"";
							// set user state to offline
							PreparedStatement pstm = con.prepareStatement(sql1);
							pstm.executeUpdate();

							String sql2 = "update user set user.recent_login = true where u_id = \"" + getTime() + "\"";
							// set user's recent login
							PreparedStatement pstm1 = con.prepareStatement(sql2);
							pstm1.executeUpdate();
							// pop up logout successful
							ID = 0; // delete previous ID and set to default
						} catch (SQLException e) {
							e.printStackTrace();
						}
						break;
					}
					case 16: { // transfer file
						InputStream is = socket.getInputStream();
						OutputStream os = socket.getOutputStream();
						double startTime = System.currentTimeMillis();
						byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
						int readBytes;
						while ((readBytes = is.read(buffer)) != -1) {
							os.write(buffer, 0, readBytes);
						}
						break;
						// send message to receiver
					}
					case 17: { // accept friend requests
						try (Connection con = DriverManager.getConnection(url, user, passwd)) {
							PreparedStatement ptmt = null, ptmt1 = null, ptmt2 = null;

							String f_name = in.nextLine();
							// return value of accept button
							String sql = "insert into friend values(\"" + f_name + "\", \"" + ID + "\";)";
							ptmt1 = con.prepareStatement(sql);
							ptmt1.executeUpdate();
							// add to friend list
							String sql2 = "delete from friend where u_id = \"" + ID + "\" and target_id = \"" + f_name
									+ "\" and type = 2)";
							ptmt2 = con.prepareStatement(sql2);
							ptmt2.executeUpdate();
							// delete friend list
						} catch (SQLException e) {
							e.printStackTrace();
						}
						break;
					}
					case 18: {// delete from ban table
						try (Connection con = DriverManager.getConnection(url, user, passwd)) {
							User u = (User) oin.readObject(); // get Object from client
							String sql = "delete from ban where ban_id = \"" + u.getU_id() + "\"and u_id = \"" + ID
									+ "\"";
							PreparedStatement ptmt = con.prepareStatement(sql);
							ptmt.executeUpdate();

							// pop up deleted from ban list!
						} catch (SQLException e) {
							e.printStackTrace();
						}
						break;
					}
					case 19: {
						// ���û ���� API
						Date nowDate = new Date();
						LocalTime nowTime = LocalTime.now();

						Calendar cal = new GregorianCalendar(Locale.KOREA);

						DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH");
						String nowTimeParams = timeFormatter.format(nowTime);
						if (nowTimeParams.equals("00")) {
							nowTimeParams = "2300";
							cal.setTime(nowDate);
							cal.add(Calendar.DATE, -1);
						} else {
							int time = Integer.parseInt(nowTimeParams) - 1;
							if (time < 10)
								nowTimeParams = "0" + String.valueOf(time);
							else
								nowTimeParams = String.valueOf(time);
							nowTimeParams += "00";
						}

						SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

						String nowDateParams = dateFormatter.format(nowDate);

						StringBuilder urlBuilder = new StringBuilder(
								"http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /* URL */
						urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
								+ "=PGmFRbd3Kb2rvBFEwLJ5CryUS%2BvT3b%2F9YZfasm35%2FngeIe8zMvse4omZSX98fY9%2Bd9pfqiBLFaVBZziPeWoOYA%3D%3D"); /*
																																			 * Service
																																			 * Key
																																			 */
						urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "="
								+ URLEncoder.encode("1", "UTF-8")); /* ��������ȣ */
						urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
								+ URLEncoder.encode("12", "UTF-8")); /* �� ������ ��� �� */
						urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "="
								+ URLEncoder.encode("JSON", "UTF-8")); /* ��û�ڷ�����(XML/JSON) Default: XML */
						urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "="
								+ URLEncoder.encode(nowDateParams, "UTF-8")); /* ��21�� 6�� 28�� ��ǥ */
						urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "="
								+ URLEncoder.encode(nowTimeParams, "UTF-8")); /* 06�� ��ǥ(���ô���) */
						urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "="
								+ URLEncoder.encode("55", "UTF-8")); /* ���������� X ��ǥ�� */
						urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "="
								+ URLEncoder.encode("127", "UTF-8")); /* ���������� Y ��ǥ�� */
						URL pUrl = new URL(urlBuilder.toString());
						HttpURLConnection conn = (HttpURLConnection) pUrl.openConnection();
						conn.setRequestMethod("GET");
						conn.setRequestProperty("Content-type", "application/json");
						System.out.println("Response code: " + conn.getResponseCode());
						BufferedReader rd;
						if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
							rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						} else {
							rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
						}
						StringBuilder sb = new StringBuilder();
						String line;
						while ((line = rd.readLine()) != null) {
							sb.append(line);
						}
						rd.close();
						conn.disconnect();
						String pResult = sb.toString();

						// Json parser�� ����� ������� ���ڿ� �����͸� ��üȭ
						JSONParser parser = new JSONParser();
						JSONObject obj = (JSONObject) parser.parse(pResult);
						// response Ű�� ������ �����͸� �Ľ�
						JSONObject parse_response = (JSONObject) obj.get("response");
						// response �� ���� body ã��
						JSONObject parse_body = (JSONObject) parse_response.get("body");
						// body �� ���� items ã��
						JSONObject parse_items = (JSONObject) parse_body.get("items");

						// items�� ���� itemlist �� �ޱ�
						JSONArray parse_item = (JSONArray) parse_items.get("item");
						String category;
						JSONObject weather; // parse_item�� �迭�����̱� ������ �ϳ��� �����͸� �ϳ��� �����ö� ���
						// ī�װ��� ���� �޾ƿ���
						String day = "";
						String time = "";
						List<GetWeatherRes> getWeatherRes = new ArrayList<GetWeatherRes>();
						for (int i = 0; i < parse_item.size(); i++) {
							weather = (JSONObject) parse_item.get(i);
							Object fcstValue = weather.get("fcstValue");
							Object fcstDate = weather.get("fcstDate");
							Object fcstTime = weather.get("fcstTime");
							category = (String) weather.get("category");

							if (category.equals("POP") || category.equals("REH") || category.equals("SKY")
									|| category.equals("T3H")) {
								getWeatherRes.add(new GetWeatherRes(category, fcstValue.toString(), fcstDate.toString(),
										fcstTime.toString()));
							}
						}

						out.println(getWeatherRes);

						/*
						 * �׸� �׸�� ���� POP ����Ȯ�� % REH ���� % SKY �ϴû��� �ڵ尪 0~5 ����, 6~8 ��������,
						 * 9~10 �帲 T3H 3�ð� ��� ��
						 * 
						 * 
						 */
					}
					case 20: {
						ServerSocket listner = new ServerSocket(9500);
						Socket socket = listner.accept();

						new ChatServerObject(socket);

						break;
					}
					case 21: {
						// ä�� �õ�
						try (Connection con = DriverManager.getConnection(url, user, passwd)) {
							User u = (User) oin.readObject();

							Statement stmt = con.createStatement();
							String sql = "select name, nickname, quote from user where ID = \"" + u.getID() + "\"";
							ResultSet rs = stmt.executeQuery(sql);
							String res = "Name : ";
							int i = 0;
							while (rs.next()) {
								res += rs.getString("name");
								res += ", NickName : ";
								res += rs.getString("nickname");
								res += ", Quote : ";
								res += rs.getString("quote");
							}
							out.println(res);
						}

						break;
					}
					case 22: { // sign out
						// ä�� �õ�
						try (Connection con = DriverManager.getConnection(url, user, passwd)) {
							System.out.println(1);
							User u = (User) oin.readObject();
							String sql = "delete from user where ID = \"" + u.getID() + "\"";
							PreparedStatement pstm = con.prepareStatement(sql);
							pstm.executeUpdate();
							
							out.println("s");
						}

						break;
					}
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// ���Ŀ� �ۼ�
			}
		}

		static String getTime() { // method get time
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// format of return value
			Calendar now = Calendar.getInstance();

			String nowTime1 = sdf1.format(now.getTime());

			return nowTime1;
		}
	}
}
//Ŭ���̾�Ʈ�� ���ÿ� ä���ϱ����ؼ� �����尡 �ʿ��ϵ��� �� �����带 ���ÿ� �޾Ƶ��� �� �ִ� ���� ���� �����尡 �Ǿ��־�� ��!
