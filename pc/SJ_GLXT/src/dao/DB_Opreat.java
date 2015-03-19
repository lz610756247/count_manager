package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import sj.util.Calculat;
import sj.util.Item;
import sj.util.Person;
import sj.util.Quan;
import sj.util.TbUser;

/*
 * 数据库的操作类
 */
public class DB_Opreat 
{

	private static Connection con()
	{
		//连接数据库
		Connection con = null;
		try 
		{
			con = DB_Connection.getInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	
	//插入订单基本信息
	public static boolean inset_jj_main(Map info) throws ClassNotFoundException, SQLException
	{
		//连接数据库
		Connection con = DB_Connection.getInstance();
		String gonghuoshang = info.get("供货商").toString();
		String dianhua = info.get("电话").toString();
		String riqi = info.get("日期").toString();
		
		
		PreparedStatement pst;
		try 
		{
			String sql = "insert into t_jinhuo_main(jh_name , jh_phone , jh_time , create_time)"
					+ " values(? , ? , ? , ?)";
			pst = con.prepareStatement(sql);
			pst.setString(1, gonghuoshang);
			pst.setString(2, dianhua);
			pst.setString(3, riqi);
			pst.setString(4, Calculat.getTime());
			pst.executeUpdate();
			pst.close();
		    return true;
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//查找订单ID
	public static int get_jj_main_Id(Map info) throws ClassNotFoundException, SQLException
	{
		//连接数据库
		Connection con = DB_Connection.getInstance();
		String gonghuoshang = info.get("供货商").toString();
		String dianhua = info.get("电话").toString();
		String riqi = info.get("日期").toString();
		int Id = -1;
		
		Statement stmt;
		stmt = con.createStatement();
		String sql = "select * from t_jinhuo_main where jh_name = \""+gonghuoshang+"\""
				+" and jh_phone = \""+dianhua+"\" and jh_time = \""+riqi+"\"";
	    ResultSet res=	stmt.executeQuery(sql);	    
	    while(res.next())
	    {
	    	Id = res.getInt("Id");
	    }
		return Id;
	}
	
	
	//根据进货ID，插入对应的每一项
	public static boolean insert_jj_detail(Vector v , int id_index)
			throws ClassNotFoundException, SQLException
	{
		int jh_index = -1;
		
		PreparedStatement pst;
		try 
		{
			String sql = "insert into t_jinhuo_detail(sp_name , sp_num , sp_dw" +
					", sp_price  , sp_bz , sp_sf , jh_index )"
					+ " values(? , ? , ? , ? , ? , ? , ?)";
			pst = con().prepareStatement(sql);
			for(int i=0; i<v.size(); i++)
			{
				List v1 = (List) v.get(i);
				pst.setString(1, v1.get(0).toString());
				pst.setString(2, v1.get(1).toString());
				pst.setString(3, v1.get(2).toString());
				pst.setString(4, v1.get(3).toString());
				pst.setString(5, v1.get(4).toString());
				pst.setDouble(6
						, Double.parseDouble(v1.get(6).toString().replace(",", "")));
				pst.setInt(7, id_index);
				pst.executeUpdate();
				
			}
			pst.close();
			
			//insert_jh(v);
			
		    return true;
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;		
	}
	
	
	//插入进货
	public static void insert_jh(Vector v)
	{
		PreparedStatement pst;
		PreparedStatement pst1;
		try 
		{
			String sql = "insert into t_jh(sp_name , sp_num , sp_dw" +
					", sp_price , sp_bz )"
					+ " values(? , ? , ? , ? , ?)";
			
			pst = con().prepareStatement(sql);
			
			for(int i=0; i<v.size(); i++)
			{
				List v1 = (List) v.get(i);
				
				if(v1.get(6).toString().equals("有"))
				{
					String sql1 = "update t_jh set sp_num = sp_num+"+
							Integer.parseInt(v1.get(1).toString())
							+" where sp_name = '"+v1.get(0).toString()+"' and sp_bz = '"
							+v1.get(5).toString()+"'";
					pst1 = con().prepareStatement(sql1);
					pst1.executeUpdate();
				}
				else
				{
					pst.setString(1, v1.get(0).toString());
					pst.setString(2, v1.get(1).toString());
					pst.setString(3, v1.get(2).toString());
					pst.setString(4, v1.get(3).toString());
					pst.setString(5, v1.get(5).toString());
					pst.executeUpdate();
				}				
			}
			pst.close();
			
		    
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//根据进货ID，插入对应的每一项
		public static boolean insert_jj_detail1(Vector v , int id_index)
				throws ClassNotFoundException, SQLException
		{
			//连接数据库
			Connection con = DB_Connection.getInstance();
			String sp_name="" , sp_num="" , sp_dw="" , sp_price="";
			int jh_index = -1;
			
			PreparedStatement pst;
			try 
			{
				String sql = "insert into t_jinhuo_detail(sp_name , sp_num , sp_dw" +
						", sp_price  , sp_bz , jh_index )"
						+ " values(? , ? , ? , ? , ? , ?)";
				pst = con.prepareStatement(sql);
				for(int i=0; i<v.size(); i++)
				{
					List v1 = (List) v.get(i);
					pst.setString(1, v1.get(1).toString());
					pst.setString(2, v1.get(2).toString());
					pst.setString(3, v1.get(3).toString());
					pst.setString(4, v1.get(4).toString());
					pst.setString(5, v1.get(6).toString());
					pst.setInt(6, id_index);
					pst.executeUpdate();
					
				}
				pst.close();
				
				insert_jh1(v);
				
			    return true;
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;		
		}
		
		
		//插入进货
		public static void insert_jh1(Vector v)
		{
			boolean bool = false;
			PreparedStatement pst;
			PreparedStatement pst1 = null;
			Statement stmt;
			try 
			{
				stmt = con().createStatement();
				String sql = "select * from t_jh"+" where sp_name = '"+v.get(1).toString()+"' and sp_bz = '"
						+v.get(6).toString()+"'";
			    ResultSet res=	stmt.executeQuery(sql);	    
			    while(res.next())
			    {
			    	bool = true;
			    }		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try 
			{
				String sql = "insert into t_jh(sp_name , sp_num , sp_dw" +
						", sp_price , sp_bz )"
						+ " values(? , ? , ? , ? , ?)";
				
				pst = con().prepareStatement(sql);
				System.out.println(v.get(7).toString());
				if(bool)
				{
					String sql1 = "update t_jh set sp_num = sp_num+"+
							Integer.parseInt(v.get(2).toString())
							+" where sp_name = '"+v.get(1).toString()+"' and sp_bz = '"
							+v.get(6).toString()+"'";
					pst1 = con().prepareStatement(sql1);
					pst1.executeUpdate();
				}
				else
				{
					pst.setString(1, v.get(1).toString());
					pst.setString(2, v.get(2).toString());
					pst.setString(3, v.get(3).toString());
					pst.setString(4, v.get(4).toString());
					pst.setString(5, v.get(6).toString());
					pst.executeUpdate();
				}		
				pst.close();
				pst1.close();
			    
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	
	//获取进货单列表
	public static List get_jj_main()
	{
		//连接数据库
		Connection con = null;
		
		List<Vector> list = new ArrayList();
		try 
		{
			con = DB_Connection.getInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int Id = -1;
		
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "select * from t_jinhuo_main order by create_time desc";
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Vector v = new Vector();
		    	
		    	v.add(res.getInt("Id"));
		    	v.add(res.getString("jh_name"));
		    	v.add(res.getString("jh_phone"));
		    	v.add(res.getString("jh_time"));

		    	list.add(v);
		    }		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	
	//移除所有选中的订单信息
	public static boolean RemoveAllSelectJJ(int Id)
	{
		//连接数据库
		Connection con = null;
		try 
		{
			con = DB_Connection.getInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PreparedStatement pst;
		try 
		{
			int result = 0;
			String sql = "delete from t_jinhuo_main where Id = "+Id;
			pst = con.prepareStatement(sql);
			result = pst.executeUpdate();
			if(result < 0)
				return false;
			
			sql = "delete from t_jinhuo_detail where jh_index = "+Id;
			pst = con.prepareStatement(sql);
			pst.executeUpdate();
			if(result < 0)
				return false;
			
			pst.close();
			return true;
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}	
	
	//查询某一条进货单
	public static Map get_item_jj_main(int id)
	{
		Map map = new HashMap();
		
		//连接数据库
		Connection con = null;
		try 
		{
			con = DB_Connection.getInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "select * from t_jinhuo_main where Id = "+id;
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	map.put("Id", res.getInt("Id"));
		    	map.put("供货商", res.getString("jh_name"));
		    	map.put("电话", res.getString("jh_phone"));
		    	map.put("日期", res.getString("jh_time"));
		    }		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		return map;
	}	
		
	//查询某一条进货单
	public static List get_item_jj_detail(int id)
	{
		List list = new ArrayList();		
		
		Statement stmt;
		try {
			stmt = con().createStatement();
			String sql = "select * from t_jinhuo_detail where jh_index = "+id;
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Vector v = new Vector();
		    	
		    	v.add(res.getInt("Id"));
		    	v.add(res.getString("sp_name"));
		    	v.add(res.getString("sp_num"));
		    	v.add(res.getString("sp_dw"));
		    	v.add(res.getString("sp_price"));
		    	v.add(res.getString("sp_bz"));
		    	v.add(Calculat.Result(Double.parseDouble(res.getString("sp_num"))
		    			*Double.parseDouble(res.getString("sp_price"))));
		    	v.add(Calculat.Result(res.getDouble("sp_sf")));
		    	v.add( Calculat.Result(Double.parseDouble(res.getString("sp_num"))
		    			*Double.parseDouble(res.getString("sp_price"))-res.getDouble("sp_sf")) );
		    	v.add(" ");
		    	list.add(v);
		    }		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return list;
	}
	
	//查询商品信息
	public static List query_all_jj_item()
	{
		List list = new ArrayList();
		Statement stmt;
		try 
		{
			stmt = con().createStatement();
			String sql = "select * from t_jh";
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Item item = new Item();
		    	item.setId(res.getInt("Id")+"");
		    	item.setSpmc(res.getString("sp_name"));
		    	item.setSl(res.getString("sp_num"));
		    	item.setDw(res.getString("sp_dw"));
		    	item.setDj(res.getString("sp_price"));
		    	item.setBz(res.getString("sp_bz"));

		    	list.add(item);
		    }		
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return list;
	}	
	
	//删除某一项指定的商品
	public static boolean remove_item_sp(int id , String table)
	{
		
		PreparedStatement pst;
		try 
		{
			int result = 0;
			
			String sql = "delete from "+table+" where Id = "+id;
			pst = con().prepareStatement(sql);
			pst.executeUpdate();
			if(result < 0)
				return false;
			
			pst.close();
			return true;
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	//更新基础信息
	public static void update_base_info(Map map)
	{
		String Id = map.get("Id").toString();
		String gonghuoshang = map.get("供货商").toString();
		String dianhua = map.get("电话").toString();
		String riqi = map.get("日期").toString();
		PreparedStatement pst;
		
		try 
		{
			int result = 0;
			
			String sql = "update t_jinhuo_main set " +
					"jh_name = ? , jh_phone = ? , jh_time = ? where Id = "+Id;
			pst = con().prepareStatement(sql);
			
			pst.setString(1, gonghuoshang);
			pst.setString(2, dianhua);
			pst.setString(3, riqi);
			
			pst.executeUpdate();
			
			
			pst.close();
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//更新详细数据
	public static void update_jj_detail(List list , String id_index)
	{
		String sp_name="" , sp_num="" , sp_dw="" , sp_price="";
		int jh_index = -1;
		
		PreparedStatement pst;
		try 
		{
			String sql = "insert into t_jinhuo_detail(sp_name , sp_num , sp_dw" +
					", sp_price , sp_bz , sp_sf , jh_index )"
					+ " values(? , ? , ? , ? , ? , ? , ?)";
			pst = con().prepareStatement(sql);
			for(int i=0; i<list.size(); i++)
			{
				Vector v1 = (Vector) list.get(i);
				pst.setString(1, v1.get(1).toString());
				pst.setString(2, v1.get(2).toString());
				pst.setString(3, v1.get(3).toString());
				pst.setString(4, v1.get(4).toString());
				pst.setString(5, v1.get(5).toString());
				pst.setDouble(6, Double.parseDouble(v1.get(7).toString().replace(",", "")));
				pst.setString(7, id_index);
				pst.executeUpdate();
				System.out.println(v1);
				//insert_jh1(v1);				
			}
			
			pst.close();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
		
	//更新详细数据
	public static void update_jj_detail_1(List list , String id_index)
	{
		String sp_name="" , sp_num="" , sp_dw="" , sp_price="";
		int jh_index = -1;
		
		PreparedStatement pst;
		try 
		{
			String sql = "update t_jinhuo_detail set sp_name=? , sp_num=? , sp_dw=?" +
					", sp_price=? , sp_bz=? , sp_sf=? where jh_index = "+id_index;
			pst = con().prepareStatement(sql);
			for(int i=0; i<list.size(); i++)
			{
				Vector v1 = (Vector) list.get(i);
				pst.setString(1, v1.get(1).toString());
				pst.setString(2, v1.get(2).toString());
				pst.setString(3, v1.get(3).toString());
				pst.setString(4, v1.get(4).toString());
				pst.setString(5, v1.get(5).toString());
				pst.setDouble(6, Double.parseDouble(v1.get(7).toString().replace(",", "")));
				pst.executeUpdate();
				//insert_jh1(v1);				
			}
			
			pst.close();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//进货单查询,指定查询
	public static List query_jj_main_equals(String sql)
	{
		List list = new ArrayList();
		Statement stmt = null;
		try {
			stmt = con().createStatement();
			ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Vector v = new Vector();
		    	
		    	v.add(res.getInt("Id"));
		    	v.add(res.getString("jh_name"));
		    	v.add(res.getString("jh_phone"));
		    	v.add(res.getString("jh_time"));
		    	
		    	list.add(v);
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
		return list;
	}
	
	//进货单查询,指定查询
	public static List query_jj_main_contains(String sql)
	{
		List list = new ArrayList();
		Statement stmt = null;
		try {
			stmt = con().createStatement();
			ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Vector v = new Vector();
		    	
		    	v.add(res.getInt("Id"));
		    	v.add(res.getString("jh_name"));
		    	v.add(res.getString("jh_phone"));
		    	v.add(res.getString("jh_time"));
		    	
		    	list.add(v);
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
		return list;
	}	
		
		//插入订单基本信息
		public static boolean insert_dd_main(Map info)
		{
			String khmc = info.get("客户名称").toString();
			String dianhua = info.get("电话").toString();
			String jsr = info.get("经手人").toString();
			String isfinish = info.get("完成").toString();
			
			PreparedStatement pst;
			try 
			{
				String sql = "insert into t_dingdan_main(dd_name , dd_phone , dd_jsr , dd_isfinish , create_time)"
						+ " values(? , ? , ? , ? , ?)";
				pst = con().prepareStatement(sql);
				pst.setString(1, khmc);
				pst.setString(2, dianhua);
				pst.setString(3, jsr);
				pst.setString(4, isfinish);
				pst.setString(5, Calculat.getTime());
				pst.executeUpdate();
				pst.close();
				return true;
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		
		//获取订单Id
		public static int get_dd_main_Id(Map info) throws SQLException
		{
			int Id = -1;
			String khmc = info.get("客户名称").toString();
			String dianhua = info.get("电话").toString();
			String jsr = info.get("经手人").toString();
			
			Statement stmt;
			stmt = con().createStatement();
			String sql = "select * from t_dingdan_main where dd_name = \""+khmc+"\""
					+" and dd_phone = \""+dianhua+"\" and dd_jsr = \""+jsr+"\"";
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Id = res.getInt("Id");
		    }
			return Id;
		}
		
		//根据进货ID，插入对应的每一项
		public static boolean insert_dd_detail(Vector v , int id_index) throws ClassNotFoundException, SQLException
		{
			int jh_index = -1;
			
			PreparedStatement pst;
			try 
			{
				String sql = "insert into t_dingdan_detail(sp_name , sp_num , sp_dw" +
						", sp_price  , sp_bz , sp_sf , sp_gr , sp_azf , dd_index )"
						+ " values(? , ? , ? , ? , ? , ? , ? , ? , ?)";
				pst = con().prepareStatement(sql);
				for(int i=0; i<v.size(); i++)
				{
					List v1 = (List) v.get(i);
					pst.setString(1, v1.get(0).toString());
					pst.setString(2, v1.get(1).toString());
					pst.setString(3, v1.get(2).toString());
					pst.setString(4, v1.get(3).toString());
					pst.setString(5, v1.get(4).toString());
					pst.setDouble(6
							, Double.parseDouble(v1.get(6).toString().replace(",", "")));
					pst.setString(7, v1.get(8).toString());
					pst.setDouble(8
							, Double.parseDouble(v1.get(9).toString().replace(",", "")));
					pst.setInt(9, id_index);
					pst.executeUpdate();
					
				}
				
				pst.close();				
				
			    return true;
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;		
		}
		
		//移除所有选中的订单信息
		public static boolean RemoveAllSelectDD(int Id)
		{
			//连接数据库
			Connection con = null;
			try 
			{
				con = DB_Connection.getInstance();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			PreparedStatement pst;
			try 
			{
				int result = 0;
				String sql = "delete from t_dingdan_main where Id = "+Id;
				pst = con.prepareStatement(sql);
				result = pst.executeUpdate();
				if(result < 0)
					return false;
				
				sql = "delete from t_dingdan_detail where dd_index = "+Id;
				pst = con.prepareStatement(sql);
				pst.executeUpdate();
				if(result < 0)
					return false;
				
				pst.close();
				return true;
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}	
		
		//获取订单列表
		public static List get_dd_main()
		{
			int Id = -1;
			List list = new ArrayList();
			
			Statement stmt;
			try {
				stmt = con().createStatement();
				String sql = "select * from t_dingdan_main order by create_time desc";
			    ResultSet res=	stmt.executeQuery(sql);	    
			    while(res.next())
			    {
			    	Vector v = new Vector();
			    	
			    	v.add(res.getInt("Id"));
			    	v.add(res.getString("dd_name"));
			    	v.add(res.getString("dd_phone"));
			    	v.add(res.getString("dd_jsr"));
			    	v.add(res.getString("dd_isfinish"));
			    	v.add(res.getDate("create_time"));

			    	list.add(v);
			    }		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}
		
		//查询某一条订单
		public static Map get_item_dd_main(int id)
		{
			Map map = new HashMap();		
			
			Statement stmt;
			try {
				stmt = con().createStatement();
				String sql = "select * from t_dingdan_main where Id = "+id;
			    ResultSet res=	stmt.executeQuery(sql);	    
			    while(res.next())
			    {
			    	map.put("Id", res.getInt("Id"));
			    	map.put("客户名称", res.getString("dd_name"));
			    	map.put("电话", res.getString("dd_phone"));
			    	map.put("经手人", res.getString("dd_jsr"));
			    	map.put("完成", res.getString("dd_isfinish"));
			    }		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
							
			return map;
		}
		
		//查询某一条进货单
		public static List get_item_dd_detail(int id)
		{
			List list = new ArrayList();		
			
			Statement stmt;
			try {
				stmt = con().createStatement();
				String sql = "select * from t_dingdan_detail where dd_index = "+id;
			    ResultSet res=	stmt.executeQuery(sql);	    
			    while(res.next())
			    {
			    	Vector v = new Vector();
			    	
			    	v.add(res.getInt("Id"));
			    	v.add(res.getString("sp_name"));
			    	v.add(res.getString("sp_num"));
			    	v.add(res.getString("sp_dw"));
			    	v.add(res.getString("sp_price"));
			    	v.add(res.getString("sp_bz"));
			    	v.add(Calculat.Result(Double.parseDouble(res.getString("sp_num"))
			    			*Double.parseDouble(res.getString("sp_price"))));
			    	v.add(Calculat.Result(res.getDouble("sp_sf")));
			    	v.add( Calculat.Result(Double.parseDouble(res.getString("sp_num"))
			    			*Double.parseDouble(res.getString("sp_price"))-res.getDouble("sp_sf")) );
			    	v.add(" ");
			    	v.add(res.getString("sp_gr"));
			    	v.add(Calculat.Result(res.getDouble("sp_azf")));
			    	list.add(v);
			    }		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			return list;
		}
		
		//更新基础信息
		public static void update_base_info1(Map map)
		{
			String Id = map.get("Id").toString();
			String khmc = map.get("客户名称").toString();
			String dianhua = map.get("电话").toString();
			String jsr = map.get("经手人").toString();
			String isfinish = map.get("完成").toString();
			PreparedStatement pst;
			
			try 
			{
				int result = 0;
				
				String sql = "update t_dingdan_main set " +
						"dd_name = ? , dd_phone = ? , dd_jsr = ? , dd_isfinish = ? where Id = "+Id;
				pst = con().prepareStatement(sql);
				
				pst.setString(1, khmc);
				pst.setString(2, dianhua);
				pst.setString(3, jsr);
				pst.setString(4, isfinish);
				
				pst.executeUpdate();
				
				
				pst.close();
				
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		//更新详细数据
		public static void update_dd_detail(List list , String id_index)
		{
			String sp_name="" , sp_num="" , sp_dw="" , sp_price="" , sp_bz="";
			int jh_index = -1;
			
			PreparedStatement pst;
			try 
			{
				String sql = "insert into t_dingdan_detail(sp_name , sp_num , sp_dw" +
						", sp_price  , sp_bz , sp_sf , sp_gr , sp_azf , dd_index )"
						+ " values(? , ? , ? , ? , ? , ? , ? , ? , ?)";
				pst = con().prepareStatement(sql);
				for(int i=0; i<list.size(); i++)
				{
					List v1 = (List) list.get(i);
					pst.setString(1, v1.get(1).toString());
					pst.setString(2, v1.get(2).toString());
					pst.setString(3, v1.get(3).toString());
					pst.setString(4, v1.get(4).toString());
					pst.setString(5, v1.get(5).toString());
					pst.setDouble(6
							, Double.parseDouble(v1.get(6).toString().replace(",", "")));
					pst.setString(7, v1.get(10).toString());
					pst.setDouble(8
							, Double.parseDouble(v1.get(11).toString().replace(",", "")));
					pst.setInt(9, Integer.parseInt(id_index));
					pst.executeUpdate();
					
				}
				pst.close();
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
				
		//更新详细数据
		public static void update_dd_detail_1(List list , String id_index)
		{
			String sp_name="" , sp_num="" , sp_dw="" , sp_price="" , sp_bz="";
			int jh_index = -1;
			
			PreparedStatement pst;
			try 
			{
				String sql = "update t_dingdan_detail set sp_name=? , sp_num=? , sp_dw=?" +
						", sp_price=?  , sp_bz=? , sp_sf=? , sp_gr=? , sp_azf=? where dd_index="+id_index;
				pst = con().prepareStatement(sql);
				for(int i=0; i<list.size(); i++)
				{
					List v1 = (List) list.get(i);
					pst.setString(1, v1.get(1).toString());
					pst.setString(2, v1.get(2).toString());
					pst.setString(3, v1.get(3).toString());
					pst.setString(4, v1.get(4).toString());
					pst.setString(5, v1.get(5).toString());
					pst.setDouble(6
							, Double.parseDouble(v1.get(6).toString().replace(",", "")));
					pst.setString(7, v1.get(10).toString());
					pst.setDouble(8
							, Double.parseDouble(v1.get(11).toString().replace(",", "")));
					pst.executeUpdate();
					
				}
				pst.close();
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//订单查询,指定查询
		public static List query_dd_main_equals(String sql)
		{
			List list = new ArrayList();
			Statement stmt = null;
			try {
				stmt = con().createStatement();
				ResultSet res=	stmt.executeQuery(sql);	    
			    while(res.next())
			    {
			    	Vector v = new Vector();
			    	
			    	v.add(res.getInt("Id"));
			    	v.add(res.getString("dd_name"));
			    	v.add(res.getString("dd_phone"));
			    	v.add(res.getString("dd_jsr"));
			    	v.add(res.getString("dd_isfinish"));
			    	
			    	list.add(v);
			    }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	    
			return list;
		}
		
		//订单查询,指定查询
		public static List query_dd_main_contains(String sql)
		{
			List list = new ArrayList();
			Statement stmt = null;
			try {
				stmt = con().createStatement();
				ResultSet res=	stmt.executeQuery(sql);	    
			    while(res.next())
			    {
			    	Vector v = new Vector();
			    	
			    	v.add(res.getInt("Id"));
			    	v.add(res.getString("dd_name"));
			    	v.add(res.getString("dd_phone"));
			    	v.add(res.getString("dd_jsr"));
			    	v.add(res.getString("dd_isfinish"));
			    	
			    	list.add(v);
			    }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	    
			return list;
		}
		
	//添加新商品
	public static boolean AddNewGoods(Map info)
	{
		String spmc = info.get("商品名称").toString();
		String sl = info.get("数量").toString();
		String dw = info.get("单位").toString();
		String dj = info.get("单价").toString();
		String gg = info.get("备注").toString();
		
		PreparedStatement pst;
		try 
		{
			String sql = "insert into t_sp(sp_name , sp_num , sp_dw" +
					", sp_price , sp_bz )"
					+ " values(? , ? , ? , ? , ?)";
			pst = con().prepareStatement(sql);
			pst.setString(1, spmc);
			pst.setString(2, sl);
			pst.setString(3, dw);
			pst.setString(4, dj);
			pst.setString(5 , gg);
			pst.executeUpdate();
			pst.close();
			return true;
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//查询商品信息
	public static List query_all_kc_item()
	{
		List list = new ArrayList();
		Statement stmt;
		try 
		{
			stmt = con().createStatement();
			String sql = "select * from t_sp";
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Item item = new Item();
		    	item.setId(res.getInt("Id")+"");
		    	item.setSpmc(res.getString("sp_name"));
		    	item.setSl(res.getString("sp_num"));
		    	item.setDw(res.getString("sp_dw"));
		    	item.setDj(res.getString("sp_price"));
		    	item.setBz(res.getString("sp_bz"));

		    	list.add(item);
		    }		
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return list;
	}
	
	//更新商品信息
	public static boolean Update_sp(Map info)
	{
		String Id = info.get("Id").toString();
		String spmc = info.get("商品名称").toString();
		int sl = Integer.parseInt(info.get("数量").toString());
		String dw = info.get("单位").toString();
		String dj = info.get("单价").toString();
		String gg = info.get("备注").toString();
		
		Statement stmt = null;
		String sql = "select * from t_sp where Id = "+Id;
	    ResultSet res;
		try 
		{
			stmt = con().createStatement();
			res = stmt.executeQuery(sql);
			while(res.next())
		    {
		    	sl += Integer.parseInt(res.getString("sp_num"));
		    }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	    
	    	
		
		PreparedStatement pst;
		int result = 0;
		
		sql = "update t_sp set " +
				"sp_name = ? , sp_num = ? , sp_dw = ?" +
				", sp_price = ? , sp_bz = ? where Id = "+Id;
		try 
		{
			pst = con().prepareStatement(sql);
			pst.setString(1, spmc);
			pst.setInt(2, sl);
			pst.setString(3, dw);
			pst.setString(4, dj);
			pst.setString(5, gg);
			
			pst.executeUpdate();
	
			pst.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
	}
	
	//删除商品
	public static boolean del_sp(Map info)
	{
		String Id = info.get("Id").toString();
		String spmc = info.get("商品名称").toString();
		String sl = info.get("数量").toString();
		String dw = info.get("单位").toString();
		String dj = info.get("单价").toString();
		String gg = info.get("备注").toString();
		
		PreparedStatement pst;
		int result = 0;
		
		String sql = "delete from t_sp where Id = "+Id;
		try 
		{
			pst = con().prepareStatement(sql);
			
			pst.executeUpdate();
	
			pst.close();
			return true;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
	}
	
	//库存查询
	public static List query_spkc()
	{
		List list = new ArrayList();
		
		Statement stmt;
		try 
		{
			stmt = con().createStatement();
			String sql = "select * from t_sp";
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Vector v = new Vector();
		    	
		    	v.add(res.getInt("Id"));
		    	v.add(res.getString("sp_name"));
		    	v.add(res.getString("sp_num"));
		    	v.add(res.getString("sp_dw"));
		    	v.add(res.getString("sp_price"));
		    	v.add(Calculat.Result(Double.parseDouble(res.getString("sp_num"))
		    			*Double.parseDouble(res.getString("sp_price"))));
		    	v.add(res.getString("sp_bz"));

		    	list.add(v);
		    }		
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//查询某个商品
	public static Map get_sp_item(String Id)
	{
		Map map = new HashMap();
		Statement stmt;
		try 
		{
			stmt = con().createStatement();
			String sql = "select * from t_sp where Id = "+Id;
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	map.put("Id", res.getInt("Id")+"");
		    	map.put("商品名称", res.getString("sp_name"));
		    	map.put("数量", res.getString("sp_num"));
		    	map.put("单位", res.getString("sp_dw"));
		    	map.put("单价", res.getString("sp_price"));
		    	map.put("备注", res.getString("sp_bz"));
		    }		
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	//更新商品
	public static boolean update_sp_item(Map info)
	{
		String Id = info.get("Id").toString();
		String spmc = info.get("商品名称").toString();
		String sl = info.get("数量").toString();
		String dw = info.get("单位").toString();
		String dj = info.get("单价").toString();
		String gg = info.get("备注").toString();
		
		PreparedStatement pst;
		int result = 0;
		
		String sql = "update t_sp set " +
				"sp_name = ? , sp_num = ? , sp_dw = ?" +
				", sp_price = ? , sp_bz = ? where Id = "+Id;
		try 
		{
			pst = con().prepareStatement(sql);
			pst.setString(1, spmc);
			pst.setString(2, sl);
			pst.setString(3, dw);
			pst.setString(4, dj);
			pst.setString(5, gg);
			
			pst.executeUpdate();
	
			pst.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
	}
	
	//商品查询,指定查询
	public static List query_sp_equals(String sql)
	{
List list = new ArrayList();
		
		Statement stmt;
		try 
		{
			stmt = con().createStatement();
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Vector v = new Vector();
		    	
		    	v.add(res.getInt("Id"));
		    	v.add(res.getString("sp_name"));
		    	v.add(res.getString("sp_num"));
		    	v.add(res.getString("sp_dw"));
		    	v.add(res.getString("sp_price"));
		    	v.add(Calculat.Result(Double.parseDouble(res.getString("sp_num"))
		    			*Double.parseDouble(res.getString("sp_price"))));
		    	v.add(res.getString("sp_bz"));

		    	list.add(v);
		    }		
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//进货单查询,指定查询
	public static List query_sp_contains(String sql)
	{
List list = new ArrayList();
		
		Statement stmt;
		try 
		{
			stmt = con().createStatement();
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Vector v = new Vector();
		    	
		    	v.add(res.getInt("Id"));
		    	v.add(res.getString("sp_name"));
		    	v.add(res.getString("sp_num"));
		    	v.add(res.getString("sp_dw"));
		    	v.add(res.getString("sp_price"));
		    	v.add(Calculat.Result(Double.parseDouble(res.getString("sp_num"))
		    			*Double.parseDouble(res.getString("sp_price"))));
		    	v.add(res.getString("sp_bz"));

		    	list.add(v);
		    }		
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}	
	
	//添加新职务
	public static boolean AddNewZW(Map info)
	{
		String zwmc = info.get("职务名称").toString();
		String bz = info.get("备注").toString();
		
		PreparedStatement pst;
		try 
		{
			String sql = "insert into t_zw( zw_mc , zw_bz )"
					+ " values(? , ?)";
			pst = con().prepareStatement(sql);
			pst.setString(1, zwmc);
			pst.setString(2, bz);
			pst.executeUpdate();
			pst.close();
			return true;
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}	
	
	//更新职务
	public static boolean Update_zw(Map info)
	{
		String Id = info.get("Id").toString();
		String zwmc = info.get("职务名称").toString();
		String bz = info.get("备注").toString();    	
		
		PreparedStatement pst;
		int result = 0;
		
		String sql = "update t_zw set " +
				"zw_mc = ? , zw_bz = ? where Id = "+Id;
		try 
		{
			pst = con().prepareStatement(sql);
			pst.setString(1, zwmc);
			pst.setString(2, bz);
			
			pst.executeUpdate();
	
			pst.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
	}	
	
	//查询职务信息
	public static List query_all_zw()
	{
		List list = new ArrayList();
		Statement stmt;
		try 
		{
			stmt = con().createStatement();
			String sql = "select * from t_zw";
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Item item = new Item();
		    	item.setId(res.getInt("Id")+"");
		    	item.setSpmc(res.getString("zw_mc"));
		    	item.setBz(res.getString("zw_bz"));

		    	list.add(item);
		    }		
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return list;
	}	
	
	//删除商品
	public static boolean del_zw(Map info)
	{
		String Id = info.get("Id").toString();
		String zwmc = info.get("职务名称").toString();
		String bz = info.get("备注").toString();  
		
		PreparedStatement pst;
		int result = 0;
		
		String sql = "delete from t_zw where Id = "+Id;
		try 
		{
			pst = con().prepareStatement(sql);
			
			pst.executeUpdate();
	
			pst.close();
			return true;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
	}	
	
	//添加新人员
	public static boolean AddNewYG(Map info)
	{
		String xm = info.get("姓名").toString();
		String mm = info.get("密码").toString();
		String dh = info.get("电话").toString();
		String zw = info.get("职务").toString();
		String zz = info.get("住址").toString();
		String bz = info.get("备注").toString();
		
		PreparedStatement pst;
		try 
		{
			String sql = "insert into t_yg( yg_xm , yg_pw , yg_dh , yg_zz , yg_zw , yg_bz )"
					+ " values(? , ? , ? , ? , ? ,?)";
			pst = con().prepareStatement(sql);
			pst.setString(1, xm);
			pst.setString(2, mm);
			pst.setString(3, dh);
			pst.setString(4, zz);
			pst.setString(5, zw);
			pst.setString(6, bz);
			pst.executeUpdate();
			pst.close();
			return true;
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}	
	
		
	//查询员工信息
	public static List query_all_yg()
	{
		List list = new ArrayList();
		Statement stmt;
		try 
		{
			stmt = con().createStatement();
			String sql = "select * from t_yg";
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Person person = new Person();
		    	person.setId(res.getInt("Id")+"");
		    	person.setXm(res.getString("yg_xm"));
		    	person.setDh(res.getString("yg_dh"));
		    	person.setZz(res.getString("yg_zz"));
		    	person.setZw(res.getString("yg_zw"));
		    	person.setBz(res.getString("yg_bz"));
		    	person.setPw(res.getString("yg_pw"));

		    	list.add(person);
		    }		
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return list;
	}	
	
	//查询员工信息
		public static Person query_all_yg_by_Id(String Id)
		{
			Person person = null;
			Statement stmt;
			try 
			{
				stmt = con().createStatement();
				String sql = "select * from t_yg where Id = "+Id;
			    ResultSet res=	stmt.executeQuery(sql);				    
			    while(res.next())
			    {
			    	person = new Person();
			    	person.setId(res.getInt("Id")+"");
			    	person.setXm(res.getString("yg_xm"));
			    	person.setPw(res.getString("yg_pw"));
			    	person.setDh(res.getString("yg_dh"));
			    	person.setZz(res.getString("yg_zz"));
			    	person.setZw(res.getString("yg_zw"));
			    	person.setBz(res.getString("yg_bz"));

			    }		
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			return person;
		}	
		
	//更新人员
	public static boolean Update_yg(Map info)
	{
		String Id = info.get("Id").toString();
		String xm = info.get("姓名").toString();
		String mm = info.get("密码").toString();
		String dh = info.get("电话").toString();
		String zw = info.get("职务").toString();
		String zz = info.get("住址").toString();
		String bz = info.get("备注").toString();   	
		
		PreparedStatement pst;
		int result = 0;
		
		String sql = "update t_yg set " +
				"yg_xm = ? , yg_pw = ? , yg_dh = ? , yg_zz = ? , yg_zw = ? , yg_bz = ? where Id = "+Id;
		try 
		{
			pst = con().prepareStatement(sql);
			pst.setString(1, xm);
			pst.setString(2, mm);
			pst.setString(3, dh);
			pst.setString(4, zz);
			pst.setString(5, zw);
			pst.setString(6, bz);
			
			pst.executeUpdate();
	
			pst.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
	}		
	
	//删除商品
	public static boolean del_yg(Map info)
	{
		String Id = info.get("Id").toString();
		String xm = info.get("姓名").toString();
		String bz = info.get("备注").toString();  
		
		PreparedStatement pst;
		int result = 0;
		
		String sql = "delete from t_yg where Id = "+Id;
		try 
		{
			pst = con().prepareStatement(sql);
			
			pst.executeUpdate();
			RemoveAllSelectYG(Integer.parseInt(Id));
			pst.close();
			return true;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
	}		
	
	//获取员工列表
	public static List get_yg_main()
	{
		//连接数据库
		Connection con = null;
		
		List<Vector> list = new ArrayList();
		try 
		{
			con = DB_Connection.getInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int Id = -1;
		
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "select * from t_yg";
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Vector v = new Vector();
		    	
		    	v.add(res.getInt("Id"));
		    	v.add(res.getString("yg_xm"));
		    	v.add(res.getString("yg_dh"));
		    	v.add(res.getString("yg_zz"));
		    	v.add(res.getString("yg_zw"));
		    	v.add(res.getString("yg_bz"));

		    	list.add(v);
		    }		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}	
	
	//查询员工信息
	public static List query_item_yg(String Id)
	{
		List list = new ArrayList();
		Statement stmt;
		try 
		{
			stmt = con().createStatement();
			String sql = "select * from t_yg where Id = "+Id;
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Person person = new Person();
		    	person.setId(res.getInt("Id")+"");
		    	person.setXm(res.getString("yg_xm"));
		    	person.setDh(res.getString("yg_dh"));
		    	person.setZz(res.getString("yg_zz"));
		    	person.setZw(res.getString("yg_zw"));
		    	person.setBz(res.getString("yg_bz"));

		    	list.add(person);
		    }		
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return list;
	}	
	
	//插入月份记录
	public static boolean insert_yx(String Id)
	{
		boolean f = false;
		Statement stmt;
		try 
		{
			stmt = con().createStatement();
			String sql = "select * from t_xs_y where yg_id = "+Id;
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	f = true;
		    }		
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(! f)
		{
			PreparedStatement pst;
			try 
			{
				String sql = "insert into t_xs_y( time , yg_id )"
						+ " values(? , ?)";
				pst = con().prepareStatement(sql);
				pst.setString(1, Calculat.getTime().substring(0, 7));
				pst.setInt(2, Integer.parseInt(Id));
				pst.executeUpdate();
				pst.close();
				
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	//插入月份记录
	public static boolean insert_yx(String Id , String date)
	{
		boolean f = false;
		Statement stmt;
		try 
		{
			stmt = con().createStatement();
			String sql = "select * from t_xs_y where yg_id = "+Id+" and time = '"+date+"'";
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	f = true;
		    }		
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(! f)
		{
			PreparedStatement pst;
			try 
			{
				String sql = "insert into t_xs_y( time , yg_id )"
						+ " values(? , ?)";
				pst = con().prepareStatement(sql);
				pst.setString(1, date);
				pst.setInt(2, Integer.parseInt(Id));
				pst.executeUpdate();
				pst.close();
				
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}	
	
	//插入月份记录
	public static boolean insert_yx_all()
	{
		boolean f = false;
		int Id=-1;
		Statement stmt;
		Statement stmt1;
		PreparedStatement pst = null;
		try 
		{
			stmt = con().createStatement();
			String sql = "select * from t_yg";
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Id = res.getInt("Id");
		    	
		    	
				try 
				{
					stmt1 = con().createStatement();
					String sql1 = "select * from t_xs_y where yg_id = "+Id;
				    ResultSet res1=	stmt1.executeQuery(sql1);	    
				    while(res1.next())
				    {
				    	f = true;
				    }		
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(! f)
				{
					
					try 
					{
						String sql2 = "insert into t_xs_y( time , yg_id )"
								+ " values(? , ?)";
						pst = con().prepareStatement(sql2);
						pst.setString(1, Calculat.getTime().substring(0, 7));
						pst.setInt(2,Id);
						pst.executeUpdate();						
						
					} 
					catch (Exception e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		    }
		    if(pst != null)
		    {
		    	pst.close();
		    }
		    
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}	
	
	//获取月薪列表
	public static List get_yx_main(String yg_Id)
	{
		
		List<Vector> list = new ArrayList();
		int Id = -1;
		
		Statement stmt;
		try {
			stmt = con().createStatement();
			String sql = "select * from t_xs_y where yg_Id = "+yg_Id+" order by time desc";
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Vector v = new Vector();
		    	
		    	Id = res.getInt("Id");
		    	v.add(res.getInt("Id"));
		    	v.add(res.getString("time"));
		    	System.out.println("------");
		    	
		    	Statement stmt1;
		    	double sum=0 , sum1=0;
				try 
				{
					stmt1 = con().createStatement();
					String sql1 = "select * from t_xs_d where xs_Id = "+Id;
					System.out.println(Id);
				    ResultSet res1=	stmt1.executeQuery(sql1);	    
				    while(res1.next())
				    {
				    	if(! res1.getString("jg_yt").equals("预支"))
				    	{
				    		sum += res1.getDouble("jg_je");	
				    	}
				    	else
				    	{
				    		sum1 += res1.getDouble("jg_je");
				    	}
				    }	
				    if(sum1 == 0)
				    {
				    	v.add(Calculat.Result(sum));
				    	v.add(Calculat.Result(sum));
				    	v.add("---");
				    }
				    else
				    {
				    	v.add(Calculat.Result(sum));
				    	v.add(Calculat.Result(sum-sum1));
				    	v.add(Calculat.Result(sum1));
				    }
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		    	list.add(v);
		    }		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}	
	
	//查询日薪表
	public static List get_item_xs_detail(String id)
	{
		List list = new ArrayList();
		
		Statement stmt;
		try {
			stmt = con().createStatement();
			String sql = "select * from t_xs_d where xs_Id = "+id;
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Vector v = new Vector();
		    	if(! res.getString("jg_yt").equals("预支"))
		    	{
		    		v.add(res.getString("Id"));
			    	v.add(res.getString("jg_wp"));
			    	v.add(res.getString("jg_sl"));
			    	v.add(res.getString("jg_dj"));
			    	v.add(res.getString("jg_bz"));
			    	v.add( Double.parseDouble(res.getString("jg_sl"))
			    			*Double.parseDouble(res.getString("jg_dj")) );
			    	v.add(res.getString("jg_yt"));
			    	v.add(res.getString("time"));
		    	}
		    	else
		    	{
		    		v.add(res.getString("Id"));
			    	v.add(res.getString("jg_wp"));
			    	v.add(res.getString("jg_sl"));
			    	v.add(res.getString("jg_dj"));
			    	v.add(res.getString("jg_bz"));
			    	v.add( res.getString("jg_je") );
			    	v.add(res.getString("jg_yt"));
			    	v.add(res.getString("time"));
		    	}
		    	
		    	
		    	list.add(v);
		    }		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return list;
	}	
	
	//查询日薪表
	public static List get_item_xs_detail1(String yg_Id , String time)
	{
		List list = new ArrayList();
		String id = "-1";
		
		Statement stmt;
		try 
		{
			stmt = con().createStatement();
			String sql = "select * from t_xs_y where yg_id = "+yg_Id+" and time ='"+time+"'";
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	id=res.getString("Id");
		    }		
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			stmt = con().createStatement();
			String sql = "select * from t_xs_d where xs_Id = "+id;
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Vector v = new Vector();
		    	if(! res.getString("jg_yt").equals("预支"))
		    	{
		    		v.add(res.getString("Id"));
			    	v.add(res.getString("jg_wp"));
			    	v.add(res.getString("jg_sl"));
			    	v.add(res.getString("jg_dj"));
			    	v.add(res.getString("jg_bz"));
			    	v.add( Double.parseDouble(res.getString("jg_sl"))
			    			*Double.parseDouble(res.getString("jg_dj")) );
			    	v.add(res.getString("jg_yt"));
			    	v.add(res.getString("time"));
		    	}
		    	else
		    	{
		    		v.add(res.getString("Id"));
			    	v.add(res.getString("jg_wp"));
			    	v.add(res.getString("jg_sl"));
			    	v.add(res.getString("jg_dj"));
			    	v.add(res.getString("jg_bz"));
			    	v.add( res.getString("jg_je") );
			    	v.add(res.getString("jg_yt"));
			    	v.add(res.getString("time"));
		    	}
		    	
		    	
		    	list.add(v);
		    }		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return list;
	}		
	
	//更新日薪详细数据
	public static void update_xs_detail(List list , String id_index)
	{
		int jh_index = -1;
		
		PreparedStatement pst;
		try 
		{
			String sql = "insert into t_xs_d(jg_wp , jg_sl , jg_dj" +
					", jg_bz , jg_yt , jg_je , time , xs_Id )"
					+ " values(? , ? , ? , ? , ? , ? , ? , ?)";
			pst = con().prepareStatement(sql);
			for(int i=0; i<list.size(); i++)
			{
				Vector v1 = (Vector) list.get(i);
				pst.setString(1, v1.get(1).toString());
				pst.setString(2, v1.get(2).toString());
				pst.setString(3, v1.get(3).toString());
				pst.setString(4, v1.get(4).toString());
				pst.setString(5, v1.get(6).toString());
				pst.setDouble(6, Double.parseDouble(v1.get(5).toString()));
				pst.setString(7, Calculat.getTime());
				pst.setString(8, id_index);
				pst.executeUpdate();	
			}
			
			pst.close();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	//更新日薪详细数据
	public static void update_xs_detail_1(List list , String id_index)
	{
		int jh_index = -1;
		
		PreparedStatement pst;
		try 
		{
			String sql = "update t_xs_d set jg_wp=? , jg_sl=? , jg_dj=?" +
					", jg_bz=? , jg_yt=? , jg_je=? , time=?  where Id = "+id_index;
			pst = con().prepareStatement(sql);
			for(int i=0; i<list.size(); i++)
			{
				Vector v1 = (Vector) list.get(i);
				pst.setString(1, v1.get(1).toString());
				pst.setString(2, v1.get(2).toString());
				pst.setString(3, v1.get(3).toString());
				pst.setString(4, v1.get(4).toString());
				pst.setString(5, v1.get(6).toString());
				pst.setDouble(6, Double.parseDouble(v1.get(5).toString()));
				pst.setString(7, Calculat.getTime());
				pst.executeUpdate();	
			}
			
			pst.close();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	//更新日薪详细数据
	public static void update_xs_detail(List list , String yg_Id , String time)
	{
		int jh_index = -1;
		String id = "-1";
		
		Statement stmt;
		try 
		{
			stmt = con().createStatement();
			String sql = "select * from t_xs_y where yg_id = "+yg_Id+" and time ='"+time+"'";
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	id=res.getString("Id");
		    }		
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PreparedStatement pst;
		try 
		{
			String sql = "insert into t_xs_d(jg_wp , jg_sl , jg_dj" +
					", jg_bz , jg_yt , jg_je , time , xs_Id )"
					+ " values(? , ? , ? , ? , ? , ? , ? , ?)";
			pst = con().prepareStatement(sql);
			for(int i=0; i<list.size(); i++)
			{
				Vector v1 = (Vector) list.get(i);
				pst.setString(1, v1.get(1).toString());
				pst.setString(2, v1.get(2).toString());
				pst.setString(3, v1.get(3).toString());
				pst.setString(4, v1.get(4).toString());
				pst.setString(5, v1.get(6).toString());
				pst.setDouble(6, Double.parseDouble(v1.get(5).toString()));
				pst.setString(7, Calculat.getTime());
				pst.setString(8, id);
				pst.executeUpdate();	
			}
			
			pst.close();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}		
	
	//更新日薪详细数据
	public static void update_xs_detail_1(List list , String yg_Id , String time)
	{
		int jh_index = -1;
		String id = "-1";
		
		Statement stmt;
		try 
		{
			stmt = con().createStatement();
			String sql = "select * from t_xs_y where yg_id = "+yg_Id+" and time ='"+time+"'";
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	id=res.getString("Id");
		    }		
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PreparedStatement pst;
		try 
		{
			String sql = "update t_xs_d set jg_wp=? , jg_sl=? , jg_dj=?" +
					", jg_bz=? , jg_yt=? , jg_je=? , time=? where xs_Id = "+id;
			pst = con().prepareStatement(sql);
			for(int i=0; i<list.size(); i++)
			{
				Vector v1 = (Vector) list.get(i);
				pst.setString(1, v1.get(1).toString());
				pst.setString(2, v1.get(2).toString());
				pst.setString(3, v1.get(3).toString());
				pst.setString(4, v1.get(4).toString());
				pst.setString(5, v1.get(6).toString());
				pst.setDouble(6, Double.parseDouble(v1.get(5).toString()));
				pst.setString(7, Calculat.getTime());
				pst.executeUpdate();	
			}
			
			pst.close();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}			
	
	//移除所有选中的订单信息
	public static boolean RemoveAllSelectXX(int Id)
	{
		
		PreparedStatement pst;
		try 
		{
			int result = 0;
			String sql = "delete from t_xs_y where Id = "+Id;
			pst = con().prepareStatement(sql);
			result = pst.executeUpdate();
			if(result < 0)
				return false;
			
			sql = "delete from t_xs_d where xs_Id = "+Id;
			pst = con().prepareStatement(sql);
			pst.executeUpdate();
			if(result < 0)
				return false;
			
			pst.close();
			return true;
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}		
	
	//月薪查询,指定查询
	public static List query_xs_main_equals(String sql)
	{
		List<Vector> list = new ArrayList();
		int Id = -1;
		
		Statement stmt;
		try {
			stmt = con().createStatement();
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Vector v = new Vector();
		    	
		    	Id = res.getInt("Id");
		    	v.add(res.getInt("Id"));
		    	v.add(res.getString("time"));
		    	
		    	Statement stmt1;
		    	double sum=0 , sum1=0;
				try 
				{
					stmt1 = con().createStatement();
					String sql1 = "select * from t_xs_d where xs_Id = "+Id;
					System.out.println(Id);
				    ResultSet res1=	stmt1.executeQuery(sql1);	    
				    while(res1.next())
				    {
				    	if(! res1.getString("jg_yt").equals("预支"))
				    	{
				    		sum += res1.getDouble("jg_je");	
				    	}
				    	else
				    	{
				    		sum1 += res1.getDouble("jg_je");
				    	}
				    }	
				    if(sum1 == 0)
				    {
				    	v.add(Calculat.Result(sum));
				    	v.add(Calculat.Result(sum));
				    	v.add("---");
				    }
				    else
				    {
				    	v.add(Calculat.Result(sum));
				    	v.add(Calculat.Result(sum-sum1));
				    	v.add(Calculat.Result(sum1));
				    }
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		    	list.add(v);
		    }		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}	
	
	//员工查询,指定查询
	public static List query_yg_main(String sql)
	{
		List list = new ArrayList();
		Statement stmt = null;
		try {
			stmt = con().createStatement();
			ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Vector v = new Vector();
		    	
		    	v.add(res.getInt("Id"));
		    	v.add(res.getString("yg_xm"));
		    	v.add(res.getString("yg_dh"));
		    	v.add(res.getString("yg_zz"));
		    	v.add(res.getString("yg_zw"));
		    	v.add(res.getString("yg_bz"));
		    	
		    	list.add(v);
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
		return list;
	}	
	
	//移除所有选中的员工信息
	public static boolean RemoveAllSelectYG(int Id)
	{
		
		PreparedStatement pst;
		PreparedStatement pst1 = null;
		try 
		{
			Statement stmt;
			try 
			{
				stmt = con().createStatement();
				String sql = "select * from t_xs_y where yg_id = "+Id;
			    ResultSet res=	stmt.executeQuery(sql);	    
			    while(res.next())
			    {
			    	int Id1 = res.getInt("Id");
			    	String sql1 = "delete from t_xs_d where xs_Id = "+Id1;
			    	pst1 = con().prepareStatement(sql1);
					pst1.executeUpdate();
			    }		
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			String sql2 = "delete from t_xs_y where yg_Id = "+Id;
			pst = con().prepareStatement(sql2);
			pst.executeUpdate();
			
			pst.close();
			pst1.close();
			return true;
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}		
	
	//获取月薪列表
	public static List get_yx_year()
	{
		
		List<Vector> list = new ArrayList();
		int Id = -1;
		
		Statement stmt;
		Statement stmt1;
		Statement stmt2 = null;
		try 
		{
			stmt = con().createStatement();
			String sql = "select * from t_xs_y group by time order by time desc";
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Vector v = new Vector();
		    	double sum=0 , sum1=0;
		    	
		    	//v.add(res.getInt("Id"));
		    	String time = res.getString("time");
		    	v.add(res.getInt("Id"));
		    	v.add(res.getString("time"));
		    	
		    	String sql2 ="select Id from t_xs_y where time ='"+time+"'";
		    	stmt2 = con().createStatement();
		    	ResultSet res2=	stmt2.executeQuery(sql2);	
		    	while(res2.next())
		    	{
		    		Id = res2.getInt("Id");
			    	
					try 
					{
						stmt1 = con().createStatement();
						String sql1 = "select * from t_xs_d where xs_Id = "+Id;
					    ResultSet res1=	stmt1.executeQuery(sql1);	    
					    while(res1.next())
					    {
					    	if(! res1.getString("jg_yt").equals("预支"))
					    	{
					    		sum += res1.getDouble("jg_je");	
					    	}
					    	else
					    	{
					    		sum1 += res1.getDouble("jg_je");
					    	}
					    }						    
					} 
					catch (SQLException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		    		
		    	}
	    	
		    	if(sum1 == 0)
			    {
			    	System.out.println(sum);
			    	v.add(Calculat.Result(sum));
			    	v.add(Calculat.Result(sum));
			    	v.add("---");
			    }
			    else
			    {
			    	v.add(Calculat.Result(sum));
			    	v.add(Calculat.Result(sum-sum1));
			    	v.add(Calculat.Result(sum1));
			    }
		    	
		    	list.add(v);
		    }		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}		
	
	//获取月薪列表
	public static List get_yx_year_equle(String sql)
	{
		
		List<Vector> list = new ArrayList();
		int Id = -1;
		
		Statement stmt;
		Statement stmt1;
		Statement stmt2 = null;
		try 
		{
			stmt = con().createStatement();
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Vector v = new Vector();
		    	
		    	String time = res.getString("time");
		    	v.add(res.getString("time"));
		    	
		    	String sql2 ="select Id from t_xs_y where time ='"+time+"'";
		    	stmt2 = con().createStatement();
		    	ResultSet res2=	stmt2.executeQuery(sql2);	
		    	while(res2.next())
		    	{
		    		Id = res2.getInt("Id");
			    	double sum=0 , sum1=0;
					try 
					{
						stmt1 = con().createStatement();
						String sql1 = "select * from t_xs_d where xs_Id = "+Id;
						System.out.println(Id);
					    ResultSet res1=	stmt1.executeQuery(sql1);	    
					    while(res1.next())
					    {
					    	if(! res1.getString("jg_yt").equals("预支"))
					    	{
					    		sum += res1.getDouble("jg_je");	
					    	}
					    	else
					    	{
					    		sum1 += res1.getDouble("jg_je");
					    	}
					    }	
					    if(sum1 == 0)
					    {
					    	v.add(Calculat.Result(sum));
					    	v.add(Calculat.Result(sum));
					    	v.add("---");
					    }
					    else
					    {
					    	v.add(Calculat.Result(sum));
					    	v.add(Calculat.Result(sum-sum1));
					    	v.add(Calculat.Result(sum1));
					    }
					} 
					catch (SQLException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		    		
		    	}
	    	

		    	list.add(v);
		    }		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}			
	
	//获取月薪记录年度报表
	public static List get_yx_main1(String time)
	{
		
		List<Vector> list = new ArrayList();
		int Id = -1 , yg_Id = -1;
		
		Statement stmt;
		try {
			stmt = con().createStatement();
			String sql = "select * from t_xs_y where time = '"+time+"'";
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	Vector v = new Vector();
		    	
		    	Id = res.getInt("Id");
		    	yg_Id = res.getInt("yg_id");
		    	v.add(res.getInt("Id"));
		    	
		    	
		    	Statement stmt2;
				try 
				{
					stmt = con().createStatement();
					String sql2 = "select * from t_yg where Id = "+yg_Id;
				    ResultSet res2=	stmt.executeQuery(sql2);	    
				    while(res2.next())
				    {
				    	v.add(res2.getString("yg_xm"));
				    }		
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				v.add(res.getString("time"));
		    	
		    	Statement stmt1;
		    	double sum=0 , sum1=0;
				try 
				{
					stmt1 = con().createStatement();
					String sql1 = "select * from t_xs_d where xs_Id = "+Id;
					System.out.println(Id);
				    ResultSet res1=	stmt1.executeQuery(sql1);	    
				    while(res1.next())
				    {
				    	if(! res1.getString("jg_yt").equals("预支"))
				    	{
				    		sum += res1.getDouble("jg_je");	
				    	}
				    	else
				    	{
				    		sum1 += res1.getDouble("jg_je");
				    	}
				    }	
				    if(sum1 == 0)
				    {
				    	v.add(Calculat.Result(sum));
				    	v.add(Calculat.Result(sum));
				    	v.add("---");
				    }
				    else
				    {
				    	v.add(Calculat.Result(sum));
				    	v.add(Calculat.Result(sum-sum1));
				    	v.add(Calculat.Result(sum1));
				    }
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		    	list.add(v);
		    }		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//移除所有选中的月薪信息
	public static boolean RemoveAllSelectYX(int Id)
	{

		PreparedStatement pst;
		PreparedStatement pst1 = null;
		try 
		{
			Statement stmt;
			try 
			{
				stmt = con().createStatement();
				String sql = "select * from t_xs_y where Id = "+Id;
				ResultSet res=	stmt.executeQuery(sql);	  				
				while(res.next())
				{	
					int Id1 = res.getInt("Id");
					String sql1 = "delete from t_xs_d where xs_Id = "+Id1;
					pst1 = con().prepareStatement(sql1);
					pst1.executeUpdate();
				}		
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			String sql2 = "delete from t_xs_y where Id = "+Id;
			pst = con().prepareStatement(sql2);
			pst.executeUpdate();

			pst.close();
			pst1.close();
			return true;
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}		
	
	//移除所有选中的员工薪水
	public static boolean RemoveAllSelectYGXS(int Id)
	{

		PreparedStatement pst;
		PreparedStatement pst1 = null;
		try 
		{
			Statement stmt;
			try 
			{
				stmt = con().createStatement();
				String sql = "select * from t_xs_y where yg_id = "+Id;
				ResultSet res=	stmt.executeQuery(sql);	    
				while(res.next())
				{
					int Id1 = res.getInt("Id");
					String sql1 = "delete from t_xs_d where xs_Id = "+Id1;
					pst1 = con().prepareStatement(sql1);
					pst1.executeUpdate();
				}		
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			String sql2 = "delete from t_xs_y where yg_Id = "+Id;
			pst = con().prepareStatement(sql2);
			pst.executeUpdate();

			pst.close();
			pst1.close();
			return true;
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}		
	
	//登陆用户信息查找
	public static TbUser login(String username , String password)
	{
		//连接数据库
		Connection con = null;
		
		try 
		{
			con = DB_Connection.getInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String xm=null , pw=null ,zw=null;
		
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "select * from t_yg where yg_dh = '"+username+"'";
		    ResultSet res=	stmt.executeQuery(sql);	    
		    while(res.next())
		    {
		    	xm = res.getString("yg_dh");
		    	pw = res.getString("yg_pw");
		    	zw = res.getString("yg_zw");		    	
		    }		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(xm == null)
		{
			TbUser.getInstance().setName("no_user");
			return TbUser.getInstance();
		}
		else
		{
			if(xm.equals(username))
			{
				if(pw.equals(password))
				{
					TbUser.getInstance().setName(xm);
					TbUser.getInstance().setPass(pw);
					TbUser.getInstance().setQuan(new Quan(zw).get_level());
					return TbUser.getInstance();
				}
				else
				{
					TbUser.getInstance().setPass("no_password");
					return TbUser.getInstance();
				}
			}
		}
		TbUser.getInstance().setPass("no_password");
		return TbUser.getInstance();
	}
}
