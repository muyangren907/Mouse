package Mouse;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Window;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
class MyImage {
  Toolkit tool=Toolkit.getDefaultToolkit();
}
class Num {
  private int num=0;
  public int getNum() {
    return num;
  }
  public void setNum(int num) {
    this.num = num;
  }
}
class Score {
  private int score=0,last=0,djs=60,sd=1000;
  public int getSd() {
    return sd;
  }
  public void setSd(int sd) {
    this.sd = sd;
  }
  private long starttime=0;
  public int getScore() {
    return score;
  }
  public void setScore(int score) {
    this.score = score;
  }
  public int getLast() {
    return last;
  }
  public void setLast(int last) {
    this.last = last;
  }
  public int getDjs() {
    return djs;
  }
  public void setDjs(int djs) {
    this.djs = djs;
  }
  public long getStarttime() {
    return starttime;
  }
  public void setStarttime(long starttime) {
    this.starttime = starttime;
  }
}
class MyPanel extends JPanel {
  public void paint(Graphics g1) {
    Graphics2D g=(Graphics2D)g1;
    super.paintComponents(g);
  }
}
public class Mouse {
  private JFrame frame;
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Mouse window = new Mouse();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
  public Mouse() {
    initialize();
  }
  private void initialize() {
    Toolkit tool=Toolkit.getDefaultToolkit(); //toolkit
    Dimension screen =tool.getScreenSize();//get ScreenSize
    ImageIcon  image=new ImageIcon("image/beijing.jpg");//background
    frame = new JFrame("打地鼠");
    frame.setType(Window.Type.UTILITY);
    frame.setBounds((int)(screen.getWidth()-image.getIconWidth())/2,(int)(screen.getHeight()-image.getIconHeight())/2,image.getIconWidth(),image.getIconHeight());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    JLayeredPane layeredPane=new JLayeredPane();
    MyPanel panel=new MyPanel();
    frame.add(panel);
    panel.setLayout(null);
    panel.setBounds(0,0,image.getIconWidth(),image.getIconHeight());
    JLabel jl=new JLabel(image);
    jl.setBounds(0,0,image.getIconWidth(),image.getIconHeight());
    panel.add(jl);
    layeredPane.add(panel,JLayeredPane.DEFAULT_LAYER);  //panel
    frame.setLayeredPane(layeredPane);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    ImageIcon dishu=new ImageIcon("image/dishu.jpg");//mouse icon
    ImageIcon kaishi=new ImageIcon("image/start.jpg");
    JButton btnStart = new JButton();
    btnStart.setIcon(kaishi);
    btnStart.setBounds(image.getIconWidth()/3+240+120, image.getIconHeight()/3+240+100-59, 125, 59);
    layeredPane.add(btnStart,JLayeredPane.MODAL_LAYER);
    int k=0;
    JButton[] but=new JButton[9];
    for(int i=image.getIconWidth()/3; i<=image.getIconWidth()/3+240; i+=120) {
      for(int j=image.getIconHeight()/3; j<=image.getIconHeight()/3+240; j+=120) {
        but[k]=new JButton();
        but[k].setBounds(i, j, 100, 100);
        but[k].setBackground(Color.gray);
        layeredPane.add(but[k],JLayeredPane.MODAL_LAYER);
        k++;
      }
    }
    Score score =new Score();
    JLabel djs =new JLabel("倒计时："); //Countdown
    djs.setBounds(image.getIconWidth()/3+240+120, image.getIconHeight()/3, 250, 59);
    layeredPane.add(djs,JLayeredPane.MODAL_LAYER);
    Font f1=new Font("隶书",1,30);
    djs.setFont(f1);

    JLabel jfb =new JLabel("得分：0"); //Scoreboard
    jfb.setBounds(image.getIconWidth()/3, image.getIconHeight()/3-80, 200, 59);
    layeredPane.add(jfb,JLayeredPane.MODAL_LAYER);
		Font f3=new Font("华文琥珀",0,30);
    jfb.setFont(f3);
    Timer timer=new Timer(score.getSd()-10,new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        but[score.getLast()].setIcon(null);
        score.setLast((int)(9*Math.random()));
        but[score.getLast()].setIcon(dishu);
      }
    });
    Timer time2=new Timer(2,new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        timer.setDelay(score.getSd());
        jfb.setText("得分："+score.getScore());
        djs.setText("倒计时："+(score.getStarttime()/1000+score.getDjs()-System.currentTimeMillis()/1000)+"s");
        if((score.getStarttime()/1000+score.getDjs()-System.currentTimeMillis()/1000)==0)
        {
          timer.stop();
          but[score.getLast()].setIcon(null);
          JOptionPane.showMessageDialog(frame, "Game Over\n 您的得分为："+score.getScore(),"得分信息",JOptionPane.PLAIN_MESSAGE);
          score.setScore(0);
          jfb.setText("得分：0");
        }
      }
    });
    Timer time3=new Timer(2,new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if((score.getStarttime()/1000+score.getDjs()-System.currentTimeMillis()/1000)==-1)
        {
          time2.stop();
          djs.setText("倒计时：");
        }
      }
    });
    btnStart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        score.setStarttime(System.currentTimeMillis());
        timer.start();
        time2.start();
        time3.start();
      }
    });
    Num num=new Num();
    but[0].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (but[0].getIcon() != null) {
          score.setScore(score.getScore() + 1);
          but[score.getLast()].setIcon(null);
          score.setLast((int) (9 * Math.random()));
          but[score.getLast()].setIcon(dishu);
        }
      }
    });
    but[1].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(but[1].getIcon()!=null) {
          score.setScore(score.getScore()+1);
          but[score.getLast()].setIcon(null);
          score.setLast((int)(9*Math.random()));
          but[score.getLast()].setIcon(dishu);
        }
      }
    });
    but[2].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(but[2].getIcon()!=null) {
          score.setScore(score.getScore()+1);
          but[score.getLast()].setIcon(null);
          score.setLast((int)(9*Math.random()));
          but[score.getLast()].setIcon(dishu);
        }
      }
    });
    but[3].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(but[3].getIcon()!=null) {
          score.setScore(score.getScore()+1);
          but[score.getLast()].setIcon(null);
          score.setLast((int)(9*Math.random()));
          but[score.getLast()].setIcon(dishu);
        }
      }
    });
    but[4].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(but[4].getIcon()!=null) {
          score.setScore(score.getScore()+1);
          but[score.getLast()].setIcon(null);
          score.setLast((int)(9*Math.random()));
          but[score.getLast()].setIcon(dishu);
          timer.restart();
        }
      }
    });
    but[5].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(but[5].getIcon()!=null) {
          score.setScore(score.getScore()+1);
          but[score.getLast()].setIcon(null);
          score.setLast((int)(9*Math.random()));
          but[score.getLast()].setIcon(dishu);
        }
      }
    });
    but[6].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(but[6].getIcon()!=null) {
          score.setScore(score.getScore()+1);
          but[score.getLast()].setIcon(null);
          score.setLast((int)(9*Math.random()));
          but[score.getLast()].setIcon(dishu);
        }
      }
    });
    but[7].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(but[7].getIcon()!=null) {
          score.setScore(score.getScore()+1);
          but[score.getLast()].setIcon(null);
          score.setLast((int)(9*Math.random()));
          but[score.getLast()].setIcon(dishu);
        }
      }
    });
    but[8].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(but[8].getIcon()!=null) {
          score.setScore(score.getScore()+1);
          but[score.getLast()].setIcon(null);
          score.setLast((int)(9*Math.random()));
          but[score.getLast()].setIcon(dishu);
        }
      }
    });
    JMenuBar bar=new JMenuBar();
    bar.setBounds(0, 0, image.getIconWidth(), 40);
    Font f2=new Font("华文行楷",0,40);//Front
    bar.setOpaque(true);
    layeredPane.add(bar,JLayeredPane.MODAL_LAYER);
    JMenu shezhi=new JMenu("设置");
    shezhi.setFont(f2);
    bar.add(shezhi);
    JMenuItem sudu=new JMenuItem("速度选择");
    sudu.setFont(f2);
    shezhi.add(sudu);
    sudu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Object[] options = {"极速","快速","中速","慢速"};
        int response=JOptionPane.showOptionDialog(frame, "选择地鼠出现的速度", "速度选择",JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if(response==0)
        {
          score.setSd(300);
        }
        else if(response==1)
        {
          score.setSd(500);
        }
        else if(response==2)
        {
          score.setSd(1000);
        }
        else {
          score.setSd(2000);
        }
      }
    });
    JMenuItem daojishi=new JMenuItem("游戏时间");
    daojishi.setFont(f2);
    shezhi.add(daojishi);
    daojishi.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Object[] possibleValues = { 1,2,3,4,5,6,7,8,9,10};
        Object selectedValue = JOptionPane.showInputDialog(frame, "选择需要的时间(单位：min)", "游戏时间",JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
        score.setDjs(60*(int)selectedValue);
      }
    });
    JMenu bangzhu=new JMenu("帮助");
    bangzhu.setFont(f2);
    bar.add(bangzhu);
    JMenuItem rule=new JMenuItem("游戏规则");
    rule.setFont(f2);
    bangzhu.add(rule);
    rule.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "Ⅰ、你可通过设置调节游戏速度和游戏时长\nⅡ、每次出现一个地鼠，用鼠标点击地鼠即可得分，时间结束会弹出得分窗口\n默认速度：中速，默认时间：1min","游戏规则",JOptionPane.PLAIN_MESSAGE);
      }
    });
    JMenuItem guanyu=new JMenuItem("关于");
    guanyu.setFont(f2);
    bangzhu.add(guanyu);
    guanyu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "软件名：打地鼠\n制作时间：2017年4月28日\n制作人：muyangren907","版权信息",JOptionPane.PLAIN_MESSAGE);
      }
    });
    frame.setVisible(true);
  }
}
