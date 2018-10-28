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
	
	private boardVO paramClass = new boardVO(); //�Ķ���͸� ������ ��ü
	private boardVO resultClass = new boardVO(); //���� ��� ���� ������ ��ü
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
	
	//������
	public viewAction() throws IOException {
		
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	
	//�󼼺���
	public String execute() throws Exception {
		
		//�ش� ���� ��ȸ�� +1
		paramClass.setNo(getNo());
		sqlMapper.update("updateReadHit",paramClass);
		
		//�ش� ��ȣ�� ���� �����´�.
		resultClass = (boardVO) sqlMapper.queryForObject("selectOne",getNo());
		
		commentList = sqlMapper.queryForList("commentSelectAll",getNo());
		
		return SUCCESS;
	}
	
	//÷�� ���� �ٿ�ε�
	public String download() throws Exception {
		
		//�ش� ��ȣ�� ���� ������ �����´�.
		resultClass = (boardVO) sqlMapper.queryForObject("selectOne",getNo());
		
		//���� ��ο� ���ϸ��� file ��ü�� �ִ´�.
		File fileInfo = new File(fileUploadPath + resultClass.getFile_savname());
		
		//�ٿ�ε� ���� ���� ����
		setContentLength(fileInfo.length());
		setContentDisposition("attachment;filename="
						+ URLEncoder.encode(resultClass.getFile_orgname(), "UTF-8"));
		setInputStream(new FileInputStream(fileUploadPath
						+ resultClass.getFile_savname()));
		
		return SUCCESS;
	}
	
	//��й�ȣ üũ ��
	public String checkForm() throws Exception {
		
		return SUCCESS;
	}
	
	//��й�ȣ üũ �׼�
	public String checkAction() throws Exception {
		
		//��й�ȣ �Է°� �Ķ���� ����
		paramClass.setNo(getNo());
		paramClass.setPassword(getPassword());
		
		//���� ���� ��й�ȣ ��������
		resultClass = (boardVO) sqlMapper.queryForObject("selectPassword",paramClass);
		
		//�Է��� ��й�ȣ�� Ʋ���� ERROR ����
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
