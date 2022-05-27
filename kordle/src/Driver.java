import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.io.FileUtils;

/**
 * 
 */
public class Driver implements KeyListener, ActionListener{
	
	
	//Attributes to aid in guessing
	TextField Country;
	TextField Handedness;
	TextField HighestRanking; 
	TextField Height;
	TextField CurrentRanking;
	TextField Age;
	
	//randomly pick player
	private int answer = 1 + (int) (Math.random() * 50); 
	private int guess = 1;
	private boolean correct = false; 
	
	private static String players_info = "";
	String[] player_names = new String[50];
	
	//Main Guess Entry Box
	JPanel addText = new JPanel();
	JTextField f = new JTextField(10);
	
	JFrame frame = new JFrame("Kordle");
	
	//main JPanel
	JPanel p = new JPanel(new GridLayout(0, 1, 1, 1));
	

    public Driver() {
    	//Set default font for all objects
    	setUIFont(new javax.swing.plaf.FontUIResource("Dialog",Font.BOLD,16));
    	
    	//frame settings
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(screenSize);
		frame.setBackground(Color.white);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
        f.addKeyListener(this);
        f.addActionListener(this);
     
		//Make guess entry box
		addText.add(new JLabel("Kordle"));
        addText.add(f);
        p.setBackground(Color.white); 
        p.add(addText);
        frame.add(p);

        frame.pack();
        frame.setVisible(true);
        
        //retrieve player data
		try {
			players_info = FileUtils.readFileToString(new File("players.json"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 1; i <=50; i++) {
			player_names[i-1] = name(players_info, i);
		}
		
		//add player names to autosuggest dictionary (see autosuggestor class at the bottom)
        AutoSuggestor autoSuggestor = new AutoSuggestor(f, frame, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f) {
            @Override
            boolean wordTyped(String typedWord) {

                ArrayList<String> words = new ArrayList<>();
                for (String x : player_names)
                	words.add(x);

                setDictionary(words);
                
                return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary

            }
        };    
        }
    
    	//this method checks if inputted guess is correct
    	public void check() {
    		String text = f.getText();
    		JLabel pname = new JLabel(text);
			if(text.equals(name(players_info, answer))) {
	        	correct = true;
	        }
	        else {
	        	correct = false;
	        }
	        
	        Color r = new Color(255, 115, 115);
	        
	        
	        //display attributes of guessed player, indicate if correct or not, and for numerical data, indicate
	        //whether higher or lower than correct attribute
	        Country = new TextField(country(players_info, name2number(players_info, text)));
	        if (Country.getText().equals(country(players_info, answer)))
	        	Country.setBackground(Color.GREEN);
	        else
	        	Country.setBackground(r);
	        JPanel Countryp = new JPanel(new BorderLayout());
	        Countryp.add(Country, BorderLayout.NORTH);
	        JLabel Countryl = new JLabel("Country");
	        Countryl.setHorizontalAlignment(JLabel.CENTER);
	        Countryp.add(Countryl, BorderLayout.SOUTH);
	        
	       
	        Handedness = new TextField(hand(players_info, name2number(players_info, text)));
	        if (Handedness.getText().equals(hand(players_info, answer)))
	        	Handedness.setBackground(Color.GREEN);
	        else
	        	Handedness.setBackground(r);
	        
	        JPanel Handp = new JPanel(new BorderLayout());
	        Handp.add(Handedness, BorderLayout.NORTH);
	        JLabel Handl = new JLabel("Handedness");
	        Handl.setHorizontalAlignment(JLabel.CENTER);
	        Handp.add(Handl, BorderLayout.SOUTH);
			
	        JLabel Highl = new JLabel();
			HighestRanking = new TextField(highest(players_info, name2number(players_info, text)));
			if (HighestRanking.getText().equals(highest(players_info, answer))) {
		        HighestRanking.setBackground(Color.GREEN);
		        Highl.setText("Highest Ranking");    
			}
		    else {
		        HighestRanking.setBackground(r);
		        if (Integer.parseInt(highest(players_info, answer)) > Integer.parseInt(HighestRanking.getText()))
		        	Highl.setText("Highest Ranking \u2191");
		        else 
			        Highl.setText("Highest Ranking \u2193");	
		    }
			
			JPanel Highp = new JPanel(new BorderLayout());
	        Highp.add(HighestRanking, BorderLayout.NORTH);
	        Highl.setHorizontalAlignment(JLabel.CENTER);
	        Highp.add(Highl, BorderLayout.SOUTH);
			
	        JLabel Heightl = new JLabel();
			Height = new TextField(height(players_info, name2number(players_info, text)));
			if (Height.getText().equals(height(players_info, answer))) {
		        Height.setBackground(Color.GREEN);
		        Heightl.setText("Height");
			}
		    else {
		        Height.setBackground(r);
		        if (Integer.parseInt(heightcm(players_info, answer)) > Integer.parseInt(heightcm(players_info, name2number(players_info, text))))
		        	Heightl.setText("Height \u2191");
		        else
			        Heightl.setText("Height \u2193");
		    }
			
			JPanel Heightp = new JPanel(new BorderLayout());
	        Heightp.add(Height, BorderLayout.NORTH);
	        Heightl.setHorizontalAlignment(JLabel.CENTER);
	        Heightp.add(Heightl, BorderLayout.SOUTH);
			
	        JLabel Currl = new JLabel();
			CurrentRanking = new TextField("" + name2number(players_info, text));
			if (CurrentRanking.getText().equals(answer+"")) {
		        CurrentRanking.setBackground(Color.GREEN);
		        Currl.setText("Current Ranking");
			}
		    else {
		        CurrentRanking.setBackground(r);
		        if (answer > Integer.parseInt(CurrentRanking.getText()))
		        	Currl.setText("Current Ranking \u2191");
		        else
			        Currl.setText("Current Ranking \u2193");
		    }
			
			JPanel Currp = new JPanel(new BorderLayout());
	        Currp.add(CurrentRanking, BorderLayout.NORTH);
	        Currl.setHorizontalAlignment(JLabel.CENTER);
	        Currp.add(Currl, BorderLayout.SOUTH);
			
	        JLabel Agel = new JLabel();
			Age = new TextField(age(players_info, name2number(players_info, text)));
			if (Age.getText().equals(age(players_info, answer))) {
		        Age.setBackground(Color.GREEN);
		        Agel.setText("Age");
			}
		    else {
		        Age.setBackground(r);
		        if (Integer.parseInt(age(players_info, answer)) > Integer.parseInt(Age.getText()))
		        	Agel.setText("Age \u2191");
		        else
			        Agel.setText("Age \u2193");
		    }
			
			JPanel Agep = new JPanel(new BorderLayout());
	        Agep.add(Age, BorderLayout.NORTH);
	        Agel.setHorizontalAlignment(JLabel.CENTER);
	        Agep.add(Agel, BorderLayout.SOUTH);
			
	        if(correct) {
	            f.setBackground(Color.GREEN);
	            pname.setForeground(Color.GREEN); 	
	        }
	        
	        if(correct == false) {
	            f.setBackground(r);
	            pname.setForeground(Color.RED);
	        }
	            
            FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 20, 20);
            JPanel pinfo = new JPanel(fl);
        	
            //add attributes to main jpanel
    		pinfo.add(new JLabel("Guess " + guess + " of 5"));
        	pinfo.add(Countryp);
        	pinfo.add(Handp);
        	pinfo.add(Highp);
        	pinfo.add(Heightp);
        	pinfo.add(Currp);
        	pinfo.add(Agep);
        	pinfo.add(pname);
        	p.add(pinfo);
        	
        	//winning or losing messages
        	if (correct) {
        		JLabel win = new JLabel("Congrats, you won in " + guess + " guesses! Press 'r' to play again.");
        		win.setHorizontalAlignment(JLabel.CENTER);
        		p.add(win);
        	}
        	if (!correct && guess == 5) {
        		JLabel lose = new JLabel("Sorry, you lose :( The correct answer was " + name(players_info, answer) + ". Press 'r' to try again.");
        		lose.setHorizontalAlignment(JLabel.CENTER);
        		p.add(lose);
        	}
        	frame.pack();
            frame.setVisible(true);
    	}

    //main method allows program to run
    public static void main(String[] args) {
    	Driver t = new Driver();
    }
    
    //these methods retrieve specific attributes from the player data to eventually be displayed
    public static String name(String j, int p) {
		String cat = "name";
		String temp1 = j.substring(j.indexOf("player " + p));
		String temp2 = temp1.substring(temp1.indexOf(cat)+cat.length()+4);
		String res2 = temp2.substring(0, temp2.indexOf(","));
		String res1 = temp2.substring(temp2.indexOf(",")+ 2, temp2.indexOf("\""));
		return res1 + " " + res2;
	}
    
    public static String country(String j, int p) {
		String cat = "country";
		String temp1 = j.substring(j.indexOf("player "+p));
		String temp2 = temp1.substring(temp1.indexOf(cat)+cat.length()+4);
		String res2 = temp2.substring(0, temp2.indexOf("\""));
		return res2;
	}
    
    public static int name2number(String j, String name) {
    	String last = name.substring(name.indexOf(" ") + 1);
    	String temp = j.substring(0, j.indexOf(last));
    	while (temp.indexOf("player") != -1) {
    		temp = temp.substring(temp.indexOf("player") + 7);
    	}
    	return Integer.parseInt(temp.substring(0, temp.indexOf("\"")));
    }
    
    public static String hand(String j, int p) {
		String cat = "handedness";
		String temp1 = j.substring(j.indexOf("player "+p));
		String temp2 = temp1.substring(temp1.indexOf(cat)+cat.length()+4);
		String res2 = temp2.substring(0, temp2.indexOf("\""));
		return res2;
	}
    
    public static String height(String j, int p) {
		String cat = "height";
		String temp1 = j.substring(j.indexOf("player "+p));
		String temp2 = temp1.substring(temp1.indexOf(cat)+cat.length()+3);
		String res2 = temp2.substring(0, temp2.indexOf(","));
		int height = Integer.parseInt(res2);
		int feet = (int) (height/30.48);
		double inches = (height/30.48 - feet) * 12;
		return feet + "'" + Math.round(inches) + "\"";
		//return res2;
	}
    
    public static String heightcm(String j, int p) {
		String cat = "height";
		String temp1 = j.substring(j.indexOf("player "+p));
		String temp2 = temp1.substring(temp1.indexOf(cat)+cat.length()+3);
		String res2 = temp2.substring(0, temp2.indexOf(","));
		return res2;
	}
    
    public static String highest(String j, int p) {
		String cat = "highest_singles_ranking";
		String temp1 = j.substring(j.indexOf("player "+p));
		String temp2 = temp1.substring(temp1.indexOf(cat)+cat.length()+3);
		String res2 = temp2.substring(0, temp2.indexOf(","));
		int height = Integer.parseInt(res2);
		return height + "";
		//return res2;
	}
    
    public static String age(String j, int p) {
		String cat = "date_of_birth";
		String temp1 = j.substring(j.indexOf("player "+p));
		String temp2 = temp1.substring(temp1.indexOf(cat)+cat.length()+4);
		String res2 = temp2.substring(0, temp2.indexOf("\""));
		int year = Integer.parseInt(res2.substring(0, 4));
		int age = 2022-year;
		if (Integer.parseInt(res2.substring(5, 7)) > 5)
			age--;
		//double inches = (height/30.48 - feet) * 12;
		return "" + age;
		//return res2;
	}
    
    
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
		//if enter is pressed then guess is inputed
		if (arg0.getKeyCode() == 10 && guess < 6) {
            check();
            System.out.println("enter");
            guess++;
            f.setText("");
		}
		
		//if r is pressed the game resets by removing all the components from the jpanel except the first one
		if (arg0.getKeyCode() == 82 && guess > 5) {
			guess = 1;
			//Get the components in the panel
			Component[] componentList = p.getComponents();

			for(Component c : componentList){
			    if(!c.equals(addText)){
			        p.remove(c);
			    }
			}

			p.revalidate();
			p.repaint();
			
			//new random player
			answer = 1 + (int) (Math.random() * 50);
			f.setBackground(Color.WHITE);
			f.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
    
	//this method allows us to change the default font for all swing objects, which is much easier than manually changing everything
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
	    java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get (key);
	      if (value instanceof javax.swing.plaf.FontUIResource)
	        UIManager.put (key, f);
	      }
	}
    
}

