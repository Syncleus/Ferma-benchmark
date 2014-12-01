# Ferma Benchmarks

This is a simple application to act as a benchmark comparing the Ferma Graph-to-Object layer with Totorom, TinkerPop
Frames, and TinkerPop Blueprints. It will help us ensure Ferma remains the top-performing option.

For more information see the main [Ferma Project page](https://github.com/freemo/Ferma).

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

    === testGetFramedVerticiesUntyped ===
    blueprints comparison: 92.52451072453944%
    totorom comparison: 291.8244188362433%
    frames comparison: Not capable

    === testGetFramedVerticiesTyped ===
    blueprints comparison: 95.27096480804313%
    totorom comparison: 290.537541185409%
    frames comparison: 93.84834048518455%

    === testGetFramedVerticiesAndNextUntyped ===
    blueprints comparison: 56.88277266726579%
    totorom comparison: 233.05768571442377%
    frames comparison: Not capable

    === testGetFramedVerticiesAndNextTyped ===
    blueprints comparison: 79.81304027007491%
    totorom comparison: 246.56771314953966%
    frames comparison: 208.2637483295682%

    === testGetAnnotatedAdjacencies ===
    blueprints comparison: Not capable
    totorom comparison: Not capable
    frames comparison: 131.7585697153527%

    === testGetConcreteAdjacenciesUntyped ===
    blueprints comparison: 26.855895921397128%
    totorom comparison: 120.77521803703586%
    frames comparison: Not capable

    === testGetConcreteAdjacenciesTyped ===
    blueprints comparison: Not capable
    totorom comparison: 113.30430579833033%
    frames comparison: Not capable


## Obtaining the Source

The official source repository for Ferma Benchmarks is located on the Syncleus Gerrit instance and can be cloned using
the following command.

```
git clone http://gerrit.syncleus.com/Ferma-benchmark
```

We also maintain a GitHub clone of the official repository which can be found
[here](https://github.com/Syncleus/Ferma-benchmark). Finally Syncleus also hosts an instance of GitLab which has a
clone of the repository which can be found [here](http://gitlab.syncleus.com/syncleus/Ferma-benchmark).