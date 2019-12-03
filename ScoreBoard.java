package game;
import javax.swing.*;
import java.awt.*;
public class ScoreBoard extends JComponent {
private static String champ;
private String lastWinner;
//private String Name;
//private String Name2;
public ScoreBoard() {
super();
setPreferredSize(new Dimension(500 ,200));
champ = "N/A";
lastWinner = "N/A";

}
@Override
public void paintComponent(Graphics g) {
Graphics g2D = (Graphics2D) g;
g2D.setColor(Color.YELLOW);
g2D.fillRect(0, 0, 1000, 120);
g2D.setColor(Color.BLACK);
Font stringFont = new Font( "Courier", Font.BOLD, 24);
g2D.setFont(stringFont);
g2D.drawString("Champion", 10, 30);
g2D.drawString(champ, 250, 30);
g2D.drawString("Last Winner", 10, 70);
g2D.drawString(lastWinner, 250, 65);
g2D.setColor(Color.CYAN);
g2D.fillRect(0, 100, 1000, 120);
g2D.setColor(Color.BLACK);
Font StringFont = new Font( "Courier", Font.BOLD, 24);
g2D.setFont(StringFont);
g2D.drawString("Name", 10, 130);
g2D.drawString(champ, 250, 30);
g2D.drawString("Score", 10, 180);
g2D.drawString(lastWinner, 250, 65);


}

public String getChampion() {
return champ;
}
public static void setChampion(String championn) {
champ = championn;
}
public String getLastWinner() {
return lastWinner;
}
public void setLastWinner(String lastWinner) {
this.lastWinner = lastWinner;
}
public void newGame() {
lastWinner = champ;
champ = "N/A";

}
}