package sj.util;

import sj.main.GLXT_Frame;

public class TbUser {
	private String name;
	private String pass;
	private int quan;
	GLXT_Frame gf;
	
	private volatile static TbUser tbuser;
	private TbUser()
	{}
	public static TbUser getInstance()
	{
		if(tbuser == null)
		{
			synchronized(TbUser.class)
			{
				if(tbuser == null)
				{
					tbuser = new TbUser();
				}
			}
		}
		return tbuser;
	}
	
	public GLXT_Frame getGf() {
		return gf;
	}

	public void setGf(GLXT_Frame gf) {
		this.gf = gf;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getPass() {
		return this.pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getQuan() {
		return this.quan;
	}
	public void setQuan(int quan) {
		this.quan = quan;
	}
}