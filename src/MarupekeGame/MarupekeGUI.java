package MarupekeGame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.regex.Pattern;

/**
 * The MarupekeGUI class represents the controller and visuals for the Marupeke program. In this class,
 * the GUI is created, and alongside it, the user is given control to modify the data. Button will let the user
 * call specific methods in the Marupeke class to change the data. After data is changed, the visuals are refreshed.
 *
 */

public class MarupekeGUI extends Application {
    private Text moves, prevMov, hintCounter;
    private GridPane marupekeBoard = new GridPane();
    private HBox controls = new HBox();
    private SquareType selectedSymbol;
    private Stage introduction, advRulesStage, stage;
    private BorderPane advPlayRoot,mainGameRoot;
    private String previousMoves = "";
    private Marupeke mainBoard;
    private Button rulesButton;
    private Scene gameStage;
    public static int argsBoardSize;

    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            System.out.println("Please insert desired Grid Size(4-10)!");
            System.exit(0);
        }
        if (args.length > 1)
        {
            System.out.println("Please insert only one element!");
            System.exit(0);
        }
        if (Pattern.matches("[0-9]+", args[0]))
        {
            if (Integer.parseInt(args[0]) > 10 || Integer.parseInt(args[0]) < 4 )
            {
                System.out.println("Please use numbers from 4 to 10!");
                System.exit(0);
            }
            //Sets grid size
            argsBoardSize = Integer.parseInt(args[0]);
            launch(args);
        }
        else {
            System.out.println("Please use only numbers!");
            System.exit(0);
        }
    }

    @Override
    public void start(Stage primaryStage)
    {
        //Creates a possible board using args size (WIll be used if Quick Play is selected!)
        mainBoard = new Marupeke(argsBoardSize,DiffLevels.medium);
        this.stage = primaryStage;
        //launches the main menu
        startScreen();
        rootSetUp();
        controlButtons();
        printBoard();
        stage.show();
    }

    //CREATION OF MAIN GAME SCREEN STARTS HERE

    /**
     * printBoard() is the method that is going to display an updated grid to the player. Everytime this method is called
     * (which happens after every change in the game), the hintCounter and moves text are updated to display the real
     * number, and the marupekeBoard GridPane is cleared and rebuilt. If I did not include such function, then the game
     * would slow down after every move. lastly, a new scene is created.
     *
     */
    public void printBoard() {
        prevMov.setText("PREVIOUS MOVE:\n\n" + previousMoves);
        hintCounter.setText("Hint Remaining: " + mainBoard.getInformation(2));
        moves.setText("Moves Made: " + mainBoard.getInformation(1));
        //Clear previous board
        marupekeBoard.getChildren().clear();
        for (int i = 0; i < mainBoard.getGameSession().getGameBoard().length; i++) {
            for (int j = 0; j < mainBoard.getGameSession().getGameBoard().length; j++) {
                marupekeBoardButtons(i, j, mainBoard.getGameSession());
            }
        }
        //IF PUZZLE IS COMPLETE, WIN ALERT POPS UP
        if (mainBoard.getGameSession().isPuzzleComplete()) winningAlert();
        //gameStage updates its root
        gameStage.setRoot(mainGameRoot);
    }

    /**
     * buttonCreation() is the method that will set up the buttons of the grid itself. Each button will have the function of changing
     * char (if it is not an illegal move). The color of the tiles is also different, depending on whether it is editable
     * or not.
     *
     * @param col represent the index of the column desired
     * @param row represent the index of the row desired
     * @param tempBoard is the current MarupekeGrid that the player is playing on
     */
    public void marupekeBoardButtons(int col, int row, MarupekeGrid tempBoard)
    {
        Button tile = new Button(String.valueOf(tempBoard.getGameBoard()[col][row].getMarkChar()));
        tile.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        tile.setMaxSize(300, 300);
        //TILE FUNCTIONALITY
        tile.setOnAction(e -> {
            //SOUND SET UP
            Media sound = new Media(new File("fall.WAV").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
            //CALL CHANGECHAR
            previousMoves = mainBoard.changeChar(col, row, selectedSymbol);
            //UPDATE BOARD
            printBoard();
        });
        if (!tempBoard.getGameBoard()[col][row].getEditable()) tile.setTextFill(Color.RED);
        //ADD TO GRID PANE
        marupekeBoard.add(tile, row, col);
    }

    /**
     * buttonCreations() gives a function to the main buttons present in the marupeke game. button previous will let
     * users go back to their previous move, hint will give players a hint (if they have hints remaining), oSymbol,
     * xSymbol and clear symbol will place the specified char in the MarupekeGrid (O,X,clear respectively). Lastly,
     * all are then added to the controls HBox.
     *
     */
    public void controlButtons() {
        Button previous = new Button("Previous Move");
        Button hint = new Button("HINT");
        // Previous Button
        previous.setOnAction(e -> {
            if (!mainBoard.getPrev().empty()) {
                mainBoard.goBack();
                printBoard();
            }
        });
        // Hint Button
        hint.setOnAction(e -> {
            hintAlert();
            printBoard();
        });
        // Controls Buttons
        Button xSymbol = new Button("X");
        Button oSymbol = new Button("O");
        Button clearSymbol = new Button("_");
        xSymbol.setOnAction(e -> {
            selectedSymbol = SquareType.X;
            xSymbol.setTextFill(Color.GREEN);
            oSymbol.setTextFill(Color.BLACK);
            clearSymbol.setTextFill(Color.BLACK);
        });
        oSymbol.setOnAction(e -> {
            selectedSymbol = SquareType.O;
            xSymbol.setTextFill(Color.BLACK);
            oSymbol.setTextFill(Color.GREEN);
            clearSymbol.setTextFill(Color.BLACK);
        });
        clearSymbol.setOnAction(e -> {
            selectedSymbol = SquareType.EMPTY;
            xSymbol.setTextFill(Color.BLACK);
            oSymbol.setTextFill(Color.BLACK);
            clearSymbol.setTextFill(Color.GREEN);
        });
        Button leaveGame = new Button("Leave Game");
        leaveGame.setOnAction(e -> {
            stage.close();
            exitAlert();
        });
        // CONTROLS GUI SET UP
        controls.setPadding(new Insets(15, 100, 15, 100));
        controls.setSpacing(50);
        controls.setAlignment(Pos.CENTER);
        controls.setStyle("-fx-background-color: #008080;" + "-fx-border-width: 2;");
        // BUTTONS GUI SET UP
        xSymbol.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        oSymbol.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        clearSymbol.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        hint.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        leaveGame.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        previous.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        // CONTROLS HBOX SET UP
        controls.getChildren().addAll(previous, hint, xSymbol, oSymbol, clearSymbol, rulesButton,leaveGame);
        gameStage = new Scene(mainGameRoot, 1500, 1200);
        stage.setScene(gameStage);
    }

    /**
     * rootSetUp() creates a new BorderPane called mainGameRoot. When mainGameRoot is created, alongside with it, many
     * methods are called (setupBoard(), title, setInformation(), setControls()) which all return either a GridPane
     * or HBox and adds them all to the root. It is separated for a better clarity of what is going on.
     *
     */
    public void rootSetUp() {
        //TITLE SET UP
        HBox mainGameTitle = new HBox();
        Text titleText = new Text("MARUPEKE GAME");
        titleText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        mainGameTitle.setAlignment(Pos.CENTER);
        mainGameTitle.setStyle("-fx-background-color: #008080;");
        mainGameTitle.getChildren().add(titleText);
        // MARUPEKE BOARD GUI SET UP
        marupekeBoard.setAlignment(Pos.CENTER);
        marupekeBoard.setStyle("-fx-border-width: 10;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: black;");
        //INSERT ALL IN ROOT
        mainGameRoot = new BorderPane(marupekeBoard, mainGameTitle, setInformation(), controls, null);
    }

    /**
     * setInformation() will set up all the content of the borderpane information. This includes the text that will be
     * added (moves and hintcounter will refresh after every move to display correct information), the size of said text
     * and lastly the alignment of all the texts.
     *
     * @return the modified Marupeke Game Board
     */
    public BorderPane setInformation()
    {
        // RULES SET UP
        Text rules = new Text("===CONTROLS RULES===\n\nPrevious Move = Go back one move!\n\n" +
                "Hint = Receive Hint!\n\n X O _ = Select mark to insert in the grid!\n\nAdvanced Rules: Read Rules" +
                "\n\nLeave Game = Save progress and leave game!\n\n\n===COUNTERS===");
        rules.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        rules.setTextAlignment(TextAlignment.CENTER);
        BorderPane information = new BorderPane(countersInformation(),rules,null,null,null);
        information.setStyle("-fx-border-width: 10;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: black;");
        return information;
    }

    /**
     * countersInformation() is a method that prepares and sets up the GUI of all the different counters. This includes
     * the number of hints remaining and the number of moves made and the previous move.
     *
     * @return returns a GridPane containing all the information regarding the counters in the game.
     */
    public GridPane countersInformation()
    {
        GridPane counters = new GridPane();
        // PrevMOV SET UP
        prevMov = new Text(previousMoves);
        prevMov.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        prevMov.setTextAlignment(TextAlignment.LEFT);
        // MOVE COUNTER SET UP
        moves = new Text("\n\nMove Counter: " + mainBoard.getInformation(1));
        moves.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        moves.setTextAlignment(TextAlignment.LEFT);
        // HINT COUNTER SET UP
        hintCounter = new Text("\n\nHints Left: " + mainBoard.getInformation(2));
        hintCounter.setTextAlignment(TextAlignment.CENTER);
        hintCounter.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        // GRIDPANE SET UP
        counters.add(moves,0,0);
        counters.add(hintCounter,0,1);
        counters.add(prevMov,0,2);
        counters.setVgap(30);
        counters.setPadding(new Insets(50,0,0,0));
        counters.setAlignment(Pos.TOP_CENTER);
        return counters;
    }

    // ALERTS METHODS (ERRORS, WINS, EXITS) START HERE

    /**
     * showError() will pop up when the player has made an illegal move.
     * The Alert will contain illegalitiesInGrid in text format, to see exactly what went wrong.
     *
     */
    public static void showError(String temp) {
        Alert a = new Alert(Alert.AlertType.ERROR, temp);
        a.showAndWait();
    }

    /**
     * hintAlert creates an alert if the clicks on the hint button.
     * By clicking the hint button, the hint alert will print out the contents of Marupeke.hint method, which
     * will be the hint given to the player.
     *
     */
    private void hintAlert() {
        Alert a = new Alert(Alert.AlertType.INFORMATION, mainBoard.hint(mainBoard.getGameSession()));
        a.showAndWait();
    }

    /**
     * winningAlert() creates an alert if the player wins the game.
     * It lets the player exit the game (and save the final grid) or start a new game.
     *
     */
    private void winningAlert() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "YOU WON!\nComplete Game Board has been Saved!\n" +
                "The folder is named Games, and the file is named: DoneGame_" + mainBoard.getUsername().getText() + "\nPress okay to play again!");
        a.getButtonTypes().add(ButtonType.FINISH);
        a.getButtonTypes().remove(ButtonType.CANCEL);
        a.showAndWait().ifPresent(type ->
        {
            if (type == ButtonType.OK) {
                mainBoard.createFile();
                stage.close();
                try {
                    restart();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (type == ButtonType.FINISH) {
                mainBoard.createFile();
                System.exit(0);
            }
        });
    }

    /**
     * exitAlert() creates an alert if the player quits the game before finishing the game.
     * It lets the player exit the game (while saving progress) and gives the option to
     * create a entirely new board.
     *
     */
    public void exitAlert() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "You left the game\nThe game will be saved in the folder Ongoing_Games as file: OngoingGame_" +
                mainBoard.getUsername().getText() + "\nClick Ok if you want to start a new game!");
        a.getButtonTypes().add(ButtonType.FINISH);
        a.getButtonTypes().remove(ButtonType.CANCEL);
        a.showAndWait().ifPresent(type ->
        {
            if (type == ButtonType.OK) {
                try {
                    mainBoard.saveGrid(""+ mainBoard.getInitialSession()+ mainBoard.getGameSession()+ mainBoard.getInformation(1)+"\n"+ mainBoard.getInformation(2)+"\n"+mainBoard.getUsername().getText());
                    stage.close();
                    restart();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (type == ButtonType.FINISH) {
                mainBoard.saveGrid(""+ mainBoard.getInitialSession()+ mainBoard.getGameSession()+ mainBoard.getInformation(1)+"\n"+ mainBoard.getInformation(2)+"\n"+mainBoard.getUsername().getText());
                System.exit(0);
            }
        });
    }

    // MAIN RULES METHODS START HERE

    /**
     * mainRules() method gathers all the rules methods GridPanes and adds them to one GridPane. It uses a newly created
     * stage, and will remain on the screen (show and wait), until the button is pressed.
     */
    private void mainRules() {
        //STAGE CREATION
        advRulesStage = new Stage();
        //BORDERPANE SET UP
        BorderPane main = new BorderPane(middleRules(),topRules(),null,bottomRules(),null);
        //SCENE CREATION
        advRulesStage.setScene(new Scene(main,1000,700));
        advRulesStage.showAndWait();
    }

    /**
     * topRules() method returns the BorderPane for the top part of the BorderPane in the mainRules() method.
     * topRules() consists of an explanation of the hint button. The hint buttons can be used for a limited amount of times,
     * it gives a possible place put a block in.
     *
     * @return returns the BorderPane (Hint Rules) for the top part of the BorderPane in mainRules()
     */
    public BorderPane topRules()
    {
        //HINT RULES SECTION SET UP
        Text hintRules = new Text ("You can utilise the hint button to receive a possible next move!\n\n\nOn the right part of the screen, " +
                "there is a hint counter. When said counter is 0, you may not receive anymore hints!\n\n\nIf there are no possible moves, " +
                "then you will be told to backtrack!");
        hintRules.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        //HINT RULES TITLE SET UP (HBOX USED FOR SETSTYLE)
        Text hintRulesTitle = new Text("HINT RULES");
        hintRulesTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        HBox topHBox = new HBox(hintRulesTitle);
        topHBox.setAlignment(Pos.BOTTOM_CENTER);
        topHBox.setStyle("-fx-background-color: #008080;");
        //RETURN BORDERPANE
        return new BorderPane(hintRules,topHBox,null,null,null);
    }

    /**
     * middleRules() method returns the BorderPane for the middle part of the BorderPane in the mainRules() method.
     * middleRules() consists of the meaning of the different colored tiles in the game. Green means a selected mark to
     * place, Red means an un-changeable tile and black represents a normal tile. All of these explanations are accompanied
     * by example buttons with the corresponding colors.
     *
     * @return returns the BorderPane (Colors Rules) for the bottom part of the BorderPane in mainRules()
     */
    public BorderPane middleRules()
    {
        //TITLE SET UP (HBOX USED FOR SETSTYLE METHOD)
        HBox titleHBox = new HBox();
        Text title = new Text("COLORS MEANING");
        titleHBox.setAlignment(Pos.BOTTOM_CENTER);
        titleHBox.getChildren().add(title);
        titleHBox.setStyle("-fx-background-color: #008080;");
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        GridPane colorRules = new GridPane();
        //DUMMY BUTTONS CREATION
        Button xButton = new Button("X");
        xButton.setTextFill(Color.GREEN);
        xButton.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        Button oButton = new Button("O");
        oButton.setTextFill(Color.RED);
        oButton.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        Button clearButton = new Button("_");
        clearButton.setTextFill(Color.BLACK);
        clearButton.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //EXPLANATIONS SET UP (INCLUDING GUI)
        Text greenRule = new Text("When a tile is green, it means the mark of that symbol is selected!");
        greenRule.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        Text redRule = new Text("When a tile is red, it means the tile cannot be changed!");
        redRule.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        Text blackRule = new Text("When a tile is black, it means the tile is changeable!");
        blackRule.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //ADD ALL TO THE GRIDPANE COLORRULES
        colorRules.add(xButton,0,0);
        colorRules.add(greenRule,1,0);
        colorRules.add(oButton,0,1);
        colorRules.add(redRule,1,1);
        colorRules.add(clearButton,0,2);
        colorRules.add(blackRule,1,2);
        colorRules.setAlignment(Pos.TOP_CENTER);
        colorRules.setAlignment(Pos.CENTER);
        //RETURN BORDERPANE
        return new BorderPane(colorRules,titleHBox,null,null,null);
    }

    /**
     * bottomRules() method returns the BorderPane for the bottom part of the BorderPane in the mainRules() method.
     * bottomRules() represent the basic rules of the Marupeke Game. It tells the user how to win the game, illegalities
     * in the game and lastly mentions the previous move button.
     *
     * @return returns the BorderPane (Marupeke Rules) for the bottom part of the BorderPane in mainRules()
     */
    public BorderPane bottomRules()
    {
        //TITLE CREATION (HBOX IS USED FOR SETSTYLE METHOD)
        Text marupekeRulesTitle = new Text("MARUPEKE RULES");
        HBox bottomHBox = new HBox();
        bottomHBox.setAlignment(Pos.CENTER);
        bottomHBox.getChildren().add(marupekeRulesTitle);
        bottomHBox.setStyle("-fx-background-color: #008080;");
        marupekeRulesTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        //MARUPEKE RULES SET UP
        Text marupekeRules = new Text ("The Marupeke Game is complete when the entire board is filled with marks\n\n\n" +
                "Three consecutive symbols cannot be the same (Horizontal, Vertical, Diagonal)!\n\n\nYou have access to a previous button to backtrack!");
        marupekeRules.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        Button exit = new Button("Close");
        exit.setOnAction(e -> advRulesStage.close());
        exit.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        return new BorderPane( marupekeRules,bottomHBox,null,exit,null);
    }

    // DIFFICULTY RULES METHODS START HERE

    /**
     *  diffRules() method creates a new Stage when called. This stage displays to the user how
     *  the different difficulty levels change the game.
     */
    public void diffRules()
    {
        Stage diffRulesStage = new Stage();
        //TITLE CREATION (HBOX USED FOR SETSTYLE METHOD)
        HBox titles = new HBox();
        titles.setStyle("-fx-background-color: #008080;");
        Text diffRulesTitle = new Text("DIFFICULTY RULES");
        diffRulesTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        titles.getChildren().add(diffRulesTitle);
        titles.setAlignment(Pos.CENTER);
        //MAIN RULES SET UP (TEXT)
        Text mainRules = new Text("The difficulty option determines:\n\nFirst, the number of unmovable tiles (Red Tiles)\n\nSecond, how many hints you have.");
        mainRules.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        Text easy = new Text("EASY DIFFICULTY\nNUMBER OF RED TILES: 1   HINTS: 4");
        easy.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        Text medium = new Text("MEDIUM DIFFICULTY\nNUMBER OF RED TILES: NoTiles/3   HINTS: 3");
        medium.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        Text hard = new Text("HARD DIFFICULTY\nNUMBER OF RED TILES: NoTiles/2   HINTS: 2");
        hard.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        //VBOX WITH THE RULES IS CREATED, AND ITEMS ARE INSERTED
        VBox diffRules = new VBox();
        diffRules.getChildren().add(mainRules);
        diffRules.getChildren().add(easy);
        diffRules.getChildren().add(medium);
        diffRules.getChildren().add(hard);
        diffRules.setAlignment(Pos.CENTER);
        diffRules.setSpacing(20);
        //LEAVE STAGE BUTTON CREATION
        Button leaveDiffRules = new Button("Close");
        leaveDiffRules.setOnAction(e -> diffRulesStage.close());
        leaveDiffRules.setAlignment(Pos.CENTER_RIGHT);
        leaveDiffRules.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //BORDERPANE CREATION AND STAGE.SHOW
        BorderPane temper = new BorderPane(diffRules,titles,null,leaveDiffRules,null);
        diffRulesStage.setScene(new Scene(temper,500,500));
        diffRulesStage.show();
    }

    // MAIN MENU METHODS START HERE

    /**
     * startScreen() is a method that gets the information of many other methods, such as the menuOptions method and
     * the createOptions method and places them in a new stage called introduction. The introduction has two possible screens,
     * a main menu(menuOptions), and  a create grid screen(createOptions).
     *
     */
    public void startScreen() {
        introduction = new Stage();
        //FIRST PAGE TEXT SET UP
        GridPane firstPage = new GridPane();
        Text mainMenuTitle = new Text("MARUPEKE GRID MENU");
        mainMenuTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        firstPage.add(mainMenuTitle,0,0);
        firstPage.setStyle("-fx-background-color: #008080;");
        firstPage.setAlignment(Pos.CENTER);
        //SECOND PAGE TEXT SET UP
        GridPane secondPage = new GridPane();
        Text createMenuTitle = new Text("CREATE MARUPEKE GRID");
        secondPage.add(createMenuTitle,0,0);
        secondPage.setStyle("-fx-background-color: #008080;");
        secondPage.setAlignment(Pos.CENTER);
        createMenuTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        //MAIN MENU PAGE SET UP
        BorderPane mainMenu = new BorderPane(menuOptions(),firstPage,null,null,null);
        //ADVANCED PLAY ROOT SET UP
        advPlayRoot = new BorderPane(createOptions(),secondPage,null,null,null);
        introduction.setScene(new Scene(mainMenu, 1000, 500));
        introduction.showAndWait();
    }

    /**
     * menuOptions() creates a GridPane that contains all the options present in the
     * main menu of the game. This includes a button to create a new Marupeke Grid, a Button to use
     * the main method args as size, button to load a previous Marupeke Grid, a button for the rules, and
     * a button to leave the game.
     *
     * @return a GridPane that contains the necessary buttons for a main menu.
     */
    public GridPane menuOptions()
    {
        GridPane mainMenuOptions = new GridPane();
        //BUTTON CREATE BOARD SET UP
        Button advancedPlayButton = new Button ("Advanced Play");
        advancedPlayButton.setOnAction(e -> introduction.setScene(new Scene(advPlayRoot, 1000, 500)));
        advancedPlayButton.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //LOAD PREVIOUS BOARD BUTTON SET UP
        Button continueGameBoard = new Button ("Continue Game");
        continueGameBoard.setOnAction(e ->
        {
            FileChooser fileChooser = new FileChooser();
            File files = fileChooser.showOpenDialog(introduction);
            mainBoard.returnGrid(files);
            introduction.close();
        });
        //QUICK PLAY BUTTON SET UP
        Button quickPlayButton = new Button ("Quick Play!");
        quickPlayButton.setOnAction(e ->
                {
                mainBoard.setUsername(new TextField("MainArgument"));
                introduction.close();
                });
        quickPlayButton.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //LEAVE BUTTON SET UP
        Button leaveGame = new Button("Leave Game");
        leaveGame.setOnAction(e -> System.exit(0));
        leaveGame.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        continueGameBoard.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //RULES BUTTON SET UP
        rulesButton = new Button ("Advanced Rules");
        rulesButton.setOnAction(e ->
                mainRules());
        rulesButton.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //EXPLANATIONS SET UP
        Text quickPlayExp = new Text("Quick Play lets you use the main argument size, with a pre-set difficulty!");
        quickPlayExp.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        Text advPlayExp = new Text("Advance Play lets you customise the size of the grid, the difficulty \nand lets you insert a username!");
        advPlayExp.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        Text contPlayExp = new Text(" Continue Game lets you load up a previous game!\n Use the OngoingGames folder to select the game you would like to continue!");
        contPlayExp.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        //GRIDPANE LOAD UP AND SET UP
        mainMenuOptions.add(quickPlayButton,0,0);
        mainMenuOptions.add(quickPlayExp,1,0);
        mainMenuOptions.add(advancedPlayButton,0,1);
        mainMenuOptions.add(advPlayExp,1,1);
        mainMenuOptions.add(continueGameBoard,0,2);
        mainMenuOptions.add(contPlayExp,1,2);
        mainMenuOptions.add(rulesButton,0,3);
        mainMenuOptions.add(leaveGame,0,4);
        mainMenuOptions.setVgap(50);
        mainMenuOptions.setAlignment(Pos.CENTER);
        //RETURNS GRID PANE
        return mainMenuOptions;
    }


    /**
     * createOptions() is a method that gives a GridPane that contains all the options
     * necessary to create a new Marupeke Grid. This includes, the size of the grid, and the
     * difficulty of the grid. The difficulty is represented as more initial blocks (uneditable)
     * and less hints.
     *
     * @return the size wanted for the to be created Marupeke Grid (int)
     */
    public GridPane createOptions()
    {
        ComboBox<DiffLevels> diffLevel = new ComboBox<>();
        ComboBox<Integer> gridSizes;
        //SET UP DIFFICULTY SELECTION
        Text diffText = new Text("Select Difficulty:     ");
        diffText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        diffLevel.getItems().add(DiffLevels.easy);
        diffLevel.getItems().add(DiffLevels.medium);
        diffLevel.getItems().add(DiffLevels.hard);
        //SET UP GRID SIZE SELECTION
        Text sizeText = new Text("Select Grid Size:     ");
        sizeText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        gridSizes = new ComboBox<>();
        for (int i = 4; i < 11; i++) gridSizes.getItems().add(i);
        //SET UP USERNAME TEXTFIELD
        Text usernameText = new Text("Insert Username:     ");
        TextField username = new TextField();
        usernameText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //SET UP CREATE BOARD BUTTON
        Button continues = new Button("Create Board");
        continues.setOnAction(e -> {
            if (gridSizes.getValue() == null || diffLevel.getValue() == null || username.getText() == null) showError("Please, make sure all options have been selected, including having a username!");
                else{
                mainBoard = new Marupeke(gridSizes.getValue(), diffLevel.getValue());
                mainBoard.setUsername(username);
                introduction.close();
            }
        });
        continues.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        //EXIT GAME BUTTON SET UP
        Button exitGameButton = new Button("Exit Game!");
        exitGameButton.setOnAction(e -> System.exit(0));
        exitGameButton.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        //SET UP RULES BUTTON
        Button rules = new Button("Difficulty Rules");
        rules.setOnAction(e -> diffRules());
        rules.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        //SET UP INTRO OPTIONS GRIDPANE
        GridPane introOptions = new GridPane();
        introOptions.add(username,1,0);
        introOptions.add(diffLevel,1,1);
        introOptions.add(gridSizes,1,2);
        introOptions.add(continues,1,3);
        introOptions.add(rules,1,4);
        introOptions.add(exitGameButton,1,5);
        introOptions.add(usernameText,0,0);
        introOptions.add(diffText,0,1);
        introOptions.add(sizeText,0,2);
        //SET VERTICAL AND HORIZONTAL GAP BETWEEN ELEMENTS
        introOptions.setHgap(40);
        introOptions.setVgap(40);
        introOptions.setAlignment(Pos.CENTER);
        //RETURNS GRIDPANE
        return introOptions;
    }

    // RESTART METHOD STARTS HERE
    
    /**
     * restart() method resets all the necessary components of the game, to allow for a smooth transition
     * between the last game, and the main menu.
     *
     * @throws IOException throws an exception in case method start() does not start.
     */
    public void restart() throws IOException {
        hintCounter = new Text();
        advPlayRoot = new BorderPane();
        marupekeBoard = new GridPane();
        controls = new HBox();
        mainBoard = new Marupeke();
        start(stage);
    }


}
