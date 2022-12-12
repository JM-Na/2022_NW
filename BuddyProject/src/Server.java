
//Server.java
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.*;

public class Server {

	int state;
	double result;
	static int ID = 0;
	String driver = "com.mysql.cj.jdbc.Driver";
	static String url = "jdbc:mysql://localhost/BuddyBuddy";
	static String user = "root";
	static String passwd = "12345";

	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		@SuppressWarnings("resource")
		ServerSocket listener = new ServerSocket(7777);
		System.out.println("Server is online.");
		System.out.println("Waiting for connection request...");
		ExecutorService pool = Executors.newFixedThreadPool(20);
		while (true) {
			Socket sock = listener.accept();
			pool.execute(new Capitalizer(sock));
		}
	}

	private static class Capitalizer implements Runnable {
		private Socket socket;
		Scanner scan = new Scanner(System.in);

		Capitalizer(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			System.out.println("Connected " + socket);
			int type = 0;
			try {
				Scanner in = new Scanner(socket.getInputStream());
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				type = in.nextInt();
				switch (type) {
				case 1: { // login
					if (ID == 0) {

						String id = in.nextLine();
						String pwd = in.nextLine();

						try (Connection con = DriverManager.getConnection(url, user, passwd)) {

							Statement stmt = con.createStatement();
							String sql = "select u_id from user where ID = \"" + id + "\" and pwd = \"" + pwd + "\";";
							ResultSet rs = stmt.executeQuery(sql);

							if (rs.next()) {
								// popup login successful
								ID = rs.getInt("u_id");
								String sql1 = "update user set user.online = true where u_id = \"" + ID + "\"";
								// set user state to online
								PreparedStatement pstm = con.prepareStatement(sql1);
								pstm.executeUpdate();

								String sql2 = "update user set user.recent_login = true where u_id = \"" + getTime()
										+ "\"";
								// set user's recent login
								PreparedStatement pstm1 = con.prepareStatement(sql2);
								pstm1.executeUpdate();
							} else {
								// pop up invalid id, pwd
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					} else {
						// pop up already logged in
					}
				}
				case 2: { // signin
					if (ID == 0) {

						String id = in.nextLine();
						String pwd = in.nextLine();
						String chk_pwd = in.nextLine();
						String nickname = in.nextLine();
						String phone = in.nextLine();
						String birth_date = in.nextLine();
						String email = in.nextLine();
						String address = in.nextLine();
						String github = in.nextLine(); // textfield input data

						try (Connection con = DriverManager.getConnection(url, user, passwd)) {

							Statement stmt = con.createStatement();
							String sql = "select u_id from user where ID = \"" + id + "\";";
							ResultSet rs = stmt.executeQuery(sql);
							PreparedStatement pstm = null;

							if (rs.next()) {
								// pop up duplicate id
							} else if (pwd != chk_pwd) {
								// pop up different pwd
							} else {
								String sql1 = "insert into user(ID, pwd, nickname, email, birthdate, phone, address, web) values(\""
										+ id + "\", \"" + pwd + "\", \"" + nickname + "\", \"" + email + "\", \""
										+ birth_date + "\", \"" + phone + "\", \"" + address + "\", \"" + github
										+ "\";)";
								pstm = con.prepareStatement(sql1);
								pstm.executeUpdate();
								// pop up sign in complete
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}

				}
				case 3: { // set nickname and quote
					try (Connection connection = DriverManager.getConnection(url, user, passwd)) {
						String quote = in.nextLine(); // textfield input
						String nickname = in.nextLine(); // textfield input

						String sql = "update user set quote = \"" + quote + "\" where u_id = \"" + ID + "\"";
						PreparedStatement ptmt = connection.prepareStatement(sql);
						ptmt.executeUpdate();
						// save quote
						String sql1 = "update user set nickname = \"" + nickname + "\" where u_id = \"" + ID + "\"";
						PreparedStatement ptmt1 = connection.prepareStatement(sql1);
						ptmt1.executeUpdate();
						// save nickname
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				case 4: { // search user
					try (Connection con = DriverManager.getConnection(url, user, passwd)) {
						String target_id = in.nextLine();
						Statement stmt = con.createStatement();
						String sql = "select nickname from user";
						ResultSet rs = stmt.executeQuery(sql);
						while (rs.next()) {
							if (rs.getString("nickname").contains(target_id)) { // find nickname that contains target words
								System.out.println(rs.getString("nickname"));
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				case 5: { // add friend
					try (Connection con = DriverManager.getConnection(url, user, passwd)) {
						String target_id = in.nextLine();
						Statement stmt = con.createStatement();
						String sql = "select u_id from user where nickname = \"" + target_id + "\"";
						ResultSet rs = stmt.executeQuery(sql);
						if (rs.next()) {
							PreparedStatement ptmt = null, ptmt1 = null;
							String f_name = rs.getString("u_id");
							String sql1 = "insert into request values(\"" + ID + "\", \"" + f_name + "\",1;)";
							ptmt = con.prepareStatement(sql1); 
							// add to request table
						} else {
							// popup invalid friend id
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				case 6: { // accept friend requests
					try (Connection con = DriverManager.getConnection(url, user, passwd)) {
						PreparedStatement ptmt = null, ptmt1 = null, ptmt2 = null;

						String f_name = in.nextLine();
						// return value of accept button
						String sql = "insert into friend values(\"" + ID + "\", \"" + f_name + "\";)";
						ptmt = con.prepareStatement(sql);
						ptmt.executeUpdate();
						String sql1 = "insert into friend values(\"" + f_name + "\", \"" + ID + "\";)";
						ptmt1 = con.prepareStatement(sql1);
						ptmt1.executeUpdate();

						String sql2 = "delete from request where u_id = \"" + f_name + "\" and target_id = \"" + ID
								+ "\" and type = 2)";
						ptmt2 = con.prepareStatement(sql2);
						ptmt2.executeUpdate();
						// delete request from request table
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				case 7: { // deny friend requests
					try (Connection con = DriverManager.getConnection(url, user, passwd)) {
						PreparedStatement ptmt = null;

						String f_name = in.nextLine();
						// return value of accept button
						String sql = "delete from request where u_id = \"" + f_name + "\" and target_id = \"" + ID
								+ "\" and type = 2)";
						ptmt = con.prepareStatement(sql);
						ptmt.executeUpdate();
						// delete denied requests
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				case 8: { // user informations
					try (Connection con = DriverManager.getConnection(url, user, passwd)) {
						String target_id = in.nextLine(); // user_id where we right clicked
						Statement stmt = con.createStatement();
						String sql = "select ID, nickname, online, recent_login from user where u_id = \"" + target_id
								+ "\"";
						ResultSet rs = stmt.executeQuery(sql);

					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				case 9: { // logout
					try (Connection con = DriverManager.getConnection(url, user, passwd)) {
						String sql1 = "update user set user.online = false where u_id = \"" + ID + "\"";
						// set user state to online
						PreparedStatement pstm = con.prepareStatement(sql1);
						pstm.executeUpdate();
						ID = 0;
						// return to login display

					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				case 10: { // find online friends
					try (Connection con = DriverManager.getConnection(url, user, passwd)) {
						Statement stmt = con.createStatement();
						String sql = "select ID, nickname from user where u_id = \"" + ID + "\" and online = true";
						ResultSet rs = stmt.executeQuery(sql);
						while (rs.next()) {
							out.println(rs.getString("ID"));
							out.println(rs.getString("nickname"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				case 11: { // find offline friends
					try (Connection con = DriverManager.getConnection(url, user, passwd)) {
						Statement stmt = con.createStatement();
						String sql = "select ID, nickname from user where u_id = \"" + ID + "\" and online = false";
						ResultSet rs = stmt.executeQuery(sql);
						while (rs.next()) {
							out.println(rs.getString("ID"));
							out.println(rs.getString("nickname"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				case 12: { // find waiting friend requests (sent)
					try (Connection con = DriverManager.getConnection(url, user, passwd)) {
						Statement stmt = con.createStatement();
						String sql = "select target_id from request where u_id = \"" + ID + "\"";
						ResultSet rs = stmt.executeQuery(sql);
						while (rs.next()) {
							out.println(rs.getString("target_id"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				case 13: { // find waiting friend requests (received)
					try (Connection con = DriverManager.getConnection(url, user, passwd)) {
						Statement stmt = con.createStatement();
						String sql = "select u_id from request where target_id = \"" + ID + "\" and type = 1 ";
						ResultSet rs = stmt.executeQuery(sql);
						while (rs.next()) {
							out.println(rs.getString("u_id"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				case 14: { // find waiting chat requests (received)
					try (Connection con = DriverManager.getConnection(url, user, passwd)) {
						Statement stmt = con.createStatement();
						String sql = "select u_id from request where target_id = \"" + ID + "\" and type = 2";
						ResultSet rs = stmt.executeQuery(sql);
						while (rs.next()) {
							out.println(rs.getString("u_id"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {

			}
		}
	}

	static String getTime() {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar now = Calendar.getInstance();

		String nowTime1 = sdf1.format(now.getTime());

		return nowTime1;
	}
}