package main

import (
	"fmt"
	"math"
)

type Display struct {
	width, height int
	ppi           float64
	model         string
}

func newDisplay(width, height int, ppi float64, model string) *Display {
	return &Display{
		width:  width,
		height: height,
		ppi:    ppi,
		model:  model,
	}
}

func (d *Display) compareSharpness(other *Display) {
	dppi := d.ppi - other.ppi
	if dppi < 0 {
		fmt.Printf(
			"Display %v is sharper than %v by %.2f ppi\n",
			other.model, d.model, math.Abs(dppi))
		return
	}

	if dppi > 0 {
		fmt.Printf(
			"Display %v is sharper than %v by %.2f ppi\n",
			d.model, other.model, math.Abs(dppi))
		return
	}

	fmt.Printf("Both %v and %v have equal sharpness\n", d.model, other.model)
}

func (d *Display) getDiagonal() float64 {
	return math.Sqrt(float64(d.width*d.width) + float64(d.height*d.height))
}

func (d *Display) compareSize(other *Display) {
	var bigger string
	var smaller string
	diff := d.getDiagonal() - other.getDiagonal()
	if diff == 0 {
		fmt.Printf(
			"Both %v and %v are of the same size -- they have the same diagonal\n",
			d.model, other.model)
	}
	if diff > 0 {
		bigger = d.model
		smaller = other.model
	} else {
		bigger = other.model
		smaller = d.model
	}
	fmt.Printf(
		"%v is bigger in diagonal compared to %v by %.2f\n",
		bigger, smaller, math.Abs(diff),
	)
}

func (d *Display) compareWithMonitor(other *Display) {
	str := fmt.Sprintf("COMPARISON: %v VS %v", d.model, other.model)
	println(str)
	for range str {
		print("-")
	}
	println()
	d.compareSharpness(other)
	d.compareSize(other)
	println()
}
