package com.ssafy.db.entity;

import java.awt.TrayIcon.MessageType;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
	@Id @GeneratedValue()
	@Column(name = "sequence")
	private Long sequence;
	
    private Long conferenceSeq;
	
	private String Writer;
	private String message;
	private LocalDateTime produceTime;
	private MessageType type;
}
