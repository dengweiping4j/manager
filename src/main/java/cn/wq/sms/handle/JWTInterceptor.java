package cn.wq.sms.handle;

import cn.wq.sms.utils.JwtUtils;
import cn.wq.sms.utils.Result;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author DengWeiPing
 * @version 1.0
 * @date 2021/6/5 15:31
 */
public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Result result = new Result();
        //获取请求头中的token令牌
        String token = request.getHeader("token");
        try {
            //验证令牌
            JwtUtils.verify(token);

            //校验接口权限

            //放行请求
            return true;
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            result.setMessage("无效签名!");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            result.setMessage("token过期!");
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            result.setMessage("token算法不一致!");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("token无效!");
        }

        result.setCode("403");//设置状态
        //转为json
        String json = new ObjectMapper().writeValueAsString(result);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
