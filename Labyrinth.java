/*
Java™ Project: ICS3U
Package: finalProject
Class: Labyrinth
Programmer: Shaan Banday.
Teacher: Mr. Elliott.
Date Created: Monday, 26th October, 2020.
Date Completed: Tuesday, 10th November, 2020. 
Program Description: Welcome to Labyrinth, a computer-based maze game coded in Java™ using the Eclipse IDE. Based on the classic 1986 board game of
the same name, the game of Labyrinth is targeted towards a 5-10 age demographic. The goal of the game is for the user to get their character to 
cross the finish line by moving it around with the arrow keys or the WASD keys. After the user-controlled character traverses the finish line, 
located at the end of the maze, they win the game. However, there are deadly walls in the maze that the user cannot pass nor touch. Furthermore, 
there is a move counter, which incrementally decreases after each key-press. Incidentally, the user must be closely attentive to make sure they 
do not make an erroneous blunder. When the user hits any of the obstructing walls or runs out of moves — whichever comes first — the game is 
subsequently lost. 
There are also three separate and distinct levels, each with an added amount of difficulty. Level 1 is trivial since there are not many walls, and
there are 750 moves available for the user. Contrastingly, Level 2 has many more walls while also only having 700 key-presses available. Lastly, 
Level 3 has the most number of walls and the least number of moves accessible by the user.
For more information, press the instructions button on the main screen.
*/
package finalProject; //Launch the class from this package named "finalProject"

