package com.company;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;

import java.nio.charset.StandardCharsets;

public final class PacketListener implements SerialPortPacketListener
{
    public static  String fsen="";
    @Override
    public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }

    @Override
    public int getPacketSize() { return 300; }

    @Override
    public void serialEvent(SerialPortEvent event)
    {
        byte[] newData = event.getReceivedData();

        String sen="";
        String str = new String(newData, StandardCharsets.UTF_8);
        Boolean start=false,end=false;

        for (int j=0;j<str.length();j++) {

            if(str.charAt(j)=='\n')
            for (int i = j; i < str.length(); ++i) {
                if (start && end) {


                    if (sen.length() > 5)
                        System.out.println(sen);

                    if (Main.isserverconnected) {
                        try {
                            Main.webSocket.send(sen);

                        } catch (Exception e) {
                            System.out.println("capture failed....................");
                        }
                    }
                    start = false;
                    end = false;
                    sen = "";

                    return;
                }

                if (start && !end) {
                    if (str.charAt(i) != '>')
                        sen += str.charAt(i);
                }
                if (str.charAt(i) == '<') start = true;
                if (str.charAt(i) == '>') {
                    end = true;

                }


            }
        }








        System.out.println("\n=============================\n");
        for (int i = 0; i < newData.length; ++i)
        {

            if ((char)newData[i]=='\n')
                System.out.println("\n++++++++++++++++++++++\n");
            System.out.print((char)newData[i]);

        }

        System.out.println("\n=============================\n");
        //System.out.println("\n");
    }

}

