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
- Add *plugin.xml*, *viewconfig-mainTab.xml*, *MANIFEST.MF* for build Protege plugin
- Add *build.xml* for build in Ant
- Add *.gitignore*

## 1.1 (2015-08-02)
### API Changes
- Rename package *visplugin* -> *.visualization.* and *.GUI.* -> *.ui.*
- Remove runnable classes for testing from src
- Remove list visitor visual methods in CFrameDecorator and OWLModelManagerDecorator
- Implemented Model-View-Presenter(MVP) pattern for Base UI
- Implemented MVP pattern for visualizations: Cajun, GraphStream, Prefuse
- Add MultiPresenter redirecting event in presenterVisMethod's: Event->ViewTreeNode->presenterTreeNode->presenterVisMethod->ViewVisMethod
- Rename OntologyManager -> ModelMultiOntology
### Feature
- Add Menu Item "Visualizations" to main frame which add visualization in tab panel
### Bug Fix
- Fixed bug in build Maven which conflicted Eclipse
- Fixed bug in thread prefuse visualization
### Other
- Edit *plugin.xml*, *viewconfig-mainTab.xml*, *MANIFEST.MF*
- Remove Ant build and *MANIFEST.MF*. **Use Maven build** for build jar file: Protege plugin and runnable if frame.
- Add logback.xml to resource
 