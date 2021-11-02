import javax.swing.*;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

public class Tetris extends JFrame implements KeyListener{
	
	JPanel j = new JPanel();
	JLabel l = new JLabel("Score");
	Color[][] a = new Color[10][20];
	int[][] index = new int[10][20]; 
	Point centerBlock =new Point(4,-1);
	ArrayList<Integer> Randompiece = new ArrayList<Integer>();
	Point[][][] pieceshape = {
							{//t
								{new Point(1, 0),new Point(0, 1),new Point(1, 1),new Point(2, 1) },
								{new Point(1, 0),new Point(1, 1),new Point(2, 1),new Point(1, 2) },
								{new Point(0, 1),new Point(1, 1),new Point(2, 1),new Point(1, 2) },
								{new Point(1, 0),new Point(0, 1),new Point(1, 1),new Point(1, 2) },
							},
							{//opposite L
								{new Point(1, 0),new Point(1, 1),new Point(1, 2),new Point(0, 2) },
								{new Point(0, 1),new Point(1, 1),new Point(2, 1),new Point(0, 0) },
								{new Point(1, 0),new Point(1, 1),new Point(1, 2),new Point(2, 0) },
								{new Point(0, 1),new Point(1, 1),new Point(2, 1),new Point(2, 2) }
							},
							{//I
								{new Point(0, 0),new Point(0, 1),new Point(0, 2),new Point(0, 3) },
								{new Point(0, 0),new Point(1, 0),new Point(2, 0),new Point(3, 0) },
								{new Point(1, 0),new Point(1, 1),new Point(1, 2),new Point(1, 3) },
								{new Point(0, 1),new Point(1, 1),new Point(2, 1),new Point(3, 1) },
							},
							{//z
								{new Point(0, 0),new Point(1, 0),new Point(1, 1),new Point(2, 1) },
								{new Point(1, 0),new Point(0, 1),new Point(1, 1),new Point(0, 2) },
								{new Point(0, 0),new Point(1, 0),new Point(1, 1),new Point(2, 1) },
								{new Point(1, 0),new Point(0, 1),new Point(1, 1),new Point(0, 2) }
							},
							{//square
								{new Point(0, 0),new Point(0, 1),new Point(1, 0),new Point(1, 1) },
								{new Point(0, 0),new Point(0, 1),new Point(1, 0),new Point(1, 1) },
								{new Point(0, 0),new Point(0, 1),new Point(1, 0),new Point(1, 1) },
								{new Point(0, 0),new Point(0, 1),new Point(1, 0),new Point(1, 1) }
							},
							{//s
								{new Point(1, 0),new Point(2, 0),new Point(0, 1),new Point(1, 1) },
								{new Point(0, 0),new Point(0, 1),new Point(1, 1),new Point(1, 2) },
								{new Point(1, 0),new Point(2, 0),new Point(0, 1),new Point(1, 1) },
								{new Point(0, 0),new Point(0, 1),new Point(1, 1),new Point(1, 2) }
							},
							{//normal L
								{new Point(1, 0),new Point(1, 1),new Point(1, 2),new Point(2, 2) },
								{new Point(0, 1),new Point(1, 1),new Point(2, 1),new Point(0, 2) },
								{new Point(1, 0),new Point(1, 1),new Point(1, 2),new Point(0, 0) },
								{new Point(0, 1),new Point(1, 1),new Point(2, 1),new Point(2, 0) },
							}
						 };
	int currentRotation=0;
	int currentshape=4;
	int scores=0;
	Color[] pieceColor = {Color.blue,Color.red,Color.cyan,Color.green,Color.magenta,Color.orange,Color.yellow};
	Tetris(){
		setBounds(100,30,500,800);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		addKeyListener(this);
		
		j.setBounds(getBounds());
		j.setVisible(true);
		j.setLayout(null);
		add(j);
		
		l.setBounds(550,100,100,30);
		l.setVisible(true);
		j.add(l);

		for(int i=0;i<10;i++) {
			for(int j=0;j<20;j++) {
				a[i][j]=Color.gray;
			}
		}
		for(int i=0;i<7;i++) {
			Randompiece.add(i);
		}
		shuffle();
	}
	public void paint(Graphics g) {
		g=j.getGraphics();
		for(int i=0;i<10;i++) {
			for(int j=0;j<20;j++) {
				g.setColor(a[i][j]);
				g.fillRect(20+31*i,40+31*j, 30, 30);
			}
		}
	}
	public void clearpart() {
		try {
			for(Point p:pieceshape[currentshape][currentRotation]) {
				index[centerBlock.x+p.x][centerBlock.y+p.y]=0;
				a[centerBlock.x+p.x][centerBlock.y+p.y]=Color.gray;
			}
		}catch(Exception e){
			
		}
	}
	public void shuffle() {
		Collections.shuffle(Randompiece);
		currentshape=Randompiece.get(0);
	}
	public void Left() {
		if(touchSide()!=0&&collidside()==false) {
			clearpart();
			centerBlock.x--;
			for(Point p:pieceshape[currentshape][currentRotation]) {
				a[centerBlock.x+p.x][centerBlock.y+p.y]=pieceColor[currentshape];
				repaint();
			}
		}
	}
	public void right() {
		if(touchSide()!=1&&collidside()==false) {
			clearpart();
			centerBlock.x++;
			for(Point p:pieceshape[currentshape][currentRotation]) {
				a[centerBlock.x+p.x][centerBlock.y+p.y]=pieceColor[currentshape];
				repaint();
			}
		}
	}
	public void down() {
		
	}
	public void rotate() {
		rotatexception();
		clearpart();
		currentRotation++;
		if(currentRotation>3) {
			currentRotation=0;
		}
		for(Point p:pieceshape[currentshape][currentRotation]) {
				a[centerBlock.x+p.x][centerBlock.y+p.y]=pieceColor[currentshape];
				repaint();
		} 
		
	}
	public boolean gameover() {
		for(int i=0;i<10;i++) {
			if(index[i][0]==1) {
				return true;
			}
		}
		return false;
	}
	public void clearow() throws InterruptedException {
		boolean percheck=false;
		for(int i=19;i>=0;i--) {
			boolean tempcheck=true;
			int row=0;
			if(percheck) {
				i=19;
				percheck=false;
			}
			for(int j=0;j<10;j++) {
				if(index[j][i]!=1) {
					tempcheck=false;
				}
			}
			if(tempcheck) {
				percheck=true;
				row=i;
				for(int k=row;k>=0;k--) {
					for(int j=0;j<10;j++) {
						try {
							a[j][k]=a[j][k-1];
							index[j][k]=index[j][k-1];
						}catch(Exception e) {
							a[j][k]=Color.gray;
							index[j][k]=0;
						}
					}
				}
				scores=scores+100;
			}
		}
	}
	public void keyTyped(KeyEvent e) {
	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_DOWN) {
			
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			Left();
		}else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			right();
		}else if(e.getKeyCode()==KeyEvent.VK_UP) {
			rotate();
		}else if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			try {
				directfall();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	public void keyReleased(KeyEvent e) {
	}
	public void fall() throws InterruptedException {
		while(true) {
			Thread.sleep(150);
			if(gameover()) {
				JOptionPane.showMessageDialog(null, "Score: "+scores, "Game over", JOptionPane.INFORMATION_MESSAGE);
				removeAll();
				break;
			}
			if(touch()) {
				Thread.sleep(150);
				clearow();
			}else {
				Thread.sleep(150);
				clearpart();
				centerBlock.y++;
			}
			
			for(Point p: pieceshape[currentshape][currentRotation]){
				a[centerBlock.x+p.x][centerBlock.y+p.y]=pieceColor[currentshape];
			}
			repaint();
		}
	}
	public void stabilize() {
		for(Point p:pieceshape[currentshape][currentRotation]) {
			index[centerBlock.x+p.x][centerBlock.y+p.y]=1;
		}
	}
	public boolean touch() throws InterruptedException {
		try {
			for(Point p:pieceshape[currentshape][currentRotation]) {
				if(index[centerBlock.x+p.x][centerBlock.y+p.y+1]==1) {
					Thread.sleep(50);
					stabilize();
					centerBlock.x=4;
					centerBlock.y=0;
					currentRotation=0;
					shuffle();
					return true;
				}
			}
		}catch(Exception e){
			for(Point p:pieceshape[currentshape][currentRotation]) {
				if(centerBlock.y+p.y==19) {
					Thread.sleep(50);
					stabilize();
					centerBlock.x=4;
					centerBlock.y=0;
					currentRotation=0;
					shuffle();
					return true;
				}
			}
		}
		return false;
	}
	public boolean collidside() {
		for(Point p:pieceshape[currentshape][currentRotation]) {
			try {
				if(index[centerBlock.x+p.x+1][centerBlock.y+p.y]==1) {
					return true;
				}
			}catch(Exception e){
				if(index[centerBlock.x+p.x-1][centerBlock.y+p.y]==1) {
					return true;
				}
			}
			try {
				if(index[centerBlock.x+p.x-1][centerBlock.y+p.y]==1) {
					return true;
				}
			}catch(Exception e) {
				if(index[centerBlock.x+p.x+1][centerBlock.y+p.y]==1) {
					return true;
				}
			}
		}
		return false;
	}
	public void directfall() throws InterruptedException {
		clearpart();
		for(int i=centerBlock.y;i<20;i++){
			if(touch2(i)) {
				centerBlock.y=i-1;
				break;
			}
		}
		for(Point p:pieceshape[currentshape][currentRotation]) {
			a[centerBlock.x+p.x][centerBlock.y+p.y]=pieceColor[currentshape];
		}
		touch();
	}
	public boolean touch2(int i) throws InterruptedException {
		try {
			for(Point p:pieceshape[currentshape][currentRotation]) {
				if(index[centerBlock.x+p.x][i+p.y+1]==1) {
					return true;
				}
			}
		}catch(Exception e){
			for(Point p:pieceshape[currentshape][currentRotation]) {
				if(i+p.y==19) {
					return true;
				}
			}
		}
		return false;
	}
	public int touchSide() {
		for(Point p:pieceshape[currentshape][currentRotation]) {
			if(centerBlock.x+p.x==0) {
				return 0;
			}else if(centerBlock.x+p.x==9) {
				return 1;
			}
		}
		return -1;
	}
	public void rotatexception() {
		int temp=currentRotation;
		int fix;
		temp++;
		if(temp>3) {
			temp=0;
		}
		for(Point p:pieceshape[currentshape][temp]) {
			if(centerBlock.x+p.x>9) {
				clearpart();
				fix=centerBlock.x+p.x-9;
				if(centerBlock.x-fix<centerBlock.x){
					centerBlock.x=centerBlock.x-fix;
				}
			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Tetris t = new Tetris();
		t.fall();
		
	}
}
