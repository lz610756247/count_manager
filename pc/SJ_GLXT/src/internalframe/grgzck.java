package internalframe;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import sj.main.GLXT_Frame;
import sj.main.GLXT_Frame1;
import sj.util.TbUser;

import dao.DB_Opreat;

/*
 * ���˹��ʲ鿴
 */
public class grgzck extends JInternalFrame
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
	JButton sj_button;	//��������·ݼ�¼
	JButton shuchu_button;
	
	public grgzck()
	{
		this.setVisible(true);
		this.setTitle("���˹��ʹ���");
		this.setBounds(0 , 0 , 750 , 400);
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
				new String[]{"����", "�绰" , "סַ" , "ְ��"}));
		this.setupComponet(chaxunziduan, 0, 0, 1, 30, true);
		
		
		this.chaxuntiaojian = new JComboBox();		//��ѯ����
		chaxuntiaojian.setModel(new DefaultComboBoxModel(
				new String[]{"����", "����"}));
		this.setupComponet(this.chaxuntiaojian, 1, 0, 1, 20, false);
		
		this.chaxun_txt = new JTextField();		//��ѯ�ı���
		this.setupComponet(this.chaxun_txt, 2, 0, 2, 200, true);
		
		this.chaxun_button = new JButton("��ѯ");		//��ѯ��ť
		this.chaxun_button.addActionListener(new CXActionListener());
		this.setupComponet(this.chaxun_button, 4, 0, 1, 0, false);
		
		this.tuihuo = new JButton("ɾ��������Ϣ");	//�˻���ť
		this.tuihuo.addActionListener(new THActionListener());
		this.setupComponet(this.tuihuo, 5, 0, 1, 20, true);		
		
		this.bianji_button = new JButton("�༭ѡ����");
		this.bianji_button.addActionListener(new BJActionListener());
		this.setupComponet(this.bianji_button, 0, 1, 1, 10, true);
		
		this.sj_button = new JButton("ȫ��������ӱ��¼�¼");
		this.sj_button.addActionListener(new SJActionListener());
		this.setupComponet(this.sj_button, 1, 1, 1, 10, true);
		
		this.shuchu_button = new JButton("���");
		this.shuchu_button.addActionListener(new SHCActionListener());
		this.setupComponet(this.shuchu_button, 2, 1, 1, 10, true);
		
		//Ȩ�޷���
		if((TbUser.getInstance().getQuan() ==-1 ) || (TbUser.getInstance().getQuan() > 10
				&& TbUser.getInstance().getQuan() <= 20))
		{
			
		}
		else
		{
			this.tuihuo.setEnabled(false);
		}		
		
	}
	
	//��ϸ�б�
	private void createDetailPane()
	{
		table = new JTable();
		table.setEnabled(true);	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setAutoscrolls(true);
		
		String[] tableHeads = new String[]{"Ա�����" , "����" , "��ϵ�绰" , "סַ" , "ְ��" , "��ע"};
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
		
		this.updateTable(DB_Opreat.get_yg_main(), dftm);	//�����
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
	
	
	//�Ƴ�����ѡ�еĶ�����Ϣ
	public boolean RemoveAllSelectYGXS(int Id)
	{
		return DB_Opreat.RemoveAllSelectYGXS(Id);
	}
	
	
	//�༭��ť����
	private final class BJActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			int id = table.getSelectedRow();
			System.out.println("ccc");
			if(id > -1)
			{
				if(!GLXT_Frame.ifs.containsKey("grgzck_p"))
				{
					grgzck_p jc = new grgzck_p();
					
					jc.addInternalFrameListener(new InternalFrameAdapter() 
					{
						public void internalFrameClosed(InternalFrameEvent e) 
						{
							GLXT_Frame.ifs.remove("grgzck_p");
						}
					});
					
					GLXT_Frame.ifs.put("grgzck_p", jc);
					GLXT_Frame.desktopPane.add(jc);
					try {
						jc.setSelected(true);
					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(jf
								, "�������ɴ���");
						e1.printStackTrace();
					}
					jc.setId(table.getValueAt(id, 0).toString());
				}												
			}
			else
			{
				JOptionPane.showMessageDialog(jf
						, "��ѡ����Ҫ�༭���");
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
					if(RemoveAllSelectYGXS(Integer.parseInt(table.getValueAt(id, 0).toString())))
					{
						JOptionPane.showMessageDialog(jf
								, "�ɹ�ɾ����¼��");
						updateTable(DB_Opreat.get_yg_main(), dftm);	//�����
					}
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
			String sql = "select * from t_yg where ";
			if(!cx.equals(""))
			{
				if(zd.equals("����"))
				{
					if(tj.equals("����"))
					{
						list = DB_Opreat.query_yg_main(sql+"yg_xm = '"+cx+"'");
					}
					if(tj.equals("����"))
					{
						list = DB_Opreat.query_yg_main(sql+"yg_xm like '%"+cx+"%'");
					}
				}
				
				if(zd.equals("�绰"))
				{
					if(tj.equals("����"))
					{
						list = DB_Opreat.query_yg_main(sql+"yg_dh = '"+cx+"'");
					}
					if(tj.equals("����"))
					{
						list = DB_Opreat.query_yg_main(sql+"yg_dh like '%"+cx+"%'");
					}
				}
				
				if(zd.equals("ְ��"))
				{
					if(tj.equals("����"))
					{
						list = DB_Opreat.query_yg_main(sql+"yg_zw = '"+cx+"'");
					}
					if(tj.equals("����"))
					{
						list = DB_Opreat.query_yg_main(sql+"yg_zw like '%"+cx+"%'");
					}
				}
				
				
				updateTable(list , dftm);
			}
			else
			{
				
			}
		}		
	}
	
	private class SHCActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			//�ļ�ѡ����
			JFileChooser filechooser = new JFileChooser();
			filechooser.setDialogType(JFileChooser.SAVE_DIALOG);
			int option = filechooser.showDialog(null, null);
			switch(option)
			{
				case JFileChooser.APPROVE_OPTION:
				{
					//���ñ���ʱ�ļ���
					try 
					{
						WritableWorkbook book = Workbook
								.createWorkbook(new File(filechooser.getSelectedFile().toString()+".xls"));
						
						 WritableSheet sheet = book.createSheet("��һҳ", 0);
						 
						 String[] tableHeads = new String[]{"����" , "��ϵ�绰" , "סַ" , "ְ��" , "��ע"};
						 for(int i=0;i<tableHeads.length;i++)
						 {
							 Label label = new Label(i, 0, tableHeads[i]);
							 sheet.addCell(label);
						 }
						 
				         
						 Vector v = dftm.getDataVector();
						 for(int i=0;i<v.size();i++)
						 {
							 Vector v1 = (Vector) v.get(i);
							 int temp = i;
							 temp++;
							 for(int j=1;j<v1.size();j++)
							 {
								 int temp1 = j;								 
								 Label label = new Label(--temp1, temp, v1.get(j).toString());
								 sheet.addCell(label);
							 }
						 }
						 
						 book.write();
				         book.close();
					} 
					catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
				
			}
		}		
	}
	
	//��������·ݼ�¼��ť����
	private final class SJActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(DB_Opreat.insert_yx_all())
			{				
				JOptionPane.showMessageDialog(jf
						, "�ɹ���ӡ�");
			}
			else
			{
				JOptionPane.showMessageDialog(jf
						, "ʧ�ܡ�");
			}			
		}		
	}	
}
