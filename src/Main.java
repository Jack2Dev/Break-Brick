import javax.swing.JFrame;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class Main {

    public static void main(String[] args) {

        Gameplay gameplay = new Gameplay();
        JFrame janela = new JFrame();
        janela.setBounds(10, 10, 700, 600);
        janela.setTitle("Quebra Blocos");
        janela.setLocationRelativeTo(null);
        janela.setResizable(false);
        janela.setVisible(true);
        janela.setDefaultCloseOperation(janela.EXIT_ON_CLOSE);
        janela.add(gameplay);
    }

    }

