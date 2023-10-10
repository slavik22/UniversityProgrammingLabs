B = [12.34, 56.78, 123.45, 67.89, 45.67, 89.12, 34.56, 78.90, 98.76, 23.45, 45.67, 67.89, 12.34, 56.78, 34.56, 89.12]

sum_of_even_integers = 0
sum_of_indices = 0

B.each_with_index do |number, index|
  integer_part = number.to_i
  
  if index.even?
    sum_of_even_integers += integer_part
  end
  
  sum_of_indices += index
end

puts sum_of_even_integers
puts sum_of_indices