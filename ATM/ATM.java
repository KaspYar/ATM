package ATM;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

final public class ATM {
    private static Client CurrentClient;
    
    private static ArrayList<Client> ClientList = new ArrayList<>();
    private static ArrayList<String> Transactions = new ArrayList<>();
    private static int Num = 4;
    private static int IDtransaction = 12345;
    private static int CashLimit = 5000;
    private static int MinBalanceLimit = 10;   
    private static int MinSendLimit = 10; 
    private static Integer MoneyCount10  = 0;
	private static Integer TotalCash  = 0;
	private static JFrame Window;
      

    public ArrayList<Client> getClient() {
        return ClientList;
    }
    
    void addTransaction(String tr){
    	Transactions.add(tr);	
    }
    
    static String nextTransaction(){
    	IDtransaction += 1;
    	return String.valueOf(IDtransaction);
    }
    
    public static void update() throws IOException{
            
           String name = "Database";
           File f = new File(name);
           f.createNewFile();
            RandomAccessFile data = new RandomAccessFile(f,"rw");
            
            String nameAM = "am";
            File fAM = new File(nameAM);
            fAM.createNewFile();
            RandomAccessFile dataAM = new RandomAccessFile(fAM,"r");   
            dataAM.seek(0);
            Num  = dataAM.readInt();
            
            for (int i=0;i<Num;i++){
            	data.writeInt(Integer.parseInt((ClientList.get(i).getCard())));
            	data.writeInt(Integer.parseInt(ClientList.get(i).getPass()));
            	data.writeInt(ClientList.get(i).getBalance());
            	data.writeUTF(ClientList.get(i).getName());   
            }
            data.close();
            dataAM.close();
    }
    

    public ATM() throws Exception{
    	Window = GUI.getInstance().getATMwindow();
        String name = "Database";
        File file = new File(name);        
        RandomAccessFile data = new RandomAccessFile(file,"r");
       
        String nameAM = "am";
        File fAM = new File(nameAM);
        fAM.createNewFile();
        RandomAccessFile dataAM = new RandomAccessFile(fAM,"r");   
        dataAM.seek(0);
        Num  = dataAM.readInt();
        
        for (int i=0;i<Num;i++){
            String s = Integer.toString(data.readInt());
            String t = Integer.toString(data.readInt());
            int fo = data.readInt();
            String f = data.readUTF();
            ClientList.add(new Client(f,s,t,fo));
        }
        data.close();
        dataAM.close();
    }
    
    static boolean isClient(String card){
        boolean find = false;
        for(int i=0;i<Num&&!find;i++)
            if (ClientList.get(i).getCard().equals(card)){
                CurrentClient = ClientList.get(i);
                find = true;
            }
        return find;
    }
      
    static boolean enterCard(String input){
        boolean finder = isClient(input);
        return finder;
    }
    
    
    static boolean enterPIN(String InputPin){ 
        return CurrentClient.getPass().equals(InputPin); 
    }
    

    static boolean doCash(int amount){
      if (amount>CashLimit){
    	JOptionPane.showMessageDialog(Window, "Перевищений ліміт", "Помилка", JOptionPane.ERROR_MESSAGE);
    	return false;
      } 
      
      if (amount>TotalCash){
      	JOptionPane.showMessageDialog(Window, "Недостатньо готівки в банкоматі", "Помилка", JOptionPane.ERROR_MESSAGE);
      	return false;
        } 
           
      if ( CurrentClient.getBalance() > amount+MinBalanceLimit){
              CurrentClient.setBalance(CurrentClient.getBalance() - amount);
              TotalCash-=amount;
              JOptionPane.showMessageDialog(Window, "Заберіть готівку", "Успішна транзакція", JOptionPane.INFORMATION_MESSAGE);
              try {
				update();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(Window, "Помилка");
			}
              return true;
        }
        else {return false;}
    }
    
