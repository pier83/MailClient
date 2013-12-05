package action;
import dialog.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

import sun.misc.BASE64Encoder;
import data.*;

/**
 * �ʼ�������
 * @author spacelis
 *
 */
public class MailAction {
	
	/**
	 * �ʼ����ͷ���
	 * @param user �û���Ϣ
	 * @param mail �ʼ�ʵ��
	 * @return �����ʼ�����״̬
	 * @throws IOException
	 */
	public static String send(User user,Mail mail) throws IOException{
	
		String from = mail.getFrom();
		String to = mail.getTo();
		String cc = mail.getCc();
		String bcc = mail.getBcc();
		
		String mailContent = new MailEncoder().transformMIME(mail,1);
		
		BASE64Encoder encoder = new BASE64Encoder();//BASE64ת����		
		String encodedUser = encoder.encode(user.getUsername().getBytes());
		String encodedPswd = encoder.encode(user.getPassword().getBytes());		
		String hostName = InetAddress.getLocalHost().getHostName();
		
		Socket s = new Socket(user.getSmtpServer(),25);
		
		BufferedReader inFromServer = new BufferedReader(//������Ӧ����
				new InputStreamReader(s.getInputStream()));		
		PrintWriter outToServer = new PrintWriter(//�ͻ��˷�����
				s.getOutputStream(),true);
		
		String response = inFromServer.readLine();		
		System.out.println(response);
		if(response.indexOf("220")==-1) return "����ʧ��";
					
		response = exchange("EHLO "+hostName, inFromServer, outToServer);
		System.out.println(response);
		if(response.indexOf("250")==-1) return "�޷����ӷ�����";
		
		response = exchange("AUTH LOGIN", inFromServer, outToServer);
		while(response.indexOf("250")!=-1){
			System.out.println(response);
			response = inFromServer.readLine();
		}
		System.out.println(response);
		if(response.indexOf("334")==-1) return "�޷���֤";			
			
		response = exchange(encodedUser, inFromServer, outToServer);
		System.out.println("3.MailServer:"+response+"�û���");   //Password:
		if(response.indexOf("334")==-1) return "�û����Ƿ�";
		
		response = exchange(encodedPswd, inFromServer, outToServer);
		System.out.println("4.MailServer:"+response+"����");
		if(response.indexOf("235")==-1) return "�������";
		
		response = exchange("MAIL FROM:<"+from+"> BODY=8BITMIME", inFromServer, outToServer);
		System.out.println("5.MailServer:"+response);
		if(response.indexOf("250")==-1) return "�����˷Ƿ�";

		
		System.out.println("6.Client:"+"RCPT TO:"+to);	
		response = exchange("RCPT TO:<"+to+">", inFromServer, outToServer);
		System.out.println("6.MailServer:"+response);
		if(response.indexOf("250")==-1) return "�ռ��˷Ƿ�";
		
		if(!"".equals(cc)){
			response = exchange("RCPT TO:<"+cc+">", inFromServer, outToServer);
			System.out.println("6.MailServer:"+response);
			if(response.indexOf("250")==-1) return "�����˷Ƿ�";
		}
		if(!"".equals(bcc)){
			response = exchange("RCPT TO:<"+bcc+">", inFromServer, outToServer);
			System.out.println("6.MailServer:"+response);
			if(response.indexOf("250")==-1) return "�����˷Ƿ�";
		}
		
		response = exchange("DATA", inFromServer, outToServer);	
		System.out.println("7.MailServer:"+response);
		if(response.indexOf("354")==-1) return "�޷������ʼ�";

		outToServer.println(mailContent);		
		response = exchange(".", inFromServer, outToServer);
		System.out.println("MailServer:"+response);
		if(response.indexOf("250")==-1) return "����ʧ��";
		System.out.println("8.MailServer:"+response);
		
		response = exchange("QUIT", inFromServer, outToServer);
		System.out.println("8.MailServer:"+inFromServer.readLine());
		s.close();
		return "���ͳɹ�";
	}
	
