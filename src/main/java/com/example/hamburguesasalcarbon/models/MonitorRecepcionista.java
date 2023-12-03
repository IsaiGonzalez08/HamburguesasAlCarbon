package com.example.hamburguesasalcarbon.models;

import com.almasb.fxgl.entity.Entity;
import com.example.hamburguesasalcarbon.Threads.HiloUsuario;
import javafx.geometry.Point2D;

import java.util.*;


public class MonitorRecepcionista {
    private boolean restauranteLleno;
    private int mesasOcupadas;
    private final int capacidadMesas;
    private Queue<HiloUsuario> colaUsuarios;
    private Map<HiloUsuario, Integer> asignarMesas;
    private MonitorMesero monitorMesero;

    public MonitorRecepcionista(int capacidadMesas) {
        this.restauranteLleno = false;
        this.mesasOcupadas = 0;
        this.capacidadMesas = capacidadMesas;
        this.colaUsuarios = new LinkedList<>();
        this.asignarMesas = new HashMap<>();
    }

    public void setMonitorMesero(MonitorMesero monitorMesero) {
        this.monitorMesero = monitorMesero;
    }

    public synchronized void llegarCliente(HiloUsuario usuario) {
        System.out.println("Cliente " + usuario.getId() + " llegó al restaurante.");

        if (mesasOcupadas == capacidadMesas) {
            System.out.println("Restaurante lleno. Cliente " + usuario.getId() + " en cola de espera.");
            colaUsuarios.add(usuario);
            while (restauranteLleno || colaUsuarios.peek() != usuario) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Cliente " + usuario.getId() + " sale de la cola de espera");
            colaUsuarios.remove();
        }

        int mesaAsignada;
        Set<Integer> mesasDisponibles = new HashSet<>();
        for (int i = 1; i <= capacidadMesas; i++) {
            mesasDisponibles.add(i);
        }
        for (Integer mesaOcupada : asignarMesas.values()) {
            mesasDisponibles.remove(mesaOcupada);
        }

        if (!mesasDisponibles.isEmpty()) {
            mesaAsignada = mesasDisponibles.iterator().next();
        } else {
            mesasOcupadas++;
            mesaAsignada = mesasOcupadas;
        }

        asignarMesas.put(usuario, mesaAsignada);

        if (mesasOcupadas == capacidadMesas) {
            restauranteLleno = true;
        }

        System.out.println("Recepcionista asignó al Cliente " + usuario.getId() + " a la mesa " + mesaAsignada);

        notifyAll();

    }

    public synchronized void abandonarRestaurante(HiloUsuario cliente, Entity usuario, Entity hamburguesa) {
        mesasOcupadas--;

        if (mesasOcupadas < capacidadMesas) {
            restauranteLleno = false;
            notifyAll();
        }

        asignarMesas.remove(cliente);
        Point2D posicionLiberada = cliente.getPosicionDada();
        List<Point2D> posiciones = cliente.getPosiciones();

        // Realiza cualquier lógica necesaria para liberar la posición, por ejemplo:
        posiciones.add(posicionLiberada);
        System.out.println("Cliente " + cliente.getId() + " abandonó el restaurante.");
        usuario.setVisible(false);
        hamburguesa.setVisible(false);
    }

    public synchronized void entregarComida(HiloUsuario cliente) {
        System.out.println("Comida entregada al Cliente " + cliente.getId());
        notify();

    }
}
