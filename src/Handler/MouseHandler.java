package Handler;

import Gamemodes.Gamemode;
import UI.PanelType;
import UI.Panels;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseHandler extends MouseAdapter {

    int rectSize;
    Panels panels;

    public MouseHandler(int rectSize, Panels panels) {
        this.rectSize = rectSize;
        this.panels = panels;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int column = e.getX() / rectSize;
        int row = (e.getY() / rectSize);

        if(e.getButton()==1){
            if(Panels.gamemode == Gamemode.ASTERN){
                if(Panels.dropS){
                    if(Panels.startPoint!=null){
                        Panels.cells.get(Panels.startPoint.x).get(Panels.startPoint.y).changePanelType(PanelType.FREE);
                    }
                    Panels.startPoint = new Point(row, column);
                    Panels.cells.get(row).get(column).changePanelType(PanelType.START);
                }else if(Panels.dropE){
                    if(Panels.endPoint!=null){
                        Panels.cells.get(Panels.endPoint.x).get(Panels.endPoint.y).changePanelType(PanelType.FREE);
                    }
                    Panels.endPoint = new Point(row, column);
                    Panels.cells.get(row).get(column).changePanelType(PanelType.END);
                }else {
                    Panels.cells.get(row).get(column).changePanelType(PanelType.WALL);
                }
            }else if(Panels.gamemode==Gamemode.GAMEOFLIFE) {
                Panels.cells.get(row).get(column).setAlive(true);
            }

        }else  if(e.getButton()==3){

            if(Panels.gamemode==Gamemode.ASTERN) {
                Panels.cells.get(row).get(column).changePanelType(PanelType.FREE);
            }else if(Panels.gamemode==Gamemode.GAMEOFLIFE) {
                Panels.cells.get(row).get(column).setAlive(false);
            }

        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int column = e.getX() / rectSize;
        int row = e.getY() / rectSize;

        if(Panels.dropLeft){
            if(Panels.gamemode==Gamemode.ASTERN) {
                Panels.cells.get(row).get(column).changePanelType(PanelType.WALL);
            }else if(Panels.gamemode==Gamemode.GAMEOFLIFE) {
                Panels.cells.get(row).get(column).setAlive(true);
            }
        }else  if(Panels.dropRight){
            if(Panels.gamemode==Gamemode.ASTERN) {
                Panels.cells.get(row).get(column).changePanelType(PanelType.FREE);
            }else if(Panels.gamemode==Gamemode.GAMEOFLIFE) {
                Panels.cells.get(row).get(column).setAlive(false);
            }
        }
        panels.repaint();

    }


    @Override
    public void mousePressed(MouseEvent e) {

        if(e.getButton()==1){
            Panels.dropLeft = true;
        }else if(e.getButton()==3){
            Panels.dropRight = true;
        }
        panels.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if(e.getButton()==1){
            Panels.dropLeft = false;
        }else if(e.getButton()==3){
            Panels.dropRight = true;
        }
        panels.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        int column = e.getX() / rectSize;
        int row = e.getY() / rectSize;

        if(Panels.currentCell.y != row || Panels.currentCell.x != column){

            if(Panels.cells.get(row).get(column).getPanelType()==PanelType.FREE) {
                Panels.cells.get(row).get(column).setHover(true);
            }

            if(Panels.hoveredCell!=null){
                Panels.cells.get(Panels.hoveredCell.y).get(Panels.hoveredCell.x).setHover(false);
            }

        }

        Panels.hoveredCell = new Point(column, row);
        Panels.currentCell = new Point(column, row);

        panels.repaint();

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        panels.addToRectSize(e.getWheelRotation());
        panels.repaint();
    }

}
