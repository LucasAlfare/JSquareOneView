import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Arrays;

public class SquareOneBonitoDimas extends JComponent {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();

        //g2d.rotate(Math.toRadians(-30), 300, 300);

        ArrayList<Path2D> pieces = new ArrayList<>(Arrays.asList(
                edge(300, 300),
                corner(300, 300),
                edge(300, 300),
                corner(300, 300),
                edge(300, 300),
                corner(300, 300),
                edge(300, 300),
                corner(300, 300)));
        //Collections.shuffle(pieces);

        boolean isEdge = isEdge(pieces.get(0));
        for (Path2D polygon : pieces){
            g2d.rotate(Math.toRadians(isEdge && isEdge(polygon) ? 30 : (!isEdge && !isEdge(polygon) ? 60 : 45)), 300, 300);
            isEdge = isEdge(polygon);

            g2d.setColor(Color.green);
            g2d.fill(polygon);
            g2d.setColor(Color.black);
            g2d.draw(polygon);
        }

        //g2d.setTransform(old);
        //things you draw after here will not be rotated
    }

    private Path2D corner(int x, int y){
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

    private Path2D edge(int x, int y){
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

    private boolean isEdge(Path2D path){
        int points = 0;
        for (PathIterator i = path.getPathIterator(null); !i.isDone(); i.next()){
            if (i.currentSegment(new double[6]) == PathIterator.SEG_LINETO){
                points++;
            }
        }

        return points + 1 == 3;
    }

//    private Polygon corner(int x, int y){
//        Polygon diamond = new Polygon();
//        diamond.npoints = 4;
//        diamond.xpoints = new int[]{x, x - (100 / 2), x - (100 / 2), new Double(x - ((100 * Math.tan(15)) / 2)).intValue()};
//        diamond.ypoints = new int[]{y, new Double(y - ((100 * Math.tan(15)) / 2)).intValue(), y - (100 / 2), y - (100 / 2)};
//
////        diamond.xpoints = new int[]{x, x - 15, x, x + 15};
////        diamond.ypoints = new int[]{y, y - 30, y - 45, y - 30};
//
//        return diamond;
//    }

//    private Polygon edge2(int x, int y){
//        Polygon triangle = new Polygon();
//        triangle.npoints = 3;
//        triangle.xpoints = new int[]{x, new Double(x - ((100 * 0.2679491924) / 2)).intValue(), new Double(x - ((100 * 0.2679491924) / 2)).intValue()};
//        triangle.ypoints = new int[]{y, y - (100 / 2), y - (100 / 2)};
//
//        return triangle;
//    }
}
