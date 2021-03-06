/*
 * ReadMail.java
 *
 * Created on __DATE__, __TIME__
 */

package dialog;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import sun.misc.BASE64Decoder;

import data.Mail;
import data.User;

/**
 * 邮件的详细信息窗体
 * @author  spacelis
 */
public class ReadMail extends javax.swing.JDialog {
	Mail mail = null;
	User user = null;
	List<Mail> readList = null;
	static int index = 0;

	/** Creates new form ReadMail */
	public ReadMail(java.awt.Frame parent, boolean modal) {
		super(parent, modal);

	}

	public ReadMail(User user, List<Mail> readList, int index) {
		this(new java.awt.Frame(), true);
		this.user = user;
		this.index = index;
		this.readList = readList;

		initComponents();
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				dispose();
			}
		});

		jButton3ActionPerformed(null);//读邮件内容状态
		setLocation(220, 170);
		this.setVisible(true);
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jToolBar1 = new javax.swing.JToolBar();
		jButton6 = new javax.swing.JButton();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jButton5 = new javax.swing.JButton();
		jScrollPane2 = new javax.swing.JScrollPane();
		contentTextArea = new javax.swing.JTextArea();
		jScrollPane1 = new javax.swing.JScrollPane();
		headerTextArea = new javax.swing.JTextArea();
		extraToolBar = new javax.swing.JToolBar();
		fileLabel = new javax.swing.JLabel();
		jButton7 = new javax.swing.JButton();

		setTitle("\u67e5\u770b\u90ae\u4ef6");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);

		jToolBar1.setRollover(true);

		jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/icons/Reply.png"))); // NOI18N
		jButton6.setFocusable(false);
		jButton6.setHorizontalTextPosition(0);
		jButton6.setText("\u56de\u590d");
		jButton6.setVerticalTextPosition(3);
		jButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton6ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton6);

		jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/icons/transmit.gif"))); // NOI18N
		jButton1.setFocusable(false);
		jButton1.setHorizontalTextPosition(0);
		jButton1.setMaximumSize(new java.awt.Dimension(55, 50));
		jButton1.setMinimumSize(new java.awt.Dimension(55, 50));
		jButton1.setText("\u8f6c\u53d1");
		jButton1.setVerticalTextPosition(3);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton1);

		jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/icons/original.gif"))); // NOI18N
		jButton2.setFocusable(false);
		jButton2.setHorizontalTextPosition(0);
		jButton2.setText("\u539f\u59cb\u62a5\u6587");
		jButton2.setVerticalTextPosition(3);
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton2);

		jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/icons/Content.gif"))); // NOI18N
		jButton3.setFocusable(false);
		jButton3.setHorizontalTextPosition(0);
		jButton3.setText("\u90ae\u4ef6\u5185\u5bb9");
		jButton3.setVerticalTextPosition(3);
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton3);

		jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/icons/last.JPG"))); // NOI18N
		jButton4.setFocusable(false);
		jButton4.setHorizontalTextPosition(0);
		jButton4.setText("\u4e0a\u4e00\u5c01");
		jButton4.setVerticalTextPosition(3);
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton4ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton4);

		jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/icons/next.JPG"))); // NOI18N
		jButton5.setFocusable(false);
		jButton5.setHorizontalTextPosition(0);
		jButton5.setText("\u4e0b\u4e00\u5c01");
		jButton5.setVerticalTextPosition(3);
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton5);

		contentTextArea.setColumns(20);
		contentTextArea.setEditable(false);
		contentTextArea.setRows(5);
		jScrollPane2.setViewportView(contentTextArea);

		jScrollPane1.setAutoscrolls(true);

		headerTextArea.setColumns(20);
		headerTextArea.setEditable(false);
		headerTextArea.setRows(3);
		headerTextArea.setAutoscrolls(false);
		headerTextArea.setBackground(new java.awt.Color(153, 204, 255));
		jScrollPane1.setViewportView(headerTextArea);

		extraToolBar.setRollover(true);

		fileLabel.setText("\u6709\u9644\u4ef6");
		extraToolBar.add(fileLabel);

		jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/icons/download.png"))); // NOI18N
		jButton7.setFocusable(false);
		jButton7.setHorizontalTextPosition(0);
		jButton7.setText("\u4e0b\u8f7d");
		jButton7.setVerticalTextPosition(3);
		jButton7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton7ActionPerformed(evt);
			}
		});
		extraToolBar.add(jButton7);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1,
				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 978,
				Short.MAX_VALUE).add(jScrollPane2,
				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 978,
				Short.MAX_VALUE).add(
				layout.createSequentialGroup().add(jToolBar1,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 452,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED).add(
								extraToolBar,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								510, Short.MAX_VALUE).addContainerGap()));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								layout
										.createSequentialGroup()
										.add(
												layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																extraToolBar,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																48,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(
																jToolBar1,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																52,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												jScrollPane1,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												78,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												jScrollPane2,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												481, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	/**
	 * 下载附件
	 */
	private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
		String s = mail.getExtraFilename();
		String filename = s.substring(0, s.indexOf("\n\n"));
		s = s.substring(s.indexOf("\n\n") + 2);

		File download = new File("user/"+user.getUsername()+"/"+filename);
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			FileOutputStream fw = new FileOutputStream(download);
			fw.write(decoder.decodeBuffer(s));
			fw.flush();
			fw.close();
			download.createNewFile();
			JOptionPane.showConfirmDialog(null, "邮件下载成功下载到"
					+ download.getAbsolutePath(), "成功", JOptionPane.OK_OPTION);
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(null, "邮件下载失败", "错误",
					JOptionPane.OK_OPTION);
			e.printStackTrace();
		}
	}

	/**
	 * 回复
	 */
	private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {

		if ("".equals(user.getUsername())) {
			JOptionPane.showConfirmDialog(null, "请先设置用户", "提示",
					JOptionPane.OK_OPTION);
			return;
		}
		mail.setSubject("Re:" + mail.getSubject());
		mail.setContent("\n\n\n" + mail.getContent());
		mail.setTo(mail.getFrom());
		mail.setCc("");
		mail.setBcc("");
		new WriteNewMail(user, mail);
	}

	/**
	 * 查看下一封
	 */
	private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
		if (index < readList.size() - 1)
			index++;
		jButton3ActionPerformed(null);
	}

	/**
	 * 查看上一封
	 * @param evt
	 */
	private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
		if (index > 0)
			index--;
		jButton3ActionPerformed(null);
	}

	/**
	 * 查看邮件内容
	 */
	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
		this.mail = readList.get(index);
		String header = "发件人:" + mail.getFrom() + "\n" + "主题:"
				+ mail.getSubject() + "\n";
		if (!"".equals(mail.getCc()))
			header += "抄送:" + mail.getCc() + "\n";
		header += "时间:" + mail.getTime();
		headerTextArea.setText(header);

		String content = mail.getContent();
		contentTextArea.setText(content);

		if ("".equals(mail.getExtraFilename()))
			extraToolBar.setVisible(false);
		else {
			String con = mail.getExtraFilename();
			fileLabel.setText("附件：" + con.substring(0, con.indexOf("\n\n")));
			extraToolBar.setVisible(true);
		}

	}

	/**
	 * 查看邮件报文
	 */
	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		contentTextArea.setText(mail.getOriginal());
	}

	/**
	 * 转发按钮响应
	 */
	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		if ("".equals(user.getUsername())) {
			JOptionPane.showConfirmDialog(null, "请先设置用户", "提示",
					JOptionPane.OK_OPTION);
			return;
		}
		mail.setFrom("");
		mail.setTo("");
		mail.setCc("");
		mail.setBcc("");
		new WriteNewMail(user, mail);
	}


	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JTextArea contentTextArea;
	private javax.swing.JToolBar extraToolBar;
	private javax.swing.JLabel fileLabel;
	private javax.swing.JTextArea headerTextArea;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JButton jButton6;
	private javax.swing.JButton jButton7;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JToolBar jToolBar1;
	// End of variables declaration//GEN-END:variables

}