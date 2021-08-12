package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConference is a Querydsl query type for Conference
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConference extends EntityPathBase<Conference> {

    private static final long serialVersionUID = -625543586L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConference conference = new QConference("conference");

    public final QConferenceCategory conferenceCategory;

    public final StringPath description = createString("description");

    public final StringPath name = createString("name");

    public final StringPath owner = createString("owner");

    public final StringPath ownerNick = createString("ownerNick");

    public final NumberPath<Integer> participantLimit = createNumber("participantLimit", Integer.class);

    public final StringPath password = createString("password");

    public final DateTimePath<java.time.LocalDateTime> produceTime = createDateTime("produceTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> sequence = createNumber("sequence", Long.class);

    public QConference(String variable) {
        this(Conference.class, forVariable(variable), INITS);
    }

    public QConference(Path<? extends Conference> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConference(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConference(PathMetadata metadata, PathInits inits) {
        this(Conference.class, metadata, inits);
    }

    public QConference(Class<? extends Conference> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.conferenceCategory = inits.isInitialized("conferenceCategory") ? new QConferenceCategory(forProperty("conferenceCategory")) : null;
    }

}

