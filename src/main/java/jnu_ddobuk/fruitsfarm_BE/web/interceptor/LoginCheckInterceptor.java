package jnu_ddobuk.fruitsfarm_BE.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jnu_ddobuk.fruitsfarm_BE.global.constant.SessionConst;
import jnu_ddobuk.fruitsfarm_BE.global.exception.CustomException;
import jnu_ddobuk.fruitsfarm_BE.global.exception.ErrorCode;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        Object loginMember = session != null ? session.getAttribute(SessionConst.LOGIN_MEMBER) : null;

        if (loginMember == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        return true;
    }

}