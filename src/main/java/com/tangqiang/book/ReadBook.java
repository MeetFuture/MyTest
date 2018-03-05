package com.tangqiang.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;

public class ReadBook {
    //每行显示字符
    private int lineLen = 50;
    //总共字符
    private int totalLen = 500;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private File file = new File("E:\\books\\从零开始.txt");
    private long skipS = 0;
    private BufferedReader bufferedReader;

    public static void main(String[] args) {
        new ReadBook().run();
    }

    public void run() {
        try {
            long lastSkip = getReadSkip();
            logger.info("FileReadBooks Read File:" + file.getAbsolutePath() + "  Skip:" + lastSkip);
            bufferedReader = getBufferedReader();
            bufferedReader.skip(lastSkip);

            while (true) {
                String cmd = readCommand();
                if (cmd.startsWith("s") || cmd.startsWith("S")) {
                    dealSearch(cmd.substring(1));
                } else if (cmd.startsWith("b") || cmd.startsWith("B")) {
                    dealBack();
                    dealSearch(cmd.substring(1));
                }

                dealReadPrint();
            }
        } catch (Exception e) {
            logger.error("Read error !", e);
        }
    }

    private BufferedReader getBufferedReader() throws Exception {
        FileInputStream fin = new FileInputStream(file);
        InputStreamReader read = new InputStreamReader(fin, "UTF-8");
        return new BufferedReader(read);
    }

    private void dealBack() throws Exception {
        logger.info("dealBack ..........................................");
        bufferedReader = getBufferedReader();
        writeReadSkip(0);
    }

    private String readCommand() throws Exception {
        logger.info("Wait for command ..........................................");
        BufferedReader bufferedReaderSystemIn = new BufferedReader(new InputStreamReader(System.in));
        String cmd = bufferedReaderSystemIn.readLine();
        return cmd.trim();
    }

    private void dealReadPrint() throws Exception {
        StringBuilder sb = new StringBuilder();
        int iRead = 0;
        String lineTxt = null;
        while ((lineTxt = bufferedReader.readLine()) != null) {
            iRead += lineTxt.length() + 2;
            sb.append(lineTxt).append("*");
            if (iRead > totalLen) {
                break;
            }
        }
        long skip = getReadSkip() + iRead;
        writeReadSkip(skip);
        print(sb);
    }


    private void dealSearch(String cmd) throws Exception {
        logger.info("dealSearch .........................................." + cmd);
        String lineTxt = null;
        long skip = getReadSkip();
        while ((lineTxt = bufferedReader.readLine()) != null) {
            if (lineTxt.contains(cmd)) {
                writeReadSkip(skip);
                bufferedReader = getBufferedReader();
                bufferedReader.skip(skip);
                break;
            }
            skip += lineTxt.length() + 2;
        }
    }

    private void print(StringBuilder lines) {
        System.out.println(new Date() + " | ");
        System.out.println(new Date() + " | ");
        System.out.println(new Date() + " | ");
        System.out.println(new Date() + " | System.out.println(new Date());");
        System.out.println(new Date() + " | System.out.println(new Date());");
        System.out.println(new Date() + " | System.out.println(new Date());");
        System.out.println(new Date() + " | System.out.println(new Date());");
        System.out.println(new Date() + " | System.out.println(new Date());");
        System.out.println(new Date() + " | ");

        String[] lineArr = lines.toString().split("\\*");
        for (String line : lineArr) {
            printLine(line);
        }
    }

    private void printLine(String line) {
        line = line.trim();
        if (line.length() == 0) {
            return;
        }
        int count = line.length() / lineLen + 1;
        for (int i = 0; i < count; i++) {
            String s = new Date() + " | " + line.substring(i * lineLen, (i + 1) * lineLen > line.length() ? line.length() : (i + 1) * lineLen);
            System.out.println(s);
        }
    }

    private void writeReadSkip(long skip) {
        try {
            logger.info("writeReadSkip :" + skip);
            skipS = skip;
            File fileSkip = new File(file.getAbsolutePath() + ".skip");
            FileWriter fw = new FileWriter(fileSkip);
            fw.write("" + skip);
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long getReadSkip() {
        long skip = 0;
        if (skipS != 0) {
            skip = skipS;
        } else {
            try {
                File fileSkip = new File(file.getAbsolutePath() + ".skip");
                FileReader fr = new FileReader(fileSkip);
                char[] cbuf = new char[100];
                fr.read(cbuf);
                String skips = new String(cbuf);
                skip = Long.valueOf(skips.trim());
                fr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.info("getReadSkip :" + skip);
        return skip;
    }
}
