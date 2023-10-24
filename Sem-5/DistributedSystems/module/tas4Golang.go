package main

import (
	"fmt"
	"sync"
	"time"
)

type Bus struct {
	busNumber int
	route     *Route
}

func (bus *Bus) run(wg *sync.WaitGroup) {
	defer wg.Done()
	for _, stop := range bus.route.getStops() {
		stop.addBus(bus)
	}
	fmt.Printf("Bus %d completed the route and returned to the fleet.\n", bus.busNumber)
}

type Stop struct {
	name     string
	busCount int
	sem      chan struct{}
}

func newStop(name string, busCount int) *Stop {
	return &Stop{
		name:     name,
		busCount: busCount,
		sem:      make(chan struct{}, busCount),
	}
}

func (stop *Stop) addBus(bus *Bus) {
	stop.sem <- struct{}{}
	fmt.Printf("Bus %d is at %s\n", bus.busNumber, stop.name)
	time.Sleep(1 * time.Second)
	<-stop.sem
}

type Route struct {
	stops []*Stop
}

func newRoute() *Route {
	return &Route{
		stops: make([]*Stop, 0),
	}
}

func (route *Route) addStop(stop *Stop) {
	route.stops = append(route.stops, stop)
}

func (route *Route) getStops() []*Stop {
	return route.stops
}

func main() {
	maxBusesAtStop := 2
	busesCount := 10

	route := newRoute()
	route.addStop(newStop("Stop A", maxBusesAtStop))
	route.addStop(newStop("Stop B", maxBusesAtStop))
	route.addStop(newStop("Stop C", maxBusesAtStop))

	var wg sync.WaitGroup
	wg.Add(busesCount)

	for i := 0; i < busesCount; i++ {
		go func(busNumber int) {
			bus := &Bus{busNumber, route}
			bus.run(&wg)
		}(i)
	}

	wg.Wait()
}
