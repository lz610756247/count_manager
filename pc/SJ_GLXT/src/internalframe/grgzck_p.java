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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import sj.util.Person;
import sj.util.TbUser;

import dao.DB_Opreat;

/*
 * ���˹��ʲ鿴
 */
public class grgzck_p extends JInternalFrame
{
	JInternalFrame jf = this;
	boolean isChanged = false;
	JTable table = new JTable();	//��ϸ��Ϣ�б�
	DefaultTableModel dftm;
	JButton tuihuo;
	JButton chaxun_button;	//��ѯ��ť
	JButton bianji_button;	//�༭ѡ���ť
	JButton shuchu_button;
	JButton tyx;
	JButton tjsj_button;	//���ʱ�䰴ť
	String Id;
	JTextField xm;
	JTextField dh;
	JTextField zw;
	JTextField from;
	JTextField to;
	JTextField tjsj;
	
	
	public grgzck_p()
	{
		this.setVisible(true);
		this.setTitle("���˹��ʲ鿴");
		this.setBounds(200 , 0 , 750 , 400);
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
		this.xm = new JTextField();
		this.xm.setEditable(false);
		this.setupComponet(this.xm, 0, 0, 1, 100, true);
		this.dh = new JTextField();
		this.dh.setEditable(false);
		this.setupComponet(this.dh, 1, 0, 1, 100, true);
		this.zw = new JTextField();
		this.zw.setEditable(false);
		this.setupComponet(this.zw, 2, 0, 2, 100, true);
		
		this.tuihuo = new JButton("ɾ��������Ϣ");	//�˻���ť
		this.tuihuo.addActionListener(new THActionListener());
		this.setupComponet(this.tuihuo, 4, 0, 1, 0, true);			
		
		this.setupComponet(new JLabel("���ڲ�ѯ    ��"), 0, 1, 1, 0, true);
		this.from = new JTextField();
		this.from.setText("2014-01");
		this.setupComponet(this.from, 1, 1, 1, 100, true);
		this.setupComponet(new JLabel("��"), 2, 1, 1, 0, true);
		this.to = new JTextField();
		this.to.setText("2014-02");
		this.setupComponet(this.to, 3 , 1, 1, 100, true);
		
		this.chaxun_button = new JButton("��ѯ");		//��ѯ��ť
		this.chaxun_button.addActionListener(new CXActionListener());
		this.setupComponet(this.chaxun_button, 4, 1, 1, 0, false);
		
		this.bianji_button = new JButton("�༭ѡ����");
		this.bianji_button.addActionListener(new BJActionListener());
		this.setupComponet(this.bianji_button, 0, 2, 1, 10, true);
		
		this.tyx = new JButton("��ӱ��¼�¼");
		this.tyx.addActionListener(new TYActionListener());
//		this.setupComponet(this.tyx, 1, 2, 1, 0, true);
		
		this.tjsj_button = new JButton("����·ݼ�¼");
		this.tjsj_button.addActionListener(new TYOActionListener());
		this.setupComponet(this.tjsj_button, 1, 2, 1, 0, true);
		
		this.tjsj = new JTextField("2014-01");
		this.setupComponet(this.tjsj, 2, 2, 2, 0, true);
		
		this.shuchu_button = new JButton("���");
		this.shuchu_button.addActionListener(new SHCActionListener());
		this.setupComponet(this.shuchu_button, 4, 2, 1, 0, true);
		
		//Ȩ�޷���
		if((TbUser.getInstance().getQuan() ==-1 ) || (TbUser.getInstance().getQuan() > 10
				&& TbUser.getInstance().getQuan() <= 20))
		{
			
		}
		else
		{
			this.tuihuo.setEnabled(false);
			this.tyx.setEnabled(false);
		}			
	}
	
	//��ϸ�б�
	private void createDetailPane()
	{
		table = new JTable();
		table.setEnabled(true);	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setAutoscrolls(true);
		
		String[] tableHeads = new String[]{"���" , "����" , "�ܹ���" , "ʵ�ʹ���" , "Ԥ֧����" };
		dftm = (DefaultTableModel) table.getModel();
		dftm.setColumnIdentifiers(tableHeads);
				
		JScrollPane pane = new JScrollPane (table);
		pane.setAutoscrolls(true);
		
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.weighty = 1.0;
		gridBagConstraints_6.insets = new Insets(0, 10, 5, 10);
		gridBagConstraints_6.fill = GridBagConstraints.BOTH;
		gridBagConstraints_6.gridwidth = 6;
		gridBagConstraints_6.gridy = 3;
		gridBagConstraints_6.gridx = 0;
		getContentPane().add(pane, gridBagConstraints_6);
				
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
	
	public void setId(String Id)
	{
		this.Id = Id;
		List list = DB_Opreat.query_item_yg(Id);
		Person person = (Person) list.get(0);
		this.xm.setText(person.getXm());
		this.dh.setText(person.getDh());
		this.zw.setText(person.getZw());
		this.updateTable(DB_Opreat.get_yx_main(Id), dftm);	//�����
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
	public boolean RemoveAllSelectXX(int Id)
	{
		return DB_Opreat.RemoveAllSelectXX(Id);
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
				if(!GLXT_Frame.ifs.containsKey("tjyx"))
				{
					tjyx jc = new tjyx();
					
					jc.addInternalFrameListener(new InternalFrameAdapter() 
					{
						public void internalFrameClosed(InternalFrameEvent e) 
						{
							GLXT_Frame.ifs.remove("tjyx");
						}
					});
					
					GLXT_Frame.ifs.put("tjyx", jc);
					GLXT_Frame.desktopPane.add(jc);
					try {
						jc.setSelected(true);
					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(jf
								, "�������ɴ���");
						e1.printStackTrace();
					}
					
					Map info = new HashMap();
					info.put("Id", table.getValueAt(id, 0).toString());
					info.put("����", xm.getText());
					info.put("�绰", dh.getText());
					info.put("ְ��", zw.getText());
					jc.setBase(info);
				}													
			}
			else
			{
				JOptionPane.showMessageDialog(jf
						, "��ѡ����Ҫ�༭�ļ�¼��");
			}
		}		
	}
	
	//����·ݼ�¼
	private class TYActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(DB_Opreat.insert_yx(Id))
			{
				updateTable(DB_Opreat.get_yx_main(Id), dftm);	//�����
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
	
	//��������·ݼ�¼
	private class TYOActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(DB_Opreat.insert_yx(Id , tjsj.getText()))
			{
				updateTable(DB_Opreat.get_yx_main(Id), dftm);	//�����
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
					if(RemoveAllSelectXX(Integer.parseInt(table.getValueAt(id, 0).toString())))
					{
						JOptionPane.showMessageDialog(jf
								, "�ɹ�ɾ����¼��");
					}
					dftm.removeRow(id);
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
			List list = null;
			String sql = "select * from t_xs_y where time >= '"+from.getText()
					+"' and time <= '"+to.getText()+"'"+"and yg_id = "+Id;
			list = DB_Opreat.query_xs_main_equals(sql);
			
			updateTable(list , dftm);
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
						 
						 String[] tableHeads = 
								 new String[]{"����" , "�ܹ���" , "ʵ�ʹ���" , "Ԥ֧����" };
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
}
