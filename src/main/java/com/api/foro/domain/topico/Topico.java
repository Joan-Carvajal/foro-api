package com.api.foro.domain.topico;

import com.api.foro.controller.DatosActualizarTopico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@AllArgsConstructor

@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String titulo;
    private  String mensaje;
    private LocalDateTime fecha;
    private  Boolean estado;



    public Topico(DatosRegistroTopico datosRegistroTopico){
        this.titulo= datosRegistroTopico.titulo();
        this.mensaje= datosRegistroTopico.mensaje();
        this.fecha = LocalDateTime.now();
        this.estado =  true;
    }
    public Topico(){};
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void actualizarTopico(DatosActualizarTopico datosActualizarTopico) {
        if (datosActualizarTopico.titulo()!=null){
            this.titulo= datosActualizarTopico.titulo();
        }
        if (datosActualizarTopico.mensaje()!= null){
            this.mensaje = datosActualizarTopico.mensaje();
        }
        if (datosActualizarTopico.estado()!=null){
            this.estado = datosActualizarTopico.estado();
        }
    }
}
