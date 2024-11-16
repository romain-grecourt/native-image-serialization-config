# Native Image Serialization And Reflection

A project that simulates two different modules:
- Library "A" contains a `Serializable` type with a `String` field
- Library "B" uses `Object.getClass().getDeclaredConstructor(String.class)` blindly (I.e. catches `NoSuchMethodException`)

Scenario:
1. Default: `NoSuchMethodException` is thrown, "B" catches it and uses a fallback mechanism that works.
2. When using a `serialization-config.json` with the `Serializable` type, `MissingReflectionRegistrationError` is thrown

When using a `reflect-config.json` with `java.lang.String` and `queryAllDeclaredConstructors=true`
a `MissingReflectionRegistrationError` is thrown.

This seems to replicate what is done implicitly with `serialization-config.json`.

Who is to blame ?
- Should "A" provide full reflection metadata for String, to avoid query only metadata ?
- Should "B" provide full reflection metadata for basic types ? (It's doing it blindly on any type that comes)
- Should the end user provide the required reflection medata for String ?

---

- "A" is `com.oracle.database.jdbc:ojdbc11:23.6.0.24.10`, see `oracle.sql.converter.CharacterConverterJDBC`
- "B" is `org.glassfish.jersey:jersey-common:3.1.9`, see [ParamConverters](https://github.com/eclipse-ee4j/jersey/blob/3.1/core-common/src/main/java/org/glassfish/jersey/internal/inject/ParamConverters.java#L137)

I.e. Native Image `21.0.3` + Jersey `3.1.9` + Oracle JDBC driver `23.6.0.24.10` is broken.

---

## Build And Run

Build:
```shell
mvn package -Pnative-image
```

Run:
```shell
./target/native-image-serialization-config
```

See also the following files (remove the `.txt` extension to use them):
- `reflect-config.json.txt`
- `serialization-config.json.txt`.

