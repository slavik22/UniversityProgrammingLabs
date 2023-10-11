import numpy as np

def random_matrix(n, min_value=1, max_value=10):
    return np.random.randint(min_value, max_value + 1, size=(n, n+1)) 

def generate_diagonally_dominant_matrix(n, min_value=1, max_value=10):
    A = random_matrix(n)
    diagonal = np.sum(np.abs(A), axis=1)
    np.fill_diagonal(A, diagonal)
    return A

def generate_hilbert_matrix(n):
    hilbert_matrix = np.zeros((n, n), dtype=float)
    
    for i in range(1, n + 1):
        for j in range(1, n + 1):
            hilbert_matrix[i - 1, j - 1] = 1 / (i + j - 1)

    return hilbert_matrix


# a = np.array([
#     [10, 1, 3, 1, 4],
#     [2, 9, 1, 0, 2],
#     [0, 2, 9, 1, 6],
#     [1, 3, 1, 10, 2],

# ])

a = generate_diagonally_dominant_matrix(10)
# a = generate_hilbert_matrix(5)
print(a)

epsilon = 10e-8

def gauss(a):
    swaps = 0
    maxElements = []

    for l in range(len(a)):
        maxElement = a[l][l]
        maxIndex = l
        for i in range(l + 1, len(a)):
            if a[i][l] > maxElement:
                maxElement = a[i][l]
                maxIndex = i
        maxElements.append(maxElement)
        if maxIndex != l:
            swaps += 1
            a[[l, maxIndex]] = a[[maxIndex, l]]

        m = np.identity(len(a))

        m[l][l] = 1 / a[l][l]
        for i in range(l + 1, len(a)):
            m[i][l] = -a[i][l] / a[l][l]
        a = np.matmul(m, a)

    for i in range(len(a) - 1, 0, -1):
        for j in range(i - 1, -1, -1):
            a[j] = np.add(a[j], a[i] * -a[j][i])


    roots = []
    for i in range(len(a)):
        roots.append(a[i][-1])

    return roots

print(f"gauss: {gauss(a)}")


# ----------------------------------------------- Yakobi method -----------------------------------------------

def norm(a):
    r = -1
    for i in range(len(a)):
        sum = 0
        for j in range(len(a[i])):
            sum += abs(a[i][j])
        r = max(r, sum)
    return r

def meetsSufficientCondition(a):
    a = np.delete(a, len(a[0, :]) - 1, 1)
    for i in range(len(a)):
        sum = 0
        for j in range(len(a[i, :])):
            if i == j:
                continue
            sum += abs(a[i][j])
        if a[i][i] < sum:
            return False
    return True

def jacobi(a):
    x = np.zeros((len(a), 1))
    while True:
        xPrev = x
        x = np.zeros((len(a), 1))
        for i in range(len(a)):
            for j in range(len(a[i, :]) - 1):
                if i == j:
                    continue
                x[i][0] -= xPrev[j][0] * a[i][j]
            x[i][0] += a[i][len(a[i]) - 1]
            x[i][0] /= a[i][i]
        if norm(x - xPrev) < epsilon:
            break
    return x

if meetsSufficientCondition(a):
    print(f"jacobi: {jacobi(a)}")
else:
    print("jacobi: check failed")


# ----------------------------------------------- Seidel`s method -----------------------------------------------

def seidel(a):
    x = np.zeros((len(a), 1))

    while True:
        x_new = x.copy()
        for i in range(len(a)):
            sum1 = np.dot(a[i, :len(a)], x_new)
            x_new[i] = (a[i, -1] - sum1 + a[i, i] * x_new[i]) / a[i, i]

        if norm(x_new - x) < epsilon:
            return x_new

        x = x_new

if meetsSufficientCondition(a):
    print(f"seidel: {seidel(a)}")
else:
    print("seidel: check failed")