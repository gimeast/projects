package gimeast.vehiclemanagement.vehicle.admin.exception;

import lombok.Getter;

public class AlreadyExistsException extends RuntimeException {
    private String message;
    @Getter
    private int code;

    public AlreadyExistsException(String message) {
        super(message);
        this.message = message;
        this.code = 400;
    }
}
