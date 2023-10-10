# Lab 3 Task 4 Variant 2

EPS = 0.00001

def factorial n
  n > 1 ? n * factorial(n - 1).to_f : 1
end

def sum1
  sum = 0.0
  n = 2
  num = 0
  while num < EPS
    num = (factorial(2*n - 1) / 2*factorial(n + 1)) ** (n * (n + 1))
    sum += num
    n += 1
  end
  sum
end

def sum2(eps)
  pi_over_4 = 0.0
  term = 1.0
  n = 1

  loop do
    pi_over_4 += term
    n += 2
    term = (-1)**((n - 1) / 2) * (1.0 / n)

    break if term.abs <= eps
  end

  pi_over_4
end


def sum3
  n = 1
  num = 1
  sum = 0
  while num > EPS
    num = factorial(3 * n - 1) * factorial(2 * n - 1) / (factorial(4 * n) * (3 ** (2 * n)) * factorial(2*n))
    sum += num
    n += 1
  end
  sum
end



puts "Task 1: #{sum1}"
puts "Enter number for task 2: "
x = gets.chomp.to_i
puts "Task 2: #{sum2(0.00001)}"
puts "pi/4 = #{ Math::PI / 4 }"
puts "Task 3: #{sum3}"