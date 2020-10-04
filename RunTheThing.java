package weezerDetect;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.StringWebResponse;
import com.gargoylesoftware.htmlunit.TopLevelWindow;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.parser.HTMLParser;

public class RunTheThing {
	public static String tweetIMG = "";
	public static void getImageDL() throws MalformedURLException {
		try {
			String command = "start chrome " + tweetIMG;
			Runtime.getRuntime().exec(new String[]{"cmd", "/c",command});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			   Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		try {
			Robot robot = new Robot();
			String format = "png";
			String fileName = "theImage." + format;
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage src = robot.createScreenCapture(screenRect);
			BufferedImage dest = src.getSubimage(500, 200, 700, 500);
			ImageIO.write(dest, format, new File(fileName));
			System.out.println("A full screenshot saved!");
		} catch (AWTException | IOException ex) {
			System.err.println(ex);
		}
	}
	
	public static void run() {
		while(true) {
			Runtime rt = Runtime.getRuntime();
			SearchWord s = new SearchWord("../word.txt");
			
			File ioFile = new File("../file.out");
			
			PrintWriter writer;
			
			try {
				writer = new PrintWriter(ioFile);
				writer.print(s.getWord());
				writer.close();
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			}
			
			try {
				Process pr = rt.exec("node ../weezerBot/bot.js");
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			File imgFile = new File("../img.txt");
			FileReader FileIn;
			BufferedReader objReader;
			String str;
			try {
				FileIn = new FileReader(imgFile);
				objReader = new BufferedReader(FileIn);
				if((str = objReader.readLine()) == null) {
					System.out.println("Emtpy!");
				}
				else {
					tweetIMG = str;
					getImageDL();
					try {
						int num = (int) Math.round((Math.random()*4));
						Process pr;
						if(num == 1) {
							pr = rt.exec("python main.py --src ./whiteShirt.png --dst C:\\Users\\alexei\\Desktop\\weezer2\\FaceDetect\\theImage.png --out C:\\Users\\alexei\\Desktop\\weezer2\\result.jpg");
							
						}
						else if(num == 2) {
							pr = rt.exec("python main.py --src ./mainDude.png --dst C:\\Users\\alexei\\Desktop\\weezer2\\FaceDetect\\theImage.png --out C:\\Users\\alexei\\Desktop\\weezer2\\result.jpg");
						}
						else if(num == 3) {
							pr = rt.exec("python main.py --src ./pinkShirt.png --dst C:\\Users\\alexei\\Desktop\\weezer2\\FaceDetect\\theImage.png --out C:\\Users\\alexei\\Desktop\\weezer2\\result.jpg");
						}
						else {
							pr = rt.exec("python main.py --src ./blueShirt.png --dst C:\\Users\\alexei\\Desktop\\weezer2\\FaceDetect\\theImage.png --out C:\\Users\\alexei\\Desktop\\weezer2\\result.jpg");
						}
						InputStream stdIn = pr.getInputStream();
						InputStreamReader isr = new InputStreamReader(stdIn);
						BufferedReader br = new BufferedReader(isr);
						String line = "";
						String output = "";
						while ((line = br.readLine()) != null)
						     output=line;
						System.out.println(output);
						if(output.equals("Detect 0 Face !!!")) {
							continue;
						}
						else {
							pr = rt.exec("node ../weezerPost/bot.js");
							
							break;
						}
					} catch(IOException e) {
						e.printStackTrace();
					}
					
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		while(true) {
			run();
			try {
				Thread.sleep(7200000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
