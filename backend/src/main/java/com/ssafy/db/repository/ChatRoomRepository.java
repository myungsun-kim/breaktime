package com.ssafy.db.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

import com.ssafy.db.entity.ChatRoom;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class ChatRoomRepository {
    private final Map<String, ChatRoom> chatRoomMap;
    @Getter
    private final Collection<ChatRoom> chatRooms;

    public ChatRoomRepository() {
        chatRoomMap = Collections.unmodifiableMap(
                Stream.of(ChatRoom.create("1번방"), ChatRoom.create("2번방"), ChatRoom.create("3번방"))
                      .collect(Collectors.toMap(ChatRoom::getId, Function.identity())));

        chatRooms = Collections.unmodifiableCollection(chatRoomMap.values());
    }

    public ChatRoom getChatRoom(String id) {
        return chatRoomMap.get(id);
    }

    public void remove(WebSocketSession session) {
        this.chatRooms.parallelStream().forEach(chatRoom -> chatRoom.remove(session));
    }
}
//private Map<String, ChatRoom> chatRoomMap;
//@PostConstruct
//private void init(){
//  chatRoomMap = new LinkedHashMap<>();
//}
//
//public ChatRoom createChatRoom(String name){
//  ChatRoom room = ChatRoom.create(name);
//  chatRoomMap.put(room.getId(), room);
//
//  return room;
//}
