package com.ssafy;

import java.nio.charset.Charset;

import org.kurento.client.KurentoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

@SpringBootApplication
public class GroupCallApplication{
	public static void main(String[] args) {
        SpringApplication.run(GroupCallApplication.class, args);
    }

}
