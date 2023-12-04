class Participant
    attr_accessor :name
  
    def initialize(name)
      @name = name
    end
  end
  
  class Venue
    attr_accessor :name
  
    def initialize(name)
      @name = name
    end
  end
  
  class Concert
    attr_accessor :name, :genre, :date, :cost, :venue, :participants, :seats_available, :bookings
  
    def initialize(name, genre, date, cost, venue)
      @name = name
      @genre = genre
      @date = date
      @cost = cost
      @venue = venue
      @participants = []
      @bookings = []
      @seats_available = {}
    end
  
    def add_participant(participant)
      @participants << participant
    end
  
    def add_seats(category, count)
      @seats_available[category] = count
    end

    def book_seat(participant, seat_category)
        if @seats_available[seat_category].to_i > 0
          booking = Booking.new(self, participant, seat_category)
          @bookings << booking
          @seats_available[seat_category] -= 1
          puts "Seat booked for #{participant.name} in category #{seat_category}"
        else
          puts "No available seats in category #{seat_category}"
        end
      end
    
      def display_bookings
        puts "Bookings for #{@name}:"
        @bookings.each do |booking|
          puts "#{booking.participant.name} - #{booking.seat_category}"
        end
        puts "-----------------------"
      end
  
    def display_info
      puts "Concert: #{@name}"
      puts "Genre: #{@genre}"
      puts "Date: #{@date}"
      puts "Cost: $#{@cost}"
      puts "Venue: #{@venue.name}"
      puts "Participants: #{@participants.map(&:name).join(', ')}"
      puts "Seats Available: #{@seats_available}"
      puts "-----------------------"
    end
  
    def self.sort_by_genre(concerts)
      concerts.sort_by(&:genre)
    end
  
    def self.sort_by_date(concerts)
      concerts.sort_by(&:date)
    end
  
    def self.sort_by_cost(concerts)
      concerts.sort_by(&:cost)
    end
  
    def self.sort_by_participant(concerts)
      concerts.sort_by { |concert| concert.participants.map(&:name).join(', ') }
    end
  
    def self.sort_by_venue(concerts)
      concerts.sort_by { |concert| concert.venue.name }
    end
  end
  
  class Booking
    attr_accessor :concert, :participant, :seat_category
  
    def initialize(concert, participant, seat_category)
      @concert = concert
      @participant = participant
      @seat_category = seat_category
    end
  end

  
venue1 = Venue.new("Concert Hall A")
venue2 = Venue.new("Outdoor Arena B")
  
participant1 = Participant.new("Artist 1")
participant2 = Participant.new("Band 2")
  
concert1 = Concert.new("Rock Night", "Rock", "2023-12-10", 50, venue1)
concert1.add_participant(participant1)
concert1.add_participant(participant2)
concert1.add_seats("General", 500)

concert2 = Concert.new("Pop Party", "Pop", "2023-12-15", 40, venue2)
concert2.add_participant(participant2)
concert2.add_seats("VIP", 200)

concert1.book_seat(participant1, "General")
concert1.book_seat(participant2, "General")
concert2.book_seat(participant2, "VIP")

concert1.display_bookings
concert2.display_bookings
  
concerts = [concert1, concert2]
  
sorted_by_genre = Concert.sort_by_genre(concerts)
sorted_by_date = Concert.sort_by_date(concerts)
sorted_by_cost = Concert.sort_by_cost(concerts)
sorted_by_participant = Concert.sort_by_participant(concerts)
sorted_by_venue = Concert.sort_by_venue(concerts)
  
puts "Sorted by Genre:"
sorted_by_genre.each(&:display_info)
  
puts "Sorted by Date:"
sorted_by_date.each(&:display_info)
  
puts "Sorted by Cost:"
sorted_by_cost.each(&:display_info)
  
puts "Sorted by Participants:"
sorted_by_participant.each(&:display_info)  

puts "Sorted by Venue:"
sorted_by_venue.each(&:display_info)
  