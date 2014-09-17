package ATM;

public class Client {
    private String Name;
    private String Card;
    private String Pass;
    private int Balance;
    private final int [] MoneyArray = new int [5];

    public void setPass(String _pass) {
        this.Pass =new String( _pass);
    }

    public Client(String _name, String _card, String _pass, int _balance) {
        this.Name = _name;
        this.Card = _card;
        this.Pass = _pass;
        this.Balance = _balance;
        this.MoneyArray[0] =20;
        this.MoneyArray[1] = 50;
        this.MoneyArray[2] = 100;
        this.MoneyArray[3] = 200;
        
    }
    Client() {
	}

	public int getMoney(int i){
        return MoneyArray[i];
    }
    public void setMoney(int i, int nm){
        this.MoneyArray[i] = nm;
    }
    
    public String toString() {
        return Name+ " "+ Card+" "+Pass+" "+Balance 
                + " " + MoneyArray[0]+ " " + MoneyArray[1]+ " " + MoneyArray[2]+ " " + MoneyArray[3];
        
    }

    public void setBalance(int _balance) {
        this.Balance = _balance;
    }

    public String getName() {
        return Name;
    }

    public int getBalance() {
        return Balance;
    }

    public String getCard() {
        return Card;
    }

    public String getPass() {
        return Pass;
    }
    
    
    
}
