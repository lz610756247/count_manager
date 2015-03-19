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
	JTable table = new JTable();	//详细信息列表
	DefaultTableModel dftm;
	JButton tuihuo;
	JComboBox chaxunziduan;		//查询字段
	JComboBox chaxuntiaojian;		//查询条件
	JTextField chaxun_txt;		//查询文本框
	JButton chaxun_button;	//查询按钮
	JButton bianji_button;	//编辑选中项按钮
	
	public kcck()
	{
		this.setVisible(true);
		this.setTitle("商品库存查看");
		this.setBounds(0 , 0 , 600 , 400);
		this.setIconifiable(true);
		this.setClosable(true);
		this.setMaximizable(false);
		this.setLayout(new GridBagLayout());
		
		this.createQueryPanel();	//插叙面板
		
		this.createDetailPane();	//详细信息列表
		
		this.pack();
	}
	
	//查询面板
		private void createQueryPanel()
		{
			chaxunziduan = new JComboBox();		//查询字段
			chaxunziduan.setModel(new DefaultComboBoxModel(
					new String[]{"商品名称" , "规格/说明"}));
			this.setupComponet(chaxunziduan, 0, 0, 1, 30, false);
			
			
			this.chaxuntiaojian = new JComboBox();		//查询条件
			chaxuntiaojian.setModel(new DefaultComboBoxModel(
					new String[]{"等于", "包含"}));
			this.setupComponet(this.chaxuntiaojian, 1, 0, 1, 20, false);
			
			this.chaxun_txt = new JTextField();		//查询文本框
			this.setupComponet(this.chaxun_txt, 2, 0, 2, 200, true);
			
			this.chaxun_button = new JButton("查询");		//查询按钮
			this.chaxun_button.addActionListener(new CXActionListener());
			this.setupComponet(this.chaxun_button, 4, 0, 1, 0, false);
			
			this.tuihuo = new JButton("删除");	//退货按钮
			this.tuihuo.addActionListener(new THActionListener());
			this.setupComponet(this.tuihuo, 5, 0, 1, 20, true);
			
			this.bianji_button = new JButton("编辑选中项");
			this.bianji_button.addActionListener(new BJActionListener());
			this.setupComponet(this.bianji_button, 0, 1, 1, 10, true);
			
		}
		
		//详细列表
		private void createDetailPane()
		{
			table = new JTable();
			table.setEnabled(true);	
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			this.setAutoscrolls(true);
			
			String[] tableHeads = new String[]{"编号" , "商品名称" , "数量" , "单位" , "单价" , "总价" , "规格/说明"};
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
			
			this.updateTable(DB_Opreat.query_spkc(), dftm);	//填充表格
		}
		
		
		// 设置组件位置并添加到容器中
		private void setupComponet(JComponent component, int gridx, int gridy,
				int gridwidth, int ipadx, boolean fill)
		{
				final GridBagConstraints gridbagconstraints = new GridBagConstraints();
				gridbagconstraints.gridx = gridx;
				gridbagconstraints.gridy = gridy;
				gridbagconstraints.insets = new Insets(5 , 5 , 5 , 5);
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
		
		
		//刷新表格
		private void updateTable(List list, DefaultTableModel dftm)
		{		
			int num = dftm.getRowCount();
			for(int i = 0; i<num;i++)	//清楚以前的数据
			{
				dftm.removeRow(0);
			}
					
			for(int i =0; i<list.size();i++)
			{
				Vector item = (Vector) list.get(i);
				dftm.addRow(item);
			}
		}
		
		//编辑按钮动作
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
								, "窗口生成错误。");
						e1.printStackTrace();
					}
					GLXT_Frame.desktopPane.add(sp);
					sp.get_sp_item(table.getValueAt(id, 0).toString());
				}
				else
				{
					JOptionPane.showMessageDialog(jf
							, "请选择需要编辑的进货单。");
				}
			}		
		}
		
		
		//退货按钮动作
		private final class THActionListener implements ActionListener
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
//						if(RemoveAllSelectDD(Integer.parseInt(table.getValueAt(id, 0).toString())))
//						{
//							JOptionPane.showMessageDialog(jf
//									, "成功删除进货单。");
//						}
//						dftm.removeRow(id);
					}
					else
					{
						JOptionPane.showMessageDialog(jf
								, "请选中后再删除。");
					}
				}		
			}		
		}
		
		
		//查询按钮动作
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
					if(zd.equals("商品名称"))
					{
						if(tj.equals("等于"))
						{
							list = DB_Opreat.query_sp_equals(sql+"sp_name = '"+cx+"'");
						}
						if(tj.equals("包含"))
						{
							list = DB_Opreat.query_sp_contains(sql+"sp_name like '%"+cx+"%'");
						}
					}
					
					if(zd.equals("规格/说明"))
					{
						if(tj.equals("等于"))
						{
							list = DB_Opreat.query_sp_equals(sql+"sp_bz = '"+cx+"'");
						}
						if(tj.equals("包含"))
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