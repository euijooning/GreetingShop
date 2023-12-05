package store.greeting.cart.validator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.security.Principal;

@Getter
@Setter
public class PrincipalContext {
    private static final String PRINCIPAL_KEY = "currentPrincipal";

    public static void setPrincipal(Principal principal) {
        RequestContextHolder.currentRequestAttributes().setAttribute(PRINCIPAL_KEY, principal, RequestAttributes.SCOPE_REQUEST);
    }

    public static Principal getPrincipal() {
        return (Principal) RequestContextHolder.currentRequestAttributes().getAttribute(PRINCIPAL_KEY, RequestAttributes.SCOPE_REQUEST);
    }

    public static void clearPrincipal() {
        RequestContextHolder.currentRequestAttributes().removeAttribute(PRINCIPAL_KEY, RequestAttributes.SCOPE_REQUEST);
    }
}