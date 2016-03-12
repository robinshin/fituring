package kinect;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.ufl.digitalworlds.gui.DWApp;
import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.utils.ProgressListener;

@SuppressWarnings("serial")
public class XEDConvertApp extends DWApp implements ProgressListener{

	JButton button;
	public JLabel fps;
	KinectRecorder kinect;
	
	@Override
	public void GUIsetup(JPanel root) {
		
		if(System.getProperty("os.arch").toLowerCase().indexOf("64")<0)
		{
			if(DWApp.showConfirmDialog("Performance Warning", "<html><center><br>WARNING: You are running a 32bit version of Java.<br>This may reduce significantly the performance of this application.<br>It is strongly adviced to exit this program and install a 64bit version of Java.<br><br>Do you want to exit now?</center>"))
				System.exit(0);
		}
		
		setLoadingProgress("Intitializing Kinect...",20);
		kinect=new KinectRecorder(this);
		if(!kinect.start(J4KSDK.DEPTH|J4KSDK.COLOR))
		{
			DWApp.showErrorDialog("ERROR", "<html><center><br>ERROR: The Kinect device could not be initialized.<br><br>1. Check if the Microsoft's Kinect SDK was succesfully installed on this computer.<br> 2. Check if the Kinect is plugged into a power outlet.<br>3. Check if the Kinect is connected to a USB port of this computer.</center>");	
		}
		else
		{
			kinect.setNearMode(false);
		}
		
		setLoadingProgress("Intitializing Window...",80);
		button=new JButton("Start");
		button.addActionListener(this);
		fps=new JLabel("0");
		
		JPanel panel=new JPanel(new GridLayout(1,0));
		panel.add(button);
		panel.add(fps);
		root.add(panel);
		
	}

	public void GUIclosing()
	{
		kinect.stop();
	}
	
	public XEDConvertApp() {

		if(System.getProperty("os.arch").toLowerCase().indexOf("64")<0)
		{
			if(DWApp.showConfirmDialog("Performance Warning", "<html><center><br>WARNING: You are running a 32bit version of Java.<br>This may reduce significantly the performance of this application.<br>It is strongly adviced to exit this program and install a 64bit version of Java.<br><br>Do you want to exit now?</center>"))
				System.exit(0);
		}
		
		setLoadingProgress("Intitializing Kinect...",20);
		kinect=new KinectRecorder(this);
		if(!kinect.start(J4KSDK.DEPTH|J4KSDK.COLOR))
		{
			DWApp.showErrorDialog("ERROR", "<html><center><br>ERROR: The Kinect device could not be initialized.<br><br>1. Check if the Microsoft's Kinect SDK was succesfully installed on this computer.<br> 2. Check if the Kinect is plugged into a power outlet.<br>3. Check if the Kinect is connected to a USB port of this computer.</center>");	
		}
		else
		{
			kinect.setNearMode(false);
		}
		
		setLoadingProgress("Intitializing Window...",80);
		button=new JButton("Start");
		button.addActionListener(this);
		fps=new JLabel("0");
		
		JPanel panel=new JPanel(new GridLayout(1,0));
		panel.add(button);
		panel.add(fps);
	}
	
	
	public static void main(String args[]) {
		
    	createMainFrame("J4K");
    	app=new XEDConvertApp();
    	setFrameSize(200,100,null);
    }
	
	@Override
	public void GUIactionPerformed(ActionEvent e)
	{
		if(e.getSource()==button)
		{
			if(button.getText().compareTo("Start")==0)
			{
				JFileChooser chooser = new JFileChooser();
		        chooser.setFileHidingEnabled(false);
		        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		        chooser.setMultiSelectionEnabled(false);
		        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		        if(getMostRecentPath().length()>0)
					chooser.setCurrentDirectory(new File(getMostRecentPath()));
		        chooser.setDialogTitle("Save Kinect Data (zip)");
		        chooser.setApproveButtonText("Save"); 
		        
		        if (chooser.showSaveDialog(this)== JFileChooser.APPROVE_OPTION) 
		        {
		        	setMostRecentPath(chooser.getCurrentDirectory().getAbsolutePath());
		        	String filename=chooser.getSelectedFile().getAbsolutePath();
					kinect.startRecording(filename);
					button.setText("Stop");
		        }
			}
			else
			{
				kinect.stopRecording();
				button.setEnabled(false);
				button.setText("Start");
			}
		}
	}

	int max_progress=1;
	
	public void setMaxProgress(int value) {max_progress=value;}

	public void setProgress(int value) {fps.setText(""+(int)(value*100f/max_progress)+"%");if(value==max_progress)button.setEnabled(true);}
}
