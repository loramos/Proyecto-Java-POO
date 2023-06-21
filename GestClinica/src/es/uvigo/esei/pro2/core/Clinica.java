/*  Definición de la clase Clinica
 *  En una clínica tendremos una serie de pacientes
 */
package es.uvigo.esei.pro2.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nrufino
 */
public class Clinica {

    /**
     * Excepciones *
     */
    public static class ClinicaException extends Exception {

        public ClinicaException(String msg) {
            super(msg);
        }
    }

    static class PosicionInexistenteClinicaException extends ClinicaException {

        public PosicionInexistenteClinicaException(String msg) {
            super(msg);
        }

    }

    static class ExistenteCitaException extends ClinicaException {

        public ExistenteCitaException(String msg) {
            super(msg);
        }

    }

    public static class ExistePacienteException extends ClinicaException {

        public ExistePacienteException(String msg) {
            super(msg);
        }

    }

    public static class ExisteMedicoException extends ClinicaException {

        public ExisteMedicoException(String msg) {
            super(msg);
        }

    }
    
    public static class ExisteEnfermeroException extends ClinicaException {
        
        public ExisteEnfermeroException(String msg){
            super(msg);
        }
    }

    /* ---------------------------------------------------- */
    private ArrayList<Paciente> pacientes;

    private ArrayList<Medico> medicos;

    private ArrayList<CitaMedica> citas;

    private ArrayList<Enfermero> enfermeros; //Lista de enfermeros
    
    private ArrayList<Par<Medico,Enfermero>> pares;
    
    private String nombreClinica;

    /**
     * Nueva Clinica con un num. max. de pacientes, de médicos y citas.
     *
     * @param nombre el nombre de la clínica, como cadena.
     * @param maxPacientes el num. max. de pacientes, como entero.
     * @param maxMedicos el num. max. de médicos, como entero.
     * @param maxEnfermeros el número máximo de enfermeros, como entero.
     * @param maxCitas el num. max. de citas, como entero.
     */
    public Clinica(String nombre, int maxPacientes, int maxMedicos,
            int maxCitas, int maxEnfermeros) {
        this.nombreClinica = nombre;
        pacientes = new ArrayList<>(maxPacientes);
        medicos = new ArrayList<>(maxMedicos);
        citas = new ArrayList<>(maxCitas);
        enfermeros = new ArrayList<>(maxEnfermeros);
        pares= new ArrayList<>();
    }

    /* ------------------------------------------------------------------ */
    /**
     * METODOS RELACIONADOS CON EL VECTOR PACIENTES *
     */
    /**
     * Devuelve el paciente situado en pos
     *
     * @param pos el lugar del paciente en el vector de pacientes
     * @return el objeto Paciente correspondiente.
     */
    public Paciente getPaciente(int pos)
            throws PosicionInexistenteClinicaException {
        if ((pos >= getNumPacientes()) || (pos < 0)) {
            throw new PosicionInexistenteClinicaException("getPaciente(): "
                    + "no existe ningun paciente en esa posicion: "
                    + (pos + 1) + " / " + getNumPacientes());
        }

        return pacientes.get(pos);
    }

    public Paciente existePaciente(String numHis, Paciente p) {
        int i = 0;

        while ((i < getNumPacientes())
                && (!(pacientes.get(i).getNumHistorial().equals(numHis)))) {
            i++;
        }
        if (i == getNumPacientes()) {
            return p;
        } else {
            return pacientes.get(i);
        }
    }

    /**
     * Devuelve el num. de pacientes creados.
     *
     * @return el num. de pacientes existentes, como entero.
     */
    public int getNumPacientes() {
        return pacientes.size();
    }

    /**
     * Inserta un nuevo paciente
     *
     * @param p el nuevo objeto Paciente
     */
    public void insertaPaciente(Paciente p) throws ExistePacienteException {

        if (existeHistorial(p.getNumHistorial())) {
            throw new ExistePacienteException("insertaPaciente(): existe un "
                    + "paciente con ese numero de historial: " + p.getNumHistorial());
        }
        pacientes.add(p);
    }

