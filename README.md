## Stack Exchange CLI Search Client

Search stack exchange websites in your terminal!
Rewritten in python with better terminal output here: https://github.com/myke2424/stack-exchange-cli

## How it works

Displays the highest up-voted question and top answer for your search request \
*Inspired by*: https://github.com/chubin/cheat.sh

## Install

*Generate JAR with dependencies*

```bash
# Run inside root directory
mvn assembly:assembly -DdescriptorId=jar-with-dependencies
```

## Run Application

```bash
java -jar se-search-jar-with-dependencies.jar -q="Search Query"
```

## Command Line Arguments

Search query (*required*)

```bash
# -q or --query
java -jar se-search-jar-with-dependencies.jar -q="How to merge two dictionaries"
```

Stack exchange site (*optional, default=stackoverflow*)

```bash
# -s or --s
java -jar se-search-jar-with-dependencies.jar -q="Big O vs Big Theta" -s="softwareengineering"
```

Stack exchange tags (*optional)

```bash
# -t or --tags
java -jar se-search-jar-with-dependencies.jar -q="Segmentation fault cause" -t="c c++ rust"
```

## Configuration

Application can be configured using the **config.yaml** located in:

```
/src/main/resources/config.yaml
```
