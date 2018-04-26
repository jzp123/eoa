package com.eoa.common.cookie;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ����Cookie������
 * @author by_jzp
 * @email 61jzp@163.com
 * @date 2016��4��24�� ����4:04:55
 * @version 1.0
 */
public final class CookieUtils {
	
	/** ����Cookie���Ƶľ�̬�ڲ��� */
	public static class CookieName{
		/** ��������Cookie���û���¼Ʊ�ݵ����� */
		public static final String TAOTAO_TICKET = "taotao_ticket";
	}
	
	
	/**
	 * ����Cookie�����ƻ�ȡָ����Cookie
	 * @param request �������
	 * @param cookieName cookie������
	 * @return Cookie
	 */
	public static Cookie getCookie(HttpServletRequest request, String cookieName){
		/** ��ȡ���е�Cookie */
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0){
        	for (Cookie cookie : cookies){
        		if (cookie.getName().equals(cookieName)){
        			return cookie;
        		}
        	}
        }
        return null;
	}
    /**
     * ����CookieName��ȡָ����Cookieֵ
     * @param request �������
     * @param cookieName cookie������
     * @param encoded �Ƿ����
     * @return Cookie��ֵ
     */
    public static String getCookieValue(HttpServletRequest request, 
    				String cookieName, boolean encoded) {
    	/** ��ȡָ����Cookie */
    	Cookie cookie = getCookie(request, cookieName);
        String cookieValue = null;
        try {
	        if (cookie != null) {
     			if (encoded){
     				cookieValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
     			}else{
     				cookieValue = cookie.getValue();
     			}
	        }
        } catch (Exception e) {
         	e.printStackTrace();
        }
        return cookieValue;
    }
 
    /**
     * ����Cookie������ɾ��ָ����Cookie
     * @param request �������
     * @param response ��Ӧ����
     * @param cookieName cookie����
     */
    public static void deleteCookie(HttpServletRequest request, 
    				HttpServletResponse response, String cookieName) {
    	setCookie(request, response, cookieName, null, 0, false);
    }
    
    /**
     * ����Cookie
     * @param request �������
     * @param response ��Ӧ����
     * @param cookieName cookie������
     * @param cookieValue cookie��ֵ
     * @param maxAge ���Cookie�������ʱ��(�������)
     * @param encoded �Ƿ����
     */
    public static void setCookie(HttpServletRequest request, 
								 HttpServletResponse response,
								 String cookieName, String cookieValue, 
								 int maxAge, boolean encoded) {
    	try {
        	/** ��Cookie��ֵ�����ж� */
            if (cookieValue == null) {
                cookieValue = "";
            }
            if (encoded) {
                cookieValue = URLEncoder.encode(cookieValue, "utf-8");
            }
            Cookie cookie = getCookie(request, cookieName);
            if (cookie == null){
            	cookie = new Cookie(cookieName, cookieValue);
            }
            /** ����Cookie��ֵ */
            cookie.setValue(cookieValue);
            /** ���������ʱ�� */
            cookie.setMaxAge(maxAge);
            if (null != request){
            	/** ����Cookie���Կ������������ .taotao.com */
                cookie.setDomain(getDomainName(request));
            }
            /** ���÷���·�� */
            cookie.setPath("/");
            /** ��ӵ��û������ */
            response.addCookie(cookie);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    /**
     * ��ȡ������׺���֣���������Cookie����
     * @param request �������
     * @return ������׺����
     */
    private static String getDomainName(HttpServletRequest request) {
    	/** �������� */
        String domainName = "";
        /** ��ȡ����URL����ת����Сд��ĸ */
        String requestURL = request.getRequestURL().toString().toLowerCase();
        /** �ж�����URL */
        if (requestURL != null && !"".equals(requestURL)) {
        	/** http://sso.taotao.com/x/x ������URL��ǰ׺httpЭ���滻�� --> sso.taotao.com/x/x */
        	requestURL = requestURL.replaceFirst("http://", "")
        						   .replaceFirst("https://", "");
            /** sso.taotao.com/x/x ��ȡURL�е�һ��/��λ�������� */
            int end = requestURL.indexOf("/");
            /** ��ȡ�õ���������: sso.taotao.com */
            requestURL = requestURL.substring(0, end);
            /** �ַ����ָ������� */
            String[] domains = requestURL.split("\\.");
            /** ��ȡ���鳤�� */
            int len = domains.length;
            /** �жϳ��� */
            if (len > 3) {
                // www.xxx.com.cn
                domainName = "." + domains[len - 3] + "." 
                				 + domains[len - 2] + "." 
                				 + domains[len - 1];
            } else if (len <= 3 && len > 1) {
                // xxx.com or xxx.cn
                domainName = "." + domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = requestURL;
            }
        } 
        return domainName;
    }
}