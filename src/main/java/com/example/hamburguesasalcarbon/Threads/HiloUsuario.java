package com.example.hamburguesasalcarbon.Threads;

import com.almasb.fxgl.entity.Entity;
import com.example.hamburguesasalcarbon.models.MonitorMesero;
import com.example.hamburguesasalcarbon.models.MonitorRecepcionista;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class HiloUsuario extends Thread {
    private static int contadorUsuarios = 0;
    private MonitorMesero monitorMesero;
    private MonitorRecepcionista monitorRecepcionista;

    private Entity usuarios;
    private Entity hamburguesa;
    private List<Entity> meserosLista;


    List<Point2D> posiciones = new ArrayList<>();
    private Point2D posicionDada;

    List<Point2D> posicionMeseros = new ArrayList<>();
    Entity meseroAsignado;


    public HiloUsuario(MonitorMesero monitorMesero, MonitorRecepcionista monitorRecepcionista, Entity usuarios, int numCliente, List<Entity> meserosLista, List<Point2D> posicionMeseros, Entity hamburguesa) {
        this.monitorMesero = monitorMesero;
        this.setName("Cliente-" + contadorUsuarios++);
    }


    public void entradaRestaurante(){
        System.out.println("El Cliente " + getId() + " ha entrado al restaurante y su mesa.");
    }

    public void pedir() {
        System.out.println("El Cliente " + getId() + " ha pedido una hamburguesa al carbón.");
    }

    public void esperar(){
        System.out.println("El Cliente " + getId() + " esperando su pedido.");
    }

    public void comerHamburguesa(){
        System.out.println("El Cliente " + getId() + " esta comiendo su hamburguesa al carbón.");
    }

    @Override
    public void run() {
        entradaRestaurante();
        pedir();
        monitorMesero.tomarPedido(this);
        esperar();
        comerHamburguesa();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Point2D getPosicionDada() {
        return posicionDada;
    }

    public List<Point2D> getPosiciones() {
        return posiciones;
    }
}
