package is.ru.flowfreeapp.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board extends View {

    private int NUM_CELLS = 5;
    private int m_cellWidth;
    private int m_cellHeight;

    private Rect m_rect = new Rect();
    private Paint m_paintGrid  = new Paint();
    private Paint m_paintPath  = new Paint();
    private Path m_path = new Path();
    private Rect pathRect = new Rect();
    private dotPath m_cellPath = null;

    private boolean[][] board = new boolean[NUM_CELLS][NUM_CELLS];

    public int totalMoves = 0;

    private int totalConnections = 0;

    private int xToCol(int x) {
        return (x - getPaddingLeft()) / m_cellWidth;
    }

    private int yToRow(int y) {
        return (y - getPaddingTop()) / m_cellHeight;
    }

    private int colToX(int col) {
        return col * m_cellWidth + getPaddingLeft();
    }

    private int rowToY(int row) {
        return row * m_cellHeight + getPaddingTop();
    }

    private ArrayList<dotPath> dotPaths = new ArrayList<dotPath>();

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_paintGrid.setStyle(Paint.Style.STROKE);
        m_paintGrid.setColor(Color.GRAY);

        m_paintPath.setStyle(Paint.Style.STROKE);
        m_paintPath.setColor(Color.GREEN);
        m_paintPath.setStrokeWidth(32);
        m_paintPath.setStrokeCap(Paint.Cap.ROUND);
        m_paintPath.setStrokeJoin(Paint.Join.ROUND);
        m_paintPath.setAntiAlias(true);

        Global global = Global.getInstance();

        ArrayList<Pack> packList = (ArrayList<Pack>) global.mPacks;

        Puzzle puzzle = packList.get(0).getPuzzles().get(0);

        int gridSize = Integer.parseInt(puzzle.getSize());
        NUM_CELLS = gridSize;
        String flows = puzzle.getFlows();
        String[] dotsForm = flows.split("\\,");
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();

        for(String dots : dotsForm) {
            String nolB = dots.replace("(", "");
            String norB = nolB.replace(")", "");
            String[] dot = norB.trim().split("\\s+");
            String store = null;
            for (int i = 0; i < dot.length; i++) {
                if(i%2 == 0)
                    store = dot[i];
                else
                    coordinates.add(new Coordinate(Integer.parseInt(store), Integer.parseInt(dot[i])));
            }

        }
        int j = 0, k = 1;
        Random rand = new Random();
        // TODO: check if size is odd
        for (int i = 0; i < coordinates.size()/2; i++) {
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);
            int randomColor = Color.rgb(r,g,b);
            dotPaths.add(new dotPath(coordinates.get(j), coordinates.get(k), randomColor));
            j+=2;
            k+=2;
        }

        /*dotPaths.add(new dotPath(new Coordinate(0, 0), new Coordinate(1, 4), Color.GREEN));
        dotPaths.add(new dotPath(new Coordinate(2, 0), new Coordinate(1, 3), Color.BLACK));
        dotPaths.add(new dotPath(new Coordinate(2, 1), new Coordinate(2, 4), Color.BLUE));
        dotPaths.add(new dotPath(new Coordinate(4, 0), new Coordinate(3, 3), Color.WHITE));
        dotPaths.add(new dotPath(new Coordinate(4, 1), new Coordinate(3, 4), Color.RED));*/

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width  = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int size = Math.min(width, height);
        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onSizeChanged( int xNew, int yNew, int xOld, int yOld ) {
        int sw = Math.max(1, (int) m_paintGrid.getStrokeWidth());
        m_cellWidth  = (xNew - getPaddingLeft() - getPaddingRight() - sw) / NUM_CELLS;
        m_cellHeight = (yNew - getPaddingTop() - getPaddingBottom() - sw) / NUM_CELLS;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for ( int r = 0; r < NUM_CELLS; ++r ) {
            for (int c = 0; c < NUM_CELLS; ++c) {
                int x = colToX(c);
                int y = rowToY(r);
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.FILL);
                m_rect.set(x, y, x + m_cellWidth, y + m_cellHeight);

                canvas.drawRect(m_rect, m_paintGrid);

            }
        }

        for (dotPath dP : dotPaths) {
            Paint circlePaint = new Paint();
            circlePaint.setStyle(Paint.Style.FILL);
            circlePaint.setColor(dP.getPathColor());
            canvas.drawCircle(colToX(dP.getEnd().getCol()) + (m_cellWidth/2),
                    rowToY(dP.getEnd().getRow()) + (m_cellWidth/2),
                    (m_cellWidth/2)*(float)0.8, circlePaint
            );
            canvas.drawCircle(colToX(dP.getStart().getCol()) + (m_cellWidth/2),
                    rowToY(dP.getStart().getRow()) + (m_cellWidth/2),
                    (m_cellWidth/2)*(float)0.8, circlePaint
            );
        }

        for(int i = 0; i < board[0].length; i++) {
            Arrays.fill(board[i], false);
        }


        Paint pathPaint = new Paint();
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(32);
        pathPaint.setStrokeCap(Paint.Cap.ROUND);
        pathPaint.setStrokeJoin(Paint.Join.ROUND);
        pathPaint.setAntiAlias(true);

        for (dotPath dP : dotPaths) {
            if (dP.getPath() != null) {
                if (!dP.getPath().isEmpty()) {

                    Coordinate coTo = dP.getPath().get(0);
                    Path forPath = new Path();
                    forPath.moveTo(colToX(coTo.getCol()) + m_cellWidth / 2,
                            rowToY(coTo.getRow()) + m_cellHeight / 2);
                    for (Coordinate coO : dP.getPath()) {
                        int x = colToX(coO.getCol());
                        int y = rowToY(coO.getRow());

                        pathPaint.setColor(dP.getPathColor());
                        Paint paint = new Paint();
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(dP.getPathColor());
                        paint.setAlpha(75);
                        pathRect.set(x, y, x + m_cellWidth, y + m_cellHeight);
                        canvas.drawRect(pathRect, paint);

                        forPath.lineTo(colToX(coO.getCol()) + m_cellWidth / 2,
                                rowToY(coO.getRow()) + m_cellHeight / 2);
                        canvas.drawPath(forPath, pathPaint);

                        board[coO.getCol()][coO.getRow()] = true;

                    }
                }
            }
        }
        //canvas.drawPath(m_path, m_paintPath);
    }

    private boolean areNeighbours(int c1, int r1, int c2, int r2) {
        return Math.abs(c1-c2) + Math.abs(r1-r2) == 1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();         // NOTE: event.getHistorical... might be needed.
        int y = (int)event.getY();
        int c = xToCol(x);
        int r = yToRow(y);
        boolean isVictory = true;

        if (c >= NUM_CELLS || r >= NUM_CELLS) {
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            for (dotPath dP : dotPaths){
                Coordinate c2 = new Coordinate(c, r);

                if (c2.equals(dP.getEnd()) || c2.equals(dP.getStart())) {
                    m_cellPath = dP;
                    dP.reset();
                    m_cellPath.append(c2);
                }

            }

        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {

            if(m_cellPath != null) {
                if (!m_cellPath.isEmpty()) {
                    List<Coordinate> coordinateList = m_cellPath.getPath();
                    Coordinate last = coordinateList.get(coordinateList.size() - 1);
                    Coordinate secondToLast = null;

                    if (coordinateList.size() >= 2) {
                        secondToLast = coordinateList.get(coordinateList.size() - 2);
                    }

                    if (areNeighbours(last.getCol(), last.getRow(), c, r)) {
                        Coordinate newCo = new Coordinate(c, r);
                        boolean addToPath = true;

                        // Checks if the dots are now connected and if you are going beyond the connection
                        if (!newCo.equals(secondToLast)) {
                            if (coordinateList.contains(m_cellPath.getStart()) && coordinateList.contains(m_cellPath.getEnd())) {
                                addToPath = false;
                            }
                        }
                        for (dotPath dP : dotPaths) {
                            if (secondToLast != null) {
                                if (!newCo.equals(secondToLast)) {
                                    if (!dP.equals(m_cellPath) && (dP.getStart().equals(newCo) || dP.getEnd().equals(newCo))) {
                                        addToPath = false;
                                        dP.setConnected(true);
                                        break;
                                    }
                                }
                            } else {
                                if (!dP.equals(m_cellPath) && (dP.getStart().equals(newCo) || dP.getEnd().equals(newCo))) {
                                    dP.setConnected(true);
                                    addToPath = false;
                                    break;
                                }
                            }
                            if (!dP.equals(m_cellPath) && dP.crossesPath(last)) {
                                dP.clearFromPath(last);
                            }
                        }

                        if (addToPath) {
                            m_cellPath.append(newCo);
                            invalidate();
                        }
                    }
                }
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            if(m_cellPath != null) {
                if (!m_cellPath.isEmpty()) {
                    List<Coordinate> list = m_cellPath.getPath();
                    totalMoves++;
                    totalConnections = 0;
                    for (dotPath dP : dotPaths) {
                        List<Coordinate> dPList = dP.getPath();
                        if (dPList.contains(m_cellPath.getStart()) && dPList.contains(m_cellPath.getEnd())) {
                            dP.setConnected(true);
                        }
                        if (m_cellPath.equals(dP)) {
                            List<Coordinate> newList = new ArrayList<Coordinate>();

                            for (Coordinate co : list) {
                                newList.add(co.clone());
                            }
                            dP.setPath(newList);
                            break;
                        }
                    }
                    for (dotPath dP : dotPaths) {
                        if (!dP.getConnected())
                            isVictory = false;
                    }
                    m_cellPath.setPath(list);
                    invalidate();
                    if (isVictory) {
                        for (int i = 0; i < NUM_CELLS; i++) {
                            for (int j = 0; j < NUM_CELLS; j++) {
                                if (!board[i][j])
                                    isVictory = false;
                            }
                        }
                        if (isVictory)
                            winningFunction();
                    }
                }
            }
        }
        return true;
    }

    private void winningFunction() {

        new AlertDialog.Builder(getContext())
                .setTitle("Victory!")
                .setMessage("Hurray! You won!")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton("Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.star_big_on)
                .show();
        Log.d("Winning", "Just won");
    }

    public void setColor(int color) {
        m_paintPath.setColor(color);
        invalidate();
    }
}
