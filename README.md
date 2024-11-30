# oop-labs

Here are the laboratory works for the oop course I am taking.
Each laboratory work is being worked on in its own branch.

## Classificator

The classificator is implemented using set filtering.

A global map is defined with all the races and their characteristics `map Race to Creature`.
The reverse index map is found for planets and traits, creating 2 index maps.

This is used to find the possible races using a creature's characteristics as indexes (see code)

It starts from a set with all the creatures, and gradually narrows down the options using intersection (for planet and traits) and filter (for age and isHumanoid).

> P.S.
> The input.json entry at id=17 is ambiguous.
> Thus the user is prompted to choose from the possible races.

To run:
```sh
cd kotlin_labs
gradle lab0_classification_system:run
```


## Intermediate tasks

This laboratory was written in golang, and I followed "oop best practices" to the best of my abilities in such a language.
This is because my subproject kotlin config broke, and the passing of program arguments in gradle is just horrendous (and I already knew golang)

```sh
cd ./go_labs/intermediate_tasks
go run ./task/{1..4}
```

fyi task 2's `textdata` package has a test.

```sh
cd ./go_labs/intermediate_tasks/task2
go test -v ./textdata
```
