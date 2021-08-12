package com.ssafy.config;

import java.nio.charset.Charset;

import org.kurento.client.KurentoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.ssafy.kurento.CallHandler;
import com.ssafy.kurento.RoomManager;
import com.ssafy.kurento.UserRegistry;

//KurentoClient는 이 클래스에서 Spring Bean으로 인스턴스화되고, 
//이 빈은 애플리케이션 미디어 기능을 추가하는 데 사용되는 Kurento Media Server의 위치를 지정해야 함
@EnableWebSocket
public class KurentoConfig implements WebSocketConfigurer{

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

	@Bean
	public UserRegistry registry() {
	  return new UserRegistry();
	}
	
	@Bean
	public RoomManager roomManager() {
	  return new RoomManager();
	}
	
	@Bean
	public CallHandler groupCallHandler() {
	  return new CallHandler();
	}
	
	@Bean
	public KurentoClient kurentoClient() {
	  return KurentoClient.create();
	}
	
	@Bean
	public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
	  ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
	  container.setMaxTextMessageBufferSize(32768);
	  return container;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(groupCallHandler(), "/groupcall");
	}
	
}
