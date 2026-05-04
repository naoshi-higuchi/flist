# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
mvn compile

# Run all tests
mvn test

# Run a single test class
mvn test -Dtest=FListTest

# Run a single test method
mvn test -Dtest=FListTest#testHead

# Generate Javadoc
mvn javadoc:javadoc

# Package as JAR
mvn package
```

## Architecture

This is a single-class Java library (`jp.nhiguchi.libs.flist.FList<E>`) — an immutable, singly-linked cons-cell list that implements `java.util.List<E>`.

Key design points:
- **Structural sharing**: `cons(head, tail)` / `prepend` are O(1) and reuse the existing tail.
- **Singleton empty list**: `EMPTY` is a static final sentinel; `isEmpty()` uses identity comparison (`this == EMPTY`).
- **Mutation methods throw**: All mutating `List` methods (`add`, `remove`, `set`, `clear`, etc.) throw `UnsupportedOperationException`.
- **`append` is O(n²)**: It rebuilds the list recursively; prefer `prepend` + `reverse` for batch construction.
- **`previous()` on `ListIterator` is O(n)**: Walks from the head each time — noted intentionally in the source.
- **Factory methods**: `flist()`, `flist(E)`, `flist(E...)`, `flist(Collection)`, `cons(E, FList)` — all static, imported statically in tests.
- **`equals`**: Compares element-by-element against any `List`, consistent with the `List` contract.

The test class (`FListTest`) covers every public method including iterator traversal, boundary conditions, and `UnsupportedOperationException` paths.
