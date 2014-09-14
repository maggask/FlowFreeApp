package is.ru.flowfreeapp.app;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class Board extends View {

    private final int NUM_CELLS = 5;
    private int m_cellWidth;
    private int m_cellHeight;

    private Rect m_rect = new Rect();
    private Paint m_paintGrid  = new Paint();
    private Paint m_paintPath  = new Paint();
    private Path m_path = new Path();

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

    private Canvas mainCanvas = new Canvas();

    public Canvas getMainCanvas() {
        return mainCanvas;
    }

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
                if(r == 4 && c == 4) {
                    canvas.drawRect(m_rect, m_paintGrid);
                    canvas.drawCircle(x - (m_cellWidth/2), y - (m_cellWidth/2), (m_cellWidth/2)*(float)0.8, paint);
                }
                else if(r == 2 && c == 2) {
                    canvas.drawRect(m_rect, m_paintGrid);
                    canvas.drawCircle(x - (m_cellWidth/2), y - (m_cellWidth/2), (m_cellWidth/2)*(float)0.8, paint);
                }
                else {
                    canvas.drawRect(m_rect, m_paintGrid);
                }
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
                int x = colToX(co.getCol());
                int y = rowToY(co.getRow());
                if((co.getCol() == 1 && co.getRow() == 1) || (co.getCol() == 3 && co.getRow() == 3)) {
                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.GREEN);
                    paint.setAlpha(150);
                    m_rect.set(x, y, x + m_cellWidth, y + m_cellHeight);
                    canvas.drawRect(m_rect, paint);
                }
            }
        }
        canvas.drawPath(m_path, m_paintPath);
        mainCanvas = canvas;
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
            //m_path.reset();
            //m_path.moveTo( colToX(c) + m_cellWidth / 2, rowToY(r) + m_cellHeight / 2 );
            m_cellPath.reset();
            Coordinate coO = new Coordinate(c,r);
            m_cellPath.append(coO);
            int p = colToX(c);
            int q = rowToY(r);
            if((c == 2 && r == 2) || (c == 4 && r ==4)) {
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.GREEN);
                paint.setAlpha(150);
                m_rect.set(p, q, p + m_cellWidth, q + m_cellHeight);
                mainCanvas.drawRect(m_rect, paint);
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //m_path.lineTo( colToX(c) + m_cellWidth / 2, rowToY(r) + m_cellHeight / 2 );
            if (!m_cellPath.isEmpty()) {
                List<Coordinate> coordinateList = m_cellPath.getCoordinates();
                Coordinate last = coordinateList.get(coordinateList.size()-1);
                if ( areNeighbours(last.getCol(),last.getRow(), c, r)) {
                    m_cellPath.append(new Coordinate(c, r));
                    invalidate();
                }
            }
        }
        return true;
    }

    public void setColor(int color) {
        m_paintPath.setColor(color);
        invalidate();
    }
}
