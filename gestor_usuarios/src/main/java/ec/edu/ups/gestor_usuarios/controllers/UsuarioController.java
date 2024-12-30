package ec.edu.ups.gestor_usuarios.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.gestor_usuarios.models.Usuario;
import ec.edu.ups.gestor_usuarios.models.UsuarioDTO;
import ec.edu.ups.gestor_usuarios.services.UsuarioService;
import ec.edu.ups.gestor_usuarios.util.RespuestaGenericaServicio;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;	

	@GetMapping("/test/{nombre}")
	public ResponseEntity<?> test(@PathVariable("nombre") String nombre) {
		return ResponseEntity.status(HttpStatus.OK).body(nombre);

	}
	
	@PostMapping("/")
	public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) throws Exception{
		try{
			Usuario usuario = new Usuario();
			usuario.setIdentificacion(usuarioDTO.getIdentificacion());
			usuario.setNombres(usuarioDTO.getNombres());
			usuario.setApellidos(usuarioDTO.getApellidos());
			usuario.setCargo(usuarioDTO.getCargo());
			usuario.setEdad(usuarioDTO.getEdad());
			Usuario usuarioCreado = usuarioService.crearUsuario(usuario, usuarioDTO.getEstado());
			RespuestaGenericaServicio respuesta = new RespuestaGenericaServicio("SUCCESS", usuarioCreado,
					new String[] { "Tarea creada exitosamente" });
			return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
		}catch (IllegalArgumentException e) {
			RespuestaGenericaServicio respuesta = new RespuestaGenericaServicio("ERROR", null,
					new String[] { e.getMessage() });
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
		}catch (Exception e) {
			RespuestaGenericaServicio respuesta = new RespuestaGenericaServicio("ERROR", null,
					new String[] { e.getMessage() });
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
		
	}
	
	@GetMapping("/{identificacion}")
	public ResponseEntity<?> obtenerUsuario(@PathVariable String identificacion){
		try {
			Usuario usuario = usuarioService.obtenerUsuario(identificacion);			
			RespuestaGenericaServicio respuesta = new RespuestaGenericaServicio("SUCCESS", usuario,
	                new String[] { "Usuario encontrado." });
	        return ResponseEntity.ok(respuesta);
			
		}catch (Exception e) {
			RespuestaGenericaServicio respuesta = new RespuestaGenericaServicio("ERROR", null,
					new String[] {e.getMessage() });
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}
	
	@PutMapping("/{identificacion}")
	public ResponseEntity<?> actualizarUsuario(@PathVariable String identificacion, @Valid @RequestBody Usuario usuario){
		try {
			 Usuario usuarioActualizado = usuarioService.actualizarUsuario(identificacion, usuario);

		        RespuestaGenericaServicio respuesta = new RespuestaGenericaServicio("SUCCESS", usuarioActualizado,
		                new String[] { "Usuario actualizado exitosamente." });
		        return ResponseEntity.ok(respuesta);
		}catch (Exception e) {
			RespuestaGenericaServicio respuesta = new RespuestaGenericaServicio("ERROR", null,
					new String[] {e.getMessage() });
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}
	
	@DeleteMapping("/{identificacion}")
	public ResponseEntity<?> borrarUsuario(@PathVariable String identificacion){
		try {
			boolean isBorrado = usuarioService.borrarUsuario(identificacion);
			if(!isBorrado) {
				RespuestaGenericaServicio respuesta = new RespuestaGenericaServicio("ERROR", null, 
						new String[] {"Usuario con identificacion: "+identificacion + " no encontrado"});	
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}
			RespuestaGenericaServicio respuesta = new RespuestaGenericaServicio("SUCCESS", null,
					new String[] { "Usuario con ID: "+identificacion+" eliminado exitosamente" });
			return ResponseEntity.ok(respuesta);
		}catch (Exception e) {
			RespuestaGenericaServicio respuesta = new RespuestaGenericaServicio("ERROR", null,
					new String[] { "Error interno del servidor: " + e.getMessage() });
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}
}
