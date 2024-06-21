package banking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

//컨트롤 클래스로 프로그램의 전반적인 기능을 구현한다. 
public class AccountManager {

  // 둘 이상의 고객 정보 저장을 위해서 배열을 사용한다.(객체배열)
  // private Account[] accounts;
  // 고객정보 저장을 위해 Set을 사용한다.
  private Set<Account> accounts;
  // 배열의 인덱스 관리를 위한 변수
  private int index;

  // 자동저장을 위한 AutoSaver 객체 생성
  private AutoSaver autoSaver;

  // 메뉴를 출력하는 메서드
  public void menuShow() {
    System.out.println("-----Menu-----");
    System.out.println("1. 계좌 개설");
    System.out.println("2. 입  급");
    System.out.println("3. 출  금");
    System.out.println("4. 계좌정보출력");
    System.out.println("5. 계좌정보삭제");
    System.out.println("6. 저장옵션");
    System.out.println("7. 퍼즐게임");
    System.out.println("8. 프로그램 종료");
    System.out.print("선택: ");
  }

  // 배열의 초기화
  public AccountManager() {
    // accounts = new Account[50];
    accounts = new HashSet<>();
    index = 0;

    // AutoSaver 객체 생성
    this.autoSaver = new AutoSaver();
  }

  // 계좌개설을 위한 함수
  public void makeAccount() {
    try {
      Scanner scan = new Scanner(System.in);
      String ino, iname;
      int ibal;
      // newAccount 객체를 만들어서 accounts에 추가
      Account newAccount;
      System.out.println("***신규계좌개설***");
      System.out.println("---계좌선택---");
      System.out.println("1. 보통예금계좌");
      System.out.println("2. 신용신뢰계좌");
      System.out.println("3. 특판계좌");
      System.out.print("선택: ");
      int choice = scan.nextInt();
      scan.nextLine(); // 버퍼비우기
      if (choice == 1) {
        System.out.println("보통예금계좌선택");
        System.out.print("계좌번호: ");
        ino = scan.nextLine();
        System.out.print("고객이름: ");
        iname = scan.nextLine();
        System.out.print("잔고: ");
        ibal = scan.nextInt();
        System.out.print("기본이자%(정수형태로입력): ");
        int inrate = scan.nextInt();
        scan.nextLine(); // 버퍼비우기
        double interestRate = inrate / 100.0; // 이자율을 소수로 변환
        // accounts[index] = new NormalAccount(ino, iname, ibal, interestRate);
        newAccount = new NormalAccount(ino, iname, ibal, interestRate);
        index++;
      } else if (choice == 2) {
        System.out.println("신용신뢰계좌선택");
        System.out.print("계좌번호: ");
        ino = scan.nextLine();
        System.out.print("고객이름: ");
        iname = scan.nextLine();
        System.out.print("잔고: ");
        ibal = scan.nextInt();
        System.out.print("기본이자%(정수형태로입력): ");
        int inrate = scan.nextInt();
        System.out.print("신용등급(A,B,C등급): ");
        scan.nextLine(); // 버퍼비우기
        String credit = scan.nextLine();
        // accounts[index] = new HighCreditAccount(ino, iname, ibal, inrate / 100.0,
        // credit);
        newAccount = new HighCreditAccount(ino, iname, ibal, inrate / 100.0, credit);
        index++;
      } else if (choice == 3) {
        System.out.println("특판계좌선택");
        System.out.print("계좌번호: ");
        ino = scan.nextLine();
        System.out.print("고객이름: ");
        iname = scan.nextLine();
        System.out.print("잔고: ");
        ibal = scan.nextInt();
        System.out.print("기본이자%(정수형태로입력): ");
        int inrate = scan.nextInt();
        scan.nextLine(); // 버퍼비우기
        // accounts[index] = new SpecialAccount(ino, iname, ibal, inrate / 100.0);
        newAccount = new SpecialAccount(ino, iname, ibal, inrate / 100.0);
        index++;
      }

      else {
        System.out.println("계좌선택이 잘못되었습니다.");
        return;
      }
      // 중복계좌발견시 덮어쓸지 물어보기
      if (accounts.contains(newAccount)) {
        System.out.println("중복계좌발견됨. 덮어쓸까요?(y or n)");
        String overwrite = scan.nextLine();
        if (overwrite.equalsIgnoreCase("y")) {
          accounts.remove(newAccount);
          accounts.add(newAccount);
          System.out.println("덮어쓰기가 되었습니다.");
          System.out.println("계좌개설이 완료되었습니다.");
        } else {
          System.out.println("기존의 정보를 유지합니다.");
        }
      } else {
        accounts.add(newAccount);
        System.out.println("계좌개설이 완료되었습니다.");
      }
    } catch (InputMismatchException e) {
      System.out.println("잘못된 입력입니다. 정수를 입력해주세요.");
    }
  }

  // 입금
  public void depositMoney() {
    Scanner scan = new Scanner(System.in);
    String ino;
    int money;
    System.out.println("***입  금***");
    System.out.println("계좌번호와 입금할 금액을 입력하세요.");
    System.out.print("계좌번호: ");
    ino = scan.nextLine();
    try {
      System.out.print("입금액: ");
      money = scan.nextInt();
      if (money < 0) {
        System.out.println("입금액은 음수가 될 수 없습니다.");
        return;
      }
      if (money % 500 != 0) {
        System.out.println("입금액은 500원 단위로 가능합니다.");
        return;
      }
    } catch (InputMismatchException e) {
      System.out.println("입금액은 숫자로 입력해주세요.");
      return;
    }
    for (Account accounts : accounts) {
      if (accounts.getAccountNo().equals(ino)) {
        accounts.deposit(money);
        System.out.println("입금이 완료되었습니다.");
        return;
      }
    }
    System.out.println("입금할 계좌번호가 존재하지 않습니다.");
  }

