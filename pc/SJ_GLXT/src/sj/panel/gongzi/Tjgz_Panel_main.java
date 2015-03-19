package sj.panel.gongzi;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import dao.DB_Opreat;

public class Tjgz_Panel_main extends JPanel
{
	
	private JTextField xm;
	private JTextField dh;
	private JTextField zw;
	Map map;	//������Ϣ
	public boolean isChanged = false;
	
	public Tjgz_Panel_main()
	{
		super();
		setLayout(new GridBagLayout());
		setBounds(0, 0, 400, 50);
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		//����
		this.setupComponet(new JLabel("������"), 0, 0, 1, 0, true);
		this.xm = new JTextField();
		this.xm.setEditable(false);
		this.setupComponet(this.xm, 1, 0, 1, 100, true);
		
		//�绰
		this.setupComponet(new JLabel("�绰��"),2 , 0, 1, 0, true);
		this.dh = new JTextField();
		this.dh.setEditable(false);
		this.setupComponet(this.dh, 3, 0, 1, 100, true);
		//ְ��
		this.setupComponet(new JLabel("ְ��"),4 , 0, 1, 0, true);
		this.zw = new JTextField();
		zw.setEditable(false);
		this.setupComponet(this.zw, 5, 0, 1, 110, true);
		
	}
	
	
	// �������λ�ò���ӵ�������
	private void setupComponet(JComponent component, int gridx, int gridy,
			int gridwidth, int ipadx, boolean fill)
	{
			final GridBagConstraints gridbagconstraints = new GridBagConstraints();
			gridbagconstraints.gridx = gridx;
			gridbagconstraints.gridy = gridy;
			gridbagconstraints.insets = new Insets(5 , 5 , 5 , 5);
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
	
	
	//��ȡ��ϢMap
	public Map getInfo()
	{
		Map info = new HashMap();
		
		if(this.map != null)
		{
			info.put("Id", this.map.get("Id"));
		}
		
		info.put("����", this.xm.getText());
		info.put("�绰", this.dh.getText());
		info.put("ְ��", this.zw.getText());
		return info;
	}
	
	public void setBaseInfo(Map map)
	{
		this.map = map;
		this.xm.setText(map.get("����").toString());
		this.dh.setText(map.get("�绰").toString());
		this.zw.setText(map.get("ְ��").toString());
	}
	
	
	public String getInfoId()
	{
		System.out.println(this.map.get("Id").toString());
		return this.map.get("Id").toString();
	}
	
}
