package com.evaluaciones.microservicioEvaluaciones.Model;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "EVALUACION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evaluacion {
   @Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evaluacion_seq")
@SequenceGenerator(name = "evaluacion_seq", sequenceName = "EVALUACION_SEQ", allocationSize = 1)
private Long id;
    
    @Column(name = "TITULO", length = 100, nullable = false)
    private String titulo;

   @Column(name = "DESCRIPCION", length = 100, nullable = false)
    private String descripcion;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;

    @Column(name = "CURSO_ID", nullable = false)
    private Long cursoId; // Id del curso relacionado

 // Getters
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Long getCursoId() {
        return cursoId;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }
}


