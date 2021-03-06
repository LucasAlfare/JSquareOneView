import com.main.puzzle.Piece;
import com.main.puzzle.SquareOne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;

/**
 * ESSA CLASSE É BOA MESMO, AGRADICMENTOS AO MEU PRIMOR EUDES QUE
 * ME AJUDOU A CALCULAR OS POLÍGONOS PQ EU NÃO ENTENDO NADA DISSO.
 */
@Deprecated
public class SquareOneBonitoDimas extends JComponent {

    private static double L = 100;
    private static final double TAN15 = Math.tan(Math.toRadians(15));

    private static final int TOP_ANCHOR_X = 85;
    private static final int TOP_ANCHOR_Y = 85;
    private static final int BOTTOM_ANCHOR_X = 85;
    private static final int BOTTOM_ANCHOR_Y = TOP_ANCHOR_X + 150;

    private SquareOne squareOne;
    private ArrayList<Path2D[]> draws;
    private ArrayList<Path2D[]> currentTopDrawed, currentBottomDrawed;

    public SquareOneBonitoDimas(SquareOne squareOne) {
        this.squareOne = squareOne;
        draws = new ArrayList<>();
        currentTopDrawed = new ArrayList<>();
        currentBottomDrawed = new ArrayList<>();

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();

        //g2d.rotate(Math.toRadians(-75), TOP_ANCHOR_X, TOP_ANCHOR_Y);
        g2d.rotate(Math.toRadians(-30), TOP_ANCHOR_X, TOP_ANCHOR_Y);
        drawFace(g2d, TOP_ANCHOR_X, TOP_ANCHOR_Y, true);
        g2d.setTransform(old);//restaura a rotação original

        g2d.rotate(Math.toRadians(-30), BOTTOM_ANCHOR_X, BOTTOM_ANCHOR_Y);
        drawFace(g2d, BOTTOM_ANCHOR_X, BOTTOM_ANCHOR_Y, false);
        g2d.setTransform(old);//restaura a rotação original
        //normal...
    }

    private void drawFace(Graphics2D g2d, int anchorX, int anchorY, boolean face){
        setupDraws(face, anchorX, anchorY);

        //AffineTransform old = g2d.getTransform();
        //g2d.rotate(Math.toRadians(face ? -45 : 0), anchorX, anchorY);

        boolean prevIsMeio = isMeio(draws.get(0)[0]);
        for (int i = 0; i < draws.size(); i++) {
            g2d.rotate(
                    Math.toRadians(
                            prevIsMeio && isMeio(draws.get(i)[0]) ? 30 : (!prevIsMeio && !isMeio(draws.get(i)[0]) ? 60 : 45)),
                    anchorX,
                    anchorY);
            prevIsMeio = isMeio(draws.get(i)[0]);

            for (int j = 0; j < squareOne.getPieces(face).get(i).getColors().length; j++) {
                g2d.setColor(getColorByChar(squareOne.getPieces(face).get(i).getColors()[j]));//i = 0: cor do topo da peça
                g2d.fill(draws.get(i)[j]);
                g2d.setColor(Color.black);
                g2d.draw(draws.get(i)[j]);
            }
        }
    }

    private void setupDraws(boolean face, int anchorX, int anchorY) {
        draws.clear();
        for (Piece x : this.getSquareOne().getPieces(face)) {
            if (x.getColors().length == 2) {
                draws.add(meioFull(anchorX, anchorY));
            } else {
                draws.add(cantoFull(anchorX, anchorY));
            }
        }

        repaint();
    }

    private Path2D[] cantoFull(int x, int y){
        return new Path2D[]{canto(x, y), lateralCantoA(x, y), lateralCantoB(x, y)};
    }

    private Path2D[] meioFull(int x, int y){
        return new Path2D[]{meio(x, y), lateralMeio(x, y)};
    }

