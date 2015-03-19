package internalframe;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import dao.DB_Connection;


import sj.panel.jinhuo.Tjjh_Panel_detail;
import sj.panel.jinhuo.Tjjh_Panel_main;

public class tjjh extends JInternalFrame
{
	JInternalFrame jf = this;
	Tjjh_Panel_main tpm;
	Tjjh_Panel_detail tpd;
	public tjjh()
	{
		this.setVisible(true);
		this.setTitle("��ӽ�����Ϣ");
		this.setIconifiable(true);
		this.setClosable(true);
		this.setMaximizable(false);
		this.setBounds(0, 0, 900, 620);
		this.setLayout(null);
		
		tpm = new Tjjh_Panel_main();	//������Ϣ
		tpm.setBounds(0, 0, 900, 50);
		this.add(tpm);
		
		
		tpd = new Tjjh_Panel_detail();	//��ϸ��Ϣ
		tpd.setBounds(0, 50, 900, 500);
		this.add(tpd);
		
		JButton insertButton = new JButton("��ӽ�����");
		insertButton.addActionListener(new AddListener());
		insertButton.setBounds(0, 550, 900, 40);
		this.add(insertButton);

	}
	
	// �������λ�ò���ӵ�������
	private void setupComponet(JComponent component, int gridx, int gridy,
			int gridwidth, int ipadx, boolean fill)
	{
			final GridBagConstraints gridbagconstraints = new GridBagConstraints();
			gridbagconstraints.gridx = gridx;
			gridbagconstraints.gridy = gridy;
			gridbagconstraints.insets = new Insets(5 , 5 , 5 , 5);
			if(gridwidth > 1)	//���ռ�õ�Ԫ�����һ���͸ı䣬�������һ��
			{
				gridbagconstraints.gridwidth = gridwidth;
			}
			if(ipadx > 0)
			{
				gridbagconstraints.ipadx = ipadx;
			}
			if(fill)
			{
				gridbagconstraints.fill = GridBagConstraints.HORIZONTAL;
			}
			this.add(component , gridbagconstraints);
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
