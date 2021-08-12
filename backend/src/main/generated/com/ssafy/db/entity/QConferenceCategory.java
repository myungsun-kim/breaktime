package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QConferenceCategory is a Querydsl query type for ConferenceCategory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConferenceCategory extends EntityPathBase<ConferenceCategory> {

    private static final long serialVersionUID = -10561156L;

    public static final QConferenceCategory conferenceCategory = new QConferenceCategory("conferenceCategory");

    public final StringPath atmosphere = createString("atmosphere");

    public final StringPath background = createString("background");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public QConferenceCategory(String variable) {
        super(ConferenceCategory.class, forVariable(variable));
    }

    public QConferenceCategory(Path<? extends ConferenceCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConferenceCategory(PathMetadata metadata) {
        super(ConferenceCategory.class, metadata);
    }

}