    private Path2D canto(int x, int y){
        Double[] valoresX = {
                (double) x,
                x - (L * 0.2588190451),
                (double) x,
                x + (L * 0.2588190451)
        };

        Double[] valoresY = {
                (double) y,
                y - (L * 0.4482877361),
                y - (L * 0.7071067812),
                y - (L * 0.4482877361)
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
        Double[] valoresX = {
                (double) x,
                x - (L * 0.1339745962),
                x + (L * 0.1339745962)
        };

        Double[] valoresY = {
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

    private Path2D lateralMeio(int x, int y){
        Double valoresX[] = {
                x - ((L * TAN15) / 2),
                x + ((L * TAN15) / 2),
                x + ((L * TAN15) / 2),
                x - ((L * TAN15) / 2)
        };

        Double valoresY[] = {
                y - (L / 2),
                y - (L / 2),
                (y - (L / 2)) - (L * 0.07D),
                (y - (L / 2)) - (L * 0.07D)
        };

        Path2D path = new Path2D.Double();
        path.moveTo(valoresX[0], valoresY[0]);

        for(int i = 1; i < valoresX.length; ++i) {
            path.lineTo(valoresX[i], valoresY[i]);
        }
        path.closePath();

        return path;
    }

    private Path2D lateralCantoA(int x, int y){
        Double valoresX[] = {
                x - ((L - (L * TAN15)) / Math.sqrt(8)),
                (double) x,
                (double) x,
                x - ((L - (L * TAN15)) / Math.sqrt(8))
        };

        Double valoresY[] = {
                y - ((L * Math.sqrt(2)) / 2) + ((L - (L * TAN15)) / Math.sqrt(8)),
                y - ((L * Math.sqrt(2)) / 2),
                (y - ((L * Math.sqrt(2)) / 2)) - (L * 0.1D),
                (y - ((L * Math.sqrt(2)) / 2) + ((L - (L * TAN15)) / Math.sqrt(8))) - (L * 0.1D)
        };

        Path2D path = new Path2D.Double();
        path.moveTo(valoresX[0], valoresY[0]);

        for(int i = 1; i < valoresX.length; ++i) {
            path.lineTo(valoresX[i], valoresY[i]);
        }
        path.closePath();

        return path;
    }

    private Path2D lateralCantoB(int x, int y){
        Double valoresX[] = {
                (double) x,
                x + ((L - (L * TAN15)) / Math.sqrt(8)),
                x + ((L - (L * TAN15)) / Math.sqrt(8)),
                (double) x
        };

        Double valoresY[] = {
                y - ((L * Math.sqrt(2)) / 2),
                y - ((L * Math.sqrt(2)) / 2) + ((L - (L * TAN15)) / Math.sqrt(8)),
                (y - ((L * Math.sqrt(2)) / 2) + ((L - (L * TAN15)) / Math.sqrt(8))) - (L * 0.1D),
                (y - ((L * Math.sqrt(2)) / 2)) - (L * 0.1)
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

    private Color getColorByChar(char sigla){
        switch (sigla){
            case 'w':
                return Color.white;
            case 'y':
                return Color.yellow;
            case 'b':
                return Color.blue;
            case 'g':
                return Color.green;
            case 'r':
                return Color.red;
            case 'o':
                return Color.orange;
                default:
                    return Color.cyan;
        }
    }

    private int clickedPath(ArrayList<Path2D[]> pieces, Point clickLocation) {
        for (int i = 0; i < pieces.size(); i++) {
            for (Path2D piece : pieces.get(i)) {
                if (piece.contains(clickLocation)) {
                    System.out.println(getSquareOne().getPieces(true).get(i));
                    System.out.println(getSquareOne().getPieces(false).get(i));
                    return i;
                }
            }
        }

        return -1;
    }

    public static double getL() {
        return L;
    }

    public SquareOne getSquareOne() {
        return squareOne;
    }

    public void setSquareOne(SquareOne squareOne) {
        this.squareOne = squareOne;
    }
}
