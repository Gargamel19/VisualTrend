package Gamemodes.GameOfLife;

import UI.Panel;
import UI.Panels;

import java.util.ArrayList;
import java.util.List;

public class GameOfLife implements Runnable {

    int sleep;
    Panels panels;

    public GameOfLife(Panels panels, int sleep) {
        this.panels = panels;
        this.sleep = sleep;
    }

    @Override
    public void run() {
        while (true){
            List<List<Panel>> clonedLists = new ArrayList<>();

            for (int i = 0; i < Panels.cells.size(); i++) {
                clonedLists.add(new ArrayList<>());
                for (int j = 0; j < Panels.cells.get(i).size(); j++) {
                    clonedLists.get(i).add(new Panel(Panels.cells.get(i).get(j).getIX(), Panels.cells.get(i).get(j).getYP(), Panels.cells.get(i).get(j).height, Panels.cells.get(i).get(j).getPanelType()));
                }
            }

            for (int i = 0; i < Panels.cells.size(); i++) {
                for (int j = 0; j < Panels.cells.get(i).size(); j++) {

                    int aliveNeighbours = 0;
                    for (int k = -1; k <= 1; k++) {
                        for (int l = -1; l <= 1; l++) {

                            if(!(k==0 && l == 0)){

                                if(i+k>=0 && j+l>=0 && i+k<Panels.cells.size() && j+l<Panels.cells.get(i+k).size()){
                                    if(Panels.cells.get(i+k).get(j+l).isAlive()){
                                        aliveNeighbours++;
                                    }
                                }
                            }

                        }
                    }

                    int magicNumber1 = 2;
                    int magicNumber2 = 3;


                    if(!Panels.cells.get(i).get(j).isAlive() && aliveNeighbours==magicNumber2) clonedLists.get(i).get(j).setAlive(true);
                    else if (Panels.cells.get(i).get(j).isAlive() && aliveNeighbours<magicNumber1) clonedLists.get(i).get(j).setAlive(false);
                    else if (Panels.cells.get(i).get(j).isAlive() && aliveNeighbours>magicNumber2) clonedLists.get(i).get(j).setAlive(false);
                    else if (Panels.cells.get(i).get(j).isAlive()) clonedLists.get(i).get(j).setAlive(true);

                }
            }

            Panels.cells = clonedLists;

            Panels.generation++;
            panels.repaint();


            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }




        }




    }
}
