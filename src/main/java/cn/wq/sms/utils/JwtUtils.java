package cn.wq.sms.utils;

import cn.wq.sms.entity.LoginUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;

/**
 * @author DengWeiPing
 * @version 1.0
 * @date 2021/6/5 15:16
 */
public class JwtUtils {

    // 用于JWT进行签名加密的秘钥
    private static final String SECRET = "sms-wq@1216";

    /**
     * 生成token
     *
     * @Param: 传入需要设置的payload信息
     * @return: 返回token
     */
    public static String generateToken(LoginUser userInfo) {
        JWTCreator.Builder builder = JWT.create();

        // 将登录用户信息传入JWT的payload中
        builder.withClaim("userId", userInfo.getUserId());
        builder.withClaim("userName", userInfo.getUserName());
        builder.withClaim("roleId", userInfo.getRoleId());

        // 设置JWT令牌的过期时间为30
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 60);
        builder.withExpiresAt(instance.getTime());

        // 设置签名并返回token
        return builder.sign(Algorithm.HMAC256(SECRET)).toString();
    }

    /**
     * 校验token
     *
     * @Param: 传入token
     * @return:
     */
    public static void verify(String token) {
        JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }

    /**
     * 解析token信息
     *
     * @Param: 传入token
     * @return: 解密的token信息
     */
    public static LoginUser getTokenInfo(String token) {
        //获取token信息
        DecodedJWT claims = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);

        //解析为LoginUser对象
        LoginUser userInfo = new LoginUser();
        userInfo.setUserId(claims.getClaim("userId").asString());
        userInfo.setUserName(claims.getClaim("userName").asString());
        userInfo.setRoleId(claims.getClaim("roleId").asString());

        return userInfo;
    }
}
