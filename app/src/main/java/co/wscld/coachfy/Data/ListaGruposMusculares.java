package co.wscld.coachfy.Data;

import java.util.ArrayList;

public class ListaGruposMusculares {
    public ArrayList<Integer> lista(){
        ArrayList<Integer> gruposMusculares = new ArrayList();
        gruposMusculares.add(1);
        gruposMusculares.add(2);
        gruposMusculares.add(3);
        gruposMusculares.add(4);
        gruposMusculares.add(5);
        gruposMusculares.add(6);
        gruposMusculares.add(7);
        gruposMusculares.add(8);
        gruposMusculares.add(9);
        gruposMusculares.add(10);
        return gruposMusculares;
    }

    public String getNomeFor(int id){
        if(id == 1)
            return "Peitoral";
        if(id == 2)
            return "Costas";
        if(id == 3)
            return "Ombro";
        if(id == 4)
            return "Perna";
        if(id == 5)
            return "Biceps";
        if(id == 6)
            return "Triceps";
        if(id == 7)
            return "Antebraço";
        if(id == 8)
            return "Quadriceps";
        if(id == 9)
            return "Posterior";
        if(id == 10)
            return "Glúteo";
        else
            return null;
    }
}
