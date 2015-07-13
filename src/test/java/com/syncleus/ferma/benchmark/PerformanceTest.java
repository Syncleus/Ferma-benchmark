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

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

public class PerformanceTest {

    private static final int ITERATIONS = 2_000_000;

    private static final MetricRegistry METRICS;
    private static final Timer FERMA_TIMER;
    private static final Timer TOTOROM_TIMER;
    private static final Timer FRAMES_TIMER;
    private static final Timer BLUEPRINTS_TIMER;
    private static final Timer TINKERPOP3_TIMER;
    private static final Timer GREMLIN_TIMER;
    private static final Timer PEAPOD_TIMER;

    private static TinkerGraph godGraph;

    private static org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph godGraph3;

    static {
        METRICS = new MetricRegistry();
        FERMA_TIMER = METRICS.timer("ferma");
        TOTOROM_TIMER = METRICS.timer("totorom");
        FRAMES_TIMER = METRICS.timer("frames");
        BLUEPRINTS_TIMER = METRICS.timer("blueprints");
        TINKERPOP3_TIMER = METRICS.timer("tinkerpop3");
        GREMLIN_TIMER = METRICS.timer("gremlin");
        PEAPOD_TIMER = METRICS.timer("peapod");
    }

    @BeforeClass
    public static void initAndWarmup() {
        godGraph = new TinkerGraph();
        GodGraphLoader.load(godGraph);

        godGraph3 = org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph.open();
        GodGraph3Loader.load(godGraph3);

        for (int i = 0; i < ITERATIONS; i++) {
            Assert.assertNotNull(godGraph.getVertices("name", "saturn").iterator().next());
            Assert.assertNotNull(godGraph3.traversal().V().has("name", "saturn").next());
        }
    }

    @Test
    public void testGetFramedVerticesTyped() {
        //Ferma test
        final com.syncleus.ferma.FramedGraph fermaGraph = new com.syncleus.ferma.DelegatingFramedGraph<Graph>(godGraph, true, false);
        com.codahale.metrics.Timer.Context time = FERMA_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            final Iterable<? extends ConcreteFermaGod> gods = fermaGraph.getFramedVertices("name", "saturn", ConcreteFermaGod.class);
            Iterator<? extends ConcreteFermaGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long fermaElapsed = time.stop();

        //Totorom test
        final org.jglue.totorom.FramedGraph totoromGraph = new org.jglue.totorom.FramedGraph(godGraph, org.jglue.totorom.FrameFactory.Default, org.jglue.totorom.TypeResolver.Java);
        time = TOTOROM_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            Iterable<TotoromGod> gods = totoromGraph.V().has("name", "saturn").frame(TotoromGod.class);
            Iterator<TotoromGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long totoromElapsed = time.stop();

        //Frames test
        com.tinkerpop.frames.FramedGraphFactory factory = new com.tinkerpop.frames.FramedGraphFactory(new com.tinkerpop.frames.modules.gremlingroovy.GremlinGroovyModule());
        final com.tinkerpop.frames.FramedGraph<Graph> framesGraph = factory.create(godGraph);
        time = FRAMES_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            Iterable<FramesGod> gods = framesGraph.getVertices("name", "saturn", FramesGod.class);
            Iterator<FramesGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long framesElapsed = time.stop();

        //Gremlin test
        time = GREMLIN_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            Iterable<Vertex> gods = new com.tinkerpop.gremlin.java.GremlinPipeline<>(godGraph).V("name", "saturn");
            Iterator<Vertex> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long gremlinElapsed = time.stop();

        //Peapod test
        final peapod.FramedGraph peapodGraph = new peapod.FramedGraph(godGraph3, PeapodGod.class.getPackage());
        time = PEAPOD_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            Assert.assertTrue(peapodGraph.V(PeapodGod.class).has("name", "saturn").hasNext());
        }
        final long peapodElapsed = time.stop();

