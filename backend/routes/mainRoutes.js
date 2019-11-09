const express=require('express');

const authController=require('../controllers/authController');
const router=express.Router();

const app=express();

router.route('/register').post(authController.signUpPage);
router.route('/login').post(authController.loginPage);

router.route('/logout').get(authController.logoutPage);

module.exports=router;