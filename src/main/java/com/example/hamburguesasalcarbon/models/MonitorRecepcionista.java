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

    public synchronized void llegarCliente(HiloUsuario cliente) {
        System.out.println("Cliente " + cliente.getId() + " llegó al restaurante.");

        if (mesasOcupadas == capacidadMesas) {
            System.out.println("Restaurante lleno. Cliente " + cliente.getId() + " en cola de espera.");
            colaUsuarios.add(cliente);
            while (restauranteLleno || colaUsuarios.peek() != cliente) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Cliente " + cliente.getId() + " sale de la cola de espera");
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

        asignarMesas.put(cliente, mesaAsignada);

        if (mesasOcupadas == capacidadMesas) {
            restauranteLleno = true;
        }

        System.out.println("Recepcionista asignó al Cliente " + cliente.getId() + " a la mesa " + mesaAsignada);

        notifyAll();

    }

    public synchronized void abandonarRestaurante(HiloUsuario usuario, Entity usuarios, Entity hamburguesas) {
        mesasOcupadas--;

        if (mesasOcupadas < capacidadMesas) {
            restauranteLleno = false;
            notifyAll();
        }

        asignarMesas.remove(usuario);
        Point2D posicionLiberada = usuario.getPosicionDada();
        List<Point2D> posiciones = usuario.getPosiciones();

        // Realiza cualquier lógica necesaria para liberar la posición, por ejemplo:
        posiciones.add(posicionLiberada);
        System.out.println("Cliente " + usuario.getId() + " abandonó el restaurante.");
        usuarios.setVisible(false);
        hamburguesas.setVisible(false);
    }

    public synchronized void entregarPizza(HiloUsuario cliente) {
        System.out.println("Pizza entregada al Cliente " + cliente.getId());

        notify();

    }
}
