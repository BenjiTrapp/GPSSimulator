package gps.NMEA.graph;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * Use this Tool to read GPGGA (gps.NMEA) Sentences from a file and draws all
 * spots as image. This image is stored as a "pg" File under log/NMEAGrap.png
 * @author Benjamin Trapp
 */
public class NMEAGraphGUI extends JPanel
{
	private static final long serialVersionUID = -7633211958882583804L;
	private JLabel statusBar;
	private BufferedImage image = null;
	private List<Double> latitudeList;
	private List<Double> longitudeList;
	private double latitudeMin = 90;
	private double latitudeMax = 0;
	private double longitudeMin = 180;
	private double longitudeMax = 0;
	
	/**
	 * Creates a gps.NMEA GUI-Graph
	 */
	public NMEAGraphGUI()
	{
		latitudeList = new ArrayList<Double>();
		longitudeList = new ArrayList<Double>();
		statusBar = new JLabel("Status: ");
		statusBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	}
	
	/**
	 * Reads all the info from the file specified in the path. During this 
	 * process, the values for the longitude and latitude are normalized.
	 * @param path
	 */
	private void readFromFile(String path)
	{
		statusBar.setText(path);
		latitudeList.clear();
		longitudeList.clear();
		BufferedReader in = null;
		String line = "";
		double tmp = -1.0;
		
		try
		{
			in = new BufferedReader(new InputStreamReader(new FileInputStream(path)));	
		
			while (in.ready())
			{
				line = in.readLine();
				
				if (line.contains("$GPGGA"))
				{
					String[] s = line.split(",");
					
					//Normalize latitude
					tmp = parse(s[2], 2);
					if (tmp > latitudeMax)
						latitudeMax = tmp;
					if (tmp < latitudeMin)
						latitudeMin = tmp;
					latitudeList.add(tmp);
					
					//Normalize longitude
					tmp = parse(s[4], 3);
					if (tmp > longitudeMax)
						longitudeMax = tmp;
					if (tmp < longitudeMin)
						longitudeMin = tmp;
					longitudeList.add(tmp);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				in.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * This function draws the Image
	 * Annotation: Behold, here be Dragons 
	 */
	private void drawImage()
	{
		BasicStroke bs = new BasicStroke(2);
		int x = -1;
		int y = -1;
		
		int latitudeLength = latitudeList.size();
		Graphics2D g = (Graphics2D) image.getGraphics();
		double cosLat = Math.cos((latitudeMax + latitudeMin) * Math.PI / 360);
		double kX = (latitudeMax - latitudeMin) / (image.getWidth() - 2);
		double kY = (longitudeMax - longitudeMin) / (image.getHeight() - 2);
		double x1 = latitudeList.get(0);
		double x2 = latitudeList.get(4);
		double y1 = longitudeList.get(0);
		double y2 = longitudeList.get(4);
		double a = Math.atan((y2 - y1) / (x2 - x1));

		g.setStroke(bs);
		g.translate(image.getWidth() >> 1, image.getHeight() >> 1);
		g.rotate(-Math.PI / 2 + a);
		g.translate(-image.getWidth() / 3, -image.getHeight() >> 1);
		
		//Draw the Points received from the GGA Sentece
		for (int i = 0; i < latitudeLength; i++)
		{
			g.setColor(Color.BLUE);
			
			//Mark the Start Point green
			if (i == 0)
				g.setColor(Color.GREEN);
			
			//Mark the End Point red
			if (i == latitudeLength - 1)
				g.setColor(Color.RED);
			
			x = (int) ((latitudeList.get(i) - latitudeMin) * cosLat / kX) + 1;
			y = (int) ((longitudeList.get(i) - longitudeMin) / kY) + 1;
			g.drawLine(x, y, x, y);
		}
	}

	/**
	 * Prepare the image 
	 * @param path path to the File that contains the GPGGA Sentences
	 */
	private void prepareImage(String path)
	{
		readFromFile(path);
		
		if (latitudeList.size() < 5)
		{
			latitudeList.clear();
			longitudeList.clear();
			return;
		}
		
		image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		drawImage();
		
		File file = new File("log/gps.NMEA.NMEAGraph.png");
		try
		{
			ImageIO.write(image, "png", file);
		} catch (IOException e)
		{
		}
	}
	
	/**
	 * Paint the component and draws the image properly scaled
	 */
	protected void paintComponent(Graphics g)
	{
		super.paintComponents(g);
		
		if (image == null)
			return;
		
		Insets insets = getInsets();
		
		g.drawImage(image.getScaledInstance(getWidth() - insets.right
				- insets.left, getHeight() - insets.top - insets.bottom,
				Image.SCALE_SMOOTH), insets.right, insets.top, null);
	}

	/**
	 * Parses a specific length of a String to double, for further operations. 
	 * @param s String to parse
	 * @param length length of the (sub)string that shall be parsed
	 * @return Gets the parsed double back
	 */
	private double parse(String s, int length)
	{
		double d = Double.parseDouble(s.substring(0, length));
		d += Double.parseDouble(s.substring(length, s.length())) / 60;
		return d;
	}
	
	/**
	 * The main 
	 * @param args
	 */
	public static void main(String[] args)
	{
		final NMEAGraphGUI gui = new NMEAGraphGUI();
		gui.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JButton open = new JButton("�ffnen");
		open.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(new File(".\\log").getAbsolutePath()));
				
				if (chooser.showOpenDialog(gui) == JFileChooser.APPROVE_OPTION)
					gui.prepareImage(chooser.getSelectedFile().getAbsolutePath());
			}
		});

		JButton draw = new JButton("Aktualisieren");
		draw.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				gui.repaint();
			}
		});
		
		//Prepare and draw the panel
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(open);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(draw);
		
		//Prepare and draw the frame
		JFrame frame = new JFrame();
		frame.setTitle("gps.NMEA-GPGGA Graph");
		frame.setLayout(new BorderLayout());
		frame.add(gui, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.NORTH);
		frame.add(gui.statusBar, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
}
