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
    private ArrayList<SortingStep> history;
    private int historyIndex;
    boolean animating = false;
    final double timerDuration = 0.3;
    double timer = timerDuration;

    /*double swappingTimer = 0;
    boolean swapping = false;
    private double newXForA;
    private double newXForB;
    private boolean pause;*/


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
        /*if (swapping){
            swappingTimer += dt;
        }*/
        if (history != null && animating) {
            timer -= dt;
            if (timer < 0){
                animateStep(true);
                timer = timerDuration;
            }
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

    public void handleAnimation(boolean play, boolean auto){
        if (auto && play) animating = true;
    }

    public void animateStep(boolean forward){
        SortingStep step = history.get(historyIndex);
        if (forward) historyIndex++;
        else historyIndex--;

        switch (step){
            case Swap s -> {
                int indexA = ((Swap)step).indexA();
                int indexB = ((Swap)step).indexB();
                double newXForA = 50+ indexB*animElements[indexB].getWidth();
                double newXForB = 50+ indexA*animElements[indexA].getWidth();
                //int ms = 1000;
                //swapping = true;
                //swappingTimer = 0;
                animElements[indexA].setX(newXForA);
                animElements[indexB].setX(newXForB);
            }

            case Marking m -> {
                int indexA = ((Marking)step).index();
                MarkingType mt = ((Marking)step).markingType();
                Element e = animElements[indexA];
                e.setMarking(mt);
            }
        }
    }
}
