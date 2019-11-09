
### :heart: Star :heart: the repo to support the project or :smile:[Follow Me](https://github.com/harsh6768).Thanks!
# Android_Nodejs


1. Clone Android  Project 

       git clone https://github.com/harsh6768/Android_Nodejs.git

2. Clone Backend Node.js Project

       git clone https://github.com/harsh6768/Android_Nodejs.git
       

Retrofit Object

#### 1. To make it work the project You system and the testing device must connected with same wifi.

       retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.43.134:3000")   //192.168.43.134 is the ip address of the system 
                .addConverterFactory(GsonConverterFactory.create())
                .build();
               

#### 2. If you want to use emulator then you should use the below code


      retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")   //for emulator devices
                .addConverterFactory(GsonConverterFactory.create())
                .build();
