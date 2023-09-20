package main

import (
	"fmt"
	"math/rand"
	"time"
)

type fight struct {
	a, b int
}

func max(d fight) int {
	if d.a >= d.b {
		return d.a
	}
	return d.b
}

func battle(fights <-chan fight, winners chan<- int) {
	for fight := range fights {
		time.Sleep(500 * time.Millisecond)

		winner := max(fight)
		fmt.Printf("Fight between %v and %v - winner %v \n", fight.a, fight.b, winner)

		winners <- winner
	}
}

func main() {
	playersCount := 40
	players := make([]int, playersCount)

	for i := 0; i < playersCount; i += 1 {
		players[i] = rand.Intn(100)
	}

	fights := make(chan fight, 100)
	winners := make(chan int, 100)

	for i := 0; i < playersCount/2; i += 1 {
		go battle(fights, winners)
	}

	for playersCount > 1 {
		if len(players) >= 2 {
			var fight fight
			fight.a = players[0]
			fight.b = players[1]
			fights <- fight

			players = players[2:]
		}

		select {
		case winner := <-winners:
			playersCount -= 1
			players = append(players, winner)
		default:
		}
	}

	close(fights)
	close(winners)

	fmt.Printf("Competition winner: %d\n", players[0])
}
