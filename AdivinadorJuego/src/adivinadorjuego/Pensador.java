package adivinadorjuego;

import java.util.ArrayList;
import java.util.Arrays;

public class Pensador {
    
    private String numero;

    public Pensador(String numero) throws Exception {
        validarNumero(numero);
        this.numero = numero;
    }

    public Pensador() {
        this.numero = pensarNumero();
    }

    private static String pensarNumero() {
        char charArray[] = new char[4];
        
        // listo los caracteres disponibles en un ArrayList
        ArrayList<Character> numeros = new ArrayList<>();
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
        
        for (int i=0; i<4; i++) {
            // Random entre 0 y 9. El máximo va bajando de a 1 a medida que elijo números
            int indice = (int) (Math.random() * (numeros.size()-1));
            // Pongo uno de los números en el array
            charArray[i] = numeros.get(indice);
            // Elimino el número que puse para no volverlo a elegir
            numeros.remove(indice);
        }
        
        // Devuelvo el Array como un String
        return new String(charArray);
    }
    
    private static void validarNumero(String numero) throws Exception {
        // Todos los caracteres son numeros y si son 4 caracteres
        if (numero.matches("\\d\\d\\d\\d")) {
            // Todos los caracteres son distintos
            char charArray[] = numero.toCharArray();
            Arrays.sort(charArray);
            
            for (int i = 0; i < charArray.length - 1; i++) {
                // Comparo cada caracter con el siguiente. Como están ordenados,
                // si hay caracteres iguales estarán juntos.
                if (charArray[i] == charArray[i+1]) {
                    throw new Exception("Ingrese dígitos distintos.");
                }
            }
        } else {
            throw new Exception("Ingrese 4 dígitos.");
        }
    }
    
    public String adivinarNumero(String intento) throws Exception {
        validarNumero(intento);
        
        int bien = 0;
        int regular = 0;
        
        for (int i = 0; i<4; i++) {
            if (numero.charAt(i) == intento.charAt(i)) {
                bien++;
            } else {
                for (int j = 0; j<4; j++) {
                    if (i==j) continue;
                    if (numero.charAt(i) == intento.charAt(j)) {
                        regular++;
                    }
                }
            }
        }
        
        return bien+" bien, "+regular+" regular";
    }
    
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
}
