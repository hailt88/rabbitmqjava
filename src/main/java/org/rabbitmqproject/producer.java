/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.rabbitmqproject;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.util.Date;

/**
 *
 * @author hai.lt
 */
public class producer implements Runnable{

    Thread thread;
    String uri = "";
    public producer(String uri) {
        System.out.println("Start producer contructor["+uri+"]");
        this.uri = uri;        
        thread = new Thread(this);
        thread.start();
    }
    
    public void startProducer(){
        this.thread.start();
    }
    public void stopProducer(){
        this.thread.interrupt();
    }

    @Override
    public void run() {
        try {
            System.out.println("Start Producer------------------------");
//            String uri = "amqps://rakkoayb:bLYhhjRY66XIbXOhGSCTNWMX5S1RHzhS@armadillo.rmq.cloudamqp.com/rakkoayb";
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(this.uri);
            Connection connection =  factory.newConnection();
            Channel channel = connection.createChannel();
            System.out.println("Connection info: "+connection.getClientProvidedName()+" - "+connection.getId());
            String nameQueue = "product_list";            
            String time = String.valueOf(new Date().getTime());
            String msg = "HELLO: "+time;
            channel.queueDeclare(nameQueue, false, false, false, null);
            System.out.println("Channel number: "+channel.getChannelNumber());
            AMQP.BasicProperties pros = new AMQP.BasicProperties();
//            pros.
            for(int i = 0; i < 100; i++){
                time = String.valueOf(new Date().getTime());
                msg = "HELLO HELLO ["+i+"] "+time;
                channel.basicPublish("", nameQueue, null, msg.getBytes());
                System.out.println("SEND TO QUEUE: "+msg);
                Thread.sleep(500);
            }
            System.out.println("connection_id: "+connection.getId());
//            Thread.sleep(50000);
//            channel.close();
//            connection.close();
        } catch (Exception ex) {
//            Logger.getLogger(RabbitmqProject.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            System.out.println("Finish producer ");
        }
    }
    
}
