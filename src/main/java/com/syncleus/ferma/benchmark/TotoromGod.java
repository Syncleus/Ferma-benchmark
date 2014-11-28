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

import org.jglue.totorom.FramedVertex;

public class TotoromGod extends FramedVertex {
    public String getName() {
        return this.getProperty("name");
    }

    public void setName(String newName) {
        this.setProperty("name", newName);
    }

    public void removeName() {
        this.setProperty("name", null);
    }

    public Integer getAge() {
        return this.getProperty("age");
    }

    public String getType() {
        return this.getProperty("type");
    }

    public Iterable<? extends TotoromGod> getSons() {
        return this.in("father").frame(TotoromGod.class);
    }

    Iterable<? extends TotoromGod> getParents() {
        return this.out("father").frame(TotoromGod.class);
    }
}
