package sj.util;

public class Quan 
{
	/*
	 * �ȼ���Ϊ4����
	 * -1��ϵͳ����Ա
	 * 0����ͨԱ��
	 * 1-10��ҵ�����Ա
	 * 11-20�����ʹ���Ա
	 */
	String zw;
	public Quan(String zw)
	{
		this.zw = zw;
	}
	
	public int get_level()
	{
		if("ϵͳ����Ա".equals(zw))
		{
			return -1;
		}
		
		if("��ͨԱ��".equals(zw))
		{
			return 0;
		}
		
		if("ҵ�����Ա".equals(zw))
		{
			return 1;
		}
		
		if("���ʹ���Ա".equals(zw))
		{
			return 11;
		}
		
		return 0;
	}
}
