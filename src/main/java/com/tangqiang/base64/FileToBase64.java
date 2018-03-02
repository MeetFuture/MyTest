package com.tangqiang.base64;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * TODO
 *
 * @author Tom
 * @version 1.0 2018-02-13 0013 Tom create
 * @date 2018-02-13 0013
 * @copyright Copyright © 2018 Grgbanking All rights reserved.
 */
public class FileToBase64 {

    public static void main(String[] args) {
        FileToBase64 th = new FileToBase64();
        th.execute();
    }

    private void execute() {

        try {
            String fileName = "D:\\WorkspaceMine\\processing\\20180213132350.png";
            File file = new File(fileName);
            byte[] bytes = FileUtils.readFileToByteArray(file);

            String base64 = Base64.encodeBase64String(bytes);
            System.out.println("FileToBase64.execute :" + base64);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
