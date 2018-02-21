import com.main.puzzle.Piece;
import com.main.puzzle.SquareOne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Arrays;

public class JSquareOne extends JComponent {

    private static final double L = 100;

    private static final int TOP_ANCHOR_X = 85;
    private static final int TOP_ANCHOR_Y = 85;
    private static final int BOTTOM_ANCHOR_X = 85;
    private static final int BOTTOM_ANCHOR_Y = TOP_ANCHOR_X + 160;

    private SquareOne squareOne;
    ArrayList<Path2D> pieces;

    private int tEdge, tCorner, bEdge, bCorner;

    public JSquareOne(SquareOne squareOne) {
        this.squareOne = squareOne;

        pieces = new ArrayList<>(Arrays.asList(meio(100, 100, 0), canto(100, 100, 45)));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                ArrayList<Path2D[]> top = piecesToDraw(true, TOP_ANCHOR_X, TOP_ANCHOR_Y);
                for (int i = 0; i < top.size(); i++) {
                    for (int j = 0; j < top.get(i).length; j++) {
                        if (top.get(i)[j].contains(e.getPoint())) {
                            if (isMeio(top.get(i)[0])) {
                                tEdge = i;
                                break;
                            } else {
                                tCorner = i;
                                break;
                            }
                        }
                    }
                }

                ArrayList<Path2D[]> bottom = piecesToDraw(false, BOTTOM_ANCHOR_X, BOTTOM_ANCHOR_Y);
                for (int i = 0; i < bottom.size(); i++) {
                    for (int j = 0; j < bottom.get(i).length; j++) {
                        if (bottom.get(i)[j].contains(e.getPoint())) {
                            if (isMeio(bottom.get(i)[0])) {
                                bEdge = i;
                                break;
                            } else {
                                bCorner = i;
                                break;
                            }
                        }
                    }
                }

                System.out.println("tEdge: " + tEdge + ", tCorner: " + tCorner);
                System.out.println("bEdge: " + bEdge + ", bCorner: " + bCorner);
                System.out.println();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        drawFace(graphics2D, true, TOP_ANCHOR_X, TOP_ANCHOR_Y);
        drawFace(graphics2D, false, BOTTOM_ANCHOR_X, BOTTOM_ANCHOR_Y);
    }

    public void drawFace(Graphics2D graphics2D, boolean face, double x, double y) {
        ArrayList<Path2D[]> pieces = piecesToDraw(face, x, y);

        for (int i = 0; i < pieces.size(); i++) {
            for (int j = 0; j < pieces.get(i).length; j++) {
                graphics2D.setColor(getColorByChar(getSquareOne().getPieces(face).get(i).getColors()[j]));
                graphics2D.fill(pieces.get(i)[j]);
                graphics2D.setColor(Color.black);
                graphics2D.draw(pieces.get(i)[j]);
            }
        }

        repaint();
    }

    private ArrayList<Path2D[]> piecesToDraw(boolean face, double x, double y) {
        ArrayList<Path2D[]> pieces = new ArrayList<>();

        boolean prevIsMeio = getSquareOne().getPieces(face).get(0).getColors().length == 2;
        double currAngle = prevIsMeio ? -30 : -45;
        for (Piece p : getSquareOne().getPieces(face)) {
            currAngle += prevIsMeio && p.getColors().length == 2 ? 30 : (!prevIsMeio && p.getColors().length != 2 ? 60 : 45);
            pieces.add(p.getColors().length == 2 ? meioFull(x, y, currAngle) : cantoFull(x, y, currAngle));
            prevIsMeio = p.getColors().length == 2;
        }

        return pieces;
    }

    private Path2D[] cantoFull(double x, double y, double angle) {
        return new Path2D[]{
                canto(x, y, angle),
                lateralCantoA(x, y, angle),
                lateralCantoB(x, y, angle)
        };
    }

    private Path2D[] meioFull(double x, double y, double angle) {
        return new Path2D[]{
                meio(x, y, angle),
                lateralMeio(x, y, angle)
        };
    }

    private Path2D meio(double x, double y, double angle) {
        ArrayList<double[]> points = new ArrayList<>(
                Arrays.asList(
                        new double[]{x, y},
                        new double[]{x - (L * 0.1339745962), y - (L / 2)},
                        new double[]{x + (L * 0.1339745962), y - (L / 2)}
                )
        );

        points = rotatedPoints(points, new double[]{x, y}, angle);

        Path2D path = new Path2D.Double();
        path.moveTo(points.get(0)[0], points.get(0)[1]);

        for (int i = 1; i < points.size(); ++i) {
            path.lineTo(points.get(i)[0], points.get(i)[1]);
        }
        path.closePath();

        return path;
    }

    private Path2D canto(double x, double y, double angle) {
        ArrayList<double[]> points = new ArrayList<>(
                Arrays.asList(
                        new double[]{x, y},
                        new double[]{x - (L * 0.2588190451), y - (L * 0.4482877361)},
                        new double[]{x, y - (L * 0.7071067812)},
                        new double[]{x + (L * 0.2588190451), y - (L * 0.4482877361)}));

        points = rotatedPoints(points, new double[]{x, y}, angle);

        Path2D path = new Path2D.Double();
        path.moveTo(points.get(0)[0], points.get(0)[1]);

        for (int i = 1; i < points.size(); ++i) {
            path.lineTo(points.get(i)[0], points.get(i)[1]);
        }
        path.closePath();

        return path;
    }

