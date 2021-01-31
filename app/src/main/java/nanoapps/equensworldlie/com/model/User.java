package nanoapps.equensworldlie.com.model;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String password;
    private String walletId;
    private String accountId;
    private String pKey;
    private String sKey;
    private String balance;


    public User(){
        this.username = "";
        this.password = "";
        this.walletId = "";
        this.accountId = "";
        this.pKey = "";
        this.sKey = "";
        this.balance = "";
    }
    public User(String username, String password, String walletId, String accountId, String pKey, String sKey) {
        this.username = username;
        this.password = password;
        this.walletId = walletId;
        this.accountId = accountId;
        this.pKey = pKey;
        this.sKey = sKey;
        this.balance = "";
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
    public String getBalance() {
        return balance;
    }
    public String getAccountId() { return accountId; }
    public String getpKey() { return pKey; }



    public void setAccountId(String accountId) { this.accountId = accountId; }
    public void setpKey(String pKey) { this.pKey = pKey; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }
    public void setBalance(String balance) {
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
