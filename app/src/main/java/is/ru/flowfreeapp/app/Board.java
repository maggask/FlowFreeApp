package is.ru.flowfreeapp.app;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Board extends View {

    private final int NUM_CELLS = 5;
    private int m_cellWidth;
    private int m_cellHeight;

    private Rect m_rect = new Rect();
    private Paint m_paintGrid  = new Paint();
    private Paint m_paintPath  = new Paint();
    private Path m_path = new Path();
    private Path dP_path = new Path();
    private Rect pathRect = new Rect();
    private dotPath dotP = null;

    private Cellpath m_cellPath = new Cellpath();

    private int xToCol(int x) {
        return (x - getPaddingLeft()) / m_cellWidth;
    }

    private int yToRow(int y) {
        return (y - getPaddingTop()) / m_cellHeight;
    }

    private int colToX(int col) {
        return col * m_cellWidth + getPaddingLeft() ;
    }

    private int rowToY(int row) {
        return row * m_cellHeight + getPaddingTop() ;
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

        dotPaths.add(new dotPath(new Coordinate(0, 0), new Coordinate(0, 3)));
        dotPaths.add(new dotPath(new Coordinate(2, 0), new Coordinate(2, 3)));
        dotPaths.add(new dotPath(new Coordinate(4, 1), new Coordinate(4, 4)));
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
        m_path.reset();
        if (!m_cellPath.isEmpty()) {
            List<Coordinate> colist = m_cellPath.getCoordinates();
            Coordinate co = colist.get(0);
            m_path.moveTo(colToX(co.getCol()) + m_cellWidth/2,
                           rowToY(co.getRow()) + m_cellHeight/2);
            for (int i = 1; i < colist.size(); ++i) {
                co = colist.get(i);
                m_path.lineTo(colToX(co.getCol()) + m_cellWidth/2,
                                rowToY(co.getRow()) + m_cellHeight/2);
            }
        }

        Paint circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        for(dotPath dP : dotPaths) {
            canvas.drawCircle(colToX(dP.getEnd().getCol()) + (m_cellWidth/2),
                    rowToY(dP.getEnd().getRow()) + (m_cellWidth/2),
                    (m_cellWidth/2)*(float)0.8, circlePaint
            );
            canvas.drawCircle(colToX(dP.getStart().getCol()) + (m_cellWidth/2),
                    rowToY(dP.getStart().getRow()) + (m_cellWidth/2),
                    (m_cellWidth/2)*(float)0.8, circlePaint
            );
        }

        dP_path.reset();
        for(dotPath dP : dotPaths) {
            if(dP.getPath() != null) {
                Coordinate coTo = dP.getPath().get(0);
                dP_path.moveTo(colToX(coTo.getCol()) + m_cellWidth/2,
                        rowToY(coTo.getRow()) + m_cellHeight/2);
                for (Coordinate coO : dP.getPath()) {
                    int x = colToX(coO.getCol());
                    int y = rowToY(coO.getRow());
                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.GREEN);
                    paint.setAlpha(75);
                    pathRect.set(x, y, x + m_cellWidth, y + m_cellHeight);
                    canvas.drawRect(pathRect, paint);

                    dP_path.lineTo(colToX(coO.getCol()) + m_cellWidth/2,
                            rowToY(coO.getRow()) + m_cellHeight/2);

                }
            }
        }
        canvas.drawPath(dP_path, m_paintPath);
        canvas.drawPath(m_path, m_paintPath);
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

        if (c >= NUM_CELLS || r >= NUM_CELLS) {
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            m_cellPath.reset();
            for(dotPath dP : dotPaths){
                Coordinate c2 = new Coordinate(c, r);
                if(c2.equals(dP.getEnd()) || c2.equals(dP.getStart())) {
                    dotP = dP;
                    dP.removePath();
                    m_cellPath.append(c2);
                }

            }

        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (!m_cellPath.isEmpty()) {
                List<Coordinate> coordinateList = m_cellPath.getCoordinates();
                Coordinate last = coordinateList.get(coordinateList.size()-1);
                if (areNeighbours(last.getCol(),last.getRow(), c, r)) {
                    Coordinate newCo = new Coordinate(c, r);
                    boolean addToPath = true;
                    for(dotPath dP : dotPaths) {
                        if(!dP.equals(dotP) && (dP.getStart().equals(newCo) || dP.getEnd().equals(newCo))) {
                            addToPath = false;
                            break;
                        }
                    }
                    if(addToPath) {
                        m_cellPath.append(newCo);
                        invalidate();
                    }
                }
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            if(!m_cellPath.isEmpty()) {
                List<Coordinate> list = m_cellPath.getCoordinates();
                for(dotPath dP : dotPaths) {
                    if(dotP.equals(dP)){
                        dP.setPath(list);
                        break;
                    }
                }
                dotP.setPath(list);
                invalidate();
            }
        }
        return true;
    }

    public void setColor(int color) {
        m_paintPath.setColor(color);
        invalidate();
    }
}
