package sj.panel.gongzi;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sj.util.Person;

import dao.DB_Opreat;

public class Glyg_Panel_tjyg extends JPanel
{
	JPanel jp = this;
	JTextField xm;
	JTextField mm;
	JTextField dh;
	JTextField zz;
	JComboBox zw;
	//文本域
	private JTextArea textarea;
	private JScrollPane panel;
	
	JButton tj;
	
	public Glyg_Panel_tjyg()
	{
		super();
		setLayout(new GridBagLayout());
		
		this.setupComponet(new JLabel("姓名："), 0, 0, 1, 0, true);
		this.xm = new JTextField();
		this.setupComponet(this.xm, 1, 0, 1, 100, true);
		
		this.setupComponet(new JLabel("密码："), 2, 0, 1, 0, true);
		this.mm = new JTextField();
		this.setupComponet(this.mm, 3, 0, 1, 100, true);
		
		this.setupComponet(new JLabel("职务："), 4, 0, 1, 0, true);
		initComboBox();		
		this.setupComponet(this.zw, 5, 0, 1, 100, true);
		
		this.setupComponet(new JLabel("电话："), 0, 1, 1, 0, true);
		this.dh = new JTextField();
		this.setupComponet(this.dh, 1, 1, 5, 0, true);
		
		this.setupComponet(new JLabel("住址："), 0, 2, 1, 0, true);
		this.zz = new JTextField();
		this.setupComponet(this.zz, 1, 2, 5, 0, true);
		
		this.setupComponet(new JLabel("备注信息"), 0, 3, 1, 0, true);
		
		//文本域控件
		this.textarea = new JTextArea();
		this.panel = new JScrollPane(textarea);
		this.textarea.setLineWrap(true);
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.weighty = 1.0;
		gridBagConstraints_6.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints_6.fill = GridBagConstraints.BOTH;
		gridBagConstraints_6.gridwidth = 6;
		gridBagConstraints_6.gridy = 4;
		gridBagConstraints_6.gridx = 0;
		gridBagConstraints_6.ipady = 50;
		gridBagConstraints_6.ipadx = 0;
		gridBagConstraints_6.fill = GridBagConstraints.HORIZONTAL;
		add(panel, gridBagConstraints_6);	
		
		this.tj = new JButton("添加");
		this.tj.addActionListener(new TJActionListener());
		this.setupComponet(this.tj, 1, 5, 1, 0, true);
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
		this.zw = new JComboBox();
		zw.removeAllItems();
		zw.addItem("普通员工");	
		zw.addItem("业务管理员");	
		zw.addItem("工资管理员");
		zw.addItem("系统管理员");
	}		
	
	public Map getInfo()
	{
		Map map = new HashMap();
		map.put("姓名", this.xm.getText());
		map.put("密码", this.mm.getText());
		map.put("电话", this.dh.getText());
		map.put("职务", this.zw.getSelectedItem().toString());
		map.put("住址", this.zz.getText());
		map.put("备注", this.textarea.getText());
		return map;
	}
	
	public void reset()
	{
		this.xm.setText("");
		this.mm.setText("");
		this.dh.setText("");
//		this.zw.setText("");
		this.zz.setText("");
		this.textarea.setText("");
	}
	
	private class TJActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(DB_Opreat.AddNewYG(getInfo()))
			{
				reset();
				JOptionPane.showMessageDialog(jp
						, "添加成功");
			}
			else
			{
				JOptionPane.showMessageDialog(jp
						, "添加失败");
			}
		}		
	}
}
