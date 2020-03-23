package UI;

import Gamemodes.ASTERN.AStern;
import Gamemodes.GameOfLife.GameOfLife;
import Gamemodes.Gamemode;
import Handler.MouseHandler;
import UI.PanelType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Panels extends JPanel {

    public static List<List<Panel>> cells;
    public static Point selectedCell;
    public static boolean dropLeft = false;
    public static boolean dropRight = false;
    public static boolean dropS = false;
    public static boolean dropE = false;
    public static Point currentCell = new Point(0, 0);
    public static Point hoveredCell = new Point(0, 0);

    public static int generation = 0;

    MouseAdapter mouseHandler;

    public static Gamemode gamemode = Gamemode.ASTERN;


    public static Point startPoint;
    public static Point endPoint;

    public static int rectSize;

    public Panels(int with, int height, int rectSize) {
        this.rectSize = rectSize;
        setSize(with, height);
        cells = new ArrayList<>();

        mouseHandler = new MouseHandler(rectSize, this);
        addMouseMotionListener(mouseHandler);
        addMouseListener(mouseHandler);
        addMouseWheelListener(mouseHandler);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {


                if(e.getKeyCode()==83){ //s key
                    dropS = true;
                }else if (e.getKeyCode()==69){ //e key
                    dropE = true;
                }


            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==83){ //s key
                    dropS = false;
                }else if (e.getKeyCode()==69){ //e key
                    dropE = false;
                }else if(e.getKeyCode()==32){ //spacebar Key

                    if(gamemode== Gamemode.GAMEOFLIFE){
                        startGameOfLife();
                    }else if(gamemode== Gamemode.ASTERN){
                        startComputeRoute();
                    }
                }
            }
        });

    }



    @Override
    public void invalidate() {
        cells.clear();
        selectedCell = null;
        super.invalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int width = getWidth();
        int height = getHeight();

        if(startPoint!=null && endPoint!=null) {
            for(int i = 0; i < cells.size(); i++){
                for(int j = 0; j < cells.get(i).size(); j++){
                    cells.get(i).get(j).computeScore();
                }
            }
        }

        if (cells.isEmpty()) {
            int hIndex = 0;
            for(int h = 0; h < height; h+=rectSize){
                cells.add(new ArrayList<Panel>());
                for(int w = 0; w < width; w+=rectSize){
                    cells.get(hIndex).add(new Panel(w, h, rectSize, PanelType.FREE));
                }
                hIndex++;
            }
        }

        g2d.setColor(Color.GRAY);
        for (List<Panel> cell : cells) {
            for (Panel panel :cell){
                if(gamemode == Gamemode.ASTERN){

                    switch (panel.getPanelType()){

                        case VISITED:
                            g2d.setColor(Color.orange);
                            g2d.fillRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                            g2d.setColor(Color.DARK_GRAY);
                            g2d.drawRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                            if(panel.computeScore()!=0) {
                                g2d.drawString(panel.computeScore() + "", panel.getIX(), panel.getYP()+10);
                            }
                            break;
                        case END:
                            g2d.setColor(Color.RED);
                            g2d.fillRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                            g2d.setColor(Color.DARK_GRAY);
                            g2d.drawRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                            if(panel.computeScore()!=0) {
                                g2d.drawString(panel.computeScore() + "", panel.getIX(), panel.getYP()+10);
                            }

                            break;
                        case FREE:
                            if(panel.isHover()){
                                g2d.setColor(Color.GREEN);
                                g2d.fillRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                            }else{
                                g2d.setColor(Color.LIGHT_GRAY);
                                g2d.fillRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                            }
                            g2d.setColor(Color.DARK_GRAY);
                            g2d.drawRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                            if(panel.computeScore()!=0){
                                g2d.drawString(panel.computeScore()+"", panel.getIX(), panel.getYP()+10);
                            }
                            break;
                        case START:
                            g2d.setColor(Color.BLUE);
                            g2d.fillRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                            g2d.setColor(Color.DARK_GRAY);
                            g2d.drawRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                            if(panel.computeScore()!=0) {
                                g2d.drawString(panel.computeScore() + "", panel.getIX(), panel.getYP()+10);
                            }
                            break;
                        case WALL:
                            g2d.setColor(Color.BLACK);
                            g2d.fillRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                            g2d.setColor(Color.DARK_GRAY);
                            g2d.drawRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                            if(panel.computeScore()!=0) {
                                g2d.drawString(panel.computeScore() + "", panel.getIX(), panel.getYP()+10);
                            }
                            break;
                        case SELECTED:
                            g2d.setColor(Color.cyan);
                            g2d.fillRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                            g2d.setColor(Color.DARK_GRAY);
                            g2d.drawRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                            if(panel.computeScore()!=0) {
                                g2d.drawString(panel.computeScore() + "", panel.getIX(), panel.getYP()+10);
                            }
                            break;
                    }
                }else if(gamemode== Gamemode.GAMEOFLIFE) {
                    if(panel.isAlive()){
                        g2d.setColor(Color.BLACK);
                        g2d.fillRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                        g2d.setColor(Color.DARK_GRAY);
                        g2d.drawRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                    }else{
                        if(panel.isHover()){
                            g2d.setColor(Color.GREEN);
                            g2d.fillRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                        }else{
                            g2d.setColor(Color.LIGHT_GRAY);
                            g2d.fillRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                        }
                        g2d.setColor(Color.DARK_GRAY);
                        g2d.drawRect(panel.getIX(), panel.getYP(), rectSize, rectSize);
                        if(panel.computeScore()!=0){
                            g2d.drawString(panel.computeScore()+"", panel.getIX(), panel.getYP()+10);
                        }
                    }



                }

            }
        }
        if(gamemode== Gamemode.GAMEOFLIFE) {

            g2d.setColor(Color.BLACK);
            g2d.drawString(generation + "", 10, getHeight()-10);
        }


        g2d.dispose();
    }

    public void addToRectSize(int ammound){
        List<List<Panel>> newCells = new ArrayList<>();
        rectSize += ammound;
        int hIndex = 0;
        for(int h = 0; h < getHeight(); h+=rectSize){
            newCells.add(new ArrayList<Panel>());
            int wIndex = 0;
            for(int w = 0; w < getWidth(); w+=rectSize){
                if(hIndex>=cells.size()){
                    newCells.add(new ArrayList<Panel>());
                }
                if(wIndex>=newCells.get(hIndex).size()){
                    newCells.get(hIndex).add(new Panel(w, h, rectSize, PanelType.FREE));
                }else if(wIndex>=cells.get(hIndex).size()){
                    newCells.get(hIndex).add(new Panel(w, h, rectSize, PanelType.FREE));
                }else{
                    newCells.get(hIndex).add(new Panel(w, h, rectSize, cells.get(hIndex).get(wIndex).getPanelType()));
                }
                wIndex++;
            }
            hIndex++;
        }
        cells = newCells;




        removeMouseMotionListener(mouseHandler);
        removeMouseListener(mouseHandler);
        removeMouseWheelListener(mouseHandler);

        mouseHandler = new MouseHandler(rectSize, this);


        addMouseMotionListener(mouseHandler);
        addMouseListener(mouseHandler);
        addMouseWheelListener(mouseHandler);
    }

    private void startComputeRoute() {
        Thread t1 = new Thread(new AStern(this, 100));
        t1.start();
    }


    private void startGameOfLife() {
        Thread t1 = new Thread(new GameOfLife(this, 10));
        t1.start();
    }

}