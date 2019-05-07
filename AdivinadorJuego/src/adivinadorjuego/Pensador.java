package adivinadorjuego;

import java.util.Arrays;

public class Pensador {
    
    private String numero;

    public Pensador(String numero) throws Exception {
        validarNumero(numero);
        this.numero = numero;
    }

    public Pensador() {
        pensarNumero();
    }

    private void pensarNumero() {
        throw new UnsupportedOperationException("Not supported yet.");
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
