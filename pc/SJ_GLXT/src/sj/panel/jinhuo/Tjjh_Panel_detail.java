package sj.panel.jinhuo;

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

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import sj.panel.jinhuo.Jhck_Panel_detail.AddDetail;
import sj.util.Calculat;
import sj.util.Item;

import dao.DB_Opreat;

public class Tjjh_Panel_detail extends JPanel
{

	JPanel tpd = this;
	JTable table = new JTable();	//详细信息列表
	DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
	DefaultTableModel dftm;
	JTextField zongjia;		//商品总额
	JTextField shifu;		//商品实付
	JTextField qianfu;		//商品欠付
	double sum = 0.0;
	double sum1 = 0.0;
	double sum2 = 0.0;
	
	
	public Tjjh_Panel_detail()
	{
		super();
		this.setLayout(new BorderLayout());
		
		
		this.add(this.createDetailPane()  , BorderLayout.CENTER);
		JButton del = new JButton("删除选中项");
		del.addActionListener(new DeleteListener());
		this.add(del , BorderLayout.EAST);
		this.add(this.createAddDetail() , BorderLayout.SOUTH);
	}
	
	
	//显示已经添加的信息
	public JScrollPane createDetailPane()
	{
		table = new JTable();
		table.setEnabled(true);	
		table.setSize(300, 100);
		tcr.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, tcr);	//设置填充文字居中
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.table.setAutoscrolls(true);
		table.setRowHeight(20);
		table.setFont(new Font("隶书" , Font.BOLD, 15));
		
		String[] tableHeads = new String[]{"商品名称" , "商品数量" , "单位" 
				, "商品单价" , "备注信息" , "总价/元" , "实付金额" , "欠付金额" };
		dftm = (DefaultTableModel) table.getModel();
		dftm.setColumnIdentifiers(tableHeads);
				
