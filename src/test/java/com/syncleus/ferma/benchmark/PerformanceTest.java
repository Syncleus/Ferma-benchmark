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

import com.codahale.metrics.*;
import com.codahale.metrics.Timer;
import com.syncleus.ferma.DefaultClassInitializer;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class PerformanceTest {
    private static final Set<Class<?>> TEST_TYPES = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{FermaGod.class, FatherEdge.class, FermaGodExtended.class, FermaGodAlternative.class}));
    private static final MetricRegistry METRICS;
    private static final com.codahale.metrics.Timer FERMA_TIMER;
    private static final com.codahale.metrics.Timer TOTOROM_TIMER;
    private static final com.codahale.metrics.Timer FRAMES_TIMER;
    private static final com.codahale.metrics.Timer BLUEPRINTS_TIMER;
    private static final com.codahale.metrics.Timer GREMLIN_TIMER;

    static {
        METRICS = new MetricRegistry();
        FERMA_TIMER = METRICS.timer("ferma");
        TOTOROM_TIMER = METRICS.timer("totorom");
        FRAMES_TIMER = METRICS.timer("frames");
        BLUEPRINTS_TIMER = METRICS.timer("blueprints");
        GREMLIN_TIMER = METRICS.timer("gremlin");
    }

    @Test
    public void testGetFramedVerticiesTyped() {
        final int iterations = 2500000;

        final TinkerGraph godGraph = new TinkerGraph();
        GodGraphLoader.load(godGraph);

        //Blueprints test
        com.codahale.metrics.Timer.Context time = BLUEPRINTS_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            Iterable<Vertex> gods = godGraph.getVertices("name", "saturn");
            Iterator<Vertex> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long blueprintsElapsed = time.stop();

        //Ferma test
        final com.syncleus.ferma.FramedGraph fermaGraph = new com.syncleus.ferma.DelegatingFramedGraph(godGraph, true, false);
        time = FERMA_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            final Iterable<? extends ConcreteFermaGod> gods = fermaGraph.getFramedVertices("name", "saturn", ConcreteFermaGod.class);
            Iterator<? extends ConcreteFermaGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long fermaElapsed = time.stop();

        final org.jglue.totorom.FramedGraph totoromGraph = new org.jglue.totorom.FramedGraph(godGraph, org.jglue.totorom.FrameFactory.Default, org.jglue.totorom.TypeResolver.Java);
        time = TOTOROM_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            Iterable<TotoromGod> gods = totoromGraph.V().has("name", "saturn").frame(TotoromGod.class);
            Iterator<TotoromGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long totoromElapsed = time.stop();


        com.tinkerpop.frames.FramedGraphFactory factory = new com.tinkerpop.frames.FramedGraphFactory(new com.tinkerpop.frames.modules.gremlingroovy.GremlinGroovyModule());
        final com.tinkerpop.frames.FramedGraph framesGraph = factory.create(godGraph);
        time = FRAMES_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            Iterable<FramesGod> gods = framesGraph.getVertices("name", "saturn", FramesGod.class);
            Iterator<FramesGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long framesElapsed = time.stop();
        
        
        //Blueprints test
        time = GREMLIN_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            Iterable<Vertex> gods = new com.tinkerpop.gremlin.java.GremlinPipeline<>(godGraph).V("name", "saturn");
            Iterator<Vertex> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long gremlinElapsed = time.stop();


        System.out.println();
        System.out.println("=== testGetFramedVerticiesTyped ===");
        System.out.println("blueprints comparison: " + ((double)blueprintsElapsed) / ((double)fermaElapsed) * 100.0 + "%");
        System.out.println("totorom comparison: " + ((double)totoromElapsed) / ((double)fermaElapsed) * 100.0 + "%");
        System.out.println("frames comparison: " + ((double)framesElapsed) / ((double)fermaElapsed) * 100.0 + "%");
        System.out.println("gremlin comparison: " + ((double)gremlinElapsed) / ((double)fermaElapsed) * 100.0 + "%");
    }

    @Test
    public void testGetFramedVerticiesUntyped() {
        final int iterations = 2500000;

        final TinkerGraph godGraph = new TinkerGraph();
        GodGraphLoader.load(godGraph);

        //Blueprints test
        com.codahale.metrics.Timer.Context time = BLUEPRINTS_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            Iterable<Vertex> gods = godGraph.getVertices("name", "saturn");
            Iterator<Vertex> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long blueprintsElapsed = time.stop();

        //Ferma test
        final com.syncleus.ferma.FramedGraph fermaGraph = new com.syncleus.ferma.DelegatingFramedGraph(godGraph, false, false);
        time = FERMA_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            final Iterable<? extends ConcreteFermaGod> gods = fermaGraph.getFramedVertices("name", "saturn", ConcreteFermaGod.class);
            Iterator<? extends ConcreteFermaGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long fermaElapsed = time.stop();

        final org.jglue.totorom.FramedGraph totoromGraph = new org.jglue.totorom.FramedGraph(godGraph, org.jglue.totorom.FrameFactory.Default, org.jglue.totorom.TypeResolver.Untyped);
        time = TOTOROM_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            Iterable<TotoromGod> gods = totoromGraph.V().has("name", "saturn").frame(TotoromGod.class);
            Iterator<TotoromGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long totoromElapsed = time.stop();

        //Gremlin test
        time = GREMLIN_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            Iterable<Vertex> gods = new com.tinkerpop.gremlin.java.GremlinPipeline<>(godGraph).V("name", "saturn");
            Iterator<Vertex> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long gremlinElapsed = time.stop();
        
        
        System.out.println();
        System.out.println("=== testGetFramedVerticiesUntyped ===");
        System.out.println("blueprints comparison: " + ((double)blueprintsElapsed) / ((double)fermaElapsed) * 100.0 + "%");
        System.out.println("totorom comparison: " + ((double)totoromElapsed) / ((double)fermaElapsed) * 100.0 + "%");
        System.out.println("frames comparison: Not capable");
        System.out.println("gremlin comparison: " + ((double)gremlinElapsed) / ((double)fermaElapsed) * 100.0 + "%");
    }

    @Test
    public void testGetFramedVerticiesAndNextTyped() {
        final int iterations = 1000000;

        final TinkerGraph godGraph = new TinkerGraph();
        GodGraphLoader.load(godGraph);

        //Blueprints test
        com.codahale.metrics.Timer.Context time = BLUEPRINTS_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            Iterable<Vertex> gods = godGraph.getVertices("name", "saturn");
            Iterator<Vertex> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            godsIterator.next();
        }
        final long blueprintsElapsed = time.stop();

        //Ferma test
        final com.syncleus.ferma.FramedGraph fermaGraph = new com.syncleus.ferma.DelegatingFramedGraph(godGraph, true, false);
        time = FERMA_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            final Iterable<? extends ConcreteFermaGod> gods = fermaGraph.getFramedVertices("name", "saturn", ConcreteFermaGod.class);
            Iterator<? extends ConcreteFermaGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            godsIterator.next();
        }
        final long fermaElapsed = time.stop();

        final org.jglue.totorom.FramedGraph totoromGraph = new org.jglue.totorom.FramedGraph(godGraph, org.jglue.totorom.FrameFactory.Default, org.jglue.totorom.TypeResolver.Java);
        time = TOTOROM_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            Iterable<TotoromGod> gods = totoromGraph.V().has("name", "saturn").frame(TotoromGod.class);
            Iterator<TotoromGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            godsIterator.next();
        }
        final long totoromElapsed = time.stop();


        com.tinkerpop.frames.FramedGraphFactory factory = new com.tinkerpop.frames.FramedGraphFactory(new com.tinkerpop.frames.modules.gremlingroovy.GremlinGroovyModule());
        final com.tinkerpop.frames.FramedGraph framesGraph = factory.create(godGraph);
        time = FRAMES_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            Iterable<FramesGod> gods = framesGraph.getVertices("name", "saturn", FramesGod.class);
            Iterator<FramesGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            godsIterator.next();
        }
        final long framesElapsed = time.stop();

        //Gremlin test
        time = GREMLIN_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            Iterable<Vertex> gods = new com.tinkerpop.gremlin.java.GremlinPipeline<>(godGraph).V("name", "saturn");
            Iterator<Vertex> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            godsIterator.next();
        }
        final long gremlinElapsed = time.stop();
        
        System.out.println();
        System.out.println("=== testGetFramedVerticiesAndNextTyped ===");
        System.out.println("blueprints comparison: " + ((double)blueprintsElapsed) / ((double)fermaElapsed) * 100.0 + "%");
        System.out.println("totorom comparison: " + ((double)totoromElapsed) / ((double)fermaElapsed) * 100.0 + "%");
        System.out.println("frames comparison: " + ((double)framesElapsed) / ((double)fermaElapsed) * 100.0 + "%");
        System.out.println("gremlin comparison: " + ((double)gremlinElapsed) / ((double)fermaElapsed) * 100.0 + "%");
    }

    @Test
    public void testGetFramedVerticiesAndNextUntyped() {
        final int iterations = 5000000;

        final TinkerGraph godGraph = new TinkerGraph();
        GodGraphLoader.load(godGraph);

        //Blueprints test
        com.codahale.metrics.Timer.Context time = BLUEPRINTS_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            Iterable<Vertex> gods = godGraph.getVertices("name", "saturn");
            Iterator<Vertex> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            godsIterator.next();
        }
        final long blueprintsElapsed = time.stop();

        //Ferma test
        final com.syncleus.ferma.FramedGraph fermaGraph = new com.syncleus.ferma.DelegatingFramedGraph(godGraph, false, false);
        time = FERMA_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            final Iterable<? extends ConcreteFermaGod> gods = fermaGraph.getFramedVertices("name", "saturn", ConcreteFermaGod.class);
            Iterator<? extends ConcreteFermaGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            godsIterator.next();
        }
        final long fermaElapsed = time.stop();

        final org.jglue.totorom.FramedGraph totoromGraph = new org.jglue.totorom.FramedGraph(godGraph, org.jglue.totorom.FrameFactory.Default, org.jglue.totorom.TypeResolver.Untyped);
        time = TOTOROM_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            Iterable<TotoromGod> gods = totoromGraph.V().has("name", "saturn").frame(TotoromGod.class);
            Iterator<TotoromGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            godsIterator.next();
        }
        final long totoromElapsed = time.stop();
        
        //Gremlin test
        time = GREMLIN_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            Iterable<Vertex> gods = new com.tinkerpop.gremlin.java.GremlinPipeline<>(godGraph).V("name", "saturn");
            Iterator<Vertex> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            godsIterator.next();
        }
        final long gremlinElapsed = time.stop();

        System.out.println();
        System.out.println("=== testGetFramedVerticiesAndNextUntyped ===");
        System.out.println("blueprints comparison: " + ((double)blueprintsElapsed) / ((double)fermaElapsed) * 100.0 + "%");
        System.out.println("totorom comparison: " + ((double)totoromElapsed) / ((double)fermaElapsed) * 100.0 + "%");
        System.out.println("frames comparison: Not capable");
        System.out.println("gremlin comparison: " + ((double)gremlinElapsed) / ((double)fermaElapsed) * 100.0 + "%");
    }

    @Test
    public void testGetAnnotatedAdjacencies() {
        final int iterations = 250000;

        final TinkerGraph godGraph = new TinkerGraph();
        GodGraphLoader.load(godGraph);

        //Ferma test
        final com.syncleus.ferma.FramedGraph fermaGraph = new com.syncleus.ferma.DelegatingFramedGraph(godGraph, true, true);
        Timer.Context time = FERMA_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            final Iterable<? extends FermaGod> gods = fermaGraph.getFramedVertices("name", "saturn", FermaGod.class);
            Iterator<? extends FermaGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            final FermaGod father = godsIterator.next();
            Iterable<? extends FermaGod> children = father.getSons(FermaGod.class);
            for(final FermaGod child : children)
                Assert.assertTrue(child.getParents().iterator().next().getName().equals("saturn"));
        }
        final long fermaElapsed = time.stop();

        com.tinkerpop.frames.FramedGraphFactory factory = new com.tinkerpop.frames.FramedGraphFactory(new com.tinkerpop.frames.modules.gremlingroovy.GremlinGroovyModule());
        final com.tinkerpop.frames.FramedGraph framesGraph = factory.create(godGraph);
        time = FRAMES_TIMER.time();
        for(int i = 0; i < iterations; i++) {
            Iterable<FramesGod> gods = framesGraph.getVertices("name", "saturn", FramesGod.class);
            Iterator<FramesGod> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            final FramesGod father = godsIterator.next();
            Iterable<? extends FramesGod> children = father.getSons();
            for(final FramesGod child : children)
                Assert.assertTrue(child.getParents().iterator().next().getName().equals("saturn"));
        }
        final long framesElapsed = time.stop();

        System.out.println();
        System.out.println("=== testGetAnnotatedAdjacencies ===");
        System.out.println("blueprints comparison: Not capable");
        System.out.println("totorom comparison: Not capable");
        System.out.println("frames comparison: " + ((double)framesElapsed) / ((double)fermaElapsed) * 100.0 + "%");
        System.out.println("gremlin comparison: Not capable");
    }
}
