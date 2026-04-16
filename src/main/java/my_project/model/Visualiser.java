package my_project.model;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;

import java.awt.*;
import java.util.ArrayList;

import static my_project.model.MarkingType.*;

public class Visualiser extends GraphicalObject{
    private Element[] animElements;
    private String algorithm;
    private boolean pause;
    private ArrayList<SortingStep> history;
    private int historyIndex;
    double swappingTimer = 0;
    boolean swapping = false;

    private double newXForA;
    private double newXForB;

    @Override
    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawFilledRectangle(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);

        drawTool.setCurrentColor(Color.WHITE);
        drawTool.drawText(10,20, "Current sorting algorithm: " + algorithm);

        if (animElements != null){
            for (Element e : animElements) {
                e.draw(drawTool);
            }
        }
    }

    public void setAlgorithm(String algorithm){
        this.algorithm = algorithm;
    }

    public void setHistory(ArrayList<SortingStep> history){
        this.history = history;
        historyIndex = 0;
        // TODO Method startAnimation?
    }

    @Override
    public void update(double dt){
        if (swapping){
            swappingTimer += dt;
        }
    }

    public void createElements(int[] array){
        animElements = new Element[array.length];
        for (int i = 0; i < array.length; i++){
            animElements[i] = new Element(array[i]);
        }

        for (int i = 0; i < animElements.length; i++) {
            animElements[i].setWidth(((Config.WINDOW_WIDTH-100)/animElements.length));
            animElements[i].setX(50+ i*animElements[i].getWidth());
        }
    }

    public void animateStep(boolean forward){
        SortingStep step = history.get(historyIndex);
        if (forward) historyIndex++;
        else historyIndex--;

        switch (step){
            case Swap s -> {
                int indexA = ((Swap)step).a();
                int indexB = ((Swap)step).b();
                newXForA = 50+ indexB*animElements[indexB].getWidth();
                newXForB = 50+ indexA*animElements[indexA].getWidth();
                int ms = 1000;
                swapping = true;
                //swappingTimer = 0;
            }

            case Marking m -> {
                int indexA = ((Marking)step).a();
                MarkingType mt = ((Marking)step).markingType();
                Element e = animElements[indexA];
                e.setMarking(mt);
            }
        }
    }
}
