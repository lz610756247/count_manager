package internalframe;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import sj.main.GLXT_Frame1;
import sj.util.Person;
import dao.DB_Opreat;

public class ndgzbb_y extends JInternalFrame
{
	JInternalFrame jf = this;
	boolean isChanged = false;
	JTable table = new JTable();	//��ϸ��Ϣ�б�
	DefaultTableModel dftm;
	String Id;
	JButton sc;
	
	public ndgzbb_y()
	{
		this.setVisible(true);
		this.setTitle("�·ݹ��ʻ���");
		this.setBounds(200 , 0 , 0 , 0);
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
		this.sc = new JButton("���");
		this.sc.addActionListener(new SHCActionListener());
		this.setupComponet(this.sc, 0, 0, 1, 0, true);
	}
	
	//��ϸ�б�
	private void createDetailPane()
	{
		table = new JTable();
		table.setEnabled(true);	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setAutoscrolls(true);
		
		String[] tableHeads = new String[]{"��¼���" , "����" , "����" , "�ܹ���" , "ʵ�ʹ���" , "Ԥ֧����" };
		dftm = (DefaultTableModel) table.getModel();
		dftm.setColumnIdentifiers(tableHeads);
				
		JScrollPane pane = new JScrollPane (table);
		pane.setAutoscrolls(true);
		
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.weighty = 1.0;
		gridBagConstraints_6.weightx = 1.0;
		gridBagConstraints_6.insets = new Insets(0, 10, 5, 10);
		gridBagConstraints_6.fill = GridBagConstraints.BOTH;
		gridBagConstraints_6.gridwidth = 6;
		gridBagConstraints_6.gridy = 1;
		gridBagConstraints_6.gridx = 0;
		gridBagConstraints_6.ipadx = 0;
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
		this.updateTable(DB_Opreat.get_yx_main1(Id), dftm);	//�����
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
								 new String[]{ "����" , "����" , "�ܹ���" , "ʵ�ʹ���" , "Ԥ֧����" };
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
