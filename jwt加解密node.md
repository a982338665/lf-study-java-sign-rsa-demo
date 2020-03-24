


**1.加密：**

    let secret = datiConfig.jwtTokenSecret;
    let expires = moment().add('days', 7).valueOf();
    //生成token
    let token = jwt.encode({
        iss: user.user_code,
        exp: expires
    }, secret);


**2.解密：**

    let secret = datiConfig.jwtTokenSecret;
    var decoded = jwt.decode(token, secret);
    if (decoded.exp <= Date.now()) {
        res.sendStatus(401);
        return;
    }
    
**3.配置：**

    var datiConfig = {
        jwtTokenSecret: 'xxxxx'
    }
