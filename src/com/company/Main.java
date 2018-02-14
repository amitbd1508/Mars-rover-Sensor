package com.company;

import com.fazecast.jSerialComm.SerialPort;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Scanner;


public class Main extends WebSocketServer {

    public Main(InetSocketAddress address) {
        super(address);
    }
    public static WebSocketServer server;
    public static  Boolean isserverconnected=false;
    public  static WebSocket webSocket=null;
    static public void main(String[] args)
    {
        Thread thread1 = new Thread() {
            public void run() {
                String input;
                System.out.println("\n"+"Enter Ip: ");
                Scanner sc=new Scanner(System.in);
                input=sc.next();


                String host = "192.168.0."+input;
                int port = 4649;



                server = new Main(new InetSocketAddress(host, port));
                System.out.print("Server started at : "+"ws://"+host+":"+port);

                server.run();

            }
        };
       //109
        // thread1.start();

        Thread thread2=new Thread(){
            public void run(){
                SerialPort comPort = SerialPort.getCommPorts()[0];
                comPort.openPort();
                PacketListener listener = new PacketListener();
                comPort.addDataListener(listener);

                //try { Thread.sleep(5000); } catch (Exception e) { e.printStackTrace(); }
                //comPort.removeDataListener();
                //comPort.closePort();
            }
        };
        thread1.start();
        thread2.start();
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {

        System.out.println("\nnew connection to " + webSocket.getRemoteSocketAddress());
        isserverconnected=true;
        this.webSocket=webSocket;

    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {

    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }
}
