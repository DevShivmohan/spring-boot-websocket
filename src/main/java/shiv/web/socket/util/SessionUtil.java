package shiv.web.socket.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SessionUtil {

    private static HttpServletResponse getSessionResponse() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getResponse();
    }

    private static HttpServletRequest getSessionRequest() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest();
    }

    public static void setSessionAttribute(Object value) {
        Cookie jwtCookie = new Cookie("JWT_TOKEN_COOKIE", value.toString());
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(365 * 24 * 60 * 60);
        getSessionResponse().addCookie(jwtCookie);
    }

    public static Object getSessionAttribute() {
        Cookie[] cookies = getSessionRequest().getCookies();
        String bearerToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT_TOKEN_COOKIE".equals(cookie.getName())) {
                    bearerToken = cookie.getValue();
                    return bearerToken;
                }
            }
        }
        return bearerToken;
    }
}

