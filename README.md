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

    === testGetFramedVerticiesAndNextUntyped ===
    blueprints comparison: 79.69072692698396%
    totorom comparison: 481.9971835358758%
    frames comparison: Not capable
    gremlin comparison: 387.01946546351553%

    === testGetAnnotatedAdjacencies ===
    blueprints comparison: Not capable
    totorom comparison: Not capable
    frames comparison: 147.66762519410636%
    gremlin comparison: Not capable

    === testGetFramedVerticiesUntyped ===
    blueprints comparison: 89.70774373047517%
    totorom comparison: 424.63802661948523%
    frames comparison: Not capable
    gremlin comparison: 394.6841823306472%

    === testGetFramedVerticiesAndNextTyped ===
    blueprints comparison: 72.41328985684804%
    totorom comparison: 331.4376182492388%
    frames comparison: 194.97457588391953%
    gremlin comparison: 291.4059621108095%

    === testGetFramedVerticiesTyped ===
    blueprints comparison: 92.33925054886328%
    totorom comparison: 420.70409351338725%
    frames comparison: 96.37876492541486%
    gremlin comparison: 394.609052258521%

## Obtaining the Source

The official source repository for Ferma Benchmarks is located on the Syncleus Gerrit instance and can be cloned using
the following command.

```
git clone http://gerrit.syncleus.com/Ferma-benchmark
```

We also maintain a GitHub clone of the official repository which can be found
[here](https://github.com/Syncleus/Ferma-benchmark). Finally Syncleus also hosts an instance of GitLab which has a
clone of the repository which can be found [here](http://gitlab.syncleus.com/syncleus/Ferma-benchmark).
