package gimeast.vehiclemanagement.vehicle.advice;

import gimeast.vehiclemanagement.vehicle.exception.AlreadyExistsException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class VehicleAPIControllerAdvice {

    /**
     * 관리자가 중복된 trim 정보를 저장하는 경우
     * @param exception
     * @return
     */
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handleEntityNotFoundException(AlreadyExistsException exception) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("message", exception.getMessage());
        errors.put("code", exception.getCode());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
