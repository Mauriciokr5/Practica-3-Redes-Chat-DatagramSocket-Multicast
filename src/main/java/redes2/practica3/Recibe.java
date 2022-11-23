/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redes2.practica3;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

/**
 *
 * @author mauri
 */
class Recibe extends Thread{
    MulticastSocket socket;
    String user;
    
    public Recibe(MulticastSocket m, String user){
        this.socket=m;
        this.user = user;
    }
    public void run(){
       try{
        DatagramPacket dp= null;
        ObjectInputStream ois = null;
        Mensaje mensaje =null;
           
        for(;;){
            dp = new DatagramPacket(new byte[1024],1024);
            socket.receive(dp);
            ois = new ObjectInputStream(new ByteArrayInputStream(dp.getData()));
            mensaje = (Mensaje)ois.readObject();
            mensaje.deplegar(user);
            ois.close();
       } //for
       }catch(Exception e){
           e.printStackTrace();
       }//catch
    }//run
}//class
