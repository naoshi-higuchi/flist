# FList

An immutable, singly-linked cons-cell list for Java that implements `java.util.List<E>`.

## Features

- **Immutable** — all mutating `List` methods throw `UnsupportedOperationException`
- **Structural sharing** — `prepend` and `cons` are O(1) and reuse the existing tail
- **`java.util.List` compatible** — drop-in wherever a `List` is expected
- **`equals` / `hashCode`** — consistent with the `List` contract; compares element-by-element against any `List`

## Requirements

- Java 26+
- Maven 3.x

## Build

```bash
mvn compile          # compile
mvn test             # run all tests
mvn package          # build JAR
mvn javadoc:javadoc  # generate Javadoc
```

## Usage

Import the static factory methods:

```java
import static jp.nhiguchi.libs.flist.FList.*;
```

### Creating lists

```java
FList<String> empty = flist();                        // []
FList<String> one   = flist("a");                     // [a]
FList<String> three = flist("a", "b", "c");           // [a, b, c]
FList<String> cons  = cons("x", flist("y", "z"));     // [x, y, z]
FList<String> fromC = flist(someCollection);          // copy from any Collection
```

### Head / tail decomposition

```java
FList<Integer> list = flist(1, 2, 3);
int head = list.head();          // 1
FList<Integer> tail = list.tail(); // [2, 3]
```

### Prepend (O(1)) vs append (O(n))

```java
FList<Integer> list = flist(2, 3);
FList<Integer> prepended = list.prepend(1); // [1, 2, 3]  — prefer this
FList<Integer> appended  = list.append(4);  // [2, 3, 4]  — O(n), rebuilds the list
```

For building a list from many elements, use `prepend` + `reverse`:

```java
FList<Integer> result = flist();
for (int i = 0; i < n; i++) result = result.prepend(i);
result = result.reverse(); // O(n) total, not O(n²)
```

### Reverse

```java
flist(1, 2, 3).reverse(); // [3, 2, 1]
```

### Standard List operations

`FList` implements the full `java.util.List<E>` interface:

```java
FList<String> list = flist("a", "b", "c");
list.size();           // 3
list.get(1);           // "b"
list.contains("c");    // true
list.indexOf("b");     // 1
list.subList(0, 2);    // [a, b]
list.toArray();        // Object[]{"a", "b", "c"}

for (String s : list) { ... }          // for-each
list.stream().filter(...).toList();    // Stream API
```

### String formatting

```java
flist(1, 2, 3).toString();                     // "[1, 2, 3]"
flist(1, 2, 3).toStringWithoutBrackets();      // "1, 2, 3"
flist(1, 2, 3).toStringWithoutBrackets(" | "); // "1 | 2 | 3"
```

## Performance notes

| Operation | Complexity |
|---|---|
| `prepend` / `cons` | O(1) |
| `head` / `tail` | O(1) |
| `reverse` | O(n) |
| `get(i)` | O(n) |
| `size` | O(n) |
| `append(elem)` | O(n) |
| `append(collection)` | O(n²) |
| `ListIterator.previous()` | O(n) per call |

## Coordinates

```xml
<dependency>
  <groupId>jp.nhiguchi.libs</groupId>
  <artifactId>flist</artifactId>
  <version>0.3</version>
</dependency>
```

## License

[MIT](LICENSE)
