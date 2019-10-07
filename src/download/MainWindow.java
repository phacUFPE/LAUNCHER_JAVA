package download;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.util.HashMap;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.SystemColor;

public class MainWindow {

	private static JFrame frame;
	static JLabel lblTotal = new JLabel("Total");
	static JLabel lblFile = new JLabel("File");
	static JLabel lblDownloading = new JLabel("Downloading:");
	static JLabel lblProgressTotalFull = new JLabel("");
	static JLabel lblBytes = new JLabel("Bytes");
	static JLabel lblCount = new JLabel("Count");
	
	//public static String webAddress = "http://localhost/updates/";
	public static final String webAddress = "http://swordarteron.com.br/content/client/updates/";
	
	public static final String rootDir = System.getProperty("user.dir");
	public static final String langFolder = "lang";
	public static final String extLang = "lang";
	
	public static final String fileVersion = "_version";
	public static final String hashList = "_hlist";
	public static final String fileConfig = "Config.ini";
	
	public static final Integer pgWidth = 425;
	
	public static String gameEngine = "OPGL";
	public static String launcherLanguage = "PORTUGUESE";
	
	public static Integer totalFiles;
	public static Integer filesDownloaded = 1;
	public static String fileDownloading;
	
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
	
	public static void changeLabelsLanguage() {
		if (languages.size() < 1) { return; }
		lblTotal.setText(languages.get(launcherLanguage).get("TOTAL"));
		lblFile.setText(languages.get(launcherLanguage).get("FILE"));
		lblDownloading.setText(String.format("%s: %s", languages.get(launcherLanguage).get("DOWNLOADING"), fileDownloading));
	}
	
	public static void changeLabelsForecolor(String strColor) {
		Color color;
		if (strColor == "white") { color = SystemColor.inactiveCaptionBorder; }
		else { color = SystemColor.black; }
		lblTotal.setForeground(color);
		lblFile.setForeground(color);
		lblServerVer.setForeground(color);
		lblClientVer.setForeground(color);
		lblDownloading.setForeground(color);
	}
	
	public static void prepareLabelsDownload(boolean visible) {
		lblDownloading.setVisible(visible);
		lblCount.setVisible(visible);
		lblBytes.setVisible(visible);
	}
	
	public static Runnable changeLabelsDownload(boolean checkingFiles, double percentTotal) {
		if (checkingFiles) { lblDownloading.setText(languages.get(launcherLanguage).get("CHECK_FILES")); } else 
		{ lblDownloading.setText(String.format("%s %s", languages.get(launcherLanguage).get("DOWNLOADING"), fileDownloading)); }
		lblProgressTotalFull.setSize((int)Math.round((double)(pgWidth * 0.01) * percentTotal), lblProgressTotalFull.getHeight());
		lblCount.setText(String.format("%s/%s", filesDownloaded, totalFiles));
		return null;
	}
	
	public static void prepareToDownload(boolean checkHash) {
		try {
			for (HashMap.Entry<String, String> entry : pathMD5files.entrySet()) {
				String[] aboutFile = entry.getKey().split("\\\\");
				String pathWeb = entry.getKey().replace("\\", "/");
				String fileName = aboutFile[aboutFile.length - 1];
				String pathDesk = entry.getKey().replace(fileName, "");
				File dir = new File(String.format("%s\\%s", rootDir, pathDesk));
				if (!dir.exists()) {
					dir.mkdirs();
				}
				double div = (double)filesDownloaded/(double)totalFiles; 
				double percent = div * 100;
				String hash = Hash.getHash(String.format("%s\\%s", rootDir, pathDesk + fileName));
				if (hash == null || entry.getValue().compareTo(hash) != 0) {
					fileDownloading = fileName;
					new Thread(changeLabelsDownload(false, percent));
					Thread tDownload = download(String.format("%s%s", webAddress, pathWeb.replace(" ", "%20")), pathDesk, fileName);
					tDownload.join(0);
					filesDownloaded += 1;
				} else {
					new Thread(changeLabelsDownload(true, percent));
					filesDownloaded += 1;
					continue;
				}
			}
		} catch (Exception e) {
			ErrorLog.saveError(e);
		}
	}
	
