package app.rinno.com.devicewall.model;

import java.util.List;

/**
 * Created by Dev21 on 11-12-16.
 */

public class Dispositivo {
    private String name;
    private String ip;
    private String ipEscalador;
    private String numPantalla;
    private String output;
    private String reproductor;
    private List<Beacon> listBeacons;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpEscalador() {
        return ipEscalador;
    }

    public void setIpEscalador(String ipEscalador) {
        this.ipEscalador = ipEscalador;
    }

    public String getNumPantalla() {
        return numPantalla;
    }

    public void setNumPantalla(String numPantalla) {
        this.numPantalla = numPantalla;
    }

    public List<Beacon> getListBeacons() {
        return listBeacons;
    }

    public void setListBeacons(List<Beacon> listBeacons) {
        this.listBeacons = listBeacons;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getReproductor() {
        return reproductor;
    }

    public void setReproductor(String reproductor) {
        this.reproductor = reproductor;
    }
}
