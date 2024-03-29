def func(x_begin, x_end, a, b, c, delta)
  result = []
  (x_begin..x_end).step(delta) do |i|
    if c < 0 && i != 0
      f = -a * i - c
    else
      if c > 0 && i == 0
        f = (i - a) / -c
      else
        f = b * i / (c - a)
      end
    end
    result.push(f)
  end

  result.length.times do |i|
    result[i] = result[i].to_i
  end
  result
end

def tests
  test1
  test2
  test3
end

def test1
  x_begin = 1
  x_end = 4
  a = 1
  b = 1
  c = -1
  delta = 1

  expected = [0, -1, -2, -3]

  result = func(x_begin, x_end, a, b, c, delta)

  result.length.times do |i|
    if (result[i] - expected[i]).abs > 0.0001
      puts "Test 1 failed"
      return
    end
  end

  puts "Test 1 passed"
end

def test2
  x_begin = 1
  x_end = 4
  a = 0
  b = 1
  c = 1
  delta = 1

  expected = [1,2,3,4]

  result = func(x_begin, x_end, a, b, c, delta)

  result.length.times do |i|
    if (result[i] - expected[i]).abs > 0.0001
      puts "Test 1 failed"
      return
    end
  end

  puts "Test 2 passed"
end

def test3

  x_begin = -3
  x_end = 3
  a = 0
  b = 2
  c = 1
  delta = 1

  expected = [-6, -4, -2, 0, 2, 4, 6]

  result = func(x_begin, x_end, a, b, c, delta)

  result.length.times do |i|
    if (result[i] - expected[i]).abs > 0.0001
      puts "Test 3 failed"
      return
    end
  end

  puts "Test 3 passed"
end

tests

