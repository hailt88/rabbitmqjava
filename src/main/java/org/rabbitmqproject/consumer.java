/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.rabbitmqproject;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ForkJoinWorkerThread;

/**
 *
 * @author hai.lt
 */
public class consumer implements Runnable{

    Thread thread;
    String uri = "";
    public consumer(String uri) {
        System.out.println("Start consumer contructor ["+uri+"]");
        this.uri = uri;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            System.out.println("Start consumer------------------------");
            String nameQueue = "product_list";
//            String uri = "amqps://rakkoayb:bLYhhjRY66XIbXOhGSCTNWMX5S1RHzhS@armadillo.rmq.cloudamqp.com/rakkoayb";
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(this.uri);
            Connection connection =  factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(nameQueue, false, false, false, null);
            ////////////////////////////////////////
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                 public void handleDelivery(
                    String consumerTag,
                    Envelope envelope, 
                    AMQP.BasicProperties properties, 
                    byte[] body) throws IOException {
                        String message = new String(body, "UTF-8");
                        String time = String.valueOf(new Date().getTime());
                        System.out.println("Receiver MSG["+time+"]: "+message);
                        // process the message
                 }
            };
            channel.basicConsume(nameQueue, true, consumer);                    
            System.out.println("connection_id: "+connection.getId());
            Thread.sleep(50000);
            channel.close();
            connection.close();
        } catch (Exception ex) {
//            Logger.getLogger(RabbitmqProject.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            System.out.println("Finish Consumer------------------------------ ");
        }
    }
    
}
