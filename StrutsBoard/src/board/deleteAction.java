package board;

import com.opensymphony.xwork2.ActionSupport;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.File;
import java.io.Reader;
import java.io.IOException;

public class deleteAction extends ActionSupport {
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	private boardVO paramClass;
	private boardVO resultClass;
	
	private cboardVO cClass = new cboardVO();
	private cboardVO cResult = new cboardVO();
	
	private int currentPage;
	
	private String fileUploadPath = "C:\\Java\\upload\\";
	
	private int no;
	
	//생성자
	public deleteAction() throws IOException {
		
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	
	//게시글 글 삭제
	public String execute() throws Exception {
		
		//파라미터와 리절트 객체 생성
		paramClass = new boardVO();
		resultClass = new boardVO();
		
		//해당 번호의 글을 가져온다.
		resultClass = (boardVO) sqlMapper.queryForObject("selectOne",getNo());
		
		//서버 파일 삭제
		File deleteFile = new File(fileUploadPath + resultClass.getFile_savname());
		deleteFile.delete();
		
		//삭제할 항목 설정
		paramClass.setNo(getNo());
		
		//삭제 쿼리 수행
		sqlMapper.update("deleteBoard",paramClass);
		
		return SUCCESS;
	}
	
	public String execute2() throws Exception {
		cClass = new cboardVO();
		cResult = new cboardVO();
		
		cClass.setNo(getNo());
		
		sqlMapper.update("deleteComment",cClass);
		
		return SUCCESS;
	}

	public boardVO getParamClass() {
		return paramClass;
	}

	public void setParamClass(boardVO paramClass) {
		this.paramClass = paramClass;
	}

	public boardVO getResultClass() {
		return resultClass;
	}

	public void setResultClass(boardVO resultClass) {
		this.resultClass = resultClass;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getFileUploadPath() {
		return fileUploadPath;
	}

	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}
	
	

}
