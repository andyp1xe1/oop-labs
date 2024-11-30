package textdata

import (
	"testing"
)

func TestFindLongestWord(t *testing.T) {
	tests := []struct {
		input    string
		expected string
	}{
		{"Hello friend", "friend"},
		{"The quick brown fox jumps over the lazy dog", "quick"},
		{"A, B, C, D!", "A"},
		{"", ""},
	}

	for _, test := range tests {
		result := findLongestWord(test.input)
		if result != test.expected {
			t.Errorf("findLongestWord(%q) = %q; expected %q",
				test.input, result, test.expected)
		}
	}
}

func TestCheckVowel(t *testing.T) {
	tests := []struct {
		input    rune
		expected bool
	}{
		{'a', true},
		{'A', true},
		{'b', false},
		{'B', false},
		{'e', true},
		{'E', true},
	}

	for _, test := range tests {
		result := checkVowel(test.input)
		if result != test.expected {
			t.Errorf("checkVowel(%c) = %v; expected %v",
				test.input, result, test.expected)
		}
	}
}

func TestProcessText(t *testing.T) {
	text := "Hello! How are you today?? WHAT'S ASPARAGUS???? This is a test."
	data := NewTextData("test.txt", text)

	if data.GetNumberOfLetters() != 44 {
		t.Errorf("Expected 44 letters, got %d", data.GetNumberOfLetters())
	}
	if data.GetNumberOfVowels() != 18 {
		t.Errorf("Expected 18 vowels, got %d", data.GetNumberOfVowels())
	}
	if data.GetNumberOfConsonants() != 26 {
		t.Errorf("Expected 26 consonants, got %d", data.GetNumberOfConsonants())
	}
	if data.GetNumberOfSentences() != 4 {
		t.Errorf("Expected 4 sentences, got %d", data.GetNumberOfSentences())
	}
	if data.GetLongestWord() != "ASPARAGUS" {
		t.Errorf("Expected longest word 'today', got '%s'", data.GetLongestWord())
	}
}
