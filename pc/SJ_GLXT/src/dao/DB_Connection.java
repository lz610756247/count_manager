package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * ���ݿ����ӣ�����ֻ��һ��ʵ��������
 */
public class DB_Connection 
{
	private static String driver  = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://127.0.0.1:3306/sj_glxt";
	private static String username = "root";
	private static String password = "238916";
	private static Connection con = null;  //���������������ݿ��Connection����
	
	private DB_Connection() 
	{
		
	}
	
	
	//��ȡʵ��������ķ���
	public static Connection getInstance() throws ClassNotFoundException, SQLException
	{
		if(con == null)
		{
			synchronized(DB_Connection.class)
			{
				if(con == null)
				{
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection( url , username, password);// ������������ 
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
