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
	
	//�ı���
	private JTextArea textarea;
	private JScrollPane panel;
			
	public Tjxx_Panel_tjzw()
	{
		super();
		setLayout(new GridBagLayout());
		
		this.setupComponet(new JLabel("ְλ��"), 0, 0, 1, 0, true);
		this.zw = new JTextField();
		this.setupComponet(this.zw, 1, 0, 1, 200, true);
		
		this.setupComponet(new JLabel("��ע��Ϣ"), 0, 1, 1, 0, true);
		
		//�ı���ؼ�
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
		
		this.tj = new JButton("���");
		tj.addActionListener(new TJActionListener());
		this.setupComponet(this.tj, 0, 3, 1, 0, true);
		
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
	
	public Map getInfo()
	{
		Map map = new HashMap();
		map.put("ְ������", this.zw.getText());
		map.put("��ע", this.textarea.getText());
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
						, "��ӳɹ�");
			}
			else
			{
				JOptionPane.showMessageDialog(jp
						, "���ʧ��");
			}
		}	
	}
}
