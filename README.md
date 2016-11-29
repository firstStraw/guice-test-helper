# guice-test-helper
A small library that simplifies unit testing of Guice injectors and modules.

##Getting it
Via Maven:
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.firststraw/guice-test-helper/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.firststraw/guice-test-helper)

##Instructions
Create an InjectorTester instance with either an existing Injector or the modules
you want to test.  Then use the InjectorTester to verify the presence and
configuration of bindings.  You can verify the presence of of the binding by class,
TypeLiteral, or Key.  Then verify the type of the binding with methods such as
"asConstructorBinding".  Each binding type has different configuration aspects that
can be verified.  Then you can verify the scoping of the binding.  Unsuccessful
attempts at verification will throw various unchecked exceptions depending on the
nature of the disparity between what you are attempting to verify and what is
actually configured in the Injector or modules.

To check for annotated bindings,
you'll need to use the verification method that takes a Key.  For example:
```
    InjectorTester tester = new InjectorTester(new MyModule());
    tester.verifyBindingFor(Key.get(MyType.class, Names.named("blah")));
```
