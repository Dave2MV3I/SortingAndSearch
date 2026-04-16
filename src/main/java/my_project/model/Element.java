package my_project.model;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;

import java.awt.*;

import static my_project.model.MarkingType.DEMARK;

public class Element extends GraphicalObject{
    private int value;

    private Color color = Color.WHITE;
    private boolean selected = false;
    private boolean sorted = false;
    private boolean marked = false;

    public Element(int value) {
        this.value = value;
        this.height = value*5;
        this.y = Config.WINDOW_HEIGHT -29- height;
        color = Color.WHITE;
    }

    @Override
    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(this.color);
        drawTool.drawFilledRectangle(x, y, width, height);

        drawTool.setCurrentColor(Color.BLACK);
        drawTool.setLineWidth(2);
        drawTool.drawRectangle(x, y, width, height);
        drawTool.setLineWidth(1);
    }

    public void setMarking(MarkingType type, boolean forward) {
        // Der Zustand wird einfach je nach Richtung an- oder ausgeschaltet
        switch (type) {
            case COMPARISON -> selected = forward;
            case PERMANENT  -> marked = forward;
            case SORTED     -> sorted = forward;
            case DEMARK     -> selected = !forward;
            case DEMARK_PERMANENT -> marked = !forward;
        }
        updateColor();
    }

    private void updateColor() {
        // Prioritäten-System: Was ist wichtiger zu sehen?
        if (selected) {
            color = Color.GRAY;
        } else if (marked) {
            color = Color.RED;
        } else if (sorted) {
            color = Color.GREEN;
        } else {
            color = Color.WHITE;
        }
    }

    public int getValue(){return value;}
}
