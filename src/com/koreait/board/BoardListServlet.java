package com.koreait.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board.dao.BoardDAO;
import com.koreait.board.model.BoardDTO;

@WebServlet("/list")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strPage = request.getParameter("page");
		int page = strPage == null ? 1 : Integer.parseInt(strPage);
		
		int rowCnt = 5;
		BoardDTO param = new BoardDTO();
		param.setRowCountPerPage(rowCnt);
		param.setStartIdx((page-1)*rowCnt);
		
		request.setAttribute("pageLength", BoardDAO.selPageLength(param));
		request.setAttribute("list", BoardDAO.selBoardList(param));
		
		String jsp = "/WEB-INF/jsp/list.jsp";
		request.getRequestDispatcher(jsp).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
