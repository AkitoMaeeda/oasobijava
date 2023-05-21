import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;



public class Game extends JFrame implements ActionListener{

    
    //全体の管理をするオブジェクトとか
    JPanel mainpanel = new JPanel();  
    CardLayout mainlayout = new CardLayout();
    String playername;


    //タイトル画面のオブジェクト
    JPanel titlep = new JPanel();
    JLabel  title = new JLabel("title");  
    JLabel  error = new JLabel("エラーが発生しました"); 
    JButton start = new JButton("Game Start!!");
    JTextField username = new JTextField(10);

    //戦闘画面のオブジェクト
    List<String[]> texts = new ArrayList<String[]>();
    JPanel gamep = new JPanel();
    JLabel  gamel = new JLabel(); 
    int count = 0;
    JLabel  countl = new JLabel("0");
    JTextField text = new JTextField(20);
    JButton treset = new JButton("reset");
    JButton gButton = new JButton("GO!!");

    //クリア画面、GameOver画面のオブジェクト
    JPanel resultp = new JPanel();
    JLabel resultt = new JLabel("結果");
    JLabel result = new JLabel();
    JLabel userl = new JLabel();
    JButton backtitle = new JButton("タイトルに戻る");
    JLabel co = new JLabel();

    public static void main(String[] args){
        new Game();
    }

    public Game(){
        
        
        mainpanel.setLayout(mainlayout);
        

        
        setTitle("タイピングゲーム");

        titlep.add(title);
        start.addActionListener(this);
        titlep.add(start);
        titlep.add(username);
        mainpanel.add(titlep,"titlep");

        gamep.add(countl);
        gamep.add(gamel);
        gamep.add(text);
        treset.addActionListener(this);
        gamep.add(treset);
        gButton.addActionListener(this);
        gamep.add(gButton);
        mainpanel.add(gamep,"gamep");


        resultp.add(resultt);
        resultp.add(result);
        resultp.add(userl);
        resultp.add(co);
        mainpanel.add(resultp,"resultp");
        backtitle.addActionListener(this);
        resultp.add(backtitle);


        setSize(600,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.add(mainpanel, BorderLayout.CENTER);
        
    }

    void gettext(){
        try{
            Socket socket = new Socket("127.0.0.1",5000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String huga = reader.readLine();
            texts.add(huga.split(","));
            String[] hoge = texts.get(0);
            gamel.setText(hoge[1]);
            

        }catch(IOException e){
            
            titlep.add(error);
            mainlayout.show(mainpanel,"titlep");
            }
    }

    void finish(){
        userl.setText(playername);
        co.setText(String.valueOf(count));
        mainlayout.show(mainpanel,"resultp");
    }


public void actionPerformed(ActionEvent e){
    
    if (e.getSource() == start){
        count = 0;
        Clock clock = new Clock();
        clock.timerstart();
        gettext();
        playername = username.getText();
        mainlayout.show(mainpanel,"gamep");
        count = 0;

    }

    else if (e.getSource() == gButton){
        String[] hoge = texts.get(0);
        String answer = text.getText();
        for (int i = 1, size = hoge.length-1; i < size; i++) {
            if(hoge[i].equals(" "+answer)){
                count++;
                countl.setText(Integer.valueOf(count).toString());
                texts.remove(0);
                gettext();
                
            }
        };
        text.setText("");
        mainlayout.show(mainpanel,"gamep");
        
    }
    else if (e.getSource() == treset){
        text.setText("");
    
    }
    else if (e.getSource() == backtitle){
        mainlayout.show(mainpanel,"titlep");
    }

}

class Clock {
    Timer timer = new Timer();
    void timerstart(){
        TimerTask task = new TimerTask() {
            public void run(){
                finish();
            }
        };
        timer.schedule(task, 10000);
        }

} 
}

