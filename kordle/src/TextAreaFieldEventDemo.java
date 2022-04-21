import java.awt.Frame;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.TextArea;
import java.awt.event.KeyListener;
import java.awt.Color;

import java.awt.Font;

import javax.swing.*;


import java.awt.event.KeyEvent;
class TextAreaFieldEventDemo extends Frame implements KeyListener
{
	TextField Name;
	TextArea textArea;
	TextField Country;
	TextField Handedness;
	TextField Titles; 
	TextField Height;
	TextField CurrentRanking;
	TextField YearTurnedPro;
	private String x; 
	private String answer = "me"; 
	private int guess = 1;
	private boolean correct = false; 
	public static void main(String args[])
	{
		TextAreaFieldEventDemo object = new TextAreaFieldEventDemo();
		object.generateGUI();
	}
	
	private void generateGUI()
	{
		/*
			Setting up frame, its location, its size etc.
		*/
		setTitle("TextArea, TextField and Listener Demo");
		setSize(600,400);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		setVisible(true);
		
		/*
			Adding TextField, TextArea GUI Components
		*/
		
		Name = new TextField("Guess " + guess + " of 8");// 25 is the number of characters to which it expands itself, try with no arguments or 0 value to find the effect
		Name.setBounds(50,100,200,30);
		textArea = new TextArea(5,50); 
		Country = new TextField("America");
		Country.setBounds(50,100,100,30);
		Handedness = new TextField("Right");
		Handedness.setBounds(50,150,100,30);
		Titles = new TextField("Titles");
		Titles.setBounds(50, 200, 100, 30);
		Height = new TextField("Height");
		Height.setBounds(150,100,100,30);
		CurrentRanking = new TextField("Rank");
		CurrentRanking.setBounds(150,150,100,30);
		YearTurnedPro = new TextField("Year");
		YearTurnedPro.setBounds(150,200,100,30);
		
		add(Name);
		//add(textArea);
		//add(Country);
		/*
			Adding listeners to both the textual GUI components
		*/
		Name.addKeyListener(this);
		textArea.addKeyListener(this);
		
			
		
	}
	
	/***** Overriding KeyListener interface's methods *****/
	public void keyPressed(KeyEvent ke)
	{
		/*
			Checking if this event is triggered by text field or text area
		*/
		System.out.println(ke.getKeyCode());
		if(correct == true) {
			Name.setForeground(Color.GREEN);
			Country.setForeground(Color.GREEN);
		}
		if(correct == false) {
			Name.setForeground(Color.RED);
		}
		if(ke.getSource() == Name)
		{
			//System.out.println("Key in Text Field is pressed! And key pressed is: "+ke.getKeyChar());
			x = Name.getText();
			System.out.println(x);
		}
		else if(ke.getSource() == textArea)
		{
			System.out.println("Key in Text Area is pressed! And key pressed is: "+ke.getKeyChar());
		}
		
		if(x.equals(answer)) {
				correct = true;
				
			}
		else {
			correct = false;
			
		}
		System.out.println(correct);
		if(ke.getKeyCode() == 10) {
			guess ++;
			System.out.println(guess);
			if(correct == false) {
				Name.setText("Guess " + guess + " of 8");
			}
		}
		
		if(guess > 1) {
			add(Country);
			add(Handedness);
			add(Titles);
			add(Height);
			add(CurrentRanking);
			add(YearTurnedPro);
		}
			
		
		
	}
	

	
		
		
		
	
	
	
	
	
	
	public void keyReleased(KeyEvent ke)
	{
		
			//Checking if this event is triggered by text field or text area
		
		/*if(ke.getSource() == textField)
		{
			System.out.println("Key in Text Field is released! And key pressed is: "+ke.getKeyChar());
		}
		else if(ke.getSource() == textArea)
		{
			System.out.println("Key in Text Area is released! And key pressed is: "+ke.getKeyChar());
		}*/
	}

	
	public void keyTyped(KeyEvent ke)
	{
		/*
			Checking if this event is triggered by text field or text area
			And this event handling code is disabled because of conflicts it may create.
			Kindly refer to the post to get the answer of what is the conflict and why.
			Here is the link to the post: https://atomic-temporary-112576576.wpcomstaging.com/2017/03/04/how-to-use-textfield-textarea-and-keylistener-keyevent/
		*/
		
		
		
		/* if(ke.getSource() == textField)
		{
			System.out.println("Key in Text Field is typed!");
		}
		else if(ke.getSource() == textArea)
		{
			System.out.println("Key in Text Area is typed!");
		}
		
		*/
	}
}
	

