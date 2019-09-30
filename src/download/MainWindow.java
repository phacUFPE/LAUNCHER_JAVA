package download;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MainWindow {

	private JFrame frame;
	
	public static String webAddress = "http://localhost/updates/";
	public static String rootDir = System.getProperty("user.dir");
	
	public static String fileVersion = "_version";
	public static String fileConfig = "Config.ini";
	
	public static String serverVer = "1";
	public static String serverMinVer = "1";
	public static String localVer = "0";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					ErrorLog.saveError(e);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		serverVer = Version.getServer(webAddress, fileVersion, "current_ver");
		serverMinVer = Version.getServer(webAddress, fileVersion, "min_ver");
		localVer = Version.getLocal(rootDir, fileConfig);
		
		if (!Version.compareSL(serverVer, localVer)) {
			try {
				//Thread tDownload = download(null, null);
				//tDownload.join(0);
			} catch (Exception e) {
				ErrorLog.saveError(e);
			}
			
		} else {
			
		}
	}
	
	public void getWebFiles()
	{
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 650, 400);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private Thread download(String fUrl, String fName) {
		Thread threadDownload = null;
		try {
			threadDownload = new Thread(Download.DownloadFile(fUrl, fName));
			threadDownload.start();
			
		} catch (Exception e) {
			ErrorLog.saveError(e);
		}
		return threadDownload;
	}

}
