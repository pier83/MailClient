package action;

import java.io.*;

import sun.misc.*;
import data.Mail;

/**
 * �ʼ�������
 * @author spacelis
 *
 */
public class MailEncoder {
	CharacterEncoder encoder = null;
	String prefix = "";
	String suffix = "";
	String transEncoding = "";
	String contEncoding = "";
	String baseBoundary="----=_NextPart_49D9609A_08E73CD8_";
	String boundaryfix = "";
	
	/**
	 * ���ʼ�ת��ΪMIME��ʽ����
	 * @param mail �ʼ�����
	 * @param type ���뷽ʽ
	 * @return ����MIME��ʽ����
	 */
	public String transformMIME(Mail mail,int type){
		if(type == 1){//����ΪBase64��ʽ
			encoder = new BASE64Encoder();
			prefix = "=?gbk?B?";
			suffix = "?=";
			transEncoding = "Content-Transfer-Encoding: 8Bit";
			contEncoding = "Content-Transfer-Encoding: base64";
		}
		//��װ�ʼ�ͷ
		StringBuffer MIMEContent = new StringBuffer("");
		MIMEContent.append("From: <"+ mail.getFrom() +">\n"+
						   "To: <"+ mail.getTo() +">\n");
		if(!"".equals(mail.getCc())) MIMEContent.append("Cc: <"+mail.getCc()+">\n");
		MIMEContent.append("Subject:"+ getPreAndSuf(mail.getSubject())+"\n");
		
		if("".equals(mail.getExtraFilename())){//û�и���
			MIMEContent.append(
					 "Mime-Version: 1.0\n"
                    +"Content-Type: multipart/alternative;\n"
                    +"\tboundary=\"----=_spacelis_alternateive\"\n"
                    +"Content-Transfer-Encoding: 8Bit\n"
                    +"\n"
                    +"------=_spacelis_alternateive\n"
                    +"Content-Type: text/plain;\n" 
                    +"\tcharset=\"gbk\"\n"                     
                    +"Content-Transfer-Encoding: base64\n"
                    +"\n"
                    + encoder.encode(mail.getContent().getBytes())+"\n"
                    +"\n"
                    +"------=_spacelis_alternateive"
                    );
		}else{//�и���
			MIMEContent.append(
					"Mime-Version: 1.0\n"
					+"Content-Type: multipart/mixed;\n"
					+"\tboundary=\"----=_spacelis_mixed\"\n"
					+"Content-Transfer-Encoding: 8Bit\n"
					+"\n"
					+"------=_spacelis_mixed\n"
					+"Content-Type: multipart/alternative;\n"
					+"\tboundary=\"----=_spacelis_alternative\";\n"
					+"\n"
					+"------=_spacelis_alternative\n"
					+"Content-Type: text/plain;\n"
					+"\tcharset=\"gbk\"\n"
					+"Content-Transfer-Encoding: base64\n"
					+"\n"
					+encoder.encode(mail.getContent().getBytes())+"\n"
					+"\n"
					+"------=_spacelis_alternative\n"
					+"\n"
					+"------=_spacelis_mixed\n"
					+getExtra(mail.getExtraFilename())+"\n"
					+"\n"
					+"------=_spacelis_mixed--"
					);				
		}
			
		return MIMEContent.toString();
	}

	/**
	 * ������ת��Ϊ���ı�����
	 * @param plainContent ����
	 * @return ���ظ�ʽ����ı��Ĳ���
	 */
	private String getPlainContent(String plainContent){
		StringBuffer content = new StringBuffer("");
		content.append("Content-Type: text/plain;\n"+
					   "	charset=\"gbk\"\n" + 
					    contEncoding);
		content.append("\n\n");
		content.append(encoder.encode(plainContent.getBytes()));
		return content.toString();
	}
	
	/**
	 * ��HTML��Ϣת��ΪHtml����
	 * @param htmlContent HTML��Ϣ��
	 * @return ���ظ�ʽ����ı��Ĳ���
	 */
	private String getHtmlContent(String htmlContent){
		return "";
	}
	
	/**
	 * ���ļ�ת��ΪMIME��ʽ����
	 * @param extraFilename �ļ���
	 * @return ���ظ�ʽ����ı��Ĳ���
	 */
	private String getExtra(String extraFilename){		
		File extraFile = new File(extraFilename);
		
		if(!extraFile.exists()){
			return "";
		}
		String name = extraFilename.substring(extraFilename.lastIndexOf("\\")+1);//�ļ���
		String extName = name.substring(name.lastIndexOf(".")+1);//��չ��
		StringBuffer declaration = new StringBuffer();//������������
		StringBuffer content = new StringBuffer();//��������

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(extraFile);			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		byte[] b = new byte[1024];
		try {
			while(fis.read(b)!=-1){
				content.append(encoder.encode(b));
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//��������
		declaration.append("Content-Type: ");
		if("rar".equals(extName)){//rar
			declaration.append("application/x-rar-compressed;\n");
		}else if("txt".equals(extName)||"exe".equals(extName)){//txt,exe
			declaration.append("application/octet-stream;\n");
		}else if("gif".equals(extName)||"jpeg".equals(extName)){//gif,jpeg
			declaration.append("image/"+extName+";\n");
		}else if("mp3".equals(extName)){
			declaration.append("audio/x-mpeg;\n");
		}else if("ppt".equals(extName)){
			declaration.append("application/vnd.ms-powerpoint;\n");
		}else if("rmvb".equals(extName)||"rm".equals(extName)){
			declaration.append("audio/x-pn-realaudio;\n");
		}else if("wav".equals(extName)){
			declaration.append("audio/x-wav;\n");
		}else if("doc".equals(extName)){
			declaration.append("application/msword;\n");
		}
		declaration.append("\tcharset=\"gbk\";\n");
		declaration.append("\tname=\""+getPreAndSuf(name)+"\"\n");
		declaration.append("Content-Disposition: attachment; filename=\""+
				getPreAndSuf(name)+"\"\n");
		declaration.append("Content-Transfer-Encoding: base64");
		
		return declaration+"\n\n"+content.toString();		
	}

	/**
	 * ת������
	 * @param s Ҫת���ı���
	 * @return ���ر�������ַ���
	 */
	private String getPreAndSuf(String s){
		return prefix+encoder.encode(s.getBytes())+suffix;
	}

}
