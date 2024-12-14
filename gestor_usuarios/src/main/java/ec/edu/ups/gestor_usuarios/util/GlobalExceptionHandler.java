package ec.edu.ups.gestor_usuarios.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<RespuestaGenericaServicio> handleValidationExceptions(MethodArgumentNotValidException ex) {

		StringBuilder errorMessage = new StringBuilder();
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			errorMessage.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append("; ");
		}


		RespuestaGenericaServicio respuesta = new RespuestaGenericaServicio("ERROR",
				null, 
				new String[] { errorMessage.toString() }
		);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
	}
}
