// package liamcole; //(because why cant java just work right)

import java.util.*;
import java.nio.ByteBuffer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
// import java.nio.charset.Charset;
// import java.rmi.UnexpectedException;
import java.io.UnsupportedEncodingException;

public class Header {
  // Packet obj that contains the given fields
  Packet p = new Packet(); // packet

  /**
   * 
   * @return Packet
   */
  public Packet retPacket() {
    return p;
  }

  public byte[] returnCTPByteArray() throws IOException {
    System.out.println("hey");
    byte[] retValue = null;
    int size = 0;

    // Scuffed little hack (FIXME: HACKY HARD CODE)
    ByteArrayOutputStream temp = new ByteArrayOutputStream();
    temp.write(this.getType() + this.getTR() + this.getWindow()); // 1 byte
    temp.write(this.getSeqnum()); // 2byte
    temp.write(p.length); // 2 byte
    temp.write(p.timestamp); // 4 byte
    temp.write(p.crc1); // 4 byte
    temp.write(p.payload); // 4 byte
    if (p.crc2 != null) {
      temp.write(p.crc2);
    }

    retValue = temp.toByteArray();
    System.out.println("///debug1: " + retValue.length);

    return retValue;
  }

  // 32 bits == 4 bytes & 4096 bits = 512 bytes

  // (4 bytes) Type|TR|Window|Seqnum|Length
  // (4 bytes) Timestamp
  // (4 bytes) CRC1
  // (up to 512 bytes) Payload
  // (4 bytes) CRC2

  // Constructor:
  public Header() {
    // NULL
  }

  // ********************************************************
  /*
   * Type Field Setter&Getter: (TODO: DONE)
   */
  public void setType(int val) throws Exception {
    byte temp = (byte) (val >> 6);
    if (temp != 0) {
      p.type = temp;
    } else {
      throw new Exception("type invalid"); // fix later
    }
  }

  public byte getType() {
    return p.type;
  }

  // ********************************************************
  /*
   * TR Field Setter&Getter: (TODO: DONE)
   */
  public void setTR(int val) {
    p.tr = (byte) (val >>> 5 & 1);
  }

  public byte getTR() {
    return p.tr;
  }

  // ********************************************************
  /*
   * Window Field Setter&Getter: (TODO: DONE)
   */
  public void setWindow(int val) {
    p.window = (byte) (val & 31);
  }

  public byte getWindow() {
    return p.window;
  }

  // ********************************************************
  /*
   * TSeqnum Field Setter&Getter: (TODO: DONE)
   */
  public byte getSeqnum() {
    return p.seqnum;
  }

  public void setSeqnum(int seq) {
    p.seqnum = (byte) seq;
  }

  // ********************************************************
  /*
   * Length Field Setter&Getter: (TODO: DONE)
   */
  public void setLength(int len) {
    p.length[0] = (byte) (len >>> 8);
    p.length[1] = (byte) (len);
  }

  public short getLength() {
    short convertedLength = ByteBuffer.wrap(p.length).getShort();
    return convertedLength;
  }

  // ********************************************************
  /*
   * TimeStamp Field Setter&Getter: (TODO: DONE)
   */
  public void setTimestamp(int val) {
    p.timestamp[0] = (byte) (val >> 24 & 0xFF);
    p.timestamp[1] = (byte) (val >> 16 & 0xFF);
    p.timestamp[2] = (byte) (val >> 8 & 0xFF);
    p.timestamp[3] = (byte) (val & 255 & 0xFF);
  }

  public int getTimestamp() {
    int convertedTimestamp = ByteBuffer.wrap(p.timestamp).getInt();
    return convertedTimestamp;
  }

  // ********************************************************
  /*
   * CRC1 Field Setter&Getter: (TODO: DONE)
   */
  public void setCRC1(int crc) {
    p.crc1[0] = (byte) (crc >> 24 & 0xFF);
    p.crc1[1] = (byte) (crc >> 16 & 0xFF);
    p.crc1[2] = (byte) (crc >> 8 & 0xFF);
    p.crc1[3] = (byte) (crc & 255 & 0xFF);
  }

  public int getCRC1() {
    int convertedCRC1 = ByteBuffer.wrap(p.crc1).getInt();
    return convertedCRC1;
  }

  // ********************************************************
  /*
   * CRC2 Field Setter&Getter: (TODO: DONE)
   */
  public void setCRC2(int crc) {
    p.crc2 = new byte[4];
    p.crc2[0] = (byte) (crc >> 24 & 0xFF);
    p.crc2[1] = (byte) (crc >> 16 & 0xFF);
    p.crc2[2] = (byte) (crc >> 8 & 0xFF);
    p.crc2[3] = (byte) (crc & 255 & 0xFF);
  }

  public int getCRC2() {
    int convertedCRC2 = ByteBuffer.wrap(p.crc2).getInt();
    return convertedCRC2;
  }

  // ********************************************************
  /*
   * Payload Field Setter&Getter: (TODO: DONE)
   */
  public void setPayload(String pay) throws UnsupportedEncodingException {
    if (this.getTR() == 0) {
      p.payload = pay.getBytes();
    } else {
      p.payload = null;
    }
  }

  public String getPayload() {
    String str = new String(p.payload);
    return str;
  }

}