  // 출금
  public void withdrawMoney() {
    Scanner scan = new Scanner(System.in);
    String ino;
    int money;
    System.out.println("***출  금***");
    System.out.println("계좌번호와 출금할 금액을 입력하세요.");
    System.out.print("계좌번호: ");
    ino = scan.nextLine();
    try {
      System.out.print("출금액: ");
      money = scan.nextInt();
      if (money < 0) {
        System.out.println("출금액은 음수가 될 수 없습니다.");
        return;
      }
      if (money % 1000 != 0) {
        System.out.println("출금액은 1000원 단위로 가능합니다.");
        return;
      }
    } catch (InputMismatchException e) {
      System.out.println("출금액은 숫자로 입력해주세요.");
      return;
    }
    for (Account accounts : accounts) {
      if (accounts.getAccountNo().equals(ino)) {
        if (accounts.getBalance() < money) {
          System.out.println("잔고가 부족합니다. 금액전체를 출금할까요? (y/n)");
          String response = scan.next();
          if (response.equalsIgnoreCase("y")) {
            accounts.withdraw(accounts.getBalance());
            System.out.println("금액전체를 출금하였습니다.");
          } else {
            System.out.println("출금요청이 취소되었습니다.");
          }
          return;
        }
        accounts.withdraw(money);
        System.out.println("출금이 완료되었습니다.");
        return;
      }
    }
    System.out.println("출금할 계좌번호가 존재하지 않습니다.");
  }

  // 전체계좌정보출력
  public void showAccInfo() {
    System.out.println("***계좌정보출력***");
    System.out.println("----------------");
    for (Account account : accounts) {
      System.out.println("계좌번호: " + account.getAccountNo()); // account 객체에 대해 getAccountNo() 호출
      System.out.println("고객이름: " + account.getOwnerName()); // account 객체에 대해 getOwnerName() 호출
      System.out.println("잔고: " + account.getBalance()); // account 객체에 대해 getBalance() 호출
      if (account instanceof NormalAccount) {
        double interestRate = ((NormalAccount) account).getInterestRate(); // account 객체에 대해 getInterestRate() 호출
        int interestRateAsInt = (int) (interestRate * 100); // 이자율을 정수로 변환
        System.out.println("기본이자: " + interestRateAsInt + "%");
      }
      if (account instanceof SpecialAccount) {
        int depositCount = ((SpecialAccount) account).getDepositCount(); // account 객체에 대해 getDepositCount() 호출
        System.out.println("입금 횟수: " + depositCount + "회");
      }
      if (account instanceof HighCreditAccount) {
        double interestRate = ((HighCreditAccount) account).getInterestRate(); // account 객체에 대해 getInterestRate() 호출
        int interestRateAsInt = (int) (interestRate * 100); // 이자율을 정수로 변환
        System.out.println("기본이자: " + interestRateAsInt + "%");
        String creditRating = ((HighCreditAccount) account).getCreditRating(); // account 객체에 대해 getCreditRating() 호출
        System.out.println("신용등급: " + creditRating);
      }
      System.out.println("----------------");
    }
    System.out.println("전체계좌정보 출력이 완료되었습니다.");
  }

  // 계좌정보삭제
  public void removeAccount() {
    try {
      Scanner scan = new Scanner(System.in);
      System.out.println("삭제할 계좌번호를 입력하세요.");
      System.out.print("계좌번호: ");
      String accountNo = scan.nextLine();

      if (accountNo.isEmpty()) {
        System.out.println("계좌번호를 입력해주세요.");
        return;
      }

      if (accounts == null) {
        System.out.println("계좌 정보가 없습니다.");
        return;
      }

      Account targetAccount = null;
      for (Account account : accounts) {
        if (account.getAccountNo().equals(accountNo)) {
          targetAccount = account;
          break;
        }
      }

      if (targetAccount != null) {
        accounts.remove(targetAccount);
        System.out.println("계좌가 삭제되었습니다.");
      } else {
        System.out.println("일치하는 계좌가 없습니다.");
      }
    } catch (Exception e) {
      System.out.println("오류가 발생했습니다: " + e.getMessage());
    }
  }

  // 파일에 객체를 저장하는 메소드
  public void saveAccount() {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/banking/AccountInfo.obj"))) {
      oos.writeObject(accounts);
      System.out.println("계좌 정보가 파일에 저장되었습니다.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // 파일에서 객체를 불러오는 메소드
  public void loadAccount() {
    File file = new File("src/banking/AccountInfo.obj");
    if (file.exists()) {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        accounts = (HashSet<Account>) ois.readObject();
        System.out.println("AccountInfo.obj 복원 완료");
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  // 자동저장
  public void autoSave() {
    try {
      Scanner scan = new Scanner(System.in);
      System.out.println("1. 자동저장On");
      System.out.println("2. 자동저장Off");
      System.out.print("선택: ");
      int choice = scan.nextInt();
      scan.nextLine(); // 버퍼비우기
      switch (choice) {
        case 1:
          autoSaver.setAccounts(accounts);
          autoSaver.startAutoSave();
          return; // 메서드 종료
        case 2:
          autoSaver.stopAutoSave();
          return; // 메서드 종료
        default:
          System.out.println("잘못된 선택입니다.");
          return; // 메서드 종료
      }
    } catch (InputMismatchException e) {
      System.out.println("잘못된 입력입니다. 정수를 입력해주세요.");
    }
  }
}