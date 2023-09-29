package main

import (
	"context"
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func main() {
	var waitGroup sync.WaitGroup
	ctx, cancel := context.WithTimeout(context.Background(), time.Second*10)
	defer cancel()

	tableChan := make(chan []bool)
	semaphore := make(chan bool, 1)

	semaphore <- true
	waitGroup.Add(1)
	go controller(tableChan, semaphore, &waitGroup, ctx)

	for i := 0; i < 3; i++ {
		waitGroup.Add(1)
		go smoker(i, tableChan, semaphore, &waitGroup, ctx)
	}

	waitGroup.Wait()

}

func controller(tableChan chan []bool, semaphore chan bool, waitGroup *sync.WaitGroup, ctx context.Context) {
	table := make([]bool, 3)
	for {
		select {
		default:
			<-semaphore
			c1, c2 := randComponents()
			fmt.Println("Put items:", c1+1, "and", c2+1)
			table[c1] = true
			table[c2] = true
			select {
			case <-ctx.Done():
				waitGroup.Done()
				return
			default:
				tableChan <- table
			}
		case <-ctx.Done():
			waitGroup.Done()
			return
		}
	}
}

func smoker(component int, tableChan chan []bool, semaphore chan bool, waitGroup *sync.WaitGroup, ctx context.Context) {
	for {
		select {
		case table := <-tableChan:
			if !table[component] {
				for i := range table {
					table[i] = false
				}
				fmt.Println("Smoking ", component+1)
				time.Sleep(time.Second)
				semaphore <- true
			} else {
				time.Sleep(1000)
				tableChan <- table
			}
		case <-ctx.Done():
			waitGroup.Done()
			return
		}
	}

}

func randComponents() (int, int) {
	for {
		component1 := rand.Intn(3)
		component2 := rand.Intn(3)

		if component1 != component2 {
			return component1, component2
		}
	}
}
