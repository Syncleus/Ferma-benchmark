/******************************************************************************
 * *
 * Copyright: (c) Syncleus, Inc.                                              *
 * *
 * You may redistribute and modify this source code under the terms and       *
 * conditions of the Open Source Community License - Type C version 1.0       *
 * or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 * There should be a copy of the license included with this file. If a copy   *
 * of the license is not included you are granted no right to distribute or   *
 * otherwise use this file except through a legal and valid license. You      *
 * should also contact Syncleus, Inc. at the information below if you cannot  *
 * find a license:                                                            *
 * *
 * Syncleus, Inc.                                                             *
 * 2604 South 12th Street                                                     *
 * Philadelphia, PA 19148                                                     *
 * *
 ******************************************************************************/
package com.syncleus.ferma.benchmark;

import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;


/**
 * Example Graph factory that creates a graph based on roman mythology.
 */
public class GodGraph3Loader {

    public static void load(final TinkerGraph graph) {

        // vertices
        graph.createIndex("name", Vertex.class);

        Vertex saturn = graph.addVertex(T.label, "god", "name", "saturn", "age", 10000, "type", "titan", "implementation_type", FermaGod.class.getName());

        Vertex sky = graph.addVertex(T.label, "god", "name", "sky", "type", "location", "other", "more useless info");

        Vertex sea = graph.addVertex(T.label, "god", "name", "sea", "type", "location");

        Vertex jupiter = graph.addVertex(T.label, "god", "name", "jupiter", "age", 5000, "type", "god", "implementation_type", FermaGod.class.getName());

        Vertex neptune = graph.addVertex(T.label, "god", "name", "neptune", "age", 4500, "type", "god", "implementation_type", FermaGod.class.getName());

        Vertex hercules = graph.addVertex(T.label, "god", "name", "hercules", "age", 30, "type", "demigod", "implementation_type", FermaGodExtended.class.getName());

        Vertex alcmene = graph.addVertex(T.label, "god", "name", "alcmene", "age", 45, "type", "human", "implementation_type", FermaGod.class.getName());

        Vertex pluto = graph.addVertex(T.label, "god", "name", "pluto", "age", 4000, "type", "god", "implementation_type", FermaGod.class.getName());

        Vertex nemean = graph.addVertex(T.label, "god", "name", "nemean", "type", "monster", "implementation_type", FermaGod.class.getName());

        Vertex hydra = graph.addVertex(T.label, "god", "name", "hydra", "type", "monster", "implementation_type", FermaGod.class.getName());

        Vertex cerberus = graph.addVertex(T.label, "god", "name", "cerberus", "type", "monster", "implementation_type", FermaGod.class.getName());

        Vertex tartarus = graph.addVertex(T.label, "god", "name", "tartarus", "type", "location", "implementation_type", FermaGod.class.getName());

        // edges

        jupiter.addEdge("father", saturn, "implementation_type", FatherEdge.class.getName());
        jupiter.addEdge("lives", sky, "reason", "loves fresh breezes");
        jupiter.addEdge("brother", neptune);
        jupiter.addEdge("brother", pluto);

        neptune.addEdge("father", saturn, "implementation_type", FatherEdge.class.getName());
        neptune.addEdge("lives", sea, "reason", "loves waves");
        neptune.addEdge("brother", jupiter);
        neptune.addEdge("brother", pluto);

        hercules.addEdge("father", jupiter, "implementation_type", FatherEdgeExtended.class.getName());
        hercules.addEdge("lives", sky, "reason", "loves heights");
        hercules.addEdge("battled", nemean, "time", 1);
        hercules.addEdge("battled", hydra, "time", 2);
        hercules.addEdge("battled", cerberus, "time", 12);

        pluto.addEdge("father", saturn, "implementation_type", FatherEdge.class.getName());
        pluto.addEdge("brother", jupiter);
        pluto.addEdge("brother", neptune);
        pluto.addEdge("lives", tartarus, "reason", "no fear of death");
        pluto.addEdge("pet", cerberus);

        cerberus.addEdge("lives", tartarus);
        cerberus.addEdge("battled", alcmene, "time", 5);

        // commit the transaction to disk
        if (graph.features().graph().supportsTransactions())
            graph.tx().commit();
    }
}