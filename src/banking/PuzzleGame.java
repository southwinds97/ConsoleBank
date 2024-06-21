package banking;

import java.util.Scanner;

public class PuzzleGame {
    // 3x3배열
    int[][] panCount = new int[3][3];
    // 공백위치
    int brow = 0;
    int bcol = 0;

    // 0~8 난수발생
    public void getRand() {
        int[] com = new int[9]; // 중복이 없는 난수값을 저장
        int su = 0; // 난수 발생시 저장한 변수
        boolean bCheck = false; // 중복 여부 확인
        for (int i = 0; i < 9; i++) {
            // 난수발생
            bCheck = true;
            while (bCheck) {
                su = (int) (Math.random() * 9);
                bCheck = false;
                // 검색 => 저장된 데이터가 중복여부 확인
                for (int j = 0; j < i; j++) {
                    if (su == com[j]) // 같은 수가 저장
                    {
                        // while을 다시 수행
                        bCheck = true;
                        break;
                    }
                }
            }
            // 난수값을 저장
            com[i] = su;
            // 3x3배열에 저장
            panCount[i / 3][i % 3] = su;
            // 공백위치 저장
            if (su == 8) {
                // 행,열 저장
                brow = i / 3;
                bcol = i % 3;
            }
        }
    }

    // 배치함수
    public void display() {
        // 3x3배열 출력
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // 공백위치 출력
                if (i == brow && j == bcol) {
                    System.out.print("X ");
                } else {
                    // 배열값 출력
                    System.out.print(panCount[i][j] + 1 + " ");
                }
            }
            System.out.println();
        }
    }

    // 종료여부확인
    public boolean isEnd() {
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (panCount[i][j] != k)
                    return false;
                k++;
            }
        }
        return true;
    }

    // 이동함수
    public void move(String direction) {
        // 이동하려는 위치
        int newBrow = brow;
        int newBcol = bcol;
        // 이동방향에 따른 공백위치 변경
        switch (direction) {
            case "w":
                newBrow++;
                break;
            case "s":
                newBrow--;
                break;
            case "a":
                newBcol++;
                break;
            case "d":
                newBcol--;
                break;
        }
        // 이동가능여부확인(이동하려는 위치가 3x3배열안에 있는지 확인)
        if (newBrow >= 0 && newBrow < 3 && newBcol >= 0 && newBcol < 3) {
            // 이동하려는 위치의 값을 현재위치에 저장
            panCount[brow][bcol] = panCount[newBrow][newBcol];
            // 이동하려는 위치에 공백값을 저장(공백값은 8)
            panCount[newBrow][newBcol] = 8;
            // 이동하는 위치를 현재위치로 변경
            brow = newBrow;
            bcol = newBcol;
        } else {
            System.out.println("xxxxxxxxxxxxx");
            System.out.println("xx이동불가xx");
            System.out.println("xxxxxxxxxxxxx");
        }
    }

    public boolean gameStart() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=====");
            PuzzleGame game = new PuzzleGame();
            game.getRand();
            game.display();

            while (true) {
                if (game.isEnd()) {
                    System.out.println("===정답입니다.===");
                    game.display();
                    System.out.println("===============");
                    break;
                }
                System.out.println("=====");
                System.out.println("[ 이동 ] a:Left d:Right w:Up s:Down");
                System.out.println("[ 종료 ] x:Exit");
                System.out.print("키를 입력하세요: ");
                String input = scanner.nextLine();
                if (input.equals("x")) {
                    break;
                }
                game.move(input);
                System.out.println("=====");
                game.display();
            }

            System.out.print("완료되었습니다. 재시작하시겠습니까? (y/n): ");
            String restart = scanner.nextLine();
            if (!restart.equalsIgnoreCase("y")) {
                return false;
            }
        }
    }
}
