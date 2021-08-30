NOTE:
    This program runs on the Oracle Java JDK 1.8.0_281

===DISPLAYING THE DATA===
General Graphics: (General GUI)
	The Stage stage is the main Stage where the MarupekeGrid and its controls are placed in. The stage uses a BorderPane as root (called. mainGameRoot) The MarupekeGrid itself is made by a GridPane of buttons, and it is placed on the center part of the GridPane. The top part of the GridPane is reserved to the title (placed in an HBox to colour it). The right part is given to the counters and some explanations (regarding the controls). Lastly, the Bottom part is given to the Controls (also placed in HBox). The middle and right parts of the GridPane utilise CSS code to create more “creative” borders. printBoard() method will explain more in depth how the GUI displays the data.  The root to be inserted in the stage is setted up in the rootSetUp() method. The actual stage is initialised in the controlButtons() method at the very end, when all
The control buttons are created.

Print Board: (Keeping up to date)
	The printBoard() method is the method that allows the GUI to keep up to date with the modification on the current MarupekeGrid. At the use of all the buttons in the Stage stage, the printBoard() is always called. This allows for the Game Board to keep up to date in the GUI. The method itself uses a nested for loop to go throught all the elements of the MarupekeGrid(). For each tile, a new button is created. The name of the button represents the current mark in the specific tile. This symbol is obtained by getting the MarupekeGrid from the created Marupeke Object. In the marupekeBoardButton() method, is where each button is given its name and function. The font and the audio is also set there. It is important to note that at each iteration of printBoard(), the GridPane marupekeBoard(gridpane storing the board) is resetted. This fixes the issue of the board being printed on top of each other.
Alerts:

Controls: (Data Fetch)
	The Controls (controlButtons() method) are all buttons that have specific actions tied to them. First and foremost, the control buttons are all placed on an HBox to create a tidy effect. This HBox is later placed on the bottom part of the stage BoardPane. The Three main controls, the X O and _ buttons will change color depending on which one you have clicked. When one of them is green, then that button is the selected mark that you then want to place on the board. The color green also helps you know which SquareType is selected to then give to the changeChar() method in the Marupeke object.

Main Menu:
	All the functionalities of Main Menu, and how the GUI works are specified in the Extra Features section of this README file.

===EDITING THE DATA===

Creating Marupeke Object:
	The Marupeke constructor creates the gameSession that the user will play on. It takes an int representing the grid size, and the difficulty in the DiffLevels Enum. When the size and number of red tiles is set (unmovable tiles), then the MarupekeGrid is generated. The MarupekeGrid generated MarupekeGrid is then put in a while loop that will not stop until the copy of the Grid is solvable. To be solvable, the newly generated Grid is placed in the dfs() method. 

dfs:
	The dfs() method is used to create a solvable grid. It does so by performing a search state (using stacks). The initial state is the newly generated MarupekeGrid, and the final state is when the isPuzzleComplete() method returns true. The childrens, for each iteration of the loop, are obtained by creating two copies of the current MarupekeGrid (The popped MarupekeGrid). In one copy, we mark the first empty tile (starting from col: 0, row: 0) with X, and the second MarupekeGrid marks the same tile with O. If the grid is not complete, then a newly generated Grid (with the same values) will be placed in the dfs() method, and this loop continues until a solvable grid is created.

Change Char:
	The ChangeChar() method, found in the Marupeke class, is used to change the data of the MarupekeGrid. It does so by asking for two ints, and a SquareType Enum. The first two ints represent the coordinates of the tile the user wants to modify, and the SquareType is the selected mark the user wants to place.(The field SquareType selectedSymbol) If the move is successful, other than changing the tile in the MarupekeGrid, the coordinates and the mark that was present before the operation are added to the prev stack. The string that it returns represents the move made. If the marking fails (if the mark method returns false), then an error alert pops up with the reason (illegality).

Go Back:
	The goBack() method allows the user to go back one step. Since the changeChar() method, at every marking, adds to the prev Stack the move made, this can be used to go back. A new String is created, and the prev is popped(). Then it divides the String to get the individual coordinates and marks. Then, at the location of the change, the mark found in the String is placed back and the MarupekeGrid is modified. 

Advance Play:
	Please go to the section Extra Features: Advance Play to see how MarupekeGUI sends information for custom created MarupekeGrids.

===EXTRA FEATURES===

===MAIN MENU===
	This Marupeke program initially gets the size of the Marupeke via the main method arguments. The program then opens up in the Main Menu. The Main Menu gives the user many options.

Quick Play:
	Quick Play is an option that allows the user to jump straight into the game. Before opening any stages, in the start method, a new MarupekeGrid is created. It utilizes the argument as the size, and the difficulty is set at medium. This option also automatically gives the player the name MainArgument. To make this option work, the button simply needs to close the stage present in the startScreen() method. (Since all the necessities are already completed, example, the Marupeke Grid).

