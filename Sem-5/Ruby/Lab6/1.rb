#Variant 8

class Student
    attr_reader :surname, :is_apartment_required, :experience, :worked_as_teacher, :school, :language 
  
    def initialize (surname, is_apartment_required, experience, worked_as_teacher, school, language)
      @surname = surname
      @is_apartment_required = is_apartment_required
      @experience = experience
      @worked_as_teacher = worked_as_teacher
      @school = school
      @language = language
    end
  end
  
  
  arr = [
    Student.new('Ivanenko', false, 10, true, "Kyiv Gymnasium", "english"),
    Student.new('Petrenko', true, 4, false, "Lviv Gymnasium", "german"),
    Student.new('Symonenko', true, 7, true, "Kharkiv Gymnasium", "spanish"),
    Student.new('Kovalenko', false, 2, false, "Odessa Gymnasium", "french"),
  ]
  
  
  def task1(arr)
    arr.select {|i| i.is_apartment_required == true}.length
  end
  
  
  def task2(arr)
    arr.select {|i| i.experience >= 2}
  end
  
  def task3(arr)
    arr.select {|i| i.school.length > 0}
  end

  def task4(arr)
    arr.group_by {|i| i.language}
  end
  
  puts "Task 1: #{task1(arr)}"
  puts "Task 2: #{task2(arr)}"
  puts "Task 3: #{task3(arr)}"
  puts "Task 4: #{task4(arr)}"