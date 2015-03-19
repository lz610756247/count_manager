package internalframe;

import javax.swing.JInternalFrame;

import dao.DB_Opreat;

import sj.panel.kucun.Spck;


/*
 * 商品查看编辑界面
 */
public class spck extends JInternalFrame
{
	Spck sp;
	public spck()
	{
		this.setVisible(true);
		this.setTitle("商品查看");
		this.setBounds(250 , 50 , 600 , 400);
		this.setIconifiable(true);
		this.setClosable(true);
		this.setMaximizable(false);
		
		sp = new Spck();
		this.add(sp);
		
		this.pack();
		
	}
	
	public void get_sp_item(String Id)
	{
		sp.set_sp_item(DB_Opreat.get_sp_item(Id));
	}
}
