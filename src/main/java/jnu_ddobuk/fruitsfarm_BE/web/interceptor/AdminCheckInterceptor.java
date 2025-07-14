package jnu_ddobuk.fruitsfarm_BE.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jnu_ddobuk.fruitsfarm_BE.global.constant.SessionConst;
import jnu_ddobuk.fruitsfarm_BE.global.exception.CustomException;
import jnu_ddobuk.fruitsfarm_BE.global.exception.ErrorCode;
import jnu_ddobuk.fruitsfarm_BE.member.entity.Member;
import jnu_ddobuk.fruitsfarm_BE.member.entity.Role;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        Member loginMember = session != null ? (Member) session.getAttribute(SessionConst.LOGIN_MEMBER) : null;

        if (loginMember == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        if (loginMember.getRole() != Role.TEACHER) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        return true;
    }
}
