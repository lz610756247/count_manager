package sj.panel.jinhuo;

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

import sj.util.DateChooser;

import dao.DB_Opreat;

public class Tjjh_Panel_main extends JPanel
{
	
	private JTextField gonghuoshang;
	private JTextField dianhua;
	private DateChooser riqi;
	Map map;	//������Ϣ
	public boolean isChanged = false;
	
	public Tjjh_Panel_main()
	{
		super();
		setLayout(new GridBagLayout());
		setBounds(0, 0, 400, 50);
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		//����������
		this.setupComponet(new JLabel("���������ƣ�"), 0, 0, 1, 0, false);
		this.gonghuoshang = new JTextField();
		this.gonghuoshang.setFocusable(true);
		this.setupComponet(this.gonghuoshang, 1, 0, 2, 300, true);
		
		//�绰
		this.setupComponet(new JLabel("��ϵ�绰��"),3 , 0, 1, 0, false);
		this.dianhua = new JTextField();
		this.setupComponet(this.dianhua, 4, 0, 2, 120, true);
		//����
		this.setupComponet(new JLabel("���ڣ�"),6 , 0, 1, 0, false);
		this.riqi = new DateChooser("yyyy-MM-dd");
		this.setupComponet(this.riqi, 7, 0, 2, 120, true);
		
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
		
		info.put("������", this.gonghuoshang.getText());
		info.put("�绰", this.dianhua.getText());
		info.put("����", this.riqi.getText());
		return info;
	}
	
	public void setBaseInfo(Map map)
	{
		this.map = map;
		this.gonghuoshang.setText(map.get("������").toString());
		this.dianhua.setText(map.get("�绰").toString());
		this.riqi.setText(map.get("����").toString());
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
						, "����д�������������绰���ա�");
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
		if(!this.gonghuoshang.getText().equals("")
				&&!this.dianhua.getText().equals("")
				&&!this.riqi.getText().equals(""))
		{			
			try 
			{
				
				if(!DB_Opreat.inset_jj_main(getInfo()))	//���������Ϣ
				{
					return -1;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this
						, "�������ش���"+e.toString());
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this
						, "���ݿ����Ӵ���"+e.toString());
				e.printStackTrace();
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
			int i = DB_Opreat.get_jj_main_Id(getInfo());
			if(i != -1)
			{
				return i;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this
					, "�������ش���"+e.toString());
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this
					, "���ݿ����Ӵ���"+e.toString());
			e.printStackTrace();
		}
		return -1;
	}
	
		
}
