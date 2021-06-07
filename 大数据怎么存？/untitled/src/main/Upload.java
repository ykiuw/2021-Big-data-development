package main;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.MultipartUpload;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Upload {
    private final static String bucketName = "zhangziyi";
    private final static String accessKey = "3DADC8A072AED72C12D3";
    private final static String secretKey = "Wzk3NUUwRTU3RUQzNTFGRTBDNDY3RDI2RjdEOUY0";
    private final static String serviceEndpoint =
            "http://scut.depts.bingosoft.net:29997";

    private final static String signingRegion = "";

    public static void main(String[] args) {
        final BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        final ClientConfiguration ccfg = new ClientConfiguration().
                withUseExpectContinue(false);

        final EndpointConfiguration endpoint = new EndpointConfiguration(serviceEndpoint, signingRegion);

        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withClientConfiguration(ccfg)
                .withEndpointConfiguration(endpoint)
                .withPathStyleAccessEnabled(true)
                .build();

        List<String> list=new ArrayList<>();
        File myFile=new File("C:/Users/86733/Desktop/zhangziyi");
        File files[]=myFile.listFiles();
        for(int i=0;i<files.length;i++){
            list.add(files[i].toString());
        }
        System.out.println(list);

        for(String s:list){
            String filePath=s;
            System.out.format("Uploading %s to S3 bucket %s...\n", filePath, bucketName);
            final String keyName = Paths.get(filePath).getFileName().toString();
            final File file = new File(filePath);
            if(file.length()>20971520){//若大于20MB，则使用分块上传
                main.MultipartUpload multiUpload =new main.MultipartUpload();
                multiUpload.upload(filePath);
                continue;
            }
            for (int i = 0; i < 2; i++) {
                try {
                    s3.putObject(bucketName, keyName, file);
                    break;
                } catch (AmazonServiceException e) {
                    if (e.getErrorCode().equalsIgnoreCase("NoSuchBucket")) {
                        s3.createBucket(bucketName);
                        continue;
                    }

                    System.err.println(e.toString());
                    System.exit(1);
                } catch (AmazonClientException e) {
                    try {
                        // detect bucket whether exists
                        s3.getBucketAcl(bucketName);
                    } catch (AmazonServiceException ase) {
                        if (ase.getErrorCode().equalsIgnoreCase("NoSuchBucket")) {
                            s3.createBucket(bucketName);
                            continue;
                        }
                    } catch (Exception ignore) {
                    }

                    System.err.println(e.toString());
                    System.exit(1);
                }
            }
        }
        System.out.println("Done!");
    }
}
