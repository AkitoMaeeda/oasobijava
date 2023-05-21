import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
public class data {
    
    static int textsize;
    static Random rand = new Random();
    public static void main(String[] args){
        
        try{
            ServerSocket se = new ServerSocket(5000);



            File textfile = new File("gametext.txt");
            FileReader fr = new FileReader(textfile);
            BufferedReader br = new BufferedReader(fr);
            String line;
            List<String[]> text = new ArrayList<String[]>(); 
            while((line = br.readLine()) != null){
                text.add(line.split(",")); 
            }
            while(true){
                Socket socket = se.accept();
                textsize = text.size();
                System.out.println(textsize);
                String[] output = text.get(rand.nextInt(textsize));
                String outputtext = Arrays.toString(output);
                System.out.println(Arrays.toString(output));
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                writer.println(outputtext);
                writer.close();
                br.close();
                
                
            }
        
        }catch (IOException e){
            System.out.println("サーバー側のエラー7");
            System.out.println(e);

        }
    }

}