package com.evaluaciones.microservicioEvaluaciones.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.evaluaciones.microservicioEvaluaciones.DTO.UsuarioDTO;

@Service
public class UsuarioClientService {

    private final RestTemplate restTemplate;
    private final String usuariosBaseUrl = "http://localhost:8081/edutech-service/api/edutech"; // ajusta si usas otro puerto

    public UsuarioClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Método para obtener datos del usuario desde el microservicio Usuarios
    public UsuarioDTO obtenerUsuarioPorId(Long idUsuario) {
        String url = usuariosBaseUrl + "/" + idUsuario;

        try {
            return restTemplate.getForObject(url, UsuarioDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar el microservicio Usuarios", e);
        }
    }
}

