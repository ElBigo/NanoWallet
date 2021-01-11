package nanoapps.equensworldlie.com.model;

public class User {

    private String username;
    private String password;
    private String walletId;
    private int balance;


    public User(String username, String password, String walletId, int balance) {
        this.username = username;
        this.password = password;
        this.walletId = walletId;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getWalletId() {
        return walletId;
    }
    public int getBalance() {
        return balance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void createAccount(){

    }
    public void deleteAccount(){

    }
    public int rechargeAccount(String accountId){
        int balance = 0;
        return balance;
    }
    public void transferFunds(String accountId){

    }
    public int receiveFunds(){
        int fund = 0;
        return fund;
    }
    public void pay(String accountId){

    }

}
