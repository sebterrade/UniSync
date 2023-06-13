/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JFramePackage;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
/**
 *
 * @author sebte
 */
public class ComponentResizer extends MouseAdapter {
    private static final int LOCATION_NONE = -1;
    private static final int LOCATION_TOP = 0;
    private static final int LOCATION_BOTTOM = 1;
    private static final int LOCATION_LEFT = 2;
    private static final int LOCATION_RIGHT = 3;
    private static final int LOCATION_TOP_LEFT = 4;
    private static final int LOCATION_TOP_RIGHT = 5;
    private static final int LOCATION_BOTTOM_LEFT = 6;
    private static final int LOCATION_BOTTOM_RIGHT = 7;

    private static final int BORDER_DRAG_THICKNESS = 5;
    private static final int CORNER_DRAG_WIDTH = 16;

    private final Cursor[] cursors = {
        Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR),
        Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR),
        Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR),
        Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR),
        Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR),
        Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR),
        Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR),
        Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR)
    };

    private int location = LOCATION_NONE;
    private Point pressed;
    private Rectangle bounds;

    private Component target;

    public ComponentResizer(Component target) {
        this.target = target;
        target.addMouseListener(this);
        target.addMouseMotionListener(this);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (isOverBorder(e)) {
            target.setCursor(cursors[location]);
        } else {
            target.setCursor(Cursor.getDefaultCursor());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isOverBorder(e)) {
            pressed = e.getLocationOnScreen();
            bounds = target.getBounds();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (pressed != null) {
            int x = bounds.x;
            int y = bounds.y;
            int width = bounds.width;
            int height = bounds.height;

            int dx = e.getLocationOnScreen().x - pressed.x;
            int dy = e.getLocationOnScreen().y - pressed.y;

            switch (location) {
                case LOCATION_TOP:
                    y += dy;
                    height -= dy;
                    break;
                case LOCATION_BOTTOM:
                    height += dy;
                    break;
                case LOCATION_LEFT:
                    x += dx;
                    width -= dx;
                    break;
                case LOCATION_RIGHT:
                    width += dx;
                    break;
                case LOCATION_TOP_LEFT:
                    x += dx;
                    width -= dx;
                    y += dy;
                    height -= dy;
                    break;
                case LOCATION_TOP_RIGHT:
                    width += dx;
                    y += dy;
                    height -= dy;
                    break;
                case LOCATION_BOTTOM_LEFT:
                    x += dx;
                    width -= dx;
                    height += dy;
                    break;
                case LOCATION_BOTTOM_RIGHT:
                    width += dx;
                    height += dy;
                    break;
            }

            target.setBounds(x, y, width, height);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = null;
    }

    private boolean isOverBorder(MouseEvent e) {
        int width = target.getWidth();
        int height = target.getHeight();

        Rectangle bounds = target.getBounds();
        bounds.grow(-BORDER_DRAG_THICKNESS, -BORDER_DRAG_THICKNESS);

        if (bounds.contains(e.getPoint())) {
            return false;
        }

        int x = e.getX();
        int y = e.getY();

        if (y < BORDER_DRAG_THICKNESS) {
            location = LOCATION_TOP;
        } else if (y > height - BORDER_DRAG_THICKNESS) {
            location = LOCATION_BOTTOM;
        } else if (x < BORDER_DRAG_THICKNESS) {
            location = LOCATION_LEFT;
        } else if (x > width - BORDER_DRAG_THICKNESS) {
            location = LOCATION_RIGHT;
        } else if (x < CORNER_DRAG_WIDTH && y < CORNER_DRAG_WIDTH) {
            location = LOCATION_TOP_LEFT;
        } else if (x < CORNER_DRAG_WIDTH && y > height - CORNER_DRAG_WIDTH) {
            location = LOCATION_BOTTOM_LEFT;
        } else if (x > width - CORNER_DRAG_WIDTH && y < CORNER_DRAG_WIDTH) {
            location = LOCATION_TOP_RIGHT;
        } else if (x > width - CORNER_DRAG_WIDTH && y > height - CORNER_DRAG_WIDTH) {
            location = LOCATION_BOTTOM_RIGHT;
        } else {
            location = LOCATION_NONE;
        }

        return location != LOCATION_NONE;
    }
}
