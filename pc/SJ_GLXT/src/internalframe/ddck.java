package internalframe;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import dao.DB_Opreat;

import sj.panel.jinhuo.Jhck_Panel_detail;
import sj.panel.jinhuo.Tjjh_Panel_main;
import sj.panel.xiaoshou.Ddck_Panel_detail;
import sj.panel.xiaoshou.Tjdd_Panel_main;
import sj.util.TbUser;

public class ddck extends JInternalFrame
{
	JInternalFrame jf = this;
	Tjdd_Panel_main tpm;
	Ddck_Panel_detail dpd;
	int id = 0;

	public ddck()
	{
		this.setVisible(true);
		this.setTitle("订单查看");
		this.setBounds(0 , 0 , 990 , 620);
		this.setIconifiable(true);
		this.setClosable(true);
		this.setMaximizable(true);
		this.setLayout(null);
		
		tpm = new Tjdd_Panel_main();	//基础信息
		tpm.setBounds(0, 0, 990, 50);
		this.add(tpm);
		
		dpd = new Ddck_Panel_detail();	//详细信息
		dpd.setBounds(0, 50, 990, 500);
		this.add(dpd);
		
		JButton bcButton = new JButton("保存订单");
		bcButton.addActionListener(new BCActionListener());
		//权限分配
		if((TbUser.getInstance().getQuan() ==-1 ) || (TbUser.getInstance().getQuan() > 0
				&& TbUser.getInstance().getQuan() <= 10))
		{
			bcButton.setBounds(0, 550, 990, 40);
			this.add(bcButton);
		}
		else
		{			
			
		}			
		
	}
	
	//获取基础信息
		public void get_dd_all(int id)
		{
			this.id = id;
			tpm.setBaseInfo(DB_Opreat.get_item_dd_main(this.id));	//设置基础信息
			dpd.setTableDetail(DB_Opreat.get_item_dd_detail(this.id) , id);	//填充表格
		}
		
		
		//保存进货单按钮
		private final class BCActionListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				tpm.checkChanged();
				if(tpm.isChanged)
				{
					//更新基础信息
					DB_Opreat.update_base_info1(tpm.getInfo());
					tpm.setChanged();
					JOptionPane.showMessageDialog(jf
							, "已经更改,基础信息。");
				}
				
				dpd.checkChanged();
				if(dpd.isChanged)
				{
					//更新详细信息
					DB_Opreat.update_dd_detail(dpd.get_add_list() , tpm.getInfoId());
					dpd.setChanged();
					JOptionPane.showMessageDialog(jf
							, "已经更改,详细信息。");
					dpd.setTableDetail(DB_Opreat.get_item_dd_detail(id) , id);	//填充表格
				}
			}	
		}
}