    public boolean existeHistorial(String historial) {
        int i = 0;

        while ((i < getNumPacientes())
                && (!pacientes.get(i).getNumHistorial().equals(historial))) {
            i++;
        }
        return (i != getNumPacientes());
    }

    public void eliminaPaciente(int pos)
            throws PosicionInexistenteClinicaException, ExistenteCitaException {
        if ((pos >= getNumPacientes()) || (pos < 0)) {
            throw new PosicionInexistenteClinicaException("eliminaPaciente(): "
                    + "no existe ningun paciente en esa posicion: "
                    + (pos + 1) + " / " + getNumPacientes());
        }

        // Comprobar que no tenga cita medica
        if (tienePacienteCita(pacientes.get(pos))) {
            throw new ExistenteCitaException("eliminaPaciente(): no se puede"
                    + " eliminar ese paciente pues tiene citas médicas.");
        }
        pacientes.remove(pos);
    }

    public String toStringPacientes() {
        StringBuilder toret;
        final int numPacientes = getNumPacientes();

        toret = new StringBuilder();
        if (numPacientes > 0) {
            for (int i = 0; i < numPacientes; i++) {
                toret.append((i + 1)).append(". ");
                toret.append(pacientes.get(i).toString()).append("\n");
            }
        } else {
            toret.append("No hay pacientes.");
        }

        return toret.toString();
    }

    public String listarPorTipoPaciente(char t) throws PosicionInexistenteClinicaException {
        final int numPacientes = getNumPacientes();
        StringBuilder toret = new StringBuilder();
        Paciente p;

        if (numPacientes > 0) {
            for (int i = 0; i < numPacientes; i++) {
                p = getPaciente(i);
                switch (t) {
                    case 'P':
                        if (p instanceof Privado) {
                            toret.append(p.toString() + "\n");
                        }
                        break;
                    case 'A':
                        if (p instanceof Asegurado) {
                            toret.append(p.toString() + "\n");
                        }
                        break;
                }
            }
        } else {
            toret.append("No hay pacientes.");
        }

        if (toret.length() == 0) {
            toret.append("No hay pacientes de ese tipo.");
        }

        return toret.toString();
    }

    /* ------------------------------------------------------------------ */
    /**
     * METODOS RELACIONADOS CON EL VECTOR MEDICOS *
     */
    /**
     * Devuelve el medico situado en pos
     *
     * @param pos el lugar del medico en el vector de medicos
     * @return el objeto Medico correspondiente.
     */
    public Medico getMedico(int pos) throws PosicionInexistenteClinicaException {
        if (pos >= getNumMedicos() || pos < 0) {
            throw new PosicionInexistenteClinicaException("getMedico(): "
                    + "no existe ningun medico en esa posicion: "
                    + (pos + 1) + " / " + getNumMedicos());
        }

        return medicos.get(pos);
    }
    
     /**
     * Devuelve el Medico con el numero de colegiado
     *
     * @param numColeg el numero de colegiado del medico
     * @return el objeto Medico correspondiente.
     */
    public Medico getMedico(String numColeg)
            throws ExisteMedicoException {

        int indice = 0;

        while ((indice < getNumMedicos())
                && (!medicos.get(indice).getNumColegiado().equals(numColeg))) {
            indice++;
        }
        if (indice == getNumMedicos()) {
            throw new ExisteMedicoException("getMedico(): "
                    + "no existe ningun medico con ese numero de "
                    + "colegiado: " + numColeg);
        }

        return medicos.get(indice);
    }

    public Medico existeMedico(String numCol, Medico m) {
        int i = 0;
        while ((i < getNumMedicos())
                && (!(medicos.get(i).getNumColegiado().equals(numCol)))) {
            i++;
        }
        if (i == getNumMedicos()) {
            return m;
        } else {
            return medicos.get(i);
        }
    }

