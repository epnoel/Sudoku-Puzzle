import java.util.ArrayList;
import java.util.Random;

public class Board {

    private int size = 9;
    private final Square[][] board = new Square[size][size];
    private final ArrayList<Integer> numberList = new ArrayList<>();
    private int[][] row1Possibilities;
    private int[][] row2Possibilities;
    private int[][] row3Possibilities;
    private int[][] row4Possibilities;
    private int[][] row5Possibilities;
    private int[][] row6Possibilities;
    private int[][] row7Possibilities;
    private int[][] row8Possibilities;
    private int[][] row9Possibilities;

    private final ArrayList<String> strings = new ArrayList<>();
    public boolean areSame = false;

    private final Random random = new Random();


    // Debugs
    public int testRow;
    public int testI;
    public int testJ;


    Board() {
        setArrayList();
        createBoard();
    }

    // Sets the values of numberList to 0-9 (this is for a 9 x 9 board)
    public void setArrayList() {
//        numberList = new ArrayList<>();
        numberList.clear();
        for (int i = 1; i < (size+1); i++) {
            numberList.add(i);
        }
    }

    public void clearBoard()
    {
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                board[i][j] = new Square();
            }
        }
    }

    // Creates a new board that fits the rules of sudoku (each row has unique digits, each column has unique digits,
    // each box has unique digits).
    // The squares on the board have reveals set to false
    public void createBoard() {

//        board = new Square[size][size];
        clearBoard();

        int numberR;
        int pos;
        int theBox = 1;
        int theBox2;
        boolean notValid = false;
        boolean justStrongReset = false;

        int numRepetitionsInner = 0;
        int numRepetitionsOuter = 0;

        for (int i = 0; i < size; i++) {

            // If i is a multiple of 3 and i is not 0 and strong reset is false
            // then add 3 to the current value of the Box

            if (i < 3) {
                theBox = 1;
            }
            else if (i < 6) {
                theBox = 4;
            }
            else if (i < 9) {
                theBox = 7;
            }

            if (justStrongReset) {
                justStrongReset = false;
            }


            // Always set Box2 to the Box
            theBox2 = theBox;
            setArrayList();

            System.out.print(i + ": ");


            for (int j = 0; j < size; j++) {

                if (j % 3 == 0 && j != 0) {
                    theBox2++;
                }


                // Generate random numbers for a cell in a row
                while (true) {

                    numberR = random.nextInt(9)+1; // used to randomly select an element in the Permutations array


                    if (findIndex(numberR,numberList) == -1) {
                        continue;
                    }
                    else {
                        pos = numberR;
                    }


                    // Goes from current row to up
                    for (int k = i - 1; k > -1; k--) {
                        if (pos == board[k][j].getValue()) {
                            notValid = true;
                            break;
                        }
                        else if (valueAlreadyInBox(pos,theBox2)) {
                            notValid = true;
                            break;
                        }
                        else {

                            notValid = false;
                        }
                    }

                    if (!notValid) {
                        int ind = findIndex(numberR,numberList);
                        numberList.remove(ind);

                        break;
                    }
                    else {
                        numRepetitionsInner++;

                    }

                    if (numRepetitionsInner == 10) {
                        break;
                    }
                }


                if (numRepetitionsInner != 10) {
//                    board[i][j] = new Square(pos, i, j, theBox2);
                    board[i][j].setValue(pos);
                    board[i][j].setRow(i);
                    board[i][j].setCol(j);
                    board[i][j].setBox(theBox2);

                }
                else {
                    clearRow(i);
                    j = -1;
                    setArrayList();
                    numRepetitionsOuter++;
                    theBox2 = theBox;

                    System.out.println();
                }

                numRepetitionsInner = 0;

                if (numRepetitionsOuter == 10) {

                    clearRow(i);
                    if (i != 0) {
                        clearRow(i - 1);
                        i = i - 2;
                    }
                    justStrongReset = true;
                    numRepetitionsOuter = 0;

                    // Debug
                    //System.out.println("STRONG RESET");
                    break;
                }

                if (j != -1) {
                    if (j != (size - 1)) {
                        System.out.print(board[i][j].getValue() + " | ");
                    } else {
                        System.out.println(board[i][j].getValue());
                    }
                }
            }

            if (i != (size - 1)) {
                System.out.println("----------------------------------");
            }

            // Debug
            // System.out.println("theBox after = " + theBox);

        }
    }

    // Returns the position of where the value is in the arraylist
    public int findIndex(int value, ArrayList<Integer> list) {
        int length = list.size();

        for (int i = 0; i < length; i++) {
            if (list.get(i) == value) {
                return i;
            }
        }

        return -1;
    }

    // Reveals whether or not the value is already in the box
    // True if value is already in the box
    // Only applies to boards that are in the process of being created
    public boolean valueAlreadyInBox(int value, int boxNumber) {

        int min;

        // Min corresponds with the last digit of the square's position in the row (aka column)
        if (boxNumber < 4) {
            min = (boxNumber - 1) * 3;
        }
        else if (boxNumber < 7) {
            min = (boxNumber + 6) * 3;
        }
        else {
            min = (boxNumber + 13) * 3;
        }

        int indexI = min / 10; // Represents the first digit (aka the row)
        int indexJ = min % 10; // Represents the last digit (aka the column)

        // Box should be 3 x 3
        for (int i = indexI; i < (indexI + 3); i++) {

            for (int j = indexJ; j < (indexJ + 3); j++) {

                // If the value is already in the box, then return false
                if (board[i][j] != null) {
                    if (board[i][j].getBox() == boxNumber && board[i][j].getValue() != 0) {
                        if (value == board[i][j].getValue()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;

    }

    // Reveals whether or not the value is already in the box
    // Only applies to unsolved boards
    public boolean valueAlreadyInBoxUnrevealed(Square[][] unrevealed, int value, int boxNumber) {

        int min;

        if (boxNumber < 4) {
            min = (boxNumber - 1) * 3;
        }
        else if (boxNumber < 7) {
            min = (boxNumber + 6) * 3;
        }
        else {
            min = (boxNumber + 13) * 3;
        }

        int indexI = min / 10;
        int indexJ = min % 10;



        for (int i = indexI; i < (indexI + 3); i++) {

            for (int j = indexJ; j < (indexJ + 3); j++) {

                if (unrevealed[i][j].getBox() == boxNumber && unrevealed[i][j].getValue() != 0) {
                    if (value == unrevealed[i][j].getValue()) {
                        testI = i;
                        testJ = j;
                        return true;
                    }
                }
            }
        }

        return false;

    }

    // Reveals whether or not the value is already in the column
    // Returns true if it is already in the column
    // Only applies to unsolved boards
    public boolean valueAlreadyInColUnRevealed(Square[][] unrevealed, int value, int col) {
        for (int i = 0; i < size; i++) {
            if (unrevealed[i][col].getValue() == value) {
                testRow = i;
                return true;
            }
        }

        return false;
    }

    // Sets reveal of individual squares of a board to true
    public void revealIndividualSquare(int row, int col) {
        board[row][col].setRevealed(true);
    }

    // Sets reveal of individual square of a board to false
    public void unrevealIndividualSquare(int row, int col) {
        board[row][col].setRevealed(false);
    }

    // Reveals a user-inputted number of squares from the board randomly
    public void revealMultipleSquaresRandom(int numSquares, boolean wantAllRowsFilled, int squaresPerRow) {
        int count = 1;
        int rowR;
        int colR;
        int[] atLeastOnce = new int[size];
        int[] atLeastTwiceBox = new int[size];

        boolean continueThis = false;

        if (numSquares < 10) {
            numSquares = 10;
        }

        // In the case where the number of Squares desired to be revealed is less
        // than the size of the board multiplied by the desired squares per row
        if (numSquares < (size * squaresPerRow)) {
            squaresPerRow = 1;
        }

        int altCount = 0;

        while (count <= numSquares) {
            rowR = random.nextInt(9);
            colR = random.nextInt(9);
            int calculatedBox;
            calculatedBox = calculateBox9x9(rowR,colR);

            if (!board[rowR][colR].getRevealed()) {

                if (wantAllRowsFilled) {
                    // Make sure that each row has at least one value
                    for (int i = 0; i < size; i++) {

                        // You can change the least amount of revealed values in each row
                        if (atLeastOnce[i] < squaresPerRow) {
                            continueThis = rowR != i;
                            break;
                        } else {
                            continueThis = false;
                        }
                    }
                }

                System.out.println("altCount = " + altCount);

                if (altCount >= (squaresPerRow*size)) {
                    for (int j = 0; j < size; j++) {
                        if (atLeastTwiceBox[j] < 2) {
                            System.out.println("rowR = " + rowR + "; colR = " + colR);
                            System.out.println("calculate9x9 = " + calculateBox9x9(rowR,colR));

                            continueThis = calculateBox9x9(rowR, colR) != (j + 1);

                            break;
                        }
                        else {
                            continueThis = false;
                        }
                    }
                }

                System.out.println("continueThis = " + continueThis);

                if (!continueThis) {
                    atLeastOnce[rowR]++;
                    atLeastTwiceBox[calculatedBox - 1]++;
                    revealIndividualSquare(rowR, colR);
                    count++;
                    altCount++;
                }
            }
        }

    }

    // New method
    // Creates a sudoku board with randomly revealed squares by first setting all the squares' revealed to true,
    // then randomly unrevealing a square one by one (aka working backwards)
    public void revealSquaresNew(int numSquares, boolean wantAllRowsFilled, int squaresPerRow) {

        Square[][] thisSquare = new Square[0][];

        revealAllSquares();
        int theOtherCount = 0;
        int resetCount = 0;

        int randR;
        int randC;

        int[] amountEachRow = new int[9];

        for (int a = 0; a < 9; a++) {
            amountEachRow[a] = 9;
        }

        // Work backwards
        while (theOtherCount < (81-numSquares)) {
            randR = random.nextInt(9);
            randC = random.nextInt(9);


            // if the square is already unrevealed, then continue and generate another random values
            if (!board[randR][randC].getRevealed()) {
                continue;
            }
            else if (wantAllRowsFilled && amountEachRow[randR] <= squaresPerRow) {
                continue;
            }


            // Unreveal the individual square
            unrevealIndividualSquare(randR,randC);

            // Debug
            // System.out.println("hasMoreThanOneSolution = " + hasMoreThanOneSolution(false,thisSquare));

            // If the board has only one solution still, then increment theOtherCount by 1
            if (!hasMoreThanOneSolution(false, thisSquare)) {
                theOtherCount++;
                amountEachRow[randR]--;

            }
            // else reveal back the square
            else {
                revealIndividualSquare(randR,randC);
                resetCount++;

                // Debug
                // System.out.println("Does not have a unique solution!");
            }


            if (resetCount >= 35) {
                theOtherCount = 0;
                resetCount = 0;
                revealAllSquares();

                for (int a = 0; a < 9; a++) {
                    amountEachRow[a] = 9;
                }


            }

        }
    }

    // Set the size of the board
    public void setBoardSize(int size) {
        this.size = size;
    }

    // Print the board; if a square is revealed, then print the value; if not, print blank
    public void printBoard() {
        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {
                if (j != (size - 1)) {
                    if (!board[i][j].getRevealed()) {
                        System.out.print(" " + " | ");
                    }
                    else {
                        System.out.print(board[i][j].getValue() + " | ");
                    }
                }
                else {
                    if (board[i][j].getRevealed()) {
                        System.out.println(board[i][j].getValue());
                    }
                    else {
                        System.out.println();
                    }
                }
            }

            if (i != (size - 1)) {
                System.out.println("-- --- --- --- --- --- --- --- ---");
            }
        }
    }

    // Prints the revealed board
    public void printRevealedBoard() {
        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {
                if (j != (size - 1)) {
                    System.out.print(board[i][j].getValue() + " | ");
                }
                else {
                    System.out.println(board[i][j].getValue());
                }
            }

            if (i != (size - 1)) {
                System.out.println("-- --- --- --- --- --- --- --- ---");
            }
        }
    }

    // Clears the squares of a row
    // Creates new squares for a row
    public void clearRow(int row) {
        for (int i = 0; i < size; i++) {
//            board[row][i] = new Square();
            board[row][i].resetSquare();
        }
    }

    // Sorts the possibilities in order
    // 1234
    // 1243
    // 1324
    public void sortPossibilities(int[][] possibilities, int row) {
        int[] temp;
        int width = possibilities[0].length;
        int[][] hiddenCells = returnHiddenCells();
        int[] theCells = new int[size];
        if (row >= 0) {
            theCells =hiddenCells[row];
        }

        for (int i = 0; i < (possibilities.length - 1); i++) {
            for (int j = 0; j < (possibilities.length - i - 1); j++) {
                int tens = (int) Math.pow(10,width - 1);
                int current = 0;
                int after = 0;
                int count = 0;

                while (tens != 0) {
                    current = tens * possibilities[j][count] + current;
                    after = tens * possibilities[j+1][count] + after;
                    tens = tens / 10;
                    count++;
                }

                if (row != -1 && theCells == possibilities[j]) {
                    possibilities[j] = new int[]{0, 0, 0};
                }

                if (current > after) {
                    temp = possibilities[j];
                    possibilities[j] = new int[width];
                    possibilities[j] = possibilities[j + 1];
                    possibilities[j + 1] = new int[width];
                    possibilities[j + 1] = temp;

                }
            }
        }
    }

    // Returns a 2D-int array with all the row possibilities of the missing numbers
    public int[][] getRowPossibilities(ArrayList<Integer> numList) {

        // Debug
        //System.out.println("RAN 1");

        int theSize = numList.size();
        int rowSize = factorials(9-theSize);
        int [][] rowPoss = new int[rowSize][9 - theSize];
        int[] missNums;


        missNums = setMissingNumbers(numList);

        int[][] combination;
        combination = differentCombinations(9 - theSize);


        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < 9 - theSize; j++) {
                rowPoss[i][j] = missNums[combination[i][j] - 1];
            }
        }

        return rowPoss;

    }

    // Generates all the different combinations based on the random combination configuration
    // Ex- for n = 4, or 1234
    // Generates all the combinations of 1234 (in this case, should be size of 24)
    public int[][] differentCombinations(int num) {
        int[][] numCombs = new int[factorials(num)][num];
        int tens = (int) Math.pow(10,num-1);

        int totNumber = 0;
        int digit = 1;


        while (tens != 0) {
            totNumber = totNumber + digit * tens;
            tens = tens / 10;
            digit++;
        }

        String stringNumber = String.valueOf(totNumber);
        permutation(stringNumber);

        // Debug
//        System.out.println("string size = " + strings.size());

        for (int i = 0; i < strings.size(); i++) {

            // Debug
            //System.out.println("Through + " + i);

            for (int j = 0; j < num; j++) {
                numCombs[i][j] = (strings.get(i).charAt(j) - 48);
                // Debug
                //System.out.println("numCombs[i][j] = " + numCombs[i][j]);

                // Debug
                //System.out.println("j = " + j);
            }

        }


        return numCombs;

    }

    // Generates a random combination based on the number given
    // Ex- if 4, then generates an individual combination from 1,2,3,4 like 1324 or 1423
    public int[] generateRandomCombination(int num) {
        int[] comb = new int[num];
        int count = 0;
        int numberR;
        boolean isDifferent = true;

        while (count != num) {
            numberR = random.nextInt(num) + 1;

            for (int i = 0; i < count; i++) {

                // if the random number is equal to what's already on
                if (comb[i] == numberR) {
                    isDifferent = false;
                    break;
                }
                else {
                    isDifferent = true;
                }
            }

            if (isDifferent) {
                comb[count] = numberR;
                count++;
            }

        }


        return comb;
    }

    // Sets the numbers missing from the row
    // I.e. if numList is 1,4,5,9, then missing numbers should be {2,3,6,7,8}
    public int[] setMissingNumbers(ArrayList<Integer> numList) {
        int[] missingNums = new int[9 - numList.size()];
        boolean isThere = false;
        int count = 0;

//        System.out.println("GOT 1");

        for (int i = 1; i < 10; i++) {
            for (Integer integer : numList) {
                if (integer == i) {
                    isThere = true;
                    break;
                } else {
                    isThere = false;
                }
            }
            if (!isThere) {
                missingNums[count] = i;
                count++;
            }

        }

        return missingNums;
    }

    // Returns factorial of a number
    public int factorials(int num) {
        if (num == 1) {
            return 1;
        }
        else if (num == 0) {
            return 1;
        }
        else {
             return num * factorials(num-1);
        }
    }

    // Removes array list elements, setting the size of the array list to 0
    public void removeArrayListElements(ArrayList<Integer> theList) {

        int theSize = theList.size();
        for (int i = 0; i < theSize; i++) {
            theList.remove(0);
        }

    }

    // Sets the row possibilities of an incomplete board
    public void setRowPossibilities(Square[][] incompleteBoard) {

        ArrayList<Integer> revealedSquares = new ArrayList<>();

        for (int i = 0; i < size; i++) {


            removeArrayListElements(revealedSquares);

            // Adds all the values that aren't in the row into the array
            // Example- if a row has 4, 1, 3, 7, then the array would most likely be 2,5,6,8,9
            // (order doesn't necessarily matter)
            for (int j = 0; j < size; j++) {
                if (incompleteBoard[i][j].getValue() != 0) {
                    revealedSquares.add(incompleteBoard[i][j].getValue());
                }
                //System.out.println("THAT - j = " + j);
            }

            if (i == 0) {
                row1Possibilities = getRowPossibilities(revealedSquares);
            }
            else if (i == 1) {
                row2Possibilities = getRowPossibilities(revealedSquares);
            }
            else if (i == 2) {
                row3Possibilities = getRowPossibilities(revealedSquares);
            }
            else if (i == 3) {
                row4Possibilities = getRowPossibilities(revealedSquares);
            }
            else if (i == 4) {
                row5Possibilities = getRowPossibilities(revealedSquares);
            }
            else if (i == 5) {
                row6Possibilities = getRowPossibilities(revealedSquares);
            }
            else if (i == 6) {
                row7Possibilities = getRowPossibilities(revealedSquares);
            }
            else if (i == 7) {
                row8Possibilities = getRowPossibilities(revealedSquares);
            }
            else if (i == 8) {
                row9Possibilities = getRowPossibilities(revealedSquares);
            }
        }
    }

    public int[][] setRowPossibilities(int row) {
        if (row == 0) {
            return row1Possibilities;
        } else if (row == 1) {
            return row2Possibilities;
        } else if (row == 2) {
            return row3Possibilities;
        } else if (row == 3) {
            return row4Possibilities;
        } else if (row == 4) {
            return row5Possibilities;
        } else if (row == 5) {
            return row6Possibilities;
        } else if (row == 6) {
            return row7Possibilities;
        } else if (row == 7) {
            return row8Possibilities;
        } else if (row == 8) {
            return row9Possibilities;
        }
        else {
            return null;
        }
    }

    // Returns the number of revealed squares in a row
    public int getNumRevealedInRow(Square[][] theSquares, int row) {
        int count = 0;

        for (int i = 0; i < size; i++) {
            if (theSquares[row][i].getValue() != 0) {
                count++;
            }
        }

        return count;
    }

    // Returns true if a sudoku board can have more than one solution (based on what is given);
    // Returns false otherwise
    public boolean hasMoreThanOneSolution(boolean testing,Square[][] testBoard) {

        Square[][] unfinishedBoard;

        if (testing) {
            unfinishedBoard = testBoard;
        }
        else {
            unfinishedBoard = boardWithOnlyRevealedSquares();
        }

        setRowPossibilities(unfinishedBoard);

        Square[][] copy = new Square[size][size];
        for (int b = 0; b < size; b++) {
            copyRow(unfinishedBoard[b],copy[b]);
        }


        int countJ;
        int countRows;


        int[] leftOffPosition = new int[size];
        for (int k = 0; k < size; k++) {
            leftOffPosition[k] = 0;
        }

        for (int i = 0; i < size; i++) {
            countJ = 0;
            int[][] onePossibility = setRowPossibilities(i);
            countRows = leftOffPosition[i];
            int revealed = getNumRevealedInRow(copy,i);

            copyRow(copy[i],unfinishedBoard[i]);

            // Debug
            //System.out.println("Row: " + i + "; revealed = " + revealed + "; leftOff = " + leftOffPosition[i]);

            if (revealed == 9) {
//                System.out.println("CAUGHT + " + i);
                break;
            }

            // Debug
//            if (i == 0 && !release) {
//                for (int c = 0; c < test.length; c++) {
//                    if (onePossibility[countRows][c] == test[c]) {
//                        release = true;
//                        System.out.println("is true");
//                    }
//                    else {
//                        release = false;
//                        break;
//                    }
//                }
//            }

            // Debug
            if (i == 0 && countRows == 1456) {
//                System.out.println("HERE");

                System.out.println();
            }


            for (int j = 0; j < size; j++) {

                if (board[i][j].getValue() == 0) {
                    return true;
                }

                if (unfinishedBoard[i][j].getValue() == 0) {


                    // As long as left off position is not greater than its factorial value,
                    // and the value is not already in the box or in the column
                    // And the value is not equal to 0
                    // And revealed is greater than 0 (aka, the row has at least one empty square)
                    if (leftOffPosition[i] < factorials(9 - revealed) && unfinishedBoard[i][j].getBox() != 0 && !valueAlreadyInBoxUnrevealed(unfinishedBoard, onePossibility[countRows][countJ],
                            unfinishedBoard[i][j].getBox()) && !valueAlreadyInColUnRevealed(unfinishedBoard,
                            onePossibility[countRows][countJ], j) && onePossibility[countRows][countJ] != 0) {

                        unfinishedBoard[i][j].setValue(onePossibility[countRows][countJ]);
                        countJ++;


                        if ( (i == size - 1)  && areTheSame(unfinishedBoard)) {
                            if ((leftOffPosition[i] + 1) >= factorials(9-revealed)) {
                                leftOffPosition[i] = 0;
                                leftOffPosition[i-1]++;
                                copyRow(copy[i],unfinishedBoard[i]);
                                i = i - 2;
                            }
                            else {
                                leftOffPosition[i]++;
                                copyRow(copy[i],unfinishedBoard[i]);
                            }

                            // Debug
                            //System.out.println("SUMMONED");

                            break;
                        }
                    }
                    // else if this isn't the case
                    else {
                        leftOffPosition[i]++;
                        copyRow(copy[i],unfinishedBoard[i]);


                        if (leftOffPosition[i] >= (factorials(9-revealed))) {


                            if (i == 0) {
                                return false;
                            }
                            else {
                                leftOffPosition[i] = 0;
                                leftOffPosition[i-1] = leftOffPosition[i-1] + 1;
                                i = i - 2;
                            }
                        }
                        else {

                            i = i - 1;
                        }

                        break;
                    }
                }
            }

        }

//        System.out.println("Should print");
        printBoard(unfinishedBoard);
//        System.out.println("PRINTED");

        if (areTheSame(unfinishedBoard)) {
            areSame = true;
            return false;
        }
        else {
            areSame = false;
        }


        return true;
    }

    // Creates and returns a board that only has values of revealed squares
    public Square[][] boardWithOnlyRevealedSquares() {
        Square[][] fakeBoard = new Square[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                fakeBoard[i][j] = new Square();
                if (board[i][j].getRevealed()) {
                    fakeBoard[i][j].setValue(board[i][j].getValue());
                    fakeBoard[i][j].setRevealed(true);
                }
                else {
                    fakeBoard[i][j].setValue(0);
                    fakeBoard[i][j].setRevealed(false);
                }

                fakeBoard[i][j].setRow(board[i][j].getRow());
                fakeBoard[i][j].setCol(board[i][j].getCol());
                fakeBoard[i][j].setBox(board[i][j].getBox());
            }
        }

        return fakeBoard;
    }

    // Prints board, based on the square array that is given
    public void printBoard(Square[][] someSquare) {
        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {
                if (j != (size - 1)) {
                    System.out.print(someSquare[i][j].getValue() + " | ");
                }
                else {
                    System.out.println(someSquare[i][j].getValue());
                }
            }

            if (i != (size - 1)) {
                System.out.println("-- --- --- --- --- --- --- --- ---");
            }
        }
    }

    // Copies one board's row to another board's row
    public void copyRow(Square[] rowOriginal, Square[] row2) {
        // Debug
        //System.out.print("row2[i].getRevealed = ");

        for (int i = 0; i < size; i++) {
            row2[i] = new Square();
            row2[i].setRow(rowOriginal[i].getRow());
            row2[i].setCol(rowOriginal[i].getCol());
            row2[i].setBox(rowOriginal[i].getBox());
            row2[i].setRevealed(rowOriginal[i].getRevealed());
            row2[i].setValue(rowOriginal[i].getValue());

        }

    }

    // Returns an 2d-array of int type with all the values of hidden squares
    public int[][] returnHiddenCells() {
        int[][] hiddenCells = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!board[i][j].getRevealed()) {
                    hiddenCells[i][j] = board[i][j].getValue();
                }
                else {
                    hiddenCells[i][j] = 0;
                }
            }
        }

        return hiddenCells;
    }

    public void permutation(String str) {
        strings.clear();
        permutation("", str);
    }

    private void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0) {
            //System.out.println(prefix);
            strings.add(prefix);
        }
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
        }
    }

    // Returns true if the board given has the same values as the game board;
    // Returns false otherwise
    public boolean areTheSame(Square[][] fakeBoard) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].getValue() != fakeBoard[i][j].getValue()) {
                    return false;
                }
            }
        }

        return true;
    }

    public int findRowDifference(Square[][] squareBase, Square[][] square2) {
        for (int i = squareBase.length - 1; i >= 0; i--) {
            for (int j = 0; j < squareBase[0].length; j++) {
                if (squareBase[i][j].getValue() != square2[i][j].getValue()) {
                    return i;
                }
            }
        }

        return -1;
    }

    public boolean getAreSame() {
        return areSame;
    }

    // Converts a 2D int array to a 2D square array (and sets all the squares to true if not equal to 0)
    public Square[][] convert2DIntArrayToSquare2D(int[][] array) {

        Square[][] theSquare = new Square[array.length][array[0].length];

        for (int i = 0; i < theSquare.length; i++) {
            for (int j = 0; j < theSquare[0].length; j++) {
                theSquare[i][j] = new Square();
                if (array[i][j] == 0) {
                    theSquare[i][j].setRevealed(false);
                    theSquare[i][j].setValue(0);
                }
                else {
                    theSquare[i][j].setRevealed(true);
                    theSquare[i][j].setValue(array[i][j]);
                }

                theSquare[i][j].setCol(j);
                theSquare[i][j].setRow(i);
                theSquare[i][j].setBox(calculateBox9x9(i,j));
            }
        }

        return theSquare;
    }

    public int convertPositionToBox(int row, int col) {
        if (row > 5) {
            if (col > 5) {
                return 9;
            }
            else if (col > 2) {
                return 8;
            }
            else {
                return 7;
            }
        }
        else if (row > 2) {
            if (col > 5) {
                return 6;
            }
            else if (col > 2) {
                return 5;
            }
            else {
                return 4;
            }
        }
        else if (row > -1){
            if (col > 5) {
                return 3;
            }
            else if (col > 2) {
                return 2;
            }
            else {
                return 1;
            }
        }

        return -1;
    }

    // Sets the game board based on the square array that is given
    public void setBoard(Square[][] someSquares) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j].setBox(someSquares[i][j].getBox());
                board[i][j].setRow(someSquares[i][j].getRow());
                board[i][j].setCol(someSquares[i][j].getCol());
                board[i][j].setValue(someSquares[i][j].getValue());
                board[i][j].setRevealed(someSquares[i][j].getRevealed());
            }
        }
    }

    public void hideAllCells() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j].setRevealed(false);
            }
        }
    }

    public Square[][] getBoard() {
        return board;
    }

    public int getSize() {
        return size;
    }

    // Reveals all the squares on the Board
    public void revealAllSquares() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                revealIndividualSquare(i,j);
            }
        }
    }

    // Unreveals all the squares on the Board
    public void unrevealAllSquares() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                board[i][j].setRevealed(false);
            }
        }
    }

    // n revealed squares - 1
    public boolean canHaveJustOneSolution() {

        Square[][] randomSquare = new Square[2][2];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {


                if (!board[i][j].getRevealed()) {
                    board[i][j].setRevealed(true);


                    if (!hasMoreThanOneSolution(false, randomSquare)) {
                        return true;
                    }
                    else {
                        // Debug
                        System.out.println("Printed Board: ");
                        printBoard();

                        board[i][j].setRevealed(false);
                    }
                }

            }
        }

        return false;
    }

    // Converts a square's position into a box number
    // Only applies to 9x9 boards
    public int calculateBox9x9(int row, int col) {

        if (row < 3) {

            if (col < 3) {
                return 1;
            }
            else if (col < 6) {
                return 2;
            }
            else if (col < 9) {
                return 3;
            }

        }
        else if (row < 6) {

            if (col < 3) {
                return 4;
            }
            else if (col < 6) {
                return 5;
            }
            else if (col < 9) {
                return 6;
            }

        }
        else if (row < 9) {

            if (col < 3) {
                return 7;
            }
            else if (col < 6) {
                return 8;
            }
            else if (col < 9) {
                return 9;
            }

        }

        return -1;
    }

}
