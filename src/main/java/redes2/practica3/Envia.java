/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redes2.practica3;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author mauri
 */
class Envia extends Thread{
    MulticastSocket socket;
    BufferedReader br;
    String autor;
    
    public Envia(MulticastSocket m, BufferedReader br, String autor){
        this.socket=m;
        this.br=br;
        this.autor=autor;
        
    }
    public void run(){
        try{
            //BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
            String dir = "231.1.1.1";
            String dir6 = "ff3e::1234:1";
            int pto=1234;
            boolean inicio = true;
            InetAddress gpo = InetAddress.getByName(dir6);
            Mensaje mensaje;
            DatagramPacket dp;
            ObjectOutputStream oos=null;
            ByteArrayOutputStream bos=null;
            System.out.println("Escribe un mensaje para ser enviado:");
            for(;;){
                
                if(inicio){
                    mensaje = new Mensaje(0, autor,"inicio");//mensaje de inicio
                    inicio=false;
                }else {
                    String texto= br.readLine();
                    if(texto.charAt(0)=='@'){
                        mensaje = new Mensaje(autor, texto.substring(1, texto.indexOf(' ')) , texto);//mensaje privado
                    }else{
                        mensaje = new Mensaje(1, autor, texto);
                    }
                }
                //byte[] b = mensaje.getBytes();
                //DatagramPacket p = new DatagramPacket(b,b.length,gpo,pto);
                dp = new DatagramPacket(new byte[1024],1024, gpo, pto);
                bos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(bos);
                byte[] buf= new byte[1024];
                oos.writeObject(mensaje);
                oos.flush();
                buf = bos.toByteArray();
                dp.setData(buf);
                socket.send(dp);
                oos.close();

            }//for
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }//run
    
    
}//class
