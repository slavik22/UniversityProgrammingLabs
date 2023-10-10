# Lab 3 Task 2

def y(x)
  x = x.to_f
  if -4 < x && x <= 0
    p1 = (x - 2).abs
    p2 = x ** 2 * Math.cos(x)
    (p1 / p2) ** 1/7
  elsif 0 < x && x <= 12
    p1 = Math.tan(x + 1 / Math.exp(x))
    p2 = Math.sin(x) ** 2
    (p1 / p2) ** 7/2
  else
    p1 = 1 + x
    p2 = 1 + x / p1
    p3 = 1 + x / p2
    1 / p3
  end
end

puts y(1)