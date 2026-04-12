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

    private double swapX1;
    private double swapX2;

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

    public void prepareAnimation(ArrayList<SortingStep> history, Element[] originalArray){
        this.animElements = originalArray;
        this.history = history;
        prepareElements();
        historyIndex = 0;
    }

    public void setAnimElementsWhenShuffling(Element[] elements){
        for (Element i : elements) System.out.println(i.getValue());
        animElements = elements;
        prepareElements();
    }

    private void prepareElements(){
        for (int i = 0; i < animElements.length; i++) {
            animElements[i].setWidth(((Config.WINDOW_WIDTH-100)/animElements.length));
            animElements[i].setX(50+ i*animElements[i].getWidth());
        }
    }

    @Override
    public void update(double dt){

    }

    public void animateStep(boolean forward){
        SortingStep step = history.get(historyIndex);
        if (forward) historyIndex++;
        else historyIndex--;

        switch (step){
            case Swap s -> {
                int indexA = ((Swap)step).a();
                int indexB = ((Swap)step).b();
                int ms = 1000;
                int timer = 0;
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
