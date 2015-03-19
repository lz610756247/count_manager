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
import sj.util.Person;
import dao.DB_Opreat;

public class Glyg_Panel_xgyg extends JPanel
{
	JPanel jp = this;
	JComboBox xm_cb;
	JTextField Id;
	JTextField xm;
	JTextField mm;
	JTextField dh;
	JTextField zz;
	JComboBox zw;
	//�ı���
	private JTextArea textarea;
	private JScrollPane panel;
	
	JButton xg;
	JButton sc;
	private Map map = new HashMap();
	
	public Glyg_Panel_xgyg()
	{
		super();
		setLayout(new GridBagLayout());
		
		this.Id = new JTextField();
		this.Id.setVisible(false);
		this.add(Id);
		
		this.initComboBox();
		this.setupComponet(this.xm_cb, 0, 0, 6, 0, true);
		this.xm_cb.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				doKeHuSelectAction();
			}		
		});
		
		this.setupComponet(new JLabel("������"), 0, 1, 1, 0, true);
		this.xm = new JTextField();
		this.setupComponet(this.xm, 1, 1, 1, 100, true);
		
		this.setupComponet(new JLabel("���룺"), 2, 1, 1, 0, true);
		this.mm = new JTextField();
		this.setupComponet(this.mm, 3, 1, 1, 100, true);
		
		this.setupComponet(new JLabel("ְ��"), 4, 1, 1, 0, true);
		initComboBox_1();	
		this.setupComponet(this.zw, 5, 1, 1, 100, true);
		
		this.setupComponet(new JLabel("�绰��"), 0, 2, 1, 0, true);
		this.dh = new JTextField();
		this.setupComponet(this.dh, 1, 2, 5, 0, true);
		
		this.setupComponet(new JLabel("סַ��"), 0, 3, 1, 0, true);
		this.zz = new JTextField();
		this.setupComponet(this.zz, 1, 3, 5, 0, true);
		
		this.setupComponet(new JLabel("��ע��Ϣ"), 0, 4, 1, 0, true);
		
		//�ı���ؼ�
		this.textarea = new JTextArea();
		this.panel = new JScrollPane(textarea);
		this.textarea.setLineWrap(true);
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.weighty = 1.0;
		gridBagConstraints_6.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints_6.fill = GridBagConstraints.BOTH;
		gridBagConstraints_6.gridwidth = 6;
		gridBagConstraints_6.gridy = 5;
		gridBagConstraints_6.gridx = 0;
		gridBagConstraints_6.ipady = 50;
		gridBagConstraints_6.ipadx = 0;
		gridBagConstraints_6.fill = GridBagConstraints.HORIZONTAL;
		add(panel, gridBagConstraints_6);	
		
		this.xg = new JButton("�޸�");
		this.xg.addActionListener(new XGActionListener());
		this.setupComponet(this.xg, 1, 6, 1, 0, true);
		
		this.sc = new JButton("ɾ��");
		this.sc.addActionListener(new SCActionListener());
		this.setupComponet(this.sc, 2, 6, 1, 0, true);
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
		if(this.xm_cb == null)
		{
			this.xm_cb = new JComboBox();
			xm_cb.removeAllItems();
			List list = DB_Opreat.query_all_yg();
			System.out.println(list);
			for(int i=0;i<list.size();i++)
			{
				Person person = (Person)list.get(i);
				map.put(person.getId(), person);
				xm_cb.addItem(person.getId()+","+person.getXm());
			}
		}
		else
		{
			doKeHuSelectAction();
		}
	}
	
	//��֯�����б�
	public void initComboBox_1()
	{
		this.zw = new JComboBox();
		zw.removeAllItems();
		zw.addItem("��ͨԱ��");	
		zw.addItem("ҵ�����Ա");	
		zw.addItem("���ʹ���Ա");	
		zw.addItem("ϵͳ����Ա");
	}		
	
	private void doKeHuSelectAction() 
	{
		String Id = this.xm_cb.getSelectedItem().toString()
				.substring(0 , this.xm_cb.getSelectedItem().toString().lastIndexOf(","));
		Person person = DB_Opreat.query_all_yg_by_Id(Id);
		this.Id.setText(person.getId());
		this.xm.setText(person.getXm());
		this.mm.setText(person.getPw());
		this.dh.setText(person.getDh());
		this.zz.setText(person.getZz());
		this.zw.setSelectedItem(person.getZw());
		this.textarea.setText(person.getBz());
		
	}
	
	public Map getInfo()
	{
		Map map = new HashMap();
		map.put("Id", this.Id.getText());
		map.put("����", this.xm.getText());
		map.put("����", this.mm.getText());
		map.put("�绰", this.dh.getText());
		map.put("ְ��", this.zw.getSelectedItem().toString());
		map.put("סַ", this.zz.getText());
		map.put("��ע", this.textarea.getText());
		return map;
	}	
	
	private class XGActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(DB_Opreat.Update_yg(getInfo()))
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
				if(DB_Opreat.del_yg(getInfo()))
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
