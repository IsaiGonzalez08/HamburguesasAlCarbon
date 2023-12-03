package com.example.hamburguesasalcarbon.Threads;

import com.almasb.fxgl.entity.Entity;
import com.example.hamburguesasalcarbon.models.MonitorMesero;
import com.example.hamburguesasalcarbon.models.MonitorRecepcionista;
import javafx.geometry.Point2D;

import java.util.List;

public class Thread implements Runnable{
    private MonitorRecepcionista monitorRecepcionista;
    private MonitorMesero monitorMesero;
    private  Entity usuario;
    private  Entity hamburguesa;

    private int idUser;
    private List<Entity> meseroslista;

    private List<Point2D> posicionesMeseros;

    public Thread(MonitorRecepcionista monitorRecepcionista, MonitorMesero monitorMesero, Entity usuario, int idUser, List<Entity> meseroslista, List<Point2D> posicionInicialMesero, Entity hamburguesa) {
        this.monitorMesero = new MonitorMesero(7);
        this.monitorRecepcionista = new MonitorRecepcionista(20);
        this.usuario = usuario;
        this.idUser = idUser;
        this.hamburguesa = hamburguesa;
        this.meseroslista = meseroslista;
        this.posicionesMeseros = posicionInicialMesero;
    }



    @Override
    public void run() {
        monitorRecepcionista.setMonitorMesero(monitorMesero);
        while (true) {


            HiloUsuario cliente = new HiloUsuario(monitorRecepcionista, monitorMesero, usuario, idUser,  meseroslista, posicionesMeseros, hamburguesa);
            cliente.start();


            try {
                java.lang.Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
