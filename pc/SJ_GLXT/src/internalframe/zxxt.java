package internalframe;

import javax.swing.JInternalFrame;

import dao.DB_Opreat;

import sj.login.Login;
import sj.panel.kucun.Spck;
import sj.util.TbUser;


/*
 * ��Ʒ�鿴�༭����
 */
public class zxxt extends JInternalFrame
{
	public zxxt()
	{
		this.setVisible(true);
		this.setTitle("ע��ϵͳ");
		this.setBounds(250 , 50 , 600 , 400);
		this.setIconifiable(true);
		this.setClosable(true);
		this.setMaximizable(false);

		this.pack();
		
		
		
//		TbUser.getInstance().getGf().getFrame().dispose();
		TbUser.getInstance().setGf(null);
		new Login();
		
		
		
	}
	
}
