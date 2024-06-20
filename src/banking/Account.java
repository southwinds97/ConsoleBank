package banking;

import java.io.Serializable;
import java.util.Objects;

public abstract class Account implements Serializable {
  // 여러 계좌정보를 저장하기 위해 인스턴스(객채)배열을 이용한다.
  private String accountNo; // 계좌번호
  private String ownerName; // 예금주
  private int balance; // 잔액

  // 기본생성자
  public Account() {
    accountNo = "";
    ownerName = "";
    balance = 0;
  }

  public Account(String no, String name, int bal) {
    accountNo = no;
    ownerName = name;
    balance = bal;
  }

  public String getAccountNo() {
    return accountNo;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public int getBalance() {
    return balance;
  }

  public void setBalance(int bal) {
    balance = bal;
  }

  // 잔액에 돈을 더하는 메서드
  public void deposit(int money) {
    balance += money;
  }

  // 잔액에서 돈을 빼는 메서드
  public void withdraw(int money) {
    balance -= money;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Account acc = (Account) obj;
    return accountNo.equals(acc.accountNo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountNo);
  }
}