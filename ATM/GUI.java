package ATM;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

final public class GUI{
	private static GUI instance = new GUI();; 
	private static MyFrame ATMwindow;

	private JPanel Panel;
	private static JTextArea MainDisplay;
	private static JTextArea PrintDisplay;
	private static JPasswordField PasswordField;
	private static JTextField ChooseCardField;
	
	private static JButton b1;
	private static JButton b2;
	private static JButton b3;
	private static JButton b4;
	private static JButton b5;
	private static JButton b6;
	private static JButton b7;
	private static JButton b8;
	private static JButton b9;
	private static JButton b0;
	private static JButton Cancel;
	private static JButton Correction;
	private static JButton Enter;
	private static JButton EXIT;
	private static JButton InputCard;

	private static JLabel ChooseCardLabel;
	private static JButton YesPrint;
	private static JButton NoPrint;
	
	private static final int h = 670; 
	private static final int w = 650; 
	
	private final Color FrameColor = new Color(0,100,0);
	private final Color DisplaysColor = new Color(143,188,143);
	private final Color ExitButtonColor = new Color(162,205,90);
	private final Color MenuButtonColor = new Color(152,251,152);
	private final Color PasswordFieldColor = new Color(107,142,35);
	private final static Color LabelForeground = ChooseCardLabel.getForeground();
	private final ActionListener BPressed  = new KeyboardListener();
	
	
	private static final String Welcome = "\n\n\n                   Welcome\n " +"         to the ATM Simulator";
	private static final String idle = "";
	
	private static JButton m1;
	private static JButton m2;
	private static JButton m3;
	private static JButton m4;
	private static JButton m5;
	
	private static JButton OK1;
	private static JButton OK2;
	private static JButton OK3;
	
	private static byte guess = 0;
	private static JPasswordField OldPwdField;
	private static JPasswordField NewPwdField1;
	private static JPasswordField NewPwdField2;
	private static String OldPwd;
	private static String NewPwd1;
	private static String NewPwd2;
		
	
	public static GUI getInstance(){ return instance; }
	
	private GUI() {
		ATMwindow = new MyFrame();
		ATMwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ATMwindow.setVisible(true);
		initMoney();
	}

    private static void initMoney(){
	   boolean init = false;
	   Integer num = 0;
	   String money = null;
	   while (init == false) {
		   money = JOptionPane.showInputDialog(ATMwindow, "Введіть кількість купюр по 10 грн (максимум: 5000)", "Валюта", 1);
		   if (money == null || money.equals("")) {JOptionPane.showMessageDialog(ATMwindow, 
					    "Ви не ввели кількість готівки", "Помилка", JOptionPane.ERROR_MESSAGE);
		   int c = JOptionPane.showConfirmDialog(ATMwindow, "Завершити роботу?");
		   if (c==0) {ATMwindow.dispose(); break; };
		   
		   } else {
		   try {
			   num = Integer.parseInt(money);
			   if (num<=0) JOptionPane.showMessageDialog(ATMwindow, 
					    "Ви ввели некоректне число", "Помилка", JOptionPane.ERROR_MESSAGE); else
			   if (num>5000) JOptionPane.showMessageDialog(ATMwindow, 
					    "Ви ввели занадто велике число", "Помилка", JOptionPane.ERROR_MESSAGE); else
			   {
			   init = true;
			   }
		   }
		   catch (Exception e){
			   JOptionPane.showMessageDialog(ATMwindow, 
					    "Ви ввели некоректне число", "Помилка", JOptionPane.ERROR_MESSAGE);
		   }
					    	
		   
					    }
		   }

	   ATM.setMoneyCount10(num);
	   ATM.setTotalCash(10*num);
   }
	
	private class MyFrame extends JFrame{
		private static final long serialVersionUID = 1L;
		public MyFrame(){
			setTitle("ATM Simulator");
			setSize(h, w);
			setLocation(5,5);
			setResizable(false);
			Container c = getContentPane();
			Panel = new MyPanel();
			Panel.setBackground(FrameColor);
			c.add(Panel);	
			
		}
	}

	private class MyPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		private MyPanel(){  //створюємо всі елементи: клавіатура, екран ...
			setLayout(null);
			
			addPasswordField();
			addMainDisplay();
			addPrintDisplay(); 
			addKeyboard();
			addReadCard();
			initSettingElements();
			initFirstMenu();
			chooseCard();     //вибираємо карту для початку роботи з АТМ	
		}

	private void addMainDisplay(){
		MainDisplay = new JTextArea(Welcome);
	    MainDisplay.setLocation(10, 10);
	    MainDisplay.setFont(new Font("Century Gothic", Font.BOLD, 20));
	    MainDisplay.setSize(400, 300);
	    MainDisplay.setLineWrap(true);
	    MainDisplay.setEditable(false);
	    MainDisplay.setBackground(DisplaysColor);
        add(MainDisplay);
	}
	
