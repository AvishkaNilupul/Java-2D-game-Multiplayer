import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Test extends JFrame implements KeyListener {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;
    JLabel label, label2, label4, black2;
    List<JLabel> label3List;
    Timer timer;
    int score = 0;
    JLabel textlabel;

    Test() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);
        this.setLayout(null);

        label = new JLabel();
        label.setBounds(0, 0, 50, 50);
        label.setBackground(Color.red);
        label.setOpaque(true);
        label4 = new JLabel();
        label4.setBounds(100, 200, 50, 50);
        label4.setBackground(Color.blue);
        label4.setOpaque(true);

        label2 = new JLabel();
        label2.setBounds(0, 400, 600, 65);
        label2.setBackground(Color.black);
        label2.setOpaque(true);
        black2 = new JLabel();
        black2.setBounds(0, 0, 600, 65);
        black2.setBackground(Color.black);
        black2.setOpaque(true);

        textlabel= new JLabel("Score:"+Integer.toString(score));
        textlabel.setBounds(400, 410, 300, 50);
        textlabel.setFont(new Font("Arial",Font.PLAIN,30));
        textlabel.setForeground(Color.blue);

        this.add(textlabel);

        this.add(label);
        this.add(black2);
        this.add(label2);
        this.add(label4);

        label3List = new ArrayList<>();

        timer = new Timer(0, null); // Timer initialization moved to keyTyped

        this.addKeyListener(this);
        this.setVisible(true);
    }

    private void moveLabelDown() {
        int labelBottom = label.getY() + label.getHeight();
        int label2Top = label2.getY();

        if (labelBottom < label2Top) {
            label.setLocation(label.getX(), label.getY() + 5);
        } else {
            timer.stop();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Test::new);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'w') {
            if (label.getY() == 350) {
                label.setLocation(label.getX(), label.getY() - 100);
            } else {
                System.out.println(label.getY());
            }
        } else if (e.getKeyChar() == 'd') {
            label.setLocation(label.getX() + 10, 350);
        } else if (e.getKeyChar() == 'a') {
            label.setLocation(label.getX() - 10, 350);
        } else if (e.getKeyChar() == 'f') {

            JLabel newLabel3 = new JLabel();
            newLabel3.setBounds(label.getX(), label.getY(), 60, 50);
            newLabel3.setBackground(Color.green);
            newLabel3.setOpaque(true);
            this.add(newLabel3);
            label3List.add(newLabel3);

            Timer newTimer = new Timer(0, (event) -> {
                for (JLabel label3 : label3List) {
                    label3.setLocation(label3.getX(), label3.getY() - 5);
                    if (label3.getBounds().intersects(black2.getBounds())) {
                        this.remove(label3);
                        label3List.remove(label3);
                        break;
                    }
                    if (label3.getBounds().intersects(label4.getBounds())){
                        this.remove(label3);
                        label3List.remove(label3);
                        score++;
                        textlabel.setText("Score:"+score);
                        break;
                    }
                }
            });
            newTimer.start();
        }

        if (!timer.isRunning()) {
            timer = new Timer(0, (event) -> moveLabelDown());
            timer.start();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
