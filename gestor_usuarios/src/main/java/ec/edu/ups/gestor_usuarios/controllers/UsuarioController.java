package ec.edu.ups.gestor_usuarios.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@GetMapping("/test/{nombre}")
	public ResponseEntity<?> test(@PathVariable("nombre") String nombre) {
		return ResponseEntity.status(HttpStatus.OK).body(nombre);

	}
}
