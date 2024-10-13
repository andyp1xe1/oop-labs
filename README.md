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
> DescriptionSince input.json is ambiguous at id=17. 
> Thus the user is prompted to choose from the possible races.
to run:
```sh
cd kotlin_labs
gradle lab0_classification_system:run
```
