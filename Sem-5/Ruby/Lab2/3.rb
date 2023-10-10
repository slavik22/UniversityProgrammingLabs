# Lab 2 Task 3 Variant 2
binary_num = "1000010010010"

def binary_to_decimal (num)
  res = 0
  num.chars.each.with_index do |n, i|
    res += 2 ** i if n == "1"
  end
  res
end

puts binary_to_decimal(binary_num)
