package internalframe;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import sj.panel.gongzi.Tjxx_Panel_detail;
import sj.util.Calculat;
import sj.util.Item;
import sj.util.Person;
import dao.DB_Opreat;

public class kstjrx extends JInternalFrame
{
	JInternalFrame jf = this;
	Tjgz_Panel_main tpm;
	Tjxx_Panel_detail tpd;
	JComboBox sj;
	JComboBox xm;
	String Id;
	public kstjrx()
	{
		this.setVisible(true);
		this.setTitle("���������н");
		this.setBounds(0 , 0 , 650 , 620);
		this.setIconifiable(true);
		this.setClosable(true);
		this.setMaximizable(false);
		this.setLayout(null);
		
		tpm = new Tjgz_Panel_main();
		tpm.setBounds(0, 0, 650, 50);
		this.add(tpm);
		
		tpd = new Tjxx_Panel_detail();
		tpd.setBounds(0, 50, 650, 500);
		this.add(tpd);
		
		JButton bcButton = new JButton("����");
		bcButton.addActionListener(new BCActionListener());
		bcButton.setBounds(0, 550, 650, 40);
		this.add(bcButton);
		
	}
	
	//ͷ����Ϣ���
	public class Tjgz_Panel_main extends JPanel
	{	
		private JTextField dh;
		private JTextField zw;
		Map map = new HashMap();	//������Ϣ
		public boolean isChanged = false;
		
