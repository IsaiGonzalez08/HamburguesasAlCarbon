package com.example.hamburguesasalcarbon.Controllers;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.example.hamburguesasalcarbon.Threads.Hilo;
import com.example.hamburguesasalcarbon.factory.Factory;
import com.example.hamburguesasalcarbon.models.MonitorMesero;
import com.example.hamburguesasalcarbon.models.MonitorRecepcionista;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
        private MonitorMesero monitorMesero;
        private MonitorRecepcionista monitorRecepcionista;
        private Hilo threads;
        private static int contadorClientes = 0;
        private static int contadorMeseros = 0;

        List<Entity> listameseros = new ArrayList<>();
        List<Point2D> posicionInicialMesero = new ArrayList<>();

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            FXGL.getGameWorld().addEntityFactory(new Factory());
            for (int i = 0; i < 7; i++) {
                int index = i;
                int numMeseros = contadorMeseros++;
                Entity mesero = FXGL.spawn("mesero", 200 + (1 * 10), 100 + (index * 40));
                listameseros.add(mesero);
                posicionInicialMesero.add(new Point2D(mesero.getX(), mesero.getY()));
            }

            for (int i = 0; i < 30; i++) {
                int index = i;
                int numClient = contadorClientes++;
                double delay = 5 + Math.random() * 1;
                FXGL.getGameTimer().runOnceAfter(() -> {
                    Entity usuario = FXGL.spawn("usuario", 1 + (1 * 10), (1 * 3));
                    Entity hamburguesa = FXGL.spawn("hamburguesa", 1 + (1 * 10), (1 * 3));
                    threads = new Hilo(monitorRecepcionista, monitorMesero,usuario, numClient, listameseros, posicionInicialMesero, hamburguesa);
                    System.out.println(usuario + " llegó al restaurante");
                    new Thread(threads).start();
                }, Duration.seconds(delay + i));
            }
        }
}