package internalframe;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sj.panel.jinhuo.Tjjh_Panel_detail;
import sj.panel.jinhuo.Tjjh_Panel_main;
import sj.panel.xiaoshou.Tjdd_Panel_detail;
import sj.panel.xiaoshou.Tjdd_Panel_main;


public class tjdd extends JInternalFrame
{

	JInternalFrame jf = this;
	Tjdd_Panel_main tpm;
	Tjdd_Panel_detail tpd;
	
	public tjdd()
	{
		this.setVisible(true);
		this.setTitle("��Ӷ���");
		this.setBounds(0 , 0 , 970 , 620);
		this.setIconifiable(true);
		this.setClosable(true);
		this.setMaximizable(false);
		this.setLayout(null);
		
		
		tpm = new Tjdd_Panel_main();	//������Ϣ
		tpm.setBounds(0, 0, 970, 50);
		this.add(tpm);
				
		tpd = new Tjdd_Panel_detail();	//��ϸ��Ϣ
		tpd.setBounds(0, 50, 970, 500);
		this.add(tpd);
		
		JButton insertButton = new JButton("��Ӷ���");
		insertButton.addActionListener(new AddListener());
		insertButton.setBounds(0, 550, 970, 40);
		this.add(insertButton);
		
	}
	
	//��Ӷ���
		private class AddListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{		
				try 
				{
					int id_index = tpm.insertInfo();	//������ȡ�������
					
					if(id_index == -1)		//���Ϊ-1������ʧ��
					{
						return;
					}

					if(tpd.inset_detail(id_index))		//������ϸ��Ϣ
					{
						JOptionPane.showMessageDialog(jf
								, "����ɹ�");
						try {
							jf.setClosed(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (ClassNotFoundException e2) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(jf
							, "�������ش���"+e2.toString());
					e2.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(jf
							, "���ݿ����Ӵ���"+e1.toString());
					e1.printStackTrace();
				}
				
			}		
		}
}
