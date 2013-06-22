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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;

public class CommonHttpUtils {

	private static final Logger log = Logger.getLogger(CommonHttpUtils.class);

	public static final String UTF8 = "UTF-8";

	private static PoolingClientConnectionManager clientConnMgr = new PoolingClientConnectionManager();

	static {
		clientConnMgr.setMaxTotal(100);
	}

	private final static int DEFAULT_TIMEOUT = 5000;

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

			HttpParams httpParams = new BasicHttpParams();
			httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
			httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
			httpParams.setParameter(CoreConnectionPNames.TCP_NODELAY, true);

			httpClient = new DefaultHttpClient(clientConnMgr, httpParams);

			httpget = new HttpGet(uri);
			httpget.addHeader("accept", "application/json");

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
			HttpParams httpParams = new BasicHttpParams();
			httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
			httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
			httpParams.setParameter(CoreConnectionPNames.TCP_NODELAY, true);

			HttpClient httpClient = new DefaultHttpClient(clientConnMgr, httpParams);

			httppost = new HttpPost(url);
			httppost.addHeader("accept", "application/json");

			List<NameValuePair> paramsList = constructParamList(paramsMap);

			httppost.setEntity(new UrlEncodedFormEntity(paramsList, UTF8));

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
