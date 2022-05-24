import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.io.FileUtils;

/**
 * 
 */
public class Driver implements KeyListener, ActionListener{
	TextField Name;
	//TextArea textArea;
	TextField Country;
	TextField Handedness;
	TextField HighestRanking; 
	TextField Height;
	TextField CurrentRanking;
	TextField Age;
	private String x; 
	private int answer = 11; 
	private int guess = 1;
	private boolean correct = false; 
	private boolean pressed = false;
	private static String players_info = "";
	String[] player_names = new String[50];
	JTextField f = new JTextField(10);
	JFrame frame = new JFrame("Kordle");
	JPanel addText = new JPanel();
	JPanel p = new JPanel(new GridLayout(0, 1, 1, 1));

    public Driver() {

    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(screenSize);
		frame.setBackground(Color.white);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.add(this);
		frame.setResizable(true);
		
		
		//frame.setLayout(new GridLayout(1,2));
		
        

        
        f.addKeyListener(this);
        f.addActionListener(this);
        Timer t = new Timer(40, this);
		t.start();
        //textArea = new TextArea(5,50); 
		//TextField Country = new TextField("country");
		//Country.setBounds(50,100,100,30);
		
		
		
		
		addText.add(new JLabel("Kordle"));

        addText.add(f);
        p.setBackground(Color.white);
        //p.setSize(800, 1000);
        
        p.add(addText);
     
        frame.add(p);
        

        frame.pack();
        frame.setVisible(true);
        
        
		try {
			players_info = FileUtils.readFileToString(new File("players.json"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String cat = "";
		//System.out.println(name(players_info, 2));
		
		for (int i = 1; i <=50; i++) {
			player_names[i-1] = name(players_info, i);
		}
		
		
			
		
	

        AutoSuggestor autoSuggestor = new AutoSuggestor(f, frame, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f) {
            @Override
            boolean wordTyped(String typedWord) {
            	
            	
            	
                //create list for dictionary this in your case might be done via calling a method which queries db and returns results as arraylist
                ArrayList<String> words = new ArrayList<>();
                /*words.add("hello");
                words.add("heritage");
                words.add("happiness");
                words.add("goodbye");
                words.add("cruel");
                words.add("car");
                words.add("war");
                words.add("will");
                words.add("world");
                words.add("wall");
                words.add("America");
                words.add("right");*/
                for (String x : player_names)
                	words.add(x);
                	
                //f.setBackground(Color.white);
                
               

                setDictionary(words);
            
                
                    
                
                return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary
                //return correct;
            }
        };
            
        }
    
    	public void check() {
    		String text = f.getText();
    		JLabel pname = new JLabel(text);
			if(text.equals(name(players_info, answer))) {
	        	correct = true;
	        }
	        else {
	        	correct = false;
	        }
	        System.out.println(correct);
	        System.out.println(answer);
	        System.out.println(text);
	        //frame.add(p);
	        Country = new TextField(country(players_info, name2number(players_info, text)));
	       // Country.setBounds(50,100,100,30);
	        Handedness = new TextField(hand(players_info, name2number(players_info, text)));
			//Handedness.setBounds(150,100,100,30);
			HighestRanking = new TextField(highest(players_info, name2number(players_info, text)));
			//HighestRanking.setBounds(250, 100, 100, 30);
			Height = new TextField(height(players_info, name2number(players_info, text)));
			//Height.setBounds(350,100,100,30);
			CurrentRanking = new TextField("" + name2number(players_info, text));
			//CurrentRanking.setBounds(450,100,100,30);
			Age = new TextField(age(players_info, name2number(players_info, text)));
			//Age.setBounds(550,100,100,30);
	        if(correct) {
	            	f.setBackground(Color.GREEN);
	            	pname.setForeground(Color.GREEN);
	            	
	            }
	            if(correct == false) {
	            	f.setBackground(Color.RED);
	            	pname.setForeground(Color.RED);
	            }
	            
	            //frame.add(p);
	            
	            JPanel pinfo = new JPanel();
            	
        		pinfo.add(new JLabel("Guess " + guess + " of 5"));
            	pinfo.add(Country);
            	pinfo.add(Handedness);
            	pinfo.add(HighestRanking);
            	pinfo.add(Height);
            	pinfo.add(CurrentRanking);
            	pinfo.add(Age);
            	pinfo.add(pname);
            	p.add(pinfo);
            	frame.pack();
                frame.setVisible(true);
            	
    	}

       
    
    
   /* public void paint(Graphics g) {
		//Font stringFont = new Font( "SansSerif", Font. PLAIN, 18 );
		g.setFont(new Font( "SansSerif", Font.BOLD, 42 ));
		g.setColor(Color.CYAN);
		g.drawString("Kordle", 330, 100);
		
	}*/

    public static void main(String[] args) {
        /*SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Test();
            }
        });*/
    	Driver t = new Driver();
        //String t = "he";
        //System.out.println(t.charAt(4));
        
    }
    
    
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
		//pressed = false;
		if (arg0.getKeyCode() == 10) {
			//addToDictionary("bye");//adds a single word
            check();
            System.out.println("enter");
            guess++;
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
    
    
}




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


