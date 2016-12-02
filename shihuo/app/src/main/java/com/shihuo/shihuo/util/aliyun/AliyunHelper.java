package com.shihuo.shihuo.util.aliyun;


import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

/**
 * Created by cm_qiujiaheng on 2016/11/30.
 */

public class AliyunHelper {

    private OSS oss;

    // 运行sample前需要配置以下字段为有效的值
//    private static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static final String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private static final String accessKeyId = "LTAIN8yzNyKE7zNg";
    private static final String accessKeySecret = "hm5adAIO04MQc5Il1QVXouYCd2MpYA";
    private static final String uploadFilePath = "<upload_file_path>";

    private static final String testBucket = "shihuo-user";
    private static final String uploadObject = "sampleObject";
    private static final String downloadObject = "sampleObject";


    private final String endPoint = "http://oss-cn-beijing.aliyuncs.com";

    private static class SingleHolder {
        private static final AliyunHelper INST = new AliyunHelper();
    }

    public static final AliyunHelper getInstance() {
        return SingleHolder.INST;
    }

    private AliyunHelper() {
    }

    public void init(Context context) {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(context, endpoint, credentialProvider, conf);
    }

    /**
     * new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
    @Override
    public void onSuccess(PutObjectRequest request, PutObjectResult result) {
    Log.d("PutObject", "UploadSuccess");

    Log.d("ETag", result.getETag());
    Log.d("RequestId", result.getRequestId());
    }

    @Override
    public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
    // 请求异常
    if (clientExcepion != null) {
    // 本地异常如网络异常等
    clientExcepion.printStackTrace();
    }
    if (serviceException != null) {
    // 服务异常
    Log.e("ErrorCode", serviceException.getErrorCode());
    Log.e("RequestId", serviceException.getRequestId());
    Log.e("HostId", serviceException.getHostId());
    Log.e("RawMessage", serviceException.getRawMessage());
    }
    }
    }
     * @param filePath
     */
    public  void asyncUplodaFile(final String filePath , final OSSCompletedCallback<PutObjectRequest, PutObjectResult> ossCompletedCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new PutObjectSamples(oss, testBucket, uploadObject, filePath).asyncPutObjectFromLocalFile(ossCompletedCallback);
            }
        }).start();
    }

}
