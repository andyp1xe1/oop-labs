package main

import (
	"fmt"
	"os"

	"github.com/andyp1xe1/oop-labs/go_labs/intermediate_tasks/task2/textdata"
)

var fileReader = FileReader{}

type FileReader struct{}

func (f FileReader) readFileIntoString(path string) (string, error) {
	content, err := os.ReadFile(path)
	if err != nil {
		return "", err
	}
	return string(content), nil
}

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Please provide the path to one or more .txt files.")
		return
	}

	for _, filePath := range os.Args[1:] {
		text, err := fileReader.readFileIntoString(filePath)
		if err != nil {
			fmt.Printf("Error reading file %s: %v\n", filePath, err)
			continue
		}

		textData := textdata.NewTextData(filePath, text)

		fmt.Printf("\nFile Name: %v\n", textData.GetFileName())
		fmt.Printf("Text Content: %v\n", textData.GetText())
		fmt.Printf("Number of Vowels: %v\n", textData.GetNumberOfVowels())
		fmt.Printf("Number of Consonants: %v\n", textData.GetNumberOfConsonants())
		fmt.Printf("Number of Letters: %v\n", textData.GetNumberOfLetters())
		fmt.Printf("Number of Sentences: %v\n", textData.GetNumberOfSentences())
		fmt.Printf("Longest Word: %v\n", textData.GetLongestWord())
	}
}
