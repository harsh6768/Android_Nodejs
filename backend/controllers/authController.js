const  mongoose=require('mongoose');
const bcrypt=require('bcryptjs');  //to encrypt the password
const User=require('../databases/db.users');

const keys=require('../config/keys');
//Connect to MongoDb
mongoose.connect(keys.mongodb.MongoURI,{ useNewUrlParser:true})
.then(()=>console.log('MongoDb Connect...'))
.catch((err)=>console.log(err));


let signUpPage=(req,res)=>{

    console.log(req.body);
    
    const {name,email,password}=req.body;
    //If user forget to enter any data
    if(!(name && email && password)){
        // console.log('Please enter the valid information!!!')
    }else{
      
    //to encrypt the password 
    const saltRounds = 10;
    let hashPassword;
    bcrypt.hash(password,saltRounds,(err,hash)=>{
          //hashPassword=hash;
          //console.log(hash)
          let user=new User({
                name,email,password:hash
          })
        
        //To save the data into the database using mongoose
        let jsonObject={
            status:200,
            body:user,
            message:'User registered!!!'
        }
        user.save()
        .then(user=>res.status(200).send(JSON.stringify(jsonObject)))
        .catch(err=>res.status(400).send(JSON.stringify({
            message:'Error occured!'
        })))

    })
  }

}

let loginPage=(req,res)=>{
   
    const { email,password}=req.body;
    console.log(email,password);
    
    if(!(email && password)){
        //console.log('Please fill valid information!!!');
    }else{
        //to fetch the users from the mongoose
        User.find({},(err,users)=>{

            if(err) return;
            
            for(let user of users){
                console.log(user)
                if(user.email==email){
 
                    // //to compare the password
                    bcrypt.compare(password,user.password,(err,resolve)=>{
                            if(!resolve) return res.status(400).send(JSON.stringify({
                                message:'Password is incorrect'
                            }));
                         
                            let loginCode={
                                status:200,
                                body:user,
                                message:'Login Successfully!'
                            }
                            res.status(200).send(JSON.stringify(loginCode));
                            
                    })
                 
                }
                
            }
        })
    }
}

let logoutPage=(req,res)=>{
    // let logoutPath=path.join(__dirname+'../../../client/views/signup.ejs');
    // res.render(logoutPath);
    //console.log('Logout Successfully!!!');
}

module.exports={
    signUpPage:signUpPage,
    loginPage:loginPage,
    logoutPage:logoutPage
}