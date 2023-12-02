package com.example.hamburguesasalcarbon.Threads;

import com.example.hamburguesasalcarbon.models.MonitorMesero;

public class HiloUsuario extends Thread {
    private static int contadorUsuario = 0;
    private MonitorMesero monitorMesero;

    public HiloUsuario(MonitorMesero monitorMesero) {
        this.monitorMesero = monitorMesero;
        this.setName("Cliente-" + contadorUsuario++);
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
}
