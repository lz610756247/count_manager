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
	//�ı���
	private JTextArea textarea;
	private JScrollPane panel;
	
	JButton tj;
	
	public Glyg_Panel_tjyg()
	{
		super();
		setLayout(new GridBagLayout());
		
		this.setupComponet(new JLabel("������"), 0, 0, 1, 0, true);
		this.xm = new JTextField();
		this.setupComponet(this.xm, 1, 0, 1, 100, true);
		
		this.setupComponet(new JLabel("���룺"), 2, 0, 1, 0, true);
		this.mm = new JTextField();
		this.setupComponet(this.mm, 3, 0, 1, 100, true);
		
		this.setupComponet(new JLabel("ְ��"), 4, 0, 1, 0, true);
		initComboBox();		
		this.setupComponet(this.zw, 5, 0, 1, 100, true);
		
		this.setupComponet(new JLabel("�绰��"), 0, 1, 1, 0, true);
		this.dh = new JTextField();
		this.setupComponet(this.dh, 1, 1, 5, 0, true);
		
		this.setupComponet(new JLabel("סַ��"), 0, 2, 1, 0, true);
		this.zz = new JTextField();
		this.setupComponet(this.zz, 1, 2, 5, 0, true);
		
		this.setupComponet(new JLabel("��ע��Ϣ"), 0, 3, 1, 0, true);
		
		//�ı���ؼ�
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
		
		this.tj = new JButton("���");
		this.tj.addActionListener(new TJActionListener());
		this.setupComponet(this.tj, 1, 5, 1, 0, true);
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
		this.zw = new JComboBox();
		zw.removeAllItems();
		zw.addItem("��ͨԱ��");	
		zw.addItem("ҵ�����Ա");	
		zw.addItem("���ʹ���Ա");
		zw.addItem("ϵͳ����Ա");
	}		
	
	public Map getInfo()
	{
		Map map = new HashMap();
		map.put("����", this.xm.getText());
		map.put("����", this.mm.getText());
		map.put("�绰", this.dh.getText());
		map.put("ְ��", this.zw.getSelectedItem().toString());
		map.put("סַ", this.zz.getText());
		map.put("��ע", this.textarea.getText());
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
