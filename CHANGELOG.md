#CHANGELOG

## 1.0 (2015-02-21)
### Feature
- add CFs creating based on basic ontology relation (taxonomy, partonomy, dependence)
- final CF images produce by Cajun, Prefuse, GraphStream frameworks

## 1.0.1 (2015-07-15)
### API Changes
- Remove classes that use stale library
- Rename package *visplugin* -> *.visualization.* and *.GUI.* -> *.ui.*
- Remove runnable classes for testing
### Other
- Edit Maven set libraries
- Edit Maven Build
- Add *plugin.xml*, *viewconfig-mainTab.xml*, *META-INF* for build Protege plugin
- Add *build.xml* for build in Ant
- Add *.gitignore*

## 1.1 (2015-08-02)
### API Changes
- Rename package *visplugin* -> *.visualization.* and *.GUI.* -> *.ui.*
- Remove runnable classes for testing
- Remove list visitor visual methods in CFrameDecorator and OWLModelManagerDecorator
- Implemented MVP pattern for Base UI
- Implemented MVP pattern for visualizations: Cajun, GraphStream
### Bug Fix
- Fixed bug in build Maven which conflicted Eclipse.
 