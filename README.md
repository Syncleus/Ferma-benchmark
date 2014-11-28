# Ferma Benchmarks

This is a simple application to act as a benchmark comparing the Ferma Graph-to-Object layer with Totorom, TinkerPop
Frames, and TinkerPop Blueprints. It will help us ensure Ferma remains the top-performing option.

For more information see the main [Ferma Project page](https://github.com/freemo/Ferma).

## Results

After starting the [Ferma project](https://github.com/Syncleus/Ferma) we decided to do a benchmark suite that compares
Ferma with TinkerPop Frames, Totorom, and raw Blueprint calls. The purpose of Ferma is to be a high-performance
alternative to all of these with the same convince of frames (annotations). As such having these benchmarks will help
keep us on track as we develop the Ferma project. It is important to note we are comparing right now with the totorom
SNAPSHOT as there have been some recent improvements there.

The way I configured the benchmark is simple. I do the same task under each framework for a set number of iterations,
recording the time it takes under each framework. I then provide a % comparing each of the alternative solutions to
Ferma. A % of 100% means Ferma and the framework it was compared to took the same time to execute. If the percentage is
below 100% then Ferma performed slower than the alternative, if the percentage is above 100% then Ferma out-performed
the alternative. __You will see below that Ferma outperforms every alternative by a significant margin.__ The only exception
is when compared against raw Blueprints, but this is to be expected.

Here is the output I get on my computer (this is a very modern and overpowered computer):

    === testGetFramedVerticiesTyped ===
    blueprints comparison: 96.4289533592499%
    totorom comparison: 267.4258014509324%
    frames comparison: 104.2812839573229%

    === testGetFramedVerticiesUntyped ===
    blueprints comparison: 93.29597377136415%
    totorom comparison: 269.1028835861173%
    frames comparison: Not capable

    === testGetFramedVerticiesAndNextTyped ===
    blueprints comparison: 58.81628404738877%
    totorom comparison: 231.2218381934416%
    frames comparison: Not capable

    === testGetFramedVerticiesAndNextUntyped ===
    blueprints comparison: 76.17905971740055%
    totorom comparison: 249.84948165251478%
    frames comparison: 210.75482573712438%

    === testGetAnnotatedAdjacencies ===
    blueprints comparison: Not capable
    totorom comparison: Not capable
    frames comparison: 128.16164815430258%

    === testGetConcreteAdjacenciesUntyped ===
    blueprints comparison: 26.595091759814483%
    totorom comparison: 119.24491582376943%
    frames comparison: Not capable

    === testGetConcreteAdjacenciesTyped ===
    blueprints comparison: Not capable
    totorom comparison: 114.77782400279175%
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