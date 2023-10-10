# Lab 3 Task 5 Variant 2

require 'bigdecimal'

def Y(x, n, c)
  return ((x**2 + x*(x**2 - c**2)**(1/2))**(1/n)) + ((x**2 + 1) / (1/x + 1/n)) + ((3*x + c**x) / (x+1))
end

def Z(x, n, c)
  return 2 * (Math.sin(4*x)**-1 - Math.tan(7*Math::PI / 2 + 4*x)) + Math.tan(5*Math::PI + x) 
end

def F(x, n, c)
  case x
  when 2...n
    return Y(x, n, c)
  when n...2*n
    return Z(x, n, c)
  else
    return Y(x, n, c) + Z(x, n, c)
  end
end

def subtask1(c, n)
  x = 1.0
  step = (n - 1)/(n + c)
  puts "step is #{step}"
  while x <= n
    puts "#{x} | #{Y(x, n, c).round(10)}"
    x += step
  end
end

def subtask2(c, n)
  x = Math::PI / n
  step = (Math::PI - x)/((3/2)*n + c)
  puts "step is #{step}"
  while x <= Math::PI
    puts "#{x} | #{Z(x, n, c)}"
    x += step
  end
end

def subtask3(c, n)
  x = 2.0
  step = (c - 2)/(2 * n)
  puts "step is #{step}"
  while x <= c
    puts "#{x} | #{F(x, n, c)}"
    x += step
    return if x == c && step == 0
  end
end

puts "Task 5"
print "Enter N: "
n = gets.chomp.to_f

print "Enter c: "
c = gets.chomp.to_f

puts "Subtask 1:"
puts "x  |  Y"
subtask1(c, n)


puts "\nSubtask 2:"
puts "x  |  Z"
subtask2(c, n)

puts "\nSubtask 3:"
puts "x  |  F" 
subtask3(c, n)