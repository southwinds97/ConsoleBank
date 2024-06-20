package banking;

public class SpecialAccount extends NormalAccount {
  private int depositCount; // 입금 횟수

  public SpecialAccount() {
    super();
    this.depositCount = 0;
  }

  public SpecialAccount(String no, String name, int bal, double interest) {
    super(no, name, bal, interest);
    this.depositCount = 0;
  }

  public int getDepositCount() {
    return depositCount;
  }

  // 입금시 이자를 계산하고 입금액을 잔고에 더하는 메서드
  @Override
  public void deposit(int amount) {
    super.deposit(amount);
    depositCount++;

    // 짝수번째 입금에는 500원씩 축하금을 별도로 지급
    if (depositCount % 2 == 0) {
      setBalance(getBalance() + 500);
    }
  }
}