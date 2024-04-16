package com.example.demo.Componentes;

public class hilo extends Thread {

   @Override
    public void run(){
        super.run();
        for (int i=1; i<=10;i++){
            System.out.println("Km "+i+ "llego"+ this.getName());
            try{
             Thread.sleep((long)(Math.random()*3000));
            }catch(Exception e){

            }
        }
    }
}
