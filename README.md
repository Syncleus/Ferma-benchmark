# Ferma Benchmarks

This is a simple application to act as a benchmark comparing the Ferma Graph-to-Object layer with Totorom, TinkerPop
Frames, and TinkerPop Blueprints. It will help us ensure Ferma remains the top-performing option.

For more information see the main [Ferma wiki page](http://wiki.syncleus.com/index.php/Ferma).

## Results

After starting the [Ferma project](https://github.com/Syncleus/Ferma) we decided to do a benchmark suite that compares
Ferma with TinkerPop Frames, Totorom, and raw Blueprint calls. The purpose of Ferma is to be a high-performance
alternative to all of these with the same convince of frames (annotations). As such having these benchmarks will help
keep us on track as we develop the Ferma project.

The way I configured the benchmark is simple. I do the same task under each framework for a set number of iterations,
recording the time it takes under each framework. I then provide a % comparing each of the alternative solutions to
Ferma. A % of 100% means Ferma and the framework it was compared to took the same time to execute. If the percentage is
below 100% then Ferma performed slower than the alternative, if the percentage is above 100% then Ferma out-performed
the alternative. __You will see below that Ferma outperforms every alternative by a significant margin.__ The only exception
is when compared against raw Blueprints, but this is to be expected.

Here is the output I get on my computer (this is a very modern and overpowered computer):

    === testGetFramedVerticesUntyped ===
    blueprints comparison: 93.24348944761809%
    tinkerpop3 comparison: 1698.9392272131245%
    totorom comparison: 423.67469757091635%
    frames comparison: Not capable
    gremlin comparison: 390.5251428045431%
    peapod comparison: Not Capable

    === testGetFramedVerticesTyped ===
    blueprints comparison: Not Capable
    tinkerpop3 comparison: Not Capable
    totorom comparison: 403.4655305796317%
    frames comparison: 99.93781089933795%
    gremlin comparison: Not Capable
    peapod comparison: 2074.05266481737%

    === testGetFramedVerticesAndNextUntyped ===
    blueprints comparison: 80.86610293318655%
    tinkerpop3 comparison: 1171.5222612963312%
    totorom comparison: 493.3926104721222%
    frames comparison: Not capable
    gremlin comparison: 338.6254392506979%
    peapod comparison: Not Capable

    === testGetFramedVerticesAndNextTyped ===
    blueprints comparison: Not Capable
    tinkerpop3 comparison: Not Capable
    totorom comparison: 349.97570532583313%
    frames comparison: 187.0806085510584%
    gremlin comparison: 315.60038209596655%
    peapod comparison: 1670.368656863122%

    === testGetAnnotatedAdjacencies ===
    blueprints comparison: Not capable
    tinkerpop3 comparison: Not Capable
    totorom comparison: Not capable
    frames comparison: 209.05528566425681%
    gremlin comparison: Not capable
    peapod comparison: 265.72131591027147%

## Obtaining the Source

The official source repository for Ferma Benchmarks is located on the Syncleus Gerrit instance and can be cloned using
the following command.

```
git clone http://gerrit.syncleus.com/Ferma-benchmark
```

We also maintain a GitHub clone of the official repository which can be found
[here](https://github.com/Syncleus/Ferma-benchmark). Finally Syncleus also hosts an instance of GitLab which has a
clone of the repository which can be found [here](http://gitlab.syncleus.com/syncleus/Ferma-benchmark).
