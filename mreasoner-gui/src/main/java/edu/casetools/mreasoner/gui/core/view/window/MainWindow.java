package edu.casetools.mreasoner.gui.core.view.window;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import edu.casetools.mreasoner.configurations.data.MConfigurations;
import edu.casetools.mreasoner.gui.core.view.panels.MainPanel;


public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private MainPanel mainPanel;

	public MainWindow(MConfigurations configs) {
		mainPanel = new MainPanel(configs);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setContentPane(mainPanel);
		
		new Console(this);
		this.setWindowConfigs(configs);
	}
	
	private void setWindowConfigs(MConfigurations configs){
		this.setTitle("System Specification File Editor - New File");
		this.setWindowBounds();
		//
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void setWindowBounds(){
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize=new Dimension((int)(screenSize.width/2),(int)(screenSize.height/2));
		int x=(int)(frameSize.width/2);
		int y=(int)(frameSize.height/2);
		this.setBounds(x,y,frameSize.width,frameSize.height);
	}

	
	public MainPanel getMainPanel() {
		return mainPanel;
	}

	
}
