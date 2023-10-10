# Lab 3 Task 3 Variant 2,3

def task2
  sum = 0
  8.times.each { |i|
    sum += 1.0 / (3 ** i)
  }
  sum
end

def task3(x)
  print "Enter n:"
  n = gets.to_i

  sum = 0
  fact = 1
  n.times.each { |i|
    sum += (x ** i) / fact
    fact *= (i + 1)
  }
  sum
end
