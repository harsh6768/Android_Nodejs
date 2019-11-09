const express = require('express');
const morgan=require('morgan');
const bodyParser=require('body-parser');

const mainRoutes=require('./backend/routes/mainRoutes');

const app=express();

//morgan logger as development
app.use(morgan('dev'));


app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());


app.use("/", mainRoutes);


app.set('port',process.env.PORT||3000); 
app.listen(app.get('port'),()=>{
    console.log(`Server is  up on port ${app.get('port')}...`);
});