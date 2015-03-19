package sj.panel.gongzi;


import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import sj.util.Calculat;
import sj.util.Item;
import sj.util.TbUser;

import dao.DB_Opreat;

public class Tjxx_Panel_detail extends JPanel
{

	JPanel jpd = this;
	JTable table = new JTable();	//详细信息列表
	DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
	DefaultTableModel dftm;
	private List<Vector> list;		//原来的数据项
	private List<Vector> temp_list;		//中间变量
	private List<Vector> add_list = new ArrayList();	//新添加的数据项
	public boolean isChanged = false;
	int count = -1;		//原来的商品记录数量
	JTextField zongjia;		//商品总额
	JTextField shifu;		//商品实付
	JTextField qianfu;		//商品欠付
	double sum = 0.0;
	double sum1 = 0.0;
	double sum2 = 0.0;
	int yf_id;
	
	
	public Tjxx_Panel_detail()
	{
		super();
		this.setEnabled(false);
		this.setBounds(0 , 100 , 400 , 0);
		this.setLayout(new BorderLayout());
		
		
		this.add(this.createDetailPane()  , BorderLayout.CENTER);
		JButton del = new JButton("删除选中项");
		del.addActionListener(new DeleteListener());
		this.add(del , BorderLayout.EAST);
		
		
		//权限分配
		if((TbUser.getInstance().getQuan() ==-1 ) || (TbUser.getInstance().getQuan() > 10
				&& TbUser.getInstance().getQuan() <= 20))
		{
			this.add(this.createAddDetail() , BorderLayout.SOUTH);
		}
		else
		{
			del.setEnabled(false);
		}				
	}
	
	
	//显示已经添加的信息
	public JScrollPane createDetailPane()
	{
		table = new JTable();
		table.setEnabled(true);	
		tcr.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, tcr);	//设置填充文字居中
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.table.setAutoscrolls(true);
		table.setRowHeight(20);
		table.setFont(new Font("隶书" , Font.BOLD, 15));
		
