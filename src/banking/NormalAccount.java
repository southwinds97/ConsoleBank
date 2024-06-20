package banking;

import java.io.Serializable;

public class NormalAccount extends Account implements Serializable {
  private double interestRate; // 이율

  // 기본 생성자
  public NormalAccount() {
    super();
    this.interestRate = 0.0;
  }

  public NormalAccount(String no, String name, int bal, double interest) {
    super(no, name, bal); // 부모 클래스의 생성자 호출
    this.interestRate = interest; // 이율 초기화
  }

  // 이율을 반환하는 메서드
  public double getInterestRate() {
    return interestRate;
  }

  // 입금시 이자를 계산하고 입금액을 잔고에 더하는 메서드
  public void deposit(int amount) {
    double interest = getBalance() * getInterestRate();
    setBalance(getBalance() + (int) interest + amount);
  }
}