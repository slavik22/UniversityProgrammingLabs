require 'test/unit'
extend Test::Unit::Assertions

a_1 = [
  [1, 2, 3],
  [4, 5, 6],
  [7, 8, 9]
]

b_1 = [
  [11, 2, 3],
  [4, 55, 6],
  [7, 8, 98]
]

a_2 = [
  [1, 8, 0],
  [0, 0, 0],
  [0, 0, 95]
]

b_2 = [
  [-18, 0, 39],
  [0, 1, 0],
  [0, 0, 0]
]

def multiply(a, b)
  c = Array.new(a.length) { Array.new(b[0].length) }
  (0...a.length).each do |i|
    (0...b[0].length).each do |j|
      c[i][j] = 0
      (0...a[0].length).each do |k|
        c[i][j] += a[i][k] * b[k][j]
      end
    end
  end
  c
end

c = multiply(a_1, b_1)
c_result = [
  [40, 136, 309],
  [106, 331, 630],
  [172, 526, 951]
]

d = multiply(a_2, b_2)
d_result = [
  [-18, 8, 39],
  [0, 0, 0],
  [0, 0, 0]
]

assert_equal c, c_result
assert_equal d, d_result