Advanced Play: 	When selecting the Advanced Play option, the player is given the freedom to further customise the game. To do so, the stage Introduction changes root. This root (BorderPane) contains a TextField (for the username), a ComboBox full of integers from 4 to 10 (representing the size of the grid) and another ComboBox that stores the Difficulty Enums (Easy,Medium,Hard). The user is also given two additional buttons. The Create Board button, uses the information in the fields mentioned earlier to create a new (finishable) grid. When using the Create Board button, all the information in the previous fields are used to create a new Marupeke Object. All the fields need to have been selected for it to create a board, if not, an error message shows up.  The other button calls on the method diffRules(). This method creates a stage, on top of the other stage, and explains to the user what the different difficulty levels mean. When the user is done reading, there is a button Close to close up the stage and go back to creating the board. NOTE: When the board is created at size 10 and hard difficulty, the creation of said board can take longer than usual.

Continue Game:
	Please see the “Saving and loading ongoing matches” section for the full explanation.

Advanced Rules:
	The Advanced Rules button, when clicked, calls the mainRules() method. This method creates a new stage that shows the user a number of important rules. The top rule  shows how the hint system works. (This whole section is created in the topRule() method) The middle rule explains the meaning of the different colours seen in the game. Alongside the explanation, dummy buttons have been placed to give the user a better understanding. (This whole section is created in the middleRule() method) Lastly, the bottom rule explains to the user how the Marupeke Game works. It explains how to win the game, illegal moves, and the use of the previous move button. (This whole section is created in the bottomRule().

Leave Game:
	The last button is very simple. It quits the game using a System.exit() command.

Saving and loading ongoing matches:
	My Marupeke program allows the users to save their progress on a board, and continue later on. The Marupeke class has a method that saves two boards in a new Text file(The name of the file is OngoingGame_”username”). Regarding the name of the file, if the file name exists already, an integer is added at the end (OngoingGame_"username"int). The first board that is saved is the original board. It needs to save this board to keep track of which tile is or is not editable. The second board saved is the actual progress made by the user. After these two boards, there are three more datas saved. The first one is the number of moves made, the second one is the number of hints left and lastly the username. This file is then saved in the OngoingGame Folder. To load the game, the program allows the user to manually select the file. To load the board, the program first calls the returnGrid method(which asks for the file). Then the elements of the boards are saved in a 2d char array. This 2d char array is then sent to the gridTxt(2d char) method. Here the method first gets the first board, and sets up red blocks (not movable), then repeats the process with the second board. gridTxt then saves the ongoing board in gameSession, and the initial board in initialSession(if the user were to stop playing again). The rest of the data in the file is then placed in the right fields. Lastly, when the user wins the game, the final board is saved in a new Text File and placed in the Games folder. 

===BOARD GAME===
	The board game contains many little extra functions, and these functions will be explained in this section:

Error Messages:  
	When the user places a tile that would make the board illegal, an error message pops up (and the tile is not placed). The error message gets the Reason from the illegalitiesInGrid method. The error is created in the changeChar() method in the Marupeke Object. If the move results in an error (mark method returns false), then the newest illegalitiesInGrid error is given to the showError() alert. The error tells the user: where the error begins, which symbol was inputted, and the reason as to why it is an error. The error message then goes away by clicking the OK button.

Move Counter:
	The move counter is a simple counter that keeps track of the number of moves made by the player. It does so by incrementing the moves field in the Marupeke object, each time a changeChar() method is successful. It may be used by players to try and beat a Marupeke Board with as little moves as possible.

Hint and hint counter:
	The Hint button gives the user a possible move. The hint counter, much like the moves counter, keeps track of how many hints the user has in the current game. If the hint counter is 0, then the player may not use the hint button anymore (An alert will pop up and tell the player). The Hint button works by calling the hint() Marupeke Object method. The hint() method calls the findEmptySpot() method, which creates a copy of the gameSession, and at the first move possible, the tile is marked (with either X or O, depending on if one is illegal or not). Then hint(), compares the two boards (gameSession, and the findEmptySpot() MarupekeGrid called solution). When there is a difference, the hint() method returns the symbol used, and the coordinates. If no moves are possible, then the hint() method returns a message that says to go back and try-again. After every successful hint, the hint counter goes down one.

Advanced Rules:
	Advanced Rules works the same as the identically named button in the Main Menu. NOTE: The right part of the screen contains additional information on the controls buttons. (There are also the counters).

Exit Alert:
	The Leave Game button closes the Stage stage, and the exitAlert() method pops up. This method will let the user know where the current game is saved, and the name of it. It will also allow the user to go back to the Main Menu or to finish the program.

Winning Alert:
	When the player is able to complete the game, the winningAlert() method pops up. This method congratulates the user for winning, and says where the file with the complete board is saved. It also tells the user how it is named. Lastly, the winningAlert() method gives two choices to the user. One, they can close the program by using the FINISH button, and second, they can go back to the Main Menu and play again.

Sound Effect:
	Whenever a block on the MarupekeGrid is clicked, a sound will play to let the player know the input went through. Each and every button created for the visualisation of the MarupekeGrid have a MediaPlayer field with the location of a sound file to play when said button is clicked. Sound taken here: (LittleRobotSoundFactory, 2015)
				    LittleRobotSoundFactory, 2015. Mouth_45.wav by LittleRobotSoundFactory. [online] Freesound. Available at: <https://freesound.org/people/LittleRobotSoundFactory/sounds/290524/> [Accessed 28 April 2021].

