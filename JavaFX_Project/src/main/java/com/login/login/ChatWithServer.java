package com.login.login;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class ChatWithServer extends Thread {
    private int ClientNbre;
    private List<Communication> lstCon = new ArrayList<Communication>();
    public static void main(String[] args) {
        new ChatWithServer().start();
    }

    @Override
    public void run()
    {
        try {
            ServerSocket ss =new ServerSocket(1235);
            System.out.println("En Attente d'une connexion...");
            while(true)
            {
                Socket s = ss.accept();
                ++ClientNbre;
                Communication newCom = new Communication(s, ClientNbre);
                lstCon.add(newCom);
                newCom.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public class Communication extends Thread{
        private Socket s;
        private int ClientNumber;

        Communication(Socket s, int ClientNumber){
            this.s = s;
            this.ClientNumber=ClientNumber;
        }
        @Override
        public void run()
        {
            try {
                InputStream is = s.getInputStream();
                OutputStream os = s.getOutputStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String Ip = s.getRemoteSocketAddress().toString();
                System.out.println("Numéro de Client : "+ ClientNumber+" est connecté ! | Son adresse IP : " +Ip);
                PrintWriter pw = new PrintWriter (os,  true);
                pw.println("Connexion Réussie avec le serveur , Client numéro :  "+ ClientNumber + ", Ecris quelque chose :");
                while (true) {
                    String UserRequest = br.readLine();
                    System.out.println("Requete reçu de la part de  : "+UserRequest);
                    if (UserRequest != null)
                    {
                        if (UserRequest.contains("=>")) {
                            String[] usermessage = UserRequest.split("=>");
                            if(usermessage.length==2){
                                String msg = usermessage[1];
                                int numeroClient = Integer.parseInt(usermessage[0]);
                                Send(msg, s, numeroClient);
                            }
                        }
                        else
                        {
                            Send(UserRequest, s, -1);
                        }
                    }
                }
        } catch (IOException e) {
                e.printStackTrace();
            }
        }
        void Send(String UserRequest , Socket socket, int nbre) throws IOException {
            System.out.println("Envoyer");
            for (Communication client : lstCon){
                if (client.s != socket)
                {
                    if (client.ClientNumber == nbre || nbre == -1 )
                    {
                        PrintWriter pw = new PrintWriter(client.s.getOutputStream(), true);
                        pw.println(UserRequest);
                    }
                }
            }
        }

    }

}
