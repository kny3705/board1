package com.koreait.board.model;

public class BoardDTO {
	private int rowCountPerPage;	//한 페이지당 행 수
	private int startIdx;
	
	public int getRowCountPerPage() {
		return rowCountPerPage;
	}
	public void setRowCountPerPage(int rowCountPerPage) {
		this.rowCountPerPage = rowCountPerPage;
	}
	public int getStartIdx() {
		return startIdx;
	}
	public void setStartIdx(int startIdx) {
		this.startIdx = startIdx;
	}
}
