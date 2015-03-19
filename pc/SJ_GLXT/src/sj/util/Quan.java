package sj.util;

public class Quan 
{
	/*
	 * 等级分为4个，
	 * -1：系统管理员
	 * 0：普通员工
	 * 1-10：业务管理员
	 * 11-20：工资管理员
	 */
	String zw;
	public Quan(String zw)
	{
		this.zw = zw;
	}
	
	public int get_level()
	{
		if("系统管理员".equals(zw))
		{
			return -1;
		}
		
		if("普通员工".equals(zw))
		{
			return 0;
		}
		
		if("业务管理员".equals(zw))
		{
			return 1;
		}
		
		if("工资管理员".equals(zw))
		{
			return 11;
		}
		
		return 0;
	}
}
