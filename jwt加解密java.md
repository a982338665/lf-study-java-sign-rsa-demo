
## https://www.jianshu.com/p/1ebfc1d78928

**1.jwt介绍：**
    
    一个JWT由三部分组成：
    Header（头部） —— base64编码的Json字符串
    Payload（载荷） —— base64编码的Json字符串
    Signature（签名）—— 使用指定算法，通过Header和Payload加盐计算的字符串
    
    各部分以" . "分割，如：
        eyJhbGciOiJIUzUxMiJ9.eyJjcnQiOjE1MjgzNDM4OTgyNjgsImV4cCI6MTUyODM0MzkxOCwidXNlcm5hbWUiOiJ0b20ifQ.E-0jxKxLICWgcFEwNwQ4pfhdMzchcHmsd8G_BTsWgkUmVwPzDd7jJlf94cAdtbwTLMm27ouYYzTTxMXq7W1jvQ
    直接通过base64解码可获得
        Header：
        {"alg":"HS512"}
        Payload：
        {"crt":1528343898268,"exp":1528343918,"username":"tom"}

**2.依赖：**
    
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt</artifactId>
        <version>0.9.0</version>
    </dependency>        
    
**3.生成token：**

    public static String generateToken(Stringusername){
        Map claims= new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,username);
        claims.put(CLAIM_KEY_CREATE_TIME,new Date(System.currentTimeMillis()));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, SIGNING_KEY)
                //.compressWith(CompressionCodecs.DEFLATE)
                .compact();
    }   
    
**4.详细介绍：**
    
    Jwts.builder() 返回了一个 DefaultJwtBuilder()
    DefaultJwtBuilder属性
        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
        private Header header; //头部
        private Claims claims; //声明
        private String payload; //载荷
        private SignatureAlgorithm algorithm; //签名算法
        private Key key; //签名key
        private byte[] keyBytes; //签名key的字节数组
        private CompressionCodec compressionCodec; //压缩算法
    DefaultJwtBuilder包含了一些Header和Payload的一些常用设置方法
        
    1.setHeader() 有两种参数形式，一种是Header接口的实现，一种是Map。其中Header接口也继承自Map。如果以第二种形式（即Map）作为参数，
        在setHeader的时候会生成默认的Header接口实现DefaultHeader对象。两种参数形式调用setHeader()，都会令Header重新赋值。即：
        this.header = header;
        或者
        this.header = new DefaultHeader(header);
    2.setHeaderParam() 和 setHeaderParams() 向Header追加参数
        两个方法都使用ensureHeader() 方法（返回当前header 如果不存在则创建DefaultHeader）
        如果即不设置签名，也不进行压缩，header是不是就应该没有了呢？
        不是。即时不进行签名，alg也应该存在，不然对其进行解析会出错。
        在生成jwt的时候，如果不设置签名，那么header中的alg应该为none。jjwt中compact()方法实现
                if (key != null) {
                    jwsHeader.setAlgorithm(algorithm.getValue());
                } else {
                    //no signature - plaintext JWT:
                    jwsHeader.setAlgorithm(SignatureAlgorithm.NONE.getValue());
                }
                即为：{"alg":"none"}
    3.载荷部分存在两个属性：payload和claims。两个属性均可作为载荷，jjwt中二者只能设置其一，如果同时设置，在终端方法compact() 中将抛出异常
        setPayload() 设置payload，直接赋值
        setClaims() 设置claims，以参数创建一个新Claims对象，直接赋值
        claim() 如果builder中Claims属性为空，则创建DefaultClaims对象，并把键值放入；如果Claims属性不为空，获取之后判断键值，存在则更新，不存在则直接放入。
    4.7个保留申明：
        setIssuer()     iss: 签发者
        setSubject()    sub: 面向用户
        setAudience()   aud: 接收者
                        jti：JWT ID为web token提供唯一标识               
        setExpiration() exp(expires): 过期时间
        setNotBefore()  nbf(not before)：不能被接收处理时间，在此之前不能被接收处理
        setIssuedAt()   iat(issued at): 签发时间
        setId()
    5.PayLoad举例：
        {"sub":"subject","aud":"sina.com","iss":"baidu.com","iat":1528360628,"nbf":1528360631,"jti":"253e6s5e","exp":1528360637}
        当然也可以在Payload中添加一些自定义的属性claims键值对
        compressWith() 压缩方法。当载荷过长时可对其进行压缩。可采用jjwt实现的两种压缩方法CompressionCodecs.GZIP和CompressionCodecs.DEFLATE
        signWith() 签名方法。两个参数分别是签名算法和自定义的签名Key（盐）。签名key可以byte[] 、String及Key的形式传入。
        前两种形式均存入builder的keyBytes属性，后一种形式存入builder的key属性。如果是第二种（及String类型）的key，则将其进行base64解码获得byte[] 。
    6.compact() 生成JWT。过程如下：
        载荷校验，前文已经提及。
        获取key。如果是keyBytes则通过keyBytes及算法名生成key对象。
        将所使用签名算法写入header。如果使用压缩，将压缩算法写入header。
        将Json形式的header转为bytes，再Base64编码
        将Json形式的claims转为bytes，如果需要压缩则压缩，再进行Base64编码
        拼接header和claims。如果签名key为空，则不进行签名(末尾补分隔符" . ")；如果签名key不为空，以拼接的字符串作为参数，按照指定签名算法进行签名计算签名部分 sign(String jwtWithoutSignature)，签名部分同样也会进行Base64编码。
        返回完整JWT
        jjwt实现的 DefaultJwtSigner 提供了一个带工厂参数的构造方法。并将jjwt实现的 DefaultSignerFactory
        静态实例传入，根据不同的签名算法创建对应的签名器进行签名。
