package banking;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AutoSaver extends Thread {
  private boolean autoSaveOn;
  private Set<Account> accounts;

  public AutoSaver() {
    this.autoSaveOn = false;
    this.accounts = new HashSet<>();
    setDaemon(true); // AutoSaver 쓰레드를 데몬 쓰레드로 설정
  }

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

  private void saveAccountInfo() {
    try (FileWriter writer = new FileWriter("src/banking/AutoSaveAccount.txt")) {
      for (Account account : accounts) {
        writer.write("계좌번호: " + account.getAccountNo() + "\n");
        writer.write("고객이름: " + account.getOwnerName() + "\n");
        writer.write("잔고: " + account.getBalance() + "\n");
        if (account instanceof NormalAccount) {
          double interestRate = ((NormalAccount) account).getInterestRate();
          int interestRateAsInt = (int) (interestRate * 100);
          writer.write("기본이자: " + interestRateAsInt + "%\n");
        }
        if (account instanceof HighCreditAccount) {
          double interestRate = ((HighCreditAccount) account).getInterestRate();
          int interestRateAsInt = (int) (interestRate * 100);
          writer.write("기본이자: " + interestRateAsInt + "%\n");
          String creditRating = ((HighCreditAccount) account).getCreditRating();
          writer.write("신용등급: " + creditRating + "\n");
        }
        if (account instanceof SpecialAccount) {
          int depositCount = ((SpecialAccount) account).getDepositCount();
          writer.write("입금 횟수: " + depositCount + "회\n");
        }
        writer.write("----------------\n");
      }
      System.out.println("계좌 정보가 텍스트로 자동 저장되었습니다.");
    } catch (IOException e) {
      System.out.println("계좌 정보 저장 중 오류가 발생했습니다.");
    }
  }
}