package com.pattern;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String , Command> map;
	// Map<Key타입, Value타입>
	// Key타입 : String  why? 사용자의 요청이 들어오면 "/insert.do"와 같은 식별값을 활용하여 구분함
	
	@Override
	public void init() throws ServletException{
		// 서버가 실행할 때 특정값들을 초기화해주는 메소드 ( 단 한번만 실행됨 )
		// map이 인터페이스 이므로 구현받은 HashMap을 불러옴
		map = new HashMap<String, Command>();
		
		// map.put("요청식별값" , 식별값과 연관된 객체)
		map.put("/insert.do", new InsertService());
		map.put("/update.do", new UpdateService());
		map.put("/delete.do", new DeleteService());
		map.put("/select.do", new SelectService());
	}

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
		
		// MAP이 있으니 조건문 삭제 ( before_Map 참고 )
		Command com = map.get(command);
		com.execute(request, response);
	}

}
