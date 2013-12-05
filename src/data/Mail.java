package data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * �ʼ���
 * @author spacelis
 *
 */
public class Mail implements Serializable {

	private String from = "";
	private String to = "";
	private String cc = "";
	private String bcc = "";
	private String subject = "";
	private String content = "";
	private String extraFilename = "";
	private long time = 0; //�ú�������¼�ʼ�ʱ��
	private String original = "";
	private String status = "";
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}	
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getExtraFilename() {
		return extraFilename;
	}
	public void setExtraFilename(String extraFilename) {
		this.extraFilename = extraFilename;
	}
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	/**
	 * �˺������ʼ�ʱ�䣨long���ͣ�ת��Ϊ���ڸ�ʽ
	 * @return ���ظ�ʽ����������ַ���
	 */
	public String getTime() {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
		return format.format(c.getTime());
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public boolean equals(Object arg0) {
		Mail m = (Mail)arg0;
		if(this.time==m.time) return true;
		else return false;
	}
	@Override
	public String toString() {
		
		return "From:"+from+"\nTo:"+to+"\nSubject:"+subject+"\nTime:"+time+"\nContent:"+content+"\n";
	}

}
