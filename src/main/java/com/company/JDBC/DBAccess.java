package com.company.JDBC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBAccess {
	// 公共的两部分：1建立连接、2资源释放
	// 想要程序一运行就能建立连接，连接需要的东西都写成static静态的，最先执行的。
	private static String driver;
	private static String url;
	private static String userName;
	private static String password;

	private static DBAccess Instance;
	static {
		Instance=new DBAccess();
	}
	public static DBAccess getInstance(){
		return Instance;
	}

	private DBAccess(){
			Properties p = new Properties();
			try {
				p.load(this.getClass().getResourceAsStream("/jdbc.properties"));//(new BufferedReader(new FileReader("resources/jdbc.properties")));
				driver = p.getProperty("mysql.driver");
				url = p.getProperty("mysql.url");
				userName = p.getProperty("mysql.userName");
				password = p.getProperty("mysql.password");
				// 加载驱动
				Class.forName(driver);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	// 建立连接
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, userName, password);
	}

	// 2.释放资源
	public static void close(ResultSet rs, PreparedStatement pst, Connection con) throws SQLException {
		close(rs);
		close(pst);
		close(con);
	}

	private static void close(Connection con) throws SQLException {
		if (con != null) {
			con.close();
		}
	}

	private static void close(PreparedStatement pst) throws SQLException {
		if (pst != null) {
			pst.close();
		}
	}

	private static void close(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
	}

}

