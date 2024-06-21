package banking;

public class MenuSelectException extends Exception {
  // 메뉴 선택이 잘못되었을 때 발생하는 예외
  public static void validate(int choice) throws MenuSelectException {
    if (choice < ICustomDefine.MAKE || choice > ICustomDefine.EXIT) {
      throw new MenuSelectException("잘못된 메뉴 선택입니다.");
    }
  }

  // 생성자
  public MenuSelectException(String message) {
    super(message);
  }
}