package pers.li.jwt;

import com.alibaba.druid.support.json.JSONUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import netscape.javascript.JSUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt
 */
public class JwtUtil {

    private static final String JWTSECRET = "mioodosecret";
//    private static final String JWTSECRET = "x9999999xx";

    /**
     * 生成token
     * @param json
     * @return
     */
    public static String createTokenWithClasims(Map json) {
        String token = Jwts.builder()
                .setClaims(json)
//                .setIssuedAt()
//                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000 * 365))//设置过期时间
                .signWith(SignatureAlgorithm.HS256, JWTSECRET)
                .compact();
        return token;
    }
    /**
     * 生成token
     * @param json
     * @return
     */
    public static String createTokenWithPayLoad(String json) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("typ", "JWT");
        map.put("alg","HS256");
        String string = JSONUtils.toJSONString(map);
        String token = Jwts.builder()
                .setHeaderParams(map)
                .setPayload(json)
                .signWith(SignatureAlgorithm.HS256, JWTSECRET)
                .compact();
        return token;
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public static Claims verifyTokenWithClaims(String token){
        Claims user = Jwts.parser()
                .setSigningKey(JWTSECRET)
                .parseClaimsJws(token)
                .getBody();
        return user;
    }


    public static void main(String[] args) {

        Map<String,Object> map = new HashMap<>();
        map.put("id",111);
        map.put("name",18);
        String token = createTokenWithClasims(map);
        System.err.println(token);
        Claims s = verifyTokenWithClaims(token);
        System.err.println(s);

//        String s1 = "{\"sub\":\"subject\",\"aud\":\"sina.com\",\"iss\":\"baidu.com\",\"iat\":1528360628,\"nbf\":1528360631,\"jti\":\"253e6s5e\",\"exp\":1528360637}";


        String s1 = JSONUtils.toJSONString(map);
        String tokenWithPayLoad = createTokenWithPayLoad(s1);
        System.err.println("s1,"+tokenWithPayLoad);
        Claims s2 = verifyTokenWithClaims(tokenWithPayLoad);
        System.err.println("s2,"+s2);
        System.err.println("=========================");

        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("iss", "111");
        objectObjectHashMap.put("exp", 1585638697003L);
        //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIxMTEiLCJleHAiOjE1ODU2Mzg2OTcwMDN9._IfKpZqi5Ykd0BbC3NAGBGmb3KQsMN10tolJdScHFms
        String dd1 = JSONUtils.toJSONString(objectObjectHashMap);
        String d1 = createTokenWithPayLoad(dd1);
        System.err.println(d1);
        Claims s2s = verifyTokenWithClaims(d1);
        System.err.println("s2,"+s2s);

        System.err.println("____________________________");

//        String xxx= "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIxYmU2ODUzMDY3NjkxMWU5OGI2YTU3NDQxMjAzYjM2NCIsImV4cCI6MTU4NTYyNTc3MTg4MX0.GfbLjtR4Jrr8V4-kFNSqRiUTMz80Gx4mutWhh18REzQ";
//        Claims s2ss = verifyTokenWithClaims(xxx);
//        System.err.println("s2,"+s2ss);

//        String s3 = "{}";
//        Claims s2 = verifyTokenWithClaims(token);
    }
}
