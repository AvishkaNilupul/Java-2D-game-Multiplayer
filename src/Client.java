import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client extends JFrame implements KeyListener {
    private long lastKeyPressTime = 0;
    private final long COOLDOWN_DURATION = 1000;

    private BufferedReader reader;
    private Socket socket;
    private PrintWriter writer;
    JLabel Player, botLabel, oPlayer, black2;
    List<JLabel> label3List,attackList;
    Timer timer;
    int score = 0;
    JLabel textlabel;


    Client() {

        try{
            Timer newTimer = new Timer(20, (event) -> {
                for (JLabel label3 : label3List) {
                    label3.setLocation(label3.getX(), label3.getY() - 5);
                    if (label3.getBounds().intersects(black2.getBounds())) {
                        this.remove(label3);
                        label3List.remove(label3);
                        break;
                    }
                    if (label3.getBounds().intersects(oPlayer.getBounds())){
                        this.remove(label3);
                        label3List.remove(label3);
                        score++;
                        textlabel.setText("Score:"+score);
                        break;
                    }
                }
            });newTimer.start();


            Timer newTimer2 = new Timer(20, (event) -> {
                for (JLabel attackL : attackList) {
                    attackL.setLocation(attackL.getX(), attackL.getY() +5);
                    if (attackL.getBounds().intersects(botLabel.getBounds())) {
                        this.remove(attackL);
                        attackList.remove(attackL);
                        break;
                    }
                    if (attackL.getBounds().intersects(Player.getBounds())){
                        this.remove(attackL);
                        attackList.remove(attackL);
                        score++;
                        textlabel.setText("Score:"+score);
                        break;
                    }
                }
            });
            newTimer2.start();


            socket = new Socket("localhost", 12345);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);


            new Thread(() -> {
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        if (line.equals("a")) {
                            if (oPlayer.getX()==550){
                                System.out.println("error");
                            }else {
                                SwingUtilities.invokeLater(() -> oPlayer.setLocation(oPlayer.getX()+10, oPlayer.getY() ));
                            }
                        } else if (line.equals("d")) {
                            if (oPlayer.getX()==0){
                                System.out.println("error");
                            }else {
                                SwingUtilities.invokeLater(()->oPlayer.setLocation(oPlayer.getX()-10,oPlayer.getY()));
                            }

                        } else if (line.equals("f")) {
                            System.out.println("correct");
                            JLabel attack = new JLabel();
                            attack.setBounds(oPlayer.getX(), oPlayer.getY(), 60, 50);
                            attack.setBackground(Color.RED);
                            attack.setOpaque(true);
                            this.add(attack);
                            attackList.add(attack);


                        } else {
                            System.out.println("error");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();


        }catch (Exception e){
            System.out.println(e);
        }

        this.setTitle("Player 1");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);
        this.setLayout(null);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'd') {
                    sendMessage("d");
                } else if (e.getKeyChar()=='a') {
                    sendMessage("a");

                } else if (e.getKeyChar()=='f') {
                    sendMessage("f");

                }
            }
        });


        Player = new JLabel();
        Player.setBounds(0, 350, 50, 50);
        Player.setBackground(Color.red);
        Player.setOpaque(true);
        oPlayer = new JLabel();
        oPlayer.setBounds(300, 65, 50, 50);
        oPlayer.setBackground(Color.blue);
        oPlayer.setOpaque(true);

        botLabel = new JLabel();
        botLabel.setBounds(0, 400, 600, 65);
        botLabel.setBackground(Color.black);
        botLabel.setOpaque(true);
        black2 = new JLabel();
        black2.setBounds(0, 0, 600, 65);
        black2.setBackground(Color.black);
        black2.setOpaque(true);

        textlabel= new JLabel("Score:"+Integer.toString(score));
        textlabel.setBounds(400, 410, 300, 50);
        textlabel.setFont(new Font("Arial",Font.PLAIN,30));
        textlabel.setForeground(Color.blue);

        this.add(textlabel);

        this.add(Player);
        this.add(black2);
        this.add(botLabel);
        this.add(oPlayer);
        attackList = new ArrayList<>();

        label3List = new ArrayList<>();

        timer = new Timer(0, null); // Timer initialization moved to keyTyped

        this.addKeyListener(this);
        this.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client::new);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        long currentTime = System.currentTimeMillis();

        if (e.getKeyChar() == 'w') {
            if (Player.getY() == 350) {
                Player.setLocation(Player.getX(), Player.getY() - 100);
            } else {
                System.out.println(Player.getY());
            }
        } else if (e.getKeyChar() == 'd') {
            if (Player.getX()==550){
                System.out.println("cannot move");
            }else {
                Player.setLocation(Player.getX() + 10, 350);
            }
        } else if (e.getKeyChar() == 'a') {
            if (Player.getX()==0){
                System.out.println("cannot move");
            }else {
                Player.setLocation(Player.getX() - 10, 350);

            }
        } else if (e.getKeyChar() == 'f' && currentTime - lastKeyPressTime >= COOLDOWN_DURATION) {
            lastKeyPressTime = currentTime;
            JLabel newLabel3 = new JLabel();
            newLabel3.setBounds(Player.getX(), Player.getY(), 60, 50);
            newLabel3.setBackground(Color.green);
            newLabel3.setOpaque(true);
            this.add(newLabel3);
            label3List.add(newLabel3);
            revalidate();



        }

    }

    private void sendMessage(String message) {
        writer.println(message);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
