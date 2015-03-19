package internalframe;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import dao.DB_Opreat;

import sj.panel.jinhuo.Jhck_Panel_detail;
import sj.panel.jinhuo.Tjjh_Panel_detail;
import sj.panel.jinhuo.Tjjh_Panel_main;
import sj.util.TbUser;

public class jhck extends JInternalFrame
{

	JInternalFrame jf = this;
	Tjjh_Panel_main tpm;
	Jhck_Panel_detail jpd;
	int id = 0;
	
	public jhck()
	{
		super();
		this.setVisible(true);
		this.setTitle("�����鿴");
		this.setIconifiable(true);
		this.setClosable(true);
		this.setMaximizable(false);
		this.setBounds(0, 0, 900, 620);
		this.setLayout(null);

		tpm = new Tjjh_Panel_main();	//������Ϣ
		tpm.setBounds(0, 0, 900, 50);
		this.add(tpm);
		
		
		jpd = new Jhck_Panel_detail();	//��ϸ��Ϣ
		jpd.setBounds(0, 50, 900, 500);
		this.add(jpd);
		
		JButton bcButton = new JButton("���������");
		bcButton.addActionListener(new BCActionListener());
		
		//Ȩ�޷���
		if((TbUser.getInstance().getQuan() ==-1 ) || (TbUser.getInstance().getQuan() > 0
				&& TbUser.getInstance().getQuan() <= 10))
		{
			bcButton.setBounds(0, 550, 900, 40);
			this.add(bcButton);
		}
		else
		{			
			
		}		
	}
	
	
	//��ȡ������Ϣ
	public void get_dd_all(int id)
	{
		this.id = id;
		tpm.setBaseInfo(DB_Opreat.get_item_jj_main(this.id));	//���û�����Ϣ
		jpd.setTableDetail(DB_Opreat.get_item_jj_detail(this.id) , this.id);	//�����
	}
	
	
	//�����������ť
	private final class BCActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			tpm.checkChanged();
			if(tpm.isChanged)
			{
				//���»�����Ϣ
				DB_Opreat.update_base_info(tpm.getInfo());
				tpm.setChanged();
				JOptionPane.showMessageDialog(jf
						, "�Ѿ�����,������Ϣ��");
			}
			
			jpd.checkChanged();
			if(jpd.isChanged)
			{
				//������ϸ��Ϣ
				DB_Opreat.update_jj_detail(jpd.get_add_list() , tpm.getInfoId());
				jpd.setChanged();
				JOptionPane.showMessageDialog(jf
						, "�Ѿ�����,��ϸ��Ϣ��");
				jpd.setTableDetail(DB_Opreat.get_item_jj_detail(id) , id);	//�����
			}
		}	
	}
	
	
	
}
