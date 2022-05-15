# SpringLab

_Spring Asteroids game_

### Introduction

In this assignment, we apply the Spring component framework in the Asteroid game setting.

### Objectives

The objective of this assignment is to get familiar with the Spring container, provide sufficient information to get
started with CODA practice using Spring, and appreciate the component-oriented features of the Spring framework.

### Classwork

I expect you to focus on the Spring container at business level. That is, for now we skip Spring MVC and persistence
component at view and persistence level.

- Just to have a simple example. Inspect the
  [SpringAgeCalculator](https://github.com/sweat-tek/SB4-KOM-F20/tree/master/SpringAgeCalculator) example from the
  book. To run the example run “Main” in the “AgeCalculatorSpringClient”. Note, Spring does not solve the flat
  classpath problem and do not have a class-loader per module. For that reason, Spring is not a full-blown component
  framework as OSGi and NetBeans Module system.
- Implement components from the Asteroids game using the Spring container based on the
  [JavaLab](https://drive.google.com/file/d/1230YX4-bS_8-lXXFp4qqAC1f_mLAmDl9/view). You can combine the use of
  NetBeans Module System (NBM) with Spring runtime container.

Use the NBM for handling the life-cycle of components and then use Spring dependency injection inside each component.
Typically, you only need the Spring IoC container if the component implementation is very complex and you want a lower
coupling inside the component. In the exercise, you can instantiate the player and enemy using Spring, even though the
system is not so complex that we really need dependency injection.

- I can recommend the
  [Spring reference manual](https://docs.spring.io/spring-framework/docs/5.2.5.RELEASE/spring-framework-reference/) for
  more details about the Spring Framework.

### Resources

- [About how to combine Lookup and Spring](http://wiki.apidesign.org/wiki/LookupAndSpring)
- [Spring Framework](https://spring.io/projects/spring-framework/)
- [Spring reference manual](https://docs.spring.io/spring-framework/docs/5.2.5.RELEASE/spring-framework-reference/)
