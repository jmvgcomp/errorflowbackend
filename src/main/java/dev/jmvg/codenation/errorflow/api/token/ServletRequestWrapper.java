package dev.jmvg.codenation.errorflow.api.token;

import org.apache.catalina.util.ParameterMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;

public class ServletRequestWrapper extends HttpServletRequestWrapper {
    String refreshToken;
    public ServletRequestWrapper(HttpServletRequest request, String refreshToken) {
        super(request);
        this.refreshToken = refreshToken;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
        map.put("refresh_token", new String[]{refreshToken});
        map.setLocked(true);
        return map;
    }
}
