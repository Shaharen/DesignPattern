package com.pattern;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.doo") // 원래는 do
public class FrontController_beforeMap extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// FrontController패턴
		// - 사용자의 모든 요청을 한 곳으로 전송할 수 있게 하는 구조
		// - URLMapping 의 경로를 "*.do" 설정
		// - 모든 요청에는 .do를 붙여야 하나의 서블릿으로 오게 됨
		// 단점 : 한 곳에 중앙집중화되므로 로직이 길어지고 하나의 기능이 문제가 생기면
		// 모든 기능을 사용할 수 없게 된다는 문제점이 발생! -> 기능별 일반 클래스로 분리 ( 서블릿 아님 )

		// 서블릿 vs 일반클래스
		// - HttpServlet을 상속 받았는가? Yes - 서블릿 / No - 클래스
		// - 지속적으로 서버의 메모리를 사용하는가? Yes - 서블릿 / No - 클래스

		// 인터페이스를 활용해서 일반클래스 구현
		// - 이후에 만들어질 서비스에 대해서 동일한 메소드로 구현을 강제

		// Command 패턴
		// - 사용자의 요청에 따라 처리할 수 있는 일반 클래스들의 공통된 메소드로 구현될 수 있도록 하는 구조
		// - execute(HttpServletRequest, HttpServletResponse) 추상 메소드 구현
		// - 일반 클래스에 implements 키워드를 이용해서 인터페이스를 구현

		// getRequestURI() : /DesignPattern/insert.do
		// getContextPath() : /DesignPattern
		String reqURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		// insert.do만 뽑기위해 인덱싱 substring
		// command : /insert.do ---> 그냥 getServletPath() 써도 됨

		// 파일경로(서블릿 맵핑된 경로) 값 반환
		String command = request.getServletPath();
		System.out.println("요청식별값 >> " + command);

		if (command.equals("/insert.do")) {
			// 데이터 추가
			Command insert = new InsertService();
			insert.execute(request, response);
		} else if (command.equals("/update.do")) {
			// 데이터 수정
			Command update = new UpdateService();
			update.execute(request, response);
		} else if (command.equals("/delete.do")) {
			// 데이터 삭제
			Command delete = new DeleteService();
			delete.execute(request, response);
		} else if (command.equals("/select.do")) {
			// 데이터 조회
			Command select = new SelectService();
			select.execute(request, response);
		}
	}

}
