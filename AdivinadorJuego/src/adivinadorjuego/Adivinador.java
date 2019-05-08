package adivinadorjuego;

import java.util.ArrayList;

public class Adivinador {
    
    private ArrayList<Character> numeros = new ArrayList<>();
    private String intento = "";

    private int bien = 0;
    private int regular = 0;
    
    public Adivinador() {
        numeros.add('0');
        numeros.add('1');
        numeros.add('2');
        numeros.add('3');
        numeros.add('4');
        numeros.add('5');
        numeros.add('6');
        numeros.add('7');
        numeros.add('8');
        numeros.add('9');
    }

    private String pensarNumero() {
        char charArray[] = new char[4];
        ArrayList<Character> num = (ArrayList) this.numeros.clone();
        
        for (int i=0; i<4; i++) {
            // Random entre 0 y 9. El máximo va bajando de a 1 a medida que elijo números
            int indice = (int) (Math.random() * (num.size()-1));
            // Pongo uno de los números en el array
            charArray[i] = num.get(indice);
            // Elimino el número que puse para no volverlo a elegir
            num.remove(indice);
        }
        
        // Devuelvo el Array como un String
        return new String(charArray);
    }

    public boolean adivinar() {
        // Realiza la busqueda propiamente dicha
        // inicia pensando un numero y preguntando
        /*
            situacion A: 4 bien, respuesta correcta
            situacion B: 0 bien y 0 regular, piensa otro numero
                        eliminando los elegidos
            situacion C: 4 numeros acertados, Bien o Regular.
                        Se deben realizar permutaciones
            situacion C: entre 1 y 3 numeros acertados, Bien o Regular.
                        Se debe ir cambiando de a 1 digito
        */
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private boolean moverNumeros() {
        // permuta digitos
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private boolean cambiarUnNumero() {
        // cambia un digito
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getIntento() {
        return intento;
    }
}
