package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase {
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closebaleHttpResponse;

	@BeforeMethod
	public void setUp() throws IOException, Exception {
		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		// https://reqres.in/api/users

		url = serviceUrl + apiUrl;
	}

	@Test(priority = 1)
	public void getAPITestWithOutHeaders() throws IOException, Exception {
		restClient = new RestClient();
		closebaleHttpResponse = restClient.get(url);

		// a.Status code
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code  ----->" + statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// b. Json String
		String resposeString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");

		JSONObject responseJson = new JSONObject(resposeString);
		System.out.println("Response JSON from API---->" + responseJson);

		// Single value assertion for JSON object

		// 1.per_page
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of per page is--->" + perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);

		// 2.total
		String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("Value of total is--->" + totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);

		// 3.get the value from JSON Array
		String lastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");
		String email = TestUtil.getValueByJPath(responseJson, "/data[0]/email");

		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);
		System.out.println(email);

		// c. All Headers
		Header[] headersArray = closebaleHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array --->" + allHeaders);
	}

	
	@Test(priority = 2)
	public void getAPITestWithHeaders() throws IOException, Exception {
		restClient = new RestClient();
		closebaleHttpResponse = restClient.get(url);

		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
	//	headerMap.put("username", "test@amazon.com");
	//	headerMap.put("password", "test213");
	//	headerMap.put("Auth Token", "12345");

		// a.Status code
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code  ----->" + statusCode);

		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// b. Json String
		String resposeString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");

		JSONObject responseJson = new JSONObject(resposeString);
		System.out.println("Response JSON from API---->" + responseJson);

		// Single value assertion
		// per_page
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of per page is--->" + perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);

		// total
		String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("Value of total is--->" + totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);

		// get the value from JSON Array
		String lastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");

		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);

		// c. All Headers
		Header[] headersArray = closebaleHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array --->" + allHeaders);
	}
}