//Import all these methods for graphics and program functionality
import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class Labyrinth extends JFrame implements ActionListener, KeyListener //The name of the class is "Labyrinth" and it implements an ActionListener for buttons, and a KeyListener for taking input from the keyboard
{ //Beginning of class "Labyrinth"
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6964878978264551897L;
	//Declare all graphical objects
	JPanel labyrinthPanel; //Panel to hold everything
	JButton level1, level2, level3, instructions, backToStart, playAgain; //Declare 3 Buttons for going to each level, a button to go to the "instructions," and two buttons that reset the level, and the entire game
	JButton [] menuButtons; //Declare an array of buttons, to hold the 3 level buttons, and the "instructions" button. These are all the menu elements
	JLabel menuLabel; //Declare a JLabel which will display the title of the game on the menu
	
	//Declare all variables
	int characterX; //X-location of character in terms of pixels away from the top left corner. That means top left corner would have the coordinate (0, 0)
    int characterY; //Y-location of character in terms of pixels away from the top left corner 
    int movesLeft; //Integer to store the number of moves left, which is initialised differently in each level
    boolean lose1 = false, lose2 = false, lose3 = false ; //Declare 3 lose booleans, for each level. If these booleans are false, then the game is still running. Otherwise, if the booleans are true, the guser has lost the game
    boolean win = false; //Declare only 1 win boolean for all 3 levels. There is only 1 since all 3 levels have the same outer boundaries, and the finish line is at the same place. If the win boolean is false, then the game continues. Contrastingly, if the win boolean is true, the game user has won the game 
    boolean play1 = false, play2 = false, play3 = false; //Declare 3 lose booleans, for each level. If the booleans are false, the corresponding level will not open. Otherwise, if the booleans are true, then that level will be played. NOTE: Only one of these booleans can be true at a time, since the user cannot play multiple levels at the same time
    boolean inst = false; //Declare 1 boolean for the instructions menu. The instructions menu is opened when this boolean turns true
    boolean menu = true; //Declare 1 boolean for the state of the menu. If true, that means the start screen is up. 
    
    //Declare all constants
    final int F_WIDTH = 1300, F_HEIGHT = 700; //Width and Height of the JFrame
    final int START_X = 30, START_Y = 60; //Starting X and Y position (coordinate) of the character.
    final int MOVEMENT = 5; //The increment in pixels by which the character moves.
    
    //Declare all fonts
	Font titleFont = new Font("Century Schoolbook", Font.BOLD, 100); //Font for the menuLabel, and therefore; the title of the game.
	Font subheadingFont = new Font("Times Roman", Font.BOLD, 26); //Font for the sub-heading information on the instructions screen
	Font buttonFont = new Font("Century Schoolbook", Font.PLAIN, 20); //Font for the buttons
	Font instFont = new Font("Copperplate Gothic Bold", Font.BOLD, 36); //Font for the instructions
	
	//Declare all Colours
	Color labGreen = new Color (18, 118, 120);
	Color labBlue = new Color (48, 181, 164);
	Color labRed = new Color (180, 217, 210);
	
	//Declare all Images
	Image character; //Image of the user-controlled character
	Image finishLine; //Image of the finish-line
	
	
	public Labyrinth() //Constructor which initialises the object of the class, and displays all the proper graphical components. Name must be the same as the class
	{ //Start of Constructor
		
		super("Labyrinth"); //Name of JFrame/window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Terminate the program, and close the window if the close button is hit, Without this, program goes forever. 
        this.setSize(F_WIDTH, F_HEIGHT); //Set the size of the window (JFrame) in pixels to the constants initialised outside the constructor        
        
        //Initialise JPanel
        labyrinthPanel = (JPanel) this.getContentPane();  //Create a JPanel to organise contents in the JFrame/Window  
        labyrinthPanel.setLayout(null); //Assign no layout (null) to the JPanel
        labyrinthPanel.setBackground(Color.BLACK); //Set the colour of the background as black. 
        
        //Starting parameters of the character
        characterX = START_X; //Set the starting X-position of the character to the constants initialised outside the constructor
        characterY = START_Y; //Set starting Y-position
        character = Toolkit.getDefaultToolkit().getImage("pictures/character.jpg"); //Obtain the photo of the character from the picture folder in the ICS3U folder
        finishLine = Toolkit.getDefaultToolkit().getImage("pictures/checker.jpg"); //Obtain the photo of the character from the picture folder in the ICS3U folder
        
        menuButtons = new JButton[4]; //Set the size of the buttons array to 4
        createBL(); //Create all buttons, labels, and add the KeyListener by calling this method
        this.setResizable(false); //Window is not unable to be resized.
        this.setVisible(true); //Everything in the JFrame is visible, unless otherwise specified  
        this.repaint(); //Invoke paint method, and update the screen 
	} //End of Constructor
	
	public void createBL() //Void method that creates all of the buttons and labels, while adding them to the JPanel. Moreover, the method adds some of the buttons to the menuButtons[] Array
	{ //Commence method if called
		//Declare constants
		final int BUTTON_X = 90, BUTTON_Y = 210, BUTTON_WIDTH = 300, BUTTON_HEIGHT = 200; //Set the X and Y coordinates of the top left corner of the buttons, while also setting the Width and Height
		
		//Initialise and set the parameters for the Menu Label
    	menuLabel = new JLabel("Labyrinth"); //Label will display the word "Labyrinth"
    	menuLabel.setForeground(Color.YELLOW); //Set the colour of the label text to yellow
    	menuLabel.setFont(titleFont); //Set the Font of the label to titleFont, which was declared outside this method
    	menuLabel.setBounds(350, 90, 700, 120); //Set the X and Y coordinates of the label, while also declaring the height and width
    	labyrinthPanel.add(menuLabel); //Add the label to the JPanel
    	
    	//Level 1 Button
    	level1 = new JButton("Level 1: EASY"); //The button for level 1 will display this text
    	level1.setBounds(BUTTON_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT); //Set the coordinates of the top left corner, and the width + height
    	level1.addActionListener(this); //Add an ActionListener to the button. Without this, pressing the button will not do anything
    	level1.setFocusable(false); //After the button is pressed, remove the focus off it so that the Keyboard can interact with the program
    	level1.setBackground(labGreen); //Set the background of the button to the custom green colour declared outside this method
    	level1.setForeground(Color.BLACK); //Set the foreground (colour of the text) to black
    	level1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.WHITE, Color.WHITE)); //Set the border of the button to a Raised Border, with a white highlight
    	level1.setFont(buttonFont); //Set the font of the button to buttonFont, which was declared outside this method
    	menuButtons[0] = level1; //Add level1 to the 0th index of the menuButtons[] array
    	labyrinthPanel.add(level1); //Add the button to the JPanel
    	
    	//Level 2 Button
    	level2 = new JButton("Level 2: MEDIUM"); //The button for level 2 will display this text
    	level2.setBounds(BUTTON_X + 400, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT); //Set the coordinates of the top left corner, and the width + height. This button is 400 pixels to the right of the previous button
    	level2.addActionListener(this); //Add an ActionListener so that the button will do something when it is pressed
    	level2.setFocusable(false); //After the button is pressed, remove the focus to allow Keyboard interaction
    	level2.setBackground(labRed); //Set background of the button to the custom red colour
    	level2.setForeground(Color.BLACK); //Set the colour of the font to pitch black
    	level2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.WHITE, Color.WHITE)); //Set the border of the button to a Raised Border, with a white highlight
    	level2.setFont(buttonFont); //Set the font of the text on the button to buttonText
    	menuButtons[1] = level2; //Add level2 to the 1st index of the menuButtons[] array
    	labyrinthPanel.add(level2); //Add level2 to the main panel
    	
    	//Level 3 Button
    	level3 = new JButton("Level 3: HARD"); //The button for level 3 will display this text
    	level3.setBounds(BUTTON_X + 800, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT); //Set the coordinates of the top left corner, and the width + height. This button is 400 pixels to the right of the previous button
    	level3.addActionListener(this); //Add an ActionListener to do something when the button is pressed
    	level3.setFocusable(false); //After the button is pressed, remove the focus
    	level3.setBackground(labBlue); //Set background of the button to the custom blue colour
    	level3.setForeground(Color.BLACK); //Set the colour of the font to pitch black
    	level3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.WHITE, Color.WHITE)); //Set the border of the button to a Raised Border, with a white highlight
    	level3.setFont(buttonFont); //Set the font of the text on the button to buttonText
    	menuButtons[2] = level3; //Add level3 to the 2nd index of the menuButtons[] array
    	labyrinthPanel.add(level3); //Add level3 to labyrinthPanel
    	
    	//Instructions Button
    	instructions = new JButton("Press to Read the Instructions"); //The instructions button will display this text
    	instructions.setBounds(BUTTON_X + 340, BUTTON_Y + 240, 400, 200); //Set the coordinates of the top left corner, and the height + width. This button is a different size compared to the level buttons
    	instructions.addActionListener(this); //Add an ActionListener to do something when the button is pressed
    	instructions.setFocusable(false); //After the button is pressed, give focus to keyboard
    	instructions.setFont(buttonFont); //Set the font of the text on the button to buttonText
    	instructions.setBackground(Color.YELLOW); //Set background of the button to yellow
    	instructions.setForeground(Color.BLACK); //Set the colour of the font to black
    	instructions.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.WHITE, Color.WHITE)); //Set the border of the button to a Raised Border, with a white highlight
    	menuButtons[3] = instructions; //Add instructions to the 3rd index of the menuButtons[] array
    	labyrinthPanel.add(instructions); //Add the button to the JPanel
    	
    	//Go Back to the Start Menu Button
    	backToStart = new JButton("Press to go Back to the Start"); //The backToStart button will display this text
    	backToStart.setBounds(440, 130, 400, 250); //Set the coordinates of the top left corner, and the height + width. Again, this is different than the other buttons
    	backToStart.addActionListener(this); //Add an action listener
    	backToStart.setFocusable(false); //Set the focus to false
    	backToStart.setVisible(false); //Set the button as invisible (for now)
    	backToStart.setFont(buttonFont); //Give the button the same font as the other buttons
    	backToStart.setBackground(Color.YELLOW); //Set the colour of the button to yellow
    	backToStart.setForeground(Color.BLACK); //Set the foreground (colour of text) to black
    	backToStart.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.WHITE, Color.WHITE)); //Give the button the same border as the other buttons
    	labyrinthPanel.add(backToStart); //Add button to the main panel
    	
    	//Play Again Button to restart the current level the user is on
    	playAgain = new JButton("Press to Re-play level"); //The playAgain button will display this text
    	playAgain.setBounds(440, 390, 400, 250);//Set the coordinates of the top left corner, and the height + width. Different from rest of buttons
    	playAgain.addActionListener(this); //Add an action listener
    	playAgain.setFocusable(false); //Set the focus to false
    	playAgain.setVisible(false); //Set the button as invisible (for now)
    	playAgain.setFont(buttonFont); //Give the button the same font as all the others 
    	playAgain.setBackground(Color.YELLOW); //Set the background colour to yellow
    	playAgain.setForeground(Color.BLACK); //Set the colour of the text to black
    	playAgain.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.WHITE, Color.WHITE)); //Give the button the same border as the other buttons
    	labyrinthPanel.add(playAgain); //Add the playAgain button to the main panel
    	
    	//Let KeyListener affect the panel
    	addKeyListener(this); //Add the key listener
        setFocusable(true); //Make it the focal point if it is used
        setFocusTraversalKeysEnabled(false); //Do not focus traversal keys (shift, tab, windows key, etc...)
	} //Termination of Method
	
	public void actionPerformed(ActionEvent e) //Create void ActionListener method, to be invoked when a button is pressed
	{ //Start of Method
		//Declare all Variables
		String buttonName = e.getActionCommand(); //String variable that replicates the button name. E.g, If level1 is pressed, buttonName = "level1"
		
		//Decisions
		switch (buttonName) //Switch statement based on the name of the button is pressed
	    { 
	        case "Level 1: EASY": //If the Level 1 Button is pressed
	        	invisibleButtons(); //Call the invisibleButtons() method, which sets all buttons in the menuButtons[] array to invisible
	        	menuLabel.setVisible(false); //Make the menuLabel invisible, since the starting screen is no longer visible
	        	movesLeft = 750; //Set the number of moves left to 750 for level 1
	        	play1 = true; //Set play1 to true. This means that the first level will be painted onto the JPanel
	        	lose1 = false; //Set lose1 to false (for now), since the user cannot start the game and immediately lose
	        	break; 
	        case "Level 2: MEDIUM": //If the Level 2 Button is pressed
	        	invisibleButtons(); //Make all menu buttons invisible
	        	menuLabel.setVisible(false); //Make the menuLabel invisible
	        	movesLeft = 700; //Set the number of moves left to 700 for level 2
	        	play2 = true; //Set play2 to true. This means that the second level will be painted onto the JPanel
	        	lose2 = false; //Set lose2 to false (for now)
	        	break;
	        case "Level 3: HARD": //If the Level 3 Button is pressed
	        	invisibleButtons(); //Make all menu buttons invisible
	        	menuLabel.setVisible(false); //Make the menuLabel invisible
	        	movesLeft = 650; //Set the number of moves left to 650 for level 3. This is going down by 50 each level, indicating added difficulty
	        	play3 = true; //Set play3 to true. This means that the second level will be painted onto the JPanel
	        	lose3 = false; //Set lose3 to false (for now)
	        	break;
	        case "Press to Read the Instructions": //If the Instructions Button is pressed
	        	menuLabel.setVisible(false); //Set if the menuLabel is visible to false
	        	invisibleButtons(); //Make all menu buttons invisible
	        	inst = true; //Set the boolean inst to true, which would paint the instructions screen 
	            break; 
	        case "Press to go Back to the Start": //If the backToStart Button is pressed
	        	backToStart.setVisible(false); //Set the backToStart Button to invisible when it is pressed
	        	playAgain.setVisible(false); //Set the play again button to invisible as well
	        	win = false; //Set Win to false
	        	lose1 = false; //Set lose 1, 2, and 3 to false
	        	lose2 = false;
	        	lose3 = false;
	        	inst = false; //Set inst to false
	        	play1 = false; //Set play 1, 2, and 3 to false
	        	play2 = false;
	        	play3 = false;
	        	
	        	//Reset Coordinates of Character
	        	characterX = START_X; //Reset X-position of the character to START_X
	        	characterY = START_Y; //Reset Y-position of the character to START_Y
	        	break;
	        case "Press to Re-play level": //If playAgain Button is pressed
	        	//Edit Button Parameters
	        	playAgain.setVisible(false); //Set the playAgain button to invisible
	        	backToStart.setVisible(false); //Set the playAgain button to invisible
	        	
	        	//Decisions
	        	if (play1) //If playAgain button is pressed, AND the user is on level 1
	        	{
	        		movesLeft = 750; //Reset movesLeft to 750
	        	}
	        	else if (play2) //If the user is on level 2
	        	{ 
	        		movesLeft = 700; //Reset movesLeft to 700
	        	}
	        	else //If the user is on level 3
	        	{
	        		movesLeft = 650; //Reset movesLeft to 650
	        	}
	        	
	        	//Make all corresponding booleans false
	        	win = false;
	        	lose1 = false;
	        	win = false;
	        	lose2 = false;
	        	lose3 = false;

	        	//Reset Coordinates of Character
	        	characterX = START_X; //Reset X-position of the character to START_X
	        	characterY = START_Y; //Reset Y-position of the character to START_Y
	        	break;
	    }  //End of switch statement
	    repaint();  //Invoke paint method, and update screen 
	}  //End of ActionListener method
	
	public void invisibleButtons() //Void Method that makes all the button in the menuButtons[] Array invisible
	{ //Start of method
		 for (int q = 0; q < menuButtons.length; q++) //For loop with the increment by 1 each time, and running until the counter "q" is no longer less than the length of the array
		 {
			 menuButtons[q].setVisible(false); //Set the button at index q to invisible
		 }
	} //End of method
	
	public void visibleButtons() //Void Method that makes all the button in the menuButtons[] Array visible
	{ //Start of method
		 for (int w = 0; w < menuButtons.length; w++) //For loop with the increment by 1 each time, and running until the counter "q" is no longer less than the length of the array
		 {
			 menuButtons[w].setVisible(true); //Set the button at index w to visible
		 }
	} //End of method
	
	public void keyPressed(KeyEvent e) //Void KeyListener method, to be invoked if any key is pressed by the user
	{ //Start of Method
		 //Declare all variables
		 int pressedCode = e.getKeyCode(); //Integer variable to store the key code since up, down, left, and right arrows do not have an ASCII code
		 
		 //Decisions
		 if (play1) //If the user is on level 1
		 {
			 //Nested Decisions
			 if ((!win) && (!lose1)) //If if the game is not over, only then let the keyboard affect the JFrame
			 {
		        switch (pressedCode) //Switch statement based on what key is pressed 
		        { 
		        case KeyEvent.VK_UP: //If the UP arrow key is pressed
		            characterY -= MOVEMENT; //Decrease y value of character by whatever value MOVEMENT is
		            if (characterY < 50) //If the Y value of the character is less than 50 (i.e. the user it hitting the top wall)
		        	{
		        		movesLeft -= 0; //Do not subtract any moves
		        	}
		        	else //If the Y value of the character is greater than, or equal to 50
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_DOWN: //If the DOWN arrow key is pressed
		        	characterY += MOVEMENT; //Increase y value of character
		        	if (characterY > 635) //If the Y-value of the character is greater than 635, which would mean the user is hitting the bottom boundary
		        	{
		        		movesLeft -= 0; //Do not subtract any moves 
		        	}
		        	else //If the Y value of the character is less than, or equal to 635
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_LEFT: //If LEFT arrow key is pressed
		        	characterX -= MOVEMENT; //Decrease x value of character
		        	if (characterX < 10) //If the X-value of the character is less than 10, which would mean the user is hitting the left boundary
		        	{
		        		movesLeft -= 0; //Do not subtract any moves 
		        	}
		        	else //If the x value of the character is greater than, or equal to 10
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_RIGHT: //If RIGHT arrow key is pressed
		        	characterX += MOVEMENT; //Increase x value of character
		        	if (characterX > 1250) //If the X-value of the character is greater than 1250, which would mean the user is hitting the right boundary
					{
		        		movesLeft -= 0; //Do not subtract any moves 
					}
		        	else
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_W: //If W is pressed
		        	characterY -= MOVEMENT; //Decrease y value of character by whatever value MOVEMENT is
		            if (characterY < 50) //If the Y value of the character is less than 50 (i.e. the user it hitting the top wall)
		        	{
		        		movesLeft -= 0; //Do not subtract any moves
		        	}
		        	else //If the Y value of the character is greater than, or equal to 50
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_S: //If S is pressed
		        	characterY += MOVEMENT; //Increase y value of character
		        	if (characterY > 635) //If the Y-value of the character is greater than 635, which would mean the user is hitting the bottom boundary
		        	{
		        		movesLeft -= 0; //Do not subtract any moves 
		        	}
		        	else //If the Y value of the character is less than, or equal to 635
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_A: //If A is pressed
		        	characterX -= MOVEMENT; //Decrease x value of character
		        	if (characterX < 10) //If the X-value of the character is less than 10, which would mean the user is hitting the left boundary
		        	{
		        		movesLeft -= 0; //Do not subtract any moves 
		        	}
		        	else //If the x value of the character is greater than, or equal to 10
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_D: //If D is pressed
		        	characterX += MOVEMENT; //Increase x value of character
		        	if (characterX > 1250) //If the X-value of the character is greater than 1250, which would mean the user is hitting the right boundary
					 {
		        		movesLeft -= 0; //Do not subtract any moves 
					 }
		        	else
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break;
		        }  //End of switch statement
			 }
		 }
		 else if (play2) //If the user is on level 2
		 {
			 //Nested Decisions
			 if ((!win) && (!lose2)) //If if the game is not over, only then let the keyboard affect the JFrame
			 {
		        switch (pressedCode) //Switch statement based on what key is pressed 
		        { 
		        case KeyEvent.VK_UP: //If the UP arrow key is pressed
		            characterY -= MOVEMENT; //Decrease y value of character by whatever value MOVEMENT is
		            if (characterY < 50) //If the Y value of the character is less than 50 (i.e. the user it hitting the top wall)
		        	{
		        		movesLeft -= 0; //Do not subtract any moves
		        	}
		        	else //If the Y value of the character is greater than, or equal to 50
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_DOWN: //If the DOWN arrow key is pressed
		        	characterY += MOVEMENT; //Increase y value of character
		        	if (characterY > 635) //If the Y-value of the character is greater than 635, which would mean the user is hitting the bottom boundary
		        	{
		        		movesLeft -= 0; //Do not subtract any moves 
		        	}
		        	else //If the Y value of the character is less than, or equal to 635
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_LEFT: //If LEFT arrow key is pressed
		        	characterX -= MOVEMENT; //Decrease x value of character
		        	if (characterX < 10) //If the X-value of the character is less than 10, which would mean the user is hitting the left boundary
		        	{
		        		movesLeft -= 0; //Do not subtract any moves 
		        	}
		        	else //If the x value of the character is greater than, or equal to 10
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_RIGHT: //If RIGHT arrow key is pressed
		        	characterX += MOVEMENT; //Increase x value of character
		        	if (characterX > 1250) //If the X-value of the character is greater than 1250, which would mean the user is hitting the right boundary
					{
		        		movesLeft -= 0; //Do not subtract any moves 
					}
		        	else
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_W: //If W is pressed
		        	characterY -= MOVEMENT; //Decrease y value of character by whatever value MOVEMENT is
		            if (characterY < 50) //If the Y value of the character is less than 50 (i.e. the user it hitting the top wall)
		        	{
		        		movesLeft -= 0; //Do not subtract any moves
		        	}
		        	else //If the Y value of the character is greater than, or equal to 50
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_S: //If S is pressed
		        	characterY += MOVEMENT; //Increase y value of character
		        	if (characterY > 635) //If the Y-value of the character is greater than 635, which would mean the user is hitting the bottom boundary
		        	{
		        		movesLeft -= 0; //Do not subtract any moves 
		        	}
		        	else //If the Y value of the character is less than, or equal to 635
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_A: //If A is pressed
		        	characterX -= MOVEMENT; //Decrease x value of character
		        	if (characterX < 10) //If the X-value of the character is less than 10, which would mean the user is hitting the left boundary
		        	{
		        		movesLeft -= 0; //Do not subtract any moves 
		        	}
		        	else //If the x value of the character is greater than, or equal to 10
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_D: //If D is pressed
		        	characterX += MOVEMENT; //Increase x value of character
		        	if (characterX > 1250) //If the X-value of the character is greater than 1250, which would mean the user is hitting the right boundary
					 {
		        		movesLeft -= 0; //Do not subtract any moves 
					 }
		        	else
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break;
		        }  //End of switch statement
			 } 
		 }
		 else if (play3) //If the user is playing level 3
		 {
			 //Nested Decisions
			 if ((!win) && (!lose3)) //If the game is not over
			 {
		        switch (pressedCode) //Switch statement based on what key is pressed 
		        { 
		        case KeyEvent.VK_UP: //If the UP arrow key is pressed
		            characterY -= MOVEMENT; //Decrease y value of character by whatever value MOVEMENT is
		            if (characterY < 50) //If the Y value of the character is less than 50 (i.e. the user it hitting the top wall)
		        	{
		        		movesLeft -= 0; //Do not subtract any moves
		        	}
		        	else //If the Y value of the character is greater than, or equal to 50
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_DOWN: //If the DOWN arrow key is pressed
		        	characterY += MOVEMENT; //Increase y value of character
		        	if (characterY > 635) //If the Y-value of the character is greater than 635, which would mean the user is hitting the bottom boundary
		        	{
		        		movesLeft -= 0; //Do not subtract any moves 
		        	}
		        	else //If the Y value of the character is less than, or equal to 635
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_LEFT: //If LEFT arrow key is pressed
		        	characterX -= MOVEMENT; //Decrease x value of character
		        	if (characterX < 10) //If the X-value of the character is less than 10, which would mean the user is hitting the left boundary
		        	{
		        		movesLeft -= 0; //Do not subtract any moves 
		        	}
		        	else //If the x value of the character is greater than, or equal to 10
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_RIGHT: //If RIGHT arrow key is pressed
		        	characterX += MOVEMENT; //Increase x value of character
		        	if (characterX > 1250) //If the X-value of the character is greater than 1250, which would mean the user is hitting the right boundary
					{
		        		movesLeft -= 0; //Do not subtract any moves 
					}
		        	else
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_W: //If W is pressed
		        	characterY -= MOVEMENT; //Decrease y value of character by whatever value MOVEMENT is
		            if (characterY < 50) //If the Y value of the character is less than 50 (i.e. the user it hitting the top wall)
		        	{
		        		movesLeft -= 0; //Do not subtract any moves
		        	}
		        	else //If the Y value of the character is greater than, or equal to 50
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_S: //If S is pressed
		        	characterY += MOVEMENT; //Increase y value of character
		        	if (characterY > 635) //If the Y-value of the character is greater than 635, which would mean the user is hitting the bottom boundary
		        	{
		        		movesLeft -= 0; //Do not subtract any moves 
		        	}
		        	else //If the Y value of the character is less than, or equal to 635
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_A: //If A is pressed
		        	characterX -= MOVEMENT; //Decrease x value of character
		        	if (characterX < 10) //If the X-value of the character is less than 10, which would mean the user is hitting the left boundary
		        	{
		        		movesLeft -= 0; //Do not subtract any moves 
		        	}
		        	else //If the x value of the character is greater than, or equal to 10
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break; 
		        case KeyEvent.VK_D: //If D is pressed
		        	characterX += MOVEMENT; //Increase x value of character
		        	if (characterX > 1250) //If the X-value of the character is greater than 1250, which would mean the user is hitting the right boundary
					 {
		        		movesLeft -= 0; //Do not subtract any moves 
					 }
		        	else
		        	{
		        		movesLeft -= 1; //Subtract 1 from movesLeft
		        	}
		            break;
		        }  //End of switch statement
			 }
		 } //End of Decisions
		 repaint(); //Invoke paint method, and update panel 
		 
		 //Check the state of game, if the game is lost, won, or still continuing
	     win = outOfBounds(characterX, characterY); //Set win to whatever value outOfBounds(integer, integer) returns. If it returns true, the game has been won, and the game won screen is displayed
	     lose1 = touchingObstacles1(characterX, characterY); //Set lose1 to whatever value touchingObstacles1(integer, integer) returns. If any of the lose booleans are true, the game has been lost and the game over screen is displayed
	     lose2 = touchingObstacles2(characterX, characterY); //Set lose2 to whatever value touchingObstacles2(integer, integer) returns
	     lose3 = touchingObstacles3(characterX, characterY); //Set lose3 to whatever value touchingObstacles3(integer, integer) returns
	}
	
	public void keyReleased(KeyEvent e) //Void KeyReleased method, to be invoked after a key stroke is released
	{ 
    	//Method is unused, so there is no code. Nevertheless, since the class implements a KeyListener, this method must be here
    } 
	
	public void keyTyped(KeyEvent e) //Void KeyTyped method, to be invoked when a key is typed
	{
    	//Method is unused, so there is no code. Nevertheless, since the class implements a KeyListener, this method must be here
	}
	
	public boolean outOfBounds (int x, int y) //Boolean outOfBounds Method, which pushes back the character if it hits a wall, or sets win to true if the user crosses the finish line
	{ //Start of Method
		 //Declare all Variables
		 boolean isGameWon = false; //Boolean to check if game has been won or not. This value gets returned. For now, it is initialised as false
		 
		 //Decisions 
		 if ((x > F_WIDTH - 50) || (x < 10)) //If character is outside the horizontal borders of the game
		 {
			 //Nested Decisions
			 if (x > F_WIDTH - 50) //If character hits the right boundary
			 {
				 characterX -= 5; //Send character back 5 pixels to the left
                 isGameWon = false; //Keep game going
			 }
			 else //If character hits the left boundary
			 {
				 characterX += 5; //Send character back 5 pixels to the right
				 isGameWon = false; //Keep game going
			 }
		 }
		 else if ((y > F_HEIGHT - 65) || (y < 50)) //If user moves their character outside the vertical borders of the game 
		 {
			 if ((y > F_HEIGHT - 65) && (x < F_WIDTH - 90)) //If the user hits the bottom boundary, and does NOT cross the finish line
			 {
				 characterY -= 5; //Send character back 5 pixels upwards
                 isGameWon = false; //Keep game going
			 }
			 else if ((y > F_HEIGHT - 65) && (x > F_WIDTH - 90)) //If the user hits the bottom boundary, and DOES cross the finish line
			 {
				 isGameWon = true; //Set isGameWon to true, meaning the "Game Won" screen will be painted
			 }
			 else //If the user hits the top boundary
			 {
				 characterY += 5; //Send character back 5 pixels downwards
				 isGameWon = false; //Keep game going
			 }
		 }
		 else //If the user stays in the vertical, and horizontal borders of the game
		 {
			 characterY += 0; //Do not move character leftwards or rightwards
			 characterX +=0; //Do not move character downwards or upwards
		 } //End of Decisions
		 repaint(); //Invoke Paint Method and repaint, while updating, the JPanel
		 return isGameWon; //Return Whether the game is won or lost
	} //End of Method
	
	public boolean touchingObstacles1 (int x, int y) //Boolean Method which checks if the user has touched any of the obstacles in level 1, or if the moves have run out
	{ //Start of Method
		//Declare variable
		boolean isGameOver = false; //Boolean to see if the user has hit any of the obstacles in level 1, or if they have no more moves left
		
		//Decisions
		if (((x >= 565) && (x <= 1200)) && ((y >= 210) && (y <= 300))) //If user hits obstacle 1. All obstacle numbers correspond with the numbers in the drawObstacles1(Graphics g) method
		{
			isGameOver = true; //Set isGameOver to true, which means user has lost
		}
		else if (((x >= 885) && (x <= 975)) && ((y >= 60) && (y <= 300))) //If user hits obstacle 2
		{
			isGameOver = true; //User has lost
		}
		else if (((x >= 60) && (x <= 160)) && ((y >= 150) && (y <= 400))) //If user hits obstacle 3
		{
			isGameOver = true; //User has lost
		}
		else if (((x >= 10) && (x <= 310)) && ((y >= 460) && (y <= 550))) //If user hits obstacle 4
		{
			isGameOver = true; //User has lost
		}
		else if (((x >= 950) && (x <= 1290)) && ((y >= 360) && (y <= 450))) //If user hits obstacle 5
		{
			isGameOver = true; //User has lost
		}
		else if (((x >= 950) && (x <= 1040)) && ((y >= 360) && (y <= 550))) //If user hits obstacle 6
		{
			isGameOver = true; //User has lost
		}
		else if (((x >= 360) && (x <= 450)) && ((y >= 320) && (y <= 610))) //If user hits obstacle 7
		{
			isGameOver = true; //User has lost
		}
		else if (((x >= 760) && (x <= 850)) && ((y >= 320) && (y <= 610))) //If user hits obstacle 8
		{
			isGameOver = true; //User has lost
		}
		else if (((x >= 560) && (x <= 650)) && ((y >= 460) && (y <= 675))) //If user hits obstacle 9
		{
			isGameOver = true; //User has lost
		}
		else if (((x >= 360) && (x <= 850)) && ((y >= 320) && (y <= 410))) //If user hits obstacle 10
		{
			isGameOver = true; //User has lost
		}
		else if (((x >= 60) && (x <= 450)) && ((y >= 110) && (y <= 200))) //If user hits obstacle 11
		{
			isGameOver = true; //User has lost
		}
		else if (((x >= 635) && (x <= 725)) && ((y >= 60) && (y <= 300))) //If user hits obstacle 12
		{
			isGameOver = true; //User has lost
		}
		else if (((x >= 235) && (x <= 325)) && ((y >= 320) && (y <= 610))) //If user hits obstacle 13
		{
			isGameOver = true; //User has lost
		}
		else if (((x >= 260) && (x <= 350)) && ((y >= 50) && (y <= 150))) //If user hits obstacle 14
		{
			isGameOver = true; //User has lost
		}
		else if (((x >= 510) && (x <= 600)) && ((y >= 50) && (y <= 150))) //If user hits obstacle 15
		{
			isGameOver = true; //User has lost
		}
		else if (((x >= 760) && (x <= 850)) && ((y >= 50) && (y <= 150))) //If user hits obstacle 16
		{
			isGameOver = true; //User has lost
		}
		else if (((x >= 1010) && (x <= 1100)) && ((y >= 50) && (y <= 150))) //If user hits obstacle 17
		{
			isGameOver = true; //User has lost
		}
		else if (movesLeft <= 0) //If user runs out of moves
		{
			isGameOver = true; //Game is over, since there are no moves left
		}
		else //If user doesn't hit any obstacles
		{
			isGameOver = false; //Keep Game going
		} //End of Decisions
		
		repaint(); //Invoke paint method to update JFrame
		return isGameOver; //Return whether the user has lost level 1 or not
	} //End of Method
	 
	public boolean touchingObstacles2(int x, int y) //Boolean Method which checks if the user has touched any of the obstacles in level 2, or if the moves have run out
	{
		 //Declare variable
		 boolean isGameOver = false; //Boolean to see if the user has hit any of the obstacles in level 2, or if they have no more moves left
		 
		 //Decisions
		 if (((x >= 10) && (x <= 110)) && ((y >= 460) && (y <= 530))) //If user hits obstacle 1. All obstacle numbers correspond with the numbers in the drawObstacles2(Graphics g) method
		 {			
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 80) && (x <= 680)) && ((y >= 80) && (y <= 150))) //If user hits obstacle 2
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 330) && (x <= 400)) && ((y >= 110) && (y <= 260))) //If user hits obstacle 3
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 530) && (x <= 600)) && ((y >= 110) && (y <= 250))) //If user hits obstacle 
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 530) && (x <= 800)) && ((y >= 210) && (y <= 280))) //If user hits obstacle 5
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 740) && (x <= 810)) && ((y >= 10) && (y <= 280))) //If user hits obstacle 6
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 600) && (x <= 670)) && ((y >= 210) && (y <= 370))) //If user hits obstacle 7
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 80) && (x <= 270)) && ((y >= 210) && (y <= 280)))  //If user hits obstacle 8
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 80) && (x <= 150)) && ((y >= 210) && (y <= 390))) //If user hits obstacle 9
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 80) && (x <= 195)) && ((y >= 350) && (y <= 420))) //If user hits obstacle 10
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 230) && (x <= 300)) && ((y >= 210) && (y <= 390))) //If user hits obstacle 11
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 230) && (x <= 500)) && ((y >= 320) && (y <= 390))) //If user hits obstacle 12
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 430) && (x <= 500)) && ((y >= 230) && (y <= 500))) //If user hits obstacle 13
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 430) && (x <= 1030)) && ((y >= 430) && (y <= 500))) //If user hits obstacle 14
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 600) && (x <= 790)) && ((y >= 330) && (y <= 400))) //If user hits obstacle 15
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 960) && (x <= 1030)) && ((y >= 270) && (y <= 675))) //If user hits obstacle 16
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 1080) && (x <= 1290)) && ((y >= 460) && (y <= 530))) //If user hits obstacle 17
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 660) && (x <= 730)) && ((y >= 460) && (y <= 600))) //If user hits obstacle 18
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 860) && (x <= 1030)) && ((y >= 290) && (y <= 360))) //If user hits obstacle 19
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 860) && (x <= 930)) && ((y >= 80) && (y <= 400))) //If user hits obstacle 20
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 860) && (x <= 1200)) && ((y >= 130) && (y <= 200))) //If user hits obstacle 21
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 1010) && (x <= 1080)) && ((y >= 80) && (y <= 170))) //If user hits obstacle 22
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 1120) && (x <= 1290)) && ((y >= 230) && (y <= 300)))  //If user hits obstacle 23
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 990) && (x <= 1160)) && ((y >= 340) && (y <= 410))) //If user hits obstacle 24
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 1080) && (x <= 1150)) && ((y >= 490) && (y <= 580))) //If user hits obstacle 25
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 270) && (x <= 610)) && ((y >= 520) && (y <= 590))) //If user hits obstacle 26
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 360) && (x <= 430)) && ((y >= 530) && (y <= 675))) //If user hits obstacle 27
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 310) && (x <= 380)) && ((y >= 430) && (y <= 575))) //If user hits obstacle 28
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 470) && (x <= 540)) && ((y >= 520) && (y <= 620))) //If user hits obstacle 29
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 860) && (x <= 1000)) && ((y >= 530) && (y <= 600))) //If user hits obstacle 30
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (((x >= 10) && (x <= 230)) && ((y >= 536) && (y <= 626))) //If user hits obstacle 31
		 {
			 isGameOver = true; //User has lost
		 }
		 else if (movesLeft <= 0) //If user runs out of moves
		 {
				isGameOver = true; //User has lost
		 }
		 else //If user has NOT run out of moves, or if they have NOT hit any obstacles
		 {
			isGameOver = false;  //Keep game going
		 } //End of decisions
		 
		 repaint(); //Invoke paint method and update screen
		 return isGameOver; //Return whether the user has lost in level 2
	} //End of Method
	
	public boolean touchingObstacles3(int x, int y) //Boolean Method which checks if the user has touched any of the obstacles in level 3, or if the moves have run out
	{ //Start of Method
		 
		 //Declare Variable
		 boolean isGameOver = false;
		 
		 //Decisions
		 if (((x >= 40) && (x <= 85)) && ((y >= 70) && (y <= 400))) //If user hits obstacle 1. All obstacle numbers correspond with the numbers in the drawObstacles3(Graphics g) method
		 {
				isGameOver = true; //The user has lost. This will invoke the "GAME OVER" screen to be painted
		 }
		 else if (((x >= 10) && (x <= 85)) && ((y >= 360) && (y <= 405))) //If user hits obstacle 2 
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 45) && (x <= 190)) && ((y >= 140) && (y <= 185))) //If user hits obstacle 3
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 115) && (x <= 260)) && ((y >= 70) && (y <= 115))) //If user hits obstacle 4
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 220) && (x <= 265)) && ((y >= 70) && (y <= 240))) //If user hits obstacle 5
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 45) && (x <= 125)) && ((y >= 200) && (y <= 245))) //If user hits obstacle 6
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 160) && (x <= 205)) && ((y >= 200) && (y <= 300))) //If user hits obstacle 7
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 110) && (x <= 480)) && ((y >= 260) && (y <= 305))) //If user hits obstacle 8
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 40) && (x <= 180)) && ((y >= 320) && (y <= 365))) //If user hits obstacle 9
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 280) && (x <= 325)) && ((y >= 220) && (y <= 390))) //If user hits obstacle 10
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 250) && (x <= 410)) && ((y >= 350) && (y <= 395))) //If user hits obstacle 11
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 250) && (x <= 295)) && ((y >= 350) && (y <= 610))) //If user hits obstacle 12
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 190) && (x <= 235)) && ((y >= 380) && (y <= 676))) //If user hits obstacle 13
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 110) && (x <= 230)) && ((y >= 380) && (y <= 425))) //If user hits obstacle 14
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 10) && (x <= 140)) && ((y >= 440) && (y <= 485))) //If user hits obstacle 15
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 60) && (x <= 230)) && ((y >= 500) && (y <= 545))) //If user hits obstacle 16
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 250) && (x <= 380)) && ((y >= 570) && (y <= 615))) //If user hits obstacle 17
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 250) && (x <= 440)) && ((y >= 510) && (y <= 555))) //If user hits obstacle 18
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 400) && (x <= 445)) && ((y >= 420) && (y <= 676))) //If user hits obstacle 19
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 225) && (x <= 555)) && ((y >= 160) && (y <= 205))) //If user hits obstacle 20
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 285) && (x <= 430)) && ((y >= 70) && (y <= 115))) //If user hits obstacle 21
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 390) && (x <= 435)) && ((y >= 70) && (y <= 240))) //If user hits obstacle 22
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 285) && (x <= 330)) && ((y >= 70) && (y <= 130))) //If user hits obstacle 23
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 450) && (x <= 495)) && ((y >= 10) && (y <= 140))) //If user hits obstacle 24
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 510) && (x <= 555)) && ((y >= 70) && (y <= 305))) //If user hits obstacle 25
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 570) && (x <= 615)) && ((y >= 10) && (y <= 230))) //If user hits obstacle 26
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 570) && (x <= 660)) && ((y >= 190) && (y <= 235))) //If user hits obstacle 27
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 510) && (x <= 710)) && ((y >= 260) && (y <= 305))) //If user hits obstacle 28
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 670) && (x <= 715)) && ((y >= 160) && (y <= 305))) //If user hits obstacle 29
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 670) && (x <= 885)) && ((y >= 160) && (y <= 205))) //If user hits obstacle 30
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 630) && (x <= 675)) && ((y >= 70) && (y <= 150))) //If user hits obstacle 31
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 630) && (x <= 870)) && ((y >= 70) && (y <= 115))) //If user hits obstacle 32
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 830) && (x <= 875)) && ((y >= 10) && (y <= 140))) //If user hits obstacle 33
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 760 ) && (x <= 805)) && ((y >= 130) && (y <= 200))) //If user hits obstacle 34
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 930) && (x <= 975)) && ((y >= 80) && (y <= 190))) //If user hits obstacle 35
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 930) && (x <= 1020)) && ((y >= 150) && (y <= 195))) //If user hits obstacle 36
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 830) && (x <= 970)) && ((y >= 100) && (y <= 145))) //If user hits obstacle 37
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 990) && (x <= 1220)) && ((y >= 70) && (y <= 115))) //If user hits obstacle 38
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 1180) && (x <= 1225)) && ((y >= 70) && (y <= 240))) //If user hits obstacle 39
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 1115) && (x <= 1225)) && ((y >= 200) && (y <= 245))) //If user hits obstacle 40
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 1180) && (x <= 1290)) && ((y >= 320) && (y <= 365))) //If user hits obstacle 41
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 1180) && (x <= 1225)) && ((y >= 260) && (y <= 360))) //If user hits obstacle 42
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 1115) && (x <= 1160)) && ((y >= 200) && (y <= 420))) //If user hits obstacle 43
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 1115) && (x <= 1225)) && ((y >= 380) && (y <= 425))) //If user hits obstacle 44
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 1060) && (x <= 1290)) && ((y >= 440) && (y <= 485))) //If user hits obstacle 45
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 1060) && (x <= 1105)) && ((y >= 380) && (y <= 480))) //If user hits obstacle 46
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 980) && (x <= 1110)) && ((y >= 380) && (y <= 425))) //If user hits obstacle 47
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 980) && (x <= 1025)) && ((y >= 220) && (y <= 420))) //If user hits obstacle 48
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 980) && (x <= 1155)) && ((y >= 300) && (y <= 345))) //If user hits obstacle 49
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 980) && (x <= 1090)) && ((y >= 220) && (y <= 265))) //If user hits obstacle 50
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 1050) && (x <= 1095)) && ((y >= 135) && (y <= 265))) //If user hits obstacle 51
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 860) && (x <= 1020)) && ((y >= 280) && (y <= 325))) //If user hits obstacle 52
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 860) && (x <= 905)) && ((y >= 220) && (y <= 320))) //If user hits obstacle 53
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 750) && (x <= 960)) && ((y >= 220) && (y <= 265))) //If user hits obstacle 54
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 860) && (x <= 905)) && ((y >= 340) && (y <= 610))) //If user hits obstacle 55
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 860) && (x <= 960)) && ((y >= 570) && (y <= 615))) //If user hits obstacle 56
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 920) && (x <= 965)) && ((y >= 365) && (y <= 615))) //If user hits obstacle 57
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 920) && (x <= 1190)) && ((y >= 500) && (y <= 545))) //If user hits obstacle 58
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 990) && (x <= 1035)) && ((y >= 440) && (y <= 540))) //If user hits obstacle 59
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 1145) && (x <= 1190)) && ((y >= 440) && (y <= 540))) //If user hits obstacle 60
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 990) && (x <= 1035)) && ((y >= 576) && (y <= 676))) //If user hits obstacle 61
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 990) && (x <= 1180)) && ((y >= 576) && (y <= 621))) //If user hits obstacle 62
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 780) && (x <= 905)) && ((y >= 420) && (y <= 465))) //If user hits obstacle 63
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 800) && (x <= 845)) && ((y >= 280) && (y <= 400))) //If user hits obstacle 64
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 720) && (x <= 845)) && ((y >= 360) && (y <= 405))) //If user hits obstacle 65
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 720) && (x <= 765)) && ((y >= 360) && (y <= 676))) //If user hits obstacle 66
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 460) && (x <= 845)) && ((y >= 480) && (y <= 525))) //If user hits obstacle 67
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 780) && (x <= 825)) && ((y >= 540) && (y <= 676))) //If user hits obstacle 68
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 740) && (x <= 840)) && ((y >= 300) && (y <= 345))) //If user hits obstacle 69
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 660) && (x <= 705)) && ((y >= 340) && (y <= 460))) //If user hits obstacle 70
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 400) && (x <= 705)) && ((y >= 420) && (y <= 465))) //If user hits obstacle 71
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 400) && (x <= 690)) && ((y >= 540) && (y <= 585))) //If user hits obstacle 72
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 480) && (x <= 525)) && ((y >= 596) && (y <= 676))) //If user hits obstacle 73
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 550) && (x <= 595)) && ((y >= 540) && (y <= 620))) //If user hits obstacle 74
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 620) && (x <= 665)) && ((y >= 596) && (y <= 676))) //If user hits obstacle 75
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 340) && (x <= 385)) && ((y >= 350) && (y <= 470))) //If user hits obstacle 76
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 315) && (x <= 385)) && ((y >= 430) && (y <= 475))) //If user hits obstacle 77
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 440) && (x <= 485)) && ((y >= 260) && (y <= 380))) //If user hits obstacle 78
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 440) && (x <= 620)) && ((y >= 340) && (y <= 385))) //If user hits obstacle 79
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (((x >= 10) && (x <= 230)) && ((y >= 560) && (y <= 676))) //If user hits obstacle 80
		 {
				isGameOver = true; //The user has lost
		 }
		 else if (movesLeft <= 0) //If the user runs out of moves
		 {
				isGameOver = true; //The user has lost
		 }
		 else //If the user does NOT run out of moves, and does NOT touch any obstacles in level 3
		 {
			 isGameOver = false; //Keep game going
		 }
		 repaint(); //Invoke paint method to update JPanel
		 return isGameOver; //Return whether the user hit any obstacles or ran out of moves in level 3
	} //End of Method
	
	public void paint(Graphics g) //Create void paint method to paint and update the JPanel. This method is invokes numerous times when relevant
	{  //Start of Method
		//Declare constants
		final int LABEL_HEIGHT = 110, WIN_WIDTH = 370, LOSE_WIDTH = 290; //Declare the height of both the "WIN" and "GAME OVER" labels. The widths are different
		final long WAIT = 1500; //Constant to pause the program in milliseconds, Must be long since Thread.sleep only takes a long argument, however if an integer is passed, the Java will convert it automatically
		
		//Decisions
		if (menu) //If menu is true, which would mean the main menu screen is visible
	    {    	
	        super.paint(g); //Enable panel to be painted
	        
	        //Nested Decisions
	        if (play1) //Otherwise, if play1 is true, which would mean the user has pressed the Level 1 button
	        {
	        	//Double Nested Decisions
	        	if (!lose1) //If the user has NOT lost
		        {
	        		//Triple Nested Decisions
		        	if (!win) //If game has NOT been won
		        	{
		        		drawCharacterAndFinish(g, 1); //Draw character and finish line. Pass the fact that it is level 1
		            	drawGameBorders(g, 1); //Draw the 4 borders of the game. Pass the fact that it is level 1
		            	drawObstacles1(g); //Draw obstacles for level 1
		            	drawLevelInfo(g, 1); //Draw the level label, and number of moves left for level 1
		        	}
		        	else //If the user has won
		        	{
		        		g.setFont(titleFont); //Set the font to the titleFont (same font used for menuLabel)
		        		g.setColor(Color.YELLOW); //Set the Colour to Yellow
			            g.drawString("YOU WIN!", WIN_WIDTH, LABEL_HEIGHT); //Draw a string at this position
			            pause(WAIT); //Pause the program for however many milliseconds WAIT is
			            backToStart.setVisible(true); //Make the backToStart button visible
		        		playAgain.setVisible(true); //Make the playAgain button visible
		        	}
		        }
	        	else //If the user has lost
	        	{
	        		g.setFont(titleFont); //Set the font to the titleFont (same font used for menuLabel)
	        		g.setColor(Color.YELLOW); //Set the Colour to Yellow
	        		g.drawString("GAME OVER!", LOSE_WIDTH, LABEL_HEIGHT); //Draw a string at this position
	        		pause(WAIT); //Pause the program for however many milliseconds WAIT is
	        		backToStart.setVisible(true); //Make the backToStart button visible
	        		playAgain.setVisible(true); //Make the playAgain button visible
	        	}
	        }
	        else if (play2) //Otherwise, if play2 is true, which would mean the user has pressed the Level 2 button
	        {
	        	//Double Nested Decisions
	        	if (!lose2) //If game is NOT over
		        {
	        		//Triple Nested Decisions
		        	if (!win) //If game has NOT been won
		        	{
		        		drawCharacterAndFinish(g, 2); //Draw character and finish line. Pass the fact that it is level 2
		            	drawGameBorders(g, 2); //Draw the 4 borders of the game. Pass the fact that it is level 2
		            	drawObstacles2(g); //Draw obstacles for level 2
		            	drawLevelInfo(g, 2); //Draw the level label, and number of moves left for level 2
		        	}
		        	else //If the game has been won
		        	{
		        		g.setFont(titleFont); //Set the font to the titleFont (same font used for menuLabel)
		        		g.setColor(Color.YELLOW); //Set the Colour to Yellow
			            g.drawString("YOU WIN!", WIN_WIDTH, LABEL_HEIGHT); //Draw a string at this position
			            pause(WAIT); //Pause the program for however many milliseconds WAIT is
			            backToStart.setVisible(true); //Make the backToStart button visible
		        		playAgain.setVisible(true); //Make the playAgain button visible
		        	}
		        }
	        	else //If the game is over
	        	{
	        		g.setFont(titleFont); //Set the font to the titleFont (same font used for menuLabel)
	        		g.setColor(Color.YELLOW); //Set the Colour to Yellow
	        		g.drawString("GAME OVER!", LOSE_WIDTH, LABEL_HEIGHT); //Draw a string at this position
	        		pause(WAIT); //Pause the program for however many milliseconds WAIT is
	        		backToStart.setVisible(true); //Make the backToStart button visible
	        		playAgain.setVisible(true); //Make the playAgain button visible
	        	}
	        }
	        else if (play3) //Otherwise, if play3 is true, which would mean the user has pressed the Level 3 button
	        {
	        	//Double Nested Decisions
	        	if (!lose3) //If game is not over
		        {
	        		//Triple Nested Decisions
		        	if (!win) //If game has not been won
		        	{
		        		drawCharacterAndFinish(g, 3); //Draw character and finish line. Pass the fact that it is level 3
		            	drawGameBorders(g, 3); //Draw the 4 borders of the game. Pass the fact that it is level 3
		            	drawObstacles3(g); //Draw obstacles for level 3
		            	drawLevelInfo(g, 3); //Draw the level label, and number of moves left for level 3
		        	}
		        	else //If the user has won
		        	{
		        		g.setFont(titleFont); //Set the font to the titleFont (same font used for menuLabel)
		        		g.setColor(Color.YELLOW); //Set the Colour to Yellow
			            g.drawString("YOU WIN!", WIN_WIDTH, LABEL_HEIGHT); //Draw a string at this position
			            pause(WAIT); //Pause the program for however many milliseconds WAIT is
			            backToStart.setVisible(true); //Make the backToStart button visible
		        		playAgain.setVisible(true); //Make the playAgain button visible
		        	}
		        }
	        	else 
	        	{
	        		g.setFont(titleFont); //Set the font to the titleFont (same font used for menuLabel)
	        		g.setColor(Color.YELLOW); //Set the Colour to Yellow
	        		g.drawString("GAME OVER!", LOSE_WIDTH, LABEL_HEIGHT); //Draw a string at this position
	        		pause(WAIT); //Pause the program for however many milliseconds WAIT is
	        		backToStart.setVisible(true); //Make the backToStart button visible
	        		playAgain.setVisible(true); //Make the playAgain button visible
	        	}
	        }
	        else if (inst) //Otherwise, if inst is true, which would mean the user has pressed the instructions button
	        {
	        	drawIntructions(g); //Draw the instructions menu
	        	pause(WAIT); //Pause the program for however many milliseconds WAIT is
	        	backToStart.setVisible(true); //Make the backToStart button visible
	        }
	        else //Otherwise, if none of the aforementioned booleans are true
	        {
	        	//Set all menu elements to true
	        	menuLabel.setVisible(true);
	        	visibleButtons();
	        }
	    } //End of Decisions
	} //End of Method
	
	public void pause(long time) //Create void pause method that stops the program for "time" milliseconds
    { //Start of Method
    	try //Try to execute the block of code below
    	{ 
    		Thread.sleep(time); //Sleep and stop the program for "time" seconds
    	}
    	catch (Exception e) { //Handle the errors found
    	}
    } //End of Method
	
	public void drawLevelInfo (Graphics g, int levelPass) //Create void level info method that paints the level info
	{ //Start of method
		 g.setColor(Color.BLACK); //Set Colour to black
		 g.setFont(subheadingFont); //Set the font to the subheadingFont
		 
		 //Decisions
		 if (levelPass == 1) //If the user is on level 1
		 {
			 g.drawString("Level 1", 1130 , 80); //Draw the string at this position
			 g.drawString("Moves Left: " + movesLeft, 1080, 110); //Draw the string at this position
		 }
		 else if (levelPass == 2) //If the user is on level 2
		 {
			 g.drawString("Level 2", 70 , 610); //Draw the string at this position
			 g.drawString("Moves Left: " + movesLeft, 15, 640); //Draw the string at this position
		 }
		 else  //Otherwise, If the user is on level 3
		 {
			 g.setColor(Color.CYAN);
			 g.drawString("Level 3", 70 , 630); //Draw the string at this position
			 g.drawString("Moves Left: " + movesLeft, 15, 660); //Draw the string at this position
		 }
	} //End of method
	
	public void drawCharacterAndFinish(Graphics g, int levelPass) //Create void paint method that draws the character and finish line
	{ //Start of Method
		 //Draw both images
		 g.drawImage(character, characterX, characterY, this); //Set the image, and the position of the character
		 g.drawImage(finishLine, 1210, 675, this); //Set the image, and the position of the finish line
		 
		 //Decisions
		 if (levelPass == 1) //If the user is on level 1
		 {
			 g.setColor(labRed); //Set colour to custom red made outside this method
		 }
		 else if (levelPass == 2) //If the user is on level 2
		 {
			 g.setColor(labBlue); //Set colour to custom blue made outside this method
		 }
		 else //If the user is on level 3
		 {
			 g.setColor(labGreen); //Set colour to custom green made outside this method
		 }
		 
		 //Draw extra finish line elements
		 g.fillRect(1250, 560, 5, 115); //Draw flag pole
		 int[] xPoints = {1210, 1250, 1250};  //Create an array for the 3 x-values of the finish line flag
 		 int[] yPoints = {600, 560, 600};  //Create an array for the 3 y-values of the finish line flag
 		 g.fillPolygon(xPoints, yPoints, 3); //Draw the triangular flag
	} //End of Method
	
	public void drawGameBorders(Graphics g, int levelPass) //Create void paint method that draws the 4 boundaries
	{
		//Declare constants
	 	final int CHANGE_X = 10, CHANGE_Y = 25; //The x and y increments for drawing the borders
 		
	 	//Decisions
	 	if (levelPass == 1) //If the user is on level 1
 		{
 			g.setColor(labGreen); //Set colour to custom green
 		}
 		else if (levelPass == 2) //If the user is on level 2
 		{
 			g.setColor(labRed); //Set colour to custom red
 		}
 		else //Otherwise, if the user is on level 1
 		{
 			g.setColor(labBlue); //Set colour to custom blue
 		}
 		
	 	//Loops
	  	for (int r = 100; r < F_WIDTH; r += CHANGE_X + 1) //Loop for top boundary, which increments by CHANGE_X + 1
    	{
    		g.fillRect(r, CHANGE_Y, CHANGE_X, CHANGE_Y); //Draw Boundary
    	}
	  	
    	for (int t = 0; t < F_WIDTH-100; t += CHANGE_X + 1) //Loop for bottom boundary, which increments by CHANGE_X + 1
    	{
    		g.fillRect(t, F_HEIGHT - 24, CHANGE_X, CHANGE_Y); //Draw Boundary
    	}
    	
    	for (int u = 0; u < F_HEIGHT - 40; u += CHANGE_Y + 1) //Loop for left boundary, which increments by CHANGE_X + 1
    	{
    		g.fillRect(0, u, CHANGE_X, CHANGE_Y); //Draw Boundary
    	}
    	
    	for (int p = 0; p < F_HEIGHT; p += CHANGE_Y) //Loop for right boundary, which increments by CHANGE_X + 1
    	{
    		g.fillRect(F_WIDTH - 10, p, CHANGE_X, CHANGE_Y); //Draw Boundary
    	}
	} //End of Method
	
	public void drawObstacles1(Graphics g) //Create void paint method that draws the obstacles for level 1
	{ //Start of Method
		 g.setColor(labGreen); //Set colour to custom green
		 
		 //Draw all obstacles
		 g.fillRect(600, 250, 600, 50); //Obstacle 1
		 g.fillRect(925, 100, 50, 200); //Obstacle 2
		 g.fillRect(100, 200, 50, 200); //Obstacle 3
		 g.fillRect(10, 500, 300, 50); //Obstacle 4
		 g.fillRect(990, 400, 300, 50); //Obstacle 5
		 g.fillRect(990, 400, 50, 150); //Obstacle 6
		 g.fillRect(400, 360, 50, 250); //Obstacle 7
		 g.fillRect(800, 360, 50, 250); //Obstacle 8
		 g.fillRect(600, 501, 50, 175); //Obstacle 9
		 g.fillRect(400, 360, 450, 50); //Obstacle 10
		 g.fillRect(100, 150, 350, 50); //Obstacle 11
		 g.fillRect(675, 100, 50, 200); //Obstacle 12
		 g.fillRect(275, 360, 50, 250); //Obstacle 13 
		 g.fillRect(1100, 50, 190, 100); //Obstacle 14
		 
		 //Loops to draw remaining Obstacles (15, 16, 17)
		 for (int a = 300; a < F_WIDTH; a +=250) //Loop which starts drawing at x = 300, and ends stops at x = F_WIDTH
		 {
			 g.fillRect(a, 50, 50, 100); //Draw Obstacles
		 }
	} //End of Method
	
	public void drawObstacles2(Graphics g) //Create void paint method that draws the obstacles for level 2
	{ //Start of Method
		g.setColor(labRed); //Set colour to custom red
		
		//Draw all obstacles 
		g.fillRect(10, 500, 100, 30);  //Obstacle 1
		g.fillRect(120, 120, 560, 30); //Obstacle 2
		g.fillRect(370, 150, 30, 110); //Obstacle 3
		g.fillRect(570, 150, 30, 100); //Obstacle 4
		g.fillRect(570, 250, 230, 30); //Obstacle 5
		g.fillRect(780, 50, 30, 230); //Obstacle 6
		g.fillRect(640, 250, 30, 120); //Obstacle 7
		g.fillRect(120, 250, 150, 30); //Obstacle 8
		g.fillRect(120, 250, 30, 140); //Obstacle 9
		g.fillRect(120, 390, 75, 30); //Obstacle 10
		g.fillRect(270, 250, 30, 140); //Obstacle 11
		g.fillRect(270, 360, 230, 30); //Obstacle 12
		g.fillRect(470, 270, 30, 230); //Obstacle 13
		g.fillRect(470, 470, 560, 30); //Obstacle 14
		g.fillRect(640, 370, 150, 30); //1Obstacle 5	
		g.fillRect(1000, 310, 30, 365); //Obstacle 16
		g.fillRect(1120, 500, 170, 30); //Obstacle 17
		g.fillRect(700, 500, 30, 100); //Obstacle 18
		g.fillRect(900, 330, 130, 30); //Obstacle 19
		g.fillRect(900, 120, 30, 280); //Obstacle 20
		g.fillRect(900, 170, 300, 30); //Obstacle 21
		g.fillRect(1050, 120, 30, 50); //Obstacle 22
		g.fillRect(1160, 270, 130, 30); //Obstacle 23
		g.fillRect(1030, 380, 130, 30); //Obstacle 24
		g.fillRect(1120, 530, 30, 50); //Obstacle 25
		g.fillRect(310, 560, 300, 30); //Obstacle 26
		g.fillRect(400, 570, 30, 105); //Obstacle 27
		g.fillRect(350, 470, 30, 105); //Obstacle 28
		g.fillRect(510, 560, 30, 60); //Obstacle 29
		g.fillRect(900, 570, 100, 30); //Obstacle 30
		g.fillRect(10, 576, 220, 100); //Obstacle 31
	} //End of Method
	
	public void drawObstacles3(Graphics g) //Create void paint method that draws the obstacles for level 3
	{ //Start of Method
		 g.setColor(labBlue); //Set colour to custom blue
		 
		 //Draw all obstacles
		 g.fillRect(80, 110, 5, 290); //1
		 g.fillRect(10, 400, 75, 5); //2
		 g.fillRect(85, 180, 105, 5); //3
		 g.fillRect(155, 110, 105, 5); //4
		 g.fillRect(260, 110, 5, 130); //5
		 g.fillRect(85, 240, 40, 5); //6
		 g.fillRect(200, 240, 5, 60); //7
		 g.fillRect(150, 300, 330, 5); //8
		 g.fillRect(80, 360, 100, 5); //9
		 g.fillRect(320, 260, 5, 130); //10
		 g.fillRect(290, 390, 120, 5); //11
		 g.fillRect(290, 390, 5, 220); //12
		 g.fillRect(230, 420, 5, 256); //13
		 g.fillRect(150, 420, 80, 5); //14
		 g.fillRect(10, 480, 130, 5); //15
		 g.fillRect(100, 540, 130, 5); //16
		 g.fillRect(290, 610, 90, 5); //17
		 g.fillRect(290, 550, 150, 5); //18
		 g.fillRect(440, 460, 5, 216); //19
		 g.fillRect(265, 200, 290, 5); //20
		 g.fillRect(325, 110, 105, 5); //21
		 g.fillRect(430, 110, 5, 130); //22
		 g.fillRect(325, 110, 5, 20); //23
		 g.fillRect(490, 50, 5, 90); //24
		 g.fillRect(550, 110, 5, 195); //25
		 g.fillRect(610, 50, 5, 180); //26
		 g.fillRect(610, 230, 50, 5); //27
		 g.fillRect(550, 300, 160, 5); //28
		 g.fillRect(710, 200, 5, 105); //29
		 g.fillRect(710, 200, 175, 5); //30
		 g.fillRect(670, 110, 5, 40); //31
		 g.fillRect(670, 110, 200, 5); //32
		 g.fillRect(870, 50, 5, 90); //33
		 g.fillRect(800, 170, 5, 30); //34
		 g.fillRect(970, 120, 5, 70); //35
		 g.fillRect(970, 190, 50, 5); //36
		 g.fillRect(870, 140, 100, 5); //37
		 g.fillRect(1030, 110, 190, 5); //38
		 g.fillRect(1220, 110, 5, 130); //39
		 g.fillRect(1155, 240, 70, 5); //40
		 g.fillRect(1220, 360, 70, 5); //41
		 g.fillRect(1220, 300, 5, 60); //42
		 g.fillRect(1155, 240, 5, 180); //43
		 g.fillRect(1155, 420, 70, 5); //44
		 g.fillRect(1100, 480, 190, 5); //45
		 g.fillRect(1100, 420, 5, 60); //46
		 g.fillRect(1020, 420, 80, 5); //47
		 g.fillRect(1020, 260, 5, 160); //48
		 g.fillRect(1020, 340, 135, 5); //49
		 g.fillRect(1020, 260, 70, 5); //50
		 g.fillRect(1090, 175, 5, 90); //51
		 g.fillRect(900, 320, 120, 5); //52
		 g.fillRect(900, 260, 5, 60); //53
		 g.fillRect(790, 260, 170, 5); //54
		 g.fillRect(900, 380, 5, 230); //55
		 g.fillRect(900, 610, 60, 5); //52
		 g.fillRect(960, 405, 5, 210); //57
		 g.fillRect(960, 540, 230, 5); //58
		 g.fillRect(1030, 480, 5, 60); //59
		 g.fillRect(1185, 480, 5, 60); //60
		 g.fillRect(1030, 616, 5, 60); //61
		 g.fillRect(1030, 616, 150, 5); //62
		 g.fillRect(820, 460, 85, 5); //63
		 g.fillRect(840, 320, 5, 80); //64
		 g.fillRect(760, 400, 85, 5); //65
		 g.fillRect(760, 400, 5, 276); //66
		 g.fillRect(500, 520, 345, 5); //67
		 g.fillRect(820, 580, 5, 96); //68
		 g.fillRect(780, 340, 60, 5); //69 
		 g.fillRect(700, 380, 5, 80); //70
		 g.fillRect(440, 460, 265, 5); //71
		 g.fillRect(440, 580, 250, 5); //72
		 g.fillRect(520, 636, 5, 40); //73
		 g.fillRect(590, 580, 5, 40); //74
		 g.fillRect(660, 636, 5, 40); //75
		 g.fillRect(380, 390, 5, 80); //76
		 g.fillRect(355, 470, 30, 5); //77
		 g.fillRect(480, 300, 5, 80); //78
		 g.fillRect(480, 380, 140, 5); //79
		 g.fillRect(10, 600, 220, 76); //80
	} //End of Method
	
	public void drawIntructions(Graphics g) //Create void paint method that draws the instructions
	{ //Start of Method
		//Declare all constants
		final int TEXT_X = 7, TEXT_Y = 510; //Set the coordinates for the text
		
		g.setColor(Color.YELLOW); //Set the colour to yellow
     	g.setFont(titleFont); //Set font to titleFont
     	g.drawString("INSTRUCTIONS", 220, 110); //Draw title at this position
     	g.setFont(instFont); //Now set font to instFont
     	
     	//Draw the text at the bottom of the instructions screen
     	g.drawString("Use the UP, LEFT, RIGHT, and DOWN arrow keys or WASD", TEXT_X, TEXT_Y);
     	g.drawString("keys to move around. Your goal is to get to the finish line", TEXT_X, TEXT_Y + 40);
     	g.drawString("through the maze without touching any of the obstacles,", TEXT_X, TEXT_Y + 80);
     	g.drawString("and before you run out of moves. The harder levels have", TEXT_X, TEXT_Y + 120);
     	g.drawString("less moves available, and more obstacles", TEXT_X + 160, TEXT_Y + 160);
	} //End of Method
} //End of Class