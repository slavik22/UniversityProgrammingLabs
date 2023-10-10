package main

import (
	"fmt"
	"math/rand"
	"sync"
)

var (
	wg     sync.WaitGroup
	n      = 3
	arrLen = 5
)

func modVal() int {
	b := rand.Intn(2)
	if b == 0 {
		return -1
	} else {
		return 1
	}
}

func modArr(array []int) {
	i := rand.Intn(len(array))
	array[i] = modVal()
	wg.Done()
}

func sum(a []int) int {
	r := 0
	for _, v := range a {
		r += v
	}
	return r
}

func main() {
	var arrays [][]int

	for i := 0; i < n; i += 1 {
		arrays = append(arrays, make([]int, arrLen))
	}

	for {
		wg.Add(n)
		for i := 0; i < n; i += 1 {
			go modArr(arrays[i])
		}
		wg.Wait()

		s0, s1, s2 := sum(arrays[0]), sum(arrays[1]), sum(arrays[2])
		if s0 == s1 && s0 == s2 {
			break
		}

	}

	for i := 0; i < n; i += 1 {
		fmt.Printf("%v\n", arrays[i])
	}
}
