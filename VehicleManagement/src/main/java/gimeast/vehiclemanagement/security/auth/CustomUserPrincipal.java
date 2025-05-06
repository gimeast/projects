package gimeast.vehiclemanagement.security.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

@RequiredArgsConstructor
public class CustomUserPrincipal implements Principal {
    @Getter
    private final Long idx;
    private final String mid;

    @Override
    public String getName() {
        return mid;
    }
}
