# Variant 4
class Abiturient
    attr_accessor :id
    attr_accessor :first_name
    attr_accessor :middle_name
    attr_accessor :last_name
    attr_accessor :address
    attr_accessor :phone_number
    attr_accessor :grades
  
    def initialize(id, first_name, middle_name, last_name, address, phone_number, grades)
      @id = id
      @first_name = first_name
      @middle_name = middle_name
      @last_name = last_name
      @address = address
      @phone_number = phone_number
      @grades = grades
    end
  
    def to_s
      "#{@id}, #{@first_name}, #{@middle_name}, #{@last_name}, #{@address}, #{@phone_number}, #{grades}"
    end
  
    def has_normal_sum
      val = @grades.sum / @grades.count
      val >= 60 and val < 90
    end
  end
  
  abiturient_list = [
    Abiturient.new(1, 'Taras', 'Shevchenko', 'Grygorovych', 'Ukraine', '+380973774457', [90, 60, 75, 80, 100]),
    Abiturient.new(2, 'Sterpan', 'Kirill', 'Rick', 'Kyiv', '+380503332226', [80, 60, 75, 90, 80]),
    Abiturient.new(3, 'Pavlo', 'Petrov', 'Ivanov', 'Lviv', '+38050343256', [100,90,98,87,95]),
    Abiturient.new(4, 'Gorge', 'Kurt', 'Mathew', 'Kharkiv', '+380937670671', [70,60,98,67,95]),
    Abiturient.new(5, 'Alex', 'Peter', 'Tom', 'Seatle', '+447587481337', [98, 90, 95, 91, 100]),
    Abiturient.new(6, 'Davin', 'Fedor', 'Alexander', 'Poltava', '+3809383848876', [60, 67, 75, 87, 63]),
    Abiturient.new(7, 'Tom', 'Lee', 'Joe', 'Los-Angeles', '+13079442338', [75,63,98,67,95]),
    Abiturient.new(8, 'Bob', 'Kurt', 'Tim', 'Toronto', '+447929181987', [98, 90, 95, 91, 100]),
    Abiturient.new(9, 'Ann', 'Emil', 'Bugatti', 'Warsaw', '+489122344556', [70, 87, 65, 67, 63]),
    Abiturient.new(10, 'Marie', 'Sophi', 'Lancaster', 'Lviv', '+380655553739', [70,60,98,67,95]),
  ]
  
  # abiturients who has at least one low grade
  def task_1(abiturient_list)
    abiturient_list.select{|abiturient| abiturient.grades.any? { |grade| grade < 75 } }.map(&:to_s)
  end
  
  # abiturients whose sum of grades is greater than given value
  def task_2(abiturient_list, sum)
    abiturient_list.select { |abiturient| abiturient.grades.sum > sum }.map(&:to_s)
  end
  
  # top n abiturients sorted by sum of grades
  def task_3_a(abiturient_list, limit)
    abiturient_list.sort_by{ |abiturient| abiturient.grades.sum}.reverse.first(limit).map(&:to_s)
  end
  
  # abiturients who have normal sum of grades
  def task_3_b(abiturient_list)
    abiturient_list.select(&:has_normal_sum).map(&:to_s)
  end
  
#   puts "Task 1: #{task_1(abiturient_list)}"
#   puts "Task 2: #{task_2(abiturient_list, 400)}"
#   puts "Task 3_a: #{task_3_a(abiturient_list, 3)}"
#   puts "Task 3_b: #{task_3_b(abiturient_list)}"
  
  puts "TESTS"
  def test_task_1(abiturient_list)
    should_equal_list = [
        "1, Taras, Shevchenko, Grygorovych, Ukraine, +380973774457, [90, 60, 75, 80, 100]", 
        "2, Sterpan, Kirill, Rick, Kyiv, +380503332226, [80, 60, 75, 90, 80]", 
        "4, Gorge, Kurt, Mathew, Kharkiv, +380937670671, [70, 60, 98, 67, 95]", 
        "6, Davin, Fedor, Alexander, Poltava, +3809383848876, [60, 67, 75, 87, 63]", 
        "7, Tom, Lee, Joe, Los-Angeles, +13079442338, [75, 63, 98, 67, 95]", 
        "9, Ann, Emil, Bugatti, Warsaw, +489122344556, [70, 87, 65, 67, 63]", 
        "10, Marie, Sophi, Lancaster, Lviv, +380655553739, [70, 60, 98, 67, 95]"
    ]
    result = task_1(abiturient_list)
    puts "Test task 1: #{result == should_equal_list}"
  end
  test_task_1(abiturient_list)
  
  def test_task_2(abiturient_list)
    should_equal_list = [
        "1, Taras, Shevchenko, Grygorovych, Ukraine, +380973774457, [90, 60, 75, 80, 100]", 
        "3, Pavlo, Petrov, Ivanov, Lviv, +38050343256, [100, 90, 98, 87, 95]", 
        "5, Alex, Peter, Tom, Seatle, +447587481337, [98, 90, 95, 91, 100]", 
        "8, Bob, Kurt, Tim, Toronto, +447929181987, [98, 90, 95, 91, 100]"
    ]
    result = task_2(abiturient_list, 400)
    puts "Test task 2: #{result == should_equal_list}"
  end
  test_task_2(abiturient_list)
  
  def test_task_3_a(abiturient_list)
    should_equal_list = [
        "8, Bob, Kurt, Tim, Toronto, +447929181987, [98, 90, 95, 91, 100]", 
        "5, Alex, Peter, Tom, Seatle, +447587481337, [98, 90, 95, 91, 100]", 
        "3, Pavlo, Petrov, Ivanov, Lviv, +38050343256, [100, 90, 98, 87, 95]"
    ]
    result = task_3_a(abiturient_list, 3)
    puts "Test task 3_a: #{result == should_equal_list}"
  end
  test_task_3_a(abiturient_list)
  
  def test_task_3_b(abiturient_list)
    should_equal_list = [
        "1, Taras, Shevchenko, Grygorovych, Ukraine, +380973774457, [90, 60, 75, 80, 100]", 
        "2, Sterpan, Kirill, Rick, Kyiv, +380503332226, [80, 60, 75, 90, 80]", 
        "4, Gorge, Kurt, Mathew, Kharkiv, +380937670671, [70, 60, 98, 67, 95]", 
        "6, Davin, Fedor, Alexander, Poltava, +3809383848876, [60, 67, 75, 87, 63]", 
        "7, Tom, Lee, Joe, Los-Angeles, +13079442338, [75, 63, 98, 67, 95]", 
        "9, Ann, Emil, Bugatti, Warsaw, +489122344556, [70, 87, 65, 67, 63]", 
        "10, Marie, Sophi, Lancaster, Lviv, +380655553739, [70, 60, 98, 67, 95]"
    ]
    result = task_3_b(abiturient_list)
    puts "Test task 3_b: #{result == should_equal_list}"
  end
  test_task_3_b(abiturient_list)