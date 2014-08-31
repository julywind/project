package com.special.ResideMenuDemo.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import android.webkit.CookieSyncManager;
import com.special.ResideMenuDemo.Login;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.webkit.CookieManager;

public class HttpUtil {
    public static final String logTag = "HttpUtil";
	public static PackageInfo readPackageInfo(Context ctx) {
		PackageInfo info = null;
		try {
			info = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),
					0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block

			if (info == null)
				info = new PackageInfo();
			info.versionName = "Unknown Version";
			info.versionCode = 0;
			
			e.printStackTrace();
		}
		return info;

	}

	public static String getServerAddr(Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("cfrt", 0);
		return sp.getString("serverAddr", Login.Default_Server);
	}

	public static String getCookieStr(Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("cfrt", 0);
		String cookieStr =sp.getString("cookieStr", ""); 
		Log.i(logTag,"getCookieStr:"+cookieStr);
		return cookieStr;
	}
    public static void setCookieStr(Context ctx,String cookieStr) {
        Log.i(logTag,"Login.setCookieStr()  cookieStr=" + cookieStr);
        SharedPreferences sp = ctx.getSharedPreferences("cfrt", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("cookieStr", cookieStr);
        editor.commit();
    }

	/**
	 * java.net实现 HTTP POST方法提交
	 * 
	 * @param url
	 * @param paramContent
	 * @return
	 */
	public static String finalUrl(Context ctx, String url) {
		String basePath = getServerAddr(ctx);

		if (basePath == null || basePath.trim().length() == 0 || url == null)
			return url;
		if (url.toLowerCase().startsWith("http://")
				|| url.toLowerCase().startsWith("https://"))
			return url;

		if (url.startsWith("/") && basePath.endsWith("/")) {
			return basePath + url.substring(1);
		} else if (!url.startsWith("/") && !basePath.endsWith("/")) {
			return basePath + "/" + url;
		} else {
			return basePath + url;
		}
	}

	public static StringBuffer submitPost(Context ctx, String url,
			String cookieStr) {
		StringBuffer responseMessage = null;
		HttpURLConnection connection = null;
		URL reqUrl = null;
		OutputStreamWriter reqOut = null;
		InputStream in = null;
		BufferedReader br = null;

		url = finalUrl(ctx, url);

		String paramStr = "";
		try {
			responseMessage = new StringBuffer();
			reqUrl = new URL(url);
			connection = (HttpURLConnection) reqUrl.openConnection();

			connection.setReadTimeout(3000);
			connection.setConnectTimeout(3000);// jdk 1.5换成这个,连接超时
			connection.setReadTimeout(3000);// jdk 1.5换成这个,读操作超时

			connection.setRequestMethod("POST");
			connection.setRequestProperty("cookie", cookieStr);
			connection.setRequestProperty("Charset", "utf-8");
			connection.setRequestProperty("terminalType", "Android-szt "
					+ readPackageInfo(ctx).versionName);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setDoInput(true);

			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream(), "utf-8");
			out.write(paramStr); // 向页面传递数据。post的关键所在！
			out.flush();
			out.close();

			int charCount = -1;
			in = connection.getInputStream();

			br = new BufferedReader(new InputStreamReader(in, "utf-8"));
			while ((charCount = br.read()) != -1) {
				responseMessage.append((char) charCount);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				in.close();
				if (reqOut != null)
					reqOut.close();
			} catch (Exception e) {
				e.printStackTrace();
				Log.i(logTag,"paramContent=" + paramStr + "|err=" + e);
			}
		}
		return responseMessage;
	}

	/**
	 * java.net实现 HTTP POST方法提交
	 * 
	 * @param url
	 * @param paramContent
	 * @return
	 */
	public static StringBuffer submitPost(Context ctx, String url,
			Map<String, String> params) throws HttpStatusException,IOException{
		StringBuffer responseMessage = null;
		HttpURLConnection connection = null;
		URL reqUrl = null;
		OutputStreamWriter reqOut = null;
		InputStream in = null;
		BufferedReader br = null;

		url = finalUrl(ctx, url);

		String paramStr = "";
		for (Map.Entry<String, String> entry : params.entrySet()) {// 构建表单字段内容
			if (paramStr.length() > 0)
				paramStr += "&";
			paramStr += entry.getKey() + "="
					+ URLEncoder.encode(entry.getValue());
		}
		try {
			System.out.println("url=" + url + "?" + paramStr + "\n");
			// System.out.println("===========post method start=========");
			responseMessage = new StringBuffer();
			reqUrl = new URL(url);
			connection = (HttpURLConnection) reqUrl.openConnection();

			connection.setReadTimeout(3000);
			connection.setConnectTimeout(3000);// jdk 1.5换成这个,连接超时
			connection.setReadTimeout(3000);// jdk 1.5换成这个,读操作超时

			connection.setRequestMethod("POST");
			connection.setRequestProperty("cookie", getCookieStr(ctx));
			connection.setRequestProperty("Charset", "utf-8");
			connection.setRequestProperty("terminalType", "Android-szt "
					+ readPackageInfo(ctx).versionName);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setDoInput(true);

			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream(), "utf-8");
			out.write(paramStr); // 向页面传递数据。post的关键所在！
			out.flush();
			out.close();

			int charCount = -1;
			in = connection.getInputStream();

			br = new BufferedReader(new InputStreamReader(in, "utf-8"));
			while ((charCount = br.read()) != -1) {
				responseMessage.append((char) charCount);
			}
			System.out.println("===========post method end=========");
		} finally {
			try {
				in.close();
				if (reqOut != null)
					reqOut.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("paramContent=" + paramStr + "|err=" + e);
			}
		}
		return responseMessage;
	}

	/**
	 * java.net实现 HTTP或HTTPs GET方法提交
	 * 
	 * @param strUrl
	 *            提交的地址及参数
	 * @return 返回的response信息
	 */
	public static String submitGet(Context ctx, String strUrl,
			String... strCookie) {
		URLConnection connection = null;
		BufferedReader reader = null;
		String str = "";
		strUrl = finalUrl(ctx, strUrl);
		System.out.println("send getmethod=" + strUrl);

		String lines;
		StringBuffer linebuff = new StringBuffer("");
		try {
			URL url = new URL(strUrl);

			System.out.println("protocal:" + url.getProtocol().toString());
			if (url.getProtocol().toString().equals("https")) {
				connection = (HttpsURLConnection) url.openConnection();
				((HttpsURLConnection) connection).setRequestMethod("GET");
				((HttpsURLConnection) connection)
						.setInstanceFollowRedirects(true);
				X509HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
				 
				HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
			} else {
				connection = (HttpURLConnection) url.openConnection();
				((HttpURLConnection) connection)
						.setInstanceFollowRedirects(true);
				((HttpURLConnection) connection).setRequestMethod("GET");
			}

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			Log.i(logTag,"strCookie:"+strCookie);
			
			if (strCookie != null && strCookie.length > 0) {
				if (strCookie[0] != null && strCookie[0].trim().length() > 0)
					connection.setRequestProperty("cookie", strCookie[0]);
			} else {
				connection.setRequestProperty("cookie", getCookieStr(ctx));
			}

			connection.setRequestProperty("terminalType", "Android-szt "
					+ readPackageInfo(ctx).versionName);

			connection.setConnectTimeout(5000);// jdk 1.5换成这个,连接超时
			connection.setReadTimeout(5000);// jdk 1.5换成这个,读操作超时

			connection.setDoInput(true);
			connection.setDoOutput(true);

			//connection.connect();
			int responseCode = 0;
			if (url.getProtocol().toString().equals("https")) {
				responseCode = ((HttpsURLConnection) connection)
						.getResponseCode();
			} else {
				responseCode = ((HttpURLConnection) connection)
						.getResponseCode();
			}

			if (responseCode == 302) {
				Map<String, List<String>> hnames = connection.getHeaderFields();
				System.out.println("[302 header]:" + hnames.toString());

				System.out
						.println("HttpUtil.submitGet() responseCode:302 Location:"
								+ connection.getHeaderField("Location"));

				String urlstr = connection.getHeaderField("Location");
				// CookieSyncManager.getInstance().startSync();
				CookieManager cm = CookieManager.getInstance();

				String cookie = connection.getHeaderField("Set-Cookie");
				if (cookie != null) {
					System.out.println("Set-Cookie:" + cookie);
					cm.setCookie(strUrl, cookie);
				} else {
					cookie = cm.getCookie(urlstr);
				}
				/*
				 * CookieSyncManager.getInstance().sync();
				 * CookieSyncManager.getInstance().stopSync();
				 */
				String loc = connection.getHeaderField("Location");
				if (url.getProtocol().toString().equals("https")) {
					((HttpsURLConnection) connection)
							.disconnect();
				} else {
					((HttpURLConnection) connection)
							.disconnect();
				}
				if (cookie == null)
					return submitGet(ctx,
							loc, "");
				else
					return submitGet(ctx,
							loc, cookie);
			} else if (responseCode == 200) {
				// 取得输入流，并使用Reader读取
				reader = new BufferedReader(new InputStreamReader(
						connection.getInputStream(), "utf-8"));
				System.out
						.println("============Contents of get request===============");

				while ((lines = reader.readLine()) != null) {
					linebuff.append(lines);
				}
				System.out.println("Contents:" + linebuff);
				System.out
						.println("============Contents of get request ends==========");
				str = linebuff.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	/**
	 * 直接通过HTTP协议提交数据到服务器,实现表单提交功能
	 * 
	 * @param url
	 *            路径
	 */
	public static Bitmap returnBitMap(Context ctx, String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		url = finalUrl(ctx, url);
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setRequestProperty("cookie", getCookieStr(ctx));
			conn.setDoInput(true);

			conn.setReadTimeout(6000);
			conn.setConnectTimeout(3000);// jdk 1.5换成这个,连接超时
			conn.setReadTimeout(3000);// jdk 1.5换成这个,读操作超时

			conn.connect();

			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 直接通过HTTP协议提交数据到服务器,实现表单提交功能
	 * 
	 * @param url
	 *            路径
	 */
	public static String downloadFile(Context ctx, String url, String path,
			String fileName) {

		URL myFileUrl = null;
		url = finalUrl(ctx, url);
		System.out.println("HttpUtil.downloadFile() url = " + url);
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		String extendedName = ".amr";
		String resultName = path;
		if (path.endsWith("/") || path.endsWith("\\")) {
			resultName += fileName;
		} else {
			resultName += "/" + fileName;
		}

		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			HttpURLConnection.setFollowRedirects(true);
			conn.setRequestProperty("cookie", getCookieStr(ctx));
			conn.setDoInput(true);

			conn.setConnectTimeout(3000);// jdk 1.5换成这个,连接超时
			conn.setReadTimeout(6000);// jdk 1.5换成这个,读操作超时

			conn.connect();

			int ret = conn.getResponseCode();
			System.out.println("HttpUitl: conn.getResponseCode():" + ret);

			if (ret == 200) {
				InputStream is = conn.getInputStream();
				String description = conn.getHeaderField("Content-Disposition");

				if (description != null) {
					extendedName = description.substring(description
							.lastIndexOf("."));
					if (description.lastIndexOf('.') != -1) {
						System.out.println("HttpUitl: advised fileName is:"
								+ description.substring(description
										.lastIndexOf("filename=")));
						System.out.println("HttpUitl: extended fileName is:"
								+ description.substring(description
										.lastIndexOf(".")));
					}
				}
				File file = new File(path + extendedName.substring(1));
				if (!file.exists()) {
					file.mkdirs();
				}
				resultName = path + extendedName.substring(1) + "/" + fileName
						+ extendedName;
				file_put_contents(resultName, is);
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultName;
	}

	public static File file_put_contents(String file_name, InputStream is) {
		File file = new File(file_name);
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			int len = 0;
			while ((len = is.read(buffer, 0, 4 * 1024)) != -1) {
				os.write(buffer, 0, len);
			}
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public static String HttpPostData(Context ctx, String url,
			Map<String, String> params,String cookieStr) throws HttpStatusException,IOException
    {
        HttpClient client = new HttpClient();
        PostMethod httppost = new PostMethod(finalUrl(ctx, url));
        System.out.println(httppost.getURI());
        // 添加http头信息
        httppost.setRequestHeader("Content-Type",
                "application/x-www-form-urlencoded;charset=utf-8");
        httppost.setRequestHeader("Authorization", "your token"); // 认证token
        httppost.setRequestHeader("User-Agent", " Mozilla/5.0 (Linux; U; Android 4.4.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
        httppost.setRequestHeader("cookie", cookieStr==null?getCookieStr(ctx):cookieStr);
        httppost.setRequestHeader("Charset", "utf-8");
        httppost.setRequestHeader("terminalType", "Android-szt "
                + readPackageInfo(ctx).versionName);
        // http post的json数据格式： {"name": "your name","parentId":
        // "id_of_parent"}

        String paramStr = "";
        NameValuePair[] param = new NameValuePair[params.size()];
        int idx = 0;
        for (Map.Entry<String, String> entry : params.entrySet()) {// 构建表单字段内容
            if (paramStr.length() > 0)
                paramStr += "&";
            paramStr += entry.getKey() + "="
                    + URLEncoder.encode(entry.getValue());

            param[idx++] = new NameValuePair(entry.getKey(),
                    entry.getValue());
        }

        httppost.setRequestBody(paramStr);
        httppost.releaseConnection();
        client.executeMethod(httppost);

        // String response = new
        // String(method.getResponseBodyAsString().getBytes("ISO-8859-1"));
        // 检验状态码，如果成功接收数据
        int code = httppost.getStatusCode();
        CookieSyncManager.createInstance(ctx);
        CookieSyncManager.getInstance().startSync();
        CookieManager cm = CookieManager.getInstance();

        String cookie=null;
        if(httppost.getResponseHeader("Set-Cookie")!=null)
        {
            cookie = httppost.getResponseHeader("Set-Cookie").getValue();
            cookie = cookie.split(" ")[0];
        }
        if (code == 302) {
            Log.i(logTag,"[302 header]:" + httppost.getResponseHeaders().toString());

            Log.i(logTag,"HttpUtil.submitGet() responseCode:302 Location:"
                            + httppost.getResponseHeader("Location"));

            String urlstr = httppost.getResponseHeader("Location").getValue();

            if (cookie != null) {
                System.out.println("Set-Cookie:" + cookie);
                cm.setCookie(urlstr, cookie);
            } else {
                cookie = cm.getCookie(urlstr);
            }
            CookieSyncManager.getInstance().sync();
            CookieSyncManager.getInstance().stopSync();
            httppost.releaseConnection();
            return HttpPostData(ctx,urlstr, params,cookie);
        } else if (code == 200) {
            if(cookie!=null) {
                Log.i(logTag,"Set-Cookie[hmt]:" + cookie);
                setCookieStr(ctx, cookie);
            }
            String response = httppost.getResponseBodyAsString();
            // String rev = EntityUtils.toString(response.getEntity());//
            // 返回json格式：
            // {"id":
            // "27JpL~j4vsL0LX00E00005","version":
            // "abc"}
            /*
             * Object obj = JSONObject.fromObject(response); String id =
             * obj.getString("id"); String version =
             * obj.getString("version");
             */
            Log.i(logTag,"============Contents of get request===============");
            Log.i(logTag,response);
            Log.i(logTag,"============Contents of get request ends===============");
            return response;
        }else{
            throw new HttpStatusException(code,"服务器状态异常");
        }
	}
}