		JScrollPane pane = new JScrollPane (table);
		pane.setSize(300, 100);
		return pane;
	}
	
	
	//添加详细信息栏
	public JPanel createAddDetail()
	{
		AddDetail newpanel = new AddDetail();
		return newpanel;
	}	
	
	class AddDetail extends JPanel
	{
		JTextField mingcheng;
		JTextField shuliang;
		JTextField danjia;
		JTextField shifu_tf;
		JComboBox danwei;
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
			this.setupComponet(new JLabel("商品名称：") , 0 , 0 , 1 , 0 , true);
			this.mingcheng = new JTextField();
			this.setupComponet(this.mingcheng, 1, 0, 4, 300, true);
			//商品数量
			this.setupComponet(new JLabel("数量：") , 5 , 0 , 1 , 0 , false);
			this.shuliang = new JTextField();
			this.setupComponet(this.shuliang, 6, 0, 1, 120, true);
			//商品单位
			this.danwei = new JComboBox();
			danwei.setModel(new DefaultComboBoxModel(new String[]{"方量",
			"件"}));
			this.setupComponet(this.danwei, 7, 0, 1, 10, false);
			//商品单价
			this.setupComponet(new JLabel("单价：") , 8 , 0 , 1 , 0 , false);
			this.danjia = new JTextField();
			this.setupComponet(this.danjia, 9, 0, 1, 120, true);
			
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
			
			//实付金额
			this.setupComponet(new JLabel("实付金额:"), 0, 2, 1, 0, true);
			shifu_tf = new JTextField();
			this.setupComponet(this.shifu_tf, 1, 2, 1, 120, true);
						
			//总价显示
			zongjia = new JTextField();
			zongjia.setEditable(false);	
			zongjia.setFont(new Font("隶书" , Font.BOLD, 14));
			this.setupComponet(zongjia, 0, 4, 2, 100, true);
			
			//实付显示
			shifu = new JTextField();
			shifu.setEditable(false);
			shifu.setFont(new Font("隶书" , Font.BOLD, 14));
			this.setupComponet(shifu, 4, 4, 2, 0, true);
			
			//欠付显示
			qianfu = new JTextField();
			qianfu.setEditable(false);
			qianfu.setFont(new Font("隶书" , Font.BOLD, 14));
			this.setupComponet(qianfu, 6, 4, 3, 0, true);
			
			updateZJ();
			
			//添加按钮
			this.add = new JButton("添加商品记录");
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
				gridbagconstraints.insets = new Insets(5 , 5 , 3 , 5);
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
				item.add(mingcheng.getText());	//名称
				item.add(shuliang.getText());		//数量
				item.add(danwei.getSelectedItem().toString());	//	单位
				item.add(danjia.getText());		//单价
				double je = Double.parseDouble(shuliang.getText())*Double.parseDouble(danjia.getText());
				double sf = Double.parseDouble(shifu_tf.getText());
				item.add(textarea.getText());
				item.add(Calculat.Result(je));
				item.add(Calculat.Result(sf));
				item.add(Calculat.Result(je-sf));
				dftm.addRow(item);
				sum = sum+je;
				sum1 = sum1+sf;
				sum2 = sum-sum1;
				updateZJ();
				mingcheng.setText("");
				shuliang.setText("");
				danjia.setText("");
				shifu_tf.setText("");
				textarea.setText("");
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
	
	public class Have_kc_jh extends JPanel
	{
		JPanel ha = this;
		JComboBox spmc_cb;
		JTextField Id_t;
		JTextField spmc;
		JTextField sl;
		JComboBox dw;
		JTextField dj;
		JButton tj;
		//文本域
		private JTextArea textarea;
		private JScrollPane panel;
		private Map map = new HashMap();
		
		public Have_kc_jh()
		{
			setLayout(new GridBagLayout());
			setBounds(0, 0, 800, 600);
			
			this.Id_t = new JTextField();
			this.Id_t.setVisible(false);
			this.setupComponet(this.Id_t, 0, 0, 8, 0, true);
			
			this.initComboBox();
			this.setupComponet(this.spmc_cb, 0, 0, 7, 400, true);
			this.spmc_cb.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					// TODO Auto-generated method stub
					doKeHuSelectAction();
				}		
			});
			
			this.setupComponet(new JLabel("商品名称："), 0, 1, 1, 0, false);
			this.spmc = new JTextField();
			this.setupComponet(this.spmc, 1, 1, 5, 400, true);
			
			this.setupComponet(new JLabel("数    量："), 0, 2, 1, 0, false);
			this.sl = new JTextField();
			this.setupComponet(this.sl, 1, 2, 1, 100, true);
			
			this.dw = new JComboBox();
			dw.setModel(new DefaultComboBoxModel(new String[]{"吨",
			"件"}));
			this.setupComponet(new JLabel("单位："), 2, 2, 1, 0, false);
			this.setupComponet(this.dw, 3, 2, 1, 10, true);
			
			this.setupComponet(new JLabel("单价："), 4, 2, 1, 0, false);
			this.dj = new JTextField();
			this.setupComponet(this.dj, 5, 2, 1, 30, true);
			
			this.textarea = new JTextArea();
			this.panel = new JScrollPane(textarea);
			this.textarea.setLineWrap(true);
			final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
			gridBagConstraints_6.weighty = 1.0;
			gridBagConstraints_6.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints_6.fill = GridBagConstraints.BOTH;
			gridBagConstraints_6.gridwidth = 6;
			gridBagConstraints_6.gridy = 3;
			gridBagConstraints_6.gridx = 0;
			gridBagConstraints_6.ipady = 50;
			gridBagConstraints_6.ipadx = 400;
			gridBagConstraints_6.fill = GridBagConstraints.HORIZONTAL;
			add(panel, gridBagConstraints_6);
			
			this.tj = new JButton("添加");
			this.tj.addActionListener(new TJActionListener());
			this.setupComponet(this.tj, 7, 3, 1, 30, false);
			
			
			doKeHuSelectAction();
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
		
		//组织下拉列表
		public void initComboBox()
		{
			if(this.spmc_cb == null)
			{
				this.spmc_cb = new JComboBox();
				spmc_cb.removeAllItems();
				List list = DB_Opreat.query_all_jj_item();
				System.out.println(list);
				for(int i=0;i<list.size();i++)
				{
					Item item = (Item)list.get(i);
					map.put(item.getId(), item);
					spmc_cb.addItem(item.getId()+","+item.getSpmc());
				}
			}
			else
			{
				doKeHuSelectAction();
			}
		}
		
		private void doKeHuSelectAction() 
		{
			String Id = this.spmc_cb.getSelectedItem().toString()
					.substring(0 , this.spmc_cb.getSelectedItem().toString().lastIndexOf(","));
			Item item = (Item) this.map.get(Id);
			this.Id_t.setText(item.getId());
			this.spmc.setText(item.getSpmc());
			this.sl.setText("0");
			this.dj.setText(item.getDj());
			this.textarea.setText(item.getBz());
			
		}
		
		private class TJActionListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Vector item = new Vector();
				item.add(spmc.getText());	//名称
				item.add(sl.getText());		//数量
				item.add(dw.getSelectedItem().toString());	//	单位
				item.add(dj.getText());		//单价
				double je = Double.parseDouble(sl.getText())*Double.parseDouble(dj.getText());
				item.add(Calculat.Result(je));
				item.add(textarea.getText());
				item.add("有");
				dftm.addRow(item);
				sum = sum+je;
				updateZJ();
			}	
		}	
	}
	
	
	//刷新表格
	private void updateTable(List list, DefaultTableModel dftm)
	{
		
	}
	
	
	//刷新总价
	private void updateZJ()
	{
		this.zongjia.setText("应付合计:  "+Calculat.Result(sum));
		this.shifu.setText("实付合计:  "+Calculat.Result(sum1));
		this.qianfu.setText("欠付合计:  "+Calculat.Result(sum2));
	}
	
	
	//返回表格数据
	public Vector getDataVector()
	{
		return dftm.getDataVector();
	}
	
	
	//插入表格数据
	public boolean inset_detail(int id_index) throws ClassNotFoundException, SQLException
	{
		return DB_Opreat.insert_jj_detail(dftm.getDataVector(), id_index);
	}
	
	
	private class DeleteListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int id = table.getSelectedRow();
			if(id > -1)
			{
				sum = sum-Double.parseDouble(table.getValueAt(id, 3).toString())
						*Double.parseDouble(table.getValueAt(id, 1).toString());
				sum1 = sum1-Calculat.Result1(table.getValueAt(id, 6).toString());
				sum2 = sum-sum1;
				updateZJ();
				dftm.removeRow(id);				
			}
			else
			{
				JOptionPane.showMessageDialog(tpd
						, "请选中后再删除。");
			}
		}

	}
	
}
