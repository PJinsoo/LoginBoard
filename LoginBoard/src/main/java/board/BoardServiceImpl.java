package board;

import java.sql.Connection;
import java.util.List;

import db_connection.JDBC;
import member.MemberDAO;
import member.MemberDAOImpl;

public class BoardServiceImpl implements BoardService {

	// DAO 객체
	private BoardDAO dao = new BoardDAOImpl();
	private MemberDAO memberDao = new MemberDAOImpl();

	// 게시글 전체 출력
	@Override
	public List<BoardDTO> selectList() {

		Connection conn = JDBC.getConnection();
		List<BoardDTO> res = dao.selectList(conn);

		return res;
	}
	
	//게시글 검색
	@Override
	public List<BoardDTO> search(String searchOption, String searchWord){

		Connection conn = JDBC.getConnection();
		List<BoardDTO> res = dao.search(conn, searchOption, searchWord);
		
		return res;
	}

	// 게시글 하나 보기
	@Override
	public BoardDTO selectOne(int boardNo) {
		Connection conn = JDBC.getConnection();

		// boardNo을 PK로 하여 DAO를 통해 DB에서 데이터 추출
		BoardDTO boardDto = dao.selectOne(conn, boardNo);
		JDBC.close(conn);

		return boardDto;
	}

	// 조회수 증가
	@Override
	public boolean countingView(BoardDTO boardDto) {
		Connection conn = JDBC.getConnection();

		boolean res = dao.countingView(conn, boardDto);

		if (res) {
			JDBC.commit(conn);
		}
		JDBC.close(conn);

		return res;
	}
	
	//추천하기
	@Override
	public boolean recommend(int boardNo) {
		Connection conn = JDBC.getConnection();

		boolean res = dao.recommend(conn, boardNo);
		
		if (res) {
			JDBC.commit(conn);
		}
		JDBC.close(conn);
		
		return res;
	}
	
	//추천 시 증가하는 조회수를 원상복구
	public boolean downView(int boardNo) {
		Connection conn = JDBC.getConnection();

		boolean res = dao.downView(conn, boardNo);
		
		if (res) {
			JDBC.commit(conn);
		}
		JDBC.close(conn);
		
		return res;
	}

	// 게시글 작성
	@Override
	public boolean insert(BoardDTO boardDto) {
		Connection conn = JDBC.getConnection();
		
		// DAO, DB 작업수행
		boolean res = dao.insert(conn, boardDto);

		// DAO에서 작업이 올바르게 수행됐다면 커밋
		if (res) {
			JDBC.commit(conn);
		}
		JDBC.close(conn);
		return res;
	}

	//사용자 신뢰성 검사(UID 일치 체크)
	@Override
	public boolean checkUID(BoardDTO boardDto) {
		Connection conn = JDBC.getConnection();
		boolean res = dao.checkUID(conn, boardDto);
		
		JDBC.close(conn);
		return res;
	}
	
	//게시글 수정
	@Override
	public boolean update(BoardDTO boardDto) {
		Connection conn = JDBC.getConnection();
		
		boolean res = dao.update(conn, boardDto);
		
		if (res) {
			JDBC.commit(conn);
		}
		JDBC.close(conn);
		return res;
	}

	@Override
	public boolean delete(int boardNo) {
		Connection conn = JDBC.getConnection();
		
		boolean res = dao.delete(conn, boardNo);
		
		if (res) {
			JDBC.commit(conn);
		}
		JDBC.close(conn);
		return res;
	}

}
