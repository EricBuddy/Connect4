package game;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.Scanner;
import java.io.*;

public class Game extends JFrame implements ActionListener{
// Colors
private static final int NUM_COLUMNS = 7;
// Columns
private static final int NUM_ROWS = 6;
//Rows
private static final Color colorEmpty = Color.WHITE;
private static final Color colorPlayer1 = Color.RED;
private static final Color colorPlayer2 = Color.YELLOW;
private static int currentPlayer; // either 1 or 2
// Buttons
static String player1;
static String player2;
 static int p1Score = 0;
 static int p2Score = 0;
private JButton[] buttons = new JButton[NUM_COLUMNS+1];
private static JCheckBox[] checkBoxes = new JCheckBox[1];
static String player;
//JCheckBox logGame = new JCheckBox("Log Game");
//logGame.addActionListener(this);

// Board
private static JLabel[][] gameBoard = new JLabel[NUM_ROWS][NUM_COLUMNS];
// ScoreBoard
private ScoreBoard scoreBoard;
public static PrintWriter pw = null;

public Game() {

try {
player1 = JOptionPane.showInputDialog("Enter a name for player 1:");
player2 = JOptionPane.showInputDialog("Enter a name for player 2:");
FileOutputStream log = new FileOutputStream("GameLog.txt");
pw = new PrintWriter(log);
pw.print("");
} catch(Exception e) {
e.printStackTrace();
}
currentPlayer = 1;

for(int i = 0; i < NUM_COLUMNS; i++) {
buttons[i] = new JButton("" + i);
buttons[i].addActionListener(this);
}
buttons[NUM_COLUMNS] = new JButton("NEW GAME");
buttons[NUM_COLUMNS].addActionListener(this);

checkBoxes[0] = new JCheckBox("Log Game");
checkBoxes[0].addActionListener(this);

//JCheckBox logGame = new JCheckBox("Log Game");
//logGame.addActionListener(this);

scoreBoard = new ScoreBoard();
for(int row = 0; row < NUM_ROWS; row++) {
for(int col = 0; col < NUM_COLUMNS; col++) {
gameBoard[row][col] = new JLabel("" + row + col);
gameBoard[row][col].setOpaque(true);
gameBoard[row][col].setBackground(colorEmpty);
gameBoard[row][col].setPreferredSize(new Dimension(70, 20));
gameBoard[row][col].setHorizontalAlignment(JLabel.CENTER);
}
}
setLayout(new GridBagLayout());
GridBagConstraints constraints;
for(int i = 0; i <= NUM_COLUMNS; i++) {
constraints = new GridBagConstraints();
constraints.gridx = i;
constraints.gridy = 0;
constraints.insets = new Insets(3, 3, 3, 3);
add(buttons[i], constraints);
}
constraints = new GridBagConstraints();
constraints.gridx = NUM_COLUMNS;
constraints.gridy = 1;
constraints.insets = new Insets(3, 3, 3, 3);
add(checkBoxes[0], constraints);
checkBoxes[0].setBorderPainted(true);
checkBoxes[0].setSelected(true);
for(int row = 0; row < NUM_ROWS; row++) {
for(int col = 0; col < NUM_COLUMNS; col++) {

constraints = new GridBagConstraints();
constraints.gridx = col;
constraints.gridy = row + 1;
constraints.insets = new Insets(3, 3, 3, 3);
add(gameBoard[row][col], constraints);
}
}
constraints = new GridBagConstraints();
constraints.gridx = 0;
constraints.gridy = 8;
constraints.gridwidth = NUM_COLUMNS;
constraints.fill = GridBagConstraints.HORIZONTAL;
constraints.insets = new Insets(3, 3, 3, 3);
add(scoreBoard, constraints);
}
private void addPiece(int col) {
// find out the empty row index of the column
for(int row = NUM_ROWS-1; row >= 0; row--) {
// check if the slot on this row is empty
if(gameBoard[row][col].getBackground() == colorEmpty) {
if(currentPlayer == 1) {
gameBoard[row][col].setBackground(colorPlayer1);

if (checkBoxes[0].isSelected()) {
pw.println(player1 + " selects row " + row + " : Column "+col);
pw.println("-----------------------");
pw.flush();
}

currentPlayer = 2;
break;
}
else{
gameBoard[row][col].setBackground(colorPlayer2);
if (checkBoxes[0].isSelected()) {
pw.println(player2+" selects row " + row + " : Column "+col);
pw.println("-----------------------");
pw.flush();
}
currentPlayer = 1;
break;
}
}
}
}
public static int getCurrentPlayer() {
return currentPlayer;

}
@Override
public void actionPerformed(ActionEvent e) {
// find out which button was clicked
JButton sourceButton = (JButton) e.getSource();

if (sourceButton.getText() == "NEW GAME") {
scoreBoard.setLastWinner(scoreBoard.getChampion());
ScoreBoard.setChampion("N/A");
repaint();
if (checkBoxes[0].isSelected()) {
pw.println("-----------------------");
pw.println("A NEW GAME HAS BEEN STARTED!");
pw.flush();
}

for(int row = 0; row < NUM_ROWS; row++) {
for(int col = 0; col < NUM_COLUMNS; col++) {
gameBoard[row][col].setBackground(colorEmpty);
}
}
} else {
for(int i = 0; i < NUM_COLUMNS; i++) {
if(sourceButton == buttons[i] && !checkWin(gameBoard, scoreBoard)) {
addPiece(i);
}
}
if (CheckTie() == true) {
ScoreBoard.setChampion("TIE");
if (checkBoxes[0].isSelected()) {
pw.println("-----------------------");
pw.println("THERE HAS BEEN A TIE!");
pw.flush();
}
repaint();
}
}
// use repaint to update the board
if(checkWin(gameBoard, scoreBoard)) {
repaint();
}
}

//Check for tie
public static boolean CheckTie() {
boolean tie = true;
for(int row = 0; row < NUM_ROWS; row++) {
for(int col = 0; col < NUM_COLUMNS; col++) {
if (gameBoard[row][col].getBackground()== colorEmpty) {
tie = false;
}
}
}
return tie;
}
public static boolean checkWin(JLabel[][] board, ScoreBoard score) {

final int HEIGHT = board.length;
final int WIDTH = board[0].length;
boolean win = false;

for(int i = 0; i < HEIGHT; i++) {
for(int j = 0; j < WIDTH; j++) {
Color currColor = board[i][j].getBackground();
if(currColor.equals(colorEmpty)) {
continue;
}
if(j + 3 < WIDTH &&
currColor.equals(board[i][j + 1].getBackground()) &&
currColor.equals(board[i][j + 2].getBackground()) &&
currColor.equals(board[i][j + 3].getBackground())) {//Right
win = true;
if (currentPlayer == 1) {
player =player2;
p2Score=1+p2Score;
} else {
	player =player1;
	p1Score=1+p1Score;
}
ScoreBoard.setChampion( player);
if (checkBoxes[0].isSelected()) {
pw.println("-----------------------");
pw.println(player + " Has won!");
pw.flush();
}

return win;
}
if(i - 3 >= 0 &&
currColor.equals(board[i - 1][j].getBackground()) &&
currColor.equals(board[i - 2][j].getBackground()) &&
currColor.equals(board[i - 3][j].getBackground())) {// Up
win = true;
if (currentPlayer == 1) {
player =player2;
p2Score=1+p2Score;
} else {
	player =player1;
	p1Score=1+p1Score;
}
ScoreBoard.setChampion( player);
if (checkBoxes[0].isSelected()) {
pw.println("-----------------------");
pw.println(player + " Has won!");
pw.flush();
}
return win;
}
if(j + 3 < WIDTH && i - 3 >= 0 &&
currColor.equals(board[i - 1][j + 1].getBackground()) &&
currColor.equals(board[i - 2][j + 2].getBackground()) &&
currColor.equals(board[i - 3][j + 3].getBackground())) {//  Right d
win = true;
if (currentPlayer == 1) {
player =player2;
p2Score=1+p2Score;
} else {
	player =player1;
	p1Score=1+p1Score;
}
ScoreBoard.setChampion( player);
if (checkBoxes[0].isSelected()) {
pw.println("-----------------------");
pw.println(player + " Has won!");
pw.flush();
}
return win;
}
if(j - 3 >= 0 && i - 3 >=0 &&
currColor.equals(board[i - 1][j - 1].getBackground()) &&
currColor.equals(board[i - 2][j - 2].getBackground()) &&
currColor.equals(board[i - 3][j - 3].getBackground())) {// Left d
win = true;
if (currentPlayer == 1) {
player =player2;
p2Score=1+p2Score;
} else {
	player =player1;
	p1Score=1+p1Score;
}
ScoreBoard.setChampion( player);
if (checkBoxes[0].isSelected()) {
pw.println("-----------------------");

pw.println(player + " Has won!");
pw.flush();
}
return win;
}

// if(currColor.equals(colorPlayer1))
}
}
return win; // No winner found;
}
public static void main(String[] args) {
Game window = new Game();
window.setTitle("Connect Four");
window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
window.setResizable(false);
window.pack();
window.setVisible(true);



}
}
