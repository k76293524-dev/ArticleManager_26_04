package org.example.Controller;

import org.example.Util.Util;
import org.example.dto.Member;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberController extends Controller {

  private Scanner sc;

  private int lastMemberId = 3;
  private List<Member> members;
  private String cmd;
  private Member loginedMember = null;

  public MemberController(Scanner sc) {
    this.sc = sc;
    members = new ArrayList<>();
  }

  public void doAction(String cmd, String actionMethodName) {
    this.cmd = cmd;

    switch (actionMethodName) {
      case "join":
        doJoin();
        break;
      case "login":
        doLogin();
        break;
      case "logout":
        doLogout();
        break;
      default:
        System.out.println("Invalid action method");
        break;
    }
  }

  private void doJoin() {
    System.out.println("==회원 가입==");
    int id = lastMemberId + 1;
    String loginId = null;
    while (true) {
      System.out.print("로그인 아이디 : ");
      loginId = sc.nextLine().trim();
      if (isJoinableLoginId(loginId) == false) {
        System.out.println("이미 사용중인 loginId");
        continue;
      }
      break;
    }
    String password = null;
    while (true) {
      System.out.print("비밀번호 : ");
      password = sc.nextLine().trim();
      System.out.print("비밀번호 확인: ");
      String passwordConfirm = sc.nextLine().trim();
      if (password.equals(passwordConfirm) == false) {
        System.out.println("비번 확인해");
        continue;
      }
      break;
    }
    System.out.print("이름 : ");
    String name = sc.nextLine().trim();
    String regDate = Util.getNowStr();
    String updateDate = Util.getNowStr();

    Member member = new Member(id, regDate, updateDate, loginId, password, name);
    members.add(member);

    System.out.println(id + "번 회원이 가입 되었습니다.");
    lastMemberId++;
  }

  private boolean isJoinableLoginId(String loginId) {
    for (Member member : members) {
      if (member.getLoginId().equals(loginId)) {
        return false;
      }
    }
    return true;
  }


  // 로그인 여부만들기
  private boolean isLogined() {
    return loginedMember != null;
  }

  public void doLogout() {
    if (!isLogined()) {
      System.out.println("이미 로그아웃 중");
      return;
    }
    loginedMember = null;
    System.out.println("로그아웃됨");
  }

  // 로그인 시도
  public void doLogin() {
    if (isLogined()) {
      System.out.println("이미 로그인 중");
      return;
    }
    System.out.println("== 로그인 ==");
    String loginId;
    String loginPw;
    System.out.print("아이디를 입력하세요 : ");
    loginId = sc.nextLine().trim();
    System.out.print("패스워드를 입력하세요 : ");
    loginPw = sc.nextLine().trim();

    Member member = getMemberByLoginId(loginId);

    if (member == null) {
      System.out.println("일치하는 회원이 없습니다.");
      return;
    }

    if (member.getLoginPw().equals(loginPw) == false) {
      System.out.println("비밀번호가 틀렸습니다.");
      return;
    }
    loginedMember = member;

    System.out.println(loginedMember.getName() + "님, 로그인 성공!");
  }

  private Member getMemberByLoginId(String loginId) {
    for (Member member : members) {
      if (member.getLoginId().equals(loginId)) {
        return member;
      }
    }
    return null;
  }

  // 회원 테스트 데이터 생성
  public void makeTestData() {
    System.out.println("==회원 테스트 데이터 생성==");
    members.add(new Member(1, Util.getNowStr(), Util.getNowStr(), "test1", "test1", "회원1"));
    members.add(new Member(2, Util.getNowStr(), Util.getNowStr(), "test2", "test2", "회원2"));
    members.add(new Member(3, Util.getNowStr(), Util.getNowStr(), "test3", "test3", "회원3"));
  }
}