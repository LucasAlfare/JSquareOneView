import com.main.puzzle.SquareOne;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JFrame{

    public Main(){
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        SquareOneBonitoDimas squareOneBonitoDimas = new SquareOneBonitoDimas(new SquareOne("(0, -1)/(0, 3)/(-5, 1)/(3, 0)/(2, -1)/(1, -2)/(-4, 0)/(-3, -3)/(4, 0)/(6, -1)/(-3, -4)/(-4, -3)/"));

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 32){
                    squareOneBonitoDimas.getSquareOne().twist();
                }

                if (e.getKeyCode() == KeyEvent.VK_F){
                    squareOneBonitoDimas.getSquareOne().move(true, -1);
                }

                if (e.getKeyCode() == KeyEvent.VK_J){
                    squareOneBonitoDimas.getSquareOne().move(true, 1);
                }
            }
        });

        add(squareOneBonitoDimas);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
