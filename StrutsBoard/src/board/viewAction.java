package board;

import com.opensymphony.xwork2.ActionSupport;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.Reader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class viewAction extends ActionSupport {
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	private boardVO paramClass = new boardVO(); //파라미터를 저장할 객체
	private boardVO resultClass = new boardVO(); //쿼리 결과 값을 저장할 객체
	private List<cboardVO> commentList = new ArrayList<cboardVO>();
	
	private cboardVO cClass = new cboardVO();
	private cboardVO cResult = new cboardVO();
	
	private int currentPage;
	
	private int no;
	private int originno;
	
	private String password;
	
	private String fileUploadPath = "C:\\Java\\upload\\";
	
	private InputStream inputStream;
	private String contentDisposition;
	private long contentLength;
	
	//생성자
	public viewAction() throws IOException {
		
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	
	//상세보기
	public String execute() throws Exception {
		
		//해당 글의 조회수 +1
		paramClass.setNo(getNo());
		sqlMapper.update("updateReadHit",paramClass);
		
		//해당 번호의 글을 가져온다.
		resultClass = (boardVO) sqlMapper.queryForObject("selectOne",getNo());
		
		commentList = sqlMapper.queryForList("commentSelectAll",getNo());
		
		return SUCCESS;
	}
	
	//첨부 파일 다운로드
	public String download() throws Exception {
		
		//해당 번호의 파일 정보를 가져온다.
		resultClass = (boardVO) sqlMapper.queryForObject("selectOne",getNo());
		
		//파일 경로와 파일명을 file 객체에 넣는다.
		File fileInfo = new File(fileUploadPath + resultClass.getFile_savname());
		
		//다운로드 파일 정보 설정
		setContentLength(fileInfo.length());
		setContentDisposition("attachment;filename="
						+ URLEncoder.encode(resultClass.getFile_orgname(), "UTF-8"));
		setInputStream(new FileInputStream(fileUploadPath
						+ resultClass.getFile_savname()));
		
		return SUCCESS;
	}
	
	//비밀번호 체크 폼
	public String checkForm() throws Exception {
		
		return SUCCESS;
	}
	
	//비밀번호 체크 액션
	public String checkAction() throws Exception {
		
		//비밀번호 입력값 파라미터 설정
		paramClass.setNo(getNo());
		paramClass.setPassword(getPassword());
		
		//현재 글의 비밀번호 가져오기
		resultClass = (boardVO) sqlMapper.queryForObject("selectPassword",paramClass);
		
		//입력한 비밀번호가 틀리면 ERROR 리턴
		if(resultClass == null) {
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public String checkAction2() throws Exception {
		cClass.setNo(getNo());
		cClass.setPassword(getPassword());
		cClass.setOriginno(getOriginno());
		cResult = (cboardVO) sqlMapper.queryForObject("selectPassword2",cClass);
		
		if(cResult == null)
			return ERROR;
		
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

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFileUploadPath() {
		return fileUploadPath;
	}

	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public List<cboardVO> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<cboardVO> commentList) {
		this.commentList = commentList;
	}

	public cboardVO getcClass() {
		return cClass;
	}

	public void setcClass(cboardVO cClass) {
		this.cClass = cClass;
	}

	public cboardVO getcResult() {
		return cResult;
	}

	public void setcResult(cboardVO cResult) {
		this.cResult = cResult;
	}

	public int getOriginno() {
		return originno;
	}

	public void setOriginno(int originno) {
		this.originno = originno;
	}
	
	

}