		public Tjgz_Panel_main()
		{
			super();
			setLayout(new GridBagLayout());
			setBounds(0, 0, 400, 50);
			this.setBorder(new BevelBorder(BevelBorder.RAISED));
			
			//����
			this.initComboBox();
			this.setupComponet(xm, 0, 0, 1, 0, true);
			xm.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					// TODO Auto-generated method stub
					doKeHuSelectAction();
				}		
			});
			
			//����
			this.setupComponet(sj, 1, 0, 1, 0, true);
			sj.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					// TODO Auto-generated method stub
					doKeHuSelectAction1();
				}		
			});
			
			//�绰
			this.setupComponet(new JLabel("�绰��"),2 , 0, 1, 0, true);
			this.dh = new JTextField();
			this.dh.setEditable(false);
			this.setupComponet(this.dh, 3, 0, 1, 100, true);
			//ְ��
			this.setupComponet(new JLabel("ְ��"),4 , 0, 1, 0, true);
			this.zw = new JTextField();
			zw.setEditable(false);
			this.setupComponet(this.zw, 5, 0, 1, 110, true);
			
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
		
		//��֯�����б�
		public void initComboBox()
		{
			if(xm == null)
			{
				xm = new JComboBox();
				xm.removeAllItems();
				List list = DB_Opreat.query_all_yg();
				xm.addItem("0,");
				
				for(int i=0;i<list.size();i++)
				{
					Person person = (Person)list.get(i);
					map.put(person.getId(), person);
					xm.addItem(person.getId()+","+person.getXm());
				}
			}
			else
			{
				doKeHuSelectAction();
			}
			if(sj == null)
			{
				sj = new JComboBox();
				get_sj();		
			}
			else
			{
				doKeHuSelectAction1();
			}
		}
		
		private void get_sj()
		{
			sj.removeAllItems();		
			List list = DB_Opreat.get_yx_main(xm.getSelectedItem().toString()
					.substring(0 , xm.getSelectedItem().toString().lastIndexOf(",")));
			for(int i=0;i<list.size();i++)
			{
				Vector v = (Vector) list.get(i);
				sj.addItem(v.get(1));
			}
		}
		
		private void doKeHuSelectAction() 
		{
			Id = xm.getSelectedItem().toString()
					.substring(0 , xm.getSelectedItem().toString().lastIndexOf(","));
			Person person = DB_Opreat.query_all_yg_by_Id(Id);
			this.dh.setText(person.getDh());
			this.zw.setText(person.getZw());						
			get_sj();
			tpd.setTableDetail(DB_Opreat.get_item_xs_detail1(Id , sj.getSelectedItem().toString()));
		}
		
		private void doKeHuSelectAction1() 
		{
			Id = xm.getSelectedItem().toString()
					.substring(0 , xm.getSelectedItem().toString().lastIndexOf(","));
			Person person = DB_Opreat.query_all_yg_by_Id(Id);
			this.dh.setText(person.getDh());
			this.zw.setText(person.getZw());
			tpd.setTableDetail(DB_Opreat.get_item_xs_detail1(Id , sj.getSelectedItem().toString()));
		}
		
		public void setBaseInfo(Map map)
		{
			
		}
		
		
		public String getInfoId()
		{
			System.out.println(this.map.get("Id").toString());
			return this.map.get("Id").toString();
		}
		
	}
	
	//�б����
	public class Tjxx_Panel_detail extends JPanel
	{

		JPanel jpd = this;
		JTable table = new JTable();	//��ϸ��Ϣ�б�
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// ����table���ݾ���
		DefaultTableModel dftm;
		private List<Vector> list;		//ԭ����������
		private List<Vector> temp_list;		//�м����
		private List<Vector> add_list = new ArrayList();	//����ӵ�������
		public boolean isChanged = false;
		int count = -1;		//ԭ������Ʒ��¼����
		JTextField zongjia;		//��Ʒ�ܶ�
		JTextField shifu;		//��Ʒʵ��
		JTextField qianfu;		//��ƷǷ��
		double sum = 0.0;
		double sum1 = 0.0;
		double sum2 = 0.0;
		
		
		public Tjxx_Panel_detail()
		{
			super();
			this.setEnabled(false);
			this.setBounds(0 , 100 , 400 , 0);
			this.setLayout(new BorderLayout());
			
			
			this.add(this.createDetailPane()  , BorderLayout.CENTER);
			JButton del = new JButton("ɾ��ѡ����");
			del.addActionListener(new DeleteListener());
			this.add(del , BorderLayout.EAST);
			this.add(this.createAddDetail() , BorderLayout.SOUTH);
		}
		
		
		//��ʾ�Ѿ���ӵ���Ϣ
		public JScrollPane createDetailPane()
		{
			table = new JTable();
			table.setEnabled(true);	
			tcr.setHorizontalAlignment(JLabel.CENTER);
			table.setDefaultRenderer(Object.class, tcr);	//����������־���
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			this.table.setAutoscrolls(true);
			table.setRowHeight(20);
			table.setFont(new Font("����" , Font.BOLD, 15));
			
			String[] tableHeads = new String[]{"���" , "�ӹ���Ʒ" , "�ӹ�����"
					, "�ӹ�����"  , "��ע��Ϣ", "�ܼ�/Ԫ" , "������;" , "����"};
			dftm = (DefaultTableModel) table.getModel();
			dftm.setColumnIdentifiers(tableHeads);
			
			table.getModel().addTableModelListener(new TableModelListener()
			{
				@Override
				public void tableChanged(TableModelEvent e) 
				{
					if(e.getType() == TableModelEvent.UPDATE)
					{
						String newvalue = table.getValueAt(e.getLastRow(),e.getColumn()).toString();
						//������ϸ��Ϣ
						Vector v = getDataVector();
						List list = new ArrayList();
						for(int i=0;i<v.size();i++)
						{
							Vector v1 = (Vector) v.get(i);
							v1.get(0);
							
							if(v1.get(0).toString().equals(table.getValueAt(e.getLastRow(),0).toString()))
							{			
								list.add(v1);
								DB_Opreat.update_xs_detail_1(list , Id , sj.getSelectedItem().toString());
								setTableDetail(DB_Opreat.get_item_xs_detail1(Id , sj.getSelectedItem().toString()));
								System.out.println("�ɹ�");
								break;
							}
						}				
					}				
				}});
					
			JScrollPane pane = new JScrollPane (table);
			return pane;
		}
		
		
		//�����ϸ��Ϣ��
		public JTabbedPane createAddDetail()
		{
			AddDetail newpanel = new AddDetail();
			YZ yz = new YZ();
			JTabbedPane tabPane = new JTabbedPane();
			tabPane.addTab("��������", null, newpanel, "��������");
			tabPane.addTab("Ԥ֧����", null, yz, "Ԥ֧����");
			return tabPane;
		}

		class AddDetail extends JPanel
		{
			JTextField mingcheng;
			JTextField shuliang;
			JTextField danjia;
			JButton add;
			JButton reset;
			JButton shuchu;
			//�ı���
			private JTextArea textarea;
			private JScrollPane panel;
			AddDetail()
			{
				super();
				setLayout(new GridBagLayout());
				this.setBorder(new BevelBorder(BevelBorder.LOWERED));
				
				//��Ʒ����
				this.setupComponet(new JLabel("�ӹ���Ʒ��") , 0 , 0 , 1 , 0 , true);
				this.mingcheng = new JTextField();
				this.setupComponet(this.mingcheng, 1, 0, 1, 100, true);
				
				//��Ʒ����
				this.setupComponet(new JLabel("������") , 2 , 0 , 1 , 0 , true);
				this.shuliang = new JTextField();
				this.setupComponet(this.shuliang, 3, 0, 1, 100, true);
				
				//��Ʒ����
				this.setupComponet(new JLabel("���ۣ�") , 4 , 0 , 1 , 0 , true);
				this.danjia = new JTextField();
				this.setupComponet(this.danjia, 5, 0, 1, 100, true);
				
				//�ı���ؼ�
				this.textarea = new JTextArea();
				this.panel = new JScrollPane(textarea);
				this.panel.setSize(this.getWidth() , 100);
				this.textarea.setLineWrap(true);
				final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
				gridBagConstraints_6.weighty = 1.0;
				gridBagConstraints_6.insets = new Insets(0, 0, 0, 0);
				gridBagConstraints_6.fill = GridBagConstraints.BOTH;
				gridBagConstraints_6.gridwidth = 10;
				gridBagConstraints_6.gridy = 1;
				gridBagConstraints_6.gridx = 0;
				gridBagConstraints_6.ipady = 50;
				gridBagConstraints_6.ipadx = 0;
				gridBagConstraints_6.fill = GridBagConstraints.HORIZONTAL;
				add(panel, gridBagConstraints_6);
				
							
				//�ܼ���ʾ
				zongjia = new JTextField();
				zongjia.setEditable(false);	
				zongjia.setFont(new Font("����" , Font.BOLD, 14));
				this.setupComponet(zongjia, 0, 3, 2, 100, true);
				
				//ʵ����ʾ
				shifu = new JTextField();
				shifu.setEditable(false);
				shifu.setFont(new Font("����" , Font.BOLD, 14));
				this.setupComponet(shifu, 2, 3, 2, 100, true);
				
				//Ƿ����ʾ
				qianfu = new JTextField();
				qianfu.setEditable(false);
				qianfu.setFont(new Font("����" , Font.BOLD, 14));
				this.setupComponet(qianfu, 4, 3, 3, 100, true);
				
				updateZJ();
				
				//��Ӱ�ť
				this.add = new JButton("��Ӽ�¼");
				this.setupComponet(this.add, 2, 2, 3, 0, false);
				this.add.addActionListener(new AddButtonActionListener());
				//���ð�ť
				this.reset = new JButton("����");
				this.setupComponet(this.reset, 5, 2, 1, 0, false);
				this.reset.addActionListener(new RestButtonActionListener());
				
				this.shuchu = new JButton("���");
				this.setupComponet(this.shuchu, 6, 2, 1, 0, false);
				this.shuchu.addActionListener(new SHCActionListener());
				
			}		
					
			// �������λ�ò���ӵ�������
			private void setupComponet(JComponent component, int gridx, int gridy,
					int gridwidth, int ipadx, boolean fill)
			{
					final GridBagConstraints gridbagconstraints = new GridBagConstraints();
					gridbagconstraints.gridx = gridx;
					gridbagconstraints.gridy = gridy;
					gridbagconstraints.insets = new Insets(5 , 5 , 0 , 5);
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
			
			
			// ��Ӱ�ť���¼�������
			private final class AddButtonActionListener implements ActionListener
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{		
					Vector item = new Vector();
					item.add("������Զ�����");
					item.add(mingcheng.getText());	//����
					item.add(shuliang.getText());		//����
					item.add(danjia.getText());		//����
					double je = Double.parseDouble
							(shuliang.getText())*Double.parseDouble(danjia.getText());			
					item.add(textarea.getText());
					item.add(Calculat.Result(je));
					item.add("---");
					item.add(Calculat.getTime());
					dftm.addRow(item);
					sum = sum+je;
					updateZJ();
					mingcheng.setText("");
					shuliang.setText("");
					danjia.setText("");
					isChanged = true;
				}		
			}		
			
			
			// ���ð�ť���¼�������
			private final class RestButtonActionListener implements ActionListener
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{				
					mingcheng.setText("");
					shuliang.setText("");
					danjia.setText("");
					textarea.setText("");
				}		
			}	
			
			private class SHCActionListener implements ActionListener
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					//�ļ�ѡ����
					JFileChooser filechooser = new JFileChooser();
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
								 
								 String[] tableHeads = new String[]{"�ӹ���Ʒ" , "�ӹ�����"
											, "�ӹ�����"  , "��ע��Ϣ", "�ܼ�/Ԫ" , "������;" , "����"};
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
		
		public class YZ extends JPanel
		{
			JPanel ha = this;
			JTextField yz;
			JButton tj;
			//�ı���
			private JTextArea textarea;
			private JScrollPane panel;
			private Map map = new HashMap();
			
			public YZ()
			{
				setLayout(new GridBagLayout());
				setBounds(0, 0, 800, 600);
				
				
				this.setupComponet(new JLabel("Ԥ֧��"), 0, 0, 1, 0, false);
				this.yz = new JTextField();
				this.setupComponet(this.yz, 1, 0, 5, 400, true);
				
				
				this.textarea = new JTextArea();
				this.panel = new JScrollPane(textarea);
				this.textarea.setLineWrap(true);
				final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
				gridBagConstraints_6.weighty = 1.0;
				gridBagConstraints_6.insets = new Insets(0, 0, 0, 0);
				gridBagConstraints_6.fill = GridBagConstraints.BOTH;
				gridBagConstraints_6.gridwidth = 6;
				gridBagConstraints_6.gridy = 1;
				gridBagConstraints_6.gridx = 0;
				gridBagConstraints_6.ipady = 50;
				gridBagConstraints_6.ipadx = 400;
				gridBagConstraints_6.fill = GridBagConstraints.HORIZONTAL;
				add(panel, gridBagConstraints_6);
				
				this.tj = new JButton("���");
				this.tj.addActionListener(new TJActionListener());
				this.setupComponet(this.tj, 7, 1, 1, 30, false);
				
			}
			
			// �������λ�ò���ӵ�������
			private void setupComponet(JComponent component, int gridx, int gridy,
					int gridwidth, int ipadx, boolean fill)
			{
					final GridBagConstraints gridbagconstraints = new GridBagConstraints();
					gridbagconstraints.gridx = gridx;
					gridbagconstraints.gridy = gridy;
					gridbagconstraints.insets = new Insets(5 , 5 , 0 , 5);
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
			
			
			private class TJActionListener implements ActionListener
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					Vector item = new Vector();
					item.add("������Զ�����");
					item.add("");	//����
					item.add(0);		//����
					item.add(0);		//����
					item.add(textarea.getText());
					item.add(Calculat.Result(Double.parseDouble(yz.getText())));				
					item.add("Ԥ֧");
					dftm.addRow(item);
					sum1 =sum - Double.parseDouble(yz.getText());
					sum2 += Double.parseDouble(yz.getText());
					updateZJ();
					isChanged = true;
				}	
			}	
		}
		
		
		//ˢ�±��
		private void updateTable(List list, DefaultTableModel dftm)
		{		
			int num = dftm.getRowCount();
			for(int i = 0; i<num;i++)	//�����ǰ������
			{
				dftm.removeRow(0);
			}
			
			sum=0;
			sum2 =0;
			
			for(int i =0; i<list.size();i++)
			{
				Vector item = (Vector) list.get(i);
				
				if(item.get(6).toString().equals("Ԥ֧"))
				{
					sum = sum+Double.parseDouble(item.get(2).toString())
							*Double.parseDouble(item.get(3).toString());
					sum2 += Double.parseDouble(item.get(5).toString());
				}
				else
				{
					sum += Double.parseDouble(item.get(5).toString());
				}
				dftm.addRow(item);
			}
			updateZJ();
		}
		
		
		//ˢ���ܼ�
		private void updateZJ()
		{
			this.zongjia.setText("�ܹ���:  "+Calculat.Result(sum));
			this.shifu.setText("ʵ�ʹ���:  "+Calculat.Result(sum1));
			this.qianfu.setText("Ԥ������:  "+Calculat.Result(sum2));
		}	
		
		
		//��ȡ�������
		public void setTableDetail(List list)
		{
			this.list = list;
			this.count = list.size();
			this.updateTable(list, dftm);
		}
		
		
		//���ر������
		public Vector getDataVector()
		{
			return dftm.getDataVector();
		}
		
		
		//����Ƿ��б䶯
		public void checkChanged()
		{
			Vector v = this.getDataVector();
			this.count = v.size();
			for(int i = 0; i<v.size();i++)
			{
				Vector v1 = (Vector) v.get(i);
				System.out.println(v1);
				if(v1.get(0).toString().contains("������Զ�����"))
					this.add_list.add(v1);
			}
			System.out.println(this.isChanged);
		}
		
		
		//���ñ䶯
		public void setChanged()
		{
			this.add_list = new ArrayList();
			this.isChanged = false;
			
		}
		
		
		//��ȡ��Ҫ���µ�����
		public List get_add_list()
		{
			return this.add_list;
		}
		
		
		//����������
		public boolean inset_detail(int id_index) throws ClassNotFoundException, SQLException
		{
			return DB_Opreat.insert_jj_detail1(dftm.getDataVector(), id_index);
		}
		
		
		//ɾ��ѡ����
		private void remove_item(int id)
		{
			DB_Opreat.remove_item_sp(id , "t_xs_d");
		}
		
		
		private class DeleteListener implements ActionListener
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
						System.out.println(count);
						System.out.println(id);
						if(id <= count)
						{
							System.out.println(table.getValueAt(id, 0).toString());
							if(!table.getValueAt(id, 0).toString().equals("������Զ�����"))
							{
								remove_item(Integer.parseInt(table.getValueAt(id, 0).toString()));
							}
						}
						
						updateZJ();
						dftm.removeRow(id);
						count--;
					}
					else
					{
						JOptionPane.showMessageDialog(jpd
								, "��ѡ�к���ɾ����");
					}
				}
			}
		}	
	}
	
	//������н��ť
	private final class BCActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// TODO Auto-generated method stub
			tpd.checkChanged();
			if(tpd.isChanged)
			{
				//������ϸ��Ϣ
				DB_Opreat.update_xs_detail(tpd.get_add_list() , Id , sj.getSelectedItem().toString());
				tpd.setChanged();
				JOptionPane.showMessageDialog(jf
						, "�Ѿ�����,��ϸ��Ϣ��");
				tpd.setTableDetail(DB_Opreat.get_item_xs_detail1(Id , sj.getSelectedItem().toString()));
			}
		}	
	}	

}
