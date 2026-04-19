package my_project.model;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;

import java.awt.*;
import KAGO_framework.model.abitur.datenstrukturen.List;

public class Visualiser extends GraphicalObject{
    private Element[] animElements;
    private String algorithm;
    private List<SortingStep> history;
    private int historyIndex;
    boolean animating = false;
    boolean animated = false;
    double cooldownDuration = 0.3;
    double timer = cooldownDuration;

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

    public void setHistory(List<SortingStep> history){
        animated = false;
        this.history = history;
        historyIndex = 0;
        history.toFirst();
    }

    @Override
    public void update(double dt){
        // Auto anim
        if (history != null && animating) {
            timer -= dt;
            if (timer < 0) {
                if (history.hasAccess()) { // Prüfung!
                    animateStep(true);
                    timer = cooldownDuration;
                } else {
                    animating = false; // Stop, wenn fertig
                }
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

    public void animateStep(boolean forward){
        SortingStep step = history.getContent();

        switch (step){
            case Swap _ -> {
                int indexA = ((Swap)step).indexA();
                int indexB = ((Swap)step).indexB();
                double newXForA = 50+ indexB*animElements[indexB].getWidth();
                double newXForB = 50+ indexA*animElements[indexA].getWidth();
                animElements[indexA].setX(newXForA);
                animElements[indexB].setX(newXForB);

                Element temp = animElements[indexA];
                animElements[indexA] = animElements[indexB];
                animElements[indexB] = temp;
            }

            case Marking _ -> {
                int indexA = ((Marking)step).index();
                MarkingType mt = ((Marking)step).markingType();
                Element e = animElements[indexA];
                e.setMarking(mt, forward);
            }
        }

        if (forward) {
            historyIndex++;
            history.next();
            if (!history.hasAccess()) {
                animated = true;
                animating = false;
            }
        }
        else {
            historyIndex--;
            if (historyIndex < 0) return;
            history.toFirst();
            for (int i = 0; i < historyIndex; i++){
                history.next();
            }
        }
    }

    public void setCooldownDuration(double cooldownDuration){this.cooldownDuration = cooldownDuration/100;}

    public void setAnimating(boolean anim){
        if (anim){
            historyIndex = 0;
            history.toFirst();
        }
        animating = anim;
    }

    public boolean hasAnimated(){return animated;}
    public boolean isAnimating(){return animating;}
}
