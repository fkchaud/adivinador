package adivinadorjuego;

import java.util.Arrays;
import java.util.List;

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
        
        // listo los caracteres disponibles
        Character nums[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        // convierto Array a Lista
        List<Character> numeros = Arrays.asList(nums);
        
        for (int i=0; i<4; i++) {
            // Random entre 0 y 9. El máximo va bajando de a 1 a medida que elijo números
            int indice = (int) (Math.random() * (10 - i));
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
    
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
}