	public static void main(String[] args) {
		
		changeLabelsLanguage();
		//changeLabelsForecolor("white");
		
		lblServerVer.setText(String.format("Server Ver: %s", (serverVer == null) ? "Undefined" : serverVer));
		lblClientVer.setText(String.format("Client Ver: %s", (localVer == null) ? "Undefined" : localVer));
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					ErrorLog.saveError(e);
				}
			}
		});
		
		try {
			prepareLabelsDownload(true);
			if (localVer.compareTo(serverMinVer) >= 0) {
				if (localVer.compareTo(serverVer) == 0) {
					try {
						prepareToDownload(true);
					} catch (Exception e) {
						ErrorLog.saveError(e);
					}
				}
			} else {
				prepareToDownload(false);
			}
			prepareLabelsDownload(false);
		} catch (Exception e) {
			ErrorLog.saveError(e);
		}
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
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
		frame.getContentPane().setBackground(SystemColor.windowBorder);
		frame.setResizable(false);
		frame.setBounds(100, 100, 650, 400);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblServerVer = new JLabel();
		lblServerVer.setForeground(SystemColor.inactiveCaptionBorder);
		lblServerVer.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
		lblServerVer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblServerVer.setBounds(514, 327, 120, 15);
		frame.getContentPane().add(lblServerVer);
		
		lblClientVer = new JLabel();
		lblClientVer.setForeground(SystemColor.inactiveCaptionBorder);
		lblClientVer.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
		lblClientVer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblClientVer.setBounds(514, 346, 120, 15);
		frame.getContentPane().add(lblClientVer);
		
		JLabel lblProgressFileFull = new JLabel("");
		lblProgressFileFull.setIcon(new ImageIcon(MainWindow.class.getResource("/images/healthbar_full_425x15.png")));
		lblProgressFileFull.setHorizontalAlignment(SwingConstants.LEFT);
		lblProgressFileFull.setForeground(Color.WHITE);
		lblProgressFileFull.setBounds(95, 270, 425, 15);
		frame.getContentPane().add(lblProgressFileFull);
		
		JLabel lblProgressFileEmpty = new JLabel("");
		lblProgressFileEmpty.setIcon(new ImageIcon(MainWindow.class.getResource("/images/healthbar_empty_425x15.png")));
		lblProgressFileEmpty.setHorizontalAlignment(SwingConstants.CENTER);
		lblProgressFileEmpty.setForeground(Color.WHITE);
		lblProgressFileEmpty.setBounds(95, 270, 425, 15);
		frame.getContentPane().add(lblProgressFileEmpty);
		
		lblProgressTotalFull.setIcon(new ImageIcon(MainWindow.class.getResource("/images/healthbar_full_425x15.png")));
		lblProgressTotalFull.setHorizontalAlignment(SwingConstants.LEFT);
		lblProgressTotalFull.setForeground(Color.WHITE);
		lblProgressTotalFull.setBounds(95, 288, 425, 15);
		frame.getContentPane().add(lblProgressTotalFull);
		
		JLabel lblProgressTotalEmpty = new JLabel("");
		lblProgressTotalEmpty.setIcon(new ImageIcon(MainWindow.class.getResource("/images/healthbar_empty_425x15.png")));
		lblProgressTotalEmpty.setHorizontalAlignment(SwingConstants.CENTER);
		lblProgressTotalEmpty.setForeground(Color.WHITE);
		lblProgressTotalEmpty.setBounds(95, 288, 425, 15);
		frame.getContentPane().add(lblProgressTotalEmpty);
		
		lblFile.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		lblFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblFile.setForeground(SystemColor.inactiveCaptionBorder);
		lblFile.setBounds(10, 269, 74, 14);
		frame.getContentPane().add(lblFile);
		
		
		lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotal.setForeground(SystemColor.inactiveCaptionBorder);
		lblTotal.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		lblTotal.setBounds(10, 287, 74, 14);
		frame.getContentPane().add(lblTotal);
		
		lblDownloading.setForeground(SystemColor.inactiveCaptionBorder);
		lblDownloading.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		lblDownloading.setBounds(98, 308, 425, 15);
		frame.getContentPane().add(lblDownloading);
		lblDownloading.setVisible(false);
		
		lblBytes.setForeground(SystemColor.inactiveCaptionBorder);
		lblBytes.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		lblBytes.setBounds(98, 327, 135, 15);
		frame.getContentPane().add(lblBytes);
		lblBytes.setVisible(false);
		
		lblCount.setForeground(SystemColor.inactiveCaptionBorder);
		lblCount.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		lblCount.setBounds(243, 328, 112, 15);
		frame.getContentPane().add(lblCount);
		lblCount.setVisible(false);
		
		JLabel lblBackgroundImage = new JLabel("");
		lblBackgroundImage.setIcon(new ImageIcon(MainWindow.class.getResource("/images/bg_saoe_644x371.png")));
		lblBackgroundImage.setBounds(0, 0, 644, 371);
		frame.getContentPane().add(lblBackgroundImage);
	}
	
	private static Thread download(String fUrl, String pathDesk, String fName) {
		Thread threadDownload = null;
		try {
			threadDownload = new Thread(Download.DownloadFile(fUrl, pathDesk, fName));
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
