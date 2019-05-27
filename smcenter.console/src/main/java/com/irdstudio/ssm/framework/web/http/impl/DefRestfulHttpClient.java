package com.irdstudio.ssm.framework.web.http.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.Cookie;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.irdstudio.ssm.framework.web.http.HttpClient;


/**
 * 默认的restful url调用
 * @param <I> 入参对象
 * @param <O> 出参对象
 * @param <P> uri参数对象
 * 
 * @author Cytus_
 * @since 2018年5月16日 下午4:49:28
 * @version 1.0
 *
 */
public class DefRestfulHttpClient<I, O> implements HttpClient<I, O> {
	
	HttpHeaders headers = new HttpHeaders();

	public O httpCall(String url, HttpMethod method, I inArgs, Class<O> clazz, Object... params) throws Exception {
		
		RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());
		
		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
		Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
		while (iterator.hasNext()) {
			HttpMessageConverter<?> converter = iterator.next();
			if (converter instanceof StringHttpMessageConverter) {
				iterator.remove();
			}
		}
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("utf-8")));
		
		//headers.put(HttpHeaders.COOKIE, null);//设置cookie
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<I> entity = new HttpEntity<I>(inArgs, headers);
		
		ResponseEntity<O> responseEntity = restTemplate.exchange(url, method, entity, clazz, params);
		HttpStatus status = responseEntity.getStatusCode();
		if (status.is2xxSuccessful())
			return responseEntity.getBody();
		else 
			return null;
	}

	public void setHeader(String key, String value) {
		// TODO Auto-generated method stub
		
	}

	public void setCookie(Collection<Cookie> cookies) {
		if (Objects.nonNull(cookies)) {
			List<String> cookieList = new ArrayList<String>();
			cookies.forEach(cookie -> cookieList.add(cookie.getName() +"="+ cookie.getValue()));
			headers.put(HttpHeaders.COOKIE, cookieList);
		}
	}

}
