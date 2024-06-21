package banking;

import java.io.Serializable;

public class HighCreditAccount extends Account implements Serializable, ICustomDefine {
  private double interestRate; // 이율
  private String creditRating; // 신용등급

  // 기본 생성자
  public HighCreditAccount() {
    super();
    this.interestRate = 0.0;
    this.creditRating = "";
  }

  public HighCreditAccount(String no, String name, int bal, double interest, String creditRating) {
    super(no, name, bal); // 부모 클래스의 생성자 호출
    this.interestRate = interest; // 이율 초기화
    this.creditRating = creditRating; // 신용등급 초기화
  }

  // 이율을 반환하는 메서드
  public double getInterestRate() {
    return interestRate;
  }

  // 신용등급을 반환하는 메서드
  public String getCreditRating() {
    return creditRating;
  }

  // 신용등급에 따른 추가이율을 반환하는 메서드
  public double getAdditionalRate() {
    if (creditRating.equals("A")) {
      return GRADE_A;
    } else if (creditRating.equals("B")) {
      return GRADE_B;
    } else if (creditRating.equals("C")) {
      return GRADE_C;
    } else {
      System.out.println("잘못된 신용등급입니다.");
      return 0.0;
    }
  }

  // 입금시 이자를 계산하고 입금액을 잔고에 더하는 메서드
  public void deposit(int amount) {
    double interest = getBalance() * getInterestRate();
    double additionalInterest = getBalance() * getAdditionalRate();
    setBalance(getBalance() + (int) interest + (int) additionalInterest + amount);
  }
}