/*  Following code is from https://stackoverflow.com/questions/14186955/create-a-autocompleting-textbox-in-java-with-a-dropdown-list
 * 	This class allows the user to autocomplete their guesses for convenience.
 *  It also eliminates incorrect guesses due to spelling errors.
 */

class AutoSuggestor {

    private final JTextField textField;
    private final Window container;
    private JPanel suggestionsPanel;
    private JWindow autoSuggestionPopUpWindow;
    private String typedWord;
    private final ArrayList<String> dictionary = new ArrayList<>();
    private int currentIndexOfSpace, tW, tH;
    private DocumentListener documentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent de) {
            checkForAndShowSuggestions();
        }

        @Override
        public void removeUpdate(DocumentEvent de) {
            checkForAndShowSuggestions();
        }

        @Override
        public void changedUpdate(DocumentEvent de) {
            checkForAndShowSuggestions();
        }
    };
    private final Color suggestionsTextColor;
    private final Color suggestionFocusedColor;

    public AutoSuggestor(JTextField textField, Window mainWindow, ArrayList<String> words, Color popUpBackground, Color textColor, Color suggestionFocusedColor, float opacity) {
        this.textField = textField;
        this.suggestionsTextColor = textColor;
        this.container = mainWindow;
        this.suggestionFocusedColor = suggestionFocusedColor;
        this.textField.getDocument().addDocumentListener(documentListener);

        setDictionary(words);

        typedWord = "";
        currentIndexOfSpace = 0;
        tW = 0;
        tH = 0;

        autoSuggestionPopUpWindow = new JWindow(mainWindow);
        autoSuggestionPopUpWindow.setOpacity(opacity);

        suggestionsPanel = new JPanel();
        suggestionsPanel.setLayout(new GridLayout(0, 1));
        suggestionsPanel.setBackground(popUpBackground);

        addKeyBindingToRequestFocusInPopUpWindow();
    }

    private void addKeyBindingToRequestFocusInPopUpWindow() {
        textField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
        textField.getActionMap().put("Down released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {//focuses the first label on popwindow
                for (int i = 0; i < suggestionsPanel.getComponentCount(); i++) {
                    if (suggestionsPanel.getComponent(i) instanceof SuggestionLabel) {
                        ((SuggestionLabel) suggestionsPanel.getComponent(i)).setFocused(true);
                        autoSuggestionPopUpWindow.toFront();
                        autoSuggestionPopUpWindow.requestFocusInWindow();
                        suggestionsPanel.requestFocusInWindow();
                        suggestionsPanel.getComponent(i).requestFocusInWindow();
                        break;
                    }
                }
            }
        });
        suggestionsPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
        suggestionsPanel.getActionMap().put("Down released", new AbstractAction() {
            int lastFocusableIndex = 0;

            @Override
            public void actionPerformed(ActionEvent ae) {//allows scrolling of labels in pop window (I know very hacky for now :))

                ArrayList<SuggestionLabel> sls = getAddedSuggestionLabels();
                int max = sls.size();

                if (max > 1) {//more than 1 suggestion
                    for (int i = 0; i < max; i++) {
                        SuggestionLabel sl = sls.get(i);
                        if (sl.isFocused()) {
                            if (lastFocusableIndex == max - 1) {
                                lastFocusableIndex = 0;
                                sl.setFocused(false);
                                autoSuggestionPopUpWindow.setVisible(false);
                                setFocusToTextField();
                                checkForAndShowSuggestions();//fire method as if document listener change occured and fired it

                            } else {
                                sl.setFocused(false);
                                lastFocusableIndex = i;
                            }
                        } else if (lastFocusableIndex <= i) {
                            if (i < max) {
                                sl.setFocused(true);
                                autoSuggestionPopUpWindow.toFront();
                                autoSuggestionPopUpWindow.requestFocusInWindow();
                                suggestionsPanel.requestFocusInWindow();
                                suggestionsPanel.getComponent(i).requestFocusInWindow();
                                lastFocusableIndex = i;
                                break;
                            }
                        }
                    }
                } else {//only a single suggestion was given
                    autoSuggestionPopUpWindow.setVisible(false);
                    setFocusToTextField();
                    checkForAndShowSuggestions();//fire method as if document listener change occured and fired it
                }
            }
        });
    }
    
    

    private void setFocusToTextField() {
        container.toFront();
        container.requestFocusInWindow();
        textField.requestFocusInWindow();
    }

    public ArrayList<SuggestionLabel> getAddedSuggestionLabels() {
        ArrayList<SuggestionLabel> sls = new ArrayList<>();
        for (int i = 0; i < suggestionsPanel.getComponentCount(); i++) {
            if (suggestionsPanel.getComponent(i) instanceof SuggestionLabel) {
                SuggestionLabel sl = (SuggestionLabel) suggestionsPanel.getComponent(i);
                sls.add(sl);
            }
        }
        return sls;
    }

    private void checkForAndShowSuggestions() {
        typedWord = getCurrentlyTypedWord();

        suggestionsPanel.removeAll();//remove previos words/jlabels that were added

        //used to calcualte size of JWindow as new Jlabels are added
        tW = 0;
        tH = 0;

        boolean added = wordTyped(typedWord);

        if (!added) {
            if (autoSuggestionPopUpWindow.isVisible()) {
                autoSuggestionPopUpWindow.setVisible(false);
            }
        } else {
            showPopUpWindow();
            setFocusToTextField();
        }
    }

    protected void addWordToSuggestions(String word) {
        SuggestionLabel suggestionLabel = new SuggestionLabel(word, suggestionFocusedColor, suggestionsTextColor, this);

        calculatePopUpWindowSize(suggestionLabel);

        suggestionsPanel.add(suggestionLabel);
    }

    public String getCurrentlyTypedWord() {//get newest word after last white spaceif any or the first word if no white spaces
        String text = textField.getText();
        String wordBeingTyped = "";
        /*if (text.contains(" ")) {
            int tmp = text.lastIndexOf(" ");
            if (tmp >= currentIndexOfSpace) {
                currentIndexOfSpace = tmp;
                wordBeingTyped = text.substring(text.lastIndexOf(" "));
            }
        } else {
            wordBeingTyped = text;
        }*/
        wordBeingTyped = text;
        return wordBeingTyped.trim();
    }

    private void calculatePopUpWindowSize(JLabel label) {
        //so we can size the JWindow correctly
        if (tW < label.getPreferredSize().width) {
            tW = label.getPreferredSize().width;
        }
        tH += label.getPreferredSize().height;
    }

    private void showPopUpWindow() {
        autoSuggestionPopUpWindow.getContentPane().add(suggestionsPanel);
        autoSuggestionPopUpWindow.setMinimumSize(new Dimension(textField.getWidth(), 30));
        autoSuggestionPopUpWindow.setSize(tW, tH);
        autoSuggestionPopUpWindow.setVisible(true);

        int windowX = 0;
        int windowY = 0;

        windowX = container.getX() + textField.getX() + 5;
        if (suggestionsPanel.getHeight() > autoSuggestionPopUpWindow.getMinimumSize().height) {
            windowY = container.getY() + textField.getY() + textField.getHeight() + autoSuggestionPopUpWindow.getMinimumSize().height;
        } else {
            windowY = container.getY() + textField.getY() + textField.getHeight() + autoSuggestionPopUpWindow.getHeight();
        }

        autoSuggestionPopUpWindow.setLocation(windowX, windowY);
        autoSuggestionPopUpWindow.setMinimumSize(new Dimension(textField.getWidth(), 30));
        autoSuggestionPopUpWindow.revalidate();
        autoSuggestionPopUpWindow.repaint();

    }

    public void setDictionary(ArrayList<String> words) {
        dictionary.clear();
        if (words == null) {
            return;//so we can call constructor with null value for dictionary without exception thrown
        }
        for (String word : words) {
            dictionary.add(word);
        }
    }

    public JWindow getAutoSuggestionPopUpWindow() {
        return autoSuggestionPopUpWindow;
    }

    public Window getContainer() {
        return container;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void addToDictionary(String word) {
        dictionary.add(word);
    }

    boolean wordTyped(String typedWord) {

        if (typedWord.isEmpty()) {
            return false;
        }
        //System.out.println("Typed word: " + typedWord);

        boolean suggestionAdded = false;

        for (String word : dictionary) {//get words in the dictionary which we added
            boolean fullymatches = true;
            for (int i = 0; i < typedWord.length(); i++) {//each string in the word
                if (!typedWord.toLowerCase().startsWith(String.valueOf(word.toLowerCase().charAt(i)), i)) {//check for match
                    fullymatches = false;
                    break;
                }
            }
            if (fullymatches) {
                addWordToSuggestions(word);
                suggestionAdded = true;
            }
        }
        return suggestionAdded;
    }
}

