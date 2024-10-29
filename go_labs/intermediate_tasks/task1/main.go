package main

func main() {
	monitor1 := newDisplay(1920, 1080, 81.59, "Monitor 1")
	monitor2 := newDisplay(2560, 1440, 108.79, "Monitor 2")
	monitor3 := newDisplay(3840, 2160, 163.18, "Monitor 3")

	monitor1.compareWithMonitor(monitor2)
	monitor1.compareWithMonitor(monitor3)
	monitor2.compareWithMonitor(monitor3)
}
