import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * ESSA CLASSE É BOA MESMO, AGRADICMENTOS AO MEU PRIMOR EUDES QUE
 * ME AJUDOU A CALCULAR OS POLÍGONOS PQ EU NÃO ENTENDO NADA DISSO.
 */
public class SquareOneBonitoDimas extends JComponent {

    private static final double L = 60;
    private static final double TAN15 = Math.tan(Math.toRadians(15));

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        //AffineTransform old = g2d.getTransform();

        g2d.rotate(Math.toRadians(-30), 300, 300);

        ArrayList<Path2D> pieces = new ArrayList<>(Arrays.asList(
                meio(300, 300),
                canto(300, 300),
                meio(300, 300),
                canto(300, 300),
                meio(300, 300),
                canto(300, 300),
                meio(300, 300),
                canto(300, 300)));
        Collections.shuffle(pieces);

        boolean previousIsMeio = isMeio(pieces.get(0));
        for (Path2D currPeca : pieces){
            g2d.rotate(
                    Math.toRadians(
                    previousIsMeio && isMeio(currPeca) ? 30 : (!previousIsMeio && !isMeio(currPeca) ? 60 : 45)),
                    300,
                    300);
            previousIsMeio = isMeio(currPeca);

            g2d.setColor(Color.green);
            g2d.fill(currPeca);
            g2d.setColor(Color.black);
            g2d.draw(currPeca);
        }

        //g2d.setTransform(old);//restaura a rotação original
        //normal...
    }

    private Path2D canto(int x, int y){
        Double valoresX[] = {
                (double) x,
                x - ((L - (L * TAN15)) / Math.sqrt(8)),
                (double) x,
                x + ((L - (L * TAN15)) / Math.sqrt(8))
        };

        Double valoresY[] = {
                (double) y,
                y - ((L * Math.sqrt(2)) / 2) + ((L - (L * TAN15)) / Math.sqrt(8)),
                y - ((L * Math.sqrt(2)) / 2),
                y - ((L * Math.sqrt(2)) / 2) + ((L - (L * TAN15)) / Math.sqrt(8))
        };

        Path2D path = new Path2D.Double();
        path.moveTo(valoresX[0], valoresY[0]);

        for(int i = 1; i < valoresX.length; ++i) {
            path.lineTo(valoresX[i], valoresY[i]);
        }
        path.closePath();

        return path;
    }

    private Path2D meio(int x, int y){
        Double valoresX[] = {
                (double) x,
                x - ((L * TAN15) / 2),
                x + ((L * TAN15) / 2)
        };

        Double valoresY[] = {
                (double) y,
                y - (L / 2),
                y - (L / 2)
        };

        Path2D path = new Path2D.Double();
        path.moveTo(valoresX[0], valoresY[0]);

        for(int i = 1; i < valoresX.length; ++i) {
            path.lineTo(valoresX[i], valoresY[i]);
        }
        path.closePath();

        return path;
    }

    private boolean isMeio(Path2D path){
        int numVertices = 0;
        for (PathIterator i = path.getPathIterator(null); !i.isDone(); i.next()){
            if (i.currentSegment(new double[6]) == PathIterator.SEG_LINETO){
                numVertices++;
            }
        }

        return numVertices + 1 == 3;
    }
}
