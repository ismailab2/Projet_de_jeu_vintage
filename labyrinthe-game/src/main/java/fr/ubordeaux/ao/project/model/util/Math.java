package fr.ubordeaux.ao.project.model.util;

//class servant a eviter la redondance de code en implémentant les fonction mathematique de base nécessaire dans le modele
public class Math {
    private static int abs(int x) {
        if(x>0){
            return x;
        }
        else {
            return -x;
        }
    }
}
