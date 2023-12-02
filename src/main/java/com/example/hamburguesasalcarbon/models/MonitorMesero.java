package com.example.hamburguesasalcarbon.models;

import com.example.hamburguesasalcarbon.Threads.HiloUsuario;

import java.util.LinkedList;
import java.util.Queue;

public class MonitorMesero {
    private int meserosDesocupados;
    private Queue<HiloUsuario> colaOrdenes;

    public MonitorMesero(int cantidadMeseros) {
        this.meserosDesocupados = cantidadMeseros;
        this.colaOrdenes = new LinkedList<>();
    }

    public synchronized void tomarPedido(HiloUsuario cliente) {

        if (meserosDesocupados == 0) {
            System.out.println("No hay meseros disponibles. Cliente " + cliente.getId() + " en cola de pedidos.");
            colaOrdenes.add(cliente);
            while (meserosDesocupados == 0 || colaOrdenes.peek() != cliente) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Cliente " + cliente.getId() + " sale de la cola de pedidos.");
            colaOrdenes.remove();
        }

        meserosDesocupados--;
        System.out.println("Pedido del Cliente " + cliente.getId() + " atendido por un mesero.");
        meserosDesocupados++;
        notifyAll();
    }

    public synchronized void desocuparMesero(HiloUsuario hiloUsuario) {
        if (meserosDesocupados < 7) {
            meserosDesocupados++;
        }
        if (colaOrdenes.size() > 0) {
            notify();
        }
    }
}
