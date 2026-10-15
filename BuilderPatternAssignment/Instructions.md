# Builder Pattern Implementation for a messaging service

## Problem Statement

You are developing a messaging application with support for various message types, including text, images, audio, and video. Each message type can have additional attributes and settings, such as delivery status and timestamps. The current approach of creating message objects using multiple overloaded constructors has become error-prone and challenging to maintain. You should streamline the creation of message objects with different configurations and immutable objects.

## Assignment

Your task is to implement the Builder pattern to create instances of message objects with different configurations. The Builder pattern allows for step-by-step construction of complex objects while keeping the creation process separate from the main object.

## Implementing the Builder Pattern

1. **Review the original class**: You have been provided with a class named `Message`. This class represents different message types and their attributes. Your task is to implement the Builder pattern to create instances of a class with the same properties.

2. **Create the builder class**: Create a new class called `MessageBuilder` that will implement the builder patter. A dummy class has been provided for you inside `Message` class to start with.