

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable{

    private static final long serialVersionUID = 1L;
    private String account_name, sort_code, account_num, type;
    private Integer balance;
	private Integer running_balance;
    private ArrayList<Transaction> recent_trxns = new ArrayList<>();

    public Account(String account_name, String sort_code, String account_num,
            String type, Integer balance, ArrayList<Transaction> recent_trxns) {
        this.account_name = account_name;
        this.sort_code = sort_code;
        this.account_num = account_num;
        this.type = type;
        this.balance = balance;
        this.recent_trxns = recent_trxns;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getSort_code() {
        return sort_code;
    }

    public void setSort_code(String sort_code) {
        this.sort_code = sort_code;
    }

    public String getAccount_num() {
        return account_num;
    }

    public void setAccount_num(String account_num) {
        this.account_num = account_num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public ArrayList<Transaction> getRecent_trxns() {
        return recent_trxns;
    }

    public void setRecent_trxns(ArrayList<Transaction> recent_trxns) {
        this.recent_trxns = recent_trxns;
    }

}
