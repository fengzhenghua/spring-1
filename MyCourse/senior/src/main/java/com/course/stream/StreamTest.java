package com.course.stream;

import org.junit.Test;

import java.beans.FeatureDescriptor;
import java.io.*;

/**
 * Created by fengzhenghua on
 * 2017-08-26 23:22.
 */
public class StreamTest {

    @Test
    public void ReaderTest() {

        File file = new File("E:\\spring\\spring\\MyCourse\\senior\\src\\main\\resource\\test.txt");
        InputStream in = null;
        //OutputStream out = null;
        InputStreamReader reader = null;
        BufferedReader bf = null;
        try {
            in = new FileInputStream(file);
            reader = new InputStreamReader(in, "UTF-8");
            bf = new BufferedReader(reader);
            //int n = 0;
            String dataTxt = bf.readLine();
            while (dataTxt != null) {
                System.out.println(dataTxt);
                dataTxt = bf.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {


        }
    }
    @Test
    public void outPutStreamTest() throws IOException {

        File file = new File("E:\\spring\\spring\\MyCourse\\senior\\src\\main\\resource\\test1.txt");
        OutputStream out = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(out,"UTF-8");
        BufferedWriter bfw = new BufferedWriter(writer);
        bfw.write("这是一个bufferedWriter测试");
        bfw.flush();
        bfw.close();
        out.close();
        writer.close();
    }

    @Test
    public void copyFile() throws IOException{
        File file = new File("E:\\spring\\spring\\MyCourse\\senior\\src\\main\\resource\\test.txt");
        InputStream in = new FileInputStream(file);
        File file1 = new File("D:\\test.txt");
        //InputStreamReader inputStreamReader = new InputStreamReader(in);
        OutputStream out = new FileOutputStream(file1);
        byte[] bytes = new byte[1024];
        int n = 0;
        while ((n=in.read(bytes)) != -1){
            out.write(bytes,0,bytes.length);
        }
        in.close();
        out.close();
    }
}
