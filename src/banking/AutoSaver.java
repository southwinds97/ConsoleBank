package banking;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class AutoSaver extends Thread {
  // 자동저장 여부를 나타내는 변수
  private boolean autoSaveOn;
  private Set<Account> accounts;

  // 기본생성자
  public AutoSaver() {
    this.autoSaveOn = false;
    this.accounts = new HashSet<>();
    setDaemon(true); // AutoSaver 쓰레드를 데몬 쓰레드로 설정
  }

  // accounts 필드의 setter 메서드
  public void setAccounts(Set<Account> accounts) {
    this.accounts = accounts;
  }

  public void run() {
    while (true) {
      synchronized (this) {
        if (!autoSaveOn) {
          break;
        }
        saveAccountInfo();
      }
      try {
        Thread.sleep(5000); // 5초
      } catch (InterruptedException e) {
        break;
      }
    }
  }

  // AutoSaver 쓰레드를 시작하는 메서드
  public void startAutoSave() {
    if (this.isAlive()) {
      if (!this.autoSaveOn) {
        this.autoSaveOn = true;
      } else {
        System.out.println("AutoSaver 쓰레드가 이미 실행 중입니다.");
      }
    } else {
      this.autoSaveOn = true;
      this.start();
    }
  }

  // AutoSaver 쓰레드를 중단하는 메서드
  public void stopAutoSave() {
    if (this.isAlive()) {
      this.autoSaveOn = false;
      this.interrupt();
      try {
        this.join(); // AutoSaver 쓰레드가 완전히 중단될 때까지 기다립니다.
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("AutoSaver 쓰레드가 중단되었습니다.");
    } else {
      System.out.println("AutoSaver 쓰레드가 이미 중단되어 있습니다.");
    }
  }

  // 계좌 정보를 파일로 저장하는 메서드
  private void saveAccountInfo() {
    try (PrintWriter writer = new PrintWriter(new FileWriter("src/banking/AutoSaveAccount.txt"))) {
      for (Account account : accounts) {
        writer.println("계좌번호: " + account.getAccountNo());
        writer.println("고객이름: " + account.getOwnerName());
        writer.println("잔고: " + account.getBalance());
        if (account instanceof NormalAccount) {
          double interestRate = ((NormalAccount) account).getInterestRate();
          int interestRateAsInt = (int) (interestRate * 100);
          writer.println("기본이자: " + interestRateAsInt + "%");
        }
        if (account instanceof HighCreditAccount) {
          double interestRate = ((HighCreditAccount) account).getInterestRate();
          int interestRateAsInt = (int) (interestRate * 100);
          writer.println("기본이자: " + interestRateAsInt + "%");
          String creditRating = ((HighCreditAccount) account).getCreditRating();
          writer.println("신용등급: " + creditRating);
        }
        if (account instanceof SpecialAccount) {
          int depositCount = ((SpecialAccount) account).getDepositCount();
          writer.println("입금 횟수: " + depositCount + "회");
        }
        writer.println("----------------");
      }
      System.out.println("계좌 정보가 텍스트로 자동 저장되었습니다.");
    } catch (IOException e) {
      System.out.println("계좌 정보 저장 중 오류가 발생했습니다.");
    }
  }
}
