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
 * 个人工资查看
 */
public class grgzck_p extends JInternalFrame
{
	JInternalFrame jf = this;
	boolean isChanged = false;
	JTable table = new JTable();	//详细信息列表
	DefaultTableModel dftm;
	JButton tuihuo;
	JButton chaxun_button;	//查询按钮
	JButton bianji_button;	//编辑选中项按钮
	JButton shuchu_button;
	JButton tyx;
	JButton tjsj_button;	//添加时间按钮
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
		this.setTitle("个人工资查看");
		this.setBounds(200 , 0 , 750 , 400);
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
		this.xm = new JTextField();
		this.xm.setEditable(false);
		this.setupComponet(this.xm, 0, 0, 1, 100, true);
		this.dh = new JTextField();
		this.dh.setEditable(false);
		this.setupComponet(this.dh, 1, 0, 1, 100, true);
		this.zw = new JTextField();
		this.zw.setEditable(false);
		this.setupComponet(this.zw, 2, 0, 2, 100, true);
		
		this.tuihuo = new JButton("删除工资信息");	//退货按钮
		this.tuihuo.addActionListener(new THActionListener());
		this.setupComponet(this.tuihuo, 4, 0, 1, 0, true);			
		
		this.setupComponet(new JLabel("日期查询    从"), 0, 1, 1, 0, true);
		this.from = new JTextField();
		this.from.setText("2014-01");
		this.setupComponet(this.from, 1, 1, 1, 100, true);
		this.setupComponet(new JLabel("到"), 2, 1, 1, 0, true);
		this.to = new JTextField();
		this.to.setText("2014-02");
		this.setupComponet(this.to, 3 , 1, 1, 100, true);
		
		this.chaxun_button = new JButton("查询");		//查询按钮
		this.chaxun_button.addActionListener(new CXActionListener());
		this.setupComponet(this.chaxun_button, 4, 1, 1, 0, false);
		
		this.bianji_button = new JButton("编辑选中项");
		this.bianji_button.addActionListener(new BJActionListener());
		this.setupComponet(this.bianji_button, 0, 2, 1, 10, true);
		
		this.tyx = new JButton("添加本月记录");
		this.tyx.addActionListener(new TYActionListener());
//		this.setupComponet(this.tyx, 1, 2, 1, 0, true);
		
		this.tjsj_button = new JButton("添加月份记录");
		this.tjsj_button.addActionListener(new TYOActionListener());
		this.setupComponet(this.tjsj_button, 1, 2, 1, 0, true);
		
		this.tjsj = new JTextField("2014-01");
		this.setupComponet(this.tjsj, 2, 2, 2, 0, true);
		
		this.shuchu_button = new JButton("输出");
		this.shuchu_button.addActionListener(new SHCActionListener());
		this.setupComponet(this.shuchu_button, 4, 2, 1, 0, true);
		
		//权限分配
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
	
	//详细列表
	private void createDetailPane()
	{
		table = new JTable();
		table.setEnabled(true);	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setAutoscrolls(true);
		
		String[] tableHeads = new String[]{"编号" , "日期" , "总工资" , "实际工资" , "预支工资" };
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
	
	public void setId(String Id)
	{
		this.Id = Id;
		List list = DB_Opreat.query_item_yg(Id);
		Person person = (Person) list.get(0);
		this.xm.setText(person.getXm());
		this.dh.setText(person.getDh());
		this.zw.setText(person.getZw());
		this.updateTable(DB_Opreat.get_yx_main(Id), dftm);	//填充表格
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
	
	
	//移除所有选中的订单信息
	public boolean RemoveAllSelectXX(int Id)
	{
		return DB_Opreat.RemoveAllSelectXX(Id);
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
								, "窗口生成错误。");
						e1.printStackTrace();
					}
					
					Map info = new HashMap();
					info.put("Id", table.getValueAt(id, 0).toString());
					info.put("姓名", xm.getText());
					info.put("电话", dh.getText());
					info.put("职务", zw.getText());
					jc.setBase(info);
				}													
			}
			else
			{
				JOptionPane.showMessageDialog(jf
						, "请选择需要编辑的记录。");
			}
		}		
	}
	
	//添加月份记录
	private class TYActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(DB_Opreat.insert_yx(Id))
			{
				updateTable(DB_Opreat.get_yx_main(Id), dftm);	//填充表格
				JOptionPane.showMessageDialog(jf
						, "成功添加。");
			}
			else
			{
				JOptionPane.showMessageDialog(jf
						, "失败。");
			}
		}		
	}
	
	//添加其他月份记录
	private class TYOActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(DB_Opreat.insert_yx(Id , tjsj.getText()))
			{
				updateTable(DB_Opreat.get_yx_main(Id), dftm);	//填充表格
				JOptionPane.showMessageDialog(jf
						, "成功添加。");
			}
			else
			{
				JOptionPane.showMessageDialog(jf
						, "失败。");
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
					if(RemoveAllSelectXX(Integer.parseInt(table.getValueAt(id, 0).toString())))
					{
						JOptionPane.showMessageDialog(jf
								, "成功删除记录。");
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
						 
						 String[] tableHeads = 
								 new String[]{"日期" , "总工资" , "实际工资" , "预支工资" };
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
