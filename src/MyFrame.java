import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyFrame extends JFrame implements KeyListener {
    JLabel label,label2;

    MyFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,500);
        this.setLayout(null);
        this.addKeyListener(this);

        label = new JLabel();
        label.setBounds(0,0,50,50);
        label.setBackground(Color.red);
        label.setOpaque(true);

        label2 = new JLabel();
        label2.setBounds(0,400,600,65);
        label2.setBackground(Color.black);
        label2.setOpaque(true);

        this.add(label);
        this.add(label2);
        this.setVisible(true);
    }


    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()){
            case 'w':label.setLocation(label.getX(),label.getY()-10);
                break;
            case 's':label.setLocation(label.getX(),label.getY()+10);
                break;
            case 'a':label.setLocation(label.getX()-10,label.getY());
                break;
            case 'd':label.setLocation(label.getX()+10,label.getY());
                break;
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println(e.getKeyChar());
        System.out.println(e.getKeyCode());

    }
}
