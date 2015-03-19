package internalframe;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sj.panel.kucun.AddNew;
import sj.panel.kucun.Haven;

public class tjkc extends JInternalFrame{

	public tjkc()
	{
		this.setVisible(true);
		this.setTitle("添加库存");
		this.setBounds(00 , 00 , 600 , 400);
		this.setIconifiable(true);
		this.setClosable(true);
		this.setMaximizable(false);
		
		this.createTabbed();
		
		this.pack();
	}
	
	private void createTabbed()
	{
		AddNew an = new AddNew();
		final Haven ha = new Haven();
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.addTab("新的商品", null, an, "新的商品");
		tabPane.addTab("添加已有", null, ha, "添加已有");
		tabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ha.initComboBox();
			}
		});
		this.add(tabPane);
	}
}
