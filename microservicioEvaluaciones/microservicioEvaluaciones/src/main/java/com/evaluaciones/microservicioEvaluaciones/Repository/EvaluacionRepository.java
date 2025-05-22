package com.evaluaciones.microservicioEvaluaciones.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evaluaciones.microservicioEvaluaciones.Model.Evaluacion;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {

}
