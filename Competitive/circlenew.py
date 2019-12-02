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
CANVAS_WIDTH = 400
CANVAS_HEIGHT = 400
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
        pygame.draw.circle(surface, (0, 0, 0), point, 3)
# Calculate distance between 2 given points
def calculateDist(point1, point2):
    return math.hypot(point1[0] - point2[0], point1[1] - point2[1])
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
        canvas.fill((150, 150, 150))
        drawPoints(points, canvas)
        drawCircle(circle, canvas, (255, 0, 0))
        pygame.display.update()
        pointsInCircle = countPointsInCircle(circle, points)
        if (pointsInCircle == N): return circle
        elif (pointsInCircle < N): return None
# Given points and percentage, find a circle to cover
# that many points
def findCircle(points, percentage, canvas):
    N = int(percentage * len(points))
    for x in range(CANVAS_WIDTH // 2, CANVAS_WIDTH):
        for y in range(CANVAS_HEIGHT // 2, CANVAS_HEIGHT):
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
        canvas.fill((150, 150, 150))
        drawPoints(p, canvas)
        drawCircle(c, canvas, (0, 255, 0)) if c != None else print("NO SUCH CIRCLE")
        pygame.display.update()

        running = True
        while running:
            for event in pygame.event.get():
                key = pygame.key.get_pressed()
                if event.type == pygame.QUIT or key[pygame.K_ESCAPE]:
                    running = False
                if key[pygame.K_SPACE]: 
                    initPyGame()
    initPyGame()
main()
# ---------------------------------------
# END OF FILE