	private void addPasswordField(){
		PasswordField = new JPasswordField();
		PasswordField.setEchoChar('*');
		PasswordField.setSize(100, 50);
		PasswordField.setLocation(150, 130);
		PasswordField.setHorizontalAlignment(JPasswordField.CENTER);
	    PasswordField.setBackground(PasswordFieldColor);
	    PasswordField.setEditable(false);
		PasswordField.setVisible(false);
        add(PasswordField);
	}
	
	private void addPrintDisplay(){
		PrintDisplay = new JTextArea(idle);
		PrintDisplay.setLocation(450, 10);
		PrintDisplay.setSize(200, 300);
		PrintDisplay.setEditable(false);
		PrintDisplay.setBackground(DisplaysColor);
        add(PrintDisplay);
	}
	
	private void addReadCard(){
		 ChooseCardField = new JTextField();
		 ChooseCardField.setColumns(2);
		 ChooseCardField.setLocation(70,150);
		 ChooseCardField.setSize(280,50);
		 ChooseCardField.setBackground(DisplaysColor);
		 ChooseCardField.setHorizontalAlignment(JPasswordField.CENTER);
		 ChooseCardField.setEditable(false);

		 ChooseCardLabel = new JLabel("Введіть номер картки:");
		 ChooseCardLabel.setLocation(50, 100);
		 ChooseCardLabel.setSize(500,30);
		 ChooseCardLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		 ChooseCardLabel.setForeground(Color.black);
		 ChooseCardField.addActionListener(new KeyboardListener());
		 
		 add(ChooseCardLabel);
		 add(ChooseCardField);
		 
	}
		
