package internalframe;



import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sj.panel.gongzi.Tjxx_Panel_tjzw;
import sj.panel.gongzi.Tjxx_Panel_xgzw;

public class tjxx extends JInternalFrame{

	public tjxx()
	{
		this.setVisible(true);
		this.setTitle("�����Ϣ��");
		this.setBounds(0 , 0 , 600 , 400);
		this.setIconifiable(true);
		this.setClosable(true);
		this.setMaximizable(false);
		
		JTabbedPane tabPane = new JTabbedPane();
		final Tjxx_Panel_tjzw tpz = new Tjxx_Panel_tjzw();
		final Tjxx_Panel_xgzw tpx = new Tjxx_Panel_xgzw();
		tabPane.addTab("ְλ���", null, tpz, "ְλ���");
		tabPane.addTab("ְλ�޸�", null, tpx, "ְλ�޸�");
		getContentPane().add(tabPane);
		tabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				tpx.initComboBox();
			}
		});
		
		this.pack();
	}
}