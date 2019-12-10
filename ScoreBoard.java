package game;
import javax.swing.*;
import java.awt.*;
public class ScoreBoard extends JComponent {
private static String champion;
private String lastWinner;
//private String player1;
public ScoreBoard() {
super();
setPreferredSize(new Dimension(500 ,200));
champion = "N/A";
lastWinner = "N/A";
//this.player1=player1;

}
public void paintComponent(Graphics g) {
Graphics g2D = (Graphics2D) g;
g2D.setColor(Color.YELLOW);
g2D.fillRect(0, 0, 1000, 120);
g2D.setColor(Color.BLACK);
Font stringFont = new Font( "Courier", Font.BOLD, 24);
g2D.setFont(stringFont);
g2D.drawString("Champion", 10, 30);
g2D.drawString(champion, 250, 30);
g2D.drawString("Last Winner", 10, 70);
g2D.drawString(lastWinner, 250, 65);
g2D.setColor(Color.CYAN);
g2D.fillRect(0, 100, 1000, 120);
g2D.setColor(Color.BLACK);
Font StringFont = new Font( "Courier", Font.BOLD, 24);
g2D.setFont(StringFont);
g2D.drawString("Name", 10, 130);
g2D.drawString(Game.player1, 250, 130);
g2D.drawString(Game.player2, 400, 130);
g2D.drawString("Score", 10, 180);
g2D.drawString(String.valueOf(Game.p1Score), 250, 180);
g2D.drawString(String.valueOf(Game.p2Score), 400, 180);

}

public String getChampion() {
return champion;
}
public static void setChampion(String championn) {
champion = championn;
}
public String getLastWinner() {
return lastWinner;
}
public void setLastWinner(String lastWinner) {
this.lastWinner = lastWinner;
}
public void newGame() {
lastWinner = champion;
champion = "N/A";

}
}
