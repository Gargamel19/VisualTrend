package Gamemodes.ASTERN;

import UI.PanelType;
import UI.Panels;

import java.util.ArrayList;

public class AStern implements Runnable {

    Panels panels;
    int pause;

    public AStern(Panels panels, int pause) {
        this.panels = panels;
        this.pause = pause;
    }

    @Override
    public void run() {
        recursiveSearch(Panels.startPoint.x, Panels.startPoint.y, 0);
    }


    public boolean recursiveSearch(int x, int y, int costs){
        ArrayList<ScoreDummy> al = new ArrayList<>();
        if(Panels.endPoint.x == x && Panels.endPoint.y == y){
            return true;
        }else{
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {

                    if(x+i>=0 && y+j>=0 && x+i<Panels.cells.size() && y+j<Panels.cells.get(x+i).size() && !(i==0&&j==0)){
                        boolean nichtBesucht = Panels.cells.get(x+i).get(y+j).getPanelType()== PanelType.VISITED || Panels.cells.get(x+i).get(y+j).getPanelType()==PanelType.FREE || Panels.cells.get(x+i).get(y+j).getPanelType()==PanelType.END;
                        if(nichtBesucht){
                            if(!(y+j==Panels.endPoint.y && x+i==Panels.endPoint.x)) {
                                Panels.cells.get(x+i).get(y+j).changePanelType(PanelType.VISITED);
                            }

                            int score = Panels.cells.get(x+i).get(y+j).computeScore();
                            al.add(new ScoreDummy(x+i, y+j, score, new Direction(i, j)));
                        }
                    }
                }
            }


            ArrayList<ScoreDummy> bestList = new ArrayList<>();
            for (int i = 0; i < al.size(); i++) {
                if (bestList.size()==0) bestList.add(al.get(i));
                else if(bestList.get(0).getScore()>al.get(i).getScore()){
                    bestList = new ArrayList<>();
                    bestList.add(al.get(i));
                }
                else if(bestList.get(0).getScore()==al.get(i).getScore()) bestList.add(al.get(i));
            }

            ScoreDummy bestPanel = null;

            for (int i = 0; i < bestList.size(); i++) {
                if ((bestList.get(i).dir.getY()==0 || bestList.get(i).dir.getX()==0)){
                    bestPanel = bestList.get(i);
                    break;
                }
            }
            if(bestPanel==null){
                bestPanel=bestList.get(0);
            }

            System.out.println("best: " + bestPanel.x + "," + bestPanel.y + ": " + bestPanel.score);




            if(bestPanel!=null) {
                if(!(bestPanel.y==Panels.endPoint.y && bestPanel.x==Panels.endPoint.x)) {
                    Panels.cells.get(bestPanel.x).get(bestPanel.y).changePanelType(PanelType.SELECTED);
                }

                panels.repaint();
                try {
                    Thread.sleep(pause);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                recursiveSearch(bestPanel.x, bestPanel.y, costs++);
            }




        }
        return true;

    }

}
