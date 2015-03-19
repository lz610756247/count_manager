package sj.panel.gongzi;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sj.util.Item;
import sj.util.Person;
import dao.DB_Opreat;

public class Glyg_Panel_xgyg extends JPanel
{
	JPanel jp = this;
	JComboBox xm_cb;
	JTextField Id;
	JTextField xm;
	JTextField mm;
	JTextField dh;
	JTextField zz;
	JComboBox zw;
	//文本域
	private JTextArea textarea;
	private JScrollPane panel;
	
	JButton xg;
	JButton sc;
	private Map map = new HashMap();
	
	public Glyg_Panel_xgyg()
	{
		super();
		setLayout(new GridBagLayout());
		
		this.Id = new JTextField();
		this.Id.setVisible(false);
		this.add(Id);
		
		this.initComboBox();
		this.setupComponet(this.xm_cb, 0, 0, 6, 0, true);
		this.xm_cb.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				doKeHuSelectAction();
			}		
		});
		
		this.setupComponet(new JLabel("姓名："), 0, 1, 1, 0, true);
		this.xm = new JTextField();
		this.setupComponet(this.xm, 1, 1, 1, 100, true);
		
		this.setupComponet(new JLabel("密码："), 2, 1, 1, 0, true);
		this.mm = new JTextField();
		this.setupComponet(this.mm, 3, 1, 1, 100, true);
		
		this.setupComponet(new JLabel("职务："), 4, 1, 1, 0, true);
		initComboBox_1();	
		this.setupComponet(this.zw, 5, 1, 1, 100, true);
		
		this.setupComponet(new JLabel("电话："), 0, 2, 1, 0, true);
		this.dh = new JTextField();
		this.setupComponet(this.dh, 1, 2, 5, 0, true);
		
		this.setupComponet(new JLabel("住址："), 0, 3, 1, 0, true);
		this.zz = new JTextField();
		this.setupComponet(this.zz, 1, 3, 5, 0, true);
		
		this.setupComponet(new JLabel("备注信息"), 0, 4, 1, 0, true);
		
		//文本域控件
		this.textarea = new JTextArea();
		this.panel = new JScrollPane(textarea);
		this.textarea.setLineWrap(true);
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.weighty = 1.0;
		gridBagConstraints_6.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints_6.fill = GridBagConstraints.BOTH;
		gridBagConstraints_6.gridwidth = 6;
		gridBagConstraints_6.gridy = 5;
		gridBagConstraints_6.gridx = 0;
		gridBagConstraints_6.ipady = 50;
		gridBagConstraints_6.ipadx = 0;
		gridBagConstraints_6.fill = GridBagConstraints.HORIZONTAL;
		add(panel, gridBagConstraints_6);	
		
		this.xg = new JButton("修改");
		this.xg.addActionListener(new XGActionListener());
		this.setupComponet(this.xg, 1, 6, 1, 0, true);
		
		this.sc = new JButton("删除");
		this.sc.addActionListener(new SCActionListener());
		this.setupComponet(this.sc, 2, 6, 1, 0, true);
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
	
	//组织下拉列表
	public void initComboBox()
	{
		if(this.xm_cb == null)
		{
			this.xm_cb = new JComboBox();
			xm_cb.removeAllItems();
			List list = DB_Opreat.query_all_yg();
			System.out.println(list);
			for(int i=0;i<list.size();i++)
			{
				Person person = (Person)list.get(i);
				map.put(person.getId(), person);
				xm_cb.addItem(person.getId()+","+person.getXm());
			}
		}
		else
		{
			doKeHuSelectAction();
		}
	}
	
	//组织下拉列表
	public void initComboBox_1()
	{
		this.zw = new JComboBox();
		zw.removeAllItems();
		zw.addItem("普通员工");	
		zw.addItem("业务管理员");	
		zw.addItem("工资管理员");	
		zw.addItem("系统管理员");
	}		
	
	private void doKeHuSelectAction() 
	{
		String Id = this.xm_cb.getSelectedItem().toString()
				.substring(0 , this.xm_cb.getSelectedItem().toString().lastIndexOf(","));
		Person person = DB_Opreat.query_all_yg_by_Id(Id);
		this.Id.setText(person.getId());
		this.xm.setText(person.getXm());
		this.mm.setText(person.getPw());
		this.dh.setText(person.getDh());
		this.zz.setText(person.getZz());
		this.zw.setSelectedItem(person.getZw());
		this.textarea.setText(person.getBz());
		
	}
	
	public Map getInfo()
	{
		Map map = new HashMap();
		map.put("Id", this.Id.getText());
		map.put("姓名", this.xm.getText());
		map.put("密码", this.mm.getText());
		map.put("电话", this.dh.getText());
		map.put("职务", this.zw.getSelectedItem().toString());
		map.put("住址", this.zz.getText());
		map.put("备注", this.textarea.getText());
		return map;
	}	
	
	private class XGActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(DB_Opreat.Update_yg(getInfo()))
			{
				JOptionPane.showMessageDialog(jp
						, "成功更新");
			}
			else
			{
				JOptionPane.showMessageDialog(jp
						, "失败");
			}
		}		
	}
	
	private class SCActionListener implements ActionListener
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
				if(DB_Opreat.del_yg(getInfo()))
				{
					JOptionPane.showMessageDialog(jp
							, "成功删除");
				}
				else
				{
					JOptionPane.showMessageDialog(jp
							, "失败");
				}
			}
		}	
	}
}
