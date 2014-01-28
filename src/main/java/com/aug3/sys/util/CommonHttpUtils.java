package com.aug3.sys.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.pool.PoolStats;
import org.apache.log4j.Logger;

public class CommonHttpUtils {

	private static final Logger log = Logger.getLogger(CommonHttpUtils.class);

	public static final String UTF8 = "UTF-8";

	private static PoolingHttpClientConnectionManager clientConnMgr = new PoolingHttpClientConnectionManager();

	static {
		clientConnMgr.setMaxTotal(100);
	}

	private final static int DEFAULT_TIMEOUT = 5000;

	/**
	 * 
	 * @param timeout
	 *            : connectionTimeout | sotimeout
	 * @return
	 */
	public static HttpClient getPooledHttpClient(int timeout) {

		RequestConfig rconf = RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout).build();

		HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(rconf).build();

		return httpClient;
	}

	public static PoolStats getPoolStats() {
		return clientConnMgr.getTotalStats();
	}

	public static String executeGetMothedRequest(String url) {
		return executeGetMothedRequest(url, DEFAULT_TIMEOUT);
	}

	/**
	 * Using Get Method arose a request
	 * 
	 * @param url
	 * @return
	 */
	public static String executeGetMothedRequest(String path, int timeout) {

		String result = null;

		HttpClient httpClient = null;
		HttpGet httpget = null;
		try {
			URL url = new URL(path);
			URI uri = new URI(url.getProtocol(), null, url.getHost(), url.getPort(), url.getPath(), url.getQuery(),
					null);

			httpget = new HttpGet(uri);
			httpget.addHeader("accept", "application/json");

			httpClient = getPooledHttpClient(timeout);

			HttpResponse response = httpClient.execute(httpget);
			if (response.getStatusLine().getStatusCode() >= 400) {
				log.warn("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), UTF8));
			StringBuilder s = new StringBuilder();
			while ((result = br.readLine()) != null) {
				s = s.append(result);
			}
			result = s.toString();
			br.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			if (httpget != null) {
				httpget.abort();
			}
		}
		return result;
	}

	public static String executePostMothedRequest(String url, Map<String, String> paramsMap) {
		return executePostMothedRequest(url, paramsMap, DEFAULT_TIMEOUT);
	}

	/**
	 * Using Post Method arose a request
	 * 
	 * @param url
	 * @param paramsMap
	 * @return
	 */
	public static String executePostMothedRequest(String url, Map<String, String> paramsMap, int timeout) {
		String result = null;

		HttpPost httppost = null;
		try {

			httppost = new HttpPost(url);
			httppost.addHeader("accept", "application/json");

			List<NameValuePair> paramsList = constructParamList(paramsMap);

			httppost.setEntity(new UrlEncodedFormEntity(paramsList, UTF8));

			HttpClient httpClient = getPooledHttpClient(timeout);

			HttpResponse response = httpClient.execute(httppost);

			if (response.getStatusLine().getStatusCode() >= 400) {
				log.warn("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), UTF8));
			StringBuilder s = new StringBuilder();
			while ((result = br.readLine()) != null) {
				s = s.append(result);
			}
			result = s.toString();
			br.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httppost != null) {
				httppost.abort();
			}
		}
		return result;
	}

	/**
	 * Convert params Map into list
	 * 
	 * @param paramsMap
	 * @return
	 */
	private static List<NameValuePair> constructParamList(Map<String, String> paramsMap) {
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		for (String key : paramsMap.keySet()) {
			paramsList.add(new BasicNameValuePair(key, paramsMap.get(key)));
		}
		return paramsList;
	}

}
