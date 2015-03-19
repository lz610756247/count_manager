package sj.panel.kucun;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
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

import sj.util.Item;

import dao.DB_Opreat;


public class Haven extends JPanel
{
	JPanel ha = this;
	JComboBox spmc_cb;
	JTextField Id_t;
	JTextField spmc;
	JTextField sl;
	JComboBox dw;
	JTextField dj;
	JButton xg;
	JButton sc;
	//文本域
	private JTextArea textarea;
	private JScrollPane panel;
	private Map map = new HashMap();
	
	public Haven()
	{
		setLayout(new GridBagLayout());
		setBounds(0, 0, 800, 600);
		
		this.Id_t = new JTextField();
		this.Id_t.setVisible(false);
		this.setupComponet(this.Id_t, 0, 0, 6, 0, true);
		
		this.initComboBox();
		this.setupComponet(this.spmc_cb, 0, 0, 6, 0, true);
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
		
		this.setupComponet(new JLabel("规格/说明:"), 0, 3, 6, 400, true);
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
		gridBagConstraints_6.ipady = 70;
		gridBagConstraints_6.ipadx = 400;
		gridBagConstraints_6.fill = GridBagConstraints.HORIZONTAL;
		add(panel, gridBagConstraints_6);
		
		this.xg = new JButton("添加");
		this.xg.addActionListener(new XGActionListener());
		this.setupComponet(this.xg, 2, 5, 1, 30, false);
		
		this.sc = new JButton("删除");
		this.sc.addActionListener(new SCActionListener());
		//this.setupComponet(this.sc, 4, 5, 1, 0, false);
		
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
			List list = DB_Opreat.query_all_kc_item();
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
	
	public Map getInfo()
	{
		Map map = new HashMap();
		map.put("Id", this.Id_t.getText());
		map.put("商品名称", this.spmc.getText());
		map.put("数量", this.sl.getText());
		map.put("单位", this.dw.getSelectedItem().toString());
		map.put("单价", this.dj.getText());
		map.put("备注", textarea.getText());
		return map;
	}
	
	private class XGActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(DB_Opreat.Update_sp(getInfo()))
			{
				JOptionPane.showMessageDialog(ha
						, "成功更新");
			}
			else
			{
				JOptionPane.showMessageDialog(ha
						, "失败");
			}
		}	
	}
	
	private class SCActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			int type = JOptionPane.showConfirmDialog(null,
					"删除后数据将不可恢复，你真的要删除吗？", "确认删除",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (type == JOptionPane.YES_OPTION)
			{
				if(DB_Opreat.del_sp(getInfo()))
				{
					JOptionPane.showMessageDialog(ha
							, "成功删除");
				}
				else
				{
					JOptionPane.showMessageDialog(ha
							, "失败");
				}
			}
		}		
	}
}
