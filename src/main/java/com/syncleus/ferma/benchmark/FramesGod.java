/******************************************************************************
 *                                                                             *
 *  Copyright: (c) Syncleus, Inc.                                              *
 *                                                                             *
 *  You may redistribute and modify this source code under the terms and       *
 *  conditions of the Open Source Community License - Type C version 1.0       *
 *  or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 *  There should be a copy of the license included with this file. If a copy   *
 *  of the license is not included you are granted no right to distribute or   *
 *  otherwise use this file except through a legal and valid license. You      *
 *  should also contact Syncleus, Inc. at the information below if you cannot  *
 *  find a license:                                                            *
 *                                                                             *
 *  Syncleus, Inc.                                                             *
 *  2604 South 12th Street                                                     *
 *  Philadelphia, PA 19148                                                     *
 *                                                                             *
 ******************************************************************************/
package com.syncleus.ferma.benchmark;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.frames.*;

public interface FramesGod {
    @Property("name")
    String getName();

    @Property("name")
    void setName(String newName);

    @Property("name")
    void removeName();

    @Property("age")
    Integer getAge();

    @Property("type")
    String getType();

    @Adjacency(label="father", direction= Direction.IN)
    Iterable<? extends FramesGod> getSons();

    @Adjacency(label="father", direction= Direction.IN)
    FramesGod getSon();

    @Adjacency(label="father", direction= Direction.IN)
    <N extends FramesGod> Iterable<? extends N> getSons(Class<? extends N> type);

    @Adjacency(label="father", direction= Direction.OUT)
    Iterable<FramesGod> getParents();

    @Adjacency(label="father", direction= Direction.IN)
    <N extends FramesGod> N addSon(Class<? extends N> type);

    @Adjacency(label="father", direction= Direction.IN)
    FramesGod addSon(FermaGod son);

    @Adjacency(label="father", direction= Direction.IN)
    VertexFrame addSon();

    @Adjacency(label="father", direction= Direction.IN)
    void setSons(Iterable<? extends FramesGod> vertexSet);

    @Adjacency(label="father", direction= Direction.IN)
    void removeSon(FramesGod son);
}
