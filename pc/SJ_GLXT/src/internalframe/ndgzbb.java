package internalframe;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
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
import javax.swing.table.DefaultTableModel;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import sj.main.GLXT_Frame;
import sj.main.GLXT_Frame1;

import dao.DB_Opreat;

public class ndgzbb extends JInternalFrame
{
	JInternalFrame jf = this;
	boolean isChanged = false;
	JTable table = new JTable();	//��ϸ��Ϣ�б�
	DefaultTableModel dftm;
	JButton tuihuo;
	JTextField chaxun_txt;		//��ѯ�ı���
	JButton chaxun_button;	//��ѯ��ť
	JButton bianji_button;	//�༭ѡ���ť
	JButton shuchu_button;
	
	public ndgzbb()
	{
		this.setVisible(true);
		this.setTitle("��Ȳ鿴");
		this.setBounds(50 , 0 , 600 , 400);
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
		this.setupComponet(new JLabel("��ѯ��ȣ�"), 0, 0, 1, 0, true);
		this.chaxun_txt = new JTextField();		//��ѯ�ı���
		this.setupComponet(this.chaxun_txt, 1, 0, 2, 200, true);
		
		this.chaxun_button = new JButton("��ѯ");		//��ѯ��ť
		this.chaxun_button.addActionListener(new CXActionListener());
		this.setupComponet(this.chaxun_button, 4, 0, 1, 0, false);
		
		this.tuihuo = new JButton("ɾ��������Ϣ");	//�˻���ť
		this.tuihuo.addActionListener(new THActionListener());
		this.setupComponet(this.tuihuo, 5, 0, 1, 20, true);
		
		this.bianji_button = new JButton("�鿴ѡ�м�¼");
		this.bianji_button.addActionListener(new BJActionListener());
		this.setupComponet(this.bianji_button, 0, 1, 1, 10, true);
		
		this.shuchu_button = new JButton("���");
		this.shuchu_button.addActionListener(new SHCActionListener());
		this.setupComponet(this.shuchu_button, 2, 1, 1, 10, true);
		
	}
	
	//��ϸ�б�
	private void createDetailPane()
	{
		table = new JTable();
		table.setEnabled(true);	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setAutoscrolls(true);
		
		String[] tableHeads = new String[]{"���","����" , "Ӧ������" , "ʵ������" , "Ԥ֧����" };
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
		
		this.updateTable(DB_Opreat.get_yx_year(), dftm);	//�����
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
	public boolean RemoveAllSelectYX(int Id)
	{
		return DB_Opreat.RemoveAllSelectYX(Id);
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
				if(!GLXT_Frame.ifs.containsKey("ndgzbb_y"))
				{
					ndgzbb_y jc = new ndgzbb_y();
					
					jc.addInternalFrameListener(new InternalFrameAdapter() 
					{
						public void internalFrameClosed(InternalFrameEvent e) 
						{
							GLXT_Frame.ifs.remove("ndgzbb_y");
						}
					});
					
					GLXT_Frame.ifs.put("ndgzbb_y", jc);
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
			System.out.println("�¹��ʼ�¼ID��"+table.getValueAt(table.getSelectedRow(), 0));
			if (type == JOptionPane.YES_OPTION)
			{
				int id = table.getSelectedRow();
				if(id > -1)
				{
					
					if(RemoveAllSelectYX(Integer.parseInt(table.getValueAt(id, 0).toString())))
					{
						JOptionPane.showMessageDialog(jf
								, "�ɹ�ɾ����¼��");
						//ˢ�±��
						updateTable(DB_Opreat.get_yx_year(), dftm);	//�����
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
			String cx = chaxun_txt.getText();
			List list = null;
			String sql = "select time from t_xs_y ";
			if(!cx.equals(""))
			{
				sql += "where time like '%"+cx+"%' "+"group by time order by time desc";
				list = DB_Opreat.get_yx_year_equle(sql);
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
						 
						 String[] tableHeads = new String[]{"����" , "Ӧ������" , "ʵ������" , "Ԥ֧����" };
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
