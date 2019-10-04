package download;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Window.Type;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class LoadWindow {

	private JFrame frame;
	
	private static LoadWindow window;
	
	static JLabel lblLoadingLanguages = new JLabel();

	/**
	 * Launch the application.
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new LoadWindow();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
					
		lblLoadingLanguages.setText("Loading Languages...");
		MainWindow.languages = Language.loadLanguages();
		lblLoadingLanguages.setText("Retrieving Server Version...");
		MainWindow.serverVer = Version.getServer(MainWindow.webAddress, MainWindow.fileVersion, "current_ver");
		MainWindow.serverMinVer = Version.getServer(MainWindow.webAddress, MainWindow.fileVersion, "min_ver");
		lblLoadingLanguages.setText("Retrieving Local Version...");
		MainWindow.localVer = Version.getLocal(MainWindow.fileConfig);
					
		lblLoadingLanguages.setText("Retrieving Files...");
		getWebFiles(MainWindow.webAddress + MainWindow.hashList);
					
		window.frame.dispose();
		
		MainWindow mWin = new MainWindow();
	}

	/**
	 * Create the application.
	 */
	public LoadWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setType(Type.UTILITY);
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 350);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.getContentPane().setLayout(null);
		frame.setBackground(Color.BLACK);
		
		lblLoadingLanguages.setForeground(Color.WHITE);
		lblLoadingLanguages.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		lblLoadingLanguages.setBounds(142, 142, 170, 22);
		frame.getContentPane().add(lblLoadingLanguages);
		
		JLabel lblLoadGIF = new JLabel("");
		lblLoadGIF.setIcon(new ImageIcon(LoadWindow.class.getResource("/images/load_gif.gif")));
		lblLoadGIF.setBounds(-165, -160, 800, 600);
		frame.getContentPane().add(lblLoadGIF);
	}

	public Window getFrame() {
		// TODO Auto-generated method stub
		return frame;
	}
	
	public static void getWebFiles(String address) {
		try {
			
			HashMap<String, String> hm = new HashMap<String, String>();
			
			URL url = new URL(address);
			Scanner s = new Scanner(url.openStream());
		   
			String text = "";
		   
			while (s.hasNextLine()) {
				text += s.nextLine() + "\n";
			}
			s.close();
		   
			Pattern pat = Pattern.compile("file=\"(.*)\", hash=\"(.*)\";");
			Matcher mat = pat.matcher(text);
			while (mat.find()) {
			   	hm.put(mat.group(1), mat.group(2));
			}
			
			MainWindow.pathMD5files = hm;
		   
			pat = Pattern.compile("totalfiles=(.*)");
			mat = pat.matcher(text);
			if (mat.find()) {
				MainWindow.totalFiles = Integer.parseInt(mat.group(1));
			}
		   
		} catch(Exception e) {
			ErrorLog.saveError(e);
		}
	}
}
