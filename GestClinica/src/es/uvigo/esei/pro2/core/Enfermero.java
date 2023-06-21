/*
 * Definición de la clase Enfermero
 */
package es.uvigo.esei.pro2.core;

/**
 * 
 * @author 
 */
public class Enfermero extends Persona{
    private String numIdentificacion;
    private Fecha fechaIncorporacion;
    private AreasMedicas areaMedica;
            
    /**
     * Crea un nuevo enfermero, con su número de identificación, su fecha de 
     * incorporación, su área médica y su nombre completo y domicilio
     * @param nombre
     * @param domicilio 
     */
    public Enfermero(String numIdentificacion, Fecha fechaIncorporacion, AreasMedicas areaMedica,
            String nombre, String domicilio) {
        super(nombre, domicilio);
        this.setNumIdentificacion(numIdentificacion);
        this.setFechaIncorporacion(fechaIncorporacion);
        this.setAreaMedica(areaMedica);
    }

    /**
     * Devuelve el número de identificación del enfermero
     * @return el numIdentificacion, como String
     */
    public String getNumIdentificacion() {
        return numIdentificacion;
    }

    /**
     * Devuelve la fecha de incorporación del enfermero
     * @return la fechaIncorporacion del enfermero, como Fecha
     */
    public Fecha getFechaIncorporacion() {
        return fechaIncorporacion;
    }

    /**
     * Devuelve el área médica del enfermero
     * @return el areaMedica como AreasMedicas
     */
    public AreasMedicas getAreaMedica() {
        return areaMedica;
    }

    /**
     * Cambia el número de identificación del enfermero
     * @param numIdentificacion el número de identificación del enfermero
     */
    public void setNumIdentificacion(String numIdentificacion) {
        this.numIdentificacion = numIdentificacion;
    }

    /**
     * Cambia la fecha de incorporación del enfermero
     * @param fechaIncorporacion la fecha de incorporación
     */
    public void setFechaIncorporacion(Fecha fechaIncorporacion) {
        this.fechaIncorporacion = fechaIncorporacion;
    }

    /**
     * Cambia el área médica del enfermero
     * @param areaMedica el área médica
     */
    public void setAreaMedica(AreasMedicas areaMedica) {
        this.areaMedica = areaMedica;
    }

    @Override
    public String toString()
    {
        StringBuilder toret = new StringBuilder();
        
        toret.append(super.toString());
        toret.append(getNumIdentificacion()).append(" ; ");
        toret.append(getFechaIncorporacion().toString()).append(" ; ");
        toret.append(getAreaMedica().toString()).append(" ; ");
             
        return toret.toString();
    }    
    
    
}
