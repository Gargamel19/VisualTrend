package UI;

import java.awt.*;

public class Panel extends Rectangle {

    private PanelType panelType;
    private int x;
    private int y;
    private int rectSize;
    private boolean hover = false;
    private boolean alive = false;

    public Panel(int x, int y, int rectSize, PanelType panelType) {
        super(x, y, rectSize, rectSize);
        this.rectSize = rectSize;
        this.x = x;
        this.y = y;
        changePanelType(panelType);
    }

    public void changePanelType(PanelType panelType){
        this.panelType = panelType;
    }

    public PanelType getPanelType() {
        return panelType;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public boolean isHover() {
        return hover;
    }

    public int getIX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getYP() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int computeScore(){
        if(Panels.startPoint!=null && Panels.endPoint!=null){
            int xend = Math.abs(x/rectSize-Panels.endPoint.y);
            int yend = Math.abs(y/rectSize-Panels.endPoint.x);

            int xstart = Math.abs(x/rectSize-Panels.startPoint.y);
            int ystart = Math.abs(y/rectSize-Panels.startPoint.x);

            int ergebniss = 2* Math.max(xend, yend) + Math.max(xstart, ystart);

            return ergebniss;
        }else{
            return 0;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
