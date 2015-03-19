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
import sj.util.TbUser;
import dao.DB_Opreat;

public class ddgl extends JInternalFrame
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
	JButton shuchu_button;

	public ddgl()
	{
		this.setVisible(true);
		this.setTitle("订单管理");
		this.setBounds(0 , 0 , 750 , 400);
		this.setIconifiable(true);
		this.setClosable(true);
		this.setMaximizable(true);
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
					new String[]{"客户名称", "电话" , "经手人" , "完成状态"}));
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
			
			this.tuihuo = new JButton("退货/删除");	//退货按钮
			this.tuihuo.addActionListener(new THActionListener());
			this.setupComponet(this.tuihuo, 5, 0, 1, 20, true);
			//权限分配
			if((TbUser.getInstance().getQuan() ==-1 ) || (TbUser.getInstance().getQuan() > 0
					&& TbUser.getInstance().getQuan() <= 10))
			{
				
			}
			else
			{
				this.tuihuo.setEnabled(false);
			}
			
			this.bianji_button = new JButton("编辑选中项");
			this.bianji_button.addActionListener(new BJActionListener());
			this.setupComponet(this.bianji_button, 0, 1, 1, 10, true);
			
			this.shuchu_button = new JButton("输出");
			this.shuchu_button.addActionListener(new SHCActionListener());
			this.setupComponet(this.shuchu_button, 1, 1, 1, 0, true);
			
		}
		
		//详细列表
		private void createDetailPane()
		{
			table = new JTable();
			table.setEnabled(true);	
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//			this.setAutoscrolls(true);
//			this.table.getColumnModel().getColumn(5)
//				.setCellRenderer(table.getDefaultRenderer(Boolean.class));
//			this.table.getColumnModel().getColumn(5)
//				.setCellEditor(table.getDefaultEditor(Boolean.class));
			
			String[] tableHeads = new String[]{"订单编号" , "客户名称" , "联系电话" , "经手人" , "完成状态" , "日期"};
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
			
			this.updateTable(DB_Opreat.get_dd_main(), dftm);	//填充表格
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
					String v = item.get(4).toString();
					//item.remove(4);
					if(item.get(4).equals("true"))
					{
						item.set(4, "是");
					}
					else
					{
						item.set(4, "---");
					}
					dftm.addRow(item);
					//table.setValueAt(new Boolean(v), i, 5);
				}
			}
		
		
		//移除所有选中的订单信息
		public boolean RemoveAllSelectDD(int Id)
		{
			return DB_Opreat.RemoveAllSelectDD(Id);
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
					if(!GLXT_Frame.ifs.containsKey("ddck"))
					{
						ddck jc = new ddck();
						
						jc.addInternalFrameListener(new InternalFrameAdapter() 
						{
							public void internalFrameClosed(InternalFrameEvent e) 
							{
								GLXT_Frame.ifs.remove("ddck");
							}
						});
						
						GLXT_Frame.ifs.put("ddck", jc);
						GLXT_Frame.desktopPane.add(jc);
						try {
							jc.setSelected(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(jf
									, "窗口生成错误。");
							e1.printStackTrace();
						}
						
						jc.get_dd_all(Integer.parseInt(table.getValueAt(id, 0).toString()));
					}					
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
						if(RemoveAllSelectDD(Integer.parseInt(table.getValueAt(id, 0).toString())))
						{
							JOptionPane.showMessageDialog(jf
									, "成功删除进货单。");
						}
						dftm.removeRow(id);
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
				String sql = "select * from t_dingdan_main where ";
				if(!cx.equals(""))
				{
					if(zd.equals("客户名称"))
					{
						if(tj.equals("等于"))
						{
							list = DB_Opreat.query_dd_main_equals(sql+"dd_name = '"+cx+"'");
						}
						if(tj.equals("包含"))
						{
							list = DB_Opreat.query_dd_main_contains(sql+"dd_name like '%"+cx+"%'");
						}
					}
					
					if(zd.equals("电话"))
					{
						if(tj.equals("等于"))
						{
							list = DB_Opreat.query_dd_main_equals(sql+"dd_phone = '"+cx+"'");
						}
						if(tj.equals("包含"))
						{
							list = DB_Opreat.query_dd_main_contains(sql+"dd_phone like '%"+cx+"%'");
						}
					}
					
					if(zd.equals("经手人"))
					{
						if(tj.equals("等于"))
						{
							list = DB_Opreat.query_dd_main_equals(sql+"dd_jsr = '"+cx+"'");
						}
						if(tj.equals("包含"))
						{
							list = DB_Opreat.query_dd_main_contains(sql+"dd_jsr like '%"+cx+"%'");
						}
					}
					
					if(zd.equals("完成状态"))
					{
						if(cx.contains("是"))
						{
							cx = "true";
						}
						if(cx.contains("-")||cx.contains("否")
								||cx.contains("未完成"))
						{
							cx = "false";
						}
						
						if(tj.equals("等于"))
						{
							list = DB_Opreat.query_dd_main_equals(sql+"dd_isfinish = '"+cx+"'");
						}
						if(tj.equals("包含"))
						{
							list = DB_Opreat.query_dd_main_contains(sql+"dd_isfinish like '%"+cx+"%'");
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
				//文件选择器
				JFileChooser filechooser = new JFileChooser();
				filechooser.setDialogType(JFileChooser.SAVE_DIALOG);
				int option = filechooser.showDialog(null, null);
				switch(option)
				{
					case JFileChooser.APPROVE_OPTION:
					{
						//设置保存时文件名
						try 
						{
							WritableWorkbook book = Workbook
									.createWorkbook(new File(filechooser.getSelectedFile().toString()+".xls"));
							
							 WritableSheet sheet = book.createSheet("第一页", 0);
							 
							 String[] tableHeads = new String[]{"客户名称" , "联系电话" 
									 , "经手人" , "完成状态" , "日期"};
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
