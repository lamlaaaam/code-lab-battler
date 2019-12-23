# Starts searching using average point, then
# brute forces if the above fails.
# A green circle signifies success using first method.
# Blue signifies brute forced.

# CONTROLS: SPACEBAR to generate, ESC to quit

# This program draws a circle if possible,
# otherwise no circle is drawn, and "NO SUCH CIRCLE"
# is logged in the console
# Percentage of points is rounded down to nearest integer
# Also, I had to draw the points as larger than 1 pixel, otherwise
# they were nearly invisible. This means some points may look like
# they touch the circle, but are actually outside.

# ---------------------------------------
# CHANGE THESE VALUES FOR TESTING
# Number of points
NUM_OF_POINTS_MIN = 1
NUM_OF_POINTS_MAX = 1000
# Canvas size in pixels
CANVAS_WIDTH = 1000
CANVAS_HEIGHT = 1000
# Percentage of points to lie in circle
PERCENTAGE = 0.5
# ---------------------------------------
# Importing modules
import pygame, random, math 

# FUNCTIONS FOR POINTS
# ---------------------------------------
# Generate a single point (x,y) where x,y runs [0,1]
def generatePoint():
    x = random.randint(0, CANVAS_WIDTH)
    y = random.randint(0, CANVAS_HEIGHT)
    return (x, y)
# Generate list of N points
def generateNPoints(N):
    points = []
    for i in range(N):
        points.append(generatePoint())
    return points
# Generate a single number N where N runs [x,y]
def generateN(x, y):
    return random.randint(x, y)
# Generate a random list of points
def generatePoints():
    N = generateN(NUM_OF_POINTS_MIN, NUM_OF_POINTS_MAX)
    NPoints = generateNPoints(N)
    return NPoints
# Draw points on screen given a list of points
def drawPoints(points, surface):
    for point in points:
        pygame.draw.circle(surface, (255, 255, 255), point, 1)
# Calculate distance between 2 given points
def calculateDist(point1, point2):
    return math.hypot(point1[0] - point2[0], point1[1] - point2[1])
# Given a set of points, find the average point
def findAveragePoint(points):
    average = [int(sum(x) / len(x)) for x in zip(*points)]
    return average
# Given a point, determine the furthest point from it
def furthestPoint(fromPoint, points):
    distances = [calculateDist(fromPoint, point) for point in points]
    idx = distances.index(max(distances))
    return points[idx]
# ---------------------------------------
# END FUNCTIONS FOR POINTS

# FUNCTIONS FOR CIRCLES
# ---------------------------------------
# Create a circle given center and radius
def createCircle(center, radius):
    return (center, radius)
# Draw circle on screen given circle 
def drawCircle(circle, surface, color):
    center, radius = circle
    pygame.draw.circle(surface, color, center, radius, 1)
# Check if a given circle is within bounds
def circleInBounds(circle):
    center, radius = circle
    centerX, centerY = center
    leftBound, rightBound, upBound, downBound = centerX - radius, centerX + radius, centerY - radius, centerY + radius
    return leftBound in range(0, CANVAS_WIDTH + 1) and rightBound in range(0, CANVAS_WIDTH + 1) and upBound in range(0, CANVAS_HEIGHT + 1) and downBound in range(0, CANVAS_HEIGHT + 1)
# ---------------------------------------
# END FUNCTIONS FOR CIRCLES

# FUNCTIONS FOR MAIN ALGORITHM
# ---------------------------------------
# Given a circle and list of points, count the number of points
# that fall on / in the circle
def countPointsInCircle(circle, points):
    count = 0
    center, radius = circle
    for point in points:
        count += 1 if calculateDist(point, center) <= radius else 0
    return count
def circleRcoverN(center, points, N, canvas):
    x = center[0]
    y = center[1]
    maxRadius = min([x, y, CANVAS_WIDTH - x, CANVAS_HEIGHT - y]) 
    for radius in range(maxRadius, 0, -1):
        circle = createCircle(center, radius)
        canvas.fill((0, 0, 0))
        drawPoints(points, canvas)
        drawCircle(circle, canvas, (255, 0, 0))
        pygame.display.update()
        pointsInCircle = countPointsInCircle(circle, points)
        if (pointsInCircle == N): return circle
        elif (pointsInCircle < N): return None
# Given points and percentage, find a circle to cover
# that many points
def findCircle(points, percentage, canvas):
    copyPoints = points.copy()
    N = int(percentage * len(points))
    while (len(copyPoints) > 0):
        center = findAveragePoint(copyPoints)
        circle = circleRcoverN(center, points, N, canvas)
        if (circle != None): return circle
        else: copyPoints.remove(furthestPoint(center, copyPoints))
    return None
def findCircleBrute(points, percentage, canvas):
    N = int(percentage * len(points))
    jump = 1
    for x in range(CANVAS_WIDTH // 2, CANVAS_WIDTH, jump):
        for y in range(CANVAS_HEIGHT // 2, CANVAS_HEIGHT):
            circle = circleRcoverN((x, y), points, N, canvas)
            if (circle != None): return circle
        for y in range(CANVAS_HEIGHT // 2 - 1, 0, -1):
            circle = circleRcoverN((x, y), points, N, canvas)
            if (circle != None): return circle
    for x in range(CANVAS_WIDTH // 2, 0, -jump):
        for y in range(CANVAS_HEIGHT // 2, CANVAS_HEIGHT):
            circle = circleRcoverN((x, y), points, N, canvas)
            if (circle != None): return circle
        for y in range(CANVAS_HEIGHT // 2 - 1, 0, -1):
            circle = circleRcoverN((x, y), points, N, canvas)
            if (circle != None): return circle
    return None
# ---------------------------------------
# END FUNCTIONS FOR MAIN ALGORITHM

# FUNCTIONS FOR PYGAME
# ---------------------------------------
# Main call for PyGame 
def main():
    # Initialize PyGame
    pygame.init()
    canvas = pygame.display.set_mode([CANVAS_WIDTH, CANVAS_HEIGHT])
    def initPyGame():
        p = generatePoints()
        c = findCircle(p, PERCENTAGE, canvas)
        if (c != None):
            canvas.fill((0, 0, 0))
            drawPoints(p, canvas)
            drawCircle(c, canvas, (0, 255, 0))
            pygame.display.update()
        else: 
            c2 = findCircleBrute(p, PERCENTAGE, canvas)
            canvas.fill((0, 0, 0))
            drawPoints(p, canvas)
            drawCircle(c2, canvas, (0, 0, 255)) if c2 != None else print("NO BRUTE CIRCLE")
            pygame.display.update()
        while True:
            for event in pygame.event.get():
                key = pygame.key.get_pressed()
                if event.type == pygame.QUIT or key[pygame.K_ESCAPE]:
                    pygame.quit()
                    exit()
                if key[pygame.K_SPACE]: 
                    initPyGame()
    initPyGame()
main()
# ---------------------------------------
# END OF FILE