    /**
     * Devuelve el num. de medicos creados.
     *
     * @return el num. de medicos existentes, como entero.
     */
    public int getNumMedicos() {
        return medicos.size();
    }

    /**
     * Inserta un nuevo medico
     *
     * @param p el nuevo objeto Medico
     */
    public void insertaMedico(Medico m)
            throws ExisteMedicoException {

        if (existeNumeroColegiado(m.getNumColegiado())) {
            throw new ExisteMedicoException("insertaMedico(): existe un "
                    + "medico con ese numero de colegiado: " + m.getNumColegiado());
        }
        medicos.add(m);
    }

    public boolean existeNumeroColegiado(String numeroColegiado) {
        int i = 0;

        while ((i < getNumMedicos())
                && (!medicos.get(i).getNumColegiado().equals(numeroColegiado))) {
            i++;
        }
        return (i != getNumMedicos());
    }
    
    /**
     * Elimina el medico situado en la posicion indicada.
     *
     * @param pos el lugar del medico en el vector de medicos
     */
    public void eliminaMedico(int pos)
            throws PosicionInexistenteClinicaException, ExistenteCitaException {
        if ((pos >= getNumMedicos()) || (pos < 0)) {
            throw new PosicionInexistenteClinicaException("eliminaMedico(): "
                    + "no existe ningun medico en esa posicion: "
                    + (pos + 1) + " / " + getNumMedicos());
        }
        // Se comprueba que no intervenga en una cita medica para poder eliminarlo
        if (tieneMedicoCita(medicos.get(pos))) {
            throw new ExistenteCitaException("eliminaMedico(): no se puede "
                    + "eliminar ese médico pues tiene citas médicas.");
        }
        medicos.remove(pos);
    }

    public String toStringMedicos() {
        StringBuilder toret;
        final int numMedicos = getNumMedicos();

        toret = new StringBuilder();
        if (numMedicos > 0) {
            toret.append("Los médicos de la clinica son: \n");
            for (int i = 0; i < numMedicos; i++) {
                toret.append((i + 1)).append(". ");
                toret.append(medicos.get(i).toString()).append("\n");
            }
        } else {
            toret.append("No hay médicos.");
        }

        return toret.toString();
    }

     /* ----------------------------------------------------------------------------- */
    /**
     * METODOS RELACIONADOS CON EL VECTOR DE ENFERMEROS 
     */
    /* ----------------------------------------------------------------------------- */
    
    /**
     * Devuelve el numero de enfermeros creados.
     *
     * @return entero indicando el número de enfermeros creados.
     */
    public int getNumEnfermeros() {
        return enfermeros.size();
    } 
    
    /**
     * Devuelve el enfermero de la posición que recibe como parámetro
     *
     * @param pos el lugar del enfermero en el vector de enfermeros
     * @return el objeto Enfermero de la posición dada
     */
    public Enfermero getEnfermero(int pos) throws PosicionInexistenteClinicaException {
        if (pos >= getNumEnfermeros() || pos < 0) {
            throw new PosicionInexistenteClinicaException("getEnfermero(): "
                    + "no existe ningun enfermero en esa posicion: "
                    + (pos + 1) + " / " + getNumEnfermeros());
        }

        return enfermeros.get(pos);
    }
    
    
    /**
     * Comprueba si existe el número de identificación de un enfermero 
     * en la clínica
     * @param numIdentificación del enfermero
     * @return True si existe y False en caso contrario
     */
    public boolean existeNumIdentificacion(String numIdentificacion){
        int i=0;
        while((i<getNumEnfermeros()) &&
                (!enfermeros.get(i).getNumIdentificacion().equals(numIdentificacion))){
            i++;
        }
        return (i!=getNumEnfermeros());
    }   
    
    /**
     * Inserta un nuevo enfermero
     * @param e el enfermero a insertar
     */
    public void insertaEnfermero(Enfermero e)
            throws ExisteEnfermeroException {

        if (existeNumIdentificacion(e.getNumIdentificacion())) {
            throw new ExisteEnfermeroException("insertaEnfermero(): existe un "
                    + "enfermero con ese numero de identificacion: " + e.getNumIdentificacion());
        }
        enfermeros.add(e);
    }
    
