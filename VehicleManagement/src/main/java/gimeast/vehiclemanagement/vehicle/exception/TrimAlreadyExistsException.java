package gimeast.vehiclemanagement.vehicle.exception;

import lombok.Getter;

public class TrimAlreadyExistsException extends RuntimeException {
    private String message;
    @Getter
    private int code;

    public TrimAlreadyExistsException(String message) {
        super(message);
        this.message = message;
        this.code = 400;
    }
}
