package com.example.hamburguesasalcarbon.factory;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.FollowComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.example.hamburguesasalcarbon.factory.type.Type;

public class Factory implements EntityFactory {
    @Spawns("usuario")
    public Entity newClient(SpawnData data){
        FollowComponent followComponent = new FollowComponent();
        followComponent.setSpeed(1);
        return FXGL.entityBuilder(data)
                .type(Type.USUARIO)
                .view("cliente.png")
                .with(followComponent)
                .buildAndAttach();
    }

    @Spawns("mesero")
    public Entity newWaiter(SpawnData data){
        FollowComponent followComponent = new FollowComponent();
        followComponent.setSpeed(1);
        return FXGL.entityBuilder(data)
                .type(Type.MESERO)
                .view("mesera.png")
                .with(followComponent)
                .buildAndAttach();
    }

    @Spawns("hamburguesa")
    public Entity newPizza(SpawnData data){
        FollowComponent followComponent = new FollowComponent();
        followComponent.setSpeed(1);
        return FXGL.entityBuilder(data)
                .type(Type.HAMBURGUESA)
                .view("hamburguesa.png")
                .with(followComponent)
                .buildAndAttach();
    }
}
