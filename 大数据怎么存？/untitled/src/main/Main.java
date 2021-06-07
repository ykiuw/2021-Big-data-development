package main;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.*;
import com.sun.org.apache.xpath.internal.operations.Mult;

import javax.tools.Tool;

//下载文件
public class Main {
    private final static String bucketName = "zhangziyi";
    private final static String filePath1   =
            "C:\\Users\\86733\\Desktop\\index.html";
    private final static String accessKey = "3DADC8A072AED72C12D3";
    private final static String secretKey =
            "Wzk3NUUwRTU3RUQzNTFGRTBDNDY3RDI2RjdEOUY0";
    private final static String serviceEndpoint =
            "http://scut.depts.bingosoft.net:29997";
    private static long partSize = 5<<20;
    private final static String signingRegion = "";

    public static void main(String[] args) {

        final BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey,secretKey);
        final ClientConfiguration ccfg = new ClientConfiguration().
                withUseExpectContinue(true);

        final EndpointConfiguration endpoint = new EndpointConfiguration(serviceEndpoint, signingRegion);

        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withClientConfiguration(ccfg)
                .withEndpointConfiguration(endpoint)
                .withPathStyleAccessEnabled(true)
                .build();
        //获取文件名
        List<String> list=new ArrayList<String>();
        ObjectListing objects=s3.listObjects("zhangziyi");
        do{
            for(S3ObjectSummary objectSummary: objects.getObjectSummaries()){
                System.out.println("Object: "+objectSummary.getKey());
                list.add(objectSummary.getKey());
            }
            objects=s3.listNextBatchOfObjects(objects);
        }while(objects.isTruncated());
        //循环获取路径名称进行访问
        for(String s:list){
            String filePath = "C:/Users/86733/Desktop/zhangziyi/" + s;
            System.out.println(filePath);
            final String keyName = Paths.get(filePath).getFileName().toString();
            System.out.println(keyName);
            if(keyName.indexOf(".")==-1) { //判断是否为文件夹
                File folder = null;
                folder = new File(filePath);
                folder.mkdirs();
                continue;
            }

            final File file = new File(filePath);

            System.out.format("Downloading %s to S3 bucket %s...\n", keyName, bucketName);

            S3ObjectInputStream s3is = null;
            FileOutputStream fos = null;
            try {
                String keyName1=s;
                S3Object o = s3.getObject(bucketName, keyName1);
                ObjectMetadata oMetaData = s3.getObjectMetadata(bucketName,keyName1);
                final long contentLength = oMetaData.getContentLength();
                if(contentLength>20971520){ //若大于20MB，则使用分块下载
                    MultipartDownload multidownload=new MultipartDownload();
                    multidownload.download(keyName1, filePath.replace(keyName1,""));
                    continue;
                }
                s3is = o.getObjectContent();
                fos = new FileOutputStream(new File(filePath));
                byte[] read_buf = new byte[64 * 1024];
                int read_len = 0;
                while ((read_len = s3is.read(read_buf)) > 0) {
                    fos.write(read_buf, 0, read_len);
                }
            } catch (AmazonServiceException e) {
                System.err.println(e.toString());
                System.exit(1);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            } finally {
                if (s3is != null) try { s3is.close(); } catch (IOException e) { }
                if (fos != null) try { fos.close(); } catch (IOException e) { }
            }
        }

        System.out.println("Done!");

    }

}

