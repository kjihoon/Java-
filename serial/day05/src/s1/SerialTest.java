package s1;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class SerialTest implements SerialPortEventListener {

	private BufferedInputStream bin;
	private InputStream in;
	private OutputStream out;
	private SerialPort serialPort;
	private CommPortIdentifier portIdentifier;
	private CommPort commPort;
	

	public SerialTest() {

	}

	public SerialTest(String portName) throws NoSuchPortException {

		portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		System.out.println("CONNECTED");
		
		try {
			connectSerial();
		    (new Thread(new SerialWriter())).start();
			System.out.println("Connect Ok");
		} catch (Exception e) {
			System.out.println("Connect Fail");
			e.printStackTrace();
		}
	}
	
	
	public void sendMsg(String msg) {
		//w28 00000000 0000000000000000 
		SerialWriter sw = new SerialWriter(msg);
		new Thread(sw).start();
		
	}
	
	
	 private class SerialWriter implements Runnable {
		  String data;

		  public SerialWriter() {
		   this.data = ":G11A9\r";    //CAN�����͹����غ��ϰڴٴ½�ȣ
		  }

		  public SerialWriter(String serialData) {
		
		   this.data = sendDataFormat(serialData);
		   System.out.println(this.data);
			//:w28 00000000 0000000000000000 53 \r �� ���������
		  }

		  public String sendDataFormat(String serialData) {
		          serialData = serialData.toUpperCase();
		          char c[] = serialData.toCharArray();
		          int cdata = 0;
		          for (char cc : c) {
		             cdata += cc;
		          }
		          cdata = (cdata & 0xFF);

		          String returnData = ":";
		          returnData += serialData + Integer.toHexString(cdata).toUpperCase();
		          returnData += "\r";
		          return returnData;
		       }

		  public void run() {
		   try {

		    byte[] inputData = data.getBytes();

		    out.write(inputData);
		   } catch (IOException e) {
		    e.printStackTrace();
		   }
		  }

		 }

	
	
	 public void connectSerial() throws Exception {

		  if (portIdentifier.isCurrentlyOwned()) {
		   System.out.println("Error: Port is currently in use");
		  } else {
		   commPort = portIdentifier.open(this.getClass().getName(), 5000);
		   if (commPort instanceof SerialPort) {
		    serialPort = (SerialPort) commPort;
		    serialPort.addEventListener(this);
		    serialPort.notifyOnDataAvailable(true);
		    serialPort.setSerialPortParams(921600, // ��żӵ�
		      SerialPort.DATABITS_8, // ������ ��Ʈ
		      SerialPort.STOPBITS_1, // stop ��Ʈ
		      SerialPort.PARITY_NONE); // �и�Ƽ
		    in = serialPort.getInputStream();
		    bin = new BufferedInputStream(in);
		    out = serialPort.getOutputStream();
		   } else {
		    System.out.println("Error: Only serial ports are handled by this example.");
		   }
		  }
		 }

	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SerialTest serialTest = null;

		try {
			serialTest = new SerialTest("COM7");
		    String msg = "W2800000000A000000000000000";
			serialTest.sendMsg(msg);

		} catch (NoSuchPortException e) {
			// TODO Auto-generated catch block
			System.out.println("Connect Fail");
			e.printStackTrace();
		}

	}


	
	
	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			byte[] readBuffer = new byte[128];

			try {

				while (bin.available() > 0) {
					int numBytes = bin.read(readBuffer);
				}

				String ss = new String(readBuffer);
				System.out.println("Receive Low Data:" + ss + "||");

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	
	

}
