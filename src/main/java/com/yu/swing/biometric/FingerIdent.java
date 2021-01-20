package com.yu.swing.biometric;

import com.zkteco.biometric.FingerprintSensorErrorCode;
import com.zkteco.biometric.FingerprintSensorEx;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class FingerIdent extends JFrame {
    private static JLabel zwtext;

    public FingerIdent(JLabel aFrame) {
        this.zwtext = aFrame;
        initialize();
    }
	public void initialize() {
        contentPane = new JPanel();

		contentPane.setLayout(null);
		btnOpen = new JButton("打开");
		contentPane.add(btnOpen);
		int nRsize = 20;
		btnOpen.setBounds(30, 130, 100, 30);

		btnEnroll = new JButton("指纹录入");
		contentPane.add(btnEnroll);
		btnEnroll.setBounds(30, 180, 100, 30);

		btnVerify = new JButton("指纹识别");
		contentPane.add(btnVerify);
		btnVerify.setBounds(30, 230, 100, 30);

		btnClose = new JButton("关闭");
		contentPane.add(btnClose);
		btnClose.setBounds(30, 270, 100, 30);

		btnImg = new JButton();
		btnImg.setBounds(167, 115, 256, 300);
		btnImg.setDefaultCapable(false);
		contentPane.add(btnImg);

		this.setSize(520, 580);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("指纹识别系统");
		this.setResizable(false);

		btnOpen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (0 != mhDevice) {
					textArea.setText("Please close device first!");
					return;
				}
				int ret = FingerprintSensorErrorCode.ZKFP_ERR_OK;
				cbRegTemp = 0;
				bRegister = false;
				bIdentify = false;
				iFid = 1;
				enroll_idx = 0;
				if (FingerprintSensorErrorCode.ZKFP_ERR_OK != FingerprintSensorEx.Init()) {
					textArea.setText("Init failed!");
					return;
				}
				ret = FingerprintSensorEx.GetDeviceCount();
				if (ret < 0) {
					textArea.setText("没有连接驱动!");
					FreeSensor();
					return;
				}
				if (0 == (mhDevice = FingerprintSensorEx.OpenDevice(0))) {
					textArea.setText("设备打开失败, 可能设备被占用ret = " + ret + "!");
					FreeSensor();
					return;
				}
				if (0 == (mhDB = FingerprintSensorEx.DBInit())) {
					textArea.setText("数据库初始化失败, ret = " + ret + "!");
					FreeSensor();
					return;
				}

				byte[] paramValue = new byte[4];
				int[] size = new int[1];

				size[0] = 4;
				FingerprintSensorEx.GetParameters(mhDevice, 1, paramValue, size);
				fpWidth = byteArrayToInt(paramValue);
				size[0] = 4;
				FingerprintSensorEx.GetParameters(mhDevice, 2, paramValue, size);
				fpHeight = byteArrayToInt(paramValue);
				// width = fingerprintSensor.getImageWidth();
				// height = fingerprintSensor.getImageHeight();
				imgbuf = new byte[fpWidth * fpHeight];
				btnImg.resize(fpWidth, fpHeight);
				mbStop = false;
				workThread = new WorkThread();
				workThread.start();// 绾跨▼鍚姩
				textArea.setText("打开成功!");
                System.out.println("进入窗体");
			}
		});

		btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FreeSensor();

				textArea.setText("关闭成功!");
			}
		});

		btnEnroll.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				if (0 == mhDevice) {
					textArea.setText("请先打开设备");
					return;
				}
				if (!bRegister) {
					enroll_idx = 0;
					bRegister = true;
					textArea.setText("请按下手指三次!");
				}
			}
		});

		btnVerify.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (0 == mhDevice) {
					textArea.setText("请先打开设备");
					return;
				}
				if (bRegister) {
					enroll_idx = 0;
					bRegister = false;
				}
				if (bIdentify) {
					bIdentify = false;
				}
			}
		});

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				FreeSensor();
			}
		});
		
		setContentPane(contentPane);
		
		textArea = new JLabel("");
		textArea.setFont(new Font("宋体", Font.PLAIN, 20));
		textArea.setHorizontalAlignment(SwingConstants.CENTER);
		textArea.setBounds(30, 10, 445, 95);
		contentPane.add(textArea);

	}

	JButton btnOpen = null;
	JButton btnEnroll = null;
	JButton btnVerify = null;
	JButton btnClose = null;
	JButton btnImg = null;
	
	private JPanel contentPane;

	// the width of fingerprint image
	int fpWidth = 0;
	// the height of fingerprint image
	int fpHeight = 0;
	// for verify test
	private byte[] lastRegTemp = new byte[2048];
	// the length of lastRegTemp
	private int cbRegTemp = 0;
	// pre-register template
	private byte[][] regtemparray = new byte[3][2048];
	// Register
	private boolean bRegister = false;
	// Identify
	private boolean bIdentify = true;
	// finger id
	private int iFid = 1;

	private int nFakeFunOn = 1;
	// must be 3
	static final int enroll_cnt = 3;
	// the index of pre-register function
	private int enroll_idx = 0;

	private byte[] imgbuf = null;
	private byte[] template = new byte[2048];
	private int[] templateLen = new int[1];

	private boolean mbStop = true;
	private long mhDevice = 0;
	private long mhDB = 0;
	private WorkThread workThread = null;
	private JLabel textArea;


	private void FreeSensor() {
		mbStop = true;
		try { // wait for thread stopping
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (0 != mhDB) {
			FingerprintSensorEx.DBFree(mhDB);
			mhDB = 0;
		}
		if (0 != mhDevice) {
			FingerprintSensorEx.CloseDevice(mhDevice);
			mhDevice = 0;
		}
		FingerprintSensorEx.Terminate();
	}

	public static void writeBitmap(byte[] imageBuf, int nWidth, int nHeight, String path) throws IOException {
		java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
		java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);

		int w = (((nWidth + 3) / 4) * 4);
		int bfType = 0x424d; // 浣嶅浘鏂囦欢绫诲瀷锛�0鈥�1瀛楄妭锛�
		int bfSize = 54 + 1024 + w * nHeight;// bmp鏂囦欢鐨勫ぇ灏忥紙2鈥�5瀛楄妭锛�
		int bfReserved1 = 0;// 浣嶅浘鏂囦欢淇濈暀瀛楋紝蹇呴』涓�0锛�6-7瀛楄妭锛�
		int bfReserved2 = 0;// 浣嶅浘鏂囦欢淇濈暀瀛楋紝蹇呴』涓�0锛�8-9瀛楄妭锛�
		int bfOffBits = 54 + 1024;// 鏂囦欢澶村紑濮嬪埌浣嶅浘瀹為檯鏁版嵁涔嬮棿鐨勫瓧鑺傜殑鍋忕Щ閲忥紙10-13瀛楄妭锛�

		dos.writeShort(bfType); // 杈撳叆浣嶅浘鏂囦欢绫诲瀷'BM'
		dos.write(changeByte(bfSize), 0, 4); // 杈撳叆浣嶅浘鏂囦欢澶у皬
		dos.write(changeByte(bfReserved1), 0, 2);// 杈撳叆浣嶅浘鏂囦欢淇濈暀瀛�
		dos.write(changeByte(bfReserved2), 0, 2);// 杈撳叆浣嶅浘鏂囦欢淇濈暀瀛�
		dos.write(changeByte(bfOffBits), 0, 4);// 杈撳叆浣嶅浘鏂囦欢鍋忕Щ閲�

		int biSize = 40;// 淇℃伅澶存墍闇�鐨勫瓧鑺傛暟锛�14-17瀛楄妭锛�
		int biWidth = nWidth;// 浣嶅浘鐨勫锛�18-21瀛楄妭锛�
		int biHeight = nHeight;// 浣嶅浘鐨勯珮锛�22-25瀛楄妭锛�
		int biPlanes = 1; // 鐩爣璁惧鐨勭骇鍒紝蹇呴』鏄�1锛�26-27瀛楄妭锛�
		int biBitcount = 8;// 姣忎釜鍍忕礌鎵�闇�鐨勪綅鏁帮紙28-29瀛楄妭锛夛紝蹇呴』鏄�1浣嶏紙鍙岃壊锛夈��4浣嶏紙16鑹诧級銆�8浣嶏紙256鑹诧級鎴栬��24浣嶏紙鐪熷僵鑹诧級涔嬩竴銆�
		int biCompression = 0;// 浣嶅浘鍘嬬缉绫诲瀷锛屽繀椤绘槸0锛堜笉鍘嬬缉锛夛紙30-33瀛楄妭锛夈��1锛圔I_RLEB鍘嬬缉绫诲瀷锛夋垨2锛圔I_RLE4鍘嬬缉绫诲瀷锛変箣涓�銆�
		int biSizeImage = w * nHeight;// 瀹為檯浣嶅浘鍥惧儚鐨勫ぇ灏忥紝鍗虫暣涓疄闄呯粯鍒剁殑鍥惧儚澶у皬锛�34-37瀛楄妭锛�
		int biXPelsPerMeter = 0;// 浣嶅浘姘村钩鍒嗚鲸鐜囷紝姣忕背鍍忕礌鏁帮紙38-41瀛楄妭锛夎繖涓暟鏄郴缁熼粯璁ゅ��
		int biYPelsPerMeter = 0;// 浣嶅浘鍨傜洿鍒嗚鲸鐜囷紝姣忕背鍍忕礌鏁帮紙42-45瀛楄妭锛夎繖涓暟鏄郴缁熼粯璁ゅ��
		int biClrUsed = 0;// 浣嶅浘瀹為檯浣跨敤鐨勯鑹茶〃涓殑棰滆壊鏁帮紙46-49瀛楄妭锛夛紝濡傛灉涓�0鐨勮瘽锛岃鏄庡叏閮ㄤ娇鐢ㄤ簡
		int biClrImportant = 0;// 浣嶅浘鏄剧ず杩囩▼涓噸瑕佺殑棰滆壊鏁�(50-53瀛楄妭)锛屽鏋滀负0鐨勮瘽锛岃鏄庡叏閮ㄩ噸瑕�

		dos.write(changeByte(biSize), 0, 4);// 杈撳叆淇℃伅澶存暟鎹殑鎬诲瓧鑺傛暟
		dos.write(changeByte(biWidth), 0, 4);// 杈撳叆浣嶅浘鐨勫
		dos.write(changeByte(biHeight), 0, 4);// 杈撳叆浣嶅浘鐨勯珮
		dos.write(changeByte(biPlanes), 0, 2);// 杈撳叆浣嶅浘鐨勭洰鏍囪澶囩骇鍒�
		dos.write(changeByte(biBitcount), 0, 2);// 杈撳叆姣忎釜鍍忕礌鍗犳嵁鐨勫瓧鑺傛暟
		dos.write(changeByte(biCompression), 0, 4);// 杈撳叆浣嶅浘鐨勫帇缂╃被鍨�
		dos.write(changeByte(biSizeImage), 0, 4);// 杈撳叆浣嶅浘鐨勫疄闄呭ぇ灏�
		dos.write(changeByte(biXPelsPerMeter), 0, 4);// 杈撳叆浣嶅浘鐨勬按骞冲垎杈ㄧ巼
		dos.write(changeByte(biYPelsPerMeter), 0, 4);// 杈撳叆浣嶅浘鐨勫瀭鐩村垎杈ㄧ巼
		dos.write(changeByte(biClrUsed), 0, 4);// 杈撳叆浣嶅浘浣跨敤鐨勬�婚鑹叉暟
		dos.write(changeByte(biClrImportant), 0, 4);// 杈撳叆浣嶅浘浣跨敤杩囩▼涓噸瑕佺殑棰滆壊鏁�

		for (int i = 0; i < 256; i++) {
			dos.writeByte(i);
			dos.writeByte(i);
			dos.writeByte(i);
			dos.writeByte(0);
		}

		byte[] filter = null;
		if (w > nWidth) {
			filter = new byte[w - nWidth];
		}

		for (int i = 0; i < nHeight; i++) {
			dos.write(imageBuf, (nHeight - 1 - i) * nWidth, nWidth);
			if (w > nWidth)
				dos.write(filter, 0, w - nWidth);
		}
		dos.flush();
		dos.close();
		fos.close();
	}

	public static byte[] changeByte(int data) {
		return intToByteArray(data);
	}

	public static byte[] intToByteArray(final int number) {
		byte[] abyte = new byte[4];
		abyte[0] = (byte) (0xff & number);
		abyte[1] = (byte) ((0xff00 & number) >> 8);
		abyte[2] = (byte) ((0xff0000 & number) >> 16);
		abyte[3] = (byte) ((0xff000000 & number) >> 24);
		return abyte;
	}

	public static int byteArrayToInt(byte[] bytes) {
		int number = bytes[0] & 0xFF;
		// "|="鎸変綅鎴栬祴鍊笺��
		number |= ((bytes[1] << 8) & 0xFF00);
		number |= ((bytes[2] << 16) & 0xFF0000);
		number |= ((bytes[3] << 24) & 0xFF000000);
		return number;
	}

	private class WorkThread extends Thread {
		@Override
		public void run() {
			super.run();
			int ret = 0;
			while (!mbStop) {
				templateLen[0] = 2048;
				if (0 == (ret = FingerprintSensorEx.AcquireFingerprint(mhDevice, imgbuf, template, templateLen))) {
					if (nFakeFunOn == 1) {
						byte[] paramValue = new byte[4];
						int[] size = new int[1];
						size[0] = 4;
						int nFakeStatus = 0;
						// GetFakeStatus
						ret = FingerprintSensorEx.GetParameters(mhDevice, 2004, paramValue, size);
						nFakeStatus = byteArrayToInt(paramValue);
						System.out.println("ret = " + ret + ",nFakeStatus=" + nFakeStatus);
						if (0 == ret && (byte) (nFakeStatus & 31) != 31) {
							textArea.setText("Is a fake-finer?");
							return;
						}
					}
					OnCatpureOK(imgbuf);
					OnExtractOK(template, templateLen[0]);
					String strBase64 = FingerprintSensorEx.BlobToBase64(template, templateLen[0]);
					// 当前指纹的特征码
					//System.out.println("strBase64=" + strBase64);
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

	private void OnCatpureOK(byte[] imgBuf) {
		try {
			writeBitmap(imgBuf, fpWidth, fpHeight, "fingerprint.bmp");
			btnImg.setIcon(new ImageIcon(ImageIO.read(new File("fingerprint.bmp"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void OnExtractOK(byte[] template, int len) {
		if (bRegister) {
			int[] fid = new int[1];
			int[] score = new int[1];
			int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
			if (ret == 0) {
				textArea.setText("指纹已经存在ID是 " + fid[0]);
				bRegister = false;
				enroll_idx = 0;
				return;
			}
			if (enroll_idx > 0 && FingerprintSensorEx.DBMatch(mhDB, regtemparray[enroll_idx - 1], template) <= 0) {
				textArea.setText("请按同一手指三次");
				return;
			}
			System.arraycopy(template, 0, regtemparray[enroll_idx], 0, 2048);
			enroll_idx++;
			if (enroll_idx == 3) {
				int[] _retLen = new int[1];
				_retLen[0] = 2048;
				byte[] regTemp = new byte[_retLen[0]];

				if (0 == (ret = FingerprintSensorEx.DBMerge(mhDB, regtemparray[0], regtemparray[1], regtemparray[2],
						regTemp, _retLen)) && 0 == (ret = FingerprintSensorEx.DBAdd(mhDB, iFid, regTemp))) {
					iFid++;
					cbRegTemp = _retLen[0];
					System.arraycopy(regTemp, 0, lastRegTemp, 0, cbRegTemp);
                    String strBase64 = FingerprintSensorEx.BlobToBase64(regTemp, cbRegTemp);
					// Base64 Template
					textArea.setText("录入成功");
					// 得到指纹特征码

					//System.out.println("指纹录入成功，指纹特征码是：" + strBase64);
					//修改父页面的值
                    zwtext.setText(strBase64);

                    FreeSensor();
                    textArea.setText("关闭成功!");

                    this.dispose();


				} else {
					textArea.setText("enroll fail, error code=" + ret);
				}
				bRegister = false;
			} else {
				textArea.setText("你还需要按你第一次按的手指 " + (3 - enroll_idx) + " 次");
			}
		} else {
			if (bIdentify) {
				int[] fid = new int[1];
				int[] score = new int[1];
				int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
				if (ret == 0) {
					textArea.setText("Identify succ, fid=" + fid[0] + ",score=" + score[0]);
				} else {
					textArea.setText("Identify fail, errcode=" + ret);
				}

			} else {
				if (cbRegTemp <= 0) {
					textArea.setText("Please register first!");
				} else {
					int ret = FingerprintSensorEx.DBMatch(mhDB, lastRegTemp, template);
					if (ret > 0) {
						textArea.setText("识别成功ID是" + ret);
					} else {
						textArea.setText("指纹不存在");
					}
				}
			}
		}
	}

	/*public static void main(String[] args) {
		FingerIdent frame = new FingerIdent();
		frame.setVisible(true);
	}*/
}
