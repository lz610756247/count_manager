package internalframe;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sj.panel.gongzi.Glyg_Panel_xgyg;
import sj.panel.gongzi.Glyg_Panel_tjyg;
import sj.panel.gongzi.Tjxx_Panel_tjzw;
import sj.panel.gongzi.Tjxx_Panel_xgzw;

public class glyg extends JInternalFrame{

	public glyg()
	{
		this.setVisible(true);
		this.setTitle("管理员工");
		this.setBounds(0 , 0 , 600 , 400);
		this.setIconifiable(true);
		this.setClosable(true);
		this.setMaximizable(false);
		
		JTabbedPane tabPane = new JTabbedPane();
		final Glyg_Panel_tjyg gpt = new Glyg_Panel_tjyg();
		final Glyg_Panel_xgyg gpg = new Glyg_Panel_xgyg();
		tabPane.addTab("人员添加", null, gpt, "人员添加");
		tabPane.addTab("人员修改", null, gpg, "人员修改");
		getContentPane().add(tabPane);
		tabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				gpg.initComboBox();
			}
		});
		
		this.pack();
	}
}