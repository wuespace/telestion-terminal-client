# Telestion Terminal Client

A Terminal Client for Telestion Projects

## Getting started

### Docker

Please ensure, you have Docker installed and ready to go.

Pull and run the latest Docker image from the GitHub Container Registry:
```
docker pull <image_name>
docker run -it <image_name>
```

### Building from source

**Please ensure, you have Java 16 installed and ready to go.**

1. Clone the project:
```
git clone git@github.com:wuespace/telestion-terminal-client.git
```

2. Set up the Personal Access Token which allows gradle to load the Telestion Dependencies.
   (see )

2. Build the project with gradle:
```
./gradlew assembleDist
```

3. Build the docker image:
```
docker build -t <image_name> .
```

4. Run the docker image:
```
docker run -it <image_name>
```

## Introduction

_TODO: Add app intro_

## Contributing

First, please clone the project:
```
git clone git@github.com:wuespace/telestion-terminal-client.git
```

Then, you a Personal Access Token which has access
to the [telestion-core](https://github.com/wuespace/telestion-core) maven packages.

Copy the `gradle.properties.example` file and name it `gradle.properties`.
After that, edit the contents and replace the `GITHUB_ACTOR` with your _GitHub username_
and replace the `GITHUB_TOKEN` with your generated _Personal Access Token_.

Then make gradle executable and run it the first time:
```
chmod +x gradlew
./gradlew
```

Please, check out to your working branch: 
```
git checkout -b <your_branch_name>
```

Now, add your first contribution!

After you finished adding your changes, commit and push them to GitHub:
```
git add ./
git commit -m "feat: <your_commit_message>
git push --set-upstream origin
```

## About

This is part of [Telestion](https://telestion.wuespace.de/),
a project by [WüSpace e.V.](https://www.wuespace.de/).
