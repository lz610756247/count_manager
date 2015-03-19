package sj.panel.xiaoshou;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import dao.DB_Opreat;

public class Tjdd_Panel_main extends JPanel
{
	
	private JTextField khmc;
	private JTextField dianhua;
	private JTextField jsr;
	private JCheckBox isfinish;
	Map map;	//������Ϣ
	public boolean isChanged = false;
	
	public Tjdd_Panel_main()
	{
		super();
		setLayout(new GridBagLayout());
		setBounds(0, 0, 400, 50);
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		//�ͻ�����
		this.setupComponet(new JLabel("�ͻ����ƣ�"), 0, 0, 1, 0, false);
		this.khmc = new JTextField();
		this.khmc.setFocusable(true);
		this.setupComponet(this.khmc, 1, 0, 2, 300, true);
		
		//�绰
		this.setupComponet(new JLabel("��ϵ�绰��"),3 , 0, 1, 0, false);
		this.dianhua = new JTextField();
		this.setupComponet(this.dianhua, 4, 0, 2, 120, true);
		//������
		this.setupComponet(new JLabel("�����ˣ�"),6 , 0, 1, 0, false);
		this.jsr = new JTextField();
		this.setupComponet(this.jsr, 7, 0, 1, 120, true);
		
		//���
		this.setupComponet(new JLabel("�Ƿ��Ѿ����?"),8 , 0, 0, 0, false);
		this.isfinish = new JCheckBox();
		this.setupComponet(this.isfinish, 9, 0, 0, 0, false);
		
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
		
		info.put("�ͻ�����", this.khmc.getText());
		info.put("�绰", this.dianhua.getText());
		info.put("������", this.jsr.getText());
		info.put("���", this.isfinish.isSelected());
		return info;
	}
	
	public void setBaseInfo(Map map)
	{
		this.map = map;
		this.khmc.setText(map.get("�ͻ�����").toString());
		this.dianhua.setText(map.get("�绰").toString());
		this.jsr.setText(map.get("������").toString());
		if(map.get("���").equals("true"))
		{
			this.isfinish.setSelected(true);
		}
		else
		{
			this.isfinish.setSelected(false);
		}
	}
	
	
	public String getInfoId()
	{
		return this.map.get("Id").toString();
	}
	
	
	//����Ƿ��б䶯
	public void checkChanged()
	{
		if(this.map != this.getInfo())
		{
			this.isChanged = true;
		}	
	}
	
	
	//���ñ䶯
	public void setChanged()
	{
		this.map = this.getInfo();
		this.isChanged = false;
	}
		
	
	//��ȡ������Ϣ , ���ز���ɹ���ļ�¼Id
	public int insertInfo()
	{
		switch(this.insert())
		{
			case -1:
			{
				JOptionPane.showMessageDialog(this
						, "���ݿ����");
				return -1;
			}
			case 0:
			{
				JOptionPane.showMessageDialog(this
						, "����д�ͻ����ơ��绰�������ˡ�");
				break;
			}
			case 1:
			{
				return this.getId();
			}
			default:
			{
				break;
			}
		}
		return -1;
	}
	
	//����
	private int insert()
	{
		if(!this.khmc.getText().equals("")
				&&!this.dianhua.getText().equals("")
				&&!this.jsr.getText().equals(""))
		{			
			if(!DB_Opreat.insert_dd_main(getInfo()))	//���������Ϣ
			{
				return -1;
			}
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	//��ȡ����Id
	private int getId()
	{
		try 
		{
			int i = DB_Opreat.get_dd_main_Id(getInfo());
			if(i != -1)
			{
				return i;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this
					, "���ݿ����Ӵ���"+e.toString());
			e.printStackTrace();
		}
		return -1;
	}
	
		
}
