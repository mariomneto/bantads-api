require("dotenv-safe").config();
const jwt = require("jsonwebtoken");
var http = require("http");
const express = require("express");
const httpProxy = require("express-http-proxy");
const app = express();
var cookieParser = require("cookie-parser");
var bodyParser = require("body-parser");
var logger = require("morgan");
const helmet = require("helmet");

// const authServiceProxy = httpProxy("http://localhost:5000");
// const accountServiceProxy = httpProxy("http://localhost:5001");
const clientServiceProxy = httpProxy("http://localhost:5002");
// const managerServiceProxy = httpProxy("http://localhost:5003");

const verifyJWT = (req, res, next) => {
    const token = req.headers['x-access-token'];
    if (!token) {
        return res.status (401).json({ auth: false, message: 'Token não fornecido.' });
    }

    jwt.verify (token, process.env.SECRET, (err, decoded) => {
        if (err){
            return res.status (500).json({ auth: false, message: 'Falha ao autenticar o token.'});
        }
    
        // se tudo estiver ok, salva no request para uso posterior
        req.userId = decoded.id;
        next();
    });
}

//parse application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: false }));

//parse application/json
app.use(bodyParser.json());

app.use(logger("dev"));
app.use(helmet());
app.use(express.json());
app.use(cookieParser());
app.use(express.urlencoded({ extended: false }));

const urlencodedParser = bodyParser.urlencoded({ extended: false });

app.post("/login", urlencodedParser, (req, res, next) => {
    //login admin
    if(req.body.user === "admin" && req.body.password === "admin"){
        const id = 1;
        var token = jwt.sign({ id }, process.env.SECRET, {
            expiresIn: 300 // expires in 5min
        });

        return res.json({auth: true, token });
    }

    //TODO login com db

    res.status(500).json({message: 'Login inválido!'});
});

app.post("/logout", (req, res) => {
    res.json({ auth: false, token: null });
});

app.get('/client', verifyJWT, (req, res, next) => {
    clientServiceProxy(req, res, next);
});

//cria servidor na porta 3000
var server = http.createServer(app);
server.listen(3000);