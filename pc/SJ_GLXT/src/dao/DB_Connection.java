package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * 数据库连接，连接只有一个实例化对象
 */
public class DB_Connection 
{
	private static String driver  = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://127.0.0.1:3306/sj_glxt";
	private static String username = "root";
	private static String password = "238916";
	private static Connection con = null;  //创建用于连接数据库的Connection对象
	
	private DB_Connection() 
	{
		
	}
	
	
	//获取实例化对象的方法
	public static Connection getInstance() throws ClassNotFoundException, SQLException
	{
		if(con == null)
		{
			synchronized(DB_Connection.class)
			{
				if(con == null)
				{
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection( url , username, password);// 创建数据连接 
				}
			}
		}
		return con;
	}
	
	public static void close() throws Exception
	{
		if(con != null)
		{
			synchronized(DB_Connection.class)
			{
				if(con != null)
				{
					con.close();
				}
			}		
		}
	}
}
