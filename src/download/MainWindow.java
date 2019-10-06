package download;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.util.HashMap;
import java.awt.Font;
import java.awt.Color;

public class MainWindow {

	private static JFrame frame;
	
	//public static String webAddress = "http://localhost/updates/";
	public static final String webAddress = "http://swordarteron.com.br/content/client/updates/";
	
	public static final String rootDir = System.getProperty("user.dir");
	public static final String langFolder = "lang";
	public static final String extLang = "lang";
	
	public static final String fileVersion = "_version";
	public static final String hashList = "_hlist";
	public static final String fileConfig = "Config.ini";
	
	public static String gameEngine = "OPGL";
	public static String launcherLanguage = "pt";
	
	public static Integer totalFiles;
	
	public static String serverVer = "0";
	public static String serverMinVer = "0";
	public static String localVer = "0";
	
	private static JLabel lblServerVer;
	private static JLabel lblClientVer;
	
	public static HashMap<String, HashMap<String, String>> languages;
	
	public static HashMap<String, String> pathMD5files;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					lblServerVer.setText(String.format("Server Ver: %s", (serverVer == null) ? "Undefined" : serverVer));
					lblClientVer.setText(String.format("Client Ver: %s", (localVer == null) ? "Undefined" : localVer));
					
					frame.setVisible(true);
				} catch (Exception e) {
					ErrorLog.saveError(e);
				}
			}
		});
		
		if (!Version.compareSL(serverVer, localVer)) {
			try {
				//Thread tDownload = download("http://swordarteron.com.br/content/client/updates/SAOE_DX9.exe", "SAOE_DX9.exe");
				//tDownload.join(0);
			} catch (Exception e) {
				ErrorLog.saveError(e);
			}
		} else {
			//HABILITAR O BOTÃO DO GAME
		}
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		
		main(null);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(51, 51, 51));
		frame.setResizable(false);
		frame.setBounds(100, 100, 650, 400);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblServerVer = new JLabel();
		lblServerVer.setForeground(Color.GRAY);
		lblServerVer.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
		lblServerVer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblServerVer.setBounds(514, 327, 120, 15);
		frame.getContentPane().add(lblServerVer);
		
		lblClientVer = new JLabel();
		lblClientVer.setForeground(Color.GRAY);
		lblClientVer.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
		lblClientVer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblClientVer.setBounds(514, 346, 120, 15);
		frame.getContentPane().add(lblClientVer);
	}
	
	private static Thread download(String fUrl, String fName) {
		Thread threadDownload = null;
		try {
			threadDownload = new Thread(Download.DownloadFile(fUrl, fName));
			threadDownload.start();
			
		} catch (Exception e) {
			ErrorLog.saveError(e);
		}
		return threadDownload;
	}

	public Window getFrame() {
		// TODO Auto-generated method stub
		return frame;
	}
}
