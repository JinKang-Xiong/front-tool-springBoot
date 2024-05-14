package com.example.usercenter2.utils;

import com.example.usercenter2.common.ErrorCode;
import com.example.usercenter2.exception.BusinessException;
import io.jsonwebtoken.*;

import java.util.Date;

/**
 * token验证德工具类
 */
public class JwtTokenUtil {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer";

    private static final String SECRET = "userjink";
    private static final String ISS = "xionjinkang";

    //过期时间是3600秒，既是1个小时
    public static final long EXPIRATION = 7200L;

    //选择了记住我之后的过期时间为7天
    public static final long EXPIRATION_REMEMBER = 604800L;


    /**
     * 创建token
     *
     * @param id
     * @param username
     * @param isRemeber
     * @return
     */
    public static String createToken(String id, String username, boolean isRemeber) {
        long expiration = isRemeber ? EXPIRATION_REMEMBER : EXPIRATION;

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setIssuer(ISS)
                .setId(id)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    /**
     * 从token中获取用户名
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    /**
     * 从token中获取id
     *
     * @param token
     * @return
     */
    public static String getObjectID(String token) {
        return getTokenBody(token).getId();
    }


    /**
     * 是否过期
     * @param token
     * @return
     */
    @Deprecated
    public static boolean isExpiration(String token) {
        return getTokenBody(token).getExpiration().before(new Date());
    }

    /**
     * 获取token信息，同时也做效验处理
     * @param token
     * @return
     */
    public static Claims getTokenBody(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException expired) {
            //过期
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        } catch (SignatureException e) {
            //无效
            throw new BusinessException(ErrorCode.INVALID_REQUEST);
        } catch (MalformedJwtException malformedJwt) {
            //无效
            throw new BusinessException(ErrorCode.INVALID_REQUEST);
        }
    }



}
