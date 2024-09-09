package shiv.web.socket.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Component
public class SessionUtil {

    private static HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);  // true will create a new session if it doesn't exist
    }

    public static void setSessionAttribute(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(String key) {
        return getSession().getAttribute(key);
    }
}

