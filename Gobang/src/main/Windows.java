package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Windows extends JFrame implements MouseListener {
    public Core core;
    private static final long serialVersionUID = 1L;
    private int var = 1;
    //iBuffer和gBuffer是为了消除调用repaint()时出现的闪烁
    private Image iBuffer;
    private Graphics gBuffer;
    public Windows(String title) {
        super(title);
        core = new Core(19, 19);
        this.setSize(800, 600);
        this.setLocation(800, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗口时保证JVM退出
        this.setVisible(true);
        this.setResizable(false);//窗口大小不可改变
        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        if(iBuffer==null){
            iBuffer=createImage(this.getSize().width,this.getSize().height);
            gBuffer=iBuffer.getGraphics();
        }
        // TODO Auto-generated method stub
        super.paint(gBuffer);
        // 横
        for (int i = 0; i < 19; i++)
            gBuffer.drawLine(30, 30 + i * 30, 570, 30 + i * 30);
        // 竖线
        for (int i = 0; i < 19; i++)
            gBuffer.drawLine(30 + i * 30, 60, 30 + i * 30, 570);

        int[][] board = core.getCore();
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                if (board[i][j] == 1)
                    gBuffer.drawOval(20 + i * 30, 50 + j * 30, 20, 20);
                if(board[i][j]==2)
                    gBuffer.fillOval(20+i*30, 50+j*30, 20, 20);
            }
        }
        gBuffer.setFont(new Font("宋体", 0, 14));
        gBuffer.drawRect(690,60, 50, 30);
        gBuffer.drawString("悔棋",700,80);
        gBuffer.drawRect(690,120,50, 30);
        gBuffer.drawString("开始",700,140);
        gBuffer.drawRect(690,180,50, 30);
        gBuffer.drawString("设置",700,200);
        g.drawImage(iBuffer, 0, 0, iBuffer.getWidth(null), iBuffer.getHeight(null), null);
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        if (e.getX() < 570 && e.getY() < 570) {
            int a = core.ChessIt(_CgetX(e.getX()), (_CgetY(e.getY())), var);
            this.repaint();
            if (a == 1) {
                JOptionPane.showMessageDialog(null,"白的赢了", "恭喜", JOptionPane.DEFAULT_OPTION);;
            }
            if(a==2) {
                JOptionPane.showMessageDialog(null,"黑的赢了", "恭喜", JOptionPane.DEFAULT_OPTION);;
            }
            if(a!=-1) {
                if(var==1) var=2;
                else if(var==2) var=1;
            }
        }
        else if(e.getX()>690&&e.getX()<760&&e.getY()>60&&e.getY()<90) {
            core.RetChess();
            if(var==1) var=2;
            else if(var==2) var=1;
            this.repaint();
        }
        if(e.getX()>690&&e.getX()<760&&e.getY()>120&&e.getY()<150) {
            core.Restart();
            this.repaint();
        }
        if(e.getX()>690&&e.getX()<760&&e.getY()>180&&e.getY()<210) {
            Object[] options = {"白先","黑先"};
            int n = JOptionPane.showOptionDialog(null,"白先还是黑先？","游戏设置",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null,options,options[0]);
            if(n==0) this.var=1;
            if(n==1) this.var=2;
            this.core.Restart();
            this.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    private int _CgetX(int x) {
        x -= 30;
        if (x % 15 <= 7)
            return x / 30;
        else
            return x / 30 + 1;
    }

    private int _CgetY(int y) {
        y -= 60;
        if (y % 15 <= 7)
            return y / 30;
        else
            return y / 30 + 1;
    }
}