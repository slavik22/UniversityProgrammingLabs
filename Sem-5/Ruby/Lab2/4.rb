# Lab 2 Task 4 Variant 2
decimal_num = 145

def decimal_to_binary(num)
  res = ""
  while num > 0
    res += (num % 2).to_s
    num /= 2
  end
  res.reverse
end

puts decimal_to_binary(decimal_num)
