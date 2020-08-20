package com.petertacon.bikecontrol;

/*** Clase usada para consulta y escritura de base de datos ***/

public class SkyRed {
    //Declarción de atributos
    public String driver;
    public String plate;
    public String password;

    //Creación de método
    public SkyRed(String driver, String plate, String password) {
        this.driver = driver;
        this.plate = plate;
        this.password = password;
    }
}
