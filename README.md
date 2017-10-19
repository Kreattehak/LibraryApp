# LibraryApp
Simple Library app which uses command line to perform crud operations.

## Quick setup

```
You can import this project into your IDE or download zip package and run it manually.
```

## Features
This app can be launched with deserialization of previously serialized Library objects.
You can pass the path to serialized file through command-line arguments.
By default if none arguments were provided, this app creates folder `LibraryApp` in user home,
where it saves it's state in a serialized file. So the path looks like:

`C:\Users\Domowy\LibraryApp\Library`

Each time a new file is created. This app tracks a count of files, when it exceeds `5` files, a short message 
is printed for user to consider delete some of the files.  

This app uses java 8 streams along with lambda expressions.
