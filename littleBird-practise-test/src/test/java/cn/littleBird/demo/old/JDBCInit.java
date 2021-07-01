package cn.littleBird.demo.old;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCInit {

	public static void main(String[] args) throws SQLException {
		Connection connection;
		Statement statement;
		String user="root",userpwd="gysm.slf";
		try {
			//1.加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			//2.创建连接对象
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/littlebird?serverTimezone=UTC",user,userpwd);
			//3.创建语句对象
			statement = connection.createStatement();
			statement.execute("insert into sys_user (id,account,password) values ('888','sulongfei_','zxcvBN123')");
			//4.关闭数据库连接
			statement.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
