package sj.panel.gongzi;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.DB_Opreat;

public class Tjxx_Panel_tjzw extends JPanel
{
	JPanel jp = this;
	JTextField zw;
	JButton tj;
	
	//文本域
	private JTextArea textarea;
	private JScrollPane panel;
			
	public Tjxx_Panel_tjzw()
	{
		super();
		setLayout(new GridBagLayout());
		
		this.setupComponet(new JLabel("职位："), 0, 0, 1, 0, true);
		this.zw = new JTextField();
		this.setupComponet(this.zw, 1, 0, 1, 200, true);
		
		this.setupComponet(new JLabel("备注信息"), 0, 1, 1, 0, true);
		
		//文本域控件
		this.textarea = new JTextArea();
		this.panel = new JScrollPane(textarea);
		this.textarea.setLineWrap(true);
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.weighty = 1.0;
		gridBagConstraints_6.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints_6.fill = GridBagConstraints.BOTH;
		gridBagConstraints_6.gridwidth = 2;
		gridBagConstraints_6.gridy = 2;
		gridBagConstraints_6.gridx = 0;
		gridBagConstraints_6.ipady = 50;
		gridBagConstraints_6.ipadx = 0;
		gridBagConstraints_6.fill = GridBagConstraints.HORIZONTAL;
		add(panel, gridBagConstraints_6);
		
		this.tj = new JButton("添加");
		tj.addActionListener(new TJActionListener());
		this.setupComponet(this.tj, 0, 3, 1, 0, true);
		
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
	
	public Map getInfo()
	{
		Map map = new HashMap();
		map.put("职务名称", this.zw.getText());
		map.put("备注", this.textarea.getText());
		return map;
	}
	
	public void reset()
	{
		this.zw.setText("");
		this.textarea.setText("");
	}
	
	private class TJActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(DB_Opreat.AddNewZW(getInfo()))
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