    /**
     * Devuelve los enfermeros que hay ahora en la clínica
     * @return String con los enfermeros de la clínica
     */
    public String toStringEnfermeros() {
        StringBuilder toret;
        final int numEnfermeros = getNumEnfermeros();

        toret = new StringBuilder();
        if (numEnfermeros > 0) {
            toret.append("Los enfermeros de la clinica son: \n");
            for (int i = 0; i < numEnfermeros; i++) {
                toret.append((i + 1)).append(". ");
                toret.append(enfermeros.get(i).toString()).append("\n");
            }
        } else {
            toret.append("No hay enfermeros en la clínica.");
        }

        return toret.toString();
    }
    
    /**
     * Elimina el enfermero de la posicion recibida como parámetro.
     * @param pos la posición del enfermero en la colección
     */
    public void eliminaEnfermero(int pos)
            throws PosicionInexistenteClinicaException{
        if ((pos >= getNumEnfermeros()) || (pos < 0)) {
            throw new PosicionInexistenteClinicaException("eliminaEnfermero(): "
                    + "no existe ningun enfermero en esa posicion: "
                    + (pos + 1) + " / " + getNumEnfermeros());
        }
        //Se elimina el enfermero   
        enfermeros.remove(pos);
    }
    
    /**
     * Relaciona un enfermero con un médico
     * @param posE Posición del enfermero
     * @param posM Posición del médico
     * @throws es.uvigo.esei.pro2.core.Clinica.PosicionInexistenteClinicaException 
     */
    public void relacionaEnfermero(int posE, int posM)
            throws PosicionInexistenteClinicaException{
        if ((posE >= getNumEnfermeros()) || (posE < 0)) {
            throw new PosicionInexistenteClinicaException("relacionaEnfermero(): "
                    + "no existe ningun enfermero en esa posicion: "
                    + (posE + 1) + " / " + getNumEnfermeros());
        }
        if ((posM >= getNumMedicos()) || (posM < 0)) {
            throw new PosicionInexistenteClinicaException("relacionaEnfermero(): "
                    + "no existe ningun medico en esa posicion: "
                    + (posM + 1) + " / " + getNumMedicos());
        }       
        
        //Se añade el par nuevo
        Par p=new Par(enfermeros.get(posE),medicos.get(posM));
        pares.add(p);
    }
    
    /**
     * Devuelve las relaciones entre enfermeros y médicos existentes
     * @return Cadena con los pares o las relaciones
     */
    public String toStringPares(){
    
        StringBuilder toret;
        final int numPares = pares.size();

        toret = new StringBuilder();
        if (numPares > 0) {
            toret.append("Las relaciones de la clinica son: \n");
            for (int i = 0; i < numPares; i++) {
                toret.append((i + 1)).append(". ");
                toret.append(pares.get(i).toString()).append("\n");
            }
        } else {
            toret.append("No hay relaciones.");
        }

        return toret.toString();
    }
    
    /**
    * Obtiene lista de nombres de enfermeros de una determinada área
    * @param a El área médica
    * @return Lista de nombres de enfermeros (cadenas)
    */
    public List<String> obtenerPorArea(AreasMedicas a){
        List<String> lista=new ArrayList<>();
        for (Enfermero e: enfermeros){
            if (e.getAreaMedica()==a){
                lista.add(e.getNombre());
            }
        }
        return lista;
    }
    
