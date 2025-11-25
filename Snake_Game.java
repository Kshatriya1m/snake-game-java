
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

/**
 *
 * @author 91705
 */
class Snake_Game extends JFrame{
    
    public Snake_Game(){
        setTitle("Snake Game");
        setResizable(false);
        setSize(905,700);
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Design d= new Design();
        d.setBackground(Color.darkGray);
        add(d);
    }
    
    public static void main(String[] args) {
        new Snake_Game().setVisible(true);
    }
}
class Design extends JPanel implements ActionListener,KeyListener{
    
    private int[] Xpos={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    private int[] Ypos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
    
    private Random random= new Random();
    private int enemyX,enemyY;
    private int[] snakeXLength=new int[750];
    private int[] snakeYLength=new int[750];
    private int LengthOfSnake=3;
    private int move=0,score=0;
    
    private boolean left=false,right=true,up=false,down=false,play=false,gameOver=false;
    private ImageIcon snakeTitle=new ImageIcon("snakeTitle.jpg");
    private ImageIcon leftMouth=new ImageIcon("leftmouth.png");
    private ImageIcon rightMouth=new ImageIcon("rightmouth.png");
    private ImageIcon upMouth=new ImageIcon("upmouth.png");
    private ImageIcon downMouth=new ImageIcon("downmouth.png");
    private ImageIcon snakeimage=new ImageIcon("snakeimage.png");
    private ImageIcon enemy=new ImageIcon("enemy.png");

    private Timer timer;
    private int delay=100;
    public Design(){
        
        timer=new Timer(delay,this);
        timer.start();
        
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        
        newEnemy();
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        g.setColor(Color.white);
        g.drawRect(24, 10, 851, 55);
        g.drawRect(24, 74, 851, 576);
        
        snakeTitle.paintIcon(this, g, 25, 11);
        g.setColor(Color.black);
        g.fillRect(25, 75, 850, 575);
        
        if(move==0){
            snakeXLength[0]=100;
            snakeXLength[1]=75;
            snakeXLength[2]=50;
            
            snakeYLength[0]=100;
            snakeYLength[1]=100;
            snakeYLength[2]=100;
            move++;
        }
        if(left){
           leftMouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
        }
        if(right){
           rightMouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
        }
        if(up){
           upMouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
        }
        if(down){
           downMouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
        }
        
        for(int i=0;i<LengthOfSnake;i++){
            if(snakeXLength[i]==enemyX && snakeYLength[i]==enemyY)
            {
                enemyX=random.nextInt(34);
                enemyY=random.nextInt(23);
            }
            enemy.paintIcon(this, g, enemyX, enemyY);
        }
        if(gameOver){
            
            g.setColor(Color.white);
            g.setFont(new Font("Serif",Font.BOLD,50));
            g.drawString("Game Over", 300, 300);
            
            g.setFont(new Font("Serif",Font.BOLD,20));
            g.drawString("Press SPACE to Restart...", 320, 350);
        }
        
        g.setColor(Color.green);
        g.setFont(new Font("Serif",Font.BOLD,20));
        g.drawString("Score: "+score, 750, 35);
        g.drawString("Length : "+ LengthOfSnake, 750, 55);
        
        for(int i=1;i<LengthOfSnake;i++)
            snakeimage.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
        
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(play){
        for(int i=LengthOfSnake-1;i>0;i--){
            snakeXLength[i]=snakeXLength[i-1];
            snakeYLength[i]=snakeYLength[i-1];
        }
        if(left){
            snakeXLength[0]-=25;
        }
        if(right){
            snakeXLength[0]+=25;
        }
        if(up){
            snakeYLength[0]-=25;
        }
        if(down){
            snakeYLength[0]+=25;
        }
        if(snakeXLength[0]>850)
            snakeXLength[0]=25;
        if(snakeXLength[0]<25)
            snakeXLength[0]=850;
        if(snakeYLength[0]>625)
            snakeYLength[0]=75;
        if(snakeYLength[0]<75)
            snakeYLength[0]=625;
        
        collidesWithEnemy();
        collidesWithBody();
        repaint();
        }
    }

    

    @Override
    public void keyPressed(KeyEvent e) {
        
        if(e.getKeyCode()==KeyEvent.VK_LEFT && !right){
            move++;
            play=true;
            right=false;
            up=false;
            down=false;
            left=true;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && !left){
            move++;
            play=true;            
            left=false;
            up=false;
            down=false;
            right=true;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP && !down){
            move++;
            play=true;
            left=false;
            right=false;
            down=false;
            up=true;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN && !up){
            move++;
            play=true;
            left=false;
            right=false;
            up=false;
            down=true;
        }
        if(gameOver){
        if(e.getKeyCode()==KeyEvent.VK_SPACE)
            restart();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
    @Override
    public void keyTyped(KeyEvent e) {}

    private void newEnemy() {
        enemyX=Xpos[random.nextInt(34)];
        enemyY=Ypos[random.nextInt(23)];
        for(int i=0;i<LengthOfSnake-1;i++){
            if(enemyX==snakeXLength[0] && enemyY==snakeYLength[0])
                newEnemy();
        }
    }

    private void collidesWithEnemy() {
        if(enemyX==snakeXLength[0] && enemyY==snakeYLength[0]){
            newEnemy();
            score++;
            LengthOfSnake++;
        }
    }

    private void collidesWithBody() {
        for(int i=1;i<LengthOfSnake-1;i++){
            if(snakeXLength[0]==snakeXLength[i] && snakeYLength[0]==snakeYLength[i]){
                gameOver=true;
                timer.stop();
            }
        }
    }

    private void restart() {
        gameOver=false;
        move=0;
        score=0;
        LengthOfSnake=3;
        left=false;
        right=true;
        up=false;
        down=false;
        timer.start();
        repaint();
    }
}
