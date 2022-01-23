package com.pattern;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.doo") // ������ do
public class FrontController_beforeMap extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		if (command.equals("/insert.do")) {
			// ������ �߰�
			Command insert = new InsertService();
			insert.execute(request, response);
		} else if (command.equals("/update.do")) {
			// ������ ����
			Command update = new UpdateService();
			update.execute(request, response);
		} else if (command.equals("/delete.do")) {
			// ������ ����
			Command delete = new DeleteService();
			delete.execute(request, response);
		} else if (command.equals("/select.do")) {
			// ������ ��ȸ
			Command select = new SelectService();
			select.execute(request, response);
		}
	}

}
