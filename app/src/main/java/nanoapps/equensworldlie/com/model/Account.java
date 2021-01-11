package nanoapps.equensworldlie.com.model;

public class Account {

    private final String accountId;
    private final String pKey;
    private final String sKey;
    private int balance = 0;

    public Account(String accountId, String pKey, String sKey) {
        this.accountId = accountId;
        this.pKey = pKey;
        this.sKey = sKey;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getpKey() {
        return pKey;
    }


    public String getsKey() {
        return sKey;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
