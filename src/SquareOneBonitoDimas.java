import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ESSA CLASSE É BOA MESMO, AGRADICMENTOS AO MEU PRIMOR EUDES QUE
 * ME AJUDOU A CALCULAR OS POLÍGONOS PQ EU NÃO ENTENDO NADA DISSO.
 */
public class SquareOneBonitoDimas extends JComponent {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        //AffineTransform old = g2d.getTransform();

        //g2d.rotate(Math.toRadians(-30), 300, 300);

        ArrayList<Path2D> pieces = new ArrayList<>(Arrays.asList(
                meio(300, 300),
                canto(300, 300),
                meio(300, 300),
                canto(300, 300),
                meio(300, 300),
                canto(300, 300),
                meio(300, 300),
                canto(300, 300)));
        //Collections.shuffle(pieces);

        boolean isEdge = isMeio(pieces.get(0));
        for (Path2D polygon : pieces){
            g2d.rotate(Math.toRadians(isEdge && isMeio(polygon) ? 30 : (!isEdge && !isMeio(polygon) ? 60 : 45)), 300, 300);
            isEdge = isMeio(polygon);

            g2d.setColor(Color.green);
            g2d.fill(polygon);
            g2d.setColor(Color.black);
            g2d.draw(polygon);
        }

        //g2d.setTransform(old);//restaura a rotação original
        //normal...
    }

    private Path2D canto(int x, int y){
        Double valoresX[] = {
                x * 1.0,
                x - ((100 - (100 * 0.2679491924)) / Math.sqrt(8)),
                x * 1.0,
                x + ((100 - (100 * 0.2679491924)) / Math.sqrt(8)),
        };

        Double valoresY[] = {
                y * 1.0,
                y - ((100 * Math.sqrt(2)) / 2) + ((100 - (100 * 0.2679491924)) / Math.sqrt(8)),
                y - ((100 * Math.sqrt(2)) / 2),
                y - ((100 * Math.sqrt(2)) / 2) + ((100 - (100 * 0.2679491924)) / Math.sqrt(8)),
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
                x * 1.0,
                x - ((100 * 0.2679491924) / 2),
                x + ((100 * 0.2679491924) / 2)
        };

        Double valoresY[] = {
                y * 1.0,
                y - (100 / 2) * 1.0,
                y - (100 / 2) * 1.0
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
        int points = 0;
        for (PathIterator i = path.getPathIterator(null); !i.isDone(); i.next()){
            if (i.currentSegment(new double[6]) == PathIterator.SEG_LINETO){
                points++;
            }
        }

        return points + 1 == 3;
    }
}
