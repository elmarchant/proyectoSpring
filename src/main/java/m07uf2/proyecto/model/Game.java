package m07uf2.proyecto.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {
    private int id;
    private String name;
    private boolean status;
    private int player1;
    private int player2;
    public ArrayList<ArrayList<String>> table1;
    public ArrayList<ArrayList<String>> table2;
    public boolean turn;

    public Game(String name, int id){
        this.id = id;
        this.name = name;
        this.status = true;
        this.turn = true;
        this.setTable();
    }

    private void setTable(){
        this.table1 = this.getTableModel();
        this.table2 = this.getTableModel();
    }

    private ArrayList<ArrayList<String>> getTableModel(){
        ArrayList<ArrayList<String>> tableModel = new ArrayList<ArrayList<String>>();

        for(int i=0; i<6; i++){
            ArrayList<String> row = new ArrayList<String>();
            for(int j=0; j<6; j++){
                if(getRandom(0, 1) == 1) row.add("");
                else row.add("X");
            }
            tableModel.add(row);
        }

        return tableModel;
    }

    private int getRandom(int min, int max){
        int random = (int)Math.floor(Math.random()*(max-min+1)+min);

        return random;
    }

    public void attackTable1(int x, int y){
        if(!table1.get(y).get(x).contains("·")){
            ArrayList<String> data = table1.get(y);
            data.set(x, data.get(x)+"·");
            table1.set(y, data);
            if(table1.get(y).get(x).contains("X")){
               this.turn = false;
            }else{
                this.turn = true;
            }
        }
    }

    public void attackTable2(int x, int y){
        if(!table2.get(y).get(x).contains("·")){
            ArrayList<String> data = table2.get(y);
            data.set(x, data.get(x)+"·");
            table2.set(y, data);
            if(table2.get(y).get(x).contains("X")){
                this.turn = true;
            }else{
                this.turn = false;
            }
        }
    }

    public boolean alive(boolean player){
        boolean valid = false;

        if(player){
            for(int i=0; i<table1.size(); i++){
                for(int j=0; j<table1.get(i).size(); j++){
                    if(table1.get(i).get(j).equals("X")) valid = true;
                }
            }
        }else{
            for(int i=0; i<table2.size(); i++){
                for(int j=0; j<table2.get(i).size(); j++){
                    if(table2.get(i).get(j).equals("X")) valid = true;
                }
            }
        }

        return valid;
    }

    public int getWinner(){
        int winner = 0;

        if(this.alive(true) && !this.alive(false)){
            winner = 1;
        }else if(!this.alive(true) && this.alive(false)){
            winner = 2;
        }

        return winner;
    }

    public void setPlayer1(int id){
        this.player1 = id;
    }

    public void setPlayer2(int id){
        this.player2 = id;
    }

    public boolean getStatus(){
        return  this.status;
    }

    public int getPlayer1() {
        return player1;
    }

    public int getPlayer2() {
        return player2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getTurn(){
        if(this.turn){
            return 1;
        }else{
            return 2;
        }
    }

    public void analizeGame(){
        if(!this.alive(true) || !this.alive(false)) this.status = false;
    }
}
