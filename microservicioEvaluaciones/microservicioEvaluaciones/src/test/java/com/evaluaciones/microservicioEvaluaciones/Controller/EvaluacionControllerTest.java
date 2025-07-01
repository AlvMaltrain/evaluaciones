package com.evaluaciones.microservicioEvaluaciones.Controller;

import com.evaluaciones.microservicioEvaluaciones.Model.Evaluacion;
import com.evaluaciones.microservicioEvaluaciones.Service.EvaluacionService;
import com.evaluaciones.microservicioEvaluaciones.Controller.Evaluacion.EvaluacionController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EvaluacionController.class)
public class EvaluacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvaluacionService evaluacionService;

    @Test
    public void testObtenerTodasEvaluaciones() throws Exception {
        Evaluacion eval1 = new Evaluacion();
        Evaluacion eval2 = new Evaluacion();

        when(evaluacionService.obtenerTodas()).thenReturn(Arrays.asList(eval1, eval2));

        mockMvc.perform(get("/api/v1/evaluaciones/evaluaciones")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
