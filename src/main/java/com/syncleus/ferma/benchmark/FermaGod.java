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

import com.syncleus.ferma.EdgeFrame;
import com.syncleus.ferma.VertexFrame;
import com.syncleus.ferma.annotations.Adjacency;
import com.syncleus.ferma.annotations.Incidence;
import com.syncleus.ferma.annotations.Property;
import com.tinkerpop.blueprints.Direction;

public interface FermaGod extends VertexFrame {
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
    Iterable<? extends FermaGod> getSons();

    @Adjacency(label="father", direction= Direction.IN)
    FermaGod getSon();

    @Adjacency(label="father", direction= Direction.IN)
    <N extends FermaGod> Iterable<? extends N> getSons(Class<? extends N> type);

    @Adjacency(label="father", direction= Direction.OUT)
    <N extends FermaGod> Iterable<? extends N> getParents();

    @Adjacency(label="father", direction= Direction.IN)
    <N extends FermaGod> N getSon(Class<? extends N> type);

    @Adjacency(label="father", direction= Direction.IN)
    <N extends FermaGod> N addSon(Class<? extends N> type);

    @Adjacency(label="father", direction= Direction.IN)
    <N extends FermaGod> N addSon(Class<? extends N> type, Class<? extends FatherEdge> edge);

    @Adjacency(label="father", direction= Direction.IN)
    FermaGod addSon(FermaGod son);

    @Adjacency(label="father", direction= Direction.IN)
    VertexFrame addSon();

    @Adjacency(label="father", direction= Direction.IN)
    FermaGod addSon(FermaGod son, Class<? extends FatherEdge> edge);

    @Adjacency(label="father", direction= Direction.IN)
    void setSons(Iterable<? extends FermaGod> vertexSet);

    @Adjacency(label="father", direction= Direction.IN)
    void removeSon(FermaGod son);

    @Incidence(label="father", direction= Direction.IN)
    Iterable<? extends EdgeFrame> getSonEdges();

    @Incidence(label="father", direction= Direction.IN)
    <N extends FatherEdge> Iterable<? extends N> getSonEdges(Class<? extends N> type);

    @Incidence(label="father", direction= Direction.IN)
    EdgeFrame getSonEdge();

    @Incidence(label="father", direction= Direction.IN)
    <N extends FatherEdge> N getSonEdge(Class<? extends N> type);

    @Incidence(label="father", direction= Direction.IN)
    void removeSonEdge(FatherEdge edge);
}
