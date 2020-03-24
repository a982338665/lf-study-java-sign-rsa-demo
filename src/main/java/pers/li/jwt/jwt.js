// const jwt = require('jwt-simple');
// const moment = require('moment');
//
// let secret = "mioodosecret";
// //生成token
//
//
//
// function f() {
//     let expires = moment().add('days', 7).valueOf();
//     console.error(expires)
//     let token = jwt.encode({
//         iss: "111",
//         exp: 1585638697003
//     }, secret,"HS256");
//     return token;
// }
//
// function f1() {
//     let token = f()
//     var decoded = jwt.decode(token, secret);
//     console.error(token)
//     console.error(decoded)
// }
// f1()
