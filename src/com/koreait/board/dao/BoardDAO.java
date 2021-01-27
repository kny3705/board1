package com.koreait.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.koreait.board.model.BoardDTO;
import com.koreait.board.model.BoardEntity;

public class BoardDAO {
	public static void insBoard(BoardEntity vo) {
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = "INSERT INTO t_board"
				+ " (title, ctnt)"
				+ " VALUES"
				+ " (?, ?)";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getTitle());
			ps.setString(2, vo.getCtnt());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps);
		}
	}
	
	public static List<BoardEntity> selBoardList(BoardDTO param) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "SELECT i_board, title, r_dt FROM t_board ORDER BY i_board DESC LIMIT ?, ?";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getStartIdx());
			ps.setInt(2, param.getRowCountPerPage());
			rs = ps.executeQuery();	//select문은 무조건 executeQuery, ResultSet을 return해줌
			List<BoardEntity> list = new ArrayList<>();
			while(rs.next()) {
				BoardEntity vo = new BoardEntity();
				vo.setI_board(rs.getInt("i_board"));
				vo.setTitle(rs.getString("title"));
				vo.setR_dt(rs.getString("r_dt"));
				list.add(vo);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps);
		}
		return null;
	}
	
	public static BoardEntity selBoard(BoardEntity param) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		
		String sql = "SELECT title, ctnt, r_dt FROM t_board WHERE i_board = ?";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getI_board());
			rs = ps.executeQuery();
			
			if(rs.next()) {	//레코드가 있다면 true 없다면 false
				BoardEntity vo = new BoardEntity();
				vo.setTitle(rs.getString("title"));
				vo.setCtnt(rs.getString("ctnt"));
				vo.setR_dt(rs.getString("r_dt"));
				vo.setI_board(param.getI_board());
				return vo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return null;
	}
	
	public static int selPageLength(BoardDTO param) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "SELECT CEIL(COUNT(i_board) / ?) FROM t_board";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getRowCountPerPage());
			rs = ps.executeQuery();
			if(rs.next()) {				
				return rs.getInt(1);
				//count와 같이 그룹 함수를 썼을 때 레코드가 한줄 이상 나옴
				//0번째 없음, 정수 값 1을 보내면 첫번째 컬럼을 의미 (컬럼 인덱스)
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return 0;
	}

	public static int delBoard(BoardEntity param) {
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = "DELETE FROM t_board WHERE i_board = ?";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getI_board());
			return ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps);
		}
		return 0;
	}
	
	public static int updBoard(BoardEntity param) {
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = "UPDATE t_board SET title = ?, ctnt = ? WHERE i_board = ?";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setNString(1, param.getTitle());
			ps.setString(2, param.getCtnt());
			ps.setInt(3, param.getI_board());
			return ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps);
		}
		
		return 0;
	}
	
}