    static int search(String id){
        /*
            return -1  - client not found
            return int - this int is client's ID
        */
        int isCl = -1;
        for(int i=0; i<Num&&isCl == -1; i++) {
            if ((ClientList.get(i).getCard().equals(id))) isCl = i;
        }
        return isCl;
    }
   
     
    static boolean ProcessSend(String fromCard, String toCard , int sum )
    {
    	if(toCard.equals(CurrentClient.getCard())) {  
    		JOptionPane.showMessageDialog(Window, "Ви ввели свій номер карти", "Помилка", JOptionPane.ERROR_MESSAGE);
    		return false;
    		}
    	
    	if(sum<MinSendLimit) {  
    		JOptionPane.showMessageDialog(Window, "Мінімальна сума переказу: " + MinBalanceLimit + " грн", "Помилка", JOptionPane.ERROR_MESSAGE);
    		return false;
    		}
    	
    	if (toCard.length()>6 || toCard.length()<5) {
        	JOptionPane.showMessageDialog(Window, "Неправильний номер карти", "Помилка", JOptionPane.ERROR_MESSAGE);
        	return false;
        }
    	
        if (search(toCard)==-1) {
        	JOptionPane.showMessageDialog(Window, "Такої карти не існує", "Помилка", JOptionPane.ERROR_MESSAGE);
        	return false;
        }
        
        if (CashLimit<sum) {
        	JOptionPane.showMessageDialog(Window, "Перевищений ліміт", "Помилка", JOptionPane.ERROR_MESSAGE);
        	return false;
        }
        
        if (CurrentClient.getBalance()-MinBalanceLimit<sum) {
        	JOptionPane.showMessageDialog(Window, "Недостатньо коштів", "Помилка", JOptionPane.ERROR_MESSAGE);
        	return true;
        } 
        else {
        
        int order = search(toCard);
        ClientList.get(order).setBalance(ClientList.get(order).getBalance() + sum );
        
        int CurSum = CurrentClient.getBalance();
        CurrentClient.setBalance(CurSum - sum);
        JOptionPane.showMessageDialog(Window, "Кошти переказано", "Успішна транзакція", JOptionPane.INFORMATION_MESSAGE);
        
        try {
            update();
        } catch (IOException ex) {
        	JOptionPane.showMessageDialog(Window, "Помилка");
        }
        return true;
        }
    }
   

 static boolean MobileFillUp(int sum)
 {
	if (sum<5) {
     	JOptionPane.showMessageDialog(Window, "Неправильне число, введіть більшу суму", "Помилка", JOptionPane.ERROR_MESSAGE);
     	return false;
     }
	
	if (sum>CashLimit) {
     	JOptionPane.showMessageDialog(Window, "Перевищений ліміт", "Помилка", JOptionPane.ERROR_MESSAGE);
     	return false;
     }

	if (CurrentClient.getBalance()-MinBalanceLimit<sum) {
    	JOptionPane.showMessageDialog(Window, "Недостатньо коштів", "Помилка", JOptionPane.ERROR_MESSAGE);
    	return false;
    }
	
   CurrentClient.setBalance(CurrentClient.getBalance()-sum);
   JOptionPane.showMessageDialog(Window, "Мобільний поповнено", "Успішна транзакція", JOptionPane.INFORMATION_MESSAGE);
   try {
       update();
   } catch (IOException ex) {
   	JOptionPane.showMessageDialog(Window, "Помилка");
   }
   return true;
 }
 

    static boolean setPassword(String oldPwd, String newPwd1, String newPwd2){
    	boolean check = false;
    	if (!oldPwd.equals(CurrentClient.getPass())) JOptionPane.showMessageDialog(Window, "Невірний старий пароль", "Помилка", JOptionPane.ERROR_MESSAGE);
    	else
    		if (newPwd1.equals(newPwd2)){
    			CurrentClient.setPass(newPwd2);
    			check = true;
    			JOptionPane.showMessageDialog(Window, "Пароль змінено", "Зміна PIN", JOptionPane.INFORMATION_MESSAGE);
    		} else
    			JOptionPane.showMessageDialog(Window, "Паролі не співпадають", "Помилка", JOptionPane.ERROR_MESSAGE);
    	try {
    	       update();
    	   } catch (IOException ex) {
    	   	JOptionPane.showMessageDialog(Window, "Помилка");
    	   }
    	return check;
    }
    
    static String getClientMoney(Client client, int i){
    	return String.valueOf(client.getMoney(i));   	   	
    }
    
	public static Client getCurrentClient() {
		return CurrentClient;
	}

	public static int getIDtransaction() {
		return IDtransaction;
	}

	public static void setIDtransaction(int iDtransaction) {
		IDtransaction = iDtransaction;
	}

	public static Integer getMoneyCount10() {
		return MoneyCount10;
	}

	public static Integer getTotalCash() {
		return TotalCash;
	}

	public static void setMoneyCount10(Integer moneyCount10) {
		MoneyCount10 = moneyCount10;
	}

	public static void setTotalCash(Integer totalCash) {
		TotalCash = totalCash;
	}
	
    
    
}
