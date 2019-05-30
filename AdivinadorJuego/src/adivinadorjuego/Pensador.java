package adivinadorjuego;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author GoldenImper
 */
public class Pensador {
    
    // El número pensado
    private String numero;

    /** 
     * Constructor de la instancia, piensa un número.
     * Llama al método {@link Pensador#pensarNumero()} para pensar el número.
     */
    public Pensador() {
        this.numero = pensarNumero();
    }

    /**
     * Elige un número.
     * Este número es el que se utilizará en el juego.
     * @return número pensado
     */
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
    
    /**
     * Verifica que el número tenga 4 dígitos sin duplicar.
     * @param numero el número a analizar
     * @throws Exception si no son 4 dígitos o si están duplicados
     */
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
    
    /**
     * Compara el intento del jugador con el número pensado.
     * Verifica caracter por caracter para determinar la cantidad de dígitos
     * bien (que están bien ubicados) y regulares (que están mal ubicados pero
     * pertenecen al número pensado)
     * @param intento el número que adivinó el jugador
     * @return cadena indicando cuántos dígitos están bien y regular
     * @throws Exception si el número es inválido
     */
    public String adivinarNumero(String intento) throws Exception {
        validarNumero(intento);
        
        int bien = 0;
        int regular = 0;
        
        for (int i = 0; i<4; i++) {
            // Si el caracter está en su lugar, suma 1 bien
            if (numero.charAt(i) == intento.charAt(i)) {
                bien++;
            } else {
                for (int j = 0; j<4; j++) {
                    // No compara respecto al caracter de su mismo índice, ya
                    //que se contó en los "bien"
                    if (i==j) continue;
                    // Si el caracter está en uno de los otros dígitos,
                    //suma 1 regular
                    if (numero.charAt(i) == intento.charAt(j)) {
                        regular++;
                    }
                }
            }
        }
        // Devuelve una cadena (que se mostrará por consola) que muestra la
        //cantidad de dígitos bien y regular
        return bien+" bien, "+regular+" regular";
    }
    
    /**
     * Devuelve el número pensado.
     * @return número pensado
     */
    public String getNumero() {
        return numero;
    }
    
}
