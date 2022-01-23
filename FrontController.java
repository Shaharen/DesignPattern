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
	// Map<KeyŸ��, ValueŸ��>
	// KeyŸ�� : String  why? ������� ��û�� ������ "/insert.do"�� ���� �ĺ����� Ȱ���Ͽ� ������
	
	@Override
	public void init() throws ServletException{
		// ������ ������ �� Ư�������� �ʱ�ȭ���ִ� �޼ҵ� ( �� �ѹ��� ����� )
		// map�� �������̽� �̹Ƿ� �������� HashMap�� �ҷ���
		map = new HashMap<String, Command>();
		
		// map.put("��û�ĺ���" , �ĺ����� ������ ��ü)
		map.put("/insert.do", new InsertService());
		map.put("/update.do", new UpdateService());
		map.put("/delete.do", new DeleteService());
		map.put("/select.do", new SelectService());
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// FrontController����
		// - ������� ��� ��û�� �� ������ ������ �� �ְ� �ϴ� ����
		// - URLMapping �� ��θ� "*.do" ����
		// - ��� ��û���� .do�� �ٿ��� �ϳ��� �������� ���� ��
		// ���� : �� ���� �߾�����ȭ�ǹǷ� ������ ������� �ϳ��� ����� ������ �����
		// ��� ����� ����� �� ���� �ȴٴ� �������� �߻�! -> ��ɺ� �Ϲ� Ŭ������ �и� ( ���� �ƴ� )
		
		// ���� vs �Ϲ�Ŭ����
		// - HttpServlet�� ��� �޾Ҵ°�? Yes - ���� / No - Ŭ����
		// - ���������� ������ �޸𸮸� ����ϴ°�? Yes - ���� / No - Ŭ����
		
		// �������̽��� Ȱ���ؼ� �Ϲ�Ŭ���� ����
		// - ���Ŀ� ������� ���񽺿� ���ؼ� ������ �޼ҵ�� ������ ����

		// Command ����
		// - ������� ��û�� ���� ó���� �� �ִ� �Ϲ� Ŭ�������� ����� �޼ҵ�� ������ �� �ֵ��� �ϴ� ����
		// - execute(HttpServletRequest, HttpServletResponse) �߻� �޼ҵ� ����
		// - �Ϲ� Ŭ������ implements Ű���带 �̿��ؼ� �������̽��� ����
		
		// getRequestURI() : /DesignPattern/insert.do
		// getContextPath() : /DesignPattern
		String reqURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		// insert.do�� �̱����� �ε��� substring
		// command : /insert.do ---> �׳� getServletPath() �ᵵ ��
		
		// ���ϰ��(���� ���ε� ���) �� ��ȯ
		String command = request.getServletPath();
		System.out.println("��û�ĺ��� >> " + command);
		
		// MAP�� ������ ���ǹ� ���� ( before_Map ���� )
		Command com = map.get(command);
		com.execute(request, response);
	}

}
