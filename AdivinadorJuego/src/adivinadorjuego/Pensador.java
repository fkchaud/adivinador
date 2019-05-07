package adivinadorjuego;

public class Pensador {
    
    private String numero;

    public Pensador(String numero) {
        validarNumero(numero);
        this.numero = numero;
    }

    public Pensador() {
        pensarNumero();
    }

    private void pensarNumero() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private void validarNumero(String numero) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
}
