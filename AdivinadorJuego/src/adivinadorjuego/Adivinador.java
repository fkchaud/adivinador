package adivinadorjuego;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Adivinador {
    
    // Lista de números intentados, con su número y la cantidad de bien y regular
    private ArrayList<Intento> intentos = new ArrayList<>();

    /**
     * Genera un número aleatorio a utilizarse en un primer intento.
     * Sólo se llama la primera vez. Comprueba que el número sea válido a
     * través del método {@link #esValido(java.lang.String)}.
     * @return el número pensado
     */
    private String pensarNumero() {
        int numero;
        String str = "";
        boolean valido = false;
        
        // se siguen generando hasta que uno sea válido
        while (!valido) {
            // genera un número entre 0 y 9876 (el máximo es 9876.999 y se trunca)
            numero = (int) (Math.random() * 9877);
            // agrego los 0s necesarios para completar los 4 dígitos
            if (numero < 10) str = "000"+numero;
            else if (numero < 100) str = "00"+numero;
            else if (numero < 1000) str = "0"+numero;
            else str = ""+numero;
            // valido el string
            valido = esValido(str);
        }   
        // devuelve un string válido
        return str;
    }

    /**
     * Realiza el primer intento de adivinación. Luego del primer intento, 
     * llama al método {@link #buscarCoincidencia()} para seguir intentando.
     * @return verdadero si se encontró respuesta, falso si no se encontró
     */
    public boolean adivinar() {
        
        String intento;
        int bien;
        int regular;
        
        // piensa un número válido
        intento = pensarNumero();
        System.out.println("¿El número es "+intento+"?");
        // carga la cantidad de "bien", dígito de 0 a 5
        System.out.println("Números bien:");
        while (!AdivinadorJuego.in.hasNextInt(5) || !AdivinadorJuego.in.hasNext("\\d")) {
            System.out.println("Ingrese un número de 0 a 5:");
            AdivinadorJuego.in.next();
        }
        bien = AdivinadorJuego.in.nextInt();
        // carga la cantidad de "regular", dígito de 0 a 5
        System.out.println("Números regular:");
        while (!AdivinadorJuego.in.hasNextInt(5) || !AdivinadorJuego.in.hasNext("\\d")) {
            System.out.println("Ingrese un número de 0 a 5:");
            AdivinadorJuego.in.next();
        }
        regular = AdivinadorJuego.in.nextInt();

        // si hay 4 bien, se encontró la respuesta, fin del juego
        if (bien == 4) {
            System.out.println("La respuesta es "+intento);
            return true;
        } else {
            // si no se encontró, se agrega el intento para hacer las
            //comparaciones posteriormente y se llama al método que se repetirá
            //hasta encontrar solución
            intentos.add(new Intento(intento,bien,regular));
            return buscarCoincidencia();
        }
    }
    
    /**
     * Búsqueda del número correcto. Realiza muchas iteraciones hasta dar con
     * el número que pensó el jugador. Para ello, a partir del primer intento,
     * suma 1 al número, lo valida, y lo compara con otros intentos. En dicha
     * comparación verifica que este nuevo intento tenga la misma cantidad de
     * "bien" y "regular" que el intento comparado. Si coincide con todos los
     * intentos anteriores, pregunta al usuario si ese es el número correcto.
     * Si no lo es, sigue intentando.
     * @return verdadero si se encontró respuesta, falso si no se encontró
     */
    public boolean buscarCoincidencia() {
        // como este método se llama una sola vez después de cargar un intento
        //en el ArrayList, puedo obtener el número intentado por ese camino
        String intentoStr = intentos.get(0).getNumero();
        // necesito un int para poder sumarle números
        int intentoNum = Integer.parseInt(intentoStr);
        Intento intentoNuevo;
        boolean rtaEncontrada = false;
        
        // bucle hasta que se encuentre respuesta
        while (!rtaEncontrada) {
            // se suma 1 al intento
            intentoNum++;

            // si el número es menor a 1000 tendrá 3 dígitos, por lo que agrego un 0
            // Si es de 1 o 2 dígitos no hace falta agregarlo, ya que tendría
            //2 o 3 ceros repetidos por lo que en cualquier caso no pasaría el
            //validador
            if (intentoNum < 1000) {
                intentoStr = "0"+intentoNum;
            } else if (intentoNum > 9876) {
            // si al sumar uno al intento, se pasó del número máximo, debe iniciar
            //desde el primer número válido que es 0123
                intentoNum = 0123;
                intentoStr = "0123";
            } else {
            // simple conversión de int a String
                intentoStr = ""+intentoNum;
            }

            // Si no es válido, repite el bucle
            if (esValido(intentoStr)) {
                boolean coincide = true;
                
                // hay que comparar el intento actual con todos los intentos previos
                for (Intento att : intentos) {
                    intentoNuevo = compararNumero(intentoStr, att.getNumero());

                    // Si el nuevo intento no tiene la misma cantidad de "bien"
                    //y "regular" que el comparado, no coincide y repite el bucle while
                    if (!(intentoNuevo.getBien() == att.getBien() && 
                        intentoNuevo.getRegular() == att.getRegular())) {
                        coincide = false;
                        break;
                    }
                }
                
                if (coincide) {
                    // Pregunta por el intento
                    System.out.println("¿El número es "+intentoStr+"?");
                    System.out.println("Números bien:");
                    while (!AdivinadorJuego.in.hasNextInt(5) || !AdivinadorJuego.in.hasNext("\\d")) {
                        System.out.println("Ingrese un número de 0 a 5:");
                        AdivinadorJuego.in.next();
                    }
                    int bien = AdivinadorJuego.in.nextInt();
                    System.out.println("Números regular:");
                    while (!AdivinadorJuego.in.hasNextInt(5) || !AdivinadorJuego.in.hasNext("\\d")) {
                        System.out.println("Ingrese un número de 0 a 5:");
                        AdivinadorJuego.in.next();
                    }
                    int regular = AdivinadorJuego.in.nextInt();
                    
                    // si hay 4 bien, se encontró la respuesta, fin del juego
                    if (bien == 4) {
                        System.out.println("La respuesta es "+intentoStr);
                        return true;
                    // si no se encontró, se agrega el intento para hacer las
                    //comparaciones en las próximas iteraciones del bucle
                    } else {
                        intentos.add(new Intento (intentoStr, bien, regular));
                    }
                }
            }
        }
        
        
        return false;
    }
    
    /**
     * Verifica si el número cumple las condiciones. Si tiene 4 dígitos y si
     * los caracteres son diferentes.
     * @param numero número a validar
     * @return verdadero si es válido, falso si no lo es
     */
    public boolean esValido(String numero) {
        // 4 digitos
        if (!numero.matches("\\d\\d\\d\\d")) return false;
        
        // cada caracter es distinto
        char charArray[] = numero.toCharArray();
        Arrays.sort(charArray);

        for (int i = 0; i < charArray.length - 1; i++) {
            // Comparo cada caracter con el siguiente. Como están ordenados,
            // si hay caracteres iguales estarán juntos.
            if (charArray[i] == charArray[i+1]) return false;
        }
        
        return true;
    }

    /**
     * Compara dos números y obtiene las coincidencias de dígitos bien y
     * regular.
     * @param intentoNuevo el número nuevo
     * @param intentoViejo el número viejo, contra el que se va a comparar
     * @return Intento con el número nuevo y la cantidad de "Bien" y "Regular"
     */
    public Intento compararNumero(String intentoNuevo, String intentoViejo) {
        int bien = 0;
        int regular = 0;
        
        for (int i = 0; i<4; i++) {
            if (intentoViejo.charAt(i) == intentoNuevo.charAt(i)) {
                bien++;
            } else {
                for (int j = 0; j<4; j++) {
                    if (i==j) continue;
                    if (intentoViejo.charAt(i) == intentoNuevo.charAt(j)) {
                        regular++;
                    }
                }
            }
        }
        return new Intento(intentoNuevo, bien, regular);
    }
}
