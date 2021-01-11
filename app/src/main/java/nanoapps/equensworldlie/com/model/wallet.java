package nanoapps.equensworldlie.com.model;

import java.util.ArrayList;
import java.util.List;

public class wallet {

    private final String id;
    private List<String> accountList = new ArrayList<String>();
    private int balance;

    public wallet(String id, List<String> accountList, int balance) {
        this.id = id;
        this.accountList = accountList;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public List<String> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<String> accountList) {
        this.accountList = accountList;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

}
