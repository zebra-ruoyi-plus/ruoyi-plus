package com.zebra.common.utils.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.springframework.util.MimeType;

import lombok.extern.slf4j.Slf4j;

/**
 * HTTP 工具类 http get/post请求 https get/post请求
 *
 * @author zebra
 *
 */
@Slf4j
public class HttpUtil {

	private static HttpUtil httputil = new HttpUtil();

	private HttpUtil() {

	}

	// 默认字符编码
	static String default_charset = "UTF-8";

	// 默认连接超时时间
	static int default_connecttimeout = 10000;

	// 默认读取超时时间
	static int default_readtimeout = 10000;

	/**
	 * 返回对象
	 *
	 * @param charset
	 *            编码集合， 可空。 目前支持UTF-8|UTF-16|ASCII|GB2312|GBK|ISO-8859-1|UNICODE
	 *            可空 默认UTF-8
	 * @param connecttimeout
	 *            连接超时时间,不可小于10秒,最长不大于60秒, 单位为毫秒
	 * @param readtimeout
	 *            读超时时间 ,不可小于10秒,最长不大于60秒, 单位为毫秒
	 * @return
	 */
	public static HttpUtil getInstance(String charset, int connecttimeout, int readtimeout) {
		// if (connecttimeout >= default_connecttimeout)
		// {
		// if (connecttimeout > 60000)
		// {
		// default_connecttimeout = 60000;
		// }
		// else
		// {
		// default_connecttimeout = connecttimeout;
		// }
		// }
		// if (readtimeout >= default_readtimeout)
		// {
		// if (readtimeout > 60000)
		// {
		// default_readtimeout = 60000;
		// }
		// else
		// {
		// default_readtimeout = readtimeout;
		// }
		// }
		default_connecttimeout = connecttimeout;
		default_readtimeout = readtimeout;
		if (charset != null && !"".equals(charset)) {
			charset = charset.toUpperCase();
			String[] temp = { "UTF-8", "UTF-16", "ASCII", "GB2312", "GBK", "ISO-8859-1", "UNICODE" };
			for (int i = 0; i < temp.length; i++) {
				if (charset.equals(temp[i])) {
					default_charset = charset;
					break;
				}
			}
		}
		/*
		 * log.info("编码集[" + default_charset + "],连接超时时间[" +
		 * default_connecttimeout + "]毫秒,读写超时时间[" + default_readtimeout +
		 * "]毫秒");
		 */
		return httputil;
	}

