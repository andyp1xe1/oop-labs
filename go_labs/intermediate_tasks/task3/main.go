package main

import (
	"fmt"
	"github.com/andyp1xe1/oop-labs/go_labs/intermediate_tasks/task1/display"
)

type Assistant struct {
	name             string
	assignedDisplays []*display.Display
}

func NewAssistant(name string) Assistant {
	return Assistant{name, make([]*display.Display, 0)}
}

func (a *Assistant) assignDisplay(newDisplay *display.Display) {
	a.assignedDisplays = append(a.assignedDisplays, newDisplay)
}

func (a *Assistant) buyDisplay(toBuy *display.Display) *display.Display {
	for i, display := range a.assignedDisplays {
		if display == toBuy {
			a.assignedDisplays = append(a.assignedDisplays[:i], a.assignedDisplays[i+1:]...)
			return display
		}
	}
	return nil
}

func (a *Assistant) assist() {
	if len(a.assignedDisplays) == 0 {
		fmt.Println("No displays assigned.")
		return
	}

	for i := 0; i < len(a.assignedDisplays); i++ {
		for j := i + 1; j < len(a.assignedDisplays); j++ {
			a.assignedDisplays[i].CompareWithMonitor(a.assignedDisplays[j])
		}
	}

	fmt.Println("Available Displays:")
	for i, d := range a.assignedDisplays {
		fmt.Printf("%d: %s\n", i+1, d.GetModel())
	}

	var choice int
	fmt.Print("Select a display to buy (enter the number): ")
	fmt.Scan(&choice)

	if choice < 1 || choice > len(a.assignedDisplays) {
		fmt.Println("Invalid choice.")
		return
	}

	selectedDisplay := a.assignedDisplays[choice-1]
	purchasedDisplay := a.buyDisplay(selectedDisplay)

	if purchasedDisplay != nil {
		fmt.Printf("You have purchased: %s\n", purchasedDisplay.GetModel())
	} else {
		fmt.Println("Display not found.")
	}
}

func main() {
	assistant := NewAssistant("John")

	monitor1 := display.NewDisplay(1920, 1080, 81.59, "Monitor 1")
	monitor2 := display.NewDisplay(2560, 1440, 108.79, "Monitor 2")
	monitor3 := display.NewDisplay(3840, 2160, 163.18, "Monitor 3")
	monitor4 := display.NewDisplay(1280, 720, 55.64, "Monitor 4")
	monitor5 := display.NewDisplay(1600, 900, 66.89, "Monitor 5")
	monitor6 := display.NewDisplay(1920, 1200, 94.25, "Monitor 6")
	monitor7 := display.NewDisplay(2560, 1600, 126.00, "Monitor 7")

	assistant.assignDisplay(monitor1)
	assistant.assignDisplay(monitor2)
	assistant.assignDisplay(monitor3)
	assistant.assignDisplay(monitor4)
	assistant.assignDisplay(monitor5)
	assistant.assignDisplay(monitor6)
	assistant.assignDisplay(monitor7)

	assistant.assist()
}
