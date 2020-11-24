package kr.com.inspect.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.Cookie;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Component("properCookieClearingLogoutHandler")
public final class ProperCookieClearingLogoutHandler implements LogoutHandler {
	
	private final List<String> cookiesToClear;

	public ProperCookieClearingLogoutHandler(String... cookiesToClear) {
        Assert.notNull(cookiesToClear, "List of cookies cannot be null");
        this.cookiesToClear = Arrays.asList(cookiesToClear);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        for (String cookieName : cookiesToClear) {
            Cookie cookie = new Cookie(cookieName, null);
            String cookiePath = request.getContextPath() + "/inspect";
            if (!StringUtils.hasLength(cookiePath)) {
                cookiePath = "/inspect";
            }
            cookie.setPath(cookiePath);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

}
