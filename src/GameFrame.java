import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    GamePanel thePanel;

    GameFrame() {

        thePanel = new GamePanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        String imagePath = "IconImage/sudokuIcon.png";
        setIconImage(Toolkit.getDefaultToolkit().getImage(imagePath));

        add(thePanel);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
