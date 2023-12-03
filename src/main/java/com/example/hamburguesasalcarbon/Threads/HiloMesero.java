package com.example.hamburguesasalcarbon.Threads;

import com.example.hamburguesasalcarbon.models.MonitorMesero;

import java.util.Random;

public class HiloMesero extends Thread {
    private static int contadorMeseros = 0;
    private MonitorMesero monitorMesero;

    public HiloMesero(MonitorMesero monitorMesero) {
        this.monitorMesero = monitorMesero;
        this.setName("Mesero-" + contadorMeseros++);
    }

    @Override
    public void run() {
        while (true) {
            monitorMesero.desocuparMesero(this);
            try {
                Thread.sleep((new Random().nextInt(4) + 5) * 1000);;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
