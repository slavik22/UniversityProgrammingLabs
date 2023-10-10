decimal_num = 334

def decimal_to_binary(num)
  res = ""
  while num > 0
    res << (num % 2).to_s
    num = num / 2
  end
  res.reverse
end

puts decimal_to_binary(decimal_num)