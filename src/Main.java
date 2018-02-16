import javax.swing.*;

public class Main extends JFrame{

    public Main(){
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);

        SquareOneBonitoDimas squareOneBonitoDimas = new SquareOneBonitoDimas();
        add(squareOneBonitoDimas);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
