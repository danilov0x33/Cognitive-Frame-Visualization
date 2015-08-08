# Cognitive Frame Visualizer
Cognitive Frame Visualizer - tool for making OWL Ontologies visualization based on the Cognitive Frames.

#### Prerequisites

To build and run the visualizer, you must have the following items installed:

+ Apache's [Maven](http://maven.apache.org/index.html).
+ A tool for checking out a [Git](http://git-scm.com/) repository.
+ A Protege distribution (4.3 or higher) if you want visualizer as plugin.


#### Build and install "Cognitive Frame Visualizer"

1. Get a copy code:

        git clone https://bitbucket.org/crider/ontvis.git CognitiveFrameVisualizer
    
2. Change into the CognitiveFrameVisualizer directory.

3. Simple command for build: **mvn**.  On build completion, the "target" directory will contain a CognitiveFrameVisualization-${version}.jar file.

4. Double-click on JAR to launch or copy the JAR file from the target directory to the "plugins" subdirectory of your Protege distribution.

#### Example plug-in screenshots
Example visualization cognitive frame concept "Router":

![](http://s32-temporary-files.radikal.ru/4c5b518c98124532947f74bab382ef3c/-929206895.jpg)