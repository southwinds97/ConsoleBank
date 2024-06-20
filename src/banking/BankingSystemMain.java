package banking;

import java.util.*;

interface BankingOperations {
  int MAKE = 1;
  int DEPOSIT = 2;
  int WITHDRAW = 3;
  int INQUIRE = 4;
  int DELETE = 5;
  int AUTOSAVE = 6;
  int PUZZLEGAME = 7;
  int EXIT = 8;
}

// main 메서드를 포함한 클래스. 프로그램은 여기서 실행한다.
public class BankingSystemMain implements BankingOperations {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    AutoSaver autoSaver = new AutoSaver(); // AutoSaver 객체 생성
    AccountManager acmg = new AccountManager();

    // 프로그램 시작 시 파일에서 계좌 정보 불러오기
    acmg.loadAccount();

    while (true) {
      // 1.메뉴 출력
      acmg.menuShow();
      // 2.메뉴 선택
      int choice;
      try {
        choice = scan.nextInt();
        if (choice < MAKE || choice > EXIT) {
          throw new IllegalArgumentException("잘못된 메뉴 선택입니다.");
        }
      } catch (InputMismatchException e) {
        System.out.println("정수를 입력해주세요.");
        scan.nextLine(); // 버퍼비우기
        continue;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
        continue;
      }
      scan.nextLine(); // 버퍼비우기
      // 3.선택된 메뉴에 따른 처리
      switch (choice) {
        case MAKE:
          acmg.makeAccount();
          break;
        case DEPOSIT:
          acmg.depositMoney();
          break;
        case WITHDRAW:
          acmg.withdrawMoney();
          break;
        case INQUIRE:
          acmg.showAccInfo();
          break;
        case DELETE:
          acmg.removeAccount();
          break;
        case AUTOSAVE:
          acmg.autoSave();
          break;
        case PUZZLEGAME:
          PuzzleGame game = new PuzzleGame();
          if (!game.gameStart()) {
            continue;
          }
          break;
        case EXIT:
          // 프로그램 종료 시 파일에 계좌 정보 저장
          acmg.saveAccount();
          System.out.println("프로그램을 종료합니다.");
          return;
      }
    }
  }
}