    private Path2D lateralMeio(double x, double y, double angle) {
        ArrayList<double[]> points = new ArrayList<>(
                Arrays.asList(
                        new double[]{x - ((L * Math.tan(Math.toRadians(15))) / 2), y - (L / 2)},
                        new double[]{x + ((L * Math.tan(Math.toRadians(15))) / 2), y - (L / 2)},
                        new double[]{x + ((L * Math.tan(Math.toRadians(15))) / 2), (y - (L / 2)) - (L * 0.07D)},
                        new double[]{x - ((L * Math.tan(Math.toRadians(15))) / 2), (y - (L / 2)) - (L * 0.07D)}));

        points = rotatedPoints(points, new double[]{x, y}, angle);

        Path2D path = new Path2D.Double();
        path.moveTo(points.get(0)[0], points.get(0)[1]);

        for (int i = 1; i < points.size(); ++i) {
            path.lineTo(points.get(i)[0], points.get(i)[1]);
        }
        path.closePath();

        return path;
    }

    private Path2D lateralCantoA(double x, double y, double angle) {
        ArrayList<double[]> points = new ArrayList<>(
                Arrays.asList(
                        new double[]{
                                x - ((L - (L * Math.tan(Math.toRadians(15)))) / Math.sqrt(8)),
                                y - ((L * Math.sqrt(2)) / 2) + ((L - (L * Math.tan(Math.toRadians(15)))) / Math.sqrt(8))},
                        new double[]{x, y - ((L * Math.sqrt(2)) / 2)},
                        new double[]{x, (y - ((L * Math.sqrt(2)) / 2)) - (L * 0.1D)},
                        new double[]{
                                x - ((L - (L * Math.tan(Math.toRadians(15)))) / Math.sqrt(8)),
                                (y - ((L * Math.sqrt(2)) / 2) + ((L - (L * Math.tan(Math.toRadians(15)))) / Math.sqrt(8))) - (L * 0.1D)}
                )
        );

        points = rotatedPoints(points, new double[]{x, y}, angle);

        Path2D path = new Path2D.Double();
        path.moveTo(points.get(0)[0], points.get(0)[1]);

        for (int i = 1; i < points.size(); ++i) {
            path.lineTo(points.get(i)[0], points.get(i)[1]);
        }
        path.closePath();

        return path;
    }

    private Path2D lateralCantoB(double x, double y, double angle) {
        ArrayList<double[]> points = new ArrayList<>(
                Arrays.asList(
                        new double[]{x, y - ((L * Math.sqrt(2)) / 2)},
                        new double[]{
                                x + ((L - (L * Math.tan(Math.toRadians(15)))) / Math.sqrt(8)),
                                y - ((L * Math.sqrt(2)) / 2) + ((L - (L * Math.tan(Math.toRadians(15)))) / Math.sqrt(8))},
                        new double[]{
                                x + ((L - (L * Math.tan(Math.toRadians(15)))) / Math.sqrt(8)),
                                (y - ((L * Math.sqrt(2)) / 2) + ((L - (L * Math.tan(Math.toRadians(15)))) / Math.sqrt(8))) - (L * 0.1D)},
                        new double[]{x, (y - ((L * Math.sqrt(2)) / 2)) - (L * 0.1)}
                )
        );

        points = rotatedPoints(points, new double[]{x, y}, angle);

        Path2D path = new Path2D.Double();
        path.moveTo(points.get(0)[0], points.get(0)[1]);

        for (int i = 1; i < points.size(); ++i) {
            path.lineTo(points.get(i)[0], points.get(i)[1]);
        }
        path.closePath();

        return path;
    }

    private Color getColorByChar(char sigla) {
        switch (sigla) {
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

    private static ArrayList<double[]> rotatedPoints(ArrayList<double[]> points, double angle) {
        return rotatedPoints(points, new double[]{100, 100}, angle);
    }

    private static ArrayList<double[]> rotatedPoints(ArrayList<double[]> points, double[] anchorPoint, double angle) {
        ArrayList<double[]> r = new ArrayList<>();

        for (double[] point : points) {
            r.add(rotatedPoint(point, anchorPoint, angle));
        }

        return r;
    }

    private static double[] rotatedPoint(double[] point, double[] anchorPoint, double angle) {
        double x1 = point[0] - anchorPoint[0];
        double y1 = point[1] - anchorPoint[1];

        double x2 = x1 * Math.cos(Math.toRadians(angle)) - y1 * Math.sin(Math.toRadians(angle));
        double y2 = x1 * Math.sin(Math.toRadians(angle)) + y1 * Math.cos(Math.toRadians(angle));

        return new double[]{x2 + anchorPoint[0], y2 + anchorPoint[1]};
    }

    private boolean isMeio(Path2D path) {
        int numVertices = 0;
        for (PathIterator i = path.getPathIterator(null); !i.isDone(); i.next()) {
            if (i.currentSegment(new double[6]) == PathIterator.SEG_LINETO) {
                numVertices++;
            }
        }

        return numVertices + 1 == 3;
    }

    public SquareOne getSquareOne() {
        return squareOne;
    }

    public void setSquareOne(SquareOne squareOne) {
        this.squareOne = squareOne;
    }
}
