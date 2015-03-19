package internalframe;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import dao.DB_Opreat;

import sj.panel.gongzi.Tjgz_Panel_main;
import sj.panel.gongzi.Tjxx_Panel_detail;
import sj.util.TbUser;

public class tjyx extends JInternalFrame
{
	JInternalFrame jf = this;
	Tjgz_Panel_main tpm;
	Tjxx_Panel_detail tpd;
	Map map = new HashMap();
	
	public tjyx()
	{
		this.setVisible(true);
		this.setTitle("个人月份查看");
		this.setBounds(0, 0, 700, 620);
		this.setLayout(null);
		this.setIconifiable(true);
		this.setClosable(true);
		this.setMaximizable(false);
		
		tpm = new Tjgz_Panel_main();
		tpm.setBounds(0, 0, 700, 50);
		this.add(tpm);
		
		tpd = new Tjxx_Panel_detail();
		tpd.setBounds(0, 50, 700, 500);
		this.add(tpd);
		
		JButton bcButton = new JButton("保存");
		bcButton.addActionListener(new BCActionListener());
		
		//权限分配
		if((TbUser.getInstance().getQuan() ==-1 ) || (TbUser.getInstance().getQuan() > 10
				&& TbUser.getInstance().getQuan() <= 20))
		{
			bcButton.setBounds(0, 550, 700, 40);
			this.add(bcButton);
		}
		else
		{
			
		}			
		
	}
	
	public void setBase(Map info)
	{
		this.map = info;
		tpm.setBaseInfo(info);
		tpd.setTableDetail(DB_Opreat.get_item_xs_detail(info.get("Id").toString()) 
				, Integer.parseInt(map.get("Id").toString()));
	}
	
	//保存进货单按钮
	private final class BCActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// TODO Auto-generated method stub
			tpd.checkChanged();
			if(tpd.isChanged)
			{
				//更新详细信息
				DB_Opreat.update_xs_detail(tpd.get_add_list() , tpm.getInfoId());
				tpd.setChanged();
				JOptionPane.showMessageDialog(jf
						, "已经更改,详细信息。");
				tpd.setTableDetail(DB_Opreat.get_item_xs_detail(map.get("Id").toString())
						,Integer.parseInt(map.get("Id").toString()));
			}
		}	
	}
}