		String[] tableHeads = new String[]{"编号" , "加工物品" , "加工数量"
				, "加工单价"  , "备注信息", "总价/元" , "工资用途" , "日期"};
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
					//更新详细信息
					Vector v = getDataVector();
					List list = new ArrayList();
					for(int i=0;i<v.size();i++)
					{
						Vector v1 = (Vector) v.get(i);
						v1.get(0);
						
						if(v1.get(0).toString().equals(table.getValueAt(e.getLastRow(),0).toString()))
						{			
							list.add(v1);
							DB_Opreat.update_xs_detail_1(list , table.getValueAt(e.getLastRow(),0).toString());
							System.out.println("成功");
							break;
						}
					}				
				}				
			}});
				
		JScrollPane pane = new JScrollPane (table);
		return pane;
	}
	
	
	//添加详细信息栏
	public JTabbedPane createAddDetail()
	{
		AddDetail newpanel = new AddDetail();
		YZ yz = new YZ();
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.addTab("正常发放", null, newpanel, "正常发放");
		tabPane.addTab("预支工资", null, yz, "预支工资");
		return tabPane;
	}

	class AddDetail extends JPanel
	{
		JTextField mingcheng;
		JTextField shuliang;
		JTextField danjia;
		JButton add;
		JButton reset;
		//文本域
		private JTextArea textarea;
		private JScrollPane panel;
		AddDetail()
		{
			super();
			setLayout(new GridBagLayout());
			this.setBorder(new BevelBorder(BevelBorder.LOWERED));
			
			//商品名称
			this.setupComponet(new JLabel("加工物品：") , 0 , 0 , 1 , 0 , true);
			this.mingcheng = new JTextField();
			this.setupComponet(this.mingcheng, 1, 0, 1, 100, true);
			
			//商品数量
			this.setupComponet(new JLabel("数量：") , 2 , 0 , 1 , 0 , true);
			this.shuliang = new JTextField();
			this.setupComponet(this.shuliang, 3, 0, 1, 100, true);
			
			//商品单价
			this.setupComponet(new JLabel("单价：") , 4 , 0 , 1 , 0 , true);
			this.danjia = new JTextField();
			this.setupComponet(this.danjia, 5, 0, 1, 100, true);
			
			//文本域控件
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
			
						
			//总价显示
			zongjia = new JTextField();
			zongjia.setEditable(false);	
			zongjia.setFont(new Font("隶书" , Font.BOLD, 14));
			this.setupComponet(zongjia, 0, 3, 2, 100, true);
			
			//实付显示
			shifu = new JTextField();
			shifu.setEditable(false);
			shifu.setFont(new Font("隶书" , Font.BOLD, 14));
			this.setupComponet(shifu, 2, 3, 2, 100, true);
			
			//欠付显示
			qianfu = new JTextField();
			qianfu.setEditable(false);
			qianfu.setFont(new Font("隶书" , Font.BOLD, 14));
			this.setupComponet(qianfu, 4, 3, 3, 100, true);
			
			updateZJ();
			
			//添加按钮
			this.add = new JButton("添加记录");
			this.setupComponet(this.add, 2, 2, 3, 0, false);
			this.add.addActionListener(new AddButtonActionListener());
			//重置按钮
			this.reset = new JButton("重置");
			this.setupComponet(this.reset, 5, 2, 1, 0, false);
			this.reset.addActionListener(new RestButtonActionListener());
			
		}		
				
		// 设置组件位置并添加到容器中
		private void setupComponet(JComponent component, int gridx, int gridy,
				int gridwidth, int ipadx, boolean fill)
		{
				final GridBagConstraints gridbagconstraints = new GridBagConstraints();
				gridbagconstraints.gridx = gridx;
				gridbagconstraints.gridy = gridy;
				gridbagconstraints.insets = new Insets(5 , 5 , 0 , 5);
				if(gridwidth > 1)	//如果占用单元格大于一个就改变，否则就用一个
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
		
		
		// 添加按钮的事件监听类
		private final class AddButtonActionListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{		
				Vector item = new Vector();
				item.add("保存后自动生成");
				item.add(mingcheng.getText());	//名称
				item.add(shuliang.getText());		//数量
				item.add(danjia.getText());		//单价
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
		
		
		// 重置按钮的事件监听类
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
	}	
	
	public class YZ extends JPanel
	{
		JPanel ha = this;
		JTextField yz;
		JButton tj;
		//文本域
		private JTextArea textarea;
		private JScrollPane panel;
		private Map map = new HashMap();
		
		public YZ()
		{
			setLayout(new GridBagLayout());
			setBounds(0, 0, 800, 600);
			
			
			this.setupComponet(new JLabel("预支金额："), 0, 0, 1, 0, false);
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
			
			this.tj = new JButton("添加");
			this.tj.addActionListener(new TJActionListener());
			this.setupComponet(this.tj, 7, 1, 1, 30, false);
			
		}
		
		// 设置组件位置并添加到容器中
		private void setupComponet(JComponent component, int gridx, int gridy,
				int gridwidth, int ipadx, boolean fill)
		{
				final GridBagConstraints gridbagconstraints = new GridBagConstraints();
				gridbagconstraints.gridx = gridx;
				gridbagconstraints.gridy = gridy;
				gridbagconstraints.insets = new Insets(5 , 5 , 0 , 5);
				if(gridwidth > 1)	//如果占用单元格大于一个就改变，否则就用一个
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
				item.add("保存后自动生成");
				item.add("");	//名称
				item.add(0);		//数量
				item.add(0);		//单价
				item.add(textarea.getText());
				item.add(Calculat.Result(Double.parseDouble(yz.getText())));				
				item.add("预支");
				dftm.addRow(item);
				sum1 =sum - Double.parseDouble(yz.getText());
				sum2 += Double.parseDouble(yz.getText());
				updateZJ();
				isChanged = true;
			}	
		}	
	}
	
	
	//刷新表格
	private void updateTable(List list, DefaultTableModel dftm)
	{		
		int num = dftm.getRowCount();
		for(int i = 0; i<num;i++)	//清楚以前的数据
		{
			dftm.removeRow(0);
		}
		
		sum=0;
		sum2 =0;
		
		for(int i =0; i<list.size();i++)
		{
			Vector item = (Vector) list.get(i);
			
			if(item.get(6).toString().equals("预支"))
			{
				System.out.println(item.get(2).toString()+"盘文静");
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
	
	
	//刷新总价
	private void updateZJ()
	{
		this.zongjia.setText("总工资:  "+Calculat.Result(sum));
		this.shifu.setText("实际工资:  "+Calculat.Result(sum1));
		this.qianfu.setText("预付工资:  "+Calculat.Result(sum2));
	}	
	
	
	//获取表格数据
	public void setTableDetail(List list , int id)
	{
		this.yf_id = id;
		this.list = list;
		this.count = list.size();
		this.updateTable(list, dftm);
	}
	
	
	//返回表格数据
	public Vector getDataVector()
	{
		return dftm.getDataVector();
	}
	
	
	//检查是否有变动
	public void checkChanged()
	{
		Vector v = this.getDataVector();
		this.count = v.size();
		for(int i = 0; i<v.size();i++)
		{
			Vector v1 = (Vector) v.get(i);
			System.out.println(v1);
			if(v1.get(0).toString().contains("保存后自动生成"))
				this.add_list.add(v1);
		}
		System.out.println(this.isChanged);
	}
	
	
	//设置变动
	public void setChanged()
	{
		this.add_list = new ArrayList();
		this.isChanged = false;
		
	}
	
	
	//获取需要更新的数据
	public List get_add_list()
	{
		return this.add_list;
	}
	
	
	//插入表格数据
	public boolean inset_detail(int id_index) throws ClassNotFoundException, SQLException
	{
		return DB_Opreat.insert_jj_detail1(dftm.getDataVector(), id_index);
	}
	
	
	//删除选中项
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
					"删除后数据将不可恢复，你真的要删除吗？", "确认删除",
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
						if(!table.getValueAt(id, 0).toString().equals("保存后自动生成"))
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
							, "请选中后再删除。");
				}
			}
		}
	}
	
}
