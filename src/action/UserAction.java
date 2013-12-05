package action;

import java.io.*;
import java.util.*;

import data.Mail;
import data.User;

/**
 * �û���������
 * @author spacelis
 *
 */
public class UserAction {
	
	/**
	 * �����û�������Ӧ�ļ������Ƿ�ʽ��
	 * @param mailList �û�����
	 * @throws IOException
	 */
	public static void save(List<User> userList) throws IOException{
		File mfile = new File("user.dat");
		if(!mfile.exists()) mfile.createNewFile();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(mfile));
		for(Iterator<User> it=userList.iterator();it.hasNext();)
			oos.writeObject(it.next());
		oos.flush();
		oos.close();
	}
	
	public static String regin(List<User> userList) throws IOException{
		File mfile = new File("user.dat");	
		if(!mfile.exists()) return "�޼�¼";
		
		User user = null;
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(mfile));		
		try{
			while((user=(User)ois.readObject())!=null){
				userList.add(user);
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
	
}
