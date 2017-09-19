package org.seraph.mvprxjavaretrofit.data.network.http;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * 使用https请求帮助类
 * date：2017/3/1 15:00
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class HttpsRequestHelp {

    private AppApplication mApplication;

    private X509TrustManager mX509TrustManager;

    private SSLSocketFactory mSSLSocketFactory;

    private String httpsCerName = "";

    @Inject
    public HttpsRequestHelp(AppApplication application) {
        this.mApplication = application;
    }

    /**
     * 设置证书在Assets下的路径
     */
    public void setHttpsCerName(String httpsCerName) {
        this.httpsCerName = httpsCerName;
    }

    /**
     * 获取X509TrustManager
     */
    public X509TrustManager getX509TrustManager() throws IOException, GeneralSecurityException {
        if (Tools.isNull(httpsCerName)){
            return null;
        }
        if (mX509TrustManager == null) {
            mX509TrustManager = getX509TrustManager(mApplication.getAssets().open(httpsCerName));
        }
        return mX509TrustManager;
    }

    /**
     * 获取SSLSocketFactory
     */
    public SSLSocketFactory getSSLSocketFactory() throws IOException, GeneralSecurityException {
        X509TrustManager x509TrustManager = getX509TrustManager();
        if (x509TrustManager == null){
            return null;
        }
        if (mSSLSocketFactory == null) {
            mSSLSocketFactory = getSSLSocketFactory(x509TrustManager);
        }
        return mSSLSocketFactory;
    }




    /**
     * 以流的方式添加信任证书
     */
    /**
     * 返回信任{@code certificates}和没有其他的信任管理器。 HTTPS服务证书没有被这些证书签署将失败与{@code SSLHandshakeException}。
     * 这可以用于用自定义替换主机平台的内置可信证书组。
     * 这在证书颁发机构信任的证书没有的开发中很有用或在生产中，以避免依赖第三方证书颁发机构。
     * 警告：自定义受信任的证书是危险的！
     * 依靠您自己的可信证书限制您的服务器团队更新他们的能力TLS证书。
     * 通过安装一组特定的受信任证书，您可以接受额外的证书操作复杂性，并限制您在证书颁发机构之间迁移的能力。
     * 如果没有服务器的TLS管理员的blessing ，请不要在生产中使用自定义的可信证书。
     */
    private X509TrustManager getX509TrustManager(InputStream in) throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // 将证书作为密钥存储。
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // 使用它来构建一个X509信任管理器。
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    /**
     * 添加password
     *
     * @param password 密码
     */
    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType()); // 这里添加自定义的密码，默认
            keyStore.load(null, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }



    private SSLSocketFactory getSSLSocketFactory(X509TrustManager trustManager) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{trustManager}, null);
        return sslContext.getSocketFactory();
    }

}
