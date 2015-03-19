package sj.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Calculat 
{
	//·µ»ØÊ±¼ä
	public static String getTime()
	{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}
	
	public static String Result(double d)
	{
		String str = "";
		String mid = ",";
		int count = 0;
		double temp = d;
		int temp1 = (int) temp;
		String temp2 = "";
		if(temp - temp1 != 0)
		{
			int i = ((temp - temp1)+"").length();
			if( i >= 5 )
			{
				temp2 = " "+((temp - temp1)+"").substring(1 , 5);
			}
			else
			{
				temp2 = " "+((temp - temp1)+"").substring(1 , i);
			}
		}		
		
		for(count = 0; temp1>0; count++)
		{
			if(count % 3 == 0)
			{
				if(!str.equals(""))
				{
					str = mid+str;
				}
				else
				{
					str = str;
				}
				count = 0;				
			}
			
			str = temp1%10+""+str;
			temp1 = temp1/10;
		}
		
		count = 0;
		str = str+temp2;
		if(str.length() == 0)
		{
			return "0";
		}
		else
		{
			return str;
		}
	}
	
	public static double Result1(String str)
	{
		if(str.equals(" ")||str.equals("---"))
			return 0;
		str = str.replace(",", "");
		return Double.parseDouble(str);
	}
}
