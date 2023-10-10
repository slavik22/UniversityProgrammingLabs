# Lab 2 Task 1 Variant 2

vertices = 
[
    [414, 42], [274, 45], [190, 34], [132, 21], [72, 54],
    [56, 107], [42, 152], [60, 221], [108, 252], [156, 285],
    [204, 307], [247, 304], [296, 303], [359, 304], [403, 303],
    [452, 294], [464, 255], [461, 223], [460, 193], [466, 173],
    [437, 156], [386, 146], [348, 121], [353, 86], [371, 75],
    [410, 70], [422, 52]
  ]

def polygon_area(vertices)
    n = vertices.length
    area = 0.0
  
    for i in 0...n
      x1, y1 = vertices[i]
      x2, y2 = vertices[(i + 1) % n]
      area += (x1 * y2 - x2 * y1)
    end
  
    area = area / 2.0
    return area.abs
  end
  
  puts polygon_area(vertices)  