/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package org.rabbitmqproject;

//import static spark.Spark.get;
//import static spark.Spark.port;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 *
 * @author hai.lt
 */
public class RabbitmqProject {

    public Object syncObj = new Object();
    public static void main(String[] args){
        try {
            RabbitmqProject runner = new RabbitmqProject();
            runner.test(3);
            String uri = System.getenv("CLOUDAMQP_URL");
            System.out.println("Hello World! ["+uri+"]");
            if (uri == null || uri.isEmpty()) {
                System.err.println("CLOUDAMQP_URL environment variable is not set.");
                System.exit(1);
            }            
            new consumer(uri);
            new producer(uri);
            int port = 8080; // Port for the health check endpoint

            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/health", (exchange -> {
                String response = "{\"status\":\"UP\"}";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(response.getBytes());
                output.flush();
                exchange.close();
            }));

        // Start the server
        server.start();

        } catch (Exception ex) {
            ex.printStackTrace();
//            Logger.getLogger(RabbitmqProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void test(int index) throws Exception{
        for(int i = 0 ; i < index; i++){
            System.out.println("Running test loop ["+i+"]");
            Thread.sleep(1000);
        }
        System.out.println("Finish test run");
    }
    
//    public void producer(){
//        try {
//            System.out.println("Start Producer------------------------");
//            String uri = "amqps://rakkoayb:bLYhhjRY66XIbXOhGSCTNWMX5S1RHzhS@armadillo.rmq.cloudamqp.com/rakkoayb";
//            ConnectionFactory factory = new ConnectionFactory();
////            factory.setHost("localhost");
//            factory.setUri(uri);
//            Connection connection =  factory.newConnection();
//            Channel channel = connection.createChannel();
//            String nameQueue = "product_list";            
//            String time = String.valueOf(new Date().getTime());
//            String msg = "HELLO HELLO "+time;
//            channel.queueDeclare(nameQueue, false, false, false, null);
//            for(int i = 0; i < 30; i++){
//                time = String.valueOf(new Date().getTime());
//                msg = "HELLO HELLO ["+i+"] "+time;
//                channel.basicPublish("", nameQueue, null, msg.getBytes());
//                System.out.println("SEND TO QUEUE: "+msg);
//                Thread.sleep(1000);
//            }
//            System.out.println("connection_id: "+connection.getId());
//            Thread.sleep(50000);
//            channel.close();
//            connection.close();
//        } catch (Exception ex) {
////            Logger.getLogger(RabbitmqProject.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    public void consumer(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    System.out.println("Start consumer------------------------");
//                    String nameQueue = "product_list";
////                    String msg = "HELLO HELLO";
//                    String uri = "amqps://rakkoayb:bLYhhjRY66XIbXOhGSCTNWMX5S1RHzhS@armadillo.rmq.cloudamqp.com/rakkoayb";
//                    ConnectionFactory factory = new ConnectionFactory();
//        //            factory.setHost("localhost");
//                    factory.setUri(uri);
//                    Connection connection =  factory.newConnection();
//                    Channel channel = connection.createChannel();
//                    channel.queueDeclare(nameQueue, false, false, false, null);
//                    ////////////////////////////////////////
//                    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
//                    DefaultConsumer consumer = new DefaultConsumer(channel) {
//                        @Override
//                         public void handleDelivery(
//                            String consumerTag,
//                            Envelope envelope, 
//                            AMQP.BasicProperties properties, 
//                            byte[] body) throws IOException {
//                                String message = new String(body, "UTF-8");
//                                String time = String.valueOf(new Date().getTime());
//                                System.out.println("Receiver MSG["+time+"]: "+message);
//                                // process the message
//                         }
//                    };
//                    channel.basicConsume(nameQueue, true, consumer);                    
////                    channel.basicPublish("", nameQueue, null, msg.getBytes());
//                    System.out.println("connection_id: "+connection.getId());
//                    Thread.sleep(30000);
//                    channel.close();
//                    connection.close();
//                } catch (Exception ex) {
//        //            Logger.getLogger(RabbitmqProject.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }).start();
//    }
}
