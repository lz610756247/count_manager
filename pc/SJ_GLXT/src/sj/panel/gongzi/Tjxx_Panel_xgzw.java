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
import dao.DB_Opreat;

public class Tjxx_Panel_xgzw extends JPanel
{
	JPanel jp = this;
	JComboBox zw_cb;
	JTextField zw;
	JTextField Id;
	JButton bc;
	JButton sc;
	
	//�ı���
	private JTextArea textarea;
	private JScrollPane panel;
	private Map map = new HashMap();
	
	public Tjxx_Panel_xgzw()
	{
		super();
		setLayout(new GridBagLayout());
		
		this.Id = new JTextField();
		this.Id.setVisible(false);
		this.add(Id);
		
		this.initComboBox();
		this.setupComponet(this.zw_cb, 0, 0, 2, 0, true);
		this.zw_cb.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				doKeHuSelectAction();
			}		
		});
		
		this.setupComponet(new JLabel("ְλ��"), 0, 1, 1, 0, true);
		this.zw = new JTextField();
		this.setupComponet(this.zw, 1, 1, 1, 200, true);
		
		this.setupComponet(new JLabel("��ע��Ϣ"), 0, 2, 1, 0, true);
		
		//�ı���ؼ�
		this.textarea = new JTextArea();
		this.panel = new JScrollPane(textarea);
		this.textarea.setLineWrap(true);
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.weighty = 1.0;
		gridBagConstraints_6.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints_6.fill = GridBagConstraints.BOTH;
		gridBagConstraints_6.gridwidth = 2;
		gridBagConstraints_6.gridy = 3;
		gridBagConstraints_6.gridx = 0;
		gridBagConstraints_6.ipady = 50;
		gridBagConstraints_6.ipadx = 0;
		gridBagConstraints_6.fill = GridBagConstraints.HORIZONTAL;
		add(panel, gridBagConstraints_6);
		
		this.bc = new JButton("����");
		bc.addActionListener(new XGActionListener());
		this.setupComponet(this.bc, 0, 4, 1, 0, true);
		
		this.sc = new JButton("ɾ��");
		sc.addActionListener(new SCActionListener());
		this.setupComponet(this.sc, 1, 4, 1, 0, true);
		
	}
	
	// �������λ�ò���ӵ�������
	private void setupComponet(JComponent component, int gridx, int gridy,
			int gridwidth, int ipadx, boolean fill)
	{
			final GridBagConstraints gridbagconstraints = new GridBagConstraints();
			gridbagconstraints.gridx = gridx;
			gridbagconstraints.gridy = gridy;
			gridbagconstraints.insets = new Insets(5 , 5 , 3 , 5);
			if(gridwidth > 1)	//���ռ�õ�Ԫ�����һ���͸ı䣬�������һ��
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
	
	//��֯�����б�
	public void initComboBox()
	{
		if(this.zw_cb == null)
		{
			this.zw_cb = new JComboBox();
			zw_cb.removeAllItems();
			List list = DB_Opreat.query_all_zw();
			System.out.println(list);
			for(int i=0;i<list.size();i++)
			{
				Item item = (Item)list.get(i);
				map.put(item.getId(), item);
				zw_cb.addItem(item.getId()+","+item.getSpmc());
			}
		}
		else
		{
			doKeHuSelectAction();
		}
	}
	
	private void doKeHuSelectAction() 
	{
		String Id = this.zw_cb.getSelectedItem().toString()
				.substring(0 , this.zw_cb.getSelectedItem().toString().lastIndexOf(","));
		Item item = (Item) this.map.get(Id);
		this.Id.setText(item.getId());
		this.zw.setText(item.getSpmc());
		this.textarea.setText(item.getBz());
		
	}
	
	public Map getInfo()
	{
		Map map = new HashMap();
		map.put("Id", this.Id.getText());
		map.put("ְ������", this.zw.getText());
		map.put("��ע", this.textarea.getText());
		return map;
	}
	
	private class XGActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(DB_Opreat.Update_zw(getInfo()))
			{
				JOptionPane.showMessageDialog(jp
						, "�ɹ�����");
			}
			else
			{
				JOptionPane.showMessageDialog(jp
						, "ʧ��");
			}
		}		
	}
	
	private class SCActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			int type = JOptionPane.showConfirmDialog(null,
					"ɾ�������ݽ����ɻָ��������Ҫɾ����", "ȷ��ɾ��",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (type == JOptionPane.YES_OPTION)
			{
				if(DB_Opreat.del_zw(getInfo()))
				{
					JOptionPane.showMessageDialog(jp
							, "�ɹ�ɾ��");
				}
				else
				{
					JOptionPane.showMessageDialog(jp
							, "ʧ��");
				}
			}
		}	
	}
}
