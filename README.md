# Kordle - guess the mystery tennis player!

![kordlesc](https://user-images.githubusercontent.com/23005664/170765586-f4727e28-07df-4a38-92e5-c3b440e81589.PNG)

# How to Play

Enter a top fifty ATP player and click 'enter'

Use the given parameters to see which are correct and incorrect in order to guess the mystery player

↑ means that that parameter's numerical value is greater for the mystery player than it is for the player you guessed, and vice versa for ↓ (for example, if you guessed Carlos Alcaraz but the correct answer was Rafael Nadal, there would be a ↓ on the right of "Highest Ranking," indicating that Nadal's highest ranking is better than Alcaraz's)

You have 5 guesses, if you guess correctly within those 5 guesses you win, but if you don't, you lose

Press 'r' to play again!

# Code Walkthrough

The code for this project is all in a single file

There are two classes, the Driver Class, which contains the majority of the functionality, and the AutoSuggestor Class, which allows us to provide a dropdown meny of valid tennis players to the user

![kordle_code1](https://user-images.githubusercontent.com/23005664/170767371-f35219cc-1a43-4c7c-8c50-435c36fed3e0.PNG)

In the above portion of the Driver Class we are setting up the frame and the initial text entry box

The layout of the game is made possible by a nested JPanel configuration, with the main JPanel being a Grid Layout consisting of smaller Flow Layout and Border Layout panels

This is also where we retrieve all the player data and put it into one string. Later on are several helper methods which allow us to retrieve specific information about each player for this string

One such method, name(), is used above to create an array of all the players' names.

![kordle_code2](https://user-images.githubusercontent.com/23005664/170767375-2e35eb1f-346c-47f8-9066-65914f47f741.PNG)

In the above code we have created an AutoSuggestor object and added all our player names into a dictionary, making it so that when the user enters a letter or series of letters, the drop down returns all valid tennis players whose names begin with that letter or series of letters, as seen here:

![kordle_as](https://user-images.githubusercontent.com/23005664/170768763-bd63512d-7d74-45c7-b9b4-4eb4347062df.PNG)


![kordle_code3](https://user-images.githubusercontent.com/23005664/170769637-13def6f1-ed86-4cde-b76c-174559a1adc2.PNG)

In the check() method shown above, the guessed player is compared to the actual answer. First it checks if it's the correct name or not, and then it checks each individual category to see whether that attribute is the same as that of the answer's attribute (country, handedness, highest ranking, height, current ranking, age), and displays that with either a red background for incorrect or green background for correct. For the quantitative categories (highest ranking, height, current ranking, age), an arrow is added to indicate whether the actual player's value is higher or lower.


![kordle_code4](https://user-images.githubusercontent.com/23005664/170769646-9a90c0ea-536a-489f-be95-79258eaaa8c8.PNG)

In the above section, all the JPanels for the attributes are added to a single FlowLayout JPanel which is then added to the main GridLayout JPanel. Also, an end message is displayed indicated that the player either guessed correctly or ran out of guesses and indicates who the right answer was.


![kordle_code5](https://user-images.githubusercontent.com/23005664/170769656-e8d9d9c3-6281-4c43-9325-ce28f0583f21.PNG)

In the above section, the main() method is created which allows the program to run. Following it is a series of helper methods that retrieves a a player's specific attribute based on their current ranking, and these helper methods are used earlier to display that information on the GUI. 

![kordle_code6](https://user-images.githubusercontent.com/23005664/170769660-d4e1a2b9-35b4-4da6-b307-8e8130a5a2fd.PNG)

The above code looks for keyboard inputs. If the user clicks 'enter', the check() function is called and the guess is actually inputted. Also the number of guesses is incremented by 1. If the user clicks 'r' the game is reset by deleting all the JPanels except the first one and choosing a new random player as the correct answer.

![kordle_code7](https://user-images.githubusercontent.com/23005664/170769669-6c2a4c0e-f5a6-4792-ac8e-edad0c7e2d69.PNG)

Lastly, this section is a method that allows us to change the default font of all JLabels and Text Fields, a much quicker alternative than manually changing the font size or style of each individual label or text field.