	private void addKeyboard(){
		//ActionListener BPressed = new ButtonPressed();
		 b1 = addButton("1", 50,50,70,400); 
		 b1.addActionListener(BPressed);
		 b2 = addButton("2", 50,50,120,400);
		 b2.addActionListener(BPressed);
		 b3 = addButton("3", 50,50,170,400);
		 b3.addActionListener(BPressed);
		 b4 = addButton("4", 50,50,70,450);
		 b4.addActionListener(BPressed);
		 b5 = addButton("5", 50,50,120,450);
		 b5.addActionListener(BPressed);
		 b6 = addButton("6", 50,50,170,450);
		 b6.addActionListener(BPressed);
		 b7 = addButton("7", 50,50,70,500);
		 b7.addActionListener(BPressed);
		 b8 = addButton("8", 50,50,120,500);
		 b8.addActionListener(BPressed);
		 b9 = addButton("9", 50,50,170,500);
		 b9.addActionListener(BPressed);
		 b0 = addButton("0", 50,50,120,550);
		 b0.addActionListener(BPressed);
		
		 Correction = addButton("CORRECTION", 120,50,230,450);
		 Correction.addActionListener(BPressed);
		 Enter = 	 addButton("ENTER", 120,50,230,500);
		 Enter.addActionListener(new ActionListener() {
			private JFrame controllingFrame; 
			@Override
			public void actionPerformed(ActionEvent event) {
				InputCard.setEnabled(true);
				if (ChooseCardField.isVisible())
				{
					if (ATM.enterCard(ChooseCardField.getText())) 
					{
						ChooseCardField.setVisible(false);
						ChooseCardLabel.setVisible(false);
						MainDisplay.setText(Welcome);
						MainDisplay.setEnabled(true);
						PrintDisplay.setText(idle);
						setVisibleKeyboard(true);
						setEnabledKeyboard(true);
						setVisibleDisplays(true);
						ChooseCardField.setText(idle);
					}
					else
					{
						ChooseCardField.setText(idle);
						ChooseCardLabel.setLocation(60, 80);
						ChooseCardLabel.setForeground(Color.red);
						ChooseCardLabel.setText("Неправильний номер карти, спробуйте ще раз:");
					}
				}
				else 
				if (ATM.enterPIN(String.valueOf(PasswordField.getPassword()))) {
					  //correct PIN
				  		ATMwindow.setVisible(true);
				  		ATMwindow.setEnabled(true);
				  		Cancel.setVisible(true);
				  		PasswordField.setVisible(false);
				  		PasswordField.setEnabled(false);
				  		PasswordField.setText(idle);
			            MainDisplay.setText(idle); 
			            Enter.setEnabled(false);
			            //show first menu
			            setVisibleFirstMenu(true);
				  		} 
				  		else {
				  				JOptionPane.showMessageDialog(controllingFrame, "Неправильний ПІН", "Помилка",
						  			                      JOptionPane.ERROR_MESSAGE); 
				  				if (guess==2) {
				  					try {
				  				JOptionPane.showMessageDialog(controllingFrame, 
				  						    "Ваша карта заблокована на 30 секунд", "Помилка",JOptionPane.ERROR_MESSAGE); 
								Thread.sleep(30000);
								guess = 0;
				  					} catch (InterruptedException e) {
				  						e.printStackTrace();
				  						}
				  					} else guess++;
				  			    PasswordField.setText(idle);				  			
				  			}
				}
			 
		 });
		 
		 Cancel = 	 addButton("CANCEL", 120,50,230,400);
		 Cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event){
						InputCard.setEnabled(false);
					    endWork();
				    };
			});

		 add(b1); add(b2); add(b3); add(Cancel);
		 add(b4); add(b5); add(b6); add(Correction);
		 add(b7); add(b8); add(b9); add(Enter);
		         add(b0);
	
		 setVisibleKeyboard(false);
		
		 EXIT = addButton("EXIT", 120,50,530,550);
		 EXIT.setBackground(ExitButtonColor);
		 EXIT.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event){
					ATMwindow.dispose();	
				} 
			});
		
		 InputCard = addButton("Вставити карту", 150,50,70,340);
		 InputCard.setBackground(MenuButtonColor);
		 InputCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				setEnabledKeyboard(true);
				inputPIN();
				} 	
		 	});
		 add(EXIT);
		 add(InputCard);		
		}	
	}
	
	private void endWork(){
		PasswordField.setVisible(false);
		MainDisplay.setText(idle);
		final JButton Yes = new JButton("Так");
		final JButton No = new JButton("Ні");
		Yes.setLocation(10, 80);
		Yes.setSize(150, 30);
		No.setLocation(10, 120);
		No.setSize(150, 30);
		Yes.setBackground(MenuButtonColor);
		No.setBackground(MenuButtonColor);
		
		setEnabledKeyboard(false);
		setVisibleFirstMenu(false);
		OK1.setVisible(false);
		OK2.setVisible(false);
		OK3.setVisible(false);
		
		OldPwdField.setVisible(false);
		NewPwdField1.setVisible(false);
		NewPwdField2.setVisible(false);
		
		MainDisplay.setText("\n   Завершити роботу?");
		MainDisplay.add(Yes);
		MainDisplay.add(No);
		
		Yes.addActionListener(new ActionListener() {
	     public void actionPerformed(ActionEvent event){
		  chooseCard();
		  Yes.setVisible(false);
		  No.setVisible(false);
		  MainDisplay.setText(idle);
		  PrintDisplay.setText(idle);
		  guess = 0;
		 } 
        });
		
		No.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent event){
		  setEnabledKeyboard(true);
		  inputPIN();
		  PasswordField.setVisible(true);
		  PasswordField.setEnabled(true);
		  No.setVisible(false);
		  Yes.setVisible(false);
		  PrintDisplay.setText(idle);
		  guess = 0;
		  } 
	    });
		
	}
	
	private void doPrint(ActionEvent ev){
		MainDisplay.setForeground(LabelForeground);
		MainDisplay.setText(idle);
		YesPrint.setVisible(false);
		NoPrint.setVisible(false);
		endWork();
		String date = new java.util.Date().toString ();
		String transaction = "               ATM \"Virtual\"\n" +
		        "\n ID транзакції: " + ATM.nextTransaction()+
		        "\n Дата:\n " + date +
		        "\n Номер карти: " + ATM.getCurrentClient().getCard() +
				"\n\n Баланс: " + ATM.getCurrentClient().getBalance() + " грн";
		if (ev.getActionCommand().equals("Так")){
		PrintDisplay.setText(transaction);
		}
	}
	
	ActionListener PrintListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			doPrint(e);
			}
		};
		
	 private void doCash(ActionEvent e, int amnt, JButton Amount1, JButton Amount2, JButton Amount3, JButton Amount4, JButton ReturnBack, JButton OtherAmount){
		 int Amount;
		 if (amnt==-1)
		    Amount = Integer.parseInt(e.getActionCommand());
		 else
			Amount = amnt;
		 
		 if (!ATM.doCash(Amount)) {
			 	MainDisplay.setText("\n Недостатньо коштів");
			 	MainDisplay.setForeground(Color.red);
		 }
		 else
		 {
		  YesPrint.setVisible(true);
		  NoPrint.setVisible(true);
		  MainDisplay.setForeground(LabelForeground);
		  MainDisplay.setText("\n   Друкувати чек?" +
					"\n\n\n\n\n\n\n Ваш баланс: " + ATM.getCurrentClient().getBalance() +" грн"); 
		  setVisibleCashButtons(Amount1, Amount2, Amount3, Amount4, ReturnBack, OtherAmount, false);
		 }
		 
	 }
	 
	 private void setVisibleCashButtons(JButton b1, JButton b2 ,JButton b3 ,JButton b4, JButton b5,JButton b6, boolean flag){
		 b1.setVisible(flag);
		 b2.setVisible(flag);
		 b3.setVisible(flag);
		 b4.setVisible(flag);
		 b5.setVisible(flag);
		 b6.setVisible(flag);
	 }
	 
     private void initFirstMenu(){
     	initShowBalanceAction();
    	initCashAction();
    	initSendMoneyAction();
    	initMobileCashAction();
		initSettingsMenu();	
	} 
     
    private void initSettingElements(){
    	OldPwdField = new JPasswordField();
		OldPwdField.setEchoChar('*');
		OldPwdField.setSize(100, 50);
		OldPwdField.setLocation(100, 50);
		OldPwdField.setHorizontalAlignment(JPasswordField.CENTER);
		OldPwdField.setBackground(PasswordFieldColor);
		OldPwdField.setEditable(false);
		OldPwdField.setVisible(false);
		
		NewPwdField1 = new JPasswordField();
		NewPwdField1.setEchoChar('*');
		NewPwdField1.setSize(100, 50);
		NewPwdField1.setLocation(100, 120);
		NewPwdField1.setHorizontalAlignment(JPasswordField.CENTER);
		NewPwdField1.setBackground(PasswordFieldColor);
		NewPwdField1.setEditable(false);
		NewPwdField1.setVisible(false);
		NewPwdField1.setEnabled(false);
		
		NewPwdField2 = new JPasswordField();
		NewPwdField2.setEchoChar('*');
		NewPwdField2.setSize(100, 50);
		NewPwdField2.setLocation(100, 190);
		NewPwdField2.setHorizontalAlignment(JPasswordField.CENTER);
		NewPwdField2.setBackground(PasswordFieldColor);
		NewPwdField2.setEditable(false);
		NewPwdField2.setVisible(false);
		NewPwdField2.setEnabled(false);
		
		OK1 = new JButton("OK");
		OK1.setBackground(MenuButtonColor);
		OK1.setLocation(220, 50);
		OK1.setSize(70, 50);
		OK1.setBackground(Color.lightGray);
		OK1.setVisible(false);
		MainDisplay.add(OK1);
				
		OK2 = new JButton("OK");
		OK2.setBackground(MenuButtonColor);
		OK2.setLocation(220, 120);
		OK2.setSize(70, 50);
		OK2.setBackground(Color.lightGray);
		OK2.setVisible(false);
		OK2.setEnabled(false);
		MainDisplay.add(OK2);
		
		OK3 = new JButton("OK");
		OK3.setBackground(MenuButtonColor);
		OK3.setLocation(220, 190);
		OK3.setSize(70, 50);
		OK3.setBackground(Color.lightGray);
		OK3.setEnabled(false);
		OK3.setVisible(false);
		MainDisplay.add(OK3);
		
		OK1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				OldPwd = OldPwdField.getText();	
				OldPwdField.setEnabled(false);
				NewPwdField1.setEnabled(true);
				OK1.setEnabled(false);
				OK2.setEnabled(true);
			}
		});
		
		OK2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				NewPwd1 = NewPwdField1.getText();	
				NewPwdField1.setEnabled(false);
				NewPwdField2.setEnabled(true);
				OK2.setEnabled(false);
				OK3.setEnabled(true);
			}
		});
		
		OK3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				NewPwd2 = NewPwdField2.getText();	
				NewPwdField2.setEnabled(false);
				OK3.setEnabled(false);
				OldPwdField.setText("");
				NewPwdField1.setText("");
				NewPwdField2.setText("");
				if (ATM.setPassword(OldPwd, NewPwd1, NewPwd2)) {
					OldPwd  = "";
					NewPwd1 = "";
					NewPwd2 = "";
					endWork();
				} 
				else {
					OK1.setEnabled(true);	
					OK2.setEnabled(false);
					OK3.setEnabled(false);
					
					OldPwdField.setEnabled(true);
					NewPwdField1.setEnabled(false);
					NewPwdField2.setEnabled(false);
					
				}
					
				
			}
		});
		
		MainDisplay.add(OldPwdField);
		MainDisplay.add(NewPwdField1);
        MainDisplay.add(NewPwdField2);
    } 
    
    private void initSettingsMenu(){
    	m5 = new JButton("5. Зміна пароля");
		m5.setHorizontalAlignment(SwingConstants.LEFT);
		m5.setFont(new Font("Century Gothic", Font.BOLD, 16));
		m5.setLocation(10, 230);
		m5.setSize(380, 30);
		m5.setBackground(Color.lightGray);
		m5.setVisible(false);       
		m5.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				setVisibleFirstMenu(false);
				OldPwdField.setText("");
				
				OldPwdField.setVisible(true);
				NewPwdField1.setVisible(true);
				NewPwdField2.setVisible(true);
				
				OK1.setVisible(true);
				OK2.setVisible(true);
				OK3.setVisible(true);
				
				NewPwdField1.setEnabled(false);
				NewPwdField2.setEnabled(false);
				
				OK1.setEnabled(true);
				OK2.setEnabled(false);
				OK3.setEnabled(false);
				
				
			}
		});
		MainDisplay.add(m5);	
    }
     
	private void initShowBalanceAction(){
		 YesPrint = addMenuButton("Так",150,30,10,80, MenuButtonColor );
    	 NoPrint = addMenuButton("Ні", 150,30,10,120, MenuButtonColor );
    	 YesPrint.setVisible(false);
    	 NoPrint.setVisible(false);
    	
			
    	m1 = new JButton("1. Перевірити баланс");
    	m1.setName("1");
    	m1.setHorizontalAlignment(SwingConstants.LEFT);
    	m1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		m1.setLocation(10, 30);
		m1.setSize(380, 30);
		m1.setBackground(Color.lightGray);
		m1.setVisible(false);
		
		YesPrint.addActionListener(PrintListener);
		NoPrint.addActionListener(PrintListener);
		m1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				YesPrint.setVisible(true);
				NoPrint.setVisible(true);
				setVisibleFirstMenu(false);
				setEnabledKeyboard(false);
				MainDisplay.setText("\n   Друкувати чек?" +
									"\n\n\n\n\n\n\n Ваш баланс: " + ATM.getCurrentClient().getBalance() +" грн"); 
			}
		});
		MainDisplay.add(m1);
		
	}
	
	private void initCashAction(){

		 final JButton Amount1     = addMenuButton("100",120,30,10,80, MenuButtonColor );
		 final JButton Amount2     = addMenuButton("200",120,30,10,120,  MenuButtonColor );
		 final JButton Amount3     = addMenuButton("300",120,30,10,160,  MenuButtonColor );
		 final JButton Amount4 	   = addMenuButton("400",120,30,10,200,  MenuButtonColor );
		 final JButton OtherAmount = addMenuButton("Інша сума",120,30,150,80, MenuButtonColor );
		 final JButton ReturnBack  = addMenuButton("Головне меню",120,30,150,120, MenuButtonColor);
		 
		 Amount1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				 doCash(e,-1,Amount1, Amount2, Amount3, Amount4, ReturnBack, OtherAmount);
			}});
		 Amount2.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					 doCash(e,-1,Amount1, Amount2, Amount3, Amount4, ReturnBack, OtherAmount);
				}});
		 Amount3.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					 doCash(e,-1,Amount1, Amount2, Amount3, Amount4, ReturnBack, OtherAmount);
				}});
		 Amount4.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					 doCash(e,-1,Amount1, Amount2, Amount3, Amount4, ReturnBack, OtherAmount);
				}});
		 ReturnBack.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					 setVisibleFirstMenu(true);
					 MainDisplay.setText(idle);
					 MainDisplay.setForeground(LabelForeground);
					 setEnabledKeyboard(true);
					 Enter.setEnabled(false);
					 setVisibleCashButtons(OtherAmount,Amount1, Amount2, Amount3, Amount4, ReturnBack, false);
				}});
		 
		 OtherAmount.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					 String str = JOptionPane.showInputDialog(null, "Введіть число кратне 10: ", "Інша сума", 1);
					 if (str==null) {
						 	JOptionPane.showMessageDialog(null, "Некоректне число", "Помилка", JOptionPane.ERROR_MESSAGE);
						 }
					 else
					    {
						 try { 
							 int amount = Integer.parseInt(str); 
							 if (amount>ATM.getTotalCash()) JOptionPane.showMessageDialog(null, "Недостатньо готівки в банкоматі", "Помилка", JOptionPane.ERROR_MESSAGE); 
							 else
							 if(amount%10==0 && amount>=10)
							        doCash(e,amount,Amount1, Amount2, Amount3, Amount4, ReturnBack, OtherAmount);
							     else
							        JOptionPane.showMessageDialog(null, "Некоректне число", "Помилка", JOptionPane.ERROR_MESSAGE);
						     }	   
						      catch(NumberFormatException error) { 
						      JOptionPane.showMessageDialog(null, "Некоректне число", "Помилка", JOptionPane.ERROR_MESSAGE);
						    }					     
					    }
					 }
		       		
		 });
		Amount1.setVisible(false);
		Amount2.setVisible(false);
		Amount3.setVisible(false);
		Amount4.setVisible(false);
		OtherAmount.setVisible(false);
		ReturnBack.setVisible(false);
		MainDisplay.add(Amount1);
		MainDisplay.add(Amount2);
		MainDisplay.add(Amount3);
		MainDisplay.add(Amount4);
		MainDisplay.add(OtherAmount);
		MainDisplay.add(ReturnBack);
				
		m2 = new JButton("2. Зняти готівку");
		m2.setHorizontalAlignment(SwingConstants.LEFT);
		m2.setFont(new Font("Century Gothic", Font.BOLD, 16));
		m2.setLocation(10, 80);
		m2.setSize(380, 30);
		m2.setBackground(Color.lightGray);
		m2.setVisible(false);
		m2.addActionListener(new ActionListener(){						
			@Override
			public void actionPerformed(ActionEvent event) {
				setEnabledKeyboard(true);
				Amount1.setVisible(true);
				Amount2.setVisible(true);
				Amount3.setVisible(true);
				Amount4.setVisible(true);
				String money = String.valueOf(ATM.getCurrentClient().getMoney(0));
				Amount1.setText(money);
				money = String.valueOf(ATM.getCurrentClient().getMoney(1));
				Amount2.setText(money);
				money = String.valueOf(ATM.getCurrentClient().getMoney(2));
				Amount3.setText(money);
				money = String.valueOf(ATM.getCurrentClient().getMoney(3));
				Amount4.setText(money);
				OtherAmount.setVisible(true);
				ReturnBack.setVisible(true);
				setVisibleFirstMenu(false);
				setEnabledKeyboard(false);
				Enter.setEnabled(false);
				MainDisplay.setText(" Ваш баланс: " + ATM.getCurrentClient().getBalance() +" грн"+
				"\n Виберіть суму");
			}
		});
		MainDisplay.add(m2);
	}
	
    private void initSendMoneyAction(){
    	final JButton Amount = addMenuButton("Вибрати суму",120,30,100,80, MenuButtonColor );
		final JButton Return  = addMenuButton("Головне меню",120,30,100,120, MenuButtonColor);
		Return.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				 setVisibleFirstMenu(true);
				 MainDisplay.setText(idle);
				 MainDisplay.setForeground(LabelForeground);
				 setEnabledKeyboard(true);
				 Return.setVisible(false);
				 Amount.setVisible(false);
				 Enter.setEnabled(false);
			}});
	 
		Amount.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				 String str = JOptionPane.showInputDialog(ATMwindow, "Введіть суму: ", "Переказ коштів", 1);
				 
				 if (str==null) {
					 	JOptionPane.showMessageDialog(ATMwindow, "Некоректне число", "Помилка", JOptionPane.ERROR_MESSAGE);
					 }
				 else
				    {
					 try { 
						 int amount = Integer.parseInt(str);
						 if (inputCardToSend(amount)){
						 Amount.setVisible(false);
						 Return.setVisible(false);
						 YesPrint.setVisible(true);
						 NoPrint.setVisible(true);
						 MainDisplay.setForeground(LabelForeground);
						 MainDisplay.setText("\n   Друкувати чек?" +
									"\n\n\n\n\n\n\n Ваш баланс: " + ATM.getCurrentClient().getBalance() +" грн");
						 }
					     }	   
					      catch(NumberFormatException error) { 
					      JOptionPane.showMessageDialog(ATMwindow, "Некоректне число", "Помилка", JOptionPane.ERROR_MESSAGE);
					    }					     
				    }				
				 }
	       		
		});
    	m3 = new JButton("3. Перевести кошти на інший рахунок");
		m3.setHorizontalAlignment(SwingConstants.LEFT);
		m3.setFont(new Font("Century Gothic", Font.BOLD, 16));
		m3.setLocation(10, 130);
		m3.setSize(380, 30);
		m3.setBackground(Color.lightGray);
		m3.setVisible(false);
		m3.addActionListener(new ActionListener(){						
			@Override
			public void actionPerformed(ActionEvent event) {
				setEnabledKeyboard(true);
				Return.setVisible(true);
				Amount.setVisible(true);
				setVisibleFirstMenu(false);
				setEnabledKeyboard(false);
				MainDisplay.setText(" Ваш баланс: " + ATM.getCurrentClient().getBalance() +" грн"+
				"\n Виберіть суму");
			}
		});
		MainDisplay.add(m2);
		MainDisplay.add(m3);
		Return.setVisible(false);
		Amount.setVisible(false);
    }
    
    private boolean inputCardToSend(int amnt){
    	boolean check = false;
    	String cardnumber = JOptionPane.showInputDialog(ATMwindow, "Введіть номер карти отримувача: ", "Переказ коштів", 1);
    	 if (cardnumber==null) {
			 	JOptionPane.showMessageDialog(ATMwindow, "Некоректний номер", "Помилка", JOptionPane.ERROR_MESSAGE);
			 }
		 else
		    {
			 try {  
				     Integer.parseInt(cardnumber);
					 if (ATM.ProcessSend(ATM.getCurrentClient().getCard(), cardnumber, amnt)){
					 check = true;
					 }
			     }	   
			      catch(NumberFormatException error) { 
			      JOptionPane.showMessageDialog(ATMwindow, "Некоректний номер", "Помилка", JOptionPane.ERROR_MESSAGE);
			    }					     
		    }
    	 return check;
	 }
    
    private boolean inputMobileNumber(int amnt){
    	boolean check = false;
    	String number = JOptionPane.showInputDialog(ATMwindow, "Номер мобільного: +380", "Поповнення мобільного", 1);
    	 if (number==null || number.length()!=9) {
			 	JOptionPane.showMessageDialog(ATMwindow, "Некоректний номер", "Помилка", JOptionPane.ERROR_MESSAGE);
			 }
		 else
		    {
			 try {  
				     Integer.parseInt(number);
					 if (ATM.MobileFillUp(amnt)) check = true; 
			     }	   
			      catch(NumberFormatException error) { 
			      JOptionPane.showMessageDialog(ATMwindow, "Некоректне число", "Помилка", JOptionPane.ERROR_MESSAGE);
			    }					     
		    }
    	 return check;
	 }
    
    private void initMobileCashAction(){
    	
    	final JButton Amount = addMenuButton("Вибрати суму",120,30,100,80, MenuButtonColor );
		final JButton Return  = addMenuButton("Головне меню",120,30,100,120, MenuButtonColor);
		Return.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				 setVisibleFirstMenu(true);
				 MainDisplay.setText(idle);
				 MainDisplay.setForeground(LabelForeground);
				 setEnabledKeyboard(true);
				 Return.setVisible(false);
				 Amount.setVisible(false);
				 Enter.setEnabled(false);
			}});
	 
		Amount.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				 String str = JOptionPane.showInputDialog(ATMwindow, "Введіть суму (не менше 5 грн): ", "Поповнення мобільного", 1);
				 int amount = 0;
				 if (str==null) {
					 	JOptionPane.showMessageDialog(ATMwindow, "Некоректне число", "Помилка", JOptionPane.ERROR_MESSAGE);
					 }
				 else
				    {
					 try { 
						 amount = Integer.parseInt(str);
						 if (inputMobileNumber(amount)) {
							 Amount.setVisible(false);
							 Return.setVisible(false);
							 YesPrint.setVisible(true);
							 NoPrint.setVisible(true);
							 MainDisplay.setForeground(LabelForeground);
							 MainDisplay.setText("\n   Друкувати чек?" +
										"\n\n\n\n\n\n Мобільний поповнено на: " + String.valueOf(amount) + " грн" +
									    "\n Ваш баланс: " + ATM.getCurrentClient().getBalance() +" грн");
						 }
					     }	   
					      catch(NumberFormatException error) { 
					      JOptionPane.showMessageDialog(ATMwindow, "Некоректне число", "Помилка", JOptionPane.ERROR_MESSAGE);
					    }					     
				    }
				 
				 }
	       		
		});
    	m4 = new JButton("4. Поповнити мобільний");
		m4.setHorizontalAlignment(SwingConstants.LEFT);
		m4.setLocation(10, 180);
		m4.setFont(new Font("Century Gothic", Font.BOLD, 16));
		m4.setSize(380, 30);
		m4.setBackground(Color.lightGray);
		m4.setVisible(false);
		m4.addActionListener(new ActionListener(){						
			@Override
			public void actionPerformed(ActionEvent event) {
				setEnabledKeyboard(true);
				Return.setVisible(true);
				Amount.setVisible(true);
				setVisibleFirstMenu(false);
				setEnabledKeyboard(false);
				MainDisplay.setText(" Ваш баланс: " + ATM.getCurrentClient().getBalance() +" грн"+
				"\n Виберіть суму");
			}
		});
		MainDisplay.add(m4);
		Return.setVisible(false);
		Amount.setVisible(false);
	}
    
	private static void inputPIN(){
		setEnabledKeyboard(true);
		setVisibleKeyboard(true);
		MainDisplay.setText(idle);
		
		MainDisplay.setEnabled(true);
		InputCard.setVisible(false);
		MainDisplay.setFont(new Font("Century Gothic", Font.BOLD, 20));
		MainDisplay.setText("\n\n\n                      Введіть PIN: ");
		PasswordField.setVisible(true);
	    PasswordField.setEnabled(true);
		PasswordField.setText(idle);
	}
	
	//загальна функція для створення кнопки
	private final JButton addButton(String lable, int sizeX, int sizeY, int x, int y){  
	   JButton button = new JButton(lable);
	   button.setSize(sizeX, sizeY);
	   button.setLocation(x, y);
	   return button;
	}
	
	//функція для створення кнопки меню дисплея
	private final JButton addMenuButton(String lable, int sizeX, int sizeY, int x, int y, Color color){  
		   JButton button = new JButton(lable);
		   button.setSize(sizeX, sizeY);
		   button.setLocation(x, y);
		   button.setBackground(color);
		   MainDisplay.add(button);
		   return button;
	}
     
	private static void readCardNumber(){
		ChooseCardField.setVisible(true);
		ChooseCardLabel.setVisible(true);
	}
	
	//режим вибору карти
	private static void chooseCard(){
		MainDisplay.setVisible(false);
		PrintDisplay.setVisible(false);
		setVisibleKeyboard(true);
		setEnabledKeyboard(true);
		InputCard.setVisible(false);
		Cancel.setEnabled(false);
		PasswordField.setVisible(false);
		readCardNumber();
		ChooseCardLabel.setText("Введіть номер картки:");
		ChooseCardLabel.setForeground(Color.black);
		ChooseCardLabel.setLocation(100, 60);
		
	}
  
	private static void setVisibleFirstMenu(boolean flag){
		m1.setVisible(flag);
		m2.setVisible(flag);
		m3.setVisible(flag);
		m4.setVisible(flag);
		m5.setVisible(flag);
	}

	private static void setVisibleKeyboard(boolean flag){
		b1.setVisible(flag);
		b2.setVisible(flag);
		b3.setVisible(flag);
		b4.setVisible(flag);
		b5.setVisible(flag);
		b6.setVisible(flag);
		b7.setVisible(flag);
		b8.setVisible(flag);
		b9.setVisible(flag);
		b0.setVisible(flag);
		Cancel.setVisible(flag);
		Correction.setVisible(flag);
		Enter.setVisible(flag);	
	}
	
	private static void setEnabledKeyboard(boolean flag){
		b1.setEnabled(flag);
		b2.setEnabled(flag);
		b3.setEnabled(flag);
		b4.setEnabled(flag);
		b5.setEnabled(flag);
		b6.setEnabled(flag);
		b7.setEnabled(flag);
		b8.setEnabled(flag);
		b9.setEnabled(flag);
		b0.setEnabled(flag);
		Cancel.setEnabled(flag);
		Correction.setEnabled(flag);
		Enter.setEnabled(flag);	
   }
   
   
   private void setVisibleDisplays(boolean flag){
	   MainDisplay.setVisible(flag);
	   PrintDisplay.setVisible(flag); 
	   InputCard.setVisible(flag);   
   }
   
   private class KeyboardListener implements ActionListener{
		   
		@Override
		public void actionPerformed(ActionEvent event) {
			if (PasswordField.isVisible()) 
				readPIN(event);
			if (ChooseCardField.isVisible()) 
				inputCard(event);	
			if (OK1.isEnabled()) 
				readPwd1(event);	
			if (OK2.isEnabled()) 
				readPwd2(event);	
			if (OK3.isEnabled()) 
				readPwd3(event);	
		}
	
		public void readPwd1(ActionEvent e){
			int len = OldPwdField.getPassword().length;
			switch (e.getActionCommand()) {
				case "CORRECTION": if (len!=0)
					OldPwdField.setText(OldPwdField.getText().substring(0, len-1)); 
					break;
				 default: if (len!=4) 
					 OldPwdField.setText(OldPwdField.getText() + e.getActionCommand()); 
				  	break;
				}  		
		}
		
		public void readPwd2(ActionEvent e){
			int len = NewPwdField1.getPassword().length;
			switch (e.getActionCommand()) {
				case "CORRECTION": if (len!=0)
					NewPwdField1.setText(NewPwdField1.getText().substring(0, len-1)); 
					break;
				 default: if (len!=4) 
					 NewPwdField1.setText(NewPwdField1.getText() + e.getActionCommand()); 
				  	break;
				}  		
		}
		
		public void readPwd3(ActionEvent e){
			int len = NewPwdField2.getPassword().length;
			switch (e.getActionCommand()) {
				case "CORRECTION": if (len!=0)
					NewPwdField2.setText(NewPwdField2.getText().substring(0, len-1)); 
					break;
				 default: if (len!=4) 
					 NewPwdField2.setText(NewPwdField2.getText() + e.getActionCommand()); 
				  	break;
				}  		
		}		
		
		public void readPIN(ActionEvent e){
			int len = PasswordField.getPassword().length;
			switch (e.getActionCommand()) {
				case "CORRECTION": if (len!=0)
					PasswordField.setText(PasswordField.getText().substring(0, len-1)); 
					break;
				 default: if (len!=4) 
					PasswordField.setText(PasswordField.getText() + e.getActionCommand()); 
				  	break;
				}  		
			}
		
		public void inputCard(ActionEvent e){
			int len = GUI.ChooseCardField.getText().length();
			switch (e.getActionCommand()) {
			     case "CORRECTION": if (len!=0)
					ChooseCardField.setText(ChooseCardField.getText().substring(0, len-1)); 
					break;
				 default: if (len!=6) 
					 ChooseCardField.setText(ChooseCardField.getText() + e.getActionCommand()); 
				  	break;
				}  		
			}
		
			
	}

   public MyFrame getATMwindow() {
	   return ATMwindow;
   }
   
   
 
}
	

