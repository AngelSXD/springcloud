package com.swapping.springcloud.ms.test.tests;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.*;
import java.security.Key;
import java.security.SecureRandom;

public class POIDealxls {


    private Key key;

    /**
     * 根据参数生成KEY
     *
     * @param strKey
     */
    public void setKey(String strKey) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("DES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strKey.getBytes());
            _generator.init(secureRandom);
            this.key = _generator.generateKey();
            _generator = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       POIDealxls poiDealxls = new POIDealxls();
       poiDealxls.setKey("!@#$%^ldsf)(*&^%");
       poiDealxls.dealXls();
    }


    public  void  dealXls(){

        File file1 = new File("E:\\xls/账号迁移.xls");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file1);

            HSSFWorkbook workbook1 = new HSSFWorkbook(fileInputStream);
            Sheet sheet = workbook1.getSheet("设备");

            if (sheet != null){
                Row row = null;
                int lastRowNum = sheet.getLastRowNum();
                System.out.println("最后一行行号："+lastRowNum);
                for (int i = 146; i <= lastRowNum; i++) {
                    row = sheet.getRow(i);
                    if (row == null){
                        continue;
                    }else {
                        System.out.println("处理行："+i);
                        Cell cell = row.getCell(2);
                        if (cell != null){
                            String encryption = cell.getStringCellValue();
                            System.out.println("加密密码："+encryption);
                            if (StringUtils.isNotBlank(encryption)){
                                String decrypt = getDesString(encryption);
                                System.out.println("解密密码："+decrypt);
                                Cell cell1 = row.createCell(3);
                                cell1.setCellValue(decrypt);
                            }
                        }


                    }

                }
            }



            FileOutputStream outPutStream = new FileOutputStream(file1);
            workbook1.write(outPutStream);
            outPutStream.close();
            workbook1.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }


    /**
     * 解密 以String密文输入,String明文输出
     *
     * @param strMi
     * @return
     */
    public String getDesString(String strMi) {
        BASE64Decoder base64De = new BASE64Decoder();
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            byteMi = base64De.decodeBuffer(strMi);
            byteMing = this.getDesCode(byteMi);
            if(byteMing != null)strMing = new String(byteMing, "UTF8");
            else strMing = strMi;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            base64De = null;
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }


    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     *
     * @param byteD
     * @return
     */
    private byte[] getDesCode(byte[] byteD) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byteFina = cipher.doFinal(byteD);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }



}
