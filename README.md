# coco_washing_machine
coco dataset toolkit

JDK: open-jdk 11

```shell
# build
mvn clean compile assembly:single

# execute
java -jar coco_washing_machine-jar-with-dependencies.jar <INPUT COCO JSON> <OUTPUT COCO JSON>

# example
java -jar coco_washing_machine-jar-with-dependencies.jar data/instances_train.json data/instances_train_out.json
```