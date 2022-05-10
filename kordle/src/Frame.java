import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.apache.commons.io.FileUtils;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {

		
	
	public void paint(Graphics g) {
		//Font stringFont = new Font( "SansSerif", Font. PLAIN, 18 );
		g.setFont(new Font( "SansSerif", Font.BOLD, 42 ));
		g.setColor(Color.CYAN);
		g.drawString("Kordle", 330, 100);
		
	}
	
	public static void main(String[] arg) {
		Frame f = new Frame();
		//System.out.println(getFullResponse());
		/*FileReader reader;
	        try {
	            reader = new FileReader("players.json");
	            JsonParser jsonParser =  new JsonParser();
	            JsonArray array = (JsonArray) jsonParser.parse(reader);
	            searchJsonElemnet(array);
	        } catch (FileNotFoundException e1) {
	            e1.printStackTrace();
	        }
	    }

	    private static void searchJsonElemnet(JsonArray jsonArray){
	        String[][] matrix = new String[50][14];
	        int i =0;
	        int j = 0;
	        for (JsonElement jsonElement : jsonArray) {
	             for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
	                 matrix[i][j] = entry.getValue().toString();
	                 j++;
	            }
	            i++;
	            j = 0;
	        }
	        for (String[] row : matrix)
	        {
	            for (String value : row)
	            {
	                System.out.println(value);
	            }
	        }*/
		String players_info = "";
		try {
			players_info = FileUtils.readFileToString(new File("players.json"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String cat = "";
		System.out.println(name(players_info, 2));
		String[] player_names = new String[50];
		for (int i = 1; i <=50; i++) {
			player_names[i-1] = name(players_info, i);
		}
		System.out.println(Arrays.toString(player_names));
		
		
	    
		
	}
	
	//Frame settings
	public Frame() {
		JFrame f = new JFrame("Kordle");
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		f.setSize(800, 1000);
		f.setBackground(Color.white);
		f.add(this);
		f.setResizable(false);
		f.setLayout(new GridLayout(1,2));
		f.addMouseListener(this);
		f.addKeyListener(this);
		Timer t = new Timer(40, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);	
	}
	
	public static String name(String j, int p) {
		String cat = "name";
		String temp1 = j.substring(j.indexOf("player " + p));
		String temp2 = temp1.substring(temp1.indexOf(cat)+cat.length()+4);
		String res2 = temp2.substring(0, temp2.indexOf(","));
		String res1 = temp2.substring(temp2.indexOf(",")+ 2, temp2.indexOf("\""));
		return res1 + " " + res2;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
			
	}
	

	//Necessary for Frame to run
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}

}