        System.out.println();
        System.out.println("=== testGetFramedVerticesTyped ===");
        System.out.println("blueprints comparison: Not Capable");
        System.out.println("tinkerpop3 comparison: Not Capable");
        System.out.println("totorom comparison: " + ((double) totoromElapsed) / ((double) fermaElapsed) * 100.0 + "%");
        System.out.println("frames comparison: " + ((double) framesElapsed) / ((double) fermaElapsed) * 100.0 + "%");
        System.out.println("gremlin comparison: Not Capable");
        System.out.println("peapod comparison: " + ((double) peapodElapsed) / ((double) fermaElapsed) * 100.0 + "%");
    }

    @Test
    public void testGetFramedVerticesUntyped() {
        //Blueprints test
        com.codahale.metrics.Timer.Context time = BLUEPRINTS_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            Iterable<Vertex> gods = godGraph.getVertices("name", "saturn");
            Iterator<Vertex> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long blueprintsElapsed = time.stop();

        //Tinkerpop 3 test
        time = TINKERPOP3_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            GraphTraversal<org.apache.tinkerpop.gremlin.structure.Vertex, org.apache.tinkerpop.gremlin.structure.Vertex> it = godGraph3.traversal().V().hasLabel("god").has("name", "saturn");
            Assert.assertTrue(it.hasNext());
        }
        final long tinkerpop3Elapsed = time.stop();

        //Ferma test
        final com.syncleus.ferma.FramedGraph fermaGraph = new com.syncleus.ferma.DelegatingFramedGraph<Graph>(godGraph, false, false);
        time = FERMA_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            final Iterable<? extends ConcreteFermaGod> gods = fermaGraph.getFramedVertices("name", "saturn", ConcreteFermaGod.class);
            Iterator<? extends ConcreteFermaGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long fermaElapsed = time.stop();

        //Totorom test
        final org.jglue.totorom.FramedGraph totoromGraph = new org.jglue.totorom.FramedGraph(godGraph, org.jglue.totorom.FrameFactory.Default, org.jglue.totorom.TypeResolver.Untyped);
        time = TOTOROM_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            Iterable<TotoromGod> gods = totoromGraph.V().has("name", "saturn").frame(TotoromGod.class);
            Iterator<TotoromGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long totoromElapsed = time.stop();

        //Gremlin test
        time = GREMLIN_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            Iterable<Vertex> gods = new com.tinkerpop.gremlin.java.GremlinPipeline<>(godGraph).V("name", "saturn");
            Iterator<Vertex> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long gremlinElapsed = time.stop();

        System.out.println();
        System.out.println("=== testGetFramedVerticesUntyped ===");
        System.out.println("blueprints comparison: " + ((double) blueprintsElapsed) / ((double) fermaElapsed) * 100.0 + "%");
        System.out.println("tinkerpop3 comparison: " + ((double) tinkerpop3Elapsed) / ((double) fermaElapsed) * 100.0 + "%");
        System.out.println("totorom comparison: " + ((double) totoromElapsed) / ((double) fermaElapsed) * 100.0 + "%");
        System.out.println("frames comparison: Not capable");
        System.out.println("gremlin comparison: " + ((double) gremlinElapsed) / ((double) fermaElapsed) * 100.0 + "%");
        System.out.println("peapod comparison: Not Capable");
    }

    @Test
    public void testGetFramedVerticesAndNextTyped() {
        //Ferma test
        final com.syncleus.ferma.FramedGraph fermaGraph = new com.syncleus.ferma.DelegatingFramedGraph<Graph>(godGraph, true, false);
        com.codahale.metrics.Timer.Context time = FERMA_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            final Iterable<? extends ConcreteFermaGod> gods = fermaGraph.getFramedVertices("name", "saturn", ConcreteFermaGod.class);
            Iterator<? extends ConcreteFermaGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            godsIterator.next();
        }
        final long fermaElapsed = time.stop();

        //Totorom test
        final org.jglue.totorom.FramedGraph totoromGraph = new org.jglue.totorom.FramedGraph(godGraph, org.jglue.totorom.FrameFactory.Default, org.jglue.totorom.TypeResolver.Java);
        time = TOTOROM_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            Iterable<TotoromGod> gods = totoromGraph.V().has("name", "saturn").frame(TotoromGod.class);
            Iterator<TotoromGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            godsIterator.next();
        }
        final long totoromElapsed = time.stop();

        //Gremlin test
        com.tinkerpop.frames.FramedGraphFactory factory = new com.tinkerpop.frames.FramedGraphFactory(new com.tinkerpop.frames.modules.gremlingroovy.GremlinGroovyModule());
        final com.tinkerpop.frames.FramedGraph<Graph> framesGraph = factory.create(godGraph);
        time = FRAMES_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            Iterable<FramesGod> gods = framesGraph.getVertices("name", "saturn", FramesGod.class);
            Iterator<FramesGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            godsIterator.next();
        }
        final long framesElapsed = time.stop();

        //Gremlin test
        time = GREMLIN_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            Iterable<Vertex> gods = new com.tinkerpop.gremlin.java.GremlinPipeline<>(godGraph).V("name", "saturn");
            Iterator<Vertex> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            godsIterator.next();
        }
        final long gremlinElapsed = time.stop();

        //Peapod test
        final peapod.FramedGraph peapodGraph = new peapod.FramedGraph(godGraph3, PeapodGod.class.getPackage());
        time = PEAPOD_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            Iterator<PeapodGod> it = peapodGraph.V(PeapodGod.class).has("name", "saturn");
            Assert.assertTrue(it.hasNext());
            it.next();
        }
        final long peapodElapsed = time.stop();

        System.out.println();
        System.out.println("=== testGetFramedVerticesAndNextTyped ===");
        System.out.println("blueprints comparison: Not Capable");
        System.out.println("tinkerpop3 comparison: Not Capable");
        System.out.println("totorom comparison: " + ((double) totoromElapsed) / ((double) fermaElapsed) * 100.0 + "%");
        System.out.println("frames comparison: " + ((double) framesElapsed) / ((double) fermaElapsed) * 100.0 + "%");
        System.out.println("gremlin comparison: " + ((double) gremlinElapsed) / ((double) fermaElapsed) * 100.0 + "%");
        System.out.println("peapod comparison: " + ((double) peapodElapsed) / ((double) fermaElapsed) * 100.0 + "%");
    }

    @Test
    public void testGetFramedVerticesAndNextUntyped() {
        //Blueprints test
        com.codahale.metrics.Timer.Context time = BLUEPRINTS_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            Iterable<Vertex> gods = godGraph.getVertices("name", "saturn");
            Iterator<Vertex> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            godsIterator.next();
        }
        final long blueprintsElapsed = time.stop();

        //Tinkerpop 3 test
        time = TINKERPOP3_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            Iterator<org.apache.tinkerpop.gremlin.structure.Vertex> it = godGraph3.traversal().V().has("name", "saturn");
            Assert.assertTrue(it.hasNext());
            it.next();
        }
        final long tinkerpop3Elapsed = time.stop();

        //Ferma test
        final com.syncleus.ferma.FramedGraph fermaGraph = new com.syncleus.ferma.DelegatingFramedGraph<Graph>(godGraph, false, false);
        time = FERMA_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            final Iterable<? extends ConcreteFermaGod> gods = fermaGraph.getFramedVertices("name", "saturn", ConcreteFermaGod.class);
            Iterator<? extends ConcreteFermaGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            godsIterator.next();
        }
        final long fermaElapsed = time.stop();

        //Totorom test
        final org.jglue.totorom.FramedGraph totoromGraph = new org.jglue.totorom.FramedGraph(godGraph, org.jglue.totorom.FrameFactory.Default, org.jglue.totorom.TypeResolver.Untyped);
        time = TOTOROM_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            Iterable<TotoromGod> gods = totoromGraph.V().has("name", "saturn").frame(TotoromGod.class);
            Iterator<TotoromGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            godsIterator.next();
        }
        final long totoromElapsed = time.stop();

        //Gremlin test
        time = GREMLIN_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            Iterable<Vertex> gods = new com.tinkerpop.gremlin.java.GremlinPipeline<>(godGraph).V("name", "saturn");
            Iterator<Vertex> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            godsIterator.next();
        }
        final long gremlinElapsed = time.stop();

        System.out.println();
        System.out.println("=== testGetFramedVerticesAndNextUntyped ===");
        System.out.println("blueprints comparison: " + ((double) blueprintsElapsed) / ((double) fermaElapsed) * 100.0 + "%");
        System.out.println("tinkerpop3 comparison: " + ((double) tinkerpop3Elapsed) / ((double) fermaElapsed) * 100.0 + "%");
        System.out.println("totorom comparison: " + ((double) totoromElapsed) / ((double) fermaElapsed) * 100.0 + "%");
        System.out.println("frames comparison: Not capable");
        System.out.println("gremlin comparison: " + ((double) gremlinElapsed) / ((double) fermaElapsed) * 100.0 + "%");
        System.out.println("peapod comparison: Not Capable");
    }

    @Test
    public void testGetAnnotatedAdjacencies() {
        //Ferma test
        final com.syncleus.ferma.FramedGraph fermaGraph = new com.syncleus.ferma.DelegatingFramedGraph<Graph>(godGraph, true, true);
        Timer.Context time = FERMA_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            final Iterable<? extends FermaGod> gods = fermaGraph.getFramedVertices("name", "saturn", FermaGod.class);
            Iterator<? extends FermaGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            final FermaGod father = godsIterator.next();
            Iterable<? extends FermaGod> children = father.getSons(FermaGod.class);
            for (final FermaGod child : children)
                Assert.assertTrue(child.getParents().iterator().next().getName().equals("saturn"));
        }
        final long fermaElapsed = time.stop();

        //Frames test
        com.tinkerpop.frames.FramedGraphFactory factory = new com.tinkerpop.frames.FramedGraphFactory(new com.tinkerpop.frames.modules.gremlingroovy.GremlinGroovyModule());
        final com.tinkerpop.frames.FramedGraph<Graph> framesGraph = factory.create(godGraph);
        time = FRAMES_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            Iterable<FramesGod> gods = framesGraph.getVertices("name", "saturn", FramesGod.class);
            Iterator<FramesGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            final FramesGod father = godsIterator.next();
            Iterable<? extends FramesGod> children = father.getSons();
            for (final FramesGod child : children)
                Assert.assertTrue(child.getParents().iterator().next().getName().equals("saturn"));
        }
        final long framesElapsed = time.stop();

        //Peapod test
        final peapod.FramedGraph peapodGraph = new peapod.FramedGraph(godGraph3, PeapodGod.class.getPackage());
        time = PEAPOD_TIMER.time();
        for (int i = 0; i < ITERATIONS; i++) {
            Iterator<PeapodGod> it = peapodGraph.V(PeapodGod.class).has("name", "saturn");
            Assert.assertTrue(it.hasNext());

            final PeapodGod father = it.next();
            List<PeapodGod> children = father.getSons();
            for (final PeapodGod child : children)
                Assert.assertTrue(child.getParents().iterator().next().getName().equals("saturn"));

        }
        final long peapodElapsed = time.stop();

        System.out.println();
        System.out.println("=== testGetAnnotatedAdjacencies ===");
        System.out.println("blueprints comparison: Not capable");
        System.out.println("tinkerpop3 comparison: Not Capable");
        System.out.println("totorom comparison: Not capable");
        System.out.println("frames comparison: " + ((double) framesElapsed) / ((double) fermaElapsed) * 100.0 + "%");
        System.out.println("gremlin comparison: Not capable");
        System.out.println("peapod comparison: " + ((double) peapodElapsed) / ((double) fermaElapsed) * 100.0 + "%");
    }
}
