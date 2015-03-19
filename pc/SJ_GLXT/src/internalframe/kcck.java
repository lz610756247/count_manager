package internalframe;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import sj.main.GLXT_Frame;

import dao.DB_Opreat;

public class kcck extends JInternalFrame
{

	
	JInternalFrame jf = this;
	boolean isChanged = false;
	JTable table = new JTable();	//��ϸ��Ϣ�б�
	DefaultTableModel dftm;
	JButton tuihuo;
	JComboBox chaxunziduan;		//��ѯ�ֶ�
	JComboBox chaxuntiaojian;		//��ѯ����
	JTextField chaxun_txt;		//��ѯ�ı���
	JButton chaxun_button;	//��ѯ��ť
	JButton bianji_button;	//�༭ѡ���ť
	
	public kcck()
	{
		this.setVisible(true);
		this.setTitle("��Ʒ���鿴");
		this.setBounds(0 , 0 , 600 , 400);
		this.setIconifiable(true);
		this.setClosable(true);
		this.setMaximizable(false);
		this.setLayout(new GridBagLayout());
		
		this.createQueryPanel();	//�������
		
		this.createDetailPane();	//��ϸ��Ϣ�б�
		
		this.pack();
	}
	
	//��ѯ���
		private void createQueryPanel()
		{
			chaxunziduan = new JComboBox();		//��ѯ�ֶ�
			chaxunziduan.setModel(new DefaultComboBoxModel(
					new String[]{"��Ʒ����" , "���/˵��"}));
			this.setupComponet(chaxunziduan, 0, 0, 1, 30, false);
			
			
			this.chaxuntiaojian = new JComboBox();		//��ѯ����
			chaxuntiaojian.setModel(new DefaultComboBoxModel(
					new String[]{"����", "����"}));
			this.setupComponet(this.chaxuntiaojian, 1, 0, 1, 20, false);
			
			this.chaxun_txt = new JTextField();		//��ѯ�ı���
			this.setupComponet(this.chaxun_txt, 2, 0, 2, 200, true);
			
			this.chaxun_button = new JButton("��ѯ");		//��ѯ��ť
			this.chaxun_button.addActionListener(new CXActionListener());
			this.setupComponet(this.chaxun_button, 4, 0, 1, 0, false);
			
			this.tuihuo = new JButton("ɾ��");	//�˻���ť
			this.tuihuo.addActionListener(new THActionListener());
			this.setupComponet(this.tuihuo, 5, 0, 1, 20, true);
			
			this.bianji_button = new JButton("�༭ѡ����");
			this.bianji_button.addActionListener(new BJActionListener());
			this.setupComponet(this.bianji_button, 0, 1, 1, 10, true);
			
		}
		
		//��ϸ�б�
		private void createDetailPane()
		{
			table = new JTable();
			table.setEnabled(true);	
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			this.setAutoscrolls(true);
			
			String[] tableHeads = new String[]{"���" , "��Ʒ����" , "����" , "��λ" , "����" , "�ܼ�" , "���/˵��"};
			dftm = (DefaultTableModel) table.getModel();
			dftm.setColumnIdentifiers(tableHeads);
					
			JScrollPane pane = new JScrollPane (table);
			pane.setAutoscrolls(true);
			
			final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
			gridBagConstraints_6.weighty = 1.0;
			gridBagConstraints_6.insets = new Insets(0, 10, 5, 10);
			gridBagConstraints_6.fill = GridBagConstraints.BOTH;
			gridBagConstraints_6.gridwidth = 6;
			gridBagConstraints_6.gridy = 2;
			gridBagConstraints_6.gridx = 0;
			getContentPane().add(pane, gridBagConstraints_6);
			
			this.updateTable(DB_Opreat.query_spkc(), dftm);	//�����
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
		
		
		//ˢ�±��
		private void updateTable(List list, DefaultTableModel dftm)
		{		
			int num = dftm.getRowCount();
			for(int i = 0; i<num;i++)	//�����ǰ������
			{
				dftm.removeRow(0);
			}
					
			for(int i =0; i<list.size();i++)
			{
				Vector item = (Vector) list.get(i);
				dftm.addRow(item);
			}
		}
		
		//�༭��ť����
		private final class BJActionListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				int id = table.getSelectedRow();
				if(id > -1)
				{
					
					spck sp = new spck();
					try 
					{
						sp.setSelected(true);
					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(jf
								, "�������ɴ���");
						e1.printStackTrace();
					}
					GLXT_Frame.desktopPane.add(sp);
					sp.get_sp_item(table.getValueAt(id, 0).toString());
				}
				else
				{
					JOptionPane.showMessageDialog(jf
							, "��ѡ����Ҫ�༭�Ľ�������");
				}
			}		
		}
		
		
		//�˻���ť����
		private final class THActionListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				int type = JOptionPane.showConfirmDialog(null,
						"ɾ�������ݽ����ɻָ��������Ҫɾ����", "ȷ��ɾ��",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (type == JOptionPane.YES_OPTION)
				{
					int id = table.getSelectedRow();
					if(id > -1)
					{
//						if(RemoveAllSelectDD(Integer.parseInt(table.getValueAt(id, 0).toString())))
//						{
//							JOptionPane.showMessageDialog(jf
//									, "�ɹ�ɾ����������");
//						}
//						dftm.removeRow(id);
					}
					else
					{
						JOptionPane.showMessageDialog(jf
								, "��ѡ�к���ɾ����");
					}
				}		
			}		
		}
		
		
		//��ѯ��ť����
		private final class CXActionListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				String zd = chaxunziduan.getSelectedItem().toString();
				String tj = chaxuntiaojian.getSelectedItem().toString();
				String cx = chaxun_txt.getText();
				List list = null;
				String sql = "select * from t_sp where ";
				if(!cx.equals(""))
				{
					if(zd.equals("��Ʒ����"))
					{
						if(tj.equals("����"))
						{
							list = DB_Opreat.query_sp_equals(sql+"sp_name = '"+cx+"'");
						}
						if(tj.equals("����"))
						{
							list = DB_Opreat.query_sp_contains(sql+"sp_name like '%"+cx+"%'");
						}
					}
					
					if(zd.equals("���/˵��"))
					{
						if(tj.equals("����"))
						{
							list = DB_Opreat.query_sp_equals(sql+"sp_bz = '"+cx+"'");
						}
						if(tj.equals("����"))
						{
							list = DB_Opreat.query_sp_contains(sql+"sp_bz like '%"+cx+"%'");
						}
					}										
					
					updateTable(list , dftm);
				}
				else
				{
					
				}
			}		
		}
}