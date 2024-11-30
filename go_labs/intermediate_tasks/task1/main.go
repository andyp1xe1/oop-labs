package main

import (
	"github.com/andyp1xe1/oop-labs/go_labs/intermediate_tasks/task1/display"
)

func main() {
	monitor1 := display.NewDisplay(1920, 1080, 81.59, "Monitor 1")
	monitor2 := display.NewDisplay(2560, 1440, 108.79, "Monitor 2")
	monitor3 := display.NewDisplay(3840, 2160, 163.18, "Monitor 3")

	monitor1.CompareWithMonitor(monitor2)
	monitor1.CompareWithMonitor(monitor3)
	monitor2.CompareWithMonitor(monitor3)
}
