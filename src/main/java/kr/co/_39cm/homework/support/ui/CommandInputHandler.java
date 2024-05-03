package kr.co._39cm.homework.support.ui;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CommandInputHandler {

    private final Scanner scanner = new Scanner(System.in);

    public String listenCommand() {
        System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
        return scanner.nextLine();
    }

    public void endProgram() {
        System.out.println("고객님의 주문 감사합니다.");
    }

    public void tryAgain() {
        System.out.print("알수없는 커멘드 입니다. 입력(o[order], 주문, q[quit] 커멘드를 입력해주세요.");
    }
}