	/**
	 * �ӷ����������ʼ������浽�ļ�����ɾ���������ϵ����ݡ�
	 * @param user �û���Ϣ
	 * @param mailList �����ʼ��б�
	 * @return ��������״̬
	 * @throws IOException
	 */
	public static String receive(User user,List<Mail> mailList)throws IOException{
		int mCount = 0;
		String response = "";
		
		Socket s = new Socket(user.getPopServer(),110);
		
		BufferedReader inFromServer = new BufferedReader(
				new InputStreamReader(s.getInputStream()));
		PrintWriter outToServer = new PrintWriter(
				new OutputStreamWriter(s.getOutputStream()));
		response = inFromServer.readLine();
		System.out.println(response);
		if(response.indexOf("+OK")==-1) return "�޷����ӷ�����";
		
		response = exchange("user "+user.getUsername(), inFromServer, outToServer);
		System.out.println(response);
		if(response.indexOf("+OK")==-1) return "�û���������";
		
		
		response = exchange("pass "+user.getPassword(), inFromServer, outToServer);
		System.out.println(response);
		if(response.indexOf("+OK")==-1) return "�������";
		
		
		response = exchange("list", inFromServer, outToServer);
		System.out.println(response);
		if(response.indexOf("+OK")==-1) return "�޷������ʼ�";		
		for(mCount=0;!".".equals(response=inFromServer.readLine());mCount++){
			System.out.println(response);
		}
		MailDecoder mDecoder = new MailDecoder();
		List<Mail> keptList = new ArrayList<Mail>();
		
		regain(keptList, "user/"+user.getUsername()+"/recvBox.dat");
		int downCount = 0;
		for(int i=1;i<=mCount;i++){
			StringBuffer original = new StringBuffer();
			response = exchange("retr "+i, inFromServer, outToServer);
			if(response.indexOf("+OK")==-1) continue;
			response = inFromServer.readLine();
			while(!".".equals(response)){
				original.append(response+"\n");
				response = inFromServer.readLine();
			}
			Mail aMail = new Mail();
			
			MailClient.stateTextArea.setText("����������"+mCount+
					"���ʼ���\n�������ص�"+i+"���ʼ�...");		
			
			mDecoder.packMail(aMail, original.toString());
			Iterator<Mail> it = keptList.iterator();
			int j=0;
			for(;it.hasNext();j++){
				if(aMail.equals(it.next())) break;
			}
			if(j==keptList.size()){//�ļ����޸ü�¼
				save(aMail,"user/"+user.getUsername()+"/recvBox.dat");
				mailList.add(aMail);
				downCount++;
			}
			
		}
		response = exchange("quit", inFromServer, outToServer);
		System.out.println(response);
		s.close();
		
		return "�ɹ�����"+downCount+"�����ʼ�";
	}
	
	/**
	 * �������ʼ����浽�ļ�
	 * @param mail �ʼ�ʵ��
	 * @param filename �ļ���
	 * @throws IOException
	 */
	public static void save(Mail mail,String filename) throws IOException{
		if(mail==null) return;
		File mfile = new File(filename);
		if(!mfile.exists()){
			mfile.createNewFile();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(mfile));
			oos.writeObject(mail);
			oos.flush();
			oos.close();
		}else{
			ObjectWriter oos = new ObjectWriter(new FileOutputStream(mfile,true));
			oos.writeObject(mail);
			oos.flush();
			oos.close();
		}		
	}
	
	/**
	 * �����ʼ�������Ӧ�ļ�
	 * @param mailList �ʼ�����
	 * @param filename �ļ���
	 * @throws IOException
	 */
	public static void save(List<Mail> mailList,String filename) throws IOException{
		if(!(mailList.size()>0)) return ;
		File mfile = new File(filename);
		if(!mfile.exists()) mfile.createNewFile();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(mfile));
		for(Iterator<Mail> it=mailList.iterator();it.hasNext();)
			oos.writeObject(it.next());
		oos.flush();
		oos.close();
	}
	
	/**
	 * ���ļ���ѯ�ʼ��б�
	 * @param mailList �ʼ��б�
	 * @param filename �ļ���
	 * @return ���ز�ѯ״̬
	 * @throws IOException
	 */
	public static String regain(List<Mail> mailList,String filename) throws IOException{
		File mfile = new File(filename);	
		if(!mfile.exists()) return "�޼�¼";

		Mail aMail = null;
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(mfile));		
		try{
			while((aMail=(Mail)ois.readObject())!=null){
				mailList.add(0, aMail);
			}
		}catch(EOFException e){
			System.out.println("�ļ���ȡ���");
		} catch (ClassNotFoundException e) {
			System.out.println("��ʽ����");
			e.printStackTrace();
		}
		ois.close();
		return "��ѯ���";
	}
	

	/**
	 * �������������Ϣ
	 * @param toServer ���͸�����������Ϣ��
	 * @param in ������
	 * @param out �����
	 * @return ���ط�������Ӧ��Ϣ
	 */
	public static String exchange(String toServer,BufferedReader in,PrintWriter out){
		out.println(toServer);
		out.flush();
		try {
			return in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
/**
 * ObjectOutputStream�����࣬��д��writeStreamHeader()����
 * @author spacelis
 *
 */
class ObjectWriter extends ObjectOutputStream{
	public ObjectWriter(OutputStream out) throws IOException {
		super(out);
	}
	/**
	 * ��׷�ӷ�ʽд�������Ҫ����дStreamHeader������д�˷���
	 */
	@Override
	protected void writeStreamHeader() throws IOException {
		super.reset();
	}		
}

