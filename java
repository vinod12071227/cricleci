version: 2.1

jobs:
  build:
    docker:
      - image: 'circleci/openjdk:8-jdk'
    steps:
      - checkout
      - run:
          name: build & test
          command: mvn verify
      - persist_to_workspace:
          root: .
          paths:
            - target/*
  sonar:
    docker:
      - image: 'circleci/openjdk:11-jdk'
    steps:
      - checkout
      - attach_workspace:
          at: .
      - run:
          name: sonar
          command: mvn sonar:sonar

workflows:
  main:
    jobs:
      - build
      - sonar:
          context: token
          requires:
            - build