    /**
     * Utilizando un HashMap, lista los enfermeros de la clínica, pero 
     * organizados por área asignada.
     */
    public void listarPorArea(){
        //Lista auxiliar
        List<String> aux = new ArrayList<>();
        
        //HashMap, cada clave (área) ha de corresponderse con un valor. En este 
        //caso, el valor va a ser la lista de los nombres de los enfermeros de cada área
        HashMap <AreasMedicas, List<String>> eMap; 
        eMap= new HashMap<>(); 
        
        if(enfermeros.isEmpty()){
            System.out.println("No hay enfermeros");
        }
        else{
           //Rellenar HashMap utilizando la función creada anteriormente
           eMap.put(AreasMedicas.pediatria,obtenerPorArea(AreasMedicas.pediatria));
           eMap.put(AreasMedicas.UCI,obtenerPorArea(AreasMedicas.UCI));
           eMap.put(AreasMedicas.urgencias,obtenerPorArea(AreasMedicas.urgencias));
              
            
            //Mostrar enfermeros por área médica
            for(AreasMedicas a: eMap.keySet()){
                System.out.println("Area: "+a.toString().toUpperCase());
                System.out.println("     Enfermeros:");
                aux.clear();
                aux=eMap.get(a);
                for (String s: aux){
                    System.out.println("          "+s);
                }
            }
        }
    }
            
            
    /* ------------------------------------------------------------------ */
    /**
     * METODOS RELACIONADOS CON EL VECTOR CITAS *
     */
    /* ------------------------------------------------------------------ */
    /**
     * Devuelve la cita médica situado en pos
     *
     * @param pos el lugar de la cita médica en el vector de citas
     * @return el objeto CitaMedica correspondiente.
     */
    public CitaMedica getCita(int pos) throws PosicionInexistenteClinicaException {
        if (pos >= getNumCitas() || pos < 0) {
            throw new PosicionInexistenteClinicaException("getCita(): "
                    + "no existe ninguna cita en esa posicion: "
                    + (pos + 1) + " / " + getNumCitas());
        }

        return citas.get(pos);
    }

    /**
     * Devuelve el num. de citas médicas creadas.
     *
     * @return el num. de citas médicas existentes, como entero.
     */
    public int getNumCitas() {
        return citas.size();
    }

    /**
     * Inserta una nueva cita medica
     *
     * @param c el nuevo objeto CitaMedica
     */
    public void insertaCita(CitaMedica c) {

        citas.add(c);
    }

    /**
     * Elimina la cita medica situado en la posicion indicada.
     *
     * @param pos el lugar de la cita medica en el vector de citas
     */
    public void eliminaCita(int pos) throws PosicionInexistenteClinicaException {
        if ((pos >= getNumCitas()) || (pos < 0)) {
            throw new PosicionInexistenteClinicaException("eliminaCita(): "
                    + "no existe ninguna cita medica en esa posicion: "
                    + (pos + 1) + " / " + getNumCitas());
        }
        citas.remove(pos);
    }

    public String toStringCitas() {
        StringBuilder toret;
        final int numCitas = getNumCitas();

        toret = new StringBuilder();
        if (numCitas > 0) {
            toret.append("Las citas médicas de la clinica son: \n");
            for (int i = 0; i < numCitas; i++) {
                toret.append((i + 1)).append(". ");
                toret.append(citas.get(i).toString()).append("\n");
            }
        } else {
            toret.append("No hay citas médicas.");
        }

        return toret.toString();
    }
    
    /* METODOS PRIVADOS */
// PACIENTES
    private boolean tienePacienteCita(Paciente p) //            throws ExistenteCitaException
    {
        int i = 0;
        while ((i < getNumCitas()) && (!citas.get(i).getPaciente().equals(p))) {
            i++;
        }
//
//        if (i != getNumCitas()){
//             throw new ExistenteCitaException ("Ese paciente tiene citas medicas");
//        }
        return (i != getNumCitas());
    }

// MEDICOS

    /* Devuelve true si el médico tiene alguna cita médica.
     *
     * @return boolean si el médico tiene o no alguna cita médica.
     */
    private boolean tieneMedicoCita(Medico m) {
        int i = 0;
        while ((i < getNumCitas()) && (!(citas.get(i).getMedico().equals(m)))) {
            i++;
        }
        return (i != getNumCitas());
    }

}
