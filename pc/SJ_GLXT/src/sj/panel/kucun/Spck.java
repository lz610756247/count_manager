package sj.panel.kucun;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.DB_Opreat;


public class Spck extends JPanel
{
	
	JPanel jp = this;
	JTextField spmc;
	JTextField sl;
	JComboBox dw;
	JTextField dj;
	JButton bc;
	//文本域
	private JTextArea textarea;
	private JScrollPane panel;
	Map map;
	
	public Spck()
	{
		super();
		setLayout(new GridBagLayout());
		setBounds(0, 0, 800, 600);
		
		this.setupComponet(new JLabel("商品名称："), 0, 0, 1, 0, false);
		this.spmc = new JTextField();
		this.setupComponet(this.spmc, 1, 0, 5, 400, true);
		
		this.setupComponet(new JLabel("数    量："), 0, 1, 1, 0, false);
		this.sl = new JTextField();
		this.setupComponet(this.sl, 1, 1, 1, 100, true);
		
		this.dw = new JComboBox();
		dw.setModel(new DefaultComboBoxModel(new String[]{"吨",
		"件"}));
		this.setupComponet(new JLabel("单位："), 2, 1, 1, 0, false);
		this.setupComponet(this.dw, 3, 1, 1, 10, true);
		
		this.setupComponet(new JLabel("单价："), 4, 1, 1, 0, false);
		this.dj = new JTextField();
		this.setupComponet(this.dj, 5, 1, 1, 30, true);
		
		this.setupComponet(new JLabel("规格/说明:"), 0, 2, 6, 400, true);
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
		gridBagConstraints_6.ipady = 70;
		gridBagConstraints_6.ipadx = 400;
		gridBagConstraints_6.fill = GridBagConstraints.HORIZONTAL;
		add(panel, gridBagConstraints_6);
		
		this.bc = new JButton("保存");
		this.bc.addActionListener(new BCActionListener());
		this.setupComponet(this.bc, 2, 4, 1, 30, false);
		
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
	
	public void set_sp_item(Map map)
	{
		if(this.map == null)
		{
			this.map = map;
		}
		this.spmc.setText(this.map.get("商品名称").toString());
		this.sl.setText(this.map.get("数量").toString());
		this.dj.setText(this.map.get("单价").toString());
		this.textarea.setText(this.map.get("备注").toString());
	}
	
	public Map getInfo()
	{
		Map map = new HashMap();
		if(this.map != null)
			map.put("Id", this.map.get("Id"));
		map.put("商品名称", this.spmc.getText());
		map.put("数量", this.sl.getText());
		map.put("单位", this.dw.getSelectedItem().toString());
		map.put("单价", this.dj.getText());
		map.put("备注", textarea.getText());
		return map;
	}
	
	//添加动作
	private class BCActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			System.out.println(getInfo());
			if(DB_Opreat.update_sp_item(getInfo()))
			{
				JOptionPane.showMessageDialog(jp
						, "成功保存");
			}
			else
			{
				JOptionPane.showMessageDialog(jp
						, "失败");
			}
		}		
	}	
}