	/**
	 * @Description: 获取SSL上下文
	 * @Check parameters by the 【caller】
	 * @param keyPath
	 *            密钥库文件绝对路径
	 * @param keyPassword
	 *            密码
	 * @param keytype
	 *            密钥类型
	 * @param trustkeyPath
	 *            受信库文件绝对路径
	 * @param trustkeyPassword
	 *            密码
	 * @param trustkeytype
	 *            密钥类型
	 * @return
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static SSLContext getSSLContext(String keyPath, String keyPassword, String keytype, String trustkeyPath,
			String trustkeyPassword, String trustkeytype)
			throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		kmf.init(getkeyStore(keyPath, keyPassword, keytype), keyPassword.toCharArray());
		KeyManager[] keyManagers = kmf.getKeyManagers();

		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
		trustManagerFactory.init(getkeyStore(trustkeyPath, trustkeyPassword, trustkeytype));
		TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(keyManagers, trustManagers, new SecureRandom());
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
		return sslContext;
	}

	/**
	 * @Description: 获取密钥对象
	 * @Check parameters by the 【caller】
	 * @param keyPath
	 *            密钥的绝对路径
	 * @param keyPassword
	 *            密码
	 * @param keytype
	 *            密钥的类型 KeyType.PKCS12.toString() or KeyType.JSK.toString()
	 * @return 密钥对象
	 */
	public static KeyStore getkeyStore(String keyPath, String keyPassword, String keytype) {
		KeyStore keySotre = null;
		try {
			keySotre = KeyStore.getInstance(keytype.toString());
			FileInputStream fis = new FileInputStream(new File(keyPath));
			keySotre.load(fis, keyPassword.toCharArray());
			fis.close();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return keySotre;
	}

	/**
	 * 把Map 转换为 key=value&key1=value1的格式
	 *
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String changeMap2String(Map<String, Object> map) throws UnsupportedEncodingException {
		// 判断Key Value参数Map集合是否有数据，如果有处理成key=value&key2=value2格式
		if (map != null && map.size() != 0) {
			StringBuffer sb = new StringBuffer();
			String str = "";
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				sb.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue().toString(), default_charset) + "&");
			}
			if (!"".equals(sb.toString())) {
				str = "?" + sb.substring(0, sb.length() - 1);
			}
			return str;
		} else {
			return "";
		}
	}

	private static class DefaultTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}

	public String sendHttpGet(String url, Map<String, Object> map, Map<String, String> headParams, MimeType mimeType) {

		HttpURLConnection connection = null;
		// 输出流
		OutputStream out = null;
		// 读取响应流
		BufferedReader in = null;
		// 响应结果
		String result = "";
		// 判断URL是否为空
		if (url == null || url.equals("")) {
			log.info("URL不能为空");
			return null;
		}
		try {
			// 增加参数
			url += changeMap2String(map);
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			connection = (HttpURLConnection) realUrl.openConnection();
			// 设置头信息
			connection.setRequestProperty("Connection", "Keep-Alive");
			// 接受任何类型的数据
			connection.setRequestProperty("accept", "*/*");
			// 设置请求头
			connection.setRequestProperty("Content-Type", mimeType + "; charset=" + default_charset);
			// 设置自定义头信息
			if (headParams != null && headParams.size() != 0) {
				for (Map.Entry<String, String> entry : headParams.entrySet()) {
					connection.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			// 设置不缓存
			connection.setUseCaches(false);
			// 设置连接超时时间
			connection.setConnectTimeout(default_connecttimeout);
			// 设置读取超时时间
			connection.setReadTimeout(default_readtimeout);
			// 建立实际的连接
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			} else {
				log.info("状态字:" + connection.getResponseCode());
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			}
		} catch (Exception e) {
			log.info("http Get请求发生异常:" + e.getMessage());
			return null;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				return null;
			}
		}
		return result;
	}

	public String sendHttpsGetByUnilateral(String url, Map<String, Object> map, Map<String, String> headParams,
			MimeType mimeType) {

		HttpsURLConnection connection = null;
		// 输出流
		OutputStream out = null;
		// 读取响应流
		BufferedReader in = null;
		// 响应结果
		String result = "";
		// 判断URL是否为空
		if (url == null || url.equals("")) {
			log.info("URL不能为空");
			return null;
		}
		try {
			// 增加参数
			url += changeMap2String(map);
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			connection = (HttpsURLConnection) realUrl.openConnection();
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
			SSLContext.setDefault(sslContext);
			SSLSocketFactory sslsocketfactory = sslContext.getSocketFactory();
			connection.setSSLSocketFactory(sslsocketfactory);
			connection.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			// 设置不缓存
			connection.setUseCaches(false);
			// 发送POST请求必须设置如下两行
			connection.setDoInput(true);
			connection.setDoOutput(true);
			// 设置头信息
			connection.setRequestProperty("Connection", "Keep-Alive");
			// 接受任何类型的数据
			connection.setRequestProperty("accept", "*/*");
			// 设置请求头
			connection.setRequestProperty("Content-Type", mimeType + "; charset=" + default_charset);
			// 设置自定义头信息
			if (headParams != null && headParams.size() != 0) {
				for (Map.Entry<String, String> entry : headParams.entrySet()) {
					connection.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			connection.setConnectTimeout(default_connecttimeout);
			connection.setReadTimeout(default_readtimeout);
			// 建立实际的连接
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			} else {
				log.info("状态字:" + connection.getResponseCode());
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			}
		} catch (Exception e) {
			log.info("https post请求发生异常:" + e.getMessage());
			return null;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public String sendHttpsGetByBoth(String url, Map<String, Object> map, Map<String, String> headParams,
			MimeType mimeType, String keyPath, String keyPassword, String keytype, String trustkeyPath,
			String trustkeyPassword, String trustkeytype) {

		HttpsURLConnection connection = null;
		// 输出流
		OutputStream out = null;
		// 读取响应流
		BufferedReader in = null;
		// 响应结果
		String result = "";
		// 判断URL是否为空
		if (url == null || url.equals("")) {
			log.info("URL不能为空");
			return null;
		}
		try {
			// 增加参数
			url += changeMap2String(map);
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			connection = (HttpsURLConnection) realUrl.openConnection();
			SSLContext sslContext = getSSLContext(keyPath, keyPassword, keytype, trustkeyPath, trustkeyPassword,
					trustkeytype);
			SSLSocketFactory sslsocketfactory = sslContext.getSocketFactory();
			connection.setSSLSocketFactory(sslsocketfactory);
			// 设置不缓存
			connection.setUseCaches(false);
			// 发送POST请求必须设置如下两行
			connection.setDoInput(true);
			connection.setDoOutput(true);
			// 设置头信息
			connection.setRequestProperty("Connection", "Keep-Alive");
			// 接受任何类型的数据
			connection.setRequestProperty("accept", "*/*");
			// 设置请求头
			connection.setRequestProperty("Content-Type", mimeType + "; charset=" + default_charset);
			// 设置自定义头信息
			if (headParams != null && headParams.size() != 0) {
				for (Map.Entry<String, String> entry : headParams.entrySet()) {
					connection.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			connection.setConnectTimeout(default_connecttimeout);
			connection.setReadTimeout(default_readtimeout);
			// 建立实际的连接
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			} else {
				log.info("状态字:" + connection.getResponseCode());
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			}
		} catch (Exception e) {
			log.info("https post请求发生异常:" + e.getMessage());
			return null;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public String sendHttpPost(String url, Map<String, Object> map, Map<String, String> headParams, String str,
			MimeType mimeType) {

		HttpURLConnection connection = null;
		// 输出流
		OutputStream out = null;
		// 读取响应流
		BufferedReader in = null;
		// 响应结果
		String result = "";
		// 判断URL是否为空
		if (url == null || url.equals("")) {
			log.info("URL不能为空");
			return null;
		}
		try {
			// 增加参数
			url += changeMap2String(map);
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			connection = (HttpURLConnection) realUrl.openConnection();
			// 设置头信息
			connection.setRequestProperty("Connection", "Keep-Alive");
			// 接受任何类型的数据
			connection.setRequestProperty("accept", "*/*");
			// 设置请求头
			connection.setRequestProperty("Content-Type", mimeType + "; charset=" + default_charset);
			// 设置自定义头信息
			if (headParams != null && headParams.size() != 0) {
				for (Map.Entry<String, String> entry : headParams.entrySet()) {
					connection.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			// 设置不缓存
			connection.setUseCaches(false);
			// 发送POST请求必须设置如下两行
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// 设置连接超时时间
			connection.setConnectTimeout(default_connecttimeout);
			// 设置读取超时时间
			connection.setReadTimeout(default_readtimeout);
			out = connection.getOutputStream();
			// 判断要发送的数据是否为空
			if (str != null && !"".equals(str)) {
				// 按字符集编码转流
				byte[] data = str.getBytes(default_charset);
				out.write(data);
			}
			out.flush();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			} else {
				log.info("状态字:" + connection.getResponseCode());
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			}
		} catch (Exception e) {
			log.info("http Post请求发生异常:" + e.getMessage());
			return null;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				return "";
			}
		}

		return result;
	}

	public String sendHttpsPostByUnilateral(String url, Map<String, Object> map, Map<String, String> headParams,
			String str, MimeType mimeType) {
		HttpsURLConnection connection = null;
		// 输出流
		OutputStream out = null;
		// 读取响应流
		BufferedReader in = null;
		// 响应结果
		String result = "";
		// 判断URL是否为空
		if (url == null || url.equals("")) {
			log.info("URL不能为空");
			return null;
		}
		try {
			// 增加参数
			url += changeMap2String(map);
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			connection = (HttpsURLConnection) realUrl.openConnection();
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
			SSLContext.setDefault(sslContext);
			SSLSocketFactory sslsocketfactory = sslContext.getSocketFactory();
			connection.setSSLSocketFactory(sslsocketfactory);
			connection.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			// 设置不缓存
			connection.setUseCaches(false);
			// 发送POST请求必须设置如下两行
			connection.setDoInput(true);
			connection.setDoOutput(true);
			// 设置头信息
			connection.setRequestProperty("Connection", "Keep-Alive");
			// 接受任何类型的数据
			connection.setRequestProperty("accept", "*/*");
			// 设置请求头
			connection.setRequestProperty("Content-Type", mimeType + "; charset=" + default_charset);
			// 设置自定义头信息
			if (headParams != null && headParams.size() != 0) {
				for (Map.Entry<String, String> entry : headParams.entrySet()) {
					connection.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			connection.setConnectTimeout(default_connecttimeout);
			connection.setReadTimeout(default_readtimeout);
			out = connection.getOutputStream();
			// 判断要发送的数据是否为空
			if (str != null && !"".equals(str)) {
				// 按字符集编码转流
				byte[] data = str.getBytes(default_charset);
				out.write(data);
			}
			out.flush();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			} else {
				log.info("状态字:" + connection.getResponseCode());
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			}
		} catch (Exception e) {
			log.info("https post请求发生异常:" + e.getMessage());
			return null;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public String sendHttpsPostByBoth(String url, Map<String, Object> map, Map<String, String> headParams, String str,
			MimeType mimeType, String keyPath, String keyPassword, String keytype, String trustkeyPath,
			String trustkeyPassword, String trustkeytype) throws Exception {
		HttpsURLConnection connection = null;
		// 输出流
		OutputStream out = null;
		// 读取响应流
		BufferedReader in = null;
		// 响应结果
		String result = "";
		// 判断URL是否为空
		if (url == null || url.equals("")) {
			log.info("URL不能为空");
			return null;
		}
		try {
			SSLContext sslContext = getSSLContext(keyPath, keyPassword, keytype, trustkeyPath, trustkeyPassword,
					trustkeytype);
			// 增加参数
			url += changeMap2String(map);
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			connection = (HttpsURLConnection) realUrl.openConnection();
			SSLSocketFactory sslsocketfactory = sslContext.getSocketFactory();
			connection.setSSLSocketFactory(sslsocketfactory);
			// 设置不缓存
			connection.setUseCaches(false);
			// 发送POST请求必须设置如下两行
			connection.setDoInput(true);
			connection.setDoOutput(true);
			// 设置头信息
			connection.setRequestProperty("Connection", "Keep-Alive");
			// 接受任何类型的数据
			connection.setRequestProperty("accept", "*/*");
			// 设置请求头
			connection.setRequestProperty("Content-Type", mimeType + "; charset=" + default_charset);
			// 设置自定义头信息
			if (headParams != null && headParams.size() != 0) {
				for (Map.Entry<String, String> entry : headParams.entrySet()) {
					connection.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			connection.setConnectTimeout(default_connecttimeout);
			connection.setReadTimeout(default_readtimeout);
			out = connection.getOutputStream();
			// 判断要发送的数据是否为空
			if (str != null && !"".equals(str)) {
				// 按字符集编码转流
				byte[] data = str.getBytes(default_charset);
				out.write(data);
			}
			out.flush();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			} else {
				log.info("状态字:" + connection.getResponseCode());
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			}
		} catch (Exception e) {
			log.info("https post请求发生异常:" + e.getMessage());
			return null;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}