class SuggestionLabel extends JLabel {

    private boolean focused = false;
    private final JWindow autoSuggestionsPopUpWindow;
    private final JTextField textField;
    private final AutoSuggestor autoSuggestor;
    private Color suggestionsTextColor, suggestionBorderColor;

    public SuggestionLabel(String string, final Color borderColor, Color suggestionsTextColor, AutoSuggestor autoSuggestor) {
        super(string);

        this.suggestionsTextColor = suggestionsTextColor;
        this.autoSuggestor = autoSuggestor;
        this.textField = autoSuggestor.getTextField();
        this.suggestionBorderColor = borderColor;
        this.autoSuggestionsPopUpWindow = autoSuggestor.getAutoSuggestionPopUpWindow();

        initComponent();
    }

    private void initComponent() {
        setFocusable(true);
        setForeground(suggestionsTextColor);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);

                replaceWithSuggestedText();

                autoSuggestionsPopUpWindow.setVisible(false);
            }
        });

        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "Enter released");
        getActionMap().put("Enter released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                replaceWithSuggestedText();
                autoSuggestionsPopUpWindow.setVisible(false);
            }
        });
    }

    public void setFocused(boolean focused) {
        if (focused) {
            setBorder(new LineBorder(suggestionBorderColor));
        } else {
            setBorder(null);
        }
        repaint();
        this.focused = focused;
    }

    public boolean isFocused() {
        return focused;
    }

    private void replaceWithSuggestedText() {
        String suggestedWord = getText();
        String text = textField.getText();
        String typedWord = autoSuggestor.getCurrentlyTypedWord();
        String t = text.substring(0, text.lastIndexOf(typedWord));
        String tmp = t + text.substring(text.lastIndexOf(typedWord)).replace(typedWord, suggestedWord);
        textField.setText(tmp + "");
    }
}



