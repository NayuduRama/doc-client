package com.docclient.interceptor;

import com.docclient.exceptionhandling.ServiceException;
import com.docclient.util.ServiceConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class ExceptionHandlingInterceptor extends HandlerInterceptorAdapter {
    private String authHeader;
    private String acceptValue;

    public String getAcceptValue() {
        return acceptValue;
    }

    public void setAcceptValue(String acceptValue) {
        this.acceptValue = acceptValue;
    }

    public String getAuthHeader() {
        return authHeader;
    }

    public void setAuthHeader(String authHeader) {
        this.authHeader = authHeader;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (StringUtils.isBlank(request.getHeader(ServiceConstants.AUTHORIZATION))){
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Please provide authentication header");
        }
        setAuthHeader(request.getHeader(ServiceConstants.AUTHORIZATION));
        setAcceptValue(request.getHeader(ServiceConstants.ACCEPT));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception{
        setAuthHeader(null);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                           Exception exception) throws Exception{
    }
}
