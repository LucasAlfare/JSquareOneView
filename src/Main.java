import com.main.puzzle.SquareOne;

import javax.swing.*;

public class Main extends JFrame{

    public Main(){
        setSize(190, 370);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JSquareOne jSquareOne = new JSquareOne(new SquareOne("(-5, 0)/(3, 3)/(-3, 3)/(-1, -4)/(-3, 0)/(-2, 0)/(6, -3)/(1, 0)/(5, -2)/(-2, 0)/(6, -2)/(-5, 0)/"));
        add(jSquareOne);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
