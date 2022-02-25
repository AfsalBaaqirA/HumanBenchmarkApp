package oops;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
public class HumanBencmarkApp{
    JFrame AppWindow;
    JLabel Title, bg;
    JButton Game1, Game2,Instructions, Credits, Exit;
    public HumanBencmarkApp(){
        AppWindow = new JFrame("Human Benchmark");
        AppWindow.setSize(400, 400);
        JPanel Content = new JPanel();
        Content.setBorder(new EmptyBorder(10, 10, 10, 10));
        Content.setLayout(new GridBagLayout());
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        AppWindow.setLayout(new BoxLayout(AppWindow.getContentPane(),BoxLayout.Y_AXIS));
        Title = new JLabel("<html><h1><strong>Human Benchmark App</strong></h1><br></html>");
        Game1 = new JButton("<html><h4>Aim Trainer Test</h4></html>");
        Game2 = new JButton("<html><h4>Word Match Test</h4></html>");
        Instructions = new JButton("<html><h4>Instructions</h4></html>");
        Credits = new JButton("<html><h4>Credits</h4></html>");
        Exit = new JButton("<html><h4>Exit</h4></html>");
        Game1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                AppWindow.setVisible(false);
                new AimTrainerWindow();
            }
        });
        Game2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                AppWindow.setVisible(false);
                new Mini();
            }
        });
        Instructions.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                AppWindow.setVisible(false);
                new InstructionWindow();
            }
        });
        Credits.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                AppWindow.setVisible(false);
                new CreditWindow();
            }            
        });
        Exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                AppWindow.dispose();
            }
        });
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        Content.add(Title, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel Buttons = new JPanel(new GridBagLayout());
        Buttons.add(Game1, gbc);
        Buttons.add(Game2, gbc);
        Buttons.add(Instructions, gbc);
        Buttons.add(Credits, gbc);
        Buttons.add(Exit, gbc);
        gbc.weighty = 1;
        Content.add(Buttons);
        AppWindow.add(Content);
        AppWindow.setVisible(true);
        AppWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public class AimTrainerWindow{
        JFrame AimFrame;
        JLabel msg, time;
        float accuracy = 100;
        int targets = 0;
        long start, end, timeTaken;
        AimTrainerWindow(){
            AimFrame =  new JFrame("Aim Trainer");
            msg = new JLabel("<html><h3>Click all the Buttons to win!</h3></html>");
            time = new JLabel("");
            AimFrame.setLayout(new GridBagLayout());
            GridBagConstraints gbca = new GridBagConstraints();
            gbca.gridwidth = GridBagConstraints.REMAINDER;
            gbca.anchor = GridBagConstraints.NORTH;
            AimFrame.add(msg);
            AimFrame.setSize(400, 400);
            AimFrame.setVisible(true);
            AimFrame.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent ev){
                    AimFrame.getContentPane().remove(msg);
                    AimFrame.repaint();
                    start = new Date().getTime();
                    Aimer();
                    AimFrame.removeMouseListener(this);
                }
            });
            AimFrame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                AppWindow.setVisible(true);
                AimFrame.setVisible(false);
            }
        });
        }
        int random(){ 
            return (int)(Math.random() * (180 - 10 + 1) + 150);
        }
        public void Aimer(){
            JButton target = new JButton();
            target.setBounds(random(), random(), 30, 30);
            AimFrame.add(target);
            AimFrame.repaint();
            MouseAdapter FClick = new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent ev){
                    accuracy = (float) (accuracy - (accuracy / 33.33));
                }
            };
            AimFrame.addMouseListener(FClick);
            ActionListener TClick = new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    AimFrame.getContentPane().remove(target);
                    AimFrame.repaint();
                    targets = targets + 1;
                    if (targets == 30){
                        String n = String.valueOf(accuracy)+"%";
                        msg.setText("<html><h3>You can now Exit the window<h3></html>");
                        AimFrame.add(msg);
                        AimFrame.repaint();
                        end = new Date().getTime();
                        timeTaken = end - start;
                        JOptionPane.showMessageDialog(AimFrame, "Accuracy is : "+n+" \n Average Time Taken : "+timeTaken/30+"ms");
                        AimFrame.removeMouseListener(FClick);
                    }
                    else{
                        for(ActionListener al : target.getActionListeners()){
                        target.removeActionListener(al);
                        }
                        Aimer();
                        AimFrame.removeMouseListener(FClick);
                    }
                    return;
                }
            };
            target.addActionListener(TClick);
        }
    }
    public class InstructionWindow{
        JFrame InstructionFrame;
        
        JTextArea instruct;
        public InstructionWindow(){
            InstructionFrame = new JFrame("Instructions");
            instruct = new JTextArea();
            instruct.setBounds(10,30, 200,200);
            instruct.setText("Instructions:\nAfter the game begins you have to click on all\n30 targets (Buttons here) as fast as\npossible and your average time between\neach response is calulated.\nIf you miss an target your accuracy goes\ndown from 100%\nBEST OF LUCK !!!");
            instruct.setEditable(false);
            InstructionFrame.setLayout(new GridBagLayout());
            GridBagConstraints gbci = new GridBagConstraints();
            gbci.gridwidth = GridBagConstraints.REMAINDER;
            gbci.anchor = GridBagConstraints.NORTH;
            InstructionFrame.add(instruct);
            InstructionFrame.setSize(400, 400);
            InstructionFrame.setVisible(true);
            InstructionFrame.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e){
                AppWindow.setVisible(true);
                InstructionFrame.setVisible(false);
            }
            });
        }
    }
    public class CreditWindow{
        JFrame CreditsFrame;
        JLabel Credit, Credit1, Credit2, Credit3;
        CreditWindow(){
            CreditsFrame = new JFrame("Credits");
            Credit = new JLabel("<html><h2>Made By :</h2></html>");
            Credit1 = new JLabel("<html><h2>Afsal Baaqir A</h2></html>");
            Credit2 = new JLabel("<html><h2>Afreeth S</h2></html>");
            Credit3 = new JLabel("<html><h2>Sanjay Karthick</h2></html>");
            CreditsFrame.setLayout(new GridBagLayout());
            GridBagConstraints gbcc = new GridBagConstraints();
            gbcc.gridwidth = GridBagConstraints.REMAINDER;
            gbcc.anchor = GridBagConstraints.NORTH;
            CreditsFrame.add(Credit, gbcc);
            CreditsFrame.add(Credit1, gbcc);
            CreditsFrame.add(Credit2, gbcc);
            CreditsFrame.add(Credit3, gbcc);
            CreditsFrame.setSize(400, 400);
            CreditsFrame.setVisible(true);
            CreditsFrame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                AppWindow.setVisible(true);
                CreditsFrame.setVisible(false);
            }
        });
        }
    }
    public class Mini implements ActionListener {
	JFrame frame = new JFrame("Word Match");
	JPanel field = new JPanel();
	JPanel menu = new JPanel();
	JPanel menu2 = new JPanel();
	JPanel menu3 = new JPanel();
	JPanel mini = new JPanel();
	JPanel start_screen = new JPanel();
	JPanel end_screen = new JPanel();
	JPanel instruct_screen = new JPanel();
	JButton btn[] = new JButton[20];
	JButton start = new JButton("Lets_Start");
        JButton over = new JButton("Are_You_Giving_Up");
        JButton easy = new JButton("Child_level");
        JButton hard = new JButton("Hard_Core");
        JButton inst = new JButton("Guide_Lines");
        JButton redo = new JButton("Another_Try");
        JButton goBack = new JButton("Main_Menu");
    Random randomGenerator = new Random();
    private boolean purgatory = false;
	JLabel winner;
	Boolean game_over = false;
	int level=0;
	int score=0;
	String[] board;
	int[] boardQ=new int[20];
	Boolean shown = true;
	int temp=30;
	int temp2=30;
	String a[]=new String[10];
	boolean eh=true;
	private JLabel label = new JLabel("Level 1 to 10");
	private JTextField text = new JTextField(10);
	private JTextArea instructM = new JTextArea("When the game begins, the screen will be filled\nwith pairs of buttons.\n Memorize their placement.\nOnce you press any button, they will all clear. \n Your goal is to click the matching buttons in a row.\nWhen you finish that, you win.\nEvery incorrect click gives you a point (those are bad).\n GOOD LUCK! \n"+"for a single level: enter a level between 1 and 10,\nselect easy or hard, then press start.");
	//instructM.setEditable(false);
	//instructW.setEditable(false);
	//instructM.setLineWrap(true);
	//instructW.setWrapStyleWord(true);
	public Mini(){
		frame.setSize(500,300);
		frame.setLocation(500,300);
		frame.setLayout(new BorderLayout());
		start_screen.setLayout(new BorderLayout());
		menu.setLayout(new FlowLayout(FlowLayout.CENTER));
		menu2.setLayout(new FlowLayout(FlowLayout.CENTER));
		menu3.setLayout(new FlowLayout(FlowLayout.CENTER));
		mini.setLayout(new FlowLayout(FlowLayout.CENTER));
		start_screen.add(menu, BorderLayout.NORTH);
		start_screen.add(menu3, BorderLayout.CENTER);
		start_screen.add(menu2, BorderLayout.SOUTH);
		menu3.add(mini, BorderLayout.CENTER);
		menu.add(label);
		menu.add(text);
		mini.add(easy, BorderLayout.NORTH);
		mini.add(hard, BorderLayout.NORTH);
		mini.add(inst, BorderLayout.SOUTH);
		start.addActionListener(this);
		start.setEnabled(true);
		menu2.add(start);
		over.addActionListener(this);
		over.setEnabled(true);
		menu2.add(over);
		easy.addActionListener(this);
		easy.setEnabled(true);
		hard.addActionListener(this);
		hard.setEnabled(true);
		inst.addActionListener(this);
		inst.setEnabled(true);
		frame.add(start_screen, BorderLayout.CENTER);
		frame.setVisible(true);
                frame.addWindowListener(new WindowAdapter(){
                    @Override
                    public void windowClosing(WindowEvent e){
                    AppWindow.setVisible(true);
                    frame.setVisible(false);
            }
                });
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	
	public void setUpGame(int x,Boolean what){
		level=x;
		clearMain();
		
		board = new String[2*x];
		for(int i=0;i<(x*2);i++){
			btn[i] = new JButton("");
			btn[i].setBackground(new Color(220, 220, 220));
			btn[i].addActionListener(this);
			btn[i].setEnabled(true);
			field.add(btn[i]);
		}
		String[] b = {"Afsal","Tom Holland","Afreeth","DoberMan","Batman","Sanjay","Joker","Keenu Reevs","Deadpool","Test"};//harder version
		String[] c = {"Afsal","Tom Holland","Afreeth","DoberMan","Batman","Sanjay","Joker","Keenu Reevs","Deadpool","Test"};//easier version
		if(what) a=c;//if what is true, make the game easy and use c
		else a=b;//otherwise make it hard and use b
		for(int i=0;i<x;i++){
				for(int z=0;z<2;z++){
					while(true){	
						int y = randomGenerator.nextInt(x*2);
						if(board[y]==null){
							btn[y].setText(a[i]);
							board[y]=a[i];
							break;
						}
					}
				}	
		}
		createBoard();
	}
	public void hideField(int x){//this sets all the boxes blank
		for(int i=0;i<(x*2);i++){
			if(boardQ[i]==0)
                            btn[i].setText("");		
		}
		shown=false;
	}
	public void switchSpot(int i){
		if(board[i]!="done"){
		if(btn[i].getText()==""){
			btn[i].setText(board[i]);
		} else {
			btn[i].setText("");
		}
		}
	}
	public void showSpot(int i){
		btn[i].setText(board[i]);
	}
	public void showField(int x, String a[]){
		for(int i=0;i<(x*2);i++){
			btn[i].setText(a[i]);
		}
		shown=true;
	}
	void waitABit(){
		try{
			Thread.sleep(5);
		} catch(Exception e){
                }
	}
	public boolean checkWin(){
		for(int i=0;i<(level*2);i++){
			if (board[i]!="done")return false;
		}
		winner();
		return true;
	}
	public void winner(){
			start_screen.remove(field);
			start_screen.add(end_screen, BorderLayout.CENTER);
			end_screen.add(new TextField("You Win"), BorderLayout.NORTH);
			end_screen.add(new TextField("Score: " + score), BorderLayout.SOUTH);
			end_screen.add(goBack);
			goBack.setEnabled(true);
			goBack.addActionListener(this);	
	}
	public void goToMainScreen(){
		new Mini();
	}
	public void createBoard(){//this is just gui stuff to show the board
		field.setLayout(new BorderLayout());
		start_screen.add(field, BorderLayout.CENTER);
		field.setLayout(new GridLayout(5,4,2,2));
		field.setBackground(Color.black);
		field.requestFocus();
	}
	public void clearMain(){
            start_screen.remove(menu);
            start_screen.remove(menu2);
            start_screen.remove(menu3);
            start_screen.revalidate();
            start_screen.repaint();
	}
public void actionPerformed(ActionEvent click){
		Object source = click.getSource();
		if(purgatory){
			switchSpot(temp2);
			switchSpot(temp);
			score++;
			temp=(level*2);
			temp2=30;
			purgatory=false;
		}
		if(source==start){
			try{
			level = Integer.parseInt(text.getText());
			} catch (Exception e){
				level=1;
			}
			setUpGame(level, eh);//level between 1 and 2, eh is true or false
		}
		if(source==over){//quits
			System.exit(0);
		}
		if(source==inst){//this just sets the instruction screen
			clearMain();
			start_screen.add(instruct_screen, BorderLayout.NORTH);
			JPanel one = new JPanel();
			one.setLayout(new FlowLayout(FlowLayout.CENTER));
			JPanel two = new JPanel();
			two.setLayout(new FlowLayout(FlowLayout.CENTER));
			instruct_screen.setLayout(new BorderLayout());
			instruct_screen.add(one, BorderLayout.NORTH);
			instruct_screen.add(two, BorderLayout.SOUTH);
			one.add(instructM);
			two.add(goBack);
			goBack.addActionListener(this);
			goBack.setEnabled(true);
		}
		if(source==goBack){//backt to main screen
		    frame.dispose();  
		    goToMainScreen();
		}
		if(source==easy){//sets the type. ex. if easy is clicked it turns blue and hard remains black
			eh=true;
			easy.setForeground(Color.BLUE);
			hard.setForeground(Color.BLACK);
		} else if(source==hard){
			eh=false;
			hard.setForeground(Color.BLUE);
			easy.setForeground(Color.BLACK);
                }
		for(int i =0;i<(level*2);i++){//gameplay when a button is pressed
			if(source==btn[i]){
				if(shown){
					hideField(level);//if first time, hides field
				}else{//otherwise play
					switchSpot(i);
					if(temp>=(level*2)){
						temp=i;
					} else {
						if((board[temp]!=board[i])||(temp==i)){
							temp2=i;
							purgatory=true;		
						} else {
							board[i]="done";
							board[temp]="done";
							checkWin();
							temp=(level*2);
						}		
					}
				}	
			}	
		}
}
}
    public static void main(String[] args){
        new HumanBencmarkApp();
    }
}