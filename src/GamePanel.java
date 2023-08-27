import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends JPanel implements MouseListener, KeyListener {

    private float screenSizeX; // was 800
    private final float screenSizeY; // 900
    private final float easyMedHardButtonWidth; // 400
    private final float easyMedHardButtonHeight; // 100
    private final float easyMedHardFont; // 4
    private final float xPosTextEMH; // 372
    private final float yPosTextEMH; // 723
    private final float puzzleNumberFontSize; // 18
    private final float puzzleStringX; // 100
    private final float puzzleStringY; // 20
    private final float errorBoxPosX; // 620
    private final float errorBoxPosY; // 0
    private final float errorBoxSizeX; // 100
    private final float errorBoxSizeY; // 21
    private final float errorStringPosX; // 625
    private final float errorStringPosY; // 20
    private final float skinnyRowLineSize; // 1
    private final float numbersOptionStartX; // 52
    private final float numbersOptionStartY; // 810
    private final float numbersOptionWidthSpace; // 759
    private final float numbersOptionCircleLineSize; // 2
    private final float numbersOptionFontSize; // 30
    private final float numbersOptionCircleSize; // 65
    private final float boardNumbersFontSize; // 60
    private final float notesNumbersFontSize; // 20
    private final float notesEraseFontSize; // 40
    private final float notesEraseBoxThickness; // 2
    private final float notesEraseBoxSizeX; // 170
    private final float notesEraseBoxSizeY; // 50
    private final float notesModeFontSize; // 15
    private final float exitButtonStartX; // 11
    private final float exitButtonStartY; // 15
    private final float exitButtonThickness; // 3
    private final float exitButtonSizeX; // 50
    private final float exitButtonSizeY; // 50
    private final float exitStringFontSize; // 50
    private final float exitStringPosX; // 19
    private final float exitStringPosY; // 58
    private final float welcomeMessageFontSize; // 80
    private final float welcomeMessagePosX; // 35
    private final float welcomeMessagePosY; // 130
    private final float mainMenuBoxThickness; // 3
    private final float mainMenuButtonsStartX; // 200
    private final float mainMenuButtonsStartY; // 325
    private final float continueButtonFontSize; // 35
    private final float continueButtonStartX; // 300
    private final float continueButtonStartY; // 200
    private final float continueButtonSizeX; // 200
    private final float continueButtonSizeY; // 50
    private final float continueStringPosX; // 330
    private final float continueStringPosY; // 237
    private final float easyMedHardFontSize; // 60
    private final float easyStringPosX; // 335
    private final float easyStringPosY; // 395
    private final float mediumStringPosX; // 295
    private final float mediumStringPosY; // 545
    private final float hardStringPosX; // 335
    private final float hardStringPosY; // 695
    private final float easyMedHardButtonGapY; // 150
    private final float optionsCirclePosX; // 700
    private final float optionsCirclePosY; // 750
    private final float optionsCircleSize; // 75
    private final float loadingBarStartX; // 175
    private final float loadingBarStartY; // 200
    private final float loadingBarSizeX; // 450
    private final float loadingBarSizeY; // 50
    private final float winLoseMessageFontSize; // 70
    private final float afterGameMessageFontSize; // 50
    private final float afterGameBoxThickness; // 2
    private final float afterGameBoxSizeY; // 60
    private final float afterGameBoxGap; // 90
    private final float optionsMenuBoxThickness; // 3
    private final float optionsMenuFontSize; // 80
    private final float lightStringFontSize; // 45
    private final float loadNewPuzzlesStringFontSize; // 50
    private final float boxSize; // Square size of the sudoku box onto the screen, 648
    private final float boardXstart; // X-point where the sudoku box point begins, 76
    private final float boardYstart; // Y-point where the sudoku box point begins, 25
    private Board gameBoard; // Board that will be reference for the sudoku board on the window
    private int squareNum = 0; // Tracks the selected square and its number (row * 10 + col % 10) - row/col range: 0-8
    private int oldSquareNum = 0; // Tracks the previous selected square and its number
    private final Color sameValueHighlight = new Color(173, 170, 170);
    private final Color highlightRowColBoxColor = new Color(246, 236, 222); // Gray highlight for Row,Col,Box
    private final Color highlightNumberOptionColor = new Color(210, 206, 0); // Yellowish Highlight Color for menu
    private final Color highlightSquareColor = new Color(75, 248, 255);
    private boolean mPressed = false; // True if mouse is being pressed; False if mouse is released
    private boolean mainMenuActivated = true; // True if main menu is activated; false otherwise
    private boolean doClearScreen = false; // Will clear screen when switching scenes
    private int numberOptionClicked = 1; // Tracks which number option below game board is clicked
    private int eraseNotesOption = -1; // Tracks which option (Erase/Notes) is selected; 1 - erase; 2 - notes
    private int exitOption = -1; // Tracks whether the exit option has been pressed
    private int menuOption = -1; // Tracks which menu option has been pressed
    private boolean notesMode = false; // Tracks if notes option is activated; true if activated, false if not
    private final boolean[][] notesSquares =  new boolean[81][9]; // Tracks which notes are in each square
    private final boolean[][] filledSquares = new boolean[9][9]; // Tracks whether the square has a number inputted by the user
    private final int[][] filledNumber = new int[9][9]; // Tracks which number is in each square
    private int saveLevel = -1; // Keeps track of user game level if user decides to click on continue
    private int saveSpot = -1; // Keeps track of user game spot if user decides to click on continue
    private Font numberNotesFont; // Font for the number notes
    private Color numberNotesColorPlain = new Color(65, 65, 65);
    private Color numberNotesColorBold = new Color(28,28,28);

    private boolean continueButton = false; // Tracks whether continue should appear on the main menu
    private int loadingCount = 0; // Tracks the number of sudoku puzzles that have been generated
    private boolean loadingDone = false; // Tracks whether all the easy, medium, and hard puzzles have been generated
    private final Board[][] easyMediumHardBoards; // Easy, Medium, Hard Board
    private final Font aboveBoardFont; // Font for the Error & Puzzle #
    private int errorCount = 0; // Tracks the number of errors made by User
    private final int errorLimit = 3; // Sets the error limit until game is over
    private int oldErrorCount = 0; // Tracks the old error count
    private int playAgainOption = -1; // Tracks which option the user chooses when the play again menu pops up
    private int userWinLose = -1; // Tracks if user has won (1) or lost (2)
    private boolean hasWon = false; // Tracks whether user has won
    private boolean optionMenuOn = false; // Tracks whether the option menu is activated
    private int optionOpt = -1; // Tracks which option the user chooses when the option menu pops up
    private Color modeColorPrime = Color.white; // Primary Color
    private Color modeColorSecond = new Color(28, 28, 28); // Secondary Color
    private final Color darkModeHighlight = new Color(5, 68, 108);
    private final Color darkHighlightRowColBoxColor = new Color(66, 64, 64);
    private Color mainHighlightSquare = highlightSquareColor;
    private Color mainHighlightRowColBox = highlightRowColBoxColor;

    private Board[][] allLevelBoards;
    private int screenshotNumber = 1;
    private int lastKeyPressed = -1;
    private final Random random = new Random();


    GamePanel() {
        super();

        // Create Game Board
        gameBoard = new Board();

        // Set notesSquares and set all of its elements to false
        setNotesSquares();
        setFilledSquaresAndNumbers();

        // Set Easy, Medium, and Hard Boards
        easyMediumHardBoards = new Board[3][5];

        // Initialize all variables
        screenSizeX = 600.f; // was 800
        screenSizeY = screenSizeX * (8.6f/8.f); // 900
        easyMedHardButtonWidth = screenSizeX * (400.f/800.f); // 400
        easyMedHardButtonHeight = screenSizeX * (100.f/800.f); // 100
        easyMedHardFont = screenSizeX * (4.f/800.f); // 4
        xPosTextEMH = screenSizeX * (372.f/800.f); // 372
        yPosTextEMH = screenSizeX * (723.f/800.f); // 723
        puzzleNumberFontSize = screenSizeX * (18.f/800.f); // 18
        puzzleStringX = screenSizeX * (100.f/800.f); // 100
        puzzleStringY = screenSizeX * (20.f/800.f); // 20
        errorBoxPosX = screenSizeX * (620.f/800.f); // 620
        errorBoxPosY = screenSizeX * (0.f/800.f); // 0
        errorBoxSizeX = screenSizeX * (100.f/800.f); // 100
        errorBoxSizeY = screenSizeX * (21.f/800.f); // 21
        errorStringPosX = screenSizeX * (625.f/800.f); // 625
        errorStringPosY = screenSizeX * (20.f/800.f); // 20
        skinnyRowLineSize = screenSizeX * (1.f/800.f); // 1
        numbersOptionStartX = screenSizeX * (52.f/800.f); // 52
        numbersOptionStartY = screenSizeX * (810.f/800.f); // 810
        numbersOptionWidthSpace = screenSizeX * (759.f/800.f); // 759
        numbersOptionCircleLineSize = screenSizeX * (2.f/800.f); // 2
        numbersOptionFontSize = screenSizeX * (30.f/800.f); // 30
        numbersOptionCircleSize = screenSizeX * (65.f/800.f); // 65
        boardNumbersFontSize = screenSizeX * (60.f/800.f); // 60
        notesNumbersFontSize = screenSizeX * (18.f/800.f); // 20
        notesEraseFontSize = screenSizeX * (40.f/800.f); // 40
        notesEraseBoxThickness = screenSizeX * (2.f/800.f); // 2
        notesEraseBoxSizeX = screenSizeX * (170.f/800.f); // 170
        notesEraseBoxSizeY = screenSizeX * (50.f/800.f); // 50
        notesModeFontSize = screenSizeX * (15.f/800.f); // 15
        exitButtonStartX = screenSizeX * (11.f/800.f); // 11
        exitButtonStartY = screenSizeX * (15.f/800.f); // 15
        exitButtonThickness = screenSizeX * (3.f/800.f); // 3
        exitButtonSizeX = screenSizeX * (50.f/800.f); // 50
        exitButtonSizeY = screenSizeX * (50.f/800.f); // 50
        exitStringFontSize = screenSizeX * (50.f/800.f); // 50
        exitStringPosX = screenSizeX * (19.f/800.f); // 19
        exitStringPosY = screenSizeX * (58.f/800.f); // 58
        welcomeMessageFontSize = screenSizeX * (80.f/800.f); // 80
        welcomeMessagePosX = screenSizeX * (35.f/800.f); // 35
        welcomeMessagePosY = screenSizeX * (130.f/800.f); // 130
        mainMenuBoxThickness = screenSizeX * (3.f/800.f); // 3
        mainMenuButtonsStartX = screenSizeX * (200.f/800.f); // 200
        mainMenuButtonsStartY = screenSizeX * (325.f/800.f); // 325
        continueButtonFontSize = screenSizeX * (35.f/800.f); // 35
        continueButtonStartX = screenSizeX * (300.f/800.f); // 300
        continueButtonStartY = screenSizeX * (200.f/800.f); // 200
        continueButtonSizeX = screenSizeX * (200.f/800.f); // 200
        continueButtonSizeY = screenSizeX * (50.f/800.f); // 50
        continueStringPosX = screenSizeX * (330.f/800.f); // 330
        continueStringPosY = screenSizeX * (237.f/800.f); // 237
        easyMedHardFontSize = screenSizeX * (60.f/800.f); // 60
        easyStringPosX = screenSizeX * (335.f/800.f); // 335
        easyStringPosY = screenSizeX * (395.f/800.f); // 395
        mediumStringPosX = screenSizeX * (295.f/800.f); // 295
        mediumStringPosY = screenSizeX * (545.f/800.f); // 545
        hardStringPosX =  screenSizeX * (335.f/800.f); // 335
        hardStringPosY = screenSizeX * (695.f/800.f); // 695
        easyMedHardButtonGapY = screenSizeX * (150.f/800.f); // 150
        optionsCirclePosX = screenSizeX * (700.f/800.f); // 700
        optionsCirclePosY = screenSizeX * (750.f/800.f); // 750
        optionsCircleSize = screenSizeX * (75.f/800.f); // 75
        loadingBarStartX = screenSizeX * (175.f/800.f); // 175
        loadingBarStartY = screenSizeX * (200.f/800.f); // 200
        loadingBarSizeX = screenSizeX * (450.f/800.f); // 450
        loadingBarSizeY = screenSizeX * (50.f/800.f); // 50
        winLoseMessageFontSize = screenSizeX * (70.f/800.f); // 70
        afterGameMessageFontSize =  screenSizeX * (50.f/800.f); // 50
        afterGameBoxThickness =  screenSizeX * (2.f/800.f); // 2
        afterGameBoxSizeY =  screenSizeX * (60.f/800.f); // 60
        afterGameBoxGap = screenSizeX * (90.f/800.f); // 90
        optionsMenuBoxThickness = screenSizeX * (3.f/800.f); // 3
        optionsMenuFontSize = screenSizeX * (80.f/800.f); // 80
        lightStringFontSize = screenSizeX * (45.f/800.f); // 45
        loadNewPuzzlesStringFontSize = screenSizeX * (50.f/800.f); // 50
        boxSize = (screenSizeX * (648.f/800.f)); // Square size of the sudoku box onto the screen, 648
        boardXstart = (screenSizeX * (76.f/800.f)); // X-point where the sudoku box point begins, 76
        boardYstart = (screenSizeX * (25.f/800.f)); // Y-point where the sudoku box point begins, 25
        numberNotesFont = new Font("Arial",Font.PLAIN, (int) (screenSizeX*(20.f/800.f))); // Font for the number notes
        aboveBoardFont = new Font("Arial",Font.ITALIC, (int) (screenSizeX*(25.f/800.f))); // Font for the Error & Puzzle #

        setBackground(modeColorPrime);
        setPreferredSize(new Dimension((int)screenSizeX,(int)screenSizeY));
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
//        this.requestFocusInWindow();
    }

    GamePanel(int screenX)
    {
        super();

        // Create Game Board
        gameBoard = new Board();

        // Set notesSquares and set all of its elements to false
        setNotesSquares();
        setFilledSquaresAndNumbers();

        // Set Easy, Medium, and Hard Boards
        easyMediumHardBoards = new Board[3][5];

        // Initialize all variables
        this.screenSizeX = screenX; // was 800
        screenSizeY = screenSizeX * (8.6f/8.f); // 900
        easyMedHardButtonWidth = screenSizeX * (400.f/800.f); // 400
        easyMedHardButtonHeight = screenSizeX * (100.f/800.f); // 100
        easyMedHardFont = screenSizeX * (4.f/800.f); // 4
        xPosTextEMH = screenSizeX * (372.f/800.f); // 372
        yPosTextEMH = screenSizeX * (723.f/800.f); // 723
        puzzleNumberFontSize = screenSizeX * (18.f/800.f); // 18
        puzzleStringX = screenSizeX * (100.f/800.f); // 100
        puzzleStringY = screenSizeX * (20.f/800.f); // 20
        errorBoxPosX = screenSizeX * (620.f/800.f); // 620
        errorBoxPosY = screenSizeX * (0.f/800.f); // 0
        errorBoxSizeX = screenSizeX * (100.f/800.f); // 100
        errorBoxSizeY = screenSizeX * (21.f/800.f); // 21
        errorStringPosX = screenSizeX * (625.f/800.f); // 625
        errorStringPosY = screenSizeX * (20.f/800.f); // 20
        skinnyRowLineSize = screenSizeX * (1.f/800.f); // 1
        numbersOptionStartX = screenSizeX * (52.f/800.f); // 52
        numbersOptionStartY = screenSizeX * (810.f/800.f); // 810
        numbersOptionWidthSpace = screenSizeX * (759.f/800.f); // 759
        numbersOptionCircleLineSize = screenSizeX * (2.f/800.f); // 2
        numbersOptionFontSize = screenSizeX * (30.f/800.f); // 30
        numbersOptionCircleSize = screenSizeX * (65.f/800.f); // 65
        boardNumbersFontSize = screenSizeX * (60.f/800.f); // 60
        notesNumbersFontSize = screenSizeX * (18.f/800.f); // 20
        notesEraseFontSize = screenSizeX * (40.f/800.f); // 40
        notesEraseBoxThickness = screenSizeX * (2.f/800.f); // 2
        notesEraseBoxSizeX = screenSizeX * (170.f/800.f); // 170
        notesEraseBoxSizeY = screenSizeX * (50.f/800.f); // 50
        notesModeFontSize = screenSizeX * (15.f/800.f); // 15
        exitButtonStartX = screenSizeX * (11.f/800.f); // 11
        exitButtonStartY = screenSizeX * (15.f/800.f); // 15
        exitButtonThickness = screenSizeX * (3.f/800.f); // 3
        exitButtonSizeX = screenSizeX * (50.f/800.f); // 50
        exitButtonSizeY = screenSizeX * (50.f/800.f); // 50
        exitStringFontSize = screenSizeX * (50.f/800.f); // 50
        exitStringPosX = screenSizeX * (19.f/800.f); // 19
        exitStringPosY = screenSizeX * (58.f/800.f); // 58
        welcomeMessageFontSize = screenSizeX * (80.f/800.f); // 80
        welcomeMessagePosX = screenSizeX * (35.f/800.f); // 35
        welcomeMessagePosY = screenSizeX * (130.f/800.f); // 130
        mainMenuBoxThickness = screenSizeX * (3.f/800.f); // 3
        mainMenuButtonsStartX = screenSizeX * (200.f/800.f); // 200
        mainMenuButtonsStartY = screenSizeX * (325.f/800.f); // 325
        continueButtonFontSize = screenSizeX * (35.f/800.f); // 35
        continueButtonStartX = screenSizeX * (300.f/800.f); // 300
        continueButtonStartY = screenSizeX * (200.f/800.f); // 200
        continueButtonSizeX = screenSizeX * (200.f/800.f); // 200
        continueButtonSizeY = screenSizeX * (50.f/800.f); // 50
        continueStringPosX = screenSizeX * (330.f/800.f); // 330
        continueStringPosY = screenSizeX * (237.f/800.f); // 237
        easyMedHardFontSize = screenSizeX * (60.f/800.f); // 60
        easyStringPosX = screenSizeX * (335.f/800.f); // 335
        easyStringPosY = screenSizeX * (395.f/800.f); // 395
        mediumStringPosX = screenSizeX * (295.f/800.f); // 295
        mediumStringPosY = screenSizeX * (545.f/800.f); // 545
        hardStringPosX =  screenSizeX * (335.f/800.f); // 335
        hardStringPosY = screenSizeX * (695.f/800.f); // 695
        easyMedHardButtonGapY = screenSizeX * (150.f/800.f); // 150
        optionsCirclePosX = screenSizeX * (700.f/800.f); // 700
        optionsCirclePosY = screenSizeX * (750.f/800.f); // 750
        optionsCircleSize = screenSizeX * (75.f/800.f); // 75
        loadingBarStartX = screenSizeX * (175.f/800.f); // 175
        loadingBarStartY = screenSizeX * (200.f/800.f); // 200
        loadingBarSizeX = screenSizeX * (450.f/800.f); // 450
        loadingBarSizeY = screenSizeX * (50.f/800.f); // 50
        winLoseMessageFontSize = screenSizeX * (70.f/800.f); // 70
        afterGameMessageFontSize =  screenSizeX * (50.f/800.f); // 50
        afterGameBoxThickness =  screenSizeX * (2.f/800.f); // 2
        afterGameBoxSizeY =  screenSizeX * (60.f/800.f); // 60
        afterGameBoxGap = screenSizeX * (90.f/800.f); // 90
        optionsMenuBoxThickness = screenSizeX * (3.f/800.f); // 3
        optionsMenuFontSize = screenSizeX * (80.f/800.f); // 80
        lightStringFontSize = screenSizeX * (45.f/800.f); // 45
        loadNewPuzzlesStringFontSize = screenSizeX * (50.f/800.f); // 50
        boxSize = (screenSizeX * (648.f/800.f)); // Square size of the sudoku box onto the screen, 648
        boardXstart = (screenSizeX * (76.f/800.f)); // X-point where the sudoku box point begins, 76
        boardYstart = (screenSizeX * (25.f/800.f)); // Y-point where the sudoku box point begins, 25
        numberNotesFont = new Font("Arial",Font.PLAIN, (int) (screenSizeX*(20.f/800.f))); // Font for the number notes
        aboveBoardFont = new Font("Arial",Font.ITALIC, (int) (screenSizeX*(25.f/800.f))); // Font for the Error & Puzzle #

        setBackground(modeColorPrime);
        setPreferredSize(new Dimension((int)screenSizeX,(int)screenSizeY));
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        if (doClearScreen) {
//            g2D.clearRect(0,0,(int)screenSizeX,(int)screenSizeY);
            doClearScreen = false;


        }


        if (!mainMenuActivated) {

            continueButton = true;

            // Debug

            for (int i = 0; i < 81; i++) {
                clearSquare(g2D,i,modeColorPrime);
            }

            highlightRowColBox(g2D, oldSquareNum, modeColorPrime); // Unhighlight the rows,cols, and box of previous square
            highlightSameNumber(g2D, oldSquareNum, modeColorPrime); // Unhighlight the old square similar numbers

            highlightSquare(g2D, oldSquareNum, modeColorPrime);
            highlightSquare(g2D, squareNum, mainHighlightSquare); // highlight the selected square in a cyan color
            highlightRowColBox(g2D, squareNum, mainHighlightRowColBox); // highlight the row, cols, and box of current selected square gray



            // Drawing Number Notes Test


            // Highlight Number Options Debug && EraseNotes Option Debug
            // highlightNumberOptions(g2D,1);


            // if the erase option is selected, then clear the highlighted square if there is no fixed
            // value to it already
            if (eraseNotesOption == 1 && userWinLose == -1) {
                if (squareNum != -1) {

                    for (int j = 0; j < 9; j++) {
                        notesSquares[squareNum][j] = false;
                    }

                    if (!gameBoard.getBoard()[squareNum / 9][squareNum % 9].getRevealed()) {
                        filledSquares[squareNum / 9][squareNum % 9] = false;
                        filledNumber[squareNum / 9][squareNum % 9] = 0;

                        // Debug
                        System.out.println();
                        System.out.println("squareNum = " + squareNum);
                        System.out.println("filledNumber[squareNum/10][squareNum%10] = " + filledNumber[squareNum / 9][squareNum % 9]);
                    }

                }
            }

            // If notes Mode is off, then when the user clicks, the square will be drawn with the number selected
            // Only works if the square is not an originally revealed square
            if (squareNum != -1 && numberOptionClicked > -1 && userWinLose == -1) {
                if (!notesMode) {
                    if (filledNumber[squareNum / 9][squareNum % 9] != -1) {

                        // If the selected square already has the wrong number, and the user inputs the same
                        // wrong number, then make that number disappear
                        boolean wrongNumberChosenAgain = false;

                        if (gameBoard.getBoard()[squareNum/9][squareNum%9].getValue() != numberOptionClicked) {

                            // Only applies when the number selected is the same as the already wrong number in the
                            // square
                            if (filledNumber[squareNum / 9][squareNum % 9] == numberOptionClicked) {
                                filledSquares[squareNum / 9][squareNum % 9] = false;
                                filledNumber[squareNum / 9][squareNum % 9] = 0;
                                wrongNumberChosenAgain = true;
                            }
                        }

                        // Else if the wrong number is chosen a second time in a row, set filled Squares to true
                        // and set the filled number to the number option chosen
                        if (!wrongNumberChosenAgain) {
                            filledSquares[squareNum / 9][squareNum % 9] = true;
                            filledNumber[squareNum / 9][squareNum % 9] = numberOptionClicked;
                        }

                        // If the inputted number does not correspond with the game board, then the number will
                        // highlight red, and errorCount will increment
                        if (gameBoard.getBoard()[squareNum/9][squareNum%9].getValue() != numberOptionClicked) {

                            if (filledNumber[squareNum / 9][squareNum % 9] != 0) {
                                errorCount++;
                                if (errorCount >= errorLimit) {
                                    userWinLose = 2;
                                }
                            }
                        }

                        removeCorrespondingNotes(numberOptionClicked,squareNum);

                    }
                }
                else {
                    // if a valid number options is clicked and a valid squareNum is highlighted
                    //                    int total = convertSquareNumToNum(squareNum);

                    boolean optionSelected = notesSquares[squareNum][numberOptionClicked - 1];

                    // Debug
//                    System.out.println("total = " + squareNum);
//                    System.out.println("optionSelected = " + optionSelected);

                    if (!filledSquares[squareNum / 9][squareNum % 9]) {
                        notesSquares[squareNum][numberOptionClicked - 1] = !optionSelected;
                    }
                }
            }



            highlightSameNumber(g2D, squareNum, sameValueHighlight);
            drawRevealedNumbersOntoBoard(g2D, gameBoard); // Draws already revealed numbers in black

            drawAllNotes(g2D); // Draw all the notes onto the board
            drawAllFilledNumber(g2D);

            paintSudokuBoard(g2D);
            paintNumberOptions(g2D);
            paintNotesEraseOptions(g2D);
            paintExit(g2D);

            if (mPressed && userWinLose == -1) {

                // Highlight numbers Option

                if (numberOptionClicked > -1) {
                    highlightNumberOptions(g2D, numberOptionClicked, highlightNumberOptionColor);
                }

                // Highlight Erase Option
                highlightNotesEraseOption(g2D, eraseNotesOption, highlightNumberOptionColor);
                highlightExit(g2D, exitOption, highlightNumberOptionColor);

                // Highlight Play Again Option
                // highlightPlayAgain(g2D,playAgainOption,highlightNumberOptionColor);

                // Debug
                // System.out.println("mPressed CALLED");

                // if the notes option is on, and the user selects a number,
                // then the number note will be drawn in the square
            } else {

                // if note option not on, highlight the number options white
//                paintNumberOptions(g2D);


                // highlight the erase / notes button white
//                paintNotesEraseOptions(g2D);
//                paintExit(g2D);



            }


            // System.out.println("highlightSameNumber squareNum = " + squareNum);
//            highlightSameNumber(g2D, squareNum, sameValueHighlight);
//            drawRevealedNumbersOntoBoard(g2D, gameBoard); // Draws already revealed numbers in black
//
//            drawAllNotes(g2D); // Draw all the notes onto the board
//            drawAllFilledNumber(g2D);


            hasWon = userHasWon();
            // Determine whether the user has won or not
            if (hasWon) {
                userWinLose = 1;
            }

//             paintSudokuBoard(g2D);
//             paintNumberOptions(g2D);
//             paintNotesEraseOptions(g2D);
//             paintExit(g2D);


            if (errorCount >= errorLimit || hasWon) {
                paintPlayAgain(g2D);
            }

            if (mPressed) {
                // Highlight Play Again Option
                highlightPlayAgain(g2D,playAgainOption,highlightNumberOptionColor);
            }

        }
        else {

            if (mPressed) {
                paintMainMenu(g2D);
                highlightMainMenu(g2D,menuOption,highlightNumberOptionColor,highlightSquareColor);
            }
            else {
                paintMainMenu(g2D);
            }


            // Debug
//            System.out.println("PAINT CALLED!!!");

            if (!loadingDone) {
                int amountSquares;

                // Original set to 30, 33, 36
                // Hard
                if (loadingCount >= 10) {
                    amountSquares = random.nextInt(3)+30; // was 30 -> should be between 29 and 32
//                    amountSquares = 50;
                }
                // Medium
                else if (loadingCount >= 5) {
                    amountSquares = random.nextInt(4)+33; // was 33 -> should be between 35 and 32
//                    amountSquares = 50;
                }
                // Easy
                else {
                    amountSquares = random.nextInt(7)+36; // was 36 -> should be between 42 and 36
//                    amountSquares = 50;
                }

                if (loadingCount < 15) {
                    easyMediumHardBoards[(loadingCount) / 5][(loadingCount) % 5] =
                            loadBoard(1, amountSquares, true, 1);

                    // when the loadingCount reaches 15, then set loadingDone to true
                    if (loadingCount == 15) {
                        allLevelBoards = easyMediumHardBoards;
                        loadingDone = true; // Set loadingDone to false afterwards, so this if statement does reach any more
                        System.out.println();
                        printEasyMediumHardBoards();
                    }
                }

                // Debug
                System.out.println("loadingCount = " + loadingCount);

                paintLoadingBar(g2D);
                fillLoadingBar(g2D);
            }

            // Only make the loading bar disappear once the user has first clicked a level
            if (saveLevel == -1 || saveSpot == -1) {
                paintLoadingBar(g2D);
                fillLoadingBar(g2D);
            }

            // paintMainMenu(g2D);

            if (optionMenuOn) {

                paintOptions(g2D);

                if (mPressed) {
                    highlightOption(g2D,optionOpt,highlightNumberOptionColor);
                }
                else {
                    paintOptions(g2D);
                }
            }




        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
//        System.out.println("Some key was pressed");
        if (e.getKeyCode() == KeyEvent.VK_S)
        {
//            System.out.println("S key was pressed");
            BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(),BufferedImage.TYPE_INT_RGB);
            this.paint(img.getGraphics());
            String theFileName = "test" + screenshotNumber + ".png";
            screenshotNumber++;
            File outputFile = new File(theFileName);
            try {
                ImageIO.write(img, "png", outputFile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (!mainMenuActivated) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//                System.out.println("Right Arrow Key Pressed");
                if (squareNum % 9 != 8) {
                    squareNum++;
                    System.out.println("squareNum = " + squareNum);
                    repaint();
                }
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT)
            {
//                System.out.println("Left Arrow Key Pressed");
                if (squareNum % 9 != 0) {
                    squareNum--;
                    System.out.println("squareNum = " + squareNum);
                    repaint();
                }
            }
            else if (e.getKeyCode() == KeyEvent.VK_UP)
            {
//                System.out.println("Up Arrow Key Pressed");
                if (squareNum / 9 != 0) {
                    squareNum -= 9;
                    System.out.println("squareNum = " + squareNum);
                    repaint();
                }
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN)
            {
//                System.out.println("Down Arrow Key Pressed");
                if (squareNum / 9 != 8) {
                    squareNum += 9;
                    System.out.println("squareNum = " + squareNum);
                    repaint();
                }
            }
            else if (e.getKeyCode() == KeyEvent.VK_1 && lastKeyPressed != KeyEvent.VK_1)
            {
//                System.out.println("1 Key Pressed");
                lastKeyPressed = KeyEvent.VK_1;
                mPressed = true;
                if ((!numberAlreadyFilled(1) && !notesMode)
                        || (numberAlreadyFilled(1) && notesMode)
                        || (!numberAlreadyFilled(1) && notesMode))
                {
                    numberOptionClicked = 1;
                }
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_2 && lastKeyPressed != KeyEvent.VK_2)
            {
//                System.out.println("2 Key Pressed");
                lastKeyPressed = KeyEvent.VK_2;
                mPressed = true;
                if ((!numberAlreadyFilled(2) && !notesMode)
                        || (numberAlreadyFilled(2) && notesMode)
                        || (!numberAlreadyFilled(2) && notesMode))
                {
                    numberOptionClicked = 2;
                }
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_3 && lastKeyPressed != KeyEvent.VK_3)
            {
//                System.out.println("3 Key Pressed");
                lastKeyPressed = KeyEvent.VK_3;
                mPressed = true;
                if ((!numberAlreadyFilled(3) && !notesMode)
                        || (numberAlreadyFilled(3) && notesMode)
                        || (!numberAlreadyFilled(3) && notesMode))
                {
                    numberOptionClicked = 3;
                }
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_4 && lastKeyPressed != KeyEvent.VK_4)
            {
//                System.out.println("4 Key Pressed");
                lastKeyPressed = KeyEvent.VK_4;
                mPressed = true;
                if ((!numberAlreadyFilled(4) && !notesMode)
                        || (numberAlreadyFilled(4) && notesMode)
                        || (!numberAlreadyFilled(4) && notesMode))
                {
                    numberOptionClicked = 4;
                }
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_5 && lastKeyPressed != KeyEvent.VK_5)
            {
//                System.out.println("5 Key Pressed");
                lastKeyPressed = KeyEvent.VK_5;
                mPressed = true;
                if ((!numberAlreadyFilled(5) && !notesMode)
                        || (numberAlreadyFilled(5) && notesMode)
                        || (!numberAlreadyFilled(5) && notesMode))
                {
                    numberOptionClicked = 5;
                }
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_6 && lastKeyPressed != KeyEvent.VK_6)
            {
//                System.out.println("6 Key Pressed");
                lastKeyPressed = KeyEvent.VK_6;
                mPressed = true;
                if ((!numberAlreadyFilled(6) && !notesMode)
                        || (numberAlreadyFilled(6) && notesMode)
                        || (!numberAlreadyFilled(6) && notesMode))
                {
                    numberOptionClicked = 6;
                }
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_7 && lastKeyPressed != KeyEvent.VK_7)
            {
//                System.out.println("7 Key Pressed");
                lastKeyPressed = KeyEvent.VK_7;
                mPressed = true;
                if ((!numberAlreadyFilled(7) && !notesMode)
                        || (numberAlreadyFilled(7) && notesMode)
                        || (!numberAlreadyFilled(7) && notesMode))
                {
                    numberOptionClicked = 7;
                }
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_8 && lastKeyPressed != KeyEvent.VK_8)
            {
//                System.out.println("8 Key Pressed");
                lastKeyPressed = KeyEvent.VK_8;
                mPressed = true;
                if ((!numberAlreadyFilled(8) && !notesMode)
                        || (numberAlreadyFilled(8) && notesMode)
                        || (!numberAlreadyFilled(8) && notesMode))
                {
                    numberOptionClicked = 8;
                }
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_9 && lastKeyPressed != KeyEvent.VK_9)
            {
//                System.out.println("9 Key Pressed");
                lastKeyPressed = KeyEvent.VK_9;
                mPressed = true;
                if ((!numberAlreadyFilled(9) && !notesMode)
                        || (numberAlreadyFilled(9) && notesMode)
                        || (!numberAlreadyFilled(9) && notesMode))
                {
                    numberOptionClicked = 9;
                }
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_N && lastKeyPressed != KeyEvent.VK_N)
            {
//                System.out.println("N Key Pressed");
                lastKeyPressed = KeyEvent.VK_N;
                mPressed = true;
                notesMode = !notesMode;
                eraseNotesOption = 2;
                numberOptionClicked = -1;
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_E && lastKeyPressed != KeyEvent.VK_E)
            {
//                System.out.println("E Key Pressed");
                lastKeyPressed = KeyEvent.VK_E;
                mPressed = true;
                eraseNotesOption = 1;
                numberOptionClicked = -1;
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_X && lastKeyPressed != KeyEvent.VK_X)
            {
//                System.out.println("X Key Pressed");
                if (userWinLose == -1) {
                    lastKeyPressed = KeyEvent.VK_X;
                    exitOption = 1;
                    mPressed = true;
//                mainMenuActivated = true;
                    numberOptionClicked = -1;
                    repaint();
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (!mainMenuActivated) {
            if (e.getKeyCode() == KeyEvent.VK_1)
            {
                lastKeyPressed = -1;
                mPressed = false;
                numberOptionClicked = -1;
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_2)
            {
                lastKeyPressed = -1;
                mPressed = false;
                numberOptionClicked = -1;
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_3)
            {
                lastKeyPressed = -1;
                mPressed = false;
                numberOptionClicked = -1;
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_4)
            {
                lastKeyPressed = -1;
                mPressed = false;
                numberOptionClicked = -1;
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_5)
            {
                lastKeyPressed = -1;
                mPressed = false;
                numberOptionClicked = -1;
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_6)
            {
                lastKeyPressed = -1;
                mPressed = false;
                numberOptionClicked = -1;
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_7)
            {
                lastKeyPressed = -1;
                mPressed = false;
                numberOptionClicked = -1;
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_8)
            {
                lastKeyPressed = -1;
                mPressed = false;
                numberOptionClicked = -1;
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_9)
            {
                lastKeyPressed = -1;
                mPressed = false;
                numberOptionClicked = -1;
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_N)
            {
                lastKeyPressed = -1;
                mPressed = false;
                eraseNotesOption = -1;
                numberOptionClicked = -1;
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_E)
            {
                lastKeyPressed = -1;
                mPressed = false;
                eraseNotesOption = -1;
                numberOptionClicked = -1;
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_X)
            {
                if (userWinLose == -1) {
                    lastKeyPressed = -1;
                    mPressed = false;
                    mainMenuActivated = true;
                    numberOptionClicked = -1;
                    exitOption = -1;
                    repaint();
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        int mouseX = e.getX();
        int mouseY = e.getY();

        mPressed = true;
        oldSquareNum = squareNum;
        numberOptionClicked = convertClickToNumberOption(mouseX,mouseY);

        // If there is already 9 of the number on the board, then don't add any more of the number

        if (!mainMenuActivated) {

            if (numberAlreadyFilled(numberOptionClicked) && !notesMode) {
                numberOptionClicked = -1;
            }
            eraseNotesOption = getNotesEraseOptionFromClick(mouseX, mouseY);
            exitOption = getExitOption(mouseX, mouseY);

            if (errorCount >= errorLimit || hasWon) {
                playAgainOption = getPlayAgainOption(mouseX,mouseY);
            }

            if (mouseX > boardXstart && mouseY > boardYstart && loadingDone && userWinLose == -1) {
                if (mouseX < (boxSize+boardXstart) && mouseY < (boxSize+boardYstart)) {
                    int temp = squareNum;
                    squareNum = getSquareNumFromClick(mouseX,mouseY);
                    oldSquareNum = temp;

                    // Debug
//                    System.out.println("squareNum = " + squareNum);
//                    System.out.println("oldSquareNum = " + oldSquareNum);
                }
            }
        }
        else {
            // Only read the menu option if loading is done
            if (loadingDone) {
                menuOption = getMainMenuOption(mouseX, mouseY);

                // If the option menu is on, and the user tries to click on something other than the options circle,
                // then set menuOption to -1
                if (optionMenuOn && menuOption != 5) {
                    menuOption = -1;
                }
                optionOpt = getOptionNum(mouseX,mouseY);
            }
            exitOption = -1;
        }

        if (menuOption == 1 && !continueButton) {
            menuOption = -1;
        }

        // Debug
//        System.out.println("Exit Option = " + exitOption);
//        System.out.println("Menu Option = " + menuOption);
//        System.out.println("Play Again Option = " + playAgainOption);

        // If the notes button is pressed, then flip the notesMode boolean
        // !mainMenuActivated is added to make sure that both scenes don't overlap
        if (eraseNotesOption == 2 && !mainMenuActivated) {
            notesMode = !notesMode;
        }

        // Debug
//        System.out.println("numberOptionClicked = " + numberOptionClicked);
//        System.out.println("eraseNotesOption = " + eraseNotesOption);


        repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        System.out.println("mouseReleased CALLED");

        mPressed = false;

        numberOptionClicked = -1;

        if (mainMenuActivated) {
            System.out.println("mainMenuActivated");
            if (menuOption == 1 || menuOption == 2 || menuOption == 3 || menuOption == 4) {

                doClearScreen = true;
                mainMenuActivated = false;

                // Debug
                System.out.println("menuOption = " + menuOption);

                int numR = random.nextInt(5);

                if (menuOption == 1) {
                    if (saveLevel == -1 || saveSpot == -1) {
                        gameBoard = allLevelBoards[0][numR];
                        setNotesSquares();
                        setFilledSquaresAndNumbers();
                        saveLevel = 0;
                        saveSpot = numR;
//                        System.out.println("oldErrorCount = " + oldErrorCount);

                    }
                    else {
                        errorCount = oldErrorCount;
                    }

                }
                else if (menuOption == 2) {
                    gameBoard = allLevelBoards[0][numR];
                    saveLevel = 0;
                    saveSpot = numR;
                    errorCount = 0;
                }
                else if (menuOption == 3) {
                    gameBoard = allLevelBoards[1][numR];
                    saveLevel = 1;
                    saveSpot = numR;
                    errorCount = 0;
                }
                else if (menuOption == 4) {
                    gameBoard = allLevelBoards[2][numR];
                    saveLevel = 2;
                    saveSpot = numR;
                    errorCount = 0;
                }

                if (menuOption != 1) {
                    setNotesSquares();
                    setFilledSquaresAndNumbers();
                }

            }
            else if (menuOption == 5) {

                // if true then false; if false, then true
                optionMenuOn = !optionMenuOn;

                // if option menu is on, then clear the screen
                if (!optionMenuOn) {
                    doClearScreen = true;
                }
            }

            // If exit on option Menu is pressed, then clear the screen
            if (optionOpt == 5 && optionMenuOn) {
                optionMenuOn = false;
                doClearScreen = true;
            }
            // else if Dark is pressed on the options menu, then switch the color modes
            else if (optionOpt == 2 && optionMenuOn) {
                // Color tempCol = modeColorPrime;
                modeColorPrime = new Color(28, 28, 28);
                modeColorSecond = Color.white;
                mainHighlightRowColBox = darkHighlightRowColBoxColor;
                mainHighlightSquare = darkModeHighlight;
                numberNotesColorPlain = Color.white;
                numberNotesColorBold = Color.white;
                doClearScreen = true;
                setBackground(modeColorPrime);
            }
            // else if Light is pressed on the options menu, then switch the color modes
            else if (optionOpt == 1 && optionMenuOn) {
                modeColorPrime = Color.white;
                modeColorSecond = new Color(28, 28, 28);
                mainHighlightRowColBox = highlightRowColBoxColor;
                mainHighlightSquare = highlightSquareColor;
                numberNotesColorPlain = new Color(65, 65, 65);
                numberNotesColorBold = new Color(28, 28, 28);
                doClearScreen = true;
                setBackground(modeColorPrime);
            }
            // else if the user selects No to loading new puzzles, then clear the screen
            else if (optionOpt == 4 && optionMenuOn) {
                optionMenuOn = false;
                doClearScreen = true;
            }
            // else if the user selects Yes to loading new puzzles
            else if (optionOpt == 3 && optionMenuOn) {
                loadingDone = false;
//                easyMediumHardBoards = new Board[3][5];
                for (int i = 0; i < 3; i++)
                {
                    for (int j = 0; j < 5; j++)
                    {
                        for (int k = 0; k < 9; k++)
                        {
                            easyMediumHardBoards[i][j].clearRow(k);
                        }
                    }
                }
                loadingCount = 0;
                doClearScreen = true;
                optionMenuOn = false;
                continueButton = false;
            }
        }


        // Go to main menu if....:

        // Exit Option is pressed
        if (exitOption == 1 && !mainMenuActivated && userWinLose == -1) {
            mainMenuActivated = true;
            doClearScreen = true;
            oldErrorCount = errorCount;
            errorCount = 0;
        }

        // Or Main Menu Button is pressed
        else if (playAgainOption == 2 && !mainMenuActivated) {
            playAgainOption = -1;
            mainMenuActivated = true;
            doClearScreen = true;
            errorCount = 0;
            userWinLose = -1;

            continueButton = false;

        }

        // If the user has lost and has selected to try again, then just reset the board
        if (playAgainOption == 1 && userWinLose == 2) {
            playAgainOption = -1;
            doClearScreen = true;
            userWinLose = -1;
            gameBoard = allLevelBoards[saveLevel][saveSpot];
            setNotesSquares();
            setFilledSquaresAndNumbers();
            errorCount = 0;
        }
        // Else if the user has lost and has selected to play another puzzle of similar level, then
        // generate another random board that is not the same as the previous board
        else if (playAgainOption == 1 && userWinLose == 1) {
            playAgainOption = -1;
            doClearScreen = true;
            userWinLose = -1;

            int randSpot;

            do {
                randSpot = random.nextInt(5);
            } while (randSpot == saveSpot);

            saveSpot = randSpot;
            gameBoard = allLevelBoards[saveLevel][saveSpot];
            setNotesSquares();
            setFilledSquaresAndNumbers();

            errorCount = 0;


        }


        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // Paints the empty Sudoku Board onto the window
    public void paintSudokuBoard(Graphics2D g2d) {

        g2d.setColor(modeColorSecond);
        g2d.setStroke(new BasicStroke(easyMedHardFont));
        g2d.drawRect((int)boardXstart,(int)boardYstart,(int)boxSize,(int)boxSize); // 648 / 9 = 72

        // Print the Level Difficulty and Number of Errors
        // Level Difficulty
        g2d.setFont(aboveBoardFont);
        int xPosText = (int)xPosTextEMH;
        int yPosText = (int)yPosTextEMH;

        String levelName = "";

        if (saveLevel == 0) {
            levelName = "Easy";
        }
        else if (saveLevel == 1) {
            levelName = "Medium";
            xPosText = (int) (screenSizeX * (355.f/800.f)); // 355
        }
        else if (saveLevel == 2) {
            levelName = "Hard";
        }

        g2d.drawString(levelName,xPosText,yPosText);

        // Puzzle Number
        g2d.setFont(new Font("Arial",Font.PLAIN,(int)puzzleNumberFontSize));
        String puzzleString = "Puzzle #";
        puzzleString += Integer.toString(saveSpot+1);
        puzzleString += "/5";
        g2d.drawString(puzzleString,puzzleStringX,puzzleStringY);

        // Errors

//        if (oldErrorCount != errorCount) {
//            g2d.setColor(modeColorPrime);
//            g2d.fillRect((int)errorBoxPosX, (int)errorBoxPosY, (int)errorBoxSizeX, (int)errorBoxSizeY);
//        }

        g2d.setColor(modeColorSecond);
        String errorString = "Errors: ";
        errorString += Integer.toString(errorCount);
        errorString += "/3";
        g2d.drawString(errorString,errorStringPosX,errorStringPosY);
//        oldErrorCount = errorCount;


        // Represents where the line should start
        int startX;
        int startY;

        // Represents where the line should end
        int endX = (int) (boardXstart + boxSize);
        int endY = (int) (boardYstart + boxSize);

        // Create Thick Main Square Lines / Box
        for (int i = 0; i < 3; i++) {

            // Vertical Lines
            startY = (int) boardYstart;
            startX = (int) (boardXstart + (boxSize/3.f) * (float)(i+1));
            g2d.drawLine(startX,startY,startX,endY);

            // Horizontal Lines
            startY = (int) (boardYstart + (boxSize / 3) * (float)(i+1));
            startX = (int)boardXstart;
            g2d.drawLine(startX,startY,endX,startY);
        }

        g2d.setStroke(new BasicStroke(skinnyRowLineSize));

        int skip = -1; // will be set to 0 at first instance of loop

        // Create Skinny row / col lines
        for (int j = 0; j < 6; j++) {

            // Skips every 2 numbers
            // Skinny lines only needed at 1, 2, 4, 5, 7, and 8
            if (j % 2 == 0) {
                skip++;
            }

            // Vertical Lines
            startY = (int)boardYstart;
            startX = (int) (boardXstart + (boxSize/9.f) * (float)(j+1 + skip));
            g2d.drawLine(startX,startY,startX,endY);

            // Horizontal Lines
            startY = (int) (boardYstart + (boxSize/9.f) * (float)(j+1 + skip));
            startX = (int)boardXstart;
            g2d.drawLine(startX,startY,endX,startY);
        }

    }

    // Draw the number options below the game board
    public void paintNumberOptions(Graphics2D g2d) {

        int positionX = (int)numbersOptionStartX;
        int positionY = (int)numbersOptionStartY; // 810
        int numberWidthSpace = (int)numbersOptionWidthSpace;

        g2d.setStroke(new BasicStroke(numbersOptionCircleLineSize));
        g2d.setColor(modeColorSecond);

        for (int i = 1; i < 10; i++) {
            g2d.setFont(new Font("Arial",Font.BOLD,(int)numbersOptionFontSize));

            int circleX = positionX - (numberWidthSpace/30);
            int circleY = positionY - (numberWidthSpace/18);

            if (!numberAlreadyFilled(i) || notesMode) {

                g2d.setColor(modeColorPrime);
                g2d.fillOval(circleX,circleY,(int)numbersOptionCircleSize,(int)numbersOptionCircleSize);
                g2d.setColor(modeColorSecond);
                g2d.drawString(Integer.toString(i), positionX, positionY);
            }
            else {
                g2d.setColor(modeColorPrime);
                g2d.fillOval(circleX,circleY,(int)numbersOptionCircleSize,(int)numbersOptionCircleSize);
            }

            g2d.setColor(modeColorSecond);
            g2d.drawOval(circleX,circleY,(int)numbersOptionCircleSize,(int)numbersOptionCircleSize); // Also draw circle around the numbers, about 35x35

            positionX += numberWidthSpace / 9;
        }

    }

    // Highlights the circle surrounding one of the number options
    public void highlightNumberOptions(Graphics2D g2d, int number, Color color) {
        int startX = (int)numbersOptionStartX;
        int startY = (int)numbersOptionStartY; // 810
        int numberWidthSpace = (int)numbersOptionWidthSpace;

        // Just reverse the equation from the previous function
        int xPos = (startX) + (numberWidthSpace/9) * (number - 1) - (numberWidthSpace/30);
        int yPos = (startY) - (numberWidthSpace/18);

        g2d.setFont(new Font("Arial",Font.BOLD,(int)numbersOptionFontSize));

        // Set color to a yellowish color
        g2d.setColor(color);
        g2d.fillOval(xPos,yPos,(int)numbersOptionCircleSize,(int)numbersOptionCircleSize);


        // Location of number
        int postX = startX + (numberWidthSpace/9) * (number - 1);

        if (!numberAlreadyFilled(number) || notesMode) {
            // Debug
            System.out.println("number = " + number);
            g2d.setColor(modeColorSecond);
            g2d.drawString(Integer.toString(number), postX, startY);
            g2d.drawOval(xPos,yPos,(int)numbersOptionCircleSize,(int)numbersOptionCircleSize);
        }
    }

    // Returns the number option that was clicked by the user
    public int convertClickToNumberOption(int xPoint, int yPoint) {
        // The radius of the circle is 65
        // Thus, the length of the side of the square is sqrt(2*r^2) = 91.92 = 92
        int radius = (int)numbersOptionCircleSize;
        int numberWidthSpace = (int)numbersOptionWidthSpace;
        int startX = (int)numbersOptionStartX; // this is where the left of the number is
        int startY = (int)numbersOptionStartY; // this is where the bottom of the number is

        // The measured top (of circle) is observed to be startY - radius + 20
        // The measured bottom (of circle) is observed to be startY + 20;

        if (yPoint > (startY - radius + 20) && yPoint < (startY + 20)) {

            // The measured side (left) of circle is measured to be startX - 28
            // The measured side (right) of circle is measured to be startX + 38

            // 1
            if (xPoint > (startX - 28) && xPoint < (startX + 38)) {
                return 1;
            }

            startX += numberWidthSpace/9;

            // 2
            if (xPoint > (startX - 28) && xPoint < (startX + 38)) {
                return 2;
            }

            startX += numberWidthSpace/9;

            // 3
            if (xPoint > (startX - 28) && xPoint < (startX + 38)) {
                return 3;
            }

            startX += numberWidthSpace/9;

            // 4
            if (xPoint > (startX - 28) && xPoint < (startX + 38)) {
                return 4;
            }

            startX += numberWidthSpace/9;

            // 5
            if (xPoint > (startX - 28) && xPoint < (startX + 38)) {
                return 5;
            }

            startX += numberWidthSpace/9;

            // 6
            if (xPoint > (startX - 28) && xPoint < (startX + 38)) {
                return 6;
            }

            startX += numberWidthSpace/9;

            // 7
            if (xPoint > (startX - 28) && xPoint < (startX + 38)) {
                return 7;
            }

            startX += numberWidthSpace/9;

            // 8
            if (xPoint > (startX - 28) && xPoint < (startX + 38)) {
                return 8;
            }

            startX += numberWidthSpace/9;

            // 9
            if (xPoint > (startX - 28) && xPoint < (startX + 38)) {
                return 9;
            }


        }

        return -1;
    }

    // Draws the little number notes on the marked squares
    public void drawNumberNotes(Graphics2D g2d, int number, int sqNum) {

        int row = sqNum / 9;
        int col = sqNum % 9;

        int startX = (int) ( boardXstart + (float)col*(boxSize/9.f) + (boxSize/160.f));
        int startY = (int) ( boardYstart + (float)row*(boxSize/9.f) - (boxSize/280.f));

        System.out.println("startX = " + startX);
        System.out.println("startY = " + startY);

        // Set Text Color to Gray and set font to Arial, plain, 12
        g2d.setColor(numberNotesColorPlain);
        g2d.setFont(new Font("Arial",Font.PLAIN,(int)notesNumbersFontSize));


        // Formula:
        // To get numbers 1 - 9, with 0 <= i < 3 and 0 <= j < 3
        // genNum = 3 * i + j + 1
        for (int i = 0; i < 3; i++) {
            startX =(int) (boardXstart + (float)col*(boxSize/9.f) + (boxSize/108.f) - (boxSize/27.f));
            startY += (int) (boxSize/27.f);


            for (int j = 0; j < 3; j++) {

                startX += (int) (boxSize/27.f);
                int genNum = 3*i + j + 1;

                // If the squareNum has the value that is equal to genNum, then bold that value
                if (filledNumber[squareNum/9][squareNum%9] == number ||
                        (gameBoard.getBoard()[squareNum/9][squareNum%9].getRevealed() &&
                                number == gameBoard.getBoard()[squareNum/9][squareNum%9].getValue())) {
                    g2d.setFont(new Font("Arial",Font.BOLD,(int)notesNumbersFontSize));
                    g2d.setColor(numberNotesColorBold);
                }
                else {
                    g2d.setFont(numberNotesFont);
                    g2d.setColor(numberNotesColorPlain);
                }

                if (genNum == number) {
                    String numberS = Integer.toString(number);
                    g2d.drawString(numberS,startX,startY);

                }
            }
        }
    }

    // Highlights the square a bluish color based on where the square is clicked
    public void highlightSquare(Graphics2D g2d, int numberSquare, Color color) {
        int row = numberSquare / 9;
        int col = numberSquare % 9;

        // Rows will always be 0 - 8
        // Cols will always be 0 - 8
        // BoxSize/9 is just the size of one of the 9 little boxes in the row / col

        // Formula
        int xPoint = (int) ((float)col * (boxSize/9.f) + boardXstart);
        int yPoint = (int) ((float)row * (boxSize/9.f) + boardYstart);


        // The Highlighted color will be cyan
        g2d.setColor(color);
        g2d.fillRect(xPoint,yPoint,(int)(boxSize/9.f),(int)(boxSize/9.f));

    }

    // Returns the number of the square from where the mouse is clicked
    // Square Number = (row * 10 + col * 10)
    // Row and Col ranges from 0-8
    public int getSquareNumFromClick(int xPoint, int yPoint) {

        // To find the column and row of the square, just isolate col and row in the previous equations
        // from highlightSquare method

        int col = (int) (((float)xPoint - boardXstart) / (boxSize/9.f));
        int row = (int) (((float)yPoint - boardYstart) / (boxSize/9.f));

        if (col > 8 || row > 8) {
            return  -1;
        }

        return row * 9 + col % 9;
    }

    // This method highlights in the user desired Color, all the squares that are in the row, col and box of squareNum
    public void highlightRowColBox(Graphics2D g2d, int numberSquare, Color color) {
        g2d.setColor(color);
        int row = numberSquare / 9;
        int col = numberSquare % 9;

        // Row
        // The y-point will remain the same, while the x-point increments
        int xIncrement = (int)boardXstart;
        int yPoint = (int) (boardYstart + (float)row * (boxSize/9.f));

        for (int i = 0; i < 9; i++) {

            if (i != col) {
                g2d.fillRect(xIncrement, yPoint, (int) (boxSize / 9.f), (int) (boxSize / 9.f));
            }
            xIncrement += (int) boxSize / 9.f;
        }

        // Col
        // The x-point will remain the same, while the y-point increments
        int yIncrement = (int)boardYstart;
        int xPoint = (int) (boardXstart + (float)col * (boxSize/9.f));

        for (int j = 0; j < 9; j++) {
            if (j != row) {
                g2d.fillRect(xPoint, yIncrement, (int) (boxSize / 9.f), (int) (boxSize / 9.f));
            }
            yIncrement += boxSize / 9;
        }

        // Box (this only applies to 9x9 boards)
        // if 1-3 in row (aka 0-2(
        int rowStart = 0;
        int rowEnd = 0;

        // Row Check
        if (row < 3) {
            rowStart = 0;
            rowEnd = 2;
        }
        else if (row < 6) {
            rowStart = 3;
            rowEnd = 5;
        }
        else if (row < 9) {
            rowStart = 6;
            rowEnd = 8;
        }


        int colStart = 0;
        int colEnd = 0;

        // Col Check
        if (col < 3) {
            colStart = 0;
            colEnd = 2;
        }
        else if (col < 6) {
            colStart = 3;
            colEnd = 5;
        }
        else if (row < 9) {
            colStart = 6;
            colEnd = 8;
        }

        int ySpot;
        int xSpot;

        for (int i = rowStart; i <= rowEnd; i++) {
            ySpot = (int) ((float)i * (boxSize/9.f) + boardYstart);

            for (int j = colStart; j <= colEnd; j++) {

                if (i == row && j == col) {
                    continue;
                }

                xSpot = (int) ((float)j * (boxSize/9.f) + boardXstart);
                g2d.fillRect(xSpot,ySpot,(int) (boxSize/9.f),(int) (boxSize/9.f));
            }
        }


    }

    // Draws the already revealed numbers onto the game board
    public void drawRevealedNumbersOntoBoard(Graphics2D g2d, Board theBoard) {

        // Values of 20 for the x difference, and 56 for the y difference seems to work

        int xPos;
        int yPos = (int) (boardYstart + ((56.f/800.f)*screenSizeX) - (boxSize/9.f));

        // Font size of 60, Quicksand, plain seems to suffice
        g2d.setColor(modeColorSecond);
        // CH g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Quicksand",Font.PLAIN,(int)boardNumbersFontSize));


        // Draw the values from theBoard onto the screen
        for (int i = 0; i < theBoard.getSize(); i++) {

            yPos += (boxSize/9);
            xPos = (int) ( boardXstart + (20.f/800.f)*(screenSizeX) - (boxSize/9.f));
            for (int j = 0; j < theBoard.getSize(); j++) {

                xPos += (boxSize/9);

                if (theBoard.getBoard()[i][j].getRevealed()) {

                    int squareValue = theBoard.getBoard()[i][j].getValue();
                    String numberS = Integer.toString(squareValue);
                    g2d.drawString(numberS,xPos,yPos);
                }
            }
        }

    }

    // Draws the Notes and Erase Option below the game board
    public void paintNotesEraseOptions(Graphics2D g2d) {

        // Font is Arial, Plain, 40
        g2d.setFont(new Font("Arial",Font.PLAIN,(int)notesEraseFontSize));
        g2d.setColor(modeColorSecond);

        // Stroke is set to thickness of 2
        g2d.setStroke(new BasicStroke(notesEraseBoxThickness));
        int xPos = (int) (boardXstart + (boxSize/8.f));
        int yPos = (int) (boardYstart + boxSize + (screenSizeX * (15.f/800.f)));

        g2d.setColor(modeColorPrime);
        g2d.fillRect(xPos,yPos,(int)notesEraseBoxSizeX,(int)notesEraseBoxSizeY);
        g2d.setColor(modeColorSecond);
        g2d.drawRect(xPos,yPos,(int)notesEraseBoxSizeX,(int)notesEraseBoxSizeY);
        g2d.drawString("Erase",xPos+(int)(screenSizeX * (30.f/800.f)),yPos+(int)(screenSizeX * (40.f/800.f)));

        xPos += (int) ((boxSize/2.f) - (screenSizeX * (10.f/800.f)));
        g2d.setColor(modeColorPrime);
        g2d.fillRect(xPos,yPos,(int)notesEraseBoxSizeX,(int)notesEraseBoxSizeY);
        g2d.setColor(modeColorSecond);
        g2d.drawRect(xPos,yPos,(int)notesEraseBoxSizeX,(int)notesEraseBoxSizeY);
        g2d.drawString("Notes",xPos+(int)(screenSizeX * (35.f/800.f)),yPos+(int)(screenSizeX * (40.f/800.f)));

        // If notesMode is on, then onOffWord = "On"; else, it is equal to "Off"
        String onOffWord;
        if (notesMode) {
            onOffWord = "On";
        }
        else {
            onOffWord = "Off";
        }

        g2d.setFont(new Font("Arial",Font.ITALIC,(int)notesModeFontSize));
//        g2d.drawString(onOffWord,xPos+145,yPos+15);
        g2d.drawString(onOffWord,xPos+(int)(screenSizeX * (145.f/800.f)),yPos+(int)(screenSizeX * (15.f/800.f)));
    }

    // Highlights one of the options of Notes or Erase, depending on where the mouse is clicked
    public void highlightNotesEraseOption(Graphics2D g2d, int option, Color color) {
        int xPos = (int) (boardXstart + (boxSize/8.f));
        int yPos = (int) (boardYstart + boxSize + (screenSizeX * (15.f/800.f)));

        g2d.setColor(color);
        g2d.setFont(new Font("Arial",Font.PLAIN,(int)notesEraseFontSize));

//        int secondX = xPos + (boxSize/2) - 10;
        int secondX = (int) (xPos + (boxSize/2.f) - (screenSizeX * (10.f/800.f)));

        if (option == 1) {
            g2d.fillRect(xPos,yPos,(int)notesEraseBoxSizeX,(int)notesEraseBoxSizeY);
            g2d.setColor(modeColorSecond);
            g2d.drawRect(xPos,yPos,(int)notesEraseBoxSizeX,(int)notesEraseBoxSizeY);
//            g2d.drawString("Erase",xPos+30,yPos+40);
            g2d.drawString("Erase",xPos+(int)(screenSizeX * (30.f/800.f)),yPos+(int)(screenSizeX * (40.f/800.f)));
        }
        else if (option == 2) {
            xPos += (int)( (boxSize/2.f) - (screenSizeX * (10.f/800.f)));
            g2d.fillRect(secondX,yPos,(int)notesEraseBoxSizeX,(int)notesEraseBoxSizeY);
            g2d.setColor(modeColorSecond);
            g2d.drawRect(secondX,yPos,(int)notesEraseBoxSizeX,(int)notesEraseBoxSizeY);
//            g2d.drawString("Notes",secondX+35,yPos+40);
            g2d.drawString("Notes",xPos+(int)(screenSizeX * (35.f/800.f)),yPos+(int)(screenSizeX * (40.f/800.f)));


            String onOffWord;
            if (notesMode) {
                onOffWord = "On";
            }
            else {
                onOffWord = "Off";
            }

            g2d.setFont(new Font("Arial",Font.ITALIC,(int)notesModeFontSize));
            g2d.drawString(onOffWord,xPos+(int)(screenSizeX * (145.f/800.f)),yPos+(int)(screenSizeX * (15.f/800.f)));

        }

    }

    // Returns 1 if erase option clicked
    // Return 2 if notes option clicked
    // Return -1 otherwise
    public int getNotesEraseOptionFromClick(int xPoint, int yPoint) {
        int xPos = (int) (boardXstart + (boxSize/8.f));
        int yPos = (int) (boardYstart + boxSize + (screenSizeX * (15.f/800.f)));

        int secondX = (int) (xPos + (boxSize/2.f) - (screenSizeX * (10.f/800.f)));


        if (yPoint > yPos && yPoint < (yPos+notesEraseBoxSizeY)) {
            if (xPoint > xPos && xPoint < (xPos+notesEraseBoxSizeX)) {
                return 1;
            }
            else if (xPoint > (secondX) && xPoint < (secondX+notesEraseBoxSizeX)) {
                return 2;
            }
        }

        return -1;
    }

    // Clears the squares (aka paints the square white) corresponding to the square number
    public void clearSquare(Graphics2D g2d, int squareNum, Color color) {
        int row = squareNum / 9;
        int col = squareNum % 9;

        int xPoint = (int) (boardXstart + (float)col*(boxSize/9.f));
        int yPoint = (int) (boardYstart + (float)row*(boxSize/9.f));

        g2d.setColor(color);
        g2d.fillRect(xPoint,yPoint,(int)(boxSize/9.f),(int)(boxSize/9.f));

    }

    // Draws all the notes revealed in each square
    public void drawAllNotes(Graphics2D g2d) {
        boolean currentPart;

        // i range: 1-81 (inclusive)
        for (int i = 0; i < 81; i++) {

            // Debug

//            if (squareNumber != squareNum) {
//                clearSquare(g2D, squareNumber, Color.white);
//            }
//            else {
//                clearSquare(g2D,squareNumber,highlightSquareColor);
//            }

            if (filledSquares[i / 9][i % 9]) {
                continue;
            }

            for (int j = 0; j < 9; j++) {
                currentPart = notesSquares[i][j];


                // Formula:
                // Position in notesSquare array (81 rows) is: row * 8 + col + (row + 1)
                // Ex- 0 should be 1 - 0 * 8 + 0 + (0+1) = 1
                // Ex- 30 (row 3; col 0) should be 28 - 3 * 8 + 0 + (3+1) = 24 + 4 = 28
                if (currentPart) {
                    drawNumberNotes(g2d,j+1,i);
                }
            }
        }
    }

    // Converts Square Number, i.e. 00 10 20 to regular number i.e. 1 through 81
    public int convertSquareNumToNum(int sqNum) {

        // Formula:
        // Position in notesSquare array (81 rows) is: row * 8 + col + (row + 1)
        // Ex- 0 should be 1 - 0 * 8 + 0 + (0+1) = 1
        // Ex- 30 (row 3; col 0) should be 28 - 3 * 8 + 0 + (3+1) = 24 + 4 = 28
        int row = sqNum / 9;
        int col = sqNum % 9;
        int factor = row + 1;
        int total = (row * 8) + col + factor;

        return total;
    }

    // Draws the desired number onto the corresponding squareNumber
    public void drawNumber(Graphics2D g2d, int number, int squareNumber) {
        int xPos;
        int yPos = (int) (boardYstart + (screenSizeX * (56.f/800.f)) - (boxSize/9.f));


        int row = squareNumber / 9;
        int col = squareNumber % 9;

        // if the number is truly the correct number as the game board, then highlight
        // the user-inputted number as blue, else highlight the number as red
        if (number == gameBoard.getBoard()[squareNumber/9][squareNumber%9].getValue()) {
            g2d.setColor(Color.BLUE);
        }
        else {
            g2d.setColor(Color.red);
        }
        g2d.setFont(new Font("Quicksand",Font.PLAIN,(int)boardNumbersFontSize));


        // Draw the values from theBoard onto the screen
        for (int i = 0; i < gameBoard.getSize(); i++) {

            yPos += (boxSize/9);
            xPos = (int) (boardXstart + (screenSizeX * (20.f/800.f)) - (boxSize/9.f));

            for (int j = 0; j < gameBoard.getSize(); j++) {

                xPos += (boxSize/9);

                if (i == row && j == col) {

                    String numberS = Integer.toString(number);
                    g2d.drawString(numberS,xPos,yPos);
                }
            }
        }
    }

    // Draws all the numbers that have been inputted by the user
    public void drawAllFilledNumber(Graphics2D g2d) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (filledSquares[i][j]) {

                    int num = filledNumber[i][j];
                    int sqNum = i * 9 + j;

                    if (!(num == 0 || num == -1)) {
                        drawNumber(g2d, num,sqNum);
                    }
                }
            }
        }
    }

    // Highlights in gray all the squares with the same number as the highlighted square (squareNum)
    public void highlightSameNumber(Graphics2D g2d, int sqNum, Color color) {
        int row = sqNum/9;
        int col = sqNum % 9;

        int value = filledNumber[row][col];
        ArrayList<Integer> sameValues = new ArrayList<>();

        // Debug
        System.out.println();
        // System.out.println("sqNum = " + sqNum);
        // System.out.println("value = " + value);

        // If value is -1, then just reset the value to the real gameBoard number
        if (value == -1) {
            value = gameBoard.getBoard()[row][col].getValue();
        }

        // If the value isn't equal to 0, then add to the arraylist all the squareNumbers that contain the value
        if (value != 0) {
            for (int i = 0; i < gameBoard.getSize(); i++) {
                for (int j = 0; j < gameBoard.getSize(); j++) {
                    int gameBoardValue = gameBoard.getBoard()[i][j].getValue();
                    boolean gameBoardRevealed = gameBoard.getBoard()[i][j].getRevealed();

                    if (filledNumber[i][j] == value || (gameBoardRevealed && value == gameBoardValue)) {
                        int newSqNum = i * 9 + j;
                        sameValues.add(newSqNum);
                    }
                }
            }
        }

        for (Integer sameValue : sameValues) {
            if (sameValue != sqNum) {
                highlightSquare(g2d, sameValue, color);
            }
        }

    }

    // Draw the exit symbol for the player to go back into mainMenu
    public void paintExit(Graphics2D g2d) {
        g2d.setColor(modeColorSecond);
        // CH g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(exitButtonThickness));
        g2d.setColor(modeColorPrime);
        g2d.fillRect((int)exitButtonStartX, (int)exitButtonStartY, (int)exitButtonSizeX,(int)exitButtonSizeY);
        g2d.setColor(modeColorSecond);
        g2d.drawRect((int)exitButtonStartX, (int)exitButtonStartY, (int)exitButtonSizeX,(int)exitButtonSizeY);
        g2d.setFont(new Font("Arial",Font.BOLD,(int)exitStringFontSize));
        g2d.drawString("X",exitStringPosX,exitStringPosY);
    }

    // Highlights the exit button
    public void highlightExit(Graphics2D g2d, int option, Color color) {

        if (option == 1) {
            g2d.setColor(color);
            g2d.fillRect((int)exitButtonStartX, (int)exitButtonStartY, (int)exitButtonSizeX,(int)exitButtonSizeY);
            g2d.setFont(new Font("Arial",Font.BOLD,(int)exitStringFontSize));
            g2d.setColor(modeColorSecond);
            g2d.drawString("X",exitStringPosX,exitStringPosY);
        }
    }

    // Returns 1 if exit button is pressed; returns -1 otherwise
    public int getExitOption(int xPoint, int yPoint) {
        if (xPoint > (exitButtonStartX) && xPoint < (exitButtonStartX+exitButtonSizeX)) {
            if (yPoint > (exitButtonStartY) && yPoint < (exitButtonStartY+exitButtonSizeY)) {
                return 1;
            }
        }

        return -1;
    }

    // Draw the Main Menu
    public void paintMainMenu(Graphics2D g2d) {

        // Welcome to Sudoku Title
        g2d.setColor(modeColorSecond);
        // CH g2d.setColor(Color.black);
        g2d.setFont(new Font("Serif",Font.BOLD,(int)welcomeMessageFontSize)); // 400)); // 80
        g2d.drawString("Welcome To Sudoku!",welcomeMessagePosX,welcomeMessagePosY); // (35,130)

        g2d.setStroke(new BasicStroke(mainMenuBoxThickness));

        int startX = (int)mainMenuButtonsStartX; // 200
        int startY = (int)mainMenuButtonsStartY; // 325


        g2d.setFont(new Font("Arial",Font.PLAIN,(int)continueButtonFontSize)); // 35

        // Continue Button - Only shows up if the user has activated a board

        if (continueButton) {
            g2d.drawRect((int)continueButtonStartX, (int)continueButtonStartY, (int)continueButtonSizeX, (int)continueButtonSizeY); // (300, 200, 200, 50)
            g2d.drawString("Continue", continueStringPosX, continueStringPosY); // (330, 237)
        }

        // Easy, Medium, Hard Buttons
        g2d.setFont(new Font("Arial", Font.BOLD,(int)easyMedHardFontSize)); // 60
        String[] levelString = {"Easy","Medium","Hard"};
        g2d.drawString(levelString[0],easyStringPosX,easyStringPosY); // (335,395)
        g2d.drawString(levelString[1],mediumStringPosX,mediumStringPosY); // (295,545)
        g2d.drawString(levelString[2],hardStringPosX,hardStringPosY); // (335,695)

        for (int i = 0; i < 3; i++) {
            g2d.drawRect(startX,startY,(int)easyMedHardButtonWidth,(int)easyMedHardButtonHeight);
            startY += easyMedHardButtonGapY; // 150
        }

        // Options Circle Button
//        g2d.setColor(modeColorPrime);
//        g2d.fillOval(700,750,75,75);
        g2d.setColor(modeColorPrime);
        g2d.fillOval((int)optionsCirclePosX,(int)optionsCirclePosY,(int)optionsCircleSize,(int)optionsCircleSize); // (700, 750, 75, 75)
        g2d.setColor(modeColorSecond);
        g2d.drawOval((int)optionsCirclePosX,(int)optionsCirclePosY,(int)optionsCircleSize,(int)optionsCircleSize); // (700, 750, 75, 75)


    }

    // Paint the loading bar
    public void paintLoadingBar(Graphics2D g2d) {

        int width = (int)loadingBarSizeX; // 450
        int height = (int)loadingBarSizeY; // 50

        g2d.setColor(modeColorSecond);
        // CH g2d.setColor(Color.black);

        g2d.setStroke(new BasicStroke(mainMenuBoxThickness));
        g2d.drawRect((int)loadingBarStartX,(int)loadingBarStartY, width, height);

        // g2d.fillRect(startX+5 ,startY+5,(width/15)-10,height-10);


        for (float i = 1.f; i < 15; i++) {
            g2d.drawLine((int)loadingBarStartX + (int)(i*((float)width/15.f)), (int)loadingBarStartY,(int)loadingBarStartX + (int)(i*((float)width/15.f)),(int)loadingBarStartY+height);
            // g2d.fillRect(startX+5 + i*(width/15),startY+5,(width/15)-10,height-10);
        }

        g2d.setFont(new Font("Arial", Font.BOLD,(int) (screenSizeX * (50.f/800.f)))); // 50

        if (!loadingDone) {
            g2d.drawString("Loading...", (int) (screenSizeX * (297.5f/800.f)), (int) (screenSizeX * (295.f/800.f))); // (297.5,295)
        }
        else {
            g2d.drawString("Done !",(int) (screenSizeX * (327.5f/800.f)),(int) (screenSizeX * (295.f/800.f))); // (327.5f,295)
        }

    }

    // Fills the loading bar
    public void fillLoadingBar(Graphics2D g2d) {

        int startX = (int)loadingBarStartX; // 175
        int startY = (int)loadingBarStartY; // 200

        int width = (int)loadingBarSizeX; // 450
        int height = (int)loadingBarSizeY; // 50

        g2d.setColor(modeColorSecond);
        // CH g2d.setColor(Color.black);

        for (float i = 0; i < loadingCount; i++) {
//            g2d.fillRect(startX+5 + i*(width/15),startY+5,(width/15)-10,height-10);
            g2d.fillRect(startX+(int)(screenSizeX * (5.f/800.f)) + (int)(i*((float)width/15.f)),startY+(int)(screenSizeX * (5.f/800.f)),
                    (int)((float)width/15.f)-(int)(screenSizeX * (10.f/800.f)),height-(int)(screenSizeX * (10.f/800.f)));

        }
    }

    // Highlights main menu option, depending on what was chosen
    public void highlightMainMenu(Graphics2D g2d, int option, Color color1, Color colorContinue) {
        int startX = (int)mainMenuButtonsStartX; // 200
        int startY = (int)mainMenuButtonsStartY; // 325

        g2d.setFont(new Font("Arial", Font.BOLD,(int)easyMedHardFontSize));



        // Option 1 - Continue
//        if (option == 1) {
//            g2d.setFont(new Font("Arial",Font.PLAIN,35));
//            // g2d.setColor(highlightSquareColor);
//        }
//        else {
//            g2d.setFont(new Font("Arial",Font.CENTER_BASELINE,60));
//        }

        String[] levelString = {"Easy","Medium","Hard"};
        g2d.drawString(levelString[0],easyStringPosX,easyStringPosY); // (335,395)
        g2d.drawString(levelString[1],mediumStringPosX,mediumStringPosY); // (295,545)
        g2d.drawString(levelString[2],hardStringPosX,hardStringPosY); // (335,695)

        // 1 - Continue
        if (option == 1) {
            if (continueButton) {
                g2d.setColor(colorContinue);
                g2d.fillRect((int)continueButtonStartX, (int)continueButtonStartY, (int)continueButtonSizeX, (int)continueButtonSizeY);
                g2d.setColor(modeColorSecond);
                g2d.drawRect((int)continueButtonStartX, (int)continueButtonStartY, (int)continueButtonSizeX, (int)continueButtonSizeY);
                g2d.setFont(new Font("Arial", Font.PLAIN, (int)continueButtonFontSize)); // 35
                g2d.drawString("Continue", continueStringPosX, continueStringPosY);
            }
        }

        g2d.setFont(new Font("Arial", Font.BOLD,(int)easyMedHardFontSize)); // 60
        // 2 - Easy
        if (option == 2) {
            g2d.setColor(color1);
            g2d.fillRect(startX,startY,(int)easyMedHardButtonWidth,(int)easyMedHardButtonHeight);
            g2d.setColor(modeColorSecond);
            g2d.drawRect(startX,startY,(int)easyMedHardButtonWidth,(int)easyMedHardButtonHeight);
            g2d.drawString(levelString[0],easyStringPosX,easyStringPosY);
        }
        // 3 - Medium
        else if (option == 3) {
            g2d.setColor(color1);
            g2d.fillRect(startX,(int)(startY+easyMedHardButtonGapY),(int)easyMedHardButtonWidth,(int)easyMedHardButtonHeight);
            g2d.setColor(modeColorSecond);
            g2d.drawRect(startX,(int)(startY+easyMedHardButtonGapY),(int)easyMedHardButtonWidth,(int)easyMedHardButtonHeight);
            g2d.drawString(levelString[1],mediumStringPosX,mediumStringPosY);
        }
        // 4 - Hard
        else if (option == 4) {
            g2d.setColor(color1);
            g2d.fillRect(startX, (int)(startY + easyMedHardButtonGapY * 2.f), (int)easyMedHardButtonWidth, (int)easyMedHardButtonHeight);
            g2d.setColor(modeColorSecond);
            g2d.drawRect(startX, (int)(startY + easyMedHardButtonGapY * 2.f), (int)easyMedHardButtonWidth, (int)easyMedHardButtonHeight);
            g2d.drawString(levelString[2],hardStringPosX,hardStringPosY);
        }
        // 5 - Options
        else if (option == 5)
        {
            g2d.setColor(color1);
            g2d.fillOval((int)optionsCirclePosX,(int)optionsCirclePosY,(int)optionsCircleSize,(int)optionsCircleSize);
            g2d.setColor(modeColorSecond);
            // CH g2d.setColor(Color.black);
            g2d.drawOval((int)optionsCirclePosX,(int)optionsCirclePosY,(int)optionsCircleSize,(int)optionsCircleSize);
        }
    }

    // Returns the menu option selected; if none selected, returns -1
    public int getMainMenuOption(int xPoint, int yPoint) {
        int startX = (int)mainMenuButtonsStartX; // 200
        int startY = (int)mainMenuButtonsStartY; // 325

        // Following applies to easy, medium, and hard buttons
        int width = (int)easyMedHardButtonWidth;
        int height = (int)easyMedHardButtonHeight;
//        int gap = 50;
        int gap = (int)(screenSizeX * (50.f/800.f)); // 50

        // Easy, Medium, and Hard Buttons
        if (xPoint > startX && xPoint < (startX+width)) {


            // Continue
            if (xPoint > continueButtonStartX && xPoint < continueButtonStartX+continueButtonSizeX) {
                if (yPoint > continueButtonStartY && yPoint < continueButtonStartY+continueButtonSizeY) {
                    return 1;
                }
            }

            // Easy
            if (yPoint > startY && yPoint < (startY + height)) {
                return 2;
            }
            // Medium
            else if (yPoint > (startY+height+gap) && yPoint < (startY+gap+2*height)) {
                return 3;
            }
            else if (yPoint > (startY+2*gap+2*height) && yPoint < (startY+2*gap+3*height)) {
                return 4;
            }
        }
        else if (xPoint > optionsCirclePosX && xPoint < optionsCirclePosX+optionsCircleSize) {
            if (yPoint > optionsCirclePosY && yPoint < optionsCirclePosY+optionsCircleSize) {
                return 5;

            }
        }

        return -1;
    }

    // Clear the screen
    public void clearScreen(Graphics2D g2d) {
        g2d.setColor(modeColorPrime);
        // CH g2d.setColor(Color.white);
//        g2d.fillRect(0,0,800,900);
        g2d.fillRect(0,0,(int)screenSizeX,(int)screenSizeY);

    }

    // Print the easy, medium, and hard boards onto the serial log
    public void printEasyMediumHardBoards() {
        // Easy
        System.out.println("Easy: ");
        for (int i = 0; i < allLevelBoards[0].length; i++) {
            allLevelBoards[0][i].printBoard();
            System.out.println();
        }

        System.out.println();
        // Medium
        System.out.println("Medium: ");
        for (int i = 0; i < allLevelBoards[0].length; i++) {
            allLevelBoards[1][i].printBoard();
            System.out.println();
        }

        System.out.println();
        // Hard
        System.out.println("Hard: ");
        for (int i = 0; i < allLevelBoards[0].length; i++) {
            allLevelBoards[2][i].printBoard();
            System.out.println();
        }

    }

    public void setNotesSquares() {
        // Set notesSquares and set all of its elements to false
//        notesSquares = new boolean[81][9];
        for (int i = 0; i < 81; i++) {
            for (int j = 0; j < 9; j++) {
                notesSquares[i][j] = false;
            }
        }
    }

    // Reset filled squares and filled numbers
    // Filled squares - returns which squares already have numbers
    // Filled numbers - returns true when a number appears 9 times on the board
    public void setFilledSquaresAndNumbers() {
        // Set filledSquare and set all of its elements that are not already revealed to false
//        filledSquares = new boolean[9][9];

        // Also set filledNumber and set all of its elements that are revealed to its value
//        filledNumber = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if (!gameBoard.getBoard()[i][j].getRevealed()) {
                    filledSquares[i][j] = false;
                    filledNumber[i][j] = 0;
                }
                // if already filled, then set filled number to -1
                else {
                    filledSquares[i][j] = true;
                    filledNumber[i][j] = -1;
                    //filledNumber[i][j] = gameBoard.getBoard()[i][j].getValue();
                }
            }
        }
    }

    // Returns true if on a 9x9 board, 9 of the selected numbers are already on the board;
    // returns false otherwise
    public boolean numberAlreadyFilled(int num) {
        int count = 0;

        for (int i = 0; i < filledNumber.length; i++) {
            for (int j = 0; j < filledNumber[0].length; j++) {
                if (filledNumber[i][j] == num) {
                    count++;
                }
                else if (filledNumber[i][j]  == -1 && gameBoard.getBoard()[i][j].getValue() == num) {
                    count++;
                }
            }
        }

        if (count >= 9) {
            return true;
        }
        else {
            return false;
        }
    }

    // Removes the notes corresponding to the number inputted by the user; number notes in the same row, col,
    // or box will be removed
    public void removeCorrespondingNotes(int number, int sqNum) {
        int row = sqNum / 9;
        int col = sqNum % 9;

        int rowStart = 0;
        int rowEnd = 0;

        // Row Check
        if (row < 3) {
            rowStart = 0;
            rowEnd = 2;
        }
        else if (row < 6) {
            rowStart = 3;
            rowEnd = 5;
        }
        else if (row < 9) {
            rowStart = 6;
            rowEnd = 8;
        }


        int colStart = 0;
        int colEnd = 0;

        // Col Check
        if (col < 3) {
            colStart = 0;
            colEnd = 2;
        }
        else if (col < 6) {
            colStart = 3;
            colEnd = 5;
        }
        else if (row < 9) {
            colStart = 6;
            colEnd = 8;
        }

        // Remove all similar values in ROW
        int convertedNumber;
        int diffSqNum;
        for (int i = 0; i < gameBoard.getSize(); i++) {
            diffSqNum = row * 9 + i;
            convertedNumber = convertSquareNumToNum(diffSqNum);

            // Debug
            notesSquares[convertedNumber-1][number-1] = false;
        }

        // Remove all similar values in COL
        for (int j = 0; j < gameBoard.getSize(); j++) {
            diffSqNum = j*9 + col;
            convertedNumber = convertSquareNumToNum(diffSqNum);

            // Debug
            notesSquares[convertedNumber-1][number-1] = false;
        }

        for (int i = rowStart; i <= rowEnd; i++) {
            for (int j = colStart; j <= colEnd; j++) {
                diffSqNum = i*9 + j;
                convertedNumber = convertSquareNumToNum(diffSqNum);

                // Debug
                notesSquares[convertedNumber-1][number-1] = false;
            }
        }

    }

    // Creates a new board and calls the repaint method
    public Board loadBoard(int amount, int revealedSquares, boolean wantAllRowsFilled, int sqSPerRow) {
        int count = 0;
        Board theBoard = new Board();
        Board[] allBoards = new Board[amount];
        // Square[][] anySquare = new Square[0][];


        theBoard.revealSquaresNew(revealedSquares, wantAllRowsFilled, sqSPerRow);
        Board copyBoard = new Board();

        copyBoard.setBoard(theBoard.getBoard());
        allBoards[count] = copyBoard;
        theBoard.createBoard();

        loadingCount++;
        repaint();

        return copyBoard;

    }

    // Paints the play again menu
    public void paintPlayAgain(Graphics2D g2d) {

        int startX = (int) (boardXstart + 2.f*(boxSize/9.f));
        int startY = (int) (boardYstart + 2.f*(boxSize/9.f));

        int size = (int)((boxSize/9.f) * 5.f);

//        g2d.setColor(modeColorPrime);
        g2d.setColor(new Color(207, 234, 255));
        // CH g2d.setColor(Color.white);
        g2d.fillRect(startX,startY,size,size);
        g2d.setStroke(new BasicStroke(mainMenuBoxThickness));
//        g2d.setColor(modeColorSecond);
        g2d.setColor(Color.BLACK);
        // CH g2d.setColor(Color.black);
        g2d.drawRect(startX,startY,size,size);

        String gameWinMessage = " You win! ";
        String gameLosMessage = " You lose!";


        String theMessage = gameLosMessage;
        if (userWinLose == 1) {
            theMessage = gameWinMessage;
        }
        else if (userWinLose == 2) {
            theMessage = gameLosMessage;
        }

        g2d.setFont(new Font("Arial",Font.BOLD,(int)winLoseMessageFontSize)); // 70
//        g2d.drawString(theMessage,startX+(size/32),startY+100);
        g2d.drawString(theMessage,startX+(size/32),startY+ (int)(screenSizeX * (100.f/800.f)));

        // New Font for "Play Again" and "Main Menu"
        g2d.setFont(new Font("Arial",Font.PLAIN,(int)afterGameMessageFontSize));

        String tryAgainMessage = "Try Again";
        String mainMenuMessage = "Main Menu";
        String playAnotherMessage = "Play Another";
        g2d.setStroke(new BasicStroke(afterGameBoxThickness));

        // Try Again Button (if user loses)

        if (userWinLose == 2) {
            g2d.drawRect(startX + (size / 16), startY + (int)(screenSizeX * (170.f/800.f)), size - (size / 8), (int)afterGameBoxSizeY);
            g2d.drawString(tryAgainMessage, startX + (size / 5), startY + (int)(screenSizeX * (215.f/800.f)));
        }

        // Play Another (if user wins)

        else if (userWinLose == 1) {
            g2d.drawRect(startX + (size / 16), startY + (int)(screenSizeX * (170.f/800.f)), size - (size / 8), (int)afterGameBoxSizeY);
            g2d.drawString(playAnotherMessage, startX + (size / 10), startY + (int)(screenSizeX * (215.f/800.f)));
        }

        // Main Menu Button
        g2d.drawRect(startX+(size/16),startY+(int)(screenSizeX * (260.f/800.f)),size - (size/8),(int)afterGameBoxSizeY);
        g2d.drawString(mainMenuMessage,startX+(size/6),startY+(int)(screenSizeX * (305.f/800.f)));



    }

    // Returns the option that the user presses on when the play again menu pops up
    public int getPlayAgainOption(int xPoint, int yPoint) {

        int startX = (int) (boardXstart + 2.f*(boxSize/9.f));
        int startY = (int) (boardYstart + 2.f*(boxSize/9.f));

        int size = (int) ((boxSize/9.f) * 5.f);
        int width = size - (size/8);
        int height = (int)afterGameBoxSizeY;

        int theX = startX+(size/16);
//        int theY = startY+170;
        int theY = startY+(int)(screenSizeX * (170.f/800.f));

        int gap = (int)afterGameBoxGap;

        if (xPoint > theX && xPoint < (theX + width)) {
            // Option 1 - Try Again / Play Another
            if (yPoint > theY && yPoint < (theY+height)) {
                return 1;
            }

            // Option 2 - Main Menu
            else if (yPoint > (theY+gap) && yPoint < (theY+gap+height)) {
                return 2;
            }
        }

        return -1;
    }

    // Highlights the play Again option
    public void highlightPlayAgain(Graphics2D g2d, int numOpt, Color color) {

        int startX = (int) (boardXstart + 2.f*(boxSize/9.f));
        int startY = (int) (boardYstart + 2.f*(boxSize/9.f));

        int size = (int) ((boxSize/9.f) * 5.f);

        // New Font for "Try Again" and "Main Menu"
        g2d.setColor(Color.black);
        g2d.setFont(new Font("Arial",Font.PLAIN,(int)afterGameMessageFontSize));

        String tryAgainMessage = "Try Again";
        String mainMenuMessage = "Main Menu";
        String playAnotherMessage = "Play Another";
        g2d.setStroke(new BasicStroke(afterGameBoxThickness));

        if (numOpt == 1) {

            // Try Again Button (if user loses)

            if (userWinLose == 2) {
                g2d.setColor(highlightNumberOptionColor);
                g2d.fillRect(startX+(size/16),startY+(int)(screenSizeX * (170.f/800.f)),size - (size/8),(int)afterGameBoxSizeY);

                g2d.setColor(Color.black);
                g2d.drawRect(startX+(size/16),startY+(int)(screenSizeX * (170.f/800.f)),size - (size/8),(int)afterGameBoxSizeY);
                g2d.drawString(tryAgainMessage,startX+(size/5),startY+(int)(screenSizeX * (215.f/800.f)));
            }

            else if (userWinLose == 1) {
                // Play Another (if user wins)
                g2d.setColor(color);
                g2d.fillRect(startX + (size / 16), startY + (int)(screenSizeX * (170.f/800.f)), size - (size / 8), (int)afterGameBoxSizeY);

                g2d.setColor(Color.black);
                g2d.drawRect(startX + (size / 16), startY + (int)(screenSizeX * (170.f/800.f)), size - (size / 8), (int)afterGameBoxSizeY);
                g2d.drawString(playAnotherMessage, startX + (size / 10), startY + (int)(screenSizeX * (215.f/800.f)));
            }
        }
        else if (numOpt == 2) {

            // Main Menu
            g2d.setColor(color);
            g2d.fillRect(startX+(size/16),startY+(int)(screenSizeX * (260.f/800.f)),size - (size/8),(int)afterGameBoxSizeY);

            g2d.setColor(Color.black);
            g2d.drawRect(startX+(size/16),startY+(int)(screenSizeX * (260.f/800.f)),size - (size/8),(int)afterGameBoxSizeY);
            g2d.drawString(mainMenuMessage,startX+(size/6),startY+(int)(screenSizeX * (305.f/800.f)));
        }
    }

    // Return true if user has filled all squares with the correct number;
    // Return false otherwise
    public boolean userHasWon() {
        for (int i = 0; i < gameBoard.getSize(); i++) {
            for (int j = 0; j < gameBoard.getSize(); j++) {
                // if the filledNumber is not equal to -1 and the filledNumber corresponds with the game board square
                // then continue;
                // Otherwise, return false
                if (filledNumber[i][j] != -1 && gameBoard.getBoard()[i][j].getValue() != filledNumber[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // Paints the options menu
    public void paintOptions(Graphics2D g2d) {

        int startX = (int) (boardXstart + (boxSize / 9.f));
        int startY = (int) (boardYstart + 5.f*(boxSize/9.f) / 2.f);

        int size = (int) ((boxSize/9.f) * 7.f);

        int width = 3*(size/7);
        int height = size/7;

        g2d.setColor(modeColorPrime);
        // CH g2d.setColor(Color.white);
        g2d.fillRect(startX,startY,size,size);
        g2d.setStroke(new BasicStroke(optionsMenuBoxThickness));
        g2d.setColor(modeColorSecond);
        // CH g2d.setColor(Color.black);
        g2d.drawRect(startX,startY,size,size);

        // Options Text
        String optionsText = "Options";
        g2d.setFont(new Font("Arial",Font.BOLD,(int)optionsMenuFontSize));
//        g2d.drawString(optionsText,startX+(size/5),startY+100);
        g2d.drawString(optionsText,startX+(size/5),startY+(int)(screenSizeX * (100.f/800.f)));

        // Exit Button
        g2d.setColor(modeColorPrime);
        // CH g2d.setColor(Color.white);
//        g2d.fillRect(startX+15,startY+15,exitButtonSizeX,exitButtonSizeY);
        g2d.fillRect(startX+(int)(screenSizeX * (15.f/800.f)),startY+(int)(screenSizeX * (15.f/800.f)),(int)exitButtonSizeX,(int)exitButtonSizeY);
        g2d.setColor(modeColorSecond);
        // CH g2d.setColor(Color.black);
        g2d.drawRect(startX+(int)(screenSizeX * (15.f/800.f)),startY+(int)(screenSizeX * (15.f/800.f)),(int)exitButtonSizeX,(int)exitButtonSizeY);
        g2d.setFont(new Font("Arial",Font.BOLD,(int)exitStringFontSize));
//        g2d.drawString("X",startX+23,startY+58);
        g2d.drawString("X",startX+(int)(screenSizeX * (23.f/800.f)),startY+(int)(screenSizeX * (58.f/800.f)));


        // Background
        g2d.setFont(new Font("Arial",Font.ITALIC,(int)exitStringFontSize));
//        g2d.drawString("Background:",startX+18,startY+195);
        g2d.drawString("Background:",startX+(int)(screenSizeX * (18.f/800.f)),startY+(int)(screenSizeX * (195.f/800.f)));
        g2d.setStroke(new BasicStroke(afterGameBoxThickness));

        // Light
        g2d.setFont(new Font("Arial",Font.PLAIN,(int)lightStringFontSize));
        g2d.setColor(modeColorPrime);
        // CH g2d.setColor(Color.white);
//        g2d.fillRect(startX+30,startY+220,width,height);
        g2d.fillRect(startX+(int)(screenSizeX * (30.f/800.f)),startY+(int)(screenSizeX * (220.f/800.f)),width,height);
        g2d.setColor(modeColorSecond);
        // CH g2d.setColor(Color.black);
//        g2d.drawRect(startX+30,startY+220,width,height);
        g2d.drawRect(startX+(int)(screenSizeX * (30.f/800.f)),startY+(int)(screenSizeX * (220.f/800.f)),width,height);
//        g2d.drawString("Light",startX+30+(width/4),startY+270);
        g2d.drawString("Light",startX+(int)(screenSizeX * (30.f/800.f))+(width/4),startY+(int)(screenSizeX * (270.f/800.f)));

        // Dark
        g2d.setColor(modeColorSecond);
        // CH g2d.setColor(Color.black);
//        g2d.fillRect(startX+size-30-width,startY+220,width,height);
        g2d.fillRect(startX+size-(int)(screenSizeX * (30.f/800.f))-width,startY+(int)(screenSizeX * (220.f/800.f)),width,height);
        g2d.setColor(modeColorPrime);
        // CH g2d.setColor(Color.white);
//        g2d.drawRect(startX+size-30-width,startY+220,width,height);
        g2d.drawRect(startX+size-(int)(screenSizeX * (30.f/800.f))-width,startY+(int)(screenSizeX * (220.f/800.f)),width,height);
//        g2d.drawString("Dark",startX+size-30-width+(width/4),startY+270);
        g2d.drawString("Dark",startX+size-(int)(screenSizeX * (30.f/800.f))-width+(width/4),startY+(int)(screenSizeX * (270.f/800.f)));




        // Load New Puzzles
        g2d.setColor(modeColorSecond);
        // CH g2d.setColor(Color.black);
        g2d.setFont(new Font("Arial",Font.ITALIC,(int)loadNewPuzzlesStringFontSize));
//        g2d.drawString("Load New Puzzles? : ",startX+18,startY+370);
        g2d.drawString("Load New Puzzles? : ",startX+(int)(screenSizeX * (18.f/800.f)),startY+(int)(screenSizeX * (370.f/800.f)));

        // Yes
        g2d.setFont(new Font("Arial",Font.PLAIN,(int)lightStringFontSize));
        g2d.setColor(modeColorPrime);
        // CH g2d.setColor(Color.white);
//        g2d.fillRect(startX+30,startY+390,width,height);
        g2d.fillRect(startX+(int)(screenSizeX * (30.f/800.f)),startY+(int)(screenSizeX * (390.f/800.f)),width,height);
        g2d.setColor(modeColorSecond);
        // CH g2d.setColor(Color.black);
//        g2d.drawRect(startX+30,startY+390,width,height);
        g2d.drawRect(startX+(int)(screenSizeX * (30.f/800.f)),startY+(int)(screenSizeX * (390.f/800.f)),width,height);
//        g2d.drawString("Yes",startX+30+(width/3),startY+440);
        g2d.drawString("Yes",startX+(int)(screenSizeX * (30.f/800.f))+(width/3),startY+(int)(screenSizeX * (440.f/800.f)));


        // No
        g2d.setColor(modeColorSecond);
        // CH g2d.setColor(Color.black);
//        g2d.fillRect(startX+size-30-width,startY+390,width,height);
        g2d.fillRect(startX+size-(int)(screenSizeX * (30.f/800.f))-width,startY+(int)(screenSizeX * (390.f/800.f)),width,height);
        g2d.setColor(modeColorPrime);
        // CH g2d.setColor(Color.white);
//        g2d.drawRect(startX+size-30-width,startY+390,width,height);
        g2d.drawRect(startX+size-(int)(screenSizeX * (30.f/800.f))-width,startY+(int)(screenSizeX * (390.f/800.f)),width,height);
//        g2d.drawString("No",startX+size-30-width+(width/3),startY+440);
        g2d.drawString("No",startX+size-(int)(screenSizeX * (30.f/800.f))-width+(width/3),startY+(int)(screenSizeX * (440.f/800.f)));

    }

    // Returns the option menu button selected; if none selected, return -1
    public int getOptionNum(int xPoint, int yPoint) {
        int startX = (int) (boardXstart + (boxSize / 9.f));
        int startY = (int) (boardYstart + 5.f*(boxSize/9.f) / 2.f);

        int size = (int) ((boxSize/9.f) * 7.f);

        int width = 3*(size/7);
        int height = size/7;

        int exitSize = (int)exitButtonSizeX;

        // 1 - Light; 2 - Dark; 3 - Yes; 4 - No; 5 - Exit

        // Options 1 and 2
        boolean b = xPoint > startX + (int)(screenSizeX * (30.f/800.f)) && xPoint < (startX + (int)(screenSizeX * (30.f/800.f)) + width);
        boolean c = xPoint > (startX+size-(int)(screenSizeX * (30.f/800.f))-width) && xPoint < (startX+size-(int)(screenSizeX * (30.f/800.f))-width+width);

        if (yPoint > (startY+(int)(screenSizeX * (220.f/800.f))) && yPoint < (startY+(int)(screenSizeX * (220.f/800.f))+height)) {
            // Option 1 - Light
            if (b) {
                return 1;
            }
            // Option 2 - Dark
            else if (c) {
                return 2;
            }
        }
        // Options 3 and 4
        else if (yPoint > (startY+(int)(screenSizeX * (390.f/800.f))) && yPoint < (startY+(int)(screenSizeX * (390.f/800.f))+height)) {
            // Option 3 - Yes
            if (b) {
                return 3;
            }
            // Option 4 - Dark
            else if (c) {
                return 4;
            }
        }
        // Option 5 - Quit
        else if (yPoint > startY+(int)(screenSizeX * (15.f/800.f)) && yPoint < (startY+(int)(screenSizeX * (15.f/800.f))+exitSize)) {
            if (xPoint > (startX+(int)(screenSizeX * (15.f/800.f))) && xPoint < (startX+(int)(screenSizeX * (15.f/800.f))+exitSize)) {
                return 5;
            }
        }

        return -1;
    }

    // Highlights one of the buttons in the options menu, depending on which button was pressed on
    public void highlightOption(Graphics2D g2d, int numOpt, Color color) {
        int startX = (int) (boardXstart + (boxSize / 9.f));
        int startY = (int) (boardYstart + 5.f*(boxSize/9.f) / 2.f);

        int size = (int) ((boxSize/9.f) * 7.f);

        int width = 3*(size/7);
        int height = size/7;


        g2d.setStroke(new BasicStroke(numbersOptionCircleLineSize));

        // Light
        if (numOpt == 1) {
            g2d.setFont(new Font("Arial", Font.PLAIN, (int)lightStringFontSize));
            g2d.setColor(color);
            g2d.fillRect(startX + (int)(screenSizeX * (30.f/800.f)), startY + (int)(screenSizeX * (220.f/800.f)), width, height);
            g2d.setColor(Color.black);
            g2d.drawRect(startX + (int)((screenSizeX * (30.f/800.f))), startY + (int)(screenSizeX * (220.f/800.f)), width, height);
            g2d.drawString("Light", startX + (int)(screenSizeX * (30.f/800.f)) + (width / 4), startY + (int)(screenSizeX * (270.f/800.f)));
        }
        // Dark
        else if (numOpt == 2) {
            g2d.setColor(color);
            g2d.fillRect(startX + size - (int)(screenSizeX * (30.f/800.f)) - width, startY + (int)(screenSizeX * (220.f/800.f)), width, height);
            g2d.setColor(Color.white);
            g2d.drawRect(startX + size - (int)(screenSizeX * (30.f/800.f)) - width, startY + (int)(screenSizeX * (220.f/800.f)), width, height);
            g2d.drawString("Dark", startX + size - (int)(screenSizeX * (30.f/800.f)) - width + (width / 4), startY + (int)(screenSizeX * (270.f/800.f)));
        }
        // Yes
        else if (numOpt == 3) {
            g2d.setFont(new Font("Arial", Font.PLAIN, (int)lightStringFontSize));
            g2d.setColor(color);
            g2d.fillRect(startX + (int)(screenSizeX * (30.f/800.f)), startY + (int)(screenSizeX * (390.f/800.f)), width, height);
            g2d.setColor(Color.black);
            g2d.drawRect(startX + (int)(screenSizeX * (30.f/800.f)), startY + (int)(screenSizeX * (390.f/800.f)), width, height);
            g2d.drawString("Yes", startX + (int)(screenSizeX * (30.f/800.f)) + (width / 3), startY + (int)(screenSizeX * (440.f/800.f)));
        }
        // No
        else if (numOpt == 4) {
            g2d.setColor(color);
            g2d.fillRect(startX + size - (int)(screenSizeX * (30.f/800.f)) - width, startY + (int)(screenSizeX * (390.f/800.f)), width, height);
            g2d.setColor(Color.white);
            g2d.drawRect(startX + size - (int)(screenSizeX * (30.f/800.f)) - width, startY + (int)(screenSizeX * (390.f/800.f)), width, height);
            g2d.drawString("No", startX + size - (int)(screenSizeX * (30.f/800.f)) - width + (width / 3), startY + (int)(screenSizeX * (440.f/800.f)));
        }
        // Exit Button
        else if (numOpt == 5) {
            g2d.setStroke(new BasicStroke(exitButtonThickness));
            g2d.setColor(color);
            g2d.fillRect(startX+(int)(screenSizeX * (15.f/800.f)),startY+(int)(screenSizeX * (15.f/800.f)),(int)exitButtonSizeX,(int)exitButtonSizeY);
            g2d.setColor(Color.black);
            g2d.drawRect(startX+(int)(screenSizeX * (15.f/800.f)),startY+(int)(screenSizeX * (15.f/800.f)),(int)exitButtonSizeX,(int)exitButtonSizeY);
            g2d.setFont(new Font("Arial",Font.BOLD,(int)exitStringFontSize));
            g2d.drawString("X",startX+(int)(screenSizeX * (23.f/800.f)),startY+(int)(screenSizeX * (58.f/800.f)));
        }
    }

//    public void loadEasyMediumHard(int amount, int capEasy, int capMedium, int capHard, boolean wantAllRowsFilled
//            , int sqsPerRow) {
//
//        Board[] easy;
//        Board[] medium;
//        Board[] hard;
//
//        easy = loadBoard(amount, capEasy, wantAllRowsFilled,sqsPerRow);
//        medium = loadBoard(amount, capMedium, wantAllRowsFilled,sqsPerRow);
//        hard = loadBoard(amount, capHard, wantAllRowsFilled,sqsPerRow);
//
//
//        easyMediumHardBoards[0] = easy;
//        easyMediumHardBoards[1] = medium;
//        easyMediumHardBoards[2] = hard;
//    }

}
