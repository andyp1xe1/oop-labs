package textdata

import (
	"strings"
	"unicode"
)

type TextData interface {
	GetFileName() string
	GetLongestWord() string
	GetNumberOfVowels() int
	GetNumberOfConsonants() int
	GetNumberOfLetters() int
	GetNumberOfSentences() int
	GetText() string
}

type textData struct {
	fileName           string
	text               string
	numberOfVowels     int
	numberOfConsonants int
	numberOfLetters    int
	numberOfSentences  int
	longestWord        string
}

func NewTextData(fileName, text string) TextData {
	data := &textData{
		fileName: fileName,
		text:     text,
	}
	data.processText()
	return data
}

func (td *textData) GetFileName() string {
	return td.fileName
}
func (td *textData) GetNumberOfVowels() int {
	return td.numberOfVowels
}
func (td *textData) GetNumberOfConsonants() int {
	return td.numberOfConsonants
}
func (td *textData) GetNumberOfLetters() int {
	return td.numberOfLetters
}
func (td *textData) GetNumberOfSentences() int {
	return td.numberOfSentences
}
func (td *textData) GetLongestWord() string {
	return td.longestWord
}
func (td *textData) GetText() string {
	return td.text
}

func (td *textData) processText() {

	td.longestWord = findLongestWord(td.text)

	reader := strings.NewReader(td.text)
	for reader.Len() > 0 {
		if toConsumeLetter(reader) {
			td.numberOfLetters++
			if wasVowel(reader) {
				td.numberOfVowels++
			} else {
				td.numberOfConsonants++
			}
		} else if consumeEndOfSentence(reader) {
			td.numberOfSentences++
		}
	}
}

func toConsumeLetter(r *strings.Reader) bool {
	ch, _, _ := r.ReadRune()
	r.UnreadRune()
	return unicode.IsLetter(ch)
}

func wasVowel(r *strings.Reader) bool {
	ch, _, _ := r.ReadRune()
	return checkVowel(ch)
}

func checkVowel(ch rune) bool {
	vowels := "aeiouAEIOU"
	return strings.ContainsRune(vowels, ch)
}

func consumeEndOfSentence(r *strings.Reader) bool {
	if !checkEndOfSentence(r) {
		return false
	}
	for checkEndOfSentence(r) {
		continue
	}
	return true
}

func checkEndOfSentence(r *strings.Reader) bool {
	ch, _, _ := r.ReadRune()
	return ch == '.' || ch == '!' || ch == '?'
}

func findLongestWord(text string) string {
	words := strings.FieldsFunc(text, func(r rune) bool {
		return !unicode.IsLetter(r)
	})
	longestWord := ""
	for _, word := range words {
		if len(word) > len(longestWord) {
			longestWord = word
		}
	}
	return longestWord
}
