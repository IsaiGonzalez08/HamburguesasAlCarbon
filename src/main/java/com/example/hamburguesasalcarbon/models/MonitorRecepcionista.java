package com.example.hamburguesasalcarbon.models;

import com.example.hamburguesasalcarbon.Threads.HiloUsuario;

import java.util.*;

public class MonitorRecepcionista {
    private boolean restauranteLleno;
    private int mesasOcupadas;
    private final int capacidadMesas;
    private Queue<HiloUsuario> colaClientes;
    private Map<HiloUsuario, Integer> asignacionMesas;
    private MonitorMesero monitorMesero;

    public MonitorRecepcionista(int capacidadMesas) {
        this.restauranteLleno = false;
        this.mesasOcupadas = 0;
        this.capacidadMesas = capacidadMesas;
        this.colaClientes = new LinkedList<>();
        this.asignacionMesas = new HashMap<>();
    }

    public void setMonitorMesero(MonitorMesero monitorMesero) {
        this.monitorMesero = monitorMesero;
    }

    public synchronized void llegarCliente(HiloUsuario usuario) {
        System.out.println("El Cliente " + usuario.getId() + " llegó al restaurante.");

        if (mesasOcupadas == capacidadMesas) {
            System.out.println("El Restaurante esta lleno. El Cliente " + usuario.getId() + "esta en cola de espera.");
            colaClientes.add(usuario);
            while (restauranteLleno || colaClientes.peek() != usuario) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("El Cliente " + usuario.getId() + " sale de la cola de espera");
            colaClientes.remove();
        }

        int mesaAsignada;
        Set<Integer> mesasDisponibles = new HashSet<>();
        for (int i = 1; i <= capacidadMesas; i++) {
            mesasDisponibles.add(i);
        }
        for (Integer mesaOcupada : asignacionMesas.values()) {
            mesasDisponibles.remove(mesaOcupada);
        }

        if (!mesasDisponibles.isEmpty()) {
            mesaAsignada = mesasDisponibles.iterator().next();
        } else {
            mesasOcupadas++;
            mesaAsignada = mesasOcupadas;
        }

        asignacionMesas.put(usuario, mesaAsignada);

        if (mesasOcupadas == capacidadMesas) {
            restauranteLleno = true;
        }

        System.out.println("El Recepcionista asignó al Cliente " + usuario.getId() + " a la mesa " + mesaAsignada);

        notifyAll();

    }

    public synchronized void abandonarRestaurante(HiloUsuario usuario) {
        mesasOcupadas--;

        if (mesasOcupadas < capacidadMesas) {
            restauranteLleno = false;
            notifyAll();
        }

        asignacionMesas.remove(usuario);

        System.out.println("Cliente " + usuario.getId()+ " abandonó el restaurante.");

    }

    public synchronized void entregarPizza(HiloUsuario usuario) {
        System.out.println("Pizza entregada al Cliente " + usuario.getId());
        notify();
    }
}
