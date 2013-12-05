package action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.*;
import java.util.*;

import sun.misc.BASE64Decoder;

import data.Mail;

/**
 * ���ฺ���ʼ����ĵĽ���
 * @author spacelis
 *
 */
public class MailDecoder {
	private BASE64Decoder decoder = new BASE64Decoder();
	
	/**
	 * ���뷢����
	 * @param original ԭ����
	 * @return �����˵�ַ
	 */
	private String findFrom(String original){
		String s = original.substring(original.indexOf("From:")+5);		
		s = s.substring(0,s.indexOf("\n"));
		if(s.indexOf("<")==-1||s.indexOf(">")==-1){
			return s;
		}else{
			return s.substring(s.indexOf("<")+1, s.indexOf(">"));
		}
	}
	
	/**
	 * �����ռ���
	 * @param original ԭ����
	 * @return �ռ��˵�ַ
	 */
	private String findTo(String original){
		String s = original.substring(original.indexOf("To:")+3);		
		s = s.substring(0,s.indexOf("\n"));
		if(s.indexOf("<")==-1||s.indexOf(">")==-1){
			return s;
		}else{
			return s.substring(s.indexOf("<")+1, s.indexOf(">"));
		}
	}
	
	/**
	 * ���볭����
	 * @param original ԭ����
	 * @return �����˵�ַ
	 */
	private String findCc(String original){
		if(original.indexOf("Cc:")==-1) return "";
		String s = original.substring(original.indexOf("Cc:")+3);		
		s = s.substring(0,s.indexOf("\n"));
		if(s.indexOf("<")==-1||s.indexOf(">")==-1){
			return s;
		}else{
			return s.substring(s.indexOf("<")+1, s.indexOf(">"));
		}
	}
	
	/**
	 * ��������
	 * @param original ԭ����
	 * @return ����
	 */
	private String findSubject(String original){
		if(original.indexOf("Subject:")==-1) return "";
		String s = original.substring(original.indexOf("Subject:")+8);	
		s = s.substring(0,s.indexOf("\n"));
		if(s.indexOf("?B?")!=-1){
			s = s.substring(s.indexOf("?B?")+3);
			s = s.substring(0,s.indexOf("?="));
			try {
				s = new String(decoder.decodeBuffer(s));
			} catch (IOException e1) {}
		}
		return s;
	}
	
	/**
	 * ��������
	 * @param original ԭ����
	 * @return ����
	 */
	private String findContent(String original){
		boolean isBase64 = true;
		String plain = "";
		String html = "";
		if(original.indexOf("Content-Type: text/plain")!=-1){
			plain = original.substring(original.indexOf("Content-Type: text/plain")+24);
			String code = plain.substring(0,plain.indexOf("\n\n"));
			if(code.indexOf("Content-Transfer-Encoding: 7bit")!=-1) isBase64 = false;
			plain = plain.substring(plain.indexOf("\n\n")+2);
			if(plain.indexOf("\n\n")!=-1) plain = plain.substring(0,plain.indexOf("\n\n"));
			
			if(isBase64){
				try {
					plain = new String(decoder.decodeBuffer(plain));
				} catch (IOException e1) {}
			}
		}
		if(original.indexOf("Content-Type: text/html")!=-1){
			html = original.substring(original.indexOf("Content-Type: text/html")+23);
			String code = html.substring(0,html.indexOf("\n\n"));
			if(code.indexOf("Content-Transfer-Encoding: 7bit")!=-1) isBase64 = false;
			else isBase64 = true;
			html = html.substring(html.indexOf("\n\n")+2);
			if(html.indexOf("\n\n")!=-1) html = html.substring(0,html.indexOf("\n\n"));
			
			if(isBase64){
				try {
					html = new String(decoder.decodeBuffer(html));
				} catch (IOException e1) {}
			}
			
		}
		String content = plain;  //+"\n"+html
		if("\n".equals(content)) content = original.substring(original.indexOf("\n\n")+2);
		return content;
	}
	
	/**
	 * ���뷢��ʱ��
	 * @param original ԭ����
	 * @return ʱ��ĺ�����
	 */
	private long findTime(String original){
		String s = original.substring(original.indexOf("Date:")+6);
		s = s.substring(s.indexOf(",")+2, s.indexOf(",")+28);
		s = s.replace("Jan", "01");
		s = s.replace("Feb", "02");
		s = s.replace("Mar", "03");
		s = s.replace("Apr", "04");
		s = s.replace("May", "05");
		s = s.replace("Jun", "06");
		s = s.replace("Jul", "07");
		s = s.replace("Aug", "08");
		s = s.replace("Sep", "09");
		s = s.replace("Oct", "10");
		s = s.replace("Nov", "11");
		s = s.replace("Dec", "12");
		SimpleDateFormat format = new SimpleDateFormat("d M y HH:mm:ss Z");
		try {			
			return format.parse(s).getTime();
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		return new Date().getTime();
	}
	
	/**
	 * ���븽��
	 * @param original ԭ����
	 * @return ��������
	 */
	private String findExtra(String original){
		String s = original.substring(original.indexOf("filename=\"")+10);
		String file = "";
		if(s.indexOf("?B?")!=-1){
			s = s.substring(s.indexOf("?B?")+3);
			file = s.substring(s.indexOf("\n\n")+2);
			file = file.substring(0,file.indexOf("\n\n"));
			s = s.substring(0,s.indexOf("?="));
			try {
				s = new String(decoder.decodeBuffer(s),"GBK");

			} catch (IOException e1) {}
			return s+"\n\n"+file;
		}else{
			file = s.substring(s.indexOf("\n\n")+2);
			file = file.substring(0,file.indexOf("\n\n"));
			s = s.substring(0,s.indexOf("\""));

			return s+"\n\n"+file;
		}
		
	}
	/**
	 * ���ʼ����Ľ�������Mailʵ��
	 * @param mail Mailʵ��
	 * @param original �ʼ�����
	 */
	public void packMail(Mail mail,String original){
		mail.setFrom(findFrom(original));
		mail.setTo(findTo(original));
		mail.setCc(findCc(original));
		mail.setSubject(findSubject(original));
		mail.setContent(findContent(original));
		mail.setOriginal(original);
		mail.setTime(findTime(original));
		
		if(original.indexOf("Content-Type: multipart/mixed")!=-1){ 
			    mail.setExtraFilename(findExtra(original));
			    mail.setStatus("? &");
		}else{
			mail.setStatus("?");
		}
	}
	
}
